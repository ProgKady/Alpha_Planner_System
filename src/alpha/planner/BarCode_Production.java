package alpha.planner;

import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.table.TableFilter;

public class BarCode_Production extends Application {

    private final TableView<RowData> table = new TableView<>();
    private final ObservableList<RowData> data = FXCollections.observableArrayList();
    private JFXButton loadBtn;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> pendingSave;
    private TablePosition<RowData, ?> anchorCell; // Track anchor for range selection

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    private static String vvaall, vvaalll;

    private static final String[] COLUMNS = {
            "date", "line", "shift", "code", "name", "machine_number",
            "style", "po", "name_wash",
            "process",
            "quantity", "start_time", "end_time"
    };

    @Override
    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private Connection connect() throws Exception {
        String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            if (line.startsWith("DB=")) {
                vvaall = line.split("=", 2)[1];
                break;
            }
        }
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:" + vvaall);
    }

    @Override
    public void start(Stage stage) {
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            javafx.scene.text.Font cairoSemiBold = javafx.scene.text.Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WASHINGController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setStyle("-fx-font-weight:bold;");

        for (String colName : COLUMNS) {
            TableColumn<RowData, String> col = new TableColumn<>(colName);
            col.setCellValueFactory(param -> param.getValue().getProperty(colName));
            col.setCellFactory(TextFieldTableCell.forTableColumn());

            col.setOnEditCommit(event -> {
                String newVal = event.getNewValue() == null ? "" : event.getNewValue().trim();
                RowData row = event.getRowValue();
                applyAndCascade(row, colName, newVal, false);
                scheduleSaveAndRecalc();
                // Restore selection and focus after edit
                int rowIndex = event.getTablePosition().getRow();
                int colIndex = table.getColumns().indexOf(event.getTableColumn());
                Platform.runLater(() -> {
                    table.getSelectionModel().clearAndSelect(rowIndex, table.getColumns().get(colIndex));
                    table.getFocusModel().focus(rowIndex, table.getColumns().get(colIndex));
                    table.requestFocus();
                    scrollIfNeeded(rowIndex, colIndex);
                });
            });

            table.getColumns().add(col);
        }

        table.setItems(data);
        new TableFilter<>(table);

        loadBtn = btn("");
        loadBtn.setGraphic(new ImageView(new Image(getClass().getResource("bonus_load.png").toExternalForm())));
        loadBtn.setOnAction(e -> {
            loadData();
            scheduleSaveAndRecalc();
            // Select first cell after loading
            if (!data.isEmpty()) {
                Platform.runLater(() -> {
                    updateSelection(0, 0, false, false);
                });
            }
        });
        Platform.runLater(loadBtn::fire);

        JFXButton saveBtn = btn("");
        saveBtn.setGraphic(new ImageView(new Image(getClass().getResource("bonus_save.png").toExternalForm())));
        saveBtn.setOnAction(e -> scheduleSaveAndRecalc());

        JFXButton addRowBtn = btn("");
        addRowBtn.setGraphic(new ImageView(new Image(getClass().getResource("bonus_addrow.png").toExternalForm())));
        addRowBtn.setOnAction(e -> {
            RowData r = new RowData();
            attachReactiveBehavior(r);
            data.add(r);
            scheduleSaveAndRecalc();
            // Select first cell of new row
            Platform.runLater(() -> {
                int rowIndex = data.size() - 1;
                updateSelection(rowIndex, 0, false, false);
            });
        });

        JFXButton addManyRowsBtn = btn("");
        addManyRowsBtn.setGraphic(new ImageView(new Image(getClass().getResource("bonus_addrows.png").toExternalForm())));
        addManyRowsBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("5");
            dialog.setHeaderText("Add Many Rows");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setMinSize(600, 50);
            styleDialog(dialogPane);
            dialog.setContentText("Enter number of rows to add:");
            dialog.showAndWait().ifPresent(numStr -> {
                try {
                    int num = Integer.parseInt(numStr);
                    int firstNewRow = data.size();
                    for (int i = 0; i < num; i++) {
                        RowData r = new RowData();
                        attachReactiveBehavior(r);
                        data.add(r);
                    }
                    scheduleSaveAndRecalc();
                    // Select first cell of first new row
                    Platform.runLater(() -> {
                        updateSelection(firstNewRow, 0, false, false);
                    });
                } catch (NumberFormatException ex) {
                    showError("Invalid Number");
                }
            });
        });

        JFXButton deleteBtn = btn("");
        deleteBtn.setGraphic(new ImageView(new Image(getClass().getResource("bonus_delete.png").toExternalForm())));
        deleteBtn.setOnAction(e -> {
            ObservableList<RowData> selected = table.getSelectionModel().getSelectedItems();
            if (selected != null && !selected.isEmpty()) {
                data.removeAll(selected);
                scheduleSaveAndRecalc();
                // Select first cell after deletion
                if (!data.isEmpty()) {
                    Platform.runLater(() -> {
                        updateSelection(0, 0, false, false);
                    });
                }
            }
        });

        JFXButton exportBtn = btn("");
        exportBtn.setGraphic(new ImageView(new Image(getClass().getResource("bonus_export.png").toExternalForm())));
        exportBtn.setOnAction(e -> exportToExcel());
        
//        JFXButton goout = btn("Go Out");
//        goout.setOnAction(e -> {
//            
//            saveDataa("2-9");
//            
//        });

        ToolBar toolbar = new ToolBar(loadBtn, saveBtn, addRowBtn, addManyRowsBtn, deleteBtn, exportBtn);

        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-font-family: 'Cairo SemiBold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #333333;"
            );
        root.setTop(toolbar);
        root.setCenter(table);

        Scene scene = new Scene(root, 1200, 600);
        try {
            BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
            String theme = bis.readLine();
            bis.close();
            scene.getStylesheets().add(getClass().getResource(theme).toExternalForm());
        } catch (Exception ignored) {}

        // Handle key shortcuts (Ctrl+C, Ctrl+V, Ctrl+D, Delete)
        table.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyShortcuts);

        // Handle Enter key to move to the right cell or next row
        table.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (table.getEditingCell() != null) return;
            if (event.getCode() == KeyCode.ENTER) {
                TablePosition<RowData, ?> focusedCell = table.getFocusModel().getFocusedCell();
                int row = focusedCell != null && focusedCell.getRow() >= 0 ? focusedCell.getRow() : 0;
                int col = focusedCell != null && focusedCell.getColumn() >= 0 ? focusedCell.getColumn() : 0;
                if (col < table.getColumns().size() - 1) {
                    col++;
                } else if (row < data.size() - 1) {
                    col = 0;
                    row++;
                }
                updateSelection(row, col, event.isShiftDown(), event.isControlDown());
                event.consume();
            }
        });

        // Handle arrow keys and Tab for navigation and selection
        table.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (table.getEditingCell() != null) return;

            TablePosition<RowData, ?> focusedCell = table.getFocusModel().getFocusedCell();
            int row = focusedCell != null && focusedCell.getRow() >= 0 ? focusedCell.getRow() : 0;
            int col = focusedCell != null && focusedCell.getColumn() >= 0 ? focusedCell.getColumn() : 0;

            if (event.getCode().isArrowKey() || event.getCode() == KeyCode.TAB) {
                if (event.getCode() == KeyCode.RIGHT) {
                    if (col < table.getColumns().size() - 1) col++;
                } else if (event.getCode() == KeyCode.LEFT) {
                    if (col > 0) col--;
                } else if (event.getCode() == KeyCode.UP) {
                    if (row > 0) row--;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (row < data.size() - 1) row++;
                } else if (event.getCode() == KeyCode.TAB) {
                    if (event.isShiftDown()) {
                        if (col > 0) {
                            col--;
                        } else if (row > 0) {
                            col = table.getColumns().size() - 1;
                            row--;
                        }
                    } else {
                        if (col < table.getColumns().size() - 1) {
                            col++;
                        } else if (row < data.size() - 1) {
                            col = 0;
                            row++;
                        }
                    }
                }
                updateSelection(row, col, event.isShiftDown(), event.isControlDown());
                event.consume();
            }
        });

        // Handle mouse clicks for selection
        table.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY && table.getEditingCell() == null) {
                TablePosition<RowData, ?> pos = table.getFocusModel().getFocusedCell();
                if (pos != null && pos.getRow() >= 0 && pos.getColumn() >= 0) {
                    updateSelection(pos.getRow(), pos.getColumn(), event.isShiftDown(), event.isControlDown());
                }
                event.consume();
            }
        });

        data.addListener((ListChangeListener<RowData>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (RowData r : c.getAddedSubList()) attachReactiveBehavior(r);
                }
            }
            scheduleSaveAndRecalc();
            // Restore selection after data change
            if (!data.isEmpty()) {
                Platform.runLater(() -> {
                    if (table.getSelectionModel().getSelectedCells().isEmpty()) {
                        updateSelection(0, 0, false, false);
                    }
                });
            }
        });

        stage.setScene(scene);
        stage.setTitle("😎 باركود "+WASHINGController.seluserr);
        stage.setMaximized(true);
        stage.show();
    }

    private void scrollIfNeeded(int row, int col) {
        if (row < 0 || row >= data.size() || col < 0 || col >= table.getColumns().size()) return;

        // Estimate visible rows and columns
        double rowHeight = 24.0; // Approximate row height, adjust based on your CSS
        double colWidth = 100.0; // Approximate column width, adjust based on your CSS
        double viewportHeight = table.getHeight() - table.getFixedCellSize();
        double viewportWidth = table.getWidth();
        int visibleRows = (int) (viewportHeight / rowHeight);
        int visibleCols = (int) (viewportWidth / colWidth);

        // Get current scroll position
        int firstVisibleRow = Math.max(0, table.getItems().indexOf(table.getItems().get(0)));
        int firstVisibleCol = table.getColumns().indexOf(table.getVisibleLeafColumn(0));

        // Scroll only if the target cell is outside the visible viewport
        if (row < firstVisibleRow || row >= firstVisibleRow + visibleRows) {
            table.scrollTo(Math.max(0, row - visibleRows / 2));
        }
        if (col < firstVisibleCol || col >= firstVisibleCol + visibleCols) {
            table.scrollToColumnIndex(Math.max(0, col - visibleCols / 2));
        }
    }

    private void updateSelection(int row, int col, boolean shiftDown, boolean ctrlDown) {
        if (row < 0 || row >= data.size() || col < 0 || col >= table.getColumns().size()) return;

        if (shiftDown && anchorCell != null) {
            // Range selection
            table.getSelectionModel().clearSelection();
            int startRow = Math.min(row, anchorCell.getRow());
            int endRow = Math.max(row, anchorCell.getRow());
            int startCol = Math.min(col, anchorCell.getColumn());
            int endCol = Math.max(col, anchorCell.getColumn());

            for (int r = startRow; r <= endRow; r++) {
                for (int c = startCol; c <= endCol; c++) {
                    table.getSelectionModel().select(r, table.getColumns().get(c));
                }
            }
        } else if (ctrlDown) {
            // Toggle selection of the cell
            if (table.getSelectionModel().isSelected(row, table.getColumns().get(col))) {
                table.getSelectionModel().clearSelection(row, table.getColumns().get(col));
            } else {
                table.getSelectionModel().select(row, table.getColumns().get(col));
            }
            anchorCell = new TablePosition<>(table, row, table.getColumns().get(col));
        } else {
            // Single cell selection
            table.getSelectionModel().clearAndSelect(row, table.getColumns().get(col));
            anchorCell = new TablePosition<>(table, row, table.getColumns().get(col));
        }

        table.getFocusModel().focus(row, table.getColumns().get(col));
        table.requestFocus();
        scrollIfNeeded(row, col);
    }

    private JFXButton btn(String text) {
        JFXButton b = new JFXButton(text);
        b.setButtonType(JFXButton.ButtonType.RAISED);
        b.setStyle("-fx-background-color:transparent;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
        return b;
    }

    private void styleDialog(DialogPane dialogPane) {
        try {
            BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
            String theme = bis.readLine();
            bis.close();
            dialogPane.getStylesheets().add(getClass().getResource(theme).toExternalForm());
        } catch (Exception ignored) {}
    }

    private void handleKeyShortcuts(KeyEvent e) {
        if (e.isControlDown() && e.getCode() == KeyCode.C) {
            e.consume();
            copyToClipboard();
        } else if (e.isControlDown() && e.getCode() == KeyCode.V) {
            e.consume();
            pasteFromClipboard();
        } else if (e.isControlDown() && e.getCode() == KeyCode.D) {
            e.consume();
            fillDownFromFocusedCell();
        } else if (e.getCode() == KeyCode.DELETE) {
            e.consume();
            clearSelectedCells();
        }
    }

    private void copyToClipboard() {
        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
        if (selectedCells == null || selectedCells.isEmpty()) return;

        // Find the bounds of the selected range
        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;

        for (TablePosition pos : selectedCells) {
            minRow = Math.min(minRow, pos.getRow());
            maxRow = Math.max(maxRow, pos.getRow());
            minCol = Math.min(minCol, pos.getColumn());
            maxCol = Math.max(maxCol, pos.getColumn());
        }

        // Build the clipboard content
        StringBuilder clipboardContent = new StringBuilder();
        for (int r = minRow; r <= maxRow; r++) {
            if (r >= data.size()) break;
            for (int c = minCol; c <= maxCol; c++) {
                if (c >= COLUMNS.length) break;
                String value = data.get(r).getProperty(COLUMNS[c]).get();
                clipboardContent.append(value == null ? "" : value);
                if (c < maxCol) clipboardContent.append("\t");
            }
            if (r < maxRow) clipboardContent.append("\n");
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(clipboardContent.toString());
        clipboard.setContent(content);
    }

    private void clearSelectedCells() {
        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
        if (selectedCells == null || selectedCells.isEmpty()) return;

        boolean anyChange = false;
        for (TablePosition tp : selectedCells) {
            int rowIndex = tp.getRow();
            int colIndex = tp.getColumn();
            if (rowIndex >= 0 && rowIndex < data.size() && colIndex >= 0 && colIndex < COLUMNS.length) {
                RowData row = data.get(rowIndex);
                String colName = COLUMNS[colIndex];
                applyAndCascade(row, colName, "", false);
                anyChange = true;
            }
        }

        if (anyChange) {
            scheduleSaveAndRecalc();
        }
        // Restore selection
        Platform.runLater(() -> {
            table.getSelectionModel().clearSelection();
            if (!selectedCells.isEmpty()) {
                TablePosition tp = selectedCells.get(0);
                updateSelection(tp.getRow(), tp.getColumn(), false, false);
            }
        });
    }

    private void scheduleSaveAndRecalc() {
        if (pendingSave != null && !pendingSave.isDone()) {
            pendingSave.cancel(false);
        }
        pendingSave = executor.schedule(() -> {
            Platform.runLater(() -> {
                recalcAllFormulas();
                saveData();
                table.refresh();
            });
        }, 500, TimeUnit.MILLISECONDS);
    }

    private void pasteFromClipboard() {
        final TablePosition<RowData, ?> focus = table.getFocusModel().getFocusedCell();
        if (focus == null || focus.getColumn() < 0) return;

        Clipboard cb = Clipboard.getSystemClipboard();
        if (cb == null || !cb.hasString()) return;

        String raw = cb.getString();
        if (raw == null || raw.isEmpty()) return;

        String[] lines = raw.replace("\r", "").split("\n");
        int baseRow = focus.getRow();
        int baseCol = focus.getColumn();

        // Ensure enough rows exist
        while (baseRow + lines.length > data.size()) {
            RowData nr = new RowData();
            attachReactiveBehavior(nr);
            data.add(nr);
        }

        // Paste the data maintaining the copied structure
        for (int r = 0; r < lines.length; r++) {
            String[] parts = lines[r].split("\t", -1);
            int targetRow = baseRow + r;
            if (targetRow >= data.size()) break;
            RowData row = data.get(targetRow);

            for (int c = 0; c < parts.length; c++) {
                int targetCol = baseCol + c;
                if (targetCol >= COLUMNS.length) break;
                String colName = COLUMNS[targetCol];
                String value = parts[c] == null ? "" : parts[c].trim();
                applyAndCascade(row, colName, value, false);
            }
        }

        scheduleSaveAndRecalc();
        // Re-select the pasted range
        Platform.runLater(() -> {
            table.getSelectionModel().clearSelection();
            int pastedRows = Math.min(lines.length, data.size() - baseRow);
            int pastedCols = lines.length > 0 ? Math.min(lines[0].split("\t", -1).length, COLUMNS.length - baseCol) : 0;
            for (int r = baseRow; r < baseRow + pastedRows && r < data.size(); r++) {
                for (int c = baseCol; c < baseCol + pastedCols && c < table.getColumns().size(); c++) {
                    table.getSelectionModel().select(r, table.getColumns().get(c));
                }
            }
            table.getFocusModel().focus(baseRow, table.getColumns().get(baseCol));
            table.requestFocus();
            scrollIfNeeded(baseRow, baseCol);
        });
    }

    private void fillDownFromFocusedCell() {
        TablePosition<RowData, ?> focus = table.getFocusModel().getFocusedCell();
        if (focus == null || focus.getColumn() < 0 || focus.getRow() < 0) return;

        int focusCol = focus.getColumn();
        String focusColName = COLUMNS[focusCol];
        Object rawVal = table.getColumns().get(focusCol).getCellData(focus.getRow());
        String value = rawVal == null ? "" : rawVal.toString();

        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
        if (selectedCells != null && !selectedCells.isEmpty()) {
            for (TablePosition tp : selectedCells) {
                int r = tp.getRow();
                int c = tp.getColumn();
                if (c != focusCol) continue;
                if (r >= 0 && r < data.size()) {
                    RowData row = data.get(r);
                    applyAndCascade(row, focusColName, value, false);
                }
            }
        } else {
            ObservableList<Integer> selectedRows = table.getSelectionModel().getSelectedIndices();
            for (Integer r : selectedRows) {
                if (r >= 0 && r < data.size()) {
                    RowData row = data.get(r);
                    applyAndCascade(row, focusColName, value, false);
                }
            }
        }

        scheduleSaveAndRecalc();
        // Restore selection
        Platform.runLater(() -> {
            updateSelection(focus.getRow(), focusCol, false, false);
        });
    }

    private void applyAndCascade(RowData row, String colName, String newVal, boolean recalcNow) {
        if (newVal == null) newVal = "";
        row.setProperty(colName, newVal.trim());

        if ("code".equals(colName)) {
            String name = getNameByCode(row.getProperty("code").get());
            row.setProperty("name", name);
        }
    }

    private void attachReactiveBehavior(RowData row) {
        row.getProperty("code").addListener((o, oldV, newV) -> {
            String name = getNameByCode(newV == null ? "" : newV.trim());
            row.setProperty("name", name);
            scheduleSaveAndRecalc();
        });
    }

    private void recalcAllFormulas() {
        for (RowData r : data) {
            String date = r.getProperty("date").get();
            String code = r.getProperty("code").get();
        }
    }

    private static String z(String s) {
        return s == null ? "" : s.trim();
    }

    private void exportToExcel() {
        // Implementation remains unchanged
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "");
        alert.setTitle("Fatal Error");
        alert.setContentText(msg);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinSize(600, 50);
        styleDialog(dialogPane);
        alert.showAndWait();
    }

    private void loadData() {
        data.clear();
        try (Connection conn = connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM BarCode_P_" + WASHINGController.seluserr)) {
            while (rs.next()) {
                RowData row = new RowData();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.setProperty(rs.getMetaData().getColumnName(i),
                            rs.getString(i) == null ? "" : rs.getString(i));
                }
                attachReactiveBehavior(row);
                data.add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    // =====================
// DATE PARSING HELPERS
// =====================
private static final String[] DATE_FORMATS = {
    "yyyy-MM-dd", "dd/MM/yyyy", "d/M/yyyy", "d-M-yy", "MM/dd/yyyy", "dd-MM-yyyy"
};

private LocalDate parseDate(String dateStr) {
    if (dateStr == null || dateStr.trim().isEmpty()) return null;
    String cleaned = dateStr.trim();
    for (String fmt : DATE_FORMATS) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern(fmt);
            return LocalDate.parse(cleaned, f);
        } catch (Exception ignored) {}
    }
    // try ISO fallback
    try {
        return LocalDate.parse(cleaned);
    } catch (Exception ignored) {}
    return null; // could not parse
}

    
//  private void saveData() {
//    try (Connection conn = connect()) {
//        conn.setAutoCommit(false);
//
//        // Make sure we clear both tables so Bonus rows line up exactly with BarCode_P_ rows
//        try (Statement st = conn.createStatement()) {
//            st.execute("DELETE FROM BarCode_P_" + WASHINGController.seluserr);
//            st.execute("DELETE FROM Bonus_" + WASHINGController.seluserr);
//        }
//
//        // Full BarCode insert SQL (all columns)
//        final String barcodeSql = "INSERT INTO BarCode_P_" + WASHINGController.seluserr +
//                " (date,line,shift,code,name,machine_number,style,po,name_wash,process,quantity,start_time,end_time) " +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//        // NOTE: match these to the actual column names in your Bonus_ table.
//        // If Bonus table column is named "process" in DB, change "washing_process" -> "process" below.
//        final String[] BONUS_COLUMNS = {"date", "shift", "code", "name", "machine_number","name_wash", "process", "start_time", "end_time"};
//
//        final String bonusSql = "INSERT INTO Bonus_" + WASHINGController.seluserr +
//                " (date,shift,code,name,machine_number,name_wash,process,start_time,end_time) VALUES (?,?,?,?,?,?,?,?,?)";
//
//        // build a name->index map from your COLUMNS array for fast lookup
//        java.util.Map<String, Integer> colIndex = new java.util.HashMap<>();
//        for (int i = 0; i < COLUMNS.length; i++) colIndex.put(COLUMNS[i], i);
//
//        try (PreparedStatement psBarcode = conn.prepareStatement(barcodeSql);
//             PreparedStatement psBonus = conn.prepareStatement(bonusSql)) {
//
//            for (RowData row : data) {
//                // populate BarCode_P_ statement
//                for (int i = 0; i < row.values.length; i++) {
//                    // PreparedStatement index is 1-based
//                    psBarcode.setString(i + 1, row.values[i] == null ? "" : row.values[i]);
//                }
//                psBarcode.addBatch();
//
//                // populate Bonus statement: build explicit values array so every parameter is set
//                String[] bonusVals = new String[BONUS_COLUMNS.length];
//                for (int i = 0; i < BONUS_COLUMNS.length; i++) {
//                    Integer idx = colIndex.get(BONUS_COLUMNS[i]);
//                    bonusVals[i] = (idx == null || idx < 0 || idx >= row.values.length) ? "" : (row.values[idx] == null ? "" : row.values[idx]);
//                }
//                for (int i = 0; i < bonusVals.length; i++) {
//                    psBonus.setString(i + 1, bonusVals[i]);
//                }
//                psBonus.addBatch();
//            }
//
//            psBarcode.executeBatch();
//            psBonus.executeBatch();
//
//            conn.commit();
//        }
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    }
//}

    

    private void saveData() {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            conn.createStatement().execute("DELETE FROM BarCode_P_" + WASHINGController.seluserr);
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO BarCode_P_" + WASHINGController.seluserr + " (date,line,shift,code,name,machine_number," +
                            "style,po,name_wash,process,quantity," +
                            "start_time,end_time) VALUES (?,?,?, ?,?,?,?,?,?,?,?, ?,?)"
            );
            for (RowData row : data) {
                for (int i = 0; i < row.values.length; i++) {
                    ps.setString(i + 1, row.values[i]);
                }
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getNameByCode(String code) {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement("SELECT name FROM Workers WHERE code=?")) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class RowData {
        private final SimpleStringProperty[] props;
        private final String[] colNames = {
                "date", "line", "shift", "code", "name", "machine_number",
                "style", "po", "name_wash", "process",
                "quantity", "start_time", "end_time"
        };
        private final String[] values;

        public RowData() {
            props = new SimpleStringProperty[colNames.length];
            values = new String[colNames.length];
            for (int i = 0; i < colNames.length; i++) {
                props[i] = new SimpleStringProperty("");
                values[i] = "";
                final int idx = i;
                props[i].addListener((obs, old, val) -> values[idx] = val);
            }
        }

        public SimpleStringProperty getProperty(String name) {
            for (int i = 0; i < colNames.length; i++) {
                if (colNames[i].equals(name)) return props[i];
            }
            return new SimpleStringProperty("");
        }

        public void setProperty(String name, String value) {
            for (int i = 0; i < colNames.length; i++) {
                if (colNames[i].equals(name)) props[i].set(value == null ? "" : value);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}