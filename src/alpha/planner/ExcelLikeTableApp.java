

/////////////////////////////////////////////////////First Version//////////////////////////////////////////////////////////////


//package alpha.planner;
//
//import com.jfoenix.controls.JFXButton;
//import java.awt.Desktop;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ListChangeListener;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import java.sql.*;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import javafx.stage.FileChooser;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.CellReference;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.controlsfx.control.table.TableFilter;
//
//public class ExcelLikeTableApp extends Application {
//
//    private TableView<RowData> table = new TableView<>();
//    
//    private ObservableList<RowData> data = FXCollections.observableArrayList();
//    JFXButton loadBtn;
//
//    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
//    static String vvaall;
//
//    private Connection connect() throws Exception {
//        String filePath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
//        List<String> lines = Files.readAllLines(Paths.get(filePath));
//        for (String line : lines) {
//            if (line.startsWith("DB=")) {
//                vvaall = line.split("=", 2)[1];
//                break;
//            }
//        }
//        Class.forName("org.sqlite.JDBC");
//        return DriverManager.getConnection("jdbc:sqlite:"+vvaall);
//    }
//    
//    private String parseMonthNumber(String dateStr) {
//    if (dateStr == null || dateStr.isEmpty()) return "";
//    List<DateTimeFormatter> formatters = Arrays.asList(
//            DateTimeFormatter.ofPattern("dd-MM"),
//            DateTimeFormatter.ofPattern("dd-M"),
//            DateTimeFormatter.ofPattern("d-M"),
//            DateTimeFormatter.ofPattern("dd-MMM"),
//            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
//            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
//            DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    );
//    for (DateTimeFormatter fmt : formatters) {
//        try {
//            LocalDate d = LocalDate.parse(dateStr, fmt.withLocale(Locale.ENGLISH));
//            return String.valueOf(d.getMonthValue());
//        } catch (Exception ignored) {}
//    }
//    return "";
//}
//
//
//    @Override
//    public void start(Stage stage) {
//        table.setEditable(true);
//        
//        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // multi select
//
//        String[] columns = {"date","month","shift","code","name","name_wash",
//                "machine_number","time_machine","process","start_time",
//                "end_time","hour","minute","recipe_time","fark",
//                "percentage","total_time_recipe","total_time","percentage_day"};
//
//        for (String colName : columns) {
//    TableColumn<RowData, String> col = new TableColumn<>(colName);
//    col.setCellValueFactory(param -> param.getValue().getProperty(colName));
//    col.setCellFactory(TextFieldTableCell.forTableColumn());
//
//    col.setOnEditCommit(event -> {
//        String newVal = event.getNewValue() == null ? "" : event.getNewValue().trim();
//        RowData row = event.getRowValue();
//        row.setProperty(colName, newVal);
//
//        // Auto-fill
//        if (colName.equals("code")) {
//            String name = getNameByCode(newVal);
//            row.setProperty("name", name);
//        }
//        if (colName.equals("name_wash")) {
//            String recipeTime = getRecipeTimeByWash(newVal);
//            row.setProperty("recipe_time", recipeTime);
//        }
//
//        // Auto-fill month from date
//        if (colName.equals("date")) {
//            String monthVal = parseMonthNumber(newVal);
//            if (!monthVal.isEmpty()) {
//                row.setProperty("month", monthVal);
//                System.out.println(monthVal);
//            }
//        }
//
//        // Recalc for key fields
//        if (colName.equals("start_time") || colName.equals("end_time")
//                || colName.equals("recipe_time") || colName.equals("date") || colName.equals("code")
//                || colName.equals("name_wash")) {
//            calculateRow(row);
//            recalcAllFormulas();
//        }
//        saveData();
//    });
//
//    table.getColumns().add(col);
//    
//}
//
//        
//
//        table.setItems(data);
//        new TableFilter<>(table);
//
//        // === Buttons ===
//        loadBtn = new JFXButton("🔌 Load");
//        loadBtn.setButtonType(JFXButton.ButtonType.RAISED);
//        loadBtn.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        loadBtn.setOnAction(e -> {
//            loadData();
//            recalcAllFormulas();
//        });
//
//        JFXButton saveBtn = new JFXButton("✨ Save");
//        saveBtn.setButtonType(JFXButton.ButtonType.RAISED);
//        saveBtn.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        saveBtn.setOnAction(e -> saveData());
//
//        JFXButton addRowBtn = new JFXButton("⚡ Add Row");
//        addRowBtn.setButtonType(JFXButton.ButtonType.RAISED);
//        addRowBtn.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        addRowBtn.setOnAction(e -> {
//            RowData r = new RowData();
//            attachReactiveBehavior(r);
//            data.add(r);
//            saveData();
//        });
//
//        JFXButton addManyRowsBtn = new JFXButton("❤ Add Many Rows");
//        addManyRowsBtn.setButtonType(JFXButton.ButtonType.RAISED);
//        addManyRowsBtn.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        addManyRowsBtn.setOnAction(e -> {
//            TextInputDialog dialog = new TextInputDialog("5");
//            dialog.setHeaderText("Add Many Rows");
//            DialogPane dialogPane = dialog.getDialogPane();
//        dialogPane.setMinSize(600, 50);
//        try {
//            BufferedReader bis=new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme=bis.readLine(); bis.close();
//            dialogPane.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//            dialog.setContentText("Enter number of rows to add:");
//            
//            dialog.showAndWait().ifPresent(numStr -> {
//                try {
//                    int num = Integer.parseInt(numStr);
//                    for (int i = 0; i < num; i++) {
//                        RowData r = new RowData();
//                        attachReactiveBehavior(r);
//                        data.add(r);
//                    }
//                    saveData();
//                } catch (NumberFormatException ex) {
//                    showError("Invalid Number");
//                }
//            });
//        });
//
//        JFXButton deleteBtn = new JFXButton("✖ Delete Row(s)");
//        deleteBtn.setButtonType(JFXButton.ButtonType.RAISED);
//        deleteBtn.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        deleteBtn.setOnAction(e -> {
//            ObservableList<RowData> selected = table.getSelectionModel().getSelectedItems();
//            if (selected != null && !selected.isEmpty()) {
//                data.removeAll(selected);
//                recalcAllFormulas();
//                saveData();
//            }
//        });
//
//        JFXButton exportBtn = new JFXButton("🛒 Export");
//        exportBtn.setButtonType(JFXButton.ButtonType.RAISED);
//        exportBtn.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        exportBtn.setOnAction(e -> exportToExcel());
//
//        ToolBar toolbar = new ToolBar(loadBtn, saveBtn, addRowBtn, addManyRowsBtn, deleteBtn, exportBtn);
//
//        BorderPane root = new BorderPane();
//        root.setTop(toolbar);
//        root.setCenter(table);
//
//        Scene scene = new Scene(root, 1200, 600);
//
//        // Theme load
//        try {
//            BufferedReader bis=new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme=bis.readLine(); bis.close();
//            scene.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//
//        // Auto-save on key
//        scene.setOnKeyReleased(e -> {
//            if (!(e.getCode() == KeyCode.CONTROL || e.getCode() == KeyCode.SHIFT || e.getCode() == KeyCode.ALT)) {
//                saveData();
//            }
//        });
//
//        // Keep totals coherent on list changes (add/remove)
//        data.addListener((ListChangeListener<RowData>) c -> {
//            while (c.next()) {
//                if (c.wasAdded()) {
//                    for (RowData r : c.getAddedSubList()) attachReactiveBehavior(r);
//                }
//            }
//            recalcAllFormulas();
//        });
//
//        stage.setScene(scene);
//        stage.setTitle("😎 BONUS PERSONAL LAUNDRY");
//        stage.setMaximized(true);
//        stage.show();
//    }
//
//    /** Attach Excel-like reactive behavior */
//    private void attachReactiveBehavior(RowData row) {
//        // Autofill name when code changes
//        row.getProperty("code").addListener((o, oldV, newV) -> {
//            String name = getNameByCode(newV == null ? "" : newV.trim());
//            row.setProperty("name", name);
//            recalcAllFormulas();
//            saveData();
//        });
//        // Autofill recipe_time when name_wash changes
//        row.getProperty("name_wash").addListener((o, oldV, newV) -> {
//            String rt = getRecipeTimeByWash(newV == null ? "" : newV.trim());
//            row.setProperty("recipe_time", rt);
//            calculateRow(row);
//            recalcAllFormulas();
//            saveData();
//        });
//        // Recalc on key inputs
//        String[] drivers = {"start_time","end_time","recipe_time","date","code"};
//        for (String k : drivers) {
//            row.getProperty(k).addListener((o, oldV, newV) -> {
//                calculateRow(row);
//                recalcAllFormulas();
//                saveData();
//            });
//        }
//    }
//
//    /** Calculate row-level values */
//    private void calculateRow(RowData row) {
//        String start = row.getProperty("start_time").get();
//        String end = row.getProperty("end_time").get();
//        String recipeStr = row.getProperty("recipe_time").get();
//
//        if (start.isEmpty() || end.isEmpty()) return;
//
//        try {
//            LocalTime startTime = LocalTime.parse(start.toUpperCase(), TIME_FORMAT);
//            LocalTime endTime = LocalTime.parse(end.toUpperCase(), TIME_FORMAT);
//
//            if (endTime.isBefore(startTime)) endTime = endTime.plusHours(24);
//
//            Duration d = Duration.between(startTime, endTime);
//            long minutes = d.toMinutes();
//            row.setProperty("hour", String.format("%d:%02d", minutes/60, minutes%60));
//            row.setProperty("minute", String.valueOf(minutes));
//
//            int recipe = 0;
//            try { recipe = Integer.parseInt(recipeStr); } catch (NumberFormatException ignore) {}
//            long fark = Math.max(0, minutes - recipe);
//            row.setProperty("fark", String.valueOf(fark));
//
//            double perc = (minutes > 0 && recipe > 0) ? (100.0 * recipe / minutes) : 0;
//            row.setProperty("percentage", String.format("%.1f", perc));
//
//        } catch (DateTimeParseException e) {
//            showError("Invalid time format! Please use format like 8:30 AM or 12:15 PM");
//            row.setProperty("start_time", "");
//            row.setProperty("end_time", "");
//        }
//    }
//
//    /** SUMIFS-like formulas across rows */
//    private void recalcAllFormulas() {
//        for (RowData r : data) {
//            String date = r.getProperty("date").get();
//            String code = r.getProperty("code").get();
//
//            if (date.isEmpty() || code.isEmpty()) {
//                r.setProperty("total_time_recipe", "");
//                r.setProperty("total_time", "");
//                r.setProperty("percentage_day", "");
//                continue;
//            }
//
//            int sumRecipe = 0, sumMinutes = 0;
//            double sumPercentage = 0;
//
//            for (RowData other : data) {
//                if (date.equals(other.getProperty("date").get()) && code.equals(other.getProperty("code").get())) {
//                    try { sumRecipe += Integer.parseInt(other.getProperty("recipe_time").get()); } catch (Exception ignore) {}
//                    try { sumMinutes += Integer.parseInt(other.getProperty("minute").get()); } catch (Exception ignore) {}
//                    try { sumPercentage += Double.parseDouble(other.getProperty("percentage").get()); } catch (Exception ignore) {}
//                }
//            }
//
//            r.setProperty("total_time_recipe", String.valueOf(sumRecipe));
//            r.setProperty("total_time", String.valueOf(sumMinutes));
//            r.setProperty("percentage_day", String.format("%.1f", sumPercentage));
//        }
//    }
//
//    private void exportToExcel() {
//        try {
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("BONUS_PERSONAL_LAUNDRY");
//
//            // Styles
//            DataFormat df = workbook.createDataFormat();
//            CellStyle timeHMM = workbook.createCellStyle();
//            timeHMM.setDataFormat(df.getFormat("h:mm")); // for "hour" column (as time from minutes/1440)
//            CellStyle pctStyle = workbook.createCellStyle();
//            pctStyle.setDataFormat(df.getFormat("0.0"));
//
//            // Header
//            Row header = sheet.createRow(0);
//            for (int j = 0; j < table.getColumns().size(); j++) {
//                org.apache.poi.ss.usermodel.Cell c = header.createCell(j);
//                c.setCellValue(table.getColumns().get(j).getText());
//            }
//
//            // Write rows: values for input columns, formulas for computed columns
//            // Col letters helper
//            String[] letters = new String[table.getColumns().size()];
//            for (int j = 0; j < letters.length; j++) letters[j] = CellReference.convertNumToColString(j);
//
//            // Index map (keep aligned with your columns array)
//            final int COL_DATE = 0;            // A
//            final int COL_MONTH = 1;           // B
//            final int COL_SHIFT = 2;           // C
//            final int COL_CODE = 3;            // D
//            final int COL_NAME = 4;            // E
//            final int COL_NAME_WASH = 5;       // F
//            final int COL_MACHINE_NUMBER = 6;  // G
//            final int COL_TIME_MACHINE = 7;    // H
//            final int COL_PROCESS = 8;         // I
//            final int COL_START_TIME = 9;      // J
//            final int COL_END_TIME = 10;       // K
//            final int COL_HOUR = 11;           // L (formula: =M2/1440, format h:mm)
//            final int COL_MINUTE = 12;         // M (formula: =MOD(TIMEVALUE(K2)-TIMEVALUE(J2),1)*1440)
//            final int COL_RECIPE_TIME = 13;    // N (value)
//            final int COL_FARK = 14;           // O (formula: =MAX(0,M2-N2))
//            final int COL_PERCENTAGE = 15;     // P (formula: =IF(AND(M2>0,N2>0),N2/M2*100,0))
//            final int COL_TOTAL_TIME_RECIPE = 16; // Q (SUMIFS N by A & D)
//            final int COL_TOTAL_TIME = 17;        // R (SUMIFS M by A & D)
//            final int COL_PERCENTAGE_DAY = 18;    // S (SUMIFS P by A & D)
//
//            for (int i = 0; i < table.getItems().size(); i++) {
//                RowData rd = table.getItems().get(i);
//                Row row = sheet.createRow(i + 1);
//                int excelRow = i + 2; // Excel rows are 1-based and header is row 1
//
//                for (int j = 0; j < table.getColumns().size(); j++) {
//                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(j);
//
//                    // Input/value columns written as plain values
//                    boolean isComputed =
//                            j == COL_HOUR || j == COL_MINUTE || j == COL_FARK || j == COL_PERCENTAGE
//                                    || j == COL_TOTAL_TIME_RECIPE || j == COL_TOTAL_TIME || j == COL_PERCENTAGE_DAY;
//
//                    if (!isComputed) {
//                        String val = table.getColumns().get(j).getCellData(i) == null ? "" :
//                                table.getColumns().get(j).getCellData(i).toString();
//                        cell.setCellValue(val);
//                        continue;
//                    }
//
//                    // Build formulas
//                    String A = letters[COL_DATE];
//                    String D = letters[COL_CODE];
//                    String J = letters[COL_START_TIME];
//                    String K = letters[COL_END_TIME];
//                    String L = letters[COL_HOUR];
//                    String M = letters[COL_MINUTE];
//                    String N = letters[COL_RECIPE_TIME];
//                    String O = letters[COL_FARK];
//                    String P = letters[COL_PERCENTAGE];
//                    String Q = letters[COL_TOTAL_TIME_RECIPE];
//                    String R = letters[COL_TOTAL_TIME];
//                    String S = letters[COL_PERCENTAGE_DAY];
//
//                    switch (j) {
//                        case COL_MINUTE: {
//                            // =MOD(TIMEVALUE(K2)-TIMEVALUE(J2),1)*1440
//                            String f = String.format("MOD(TIMEVALUE(%s%d)-TIMEVALUE(%s%d),1)*1440", K, excelRow, J, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_HOUR: {
//                            // =M2/1440   (displayed as h:mm)
//                            String f = String.format("%s%d/1440", M, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(timeHMM);
//                            break;
//                        }
//                        case COL_FARK: {
//                            // =MAX(0,M2-N2)
//                            String f = String.format("MAX(0,%s%d-%s%d)", M, excelRow, N, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_PERCENTAGE: {
//                            // =IF(AND(M2>0,N2>0),N2/M2*100,0)
//                            String f = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,0)",
//                                    M, excelRow, N, excelRow, N, excelRow, M, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(pctStyle);
//                            break;
//                        }
//                        case COL_TOTAL_TIME_RECIPE: {
//                            // =SUMIFS(N:N,A:A,A2,D:D,D2)
//                            String f = String.format("SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d)",
//                                    N, N, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_TOTAL_TIME: {
//                            // =SUMIFS(M:M,A:A,A2,D:D,D2)
//                            String f = String.format("SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d)",
//                                    M, M, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_PERCENTAGE_DAY: {
//                            // =SUMIFS(P:P,A:A,A2,D:D,D2)
//                            String f = String.format("SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d)",
//                                    P, P, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(pctStyle);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            // Freeze header row & add filter
//            sheet.createFreezePane(0, 1);
//            sheet.setAutoFilter(new CellRangeAddress(0, table.getItems().size(), 0, table.getColumns().size()-1));
//
//            // Autosize
//            for (int j = 0; j < table.getColumns().size(); j++) sheet.autoSizeColumn(j);
//
//            // Save file
//            FileChooser dialog = new FileChooser();
//            dialog.setInitialDirectory(new File(System.getProperty("user.home")+"\\Desktop"));
//            dialog.setInitialFileName("BONUS_PERSONAL_LAUNDRY");
//            dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
//            File dialogResult = dialog.showSaveDialog(null);
//            if (dialogResult == null) return;
//
//            try (FileOutputStream out = new FileOutputStream(dialogResult)) {
//                workbook.write(out);
//            }
//            workbook.close();
//            Desktop.getDesktop().open(dialogResult);
//        } catch (Exception ff) {
//            ff.printStackTrace();
//        }
//    }
//
//    private void showError(String msg) {
//        Alert alert= new Alert(Alert.AlertType.ERROR,"");
//        alert.setTitle("Fatal Error");
//        alert.setContentText(msg);
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.setMinSize(600, 50);
//        try {
//            BufferedReader bis=new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme=bis.readLine(); bis.close();
//            dialogPane.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//        alert.showAndWait();
//    }
//
//    private void loadData() {
//        data.clear();
//        try (Connection conn = connect();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery("SELECT * FROM Bonus")) {
//            while (rs.next()) {
//                RowData row = new RowData();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    row.setProperty(rs.getMetaData().getColumnName(i),
//                            rs.getString(i) == null ? "" : rs.getString(i));
//                }
//                attachReactiveBehavior(row);
//                calculateRow(row);
//                data.add(row);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void saveData() {
//        try (Connection conn = connect()) {
//            conn.createStatement().execute("DELETE FROM Bonus");
//            PreparedStatement ps = conn.prepareStatement(
//                    "INSERT INTO Bonus (date,month,shift,code,name,name_wash," +
//                            "machine_number,time_machine,process,start_time,end_time," +
//                            "hour,minute,recipe_time,fark,percentage," +
//                            "total_time_recipe,total_time,percentage_day) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
//            );
//            for (RowData row : data) {
//                for (int i = 0; i < row.values.length; i++) {
//                    ps.setString(i + 1, row.values[i]);
//                }
//                ps.addBatch();
//            }
//            ps.executeBatch();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private String getNameByCode(String code) {
//        try (Connection conn = connect();
//             PreparedStatement ps = conn.prepareStatement("SELECT name FROM Workers WHERE code=?")) {
//            ps.setString(1, code);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return rs.getString("name");
//        } catch (Exception e) { e.printStackTrace(); }
//        return "";
//    }
//
//    private String getRecipeTimeByWash(String wash) {
//        try (Connection conn = connect();
//             PreparedStatement ps = conn.prepareStatement("SELECT recipe_time FROM Times WHERE name_wash=?")) {
//            ps.setString(1, wash);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return rs.getString("recipe_time");
//        } catch (Exception e) { e.printStackTrace(); }
//        return "";
//    }
//
//    // === RowData ===
//    public static class RowData {
//        private final SimpleStringProperty[] props;
//        private final String[] colNames = {"date","month","shift","code","name","name_wash",
//                "machine_number","time_machine","process","start_time",
//                "end_time","hour","minute","recipe_time","fark",
//                "percentage","total_time_recipe","total_time","percentage_day"};
//        private final String[] values;
//
//        public RowData() {
//            props = new SimpleStringProperty[colNames.length];
//            values = new String[colNames.length];
//            for (int i = 0; i < colNames.length; i++) {
//                props[i] = new SimpleStringProperty("");
//                values[i] = "";
//                final int idx = i;
//                props[i].addListener((obs, old, val) -> values[idx] = val);
//            }
//        }
//
//        public SimpleStringProperty getProperty(String name) {
//            for (int i = 0; i < colNames.length; i++) {
//                if (colNames[i].equals(name)) return props[i];
//            }
//            return new SimpleStringProperty("");
//        }
//
//        public void setProperty(String name, String value) {
//            for (int i = 0; i < colNames.length; i++) {
//                if (colNames[i].equals(name)) props[i].set(value == null ? "" : value);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}


/////////////////////////////////////////////////////Second Version//////////////////////////////////////////////////////////////



//
//
//package alpha.planner;
//
//import com.jfoenix.controls.JFXButton;
//import java.awt.Desktop;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ListChangeListener;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.input.Clipboard;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import javafx.stage.FileChooser;
//import java.sql.*;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.CellReference;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.controlsfx.control.table.TableFilter;
//
//public class ExcelLikeTableApp extends Application {
//
//    private final TableView<RowData> table = new TableView<>();
//    private final ObservableList<RowData> data = FXCollections.observableArrayList();
//    private JFXButton loadBtn;
//    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//    private ScheduledFuture<?> pendingSave;
//
//    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
//    private static String vvaall, vvaalll;
//
//    private static final String[] COLUMNS = {
//            "date", "month", "shift", "code", "name", "name_wash",
//            "machine_number", "time_machine", "process", "start_time",
//            "end_time", "hour", "minute", "recipe_time", "fark",
//            "percentage", "total_time_recipe", "total_time", "percentage_day"
//    };
//
//    @Override
//    public void stop() {
//        executor.shutdown();
//        try {
//            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
//                executor.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executor.shutdownNow();
//        }
//    }
//
//    private Connection connect() throws Exception {
//        String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
//        List<String> lines = Files.readAllLines(Paths.get(filePath));
//        for (String line : lines) {
//            if (line.startsWith("DB=")) {
//                vvaall = line.split("=", 2)[1];
//                break;
//            }
//        }
//        Class.forName("org.sqlite.JDBC");
//        return DriverManager.getConnection("jdbc:sqlite:" + vvaall);
//    }
//    
//    private Connection connect2() throws Exception {
//        String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
//        List<String> lines = Files.readAllLines(Paths.get(filePath));
//        for (String line : lines) {
//            if (line.startsWith("Recipe_DB=")) {
//                vvaalll = line.split("=", 2)[1];
//                break;
//            }
//        }
//        Class.forName("org.sqlite.JDBC");
//        return DriverManager.getConnection("jdbc:sqlite:" + vvaalll);
//    }
//
//    private String parseMonthNumber(String dateStr) {
//        if (dateStr == null || dateStr.trim().isEmpty()) return "";
//
//        List<DateTimeFormatter> formatters = Arrays.asList(
//                DateTimeFormatter.ofPattern("dd-MM"),
//                DateTimeFormatter.ofPattern("dd.MM"),
//                DateTimeFormatter.ofPattern("d-M"),
//                DateTimeFormatter.ofPattern("d.M"),
//                DateTimeFormatter.ofPattern("dd-MMM"),
//                DateTimeFormatter.ofPattern("d-MMM"),
//                DateTimeFormatter.ofPattern("dd-MMMM"),
//                DateTimeFormatter.ofPattern("d-MMMM"),
//                DateTimeFormatter.ofPattern("dd-MM-yy"),
//                DateTimeFormatter.ofPattern("dd.MM.yy"),
//                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
//                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
//                DateTimeFormatter.ofPattern("d-M-yy"),
//                DateTimeFormatter.ofPattern("d.M.yy"),
//                DateTimeFormatter.ofPattern("d-M-yyyy"),
//                DateTimeFormatter.ofPattern("d.M.yyyy"),
//                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
//                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
//                DateTimeFormatter.ofPattern("MMM-dd"),
//                DateTimeFormatter.ofPattern("MMMM-dd")
//        );
//
//        for (DateTimeFormatter fmt : formatters) {
//            try {
//                if (!dateStr.contains("-20") && !dateStr.contains(".20") && !dateStr.contains("/20")) {
//                    String currentYear = String.valueOf(LocalDate.now().getYear());
//                    String fullDateStr = dateStr + "-" + currentYear;
//                    List<DateTimeFormatter> fullFormatters = Arrays.asList(
//                            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
//                            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
//                            DateTimeFormatter.ofPattern("d-M-yyyy"),
//                            DateTimeFormatter.ofPattern("d.M.yyyy"),
//                            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
//                            DateTimeFormatter.ofPattern("d-MMM-yyyy"),
//                            DateTimeFormatter.ofPattern("dd-MMMM-yyyy"),
//                            DateTimeFormatter.ofPattern("d-MMMM-yyyy")
//                    );
//                    for (DateTimeFormatter fullFmt : fullFormatters) {
//                        try {
//                            LocalDate d = LocalDate.parse(fullDateStr, fullFmt.withLocale(Locale.ENGLISH));
//                            return String.format("%02d", d.getMonthValue());
//                        } catch (Exception ignored) {}
//                    }
//                }
//                LocalDate d = LocalDate.parse(dateStr, fmt.withLocale(Locale.ENGLISH));
//                return String.format("%02d", d.getMonthValue());
//            } catch (Exception ignored) {}
//        }
//        return "";
//    }
//
//    @Override
//    public void start(Stage stage) {
//        table.setEditable(true);
//        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        table.getSelectionModel().setCellSelectionEnabled(true);
//
//        for (String colName : COLUMNS) {
//            TableColumn<RowData, String> col = new TableColumn<>(colName);
//            col.setCellValueFactory(param -> param.getValue().getProperty(colName));
//            col.setCellFactory(TextFieldTableCell.forTableColumn());
//
//            col.setOnEditCommit(event -> {
//                String newVal = event.getNewValue() == null ? "" : event.getNewValue().trim();
//                RowData row = event.getRowValue();
//                applyAndCascade(row, colName, newVal, false);
//                scheduleSaveAndRecalc();
//            });
//
//            table.getColumns().add(col);
//        }
//
//        table.setItems(data);
//        new TableFilter<>(table);
//
//        loadBtn = btn("🔌 Load");
//        loadBtn.setOnAction(e -> {
//            loadData();
//            scheduleSaveAndRecalc();
//        });
//        Platform.runLater(loadBtn::fire);
//
//        JFXButton saveBtn = btn("✨ Save");
//        saveBtn.setOnAction(e -> scheduleSaveAndRecalc());
//
//        JFXButton addRowBtn = btn("⚡ Add Row");
//        addRowBtn.setOnAction(e -> {
//            RowData r = new RowData();
//            attachReactiveBehavior(r);
//            data.add(r);
//            scheduleSaveAndRecalc();
//        });
//
//        JFXButton addManyRowsBtn = btn("❤ Add Many Rows");
//        addManyRowsBtn.setOnAction(e -> {
//            TextInputDialog dialog = new TextInputDialog("5");
//            dialog.setHeaderText("Add Many Rows");
//            DialogPane dialogPane = dialog.getDialogPane();
//            dialogPane.setMinSize(600, 50);
//            styleDialog(dialogPane);
//            dialog.setContentText("Enter number of rows to add:");
//            dialog.showAndWait().ifPresent(numStr -> {
//                try {
//                    int num = Integer.parseInt(numStr);
//                    for (int i = 0; i < num; i++) {
//                        RowData r = new RowData();
//                        attachReactiveBehavior(r);
//                        data.add(r);
//                    }
//                    scheduleSaveAndRecalc();
//                } catch (NumberFormatException ex) {
//                    showError("Invalid Number");
//                }
//            });
//        });
//
//        JFXButton deleteBtn = btn("✖ Delete Row(s)");
//        deleteBtn.setOnAction(e -> {
//            ObservableList<RowData> selected = table.getSelectionModel().getSelectedItems();
//            if (selected != null && !selected.isEmpty()) {
//                data.removeAll(selected);
//                scheduleSaveAndRecalc();
//            }
//        });
//
//        JFXButton exportBtn = btn("🛒 Export");
//        exportBtn.setOnAction(e -> exportToExcel());
//
//        ToolBar toolbar = new ToolBar(loadBtn, saveBtn, addRowBtn, addManyRowsBtn, deleteBtn, exportBtn);
//
//        BorderPane root = new BorderPane();
//        root.setTop(toolbar);
//        root.setCenter(table);
//
//        Scene scene = new Scene(root, 1200, 600);
//        try {
//            BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme = bis.readLine();
//            bis.close();
//            scene.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//
//        table.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyShortcuts);
//
//        data.addListener((ListChangeListener<RowData>) c -> {
//            while (c.next()) {
//                if (c.wasAdded()) {
//                    for (RowData r : c.getAddedSubList()) attachReactiveBehavior(r);
//                }
//            }
//            scheduleSaveAndRecalc();
//        });
//
//        stage.setScene(scene);
//        stage.setTitle("😎 BONUS PERSONAL LAUNDRY");
//        stage.setMaximized(true);
//        stage.show();
//    }
//
//    private JFXButton btn(String text) {
//        JFXButton b = new JFXButton(text);
//        b.setButtonType(JFXButton.ButtonType.RAISED);
//        b.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        return b;
//    }
//
//    private void styleDialog(DialogPane dialogPane) {
//        try {
//            BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme = bis.readLine();
//            bis.close();
//            dialogPane.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//    }
//
//    private void handleKeyShortcuts(KeyEvent e) {
//        if (e.isControlDown() && e.getCode() == KeyCode.V) {
//            e.consume();
//            pasteFromClipboard();
//        } else if (e.isControlDown() && e.getCode() == KeyCode.D) {
//            e.consume();
//            fillDownFromFocusedCell();
//        } else if (e.getCode() == KeyCode.DELETE) {
//            e.consume();
//            clearSelectedCells();
//        }
//    }
//
//    private void clearSelectedCells() {
//        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
//        if (selectedCells == null || selectedCells.isEmpty()) return;
//
//        boolean anyChange = false;
//        for (TablePosition tp : selectedCells) {
//            int rowIndex = tp.getRow();
//            int colIndex = tp.getColumn();
//            if (rowIndex >= 0 && rowIndex < data.size() && colIndex >= 0 && colIndex < COLUMNS.length) {
//                RowData row = data.get(rowIndex);
//                String colName = COLUMNS[colIndex];
//                applyAndCascade(row, colName, "", false);
//                anyChange = true;
//            }
//        }
//
//        if (anyChange) {
//            scheduleSaveAndRecalc();
//        }
//    }
//
//    private void scheduleSaveAndRecalc() {
//        if (pendingSave != null && !pendingSave.isDone()) {
//            pendingSave.cancel(false);
//        }
//        pendingSave = executor.schedule(() -> {
//            Platform.runLater(() -> {
//                recalcAllFormulas();
//                saveData();
//                table.refresh();
//            });
//        }, 500, TimeUnit.MILLISECONDS);
//    }
//
//    private void pasteFromClipboard() {
//        final TablePosition<RowData, ?> focus = table.getFocusModel().getFocusedCell();
//        if (focus == null || focus.getColumn() < 0) return;
//
//        Clipboard cb = Clipboard.getSystemClipboard();
//        if (cb == null || !cb.hasString()) return;
//
//        String raw = cb.getString();
//        if (raw == null || raw.isEmpty()) return;
//
//        String[] lines = raw.replace("\r", "").split("\n");
//        int baseRow = focus.getRow();
//        int baseCol = focus.getColumn();
//
//        for (int r = 0; r < lines.length; r++) {
//            String[] parts = lines[r].split("\t", -1);
//            int targetRow = baseRow + r;
//
//            while (targetRow >= data.size()) {
//                RowData nr = new RowData();
//                attachReactiveBehavior(nr);
//                data.add(nr);
//            }
//            RowData row = data.get(targetRow);
//
//            for (int c = 0; c < parts.length; c++) {
//                int targetCol = baseCol + c;
//                if (targetCol >= COLUMNS.length) break;
//                String colName = COLUMNS[targetCol];
//                String value = parts[c] == null ? "" : parts[c].trim();
//                applyAndCascade(row, colName, value, false);
//            }
//        }
//
//        scheduleSaveAndRecalc();
//    }
//
//    private void fillDownFromFocusedCell() {
//        TablePosition<RowData, ?> focus = table.getFocusModel().getFocusedCell();
//        if (focus == null || focus.getColumn() < 0 || focus.getRow() < 0) return;
//
//        int focusCol = focus.getColumn();
//        String focusColName = COLUMNS[focusCol];
//        Object rawVal = table.getColumns().get(focusCol).getCellData(focus.getRow());
//        String value = rawVal == null ? "" : rawVal.toString();
//
//        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
//        if (selectedCells != null && !selectedCells.isEmpty()) {
//            for (TablePosition tp : selectedCells) {
//                int r = tp.getRow();
//                int c = tp.getColumn();
//                if (c != focusCol) continue;
//                if (r >= 0 && r < data.size()) {
//                    RowData row = data.get(r);
//                    applyAndCascade(row, focusColName, value, false);
//                }
//            }
//        } else {
//            ObservableList<Integer> selectedRows = table.getSelectionModel().getSelectedIndices();
//            for (Integer r : selectedRows) {
//                if (r >= 0 && r < data.size()) {
//                    RowData row = data.get(r);
//                    applyAndCascade(row, focusColName, value, false);
//                }
//            }
//        }
//
//        scheduleSaveAndRecalc();
//    }
//
//    private void applyAndCascade(RowData row, String colName, String newVal, boolean recalcNow) {
//        if (newVal == null) newVal = "";
//        row.setProperty(colName, newVal.trim());
//
//        if ("code".equals(colName)) {
//            String name = getNameByCode(row.getProperty("code").get());
//            row.setProperty("name", name);
//        }
//        if ("name_wash".equals(colName)) {
//            String recipeTime = getRecipeTimeByWash(row.getProperty("name_wash").get());
//            row.setProperty("recipe_time", recipeTime);
//        }
//        if ("date".equals(colName)) {
//            String monthVal = parseMonthNumber(row.getProperty("date").get());
//            if (!monthVal.isEmpty()) row.setProperty("month", monthVal);
//        }
//
//        if (recalcNow || "start_time".equals(colName) || "end_time".equals(colName)
//                || "recipe_time".equals(colName) || "date".equals(colName)
//                || "code".equals(colName) || "name_wash".equals(colName)) {
//            calculateRow(row);
//        }
//    }
//
//    private void attachReactiveBehavior(RowData row) {
//        row.getProperty("code").addListener((o, oldV, newV) -> {
//            String name = getNameByCode(newV == null ? "" : newV.trim());
//            row.setProperty("name", name);
//            calculateRow(row);
//            scheduleSaveAndRecalc();
//        });
//        row.getProperty("name_wash").addListener((o, oldV, newV) -> {
//            String rt = getRecipeTimeByWash(newV == null ? "" : newV.trim());
//            row.setProperty("recipe_time", rt);
//            calculateRow(row);
//            scheduleSaveAndRecalc();
//        });
//        String[] drivers = {"start_time", "end_time", "recipe_time", "date", "code"};
//        for (String k : drivers) {
//            row.getProperty(k).addListener((o, oldV, newV) -> {
//                if ("date".equals(k)) {
//                    String m = parseMonthNumber(newV == null ? "" : newV.trim());
//                    if (!m.isEmpty()) row.setProperty("month", m);
//                }
//                calculateRow(row);
//                scheduleSaveAndRecalc();
//            });
//        }
//    }
//
//    private void calculateRow(RowData row) {
//        String start = row.getProperty("start_time").get();
//        String end = row.getProperty("end_time").get();
//        String recipeStr = row.getProperty("recipe_time").get();
//
//        if (start == null) start = "";
//        if (end == null) end = "";
//
//        if (start.isEmpty() || end.isEmpty()) {
//            row.setProperty("hour", "");
//            row.setProperty("minute", "");
//            row.setProperty("fark", "");
//            row.setProperty("percentage", "");
//            return;
//        }
//
//        try {
//            LocalTime startTime = LocalTime.parse(start.toUpperCase(), TIME_FORMAT);
//            LocalTime endTime = LocalTime.parse(end.toUpperCase(), TIME_FORMAT);
//
//            if (endTime.isBefore(startTime)) endTime = endTime.plusHours(24);
//
//            Duration d = Duration.between(startTime, endTime);
//            long minutes = d.toMinutes();
//            row.setProperty("hour", String.format("%d:%02d", minutes / 60, minutes % 60));
//            row.setProperty("minute", String.valueOf(minutes));
//
//            int recipe = 0;
//            try {
//                recipe = Integer.parseInt(recipeStr == null ? "0" : recipeStr.trim());
//            } catch (NumberFormatException ignore) {}
//            long fark = Math.max(0, minutes - recipe);
//            row.setProperty("fark", String.valueOf(fark));
//
//            double perc = (minutes > 0 && recipe > 0) ? (100.0 * recipe / minutes) : 0;
//            row.setProperty("percentage", String.format(Locale.ENGLISH, "%.1f", perc));
//
//        } catch (DateTimeParseException e) {
//            showError("Invalid time format! Please use format like 8:30 AM or 12:15 PM");
//            row.setProperty("start_time", "");
//            row.setProperty("end_time", "");
//            row.setProperty("hour", "");
//            row.setProperty("minute", "");
//            row.setProperty("fark", "");
//            row.setProperty("percentage", "");
//        }
//    }
//
//    private void recalcAllFormulas() {
//        // Group rows by date and code to calculate totals
//        for (RowData r : data) {
//            String date = r.getProperty("date").get();
//            String code = r.getProperty("code").get();
//
//            if (date == null || date.isEmpty() || code == null || code.isEmpty()) {
//                r.setProperty("total_time_recipe", "");
//                r.setProperty("total_time", "");
//                r.setProperty("percentage_day", "");
//                continue;
//            }
//
//            double sumRecipe = 0;
//            double sumMinutes = 0;
//
//            // Sum recipe_time and minute for matching date and code
//            for (RowData other : data) {
//                if (date.equals(other.getProperty("date").get()) &&
//                        code.equals(other.getProperty("code").get())) {
//                    try {
//                        String recipeStr = z(other.getProperty("recipe_time").get());
//                        if (!recipeStr.isEmpty()) {
//                            sumRecipe += Double.parseDouble(recipeStr);
//                        }
//                    } catch (NumberFormatException ignore) {}
//                    try {
//                        String minuteStr = z(other.getProperty("minute").get());
//                        if (!minuteStr.isEmpty()) {
//                            sumMinutes += Double.parseDouble(minuteStr);
//                        }
//                    } catch (NumberFormatException ignore) {}
//                }
//            }
//
//            r.setProperty("total_time_recipe", String.format(Locale.ENGLISH, "%.0f", sumRecipe));
//            r.setProperty("total_time", String.format(Locale.ENGLISH, "%.0f", sumMinutes));
//            String percDay = (sumMinutes > 0 && sumRecipe > 0) ? 
//                            String.format(Locale.ENGLISH, "%.1f", (100.0 * sumRecipe / sumMinutes)) : "";
//            r.setProperty("percentage_day", percDay);
//        }
//    }
//
//    private static String z(String s) {
//        return s == null ? "" : s.trim();
//    }
//
//    private void exportToExcel() {
//        try {
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("BONUS_PERSONAL_LAUNDRY");
//
//            DataFormat df = workbook.createDataFormat();
//            CellStyle timeHMM = workbook.createCellStyle();
//            timeHMM.setDataFormat(df.getFormat("h:mm"));
//            CellStyle pctStyle = workbook.createCellStyle();
//            pctStyle.setDataFormat(df.getFormat("0.0"));
//
//            Row header = sheet.createRow(0);
//            for (int j = 0; j < table.getColumns().size(); j++) {
//                org.apache.poi.ss.usermodel.Cell c = header.createCell(j);
//                c.setCellValue(table.getColumns().get(j).getText());
//            }
//
//            String[] letters = new String[table.getColumns().size()];
//            for (int j = 0; j < letters.length; j++) letters[j] = CellReference.convertNumToColString(j);
//
//            final int COL_DATE = 0;
//            final int COL_MONTH = 1;
//            final int COL_SHIFT = 2;
//            final int COL_CODE = 3;
//            final int COL_NAME = 4;
//            final int COL_NAME_WASH = 5;
//            final int COL_MACHINE_NUMBER = 6;
//            final int COL_TIME_MACHINE = 7;
//            final int COL_PROCESS = 8;
//            final int COL_START_TIME = 9;
//            final int COL_END_TIME = 10;
//            final int COL_HOUR = 11;
//            final int COL_MINUTE = 12;
//            final int COL_RECIPE_TIME = 13;
//            final int COL_FARK = 14;
//            final int COL_PERCENTAGE = 15;
//            final int COL_TOTAL_TIME_RECIPE = 16;
//            final int COL_TOTAL_TIME = 17;
//            final int COL_PERCENTAGE_DAY = 18;
//
//            for (int i = 0; i < table.getItems().size(); i++) {
//                RowData rd = table.getItems().get(i);
//                Row row = sheet.createRow(i + 1);
//                int excelRow = i + 2;
//
//                for (int j = 0; j < table.getColumns().size(); j++) {
//                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(j);
//
//                    boolean isComputed = j == COL_HOUR || j == COL_MINUTE || j == COL_FARK || j == COL_PERCENTAGE
//                            || j == COL_TOTAL_TIME_RECIPE || j == COL_TOTAL_TIME || j == COL_PERCENTAGE_DAY;
//
//                    if (!isComputed) {
//                        String val = table.getColumns().get(j).getCellData(i) == null ? "" :
//                                table.getColumns().get(j).getCellData(i).toString();
//                        cell.setCellValue(val);
//                        continue;
//                    }
//
//                    String A = letters[COL_DATE];
//                    String D = letters[COL_CODE];
//                    String J = letters[COL_START_TIME];
//                    String K = letters[COL_END_TIME];
//                    String L = letters[COL_HOUR];
//                    String M = letters[COL_MINUTE];
//                    String N = letters[COL_RECIPE_TIME];
//                    String P = letters[COL_PERCENTAGE];
//                    String Q = letters[COL_TOTAL_TIME_RECIPE];
//                    String R = letters[COL_TOTAL_TIME];
//
//                    switch (j) {
//                        case COL_MINUTE: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),MOD(TIMEVALUE(%s%d)-TIMEVALUE(%s%d),1)*1440,\"\")",
//                                    J, excelRow, K, excelRow, K, excelRow, J, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_HOUR: {
//                            String f = String.format("IF(%s%d<>\"\",%s%d/1440,\"\")", M, excelRow, M, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(timeHMM);
//                            break;
//                        }
//                        case COL_FARK: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),MAX(0,%s%d-%s%d),\"\")",
//                                    M, excelRow, N, excelRow, M, excelRow, N, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_PERCENTAGE: {
//                            String f = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,\"\")",
//                                    M, excelRow, N, excelRow, N, excelRow, M, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(pctStyle);
//                            break;
//                        }
//                        case COL_TOTAL_TIME_RECIPE: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d),\"\")",
//                                    A, excelRow, D, excelRow, N, N, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_TOTAL_TIME: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d),\"\")",
//                                    A, excelRow, D, excelRow, M, M, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_PERCENTAGE_DAY: {
//                            String f = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,\"\")",
//                                    Q, excelRow, R, excelRow, Q, excelRow, R, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(pctStyle);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            sheet.createFreezePane(0, 1);
//            sheet.setAutoFilter(new CellRangeAddress(0, table.getItems().size(), 0, table.getColumns().size() - 1));
//
//            for (int j = 0; j < table.getColumns().size(); j++) sheet.autoSizeColumn(j);
//
//            FileChooser dialog = new FileChooser();
//            dialog.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
//            dialog.setInitialFileName("BONUS_PERSONAL_LAUNDRY");
//            dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
//            File dialogResult = dialog.showSaveDialog(null);
//            if (dialogResult == null) return;
//
//            try (FileOutputStream out = new FileOutputStream(dialogResult)) {
//                workbook.write(out);
//            }
//            workbook.close();
//            Desktop.getDesktop().open(dialogResult);
//        } catch (Exception ff) {
//            ff.printStackTrace();
//        }
//    }
//
//    private void showError(String msg) {
//        Alert alert = new Alert(Alert.AlertType.ERROR, "");
//        alert.setTitle("Fatal Error");
//        alert.setContentText(msg);
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.setMinSize(600, 50);
//        styleDialog(dialogPane);
//        alert.showAndWait();
//    }
//
//    private void loadData() {
//        data.clear();
//        try (Connection conn = connect();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery("SELECT * FROM Bonus_" + Alpha_PlannerController.seluserr)) {
//            while (rs.next()) {
//                RowData row = new RowData();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    row.setProperty(rs.getMetaData().getColumnName(i),
//                            rs.getString(i) == null ? "" : rs.getString(i));
//                }
//                attachReactiveBehavior(row);
//                calculateRow(row);
//                data.add(row);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void saveData() {
//        try (Connection conn = connect()) {
//            conn.setAutoCommit(false);
//            conn.createStatement().execute("DELETE FROM Bonus_" + Alpha_PlannerController.seluserr);
//            PreparedStatement ps = conn.prepareStatement(
//                    "INSERT INTO Bonus_" + Alpha_PlannerController.seluserr + " (date,month,shift,code,name,name_wash," +
//                            "machine_number,time_machine,process,start_time,end_time," +
//                            "hour,minute,recipe_time,fark,percentage," +
//                            "total_time_recipe,total_time,percentage_day) VALUES (?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?)"
//            );
//            for (RowData row : data) {
//                for (int i = 0; i < row.values.length; i++) {
//                    ps.setString(i + 1, row.values[i]);
//                }
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            conn.commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private String getNameByCode(String code) {
//        try (Connection conn = connect();
//             PreparedStatement ps = conn.prepareStatement("SELECT name FROM Workers WHERE code=?")) {
//            ps.setString(1, code);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return rs.getString("name");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    private String getRecipeTimeByWash(String wash) {
//        if (wash == null || wash.trim().isEmpty()) {
//            return "";
//        }
//
//        String normalizedWash = wash.toLowerCase().replace("_", "").replace(" ", "");
//
//        if (normalizedWash.equals("moonwash") || normalizedWash.equals("moonwashfoam")) {
//            return "120";
//        }
//        if (normalizedWash.equals("rinse")) {
//            return "60";
//        }
//
//        String queryTimer = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
//                           "FROM Timer " +
//                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        String queryThreeShots = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
//                                "FROM Timer_Three_Shots " +
//                                "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        String queryPilot = "SELECT Shot, Time_In_Min " +
//                           "FROM Timer_Pilot " +
//                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        try (Connection conn = connect2()) {
//            if (normalizedWash.equals("rigid")) {
//                double total = 0.0;
//                boolean hasShot1 = false;
//
//                try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
//                    ps.setString(1, normalizedWash);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            String shot = rs.getString("Shot");
//                            String updated = rs.getString("Time_In_Min_Updated");
//                            String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                    ? rs.getString("Time_In_Min")
//                                    : updated;
//
//                            if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                double t = Double.parseDouble(timeStr);
//                                if ("1".equals(shot)) {
//                                    total += t;
//                                    hasShot1 = true;
//                                } else if ("2".equals(shot)) {
//                                    total += t;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (hasShot1) {
//                    return String.valueOf(total);
//                }
//            }
//
//            if (normalizedWash.equals("tint")) {
//                double total = 0.0;
//                boolean found = false;
//
//                try (PreparedStatement ps = conn.prepareStatement(queryTimer)) {
//                    ps.setString(1, normalizedWash);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            String shot = rs.getString("Shot");
//                            if ("2".equals(shot)) {
//                                String updated = rs.getString("Time_In_Min_Updated");
//                                String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                        ? rs.getString("Time_In_Min")
//                                        : updated;
//                                if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                    total += Double.parseDouble(timeStr);
//                                    found = true;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
//                    ps.setString(1, normalizedWash);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            String shot = rs.getString("Shot");
//                            if ("3".equals(shot)) {
//                                String updated = rs.getString("Time_In_Min_Updated");
//                                String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                        ? rs.getString("Time_In_Min")
//                                        : updated;
//                                if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                    total += Double.parseDouble(timeStr);
//                                    found = true;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (!found) {
//                    try (PreparedStatement ps = conn.prepareStatement(queryPilot)) {
//                        ps.setString(1, normalizedWash);
//                        try (ResultSet rs = ps.executeQuery()) {
//                            while (rs.next()) {
//                                String shot = rs.getString("Shot");
//                                if ("2".equals(shot) || "3".equals(shot)) {
//                                    String updated = rs.getString("Time_In_Min");
//                                    String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                            ? rs.getString("Time_In_Min")
//                                            : updated;
//                                    if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                        total += Double.parseDouble(timeStr);
//                                        found = true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (found) return String.valueOf(total);
//            }
//
//            try (PreparedStatement ps = conn.prepareStatement(queryTimer)) {
//                ps.setString(1, normalizedWash);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        String updated = rs.getString("Time_In_Min_Updated");
//                        return "Hasnot_Updated_Yet".equals(updated)
//                                ? rs.getString("Time_In_Min")
//                                : updated;
//                    }
//                }
//            }
//
//            double totalTime = 0.0;
//            boolean hasShot1 = false, hasShot2 = false;
//
//            try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
//                ps.setString(1, normalizedWash);
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        String shot = rs.getString("Shot");
//                        String updated = rs.getString("Time_In_Min_Updated");
//                        String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                ? rs.getString("Time_In_Min")
//                                : updated;
//
//                        if (timeStr != null && !timeStr.trim().isEmpty()) {
//                            double t = Double.parseDouble(timeStr);
//                            if ("1".equals(shot)) {
//                                totalTime += t;
//                                hasShot1 = true;
//                            } else if ("2".equals(shot)) {
//                                totalTime += t;
//                                hasShot2 = true;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (hasShot1 && hasShot2) return String.valueOf(totalTime);
//            if (hasShot1) return String.valueOf(totalTime);
//
//            try (PreparedStatement ps = conn.prepareStatement(queryPilot)) {
//                ps.setString(1, normalizedWash);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        String updated = rs.getString("Time_In_Min");
//                        return "Hasnot_Updated_Yet".equals(updated)
//                                ? rs.getString("Time_In_Min")
//                                : updated;
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            Logger.getLogger(ExcelLikeTableApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return "";
//    }
//
//    public static class RowData {
//        private final SimpleStringProperty[] props;
//        private final String[] colNames = {
//                "date", "month", "shift", "code", "name", "name_wash",
//                "machine_number", "time_machine", "process", "start_time",
//                "end_time", "hour", "minute", "recipe_time", "fark",
//                "percentage", "total_time_recipe", "total_time", "percentage_day"
//        };
//        private final String[] values;
//
//        public RowData() {
//            props = new SimpleStringProperty[colNames.length];
//            values = new String[colNames.length];
//            for (int i = 0; i < colNames.length; i++) {
//                props[i] = new SimpleStringProperty("");
//                values[i] = "";
//                final int idx = i;
//                props[i].addListener((obs, old, val) -> values[idx] = val);
//            }
//        }
//
//        public SimpleStringProperty getProperty(String name) {
//            for (int i = 0; i < colNames.length; i++) {
//                if (colNames[i].equals(name)) return props[i];
//            }
//            return new SimpleStringProperty("");
//        }
//
//        public void setProperty(String name, String value) {
//            for (int i = 0; i < colNames.length; i++) {
//                if (colNames[i].equals(name)) props[i].set(value == null ? "" : value);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}


/////////////////////////////////////////////////////Third Version//////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////Q/R//////////////////////////////////////////////////////////////


//
//package alpha.planner;
//
//import com.jfoenix.controls.JFXButton;
//import java.awt.Desktop;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ListChangeListener;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.input.Clipboard;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import javafx.stage.FileChooser;
//import java.sql.*;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.CellReference;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.controlsfx.control.table.TableFilter;
//
//public class ExcelLikeTableApp extends Application {
//
//    private final TableView<RowData> table = new TableView<>();
//    private final ObservableList<RowData> data = FXCollections.observableArrayList();
//    private JFXButton loadBtn;
//    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//    private ScheduledFuture<?> pendingSave;
//
//    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
//    private static String vvaall, vvaalll;
//
//    private static final String[] COLUMNS = {
//            "date", "month", "shift", "code", "name", "name_wash",
//            "machine_number", "time_machine", "process", "start_time",
//            "end_time", "hour", "minute", "recipe_time", "fark",
//            "percentage", "total_time_recipe", "total_time", "percentage_day"
//    };
//
//    @Override
//    public void stop() {
//        executor.shutdown();
//        try {
//            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
//                executor.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executor.shutdownNow();
//        }
//    }
//
//    private Connection connect() throws Exception {
//        String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
//        List<String> lines = Files.readAllLines(Paths.get(filePath));
//        for (String line : lines) {
//            if (line.startsWith("DB=")) {
//                vvaall = line.split("=", 2)[1];
//                break;
//            }
//        }
//        Class.forName("org.sqlite.JDBC");
//        return DriverManager.getConnection("jdbc:sqlite:" + vvaall);
//    }
//    
//    private Connection connect2() throws Exception {
//        String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
//        List<String> lines = Files.readAllLines(Paths.get(filePath));
//        for (String line : lines) {
//            if (line.startsWith("Recipe_DB=")) {
//                vvaalll = line.split("=", 2)[1];
//                break;
//            }
//        }
//        Class.forName("org.sqlite.JDBC");
//        return DriverManager.getConnection("jdbc:sqlite:" + vvaalll);
//    }
//
//    private String parseMonthNumber(String dateStr) {
//        if (dateStr == null || dateStr.trim().isEmpty()) return "";
//
//        List<DateTimeFormatter> formatters = Arrays.asList(
//                DateTimeFormatter.ofPattern("dd-MM"),
//                DateTimeFormatter.ofPattern("dd.MM"),
//                DateTimeFormatter.ofPattern("d-M"),
//                DateTimeFormatter.ofPattern("d.M"),
//                DateTimeFormatter.ofPattern("dd-MMM"),
//                DateTimeFormatter.ofPattern("d-MMM"),
//                DateTimeFormatter.ofPattern("dd-MMMM"),
//                DateTimeFormatter.ofPattern("d-MMMM"),
//                DateTimeFormatter.ofPattern("dd-MM-yy"),
//                DateTimeFormatter.ofPattern("dd.MM.yy"),
//                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
//                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
//                DateTimeFormatter.ofPattern("d-M-yy"),
//                DateTimeFormatter.ofPattern("d.M.yy"),
//                DateTimeFormatter.ofPattern("d-M-yyyy"),
//                DateTimeFormatter.ofPattern("d.M.yyyy"),
//                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
//                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
//                DateTimeFormatter.ofPattern("MMM-dd"),
//                DateTimeFormatter.ofPattern("MMMM-dd")
//        );
//
//        for (DateTimeFormatter fmt : formatters) {
//            try {
//                if (!dateStr.contains("-20") && !dateStr.contains(".20") && !dateStr.contains("/20")) {
//                    String currentYear = String.valueOf(LocalDate.now().getYear());
//                    String fullDateStr = dateStr + "-" + currentYear;
//                    List<DateTimeFormatter> fullFormatters = Arrays.asList(
//                            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
//                            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
//                            DateTimeFormatter.ofPattern("d-M-yyyy"),
//                            DateTimeFormatter.ofPattern("d.M.yyyy"),
//                            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
//                            DateTimeFormatter.ofPattern("d-MMM-yyyy"),
//                            DateTimeFormatter.ofPattern("dd-MMMM-yyyy"),
//                            DateTimeFormatter.ofPattern("d-MMMM-yyyy")
//                    );
//                    for (DateTimeFormatter fullFmt : fullFormatters) {
//                        try {
//                            LocalDate d = LocalDate.parse(fullDateStr, fullFmt.withLocale(Locale.ENGLISH));
//                            return String.format("%02d", d.getMonthValue());
//                        } catch (Exception ignored) {}
//                    }
//                }
//                LocalDate d = LocalDate.parse(dateStr, fmt.withLocale(Locale.ENGLISH));
//                return String.format("%02d", d.getMonthValue());
//            } catch (Exception ignored) {}
//        }
//        return "";
//    }
//
//    @Override
//    public void start(Stage stage) {
//        table.setEditable(true);
//        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        table.getSelectionModel().setCellSelectionEnabled(true);
//
//        for (String colName : COLUMNS) {
//            TableColumn<RowData, String> col = new TableColumn<>(colName);
//            col.setCellValueFactory(param -> param.getValue().getProperty(colName));
//            col.setCellFactory(TextFieldTableCell.forTableColumn());
//
//            col.setOnEditCommit(event -> {
//                String newVal = event.getNewValue() == null ? "" : event.getNewValue().trim();
//                RowData row = event.getRowValue();
//                applyAndCascade(row, colName, newVal, false);
//                scheduleSaveAndRecalc();
//            });
//
//            table.getColumns().add(col);
//        }
//
//        table.setItems(data);
//        new TableFilter<>(table);
//
//        loadBtn = btn("🔌 Load");
//        loadBtn.setOnAction(e -> {
//            loadData();
//            scheduleSaveAndRecalc();
//        });
//        Platform.runLater(loadBtn::fire);
//
//        JFXButton saveBtn = btn("✨ Save");
//        saveBtn.setOnAction(e -> scheduleSaveAndRecalc());
//
//        JFXButton addRowBtn = btn("⚡ Add Row");
//        addRowBtn.setOnAction(e -> {
//            RowData r = new RowData();
//            attachReactiveBehavior(r);
//            data.add(r);
//            scheduleSaveAndRecalc();
//        });
//
//        JFXButton addManyRowsBtn = btn("❤ Add Many Rows");
//        addManyRowsBtn.setOnAction(e -> {
//            TextInputDialog dialog = new TextInputDialog("5");
//            dialog.setHeaderText("Add Many Rows");
//            DialogPane dialogPane = dialog.getDialogPane();
//            dialogPane.setMinSize(600, 50);
//            styleDialog(dialogPane);
//            dialog.setContentText("Enter number of rows to add:");
//            dialog.showAndWait().ifPresent(numStr -> {
//                try {
//                    int num = Integer.parseInt(numStr);
//                    for (int i = 0; i < num; i++) {
//                        RowData r = new RowData();
//                        attachReactiveBehavior(r);
//                        data.add(r);
//                    }
//                    scheduleSaveAndRecalc();
//                } catch (NumberFormatException ex) {
//                    showError("Invalid Number");
//                }
//            });
//        });
//
//        JFXButton deleteBtn = btn("✖ Delete Row(s)");
//        deleteBtn.setOnAction(e -> {
//            ObservableList<RowData> selected = table.getSelectionModel().getSelectedItems();
//            if (selected != null && !selected.isEmpty()) {
//                data.removeAll(selected);
//                scheduleSaveAndRecalc();
//            }
//        });
//
//        JFXButton exportBtn = btn("🛒 Export");
//        exportBtn.setOnAction(e -> exportToExcel());
//
//        ToolBar toolbar = new ToolBar(loadBtn, saveBtn, addRowBtn, addManyRowsBtn, deleteBtn, exportBtn);
//
//        BorderPane root = new BorderPane();
//        root.setTop(toolbar);
//        root.setCenter(table);
//
//        Scene scene = new Scene(root, 1200, 600);
//        try {
//            BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme = bis.readLine();
//            bis.close();
//            scene.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//
//        table.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyShortcuts);
//
//        data.addListener((ListChangeListener<RowData>) c -> {
//            while (c.next()) {
//                if (c.wasAdded()) {
//                    for (RowData r : c.getAddedSubList()) attachReactiveBehavior(r);
//                }
//            }
//            scheduleSaveAndRecalc();
//        });
//
//        stage.setScene(scene);
//        stage.setTitle("😎 BONUS PERSONAL LAUNDRY");
//        stage.setMaximized(true);
//        stage.show();
//    }
//
//    private JFXButton btn(String text) {
//        JFXButton b = new JFXButton(text);
//        b.setButtonType(JFXButton.ButtonType.RAISED);
//        b.setStyle("-fx-background-color:#0A84FF;-fx-font-weight:bold;-fx-text-fill:white;-fx-background-radius:3em;");
//        return b;
//    }
//
//    private void styleDialog(DialogPane dialogPane) {
//        try {
//            BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//            String theme = bis.readLine();
//            bis.close();
//            dialogPane.getStylesheets().add(getClass().getResource(theme).toExternalForm());
//        } catch (Exception ignored) {}
//    }
//
//    private void handleKeyShortcuts(KeyEvent e) {
//        if (e.isControlDown() && e.getCode() == KeyCode.V) {
//            e.consume();
//            pasteFromClipboard();
//        } else if (e.isControlDown() && e.getCode() == KeyCode.D) {
//            e.consume();
//            fillDownFromFocusedCell();
//        } else if (e.getCode() == KeyCode.DELETE) {
//            e.consume();
//            clearSelectedCells();
//        }
//    }
//
//    private void clearSelectedCells() {
//        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
//        if (selectedCells == null || selectedCells.isEmpty()) return;
//
//        boolean anyChange = false;
//        for (TablePosition tp : selectedCells) {
//            int rowIndex = tp.getRow();
//            int colIndex = tp.getColumn();
//            if (rowIndex >= 0 && rowIndex < data.size() && colIndex >= 0 && colIndex < COLUMNS.length) {
//                RowData row = data.get(rowIndex);
//                String colName = COLUMNS[colIndex];
//                applyAndCascade(row, colName, "", false);
//                anyChange = true;
//            }
//        }
//
//        if (anyChange) {
//            scheduleSaveAndRecalc();
//        }
//    }
//
//    private void scheduleSaveAndRecalc() {
//        if (pendingSave != null && !pendingSave.isDone()) {
//            pendingSave.cancel(false);
//        }
//        pendingSave = executor.schedule(() -> {
//            Platform.runLater(() -> {
//                recalcAllFormulas();
//                saveData();
//                table.refresh();
//            });
//        }, 500, TimeUnit.MILLISECONDS);
//    }
//
//    private void pasteFromClipboard() {
//        final TablePosition<RowData, ?> focus = table.getFocusModel().getFocusedCell();
//        if (focus == null || focus.getColumn() < 0) return;
//
//        Clipboard cb = Clipboard.getSystemClipboard();
//        if (cb == null || !cb.hasString()) return;
//
//        String raw = cb.getString();
//        if (raw == null || raw.isEmpty()) return;
//
//        String[] lines = raw.replace("\r", "").split("\n");
//        int baseRow = focus.getRow();
//        int baseCol = focus.getColumn();
//
//        for (int r = 0; r < lines.length; r++) {
//            String[] parts = lines[r].split("\t", -1);
//            int targetRow = baseRow + r;
//
//            while (targetRow >= data.size()) {
//                RowData nr = new RowData();
//                attachReactiveBehavior(nr);
//                data.add(nr);
//            }
//            RowData row = data.get(targetRow);
//
//            for (int c = 0; c < parts.length; c++) {
//                int targetCol = baseCol + c;
//                if (targetCol >= COLUMNS.length) break;
//                String colName = COLUMNS[targetCol];
//                String value = parts[c] == null ? "" : parts[c].trim();
//                applyAndCascade(row, colName, value, false);
//            }
//        }
//
//        scheduleSaveAndRecalc();
//    }
//
//    private void fillDownFromFocusedCell() {
//        TablePosition<RowData, ?> focus = table.getFocusModel().getFocusedCell();
//        if (focus == null || focus.getColumn() < 0 || focus.getRow() < 0) return;
//
//        int focusCol = focus.getColumn();
//        String focusColName = COLUMNS[focusCol];
//        Object rawVal = table.getColumns().get(focusCol).getCellData(focus.getRow());
//        String value = rawVal == null ? "" : rawVal.toString();
//
//        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
//        if (selectedCells != null && !selectedCells.isEmpty()) {
//            for (TablePosition tp : selectedCells) {
//                int r = tp.getRow();
//                int c = tp.getColumn();
//                if (c != focusCol) continue;
//                if (r >= 0 && r < data.size()) {
//                    RowData row = data.get(r);
//                    applyAndCascade(row, focusColName, value, false);
//                }
//            }
//        } else {
//            ObservableList<Integer> selectedRows = table.getSelectionModel().getSelectedIndices();
//            for (Integer r : selectedRows) {
//                if (r >= 0 && r < data.size()) {
//                    RowData row = data.get(r);
//                    applyAndCascade(row, focusColName, value, false);
//                }
//            }
//        }
//
//        scheduleSaveAndRecalc();
//    }
//
//    private void applyAndCascade(RowData row, String colName, String newVal, boolean recalcNow) {
//        if (newVal == null) newVal = "";
//        row.setProperty(colName, newVal.trim());
//
//        if ("code".equals(colName)) {
//            String name = getNameByCode(row.getProperty("code").get());
//            row.setProperty("name", name);
//        }
//        if ("process".equals(colName) || "name_wash".equals(colName)) {
//            String process = row.getProperty("process").get();
//            String nameWash = row.getProperty("name_wash").get();
//            String recipeTime = getRecipeTimeByProcess(process, nameWash);
//            row.setProperty("recipe_time", recipeTime);
//        }
//        if ("date".equals(colName)) {
//            String monthVal = parseMonthNumber(row.getProperty("date").get());
//            if (!monthVal.isEmpty()) row.setProperty("month", monthVal);
//        }
//
//        if (recalcNow || "start_time".equals(colName) || "end_time".equals(colName)
//                || "recipe_time".equals(colName) || "date".equals(colName)
//                || "code".equals(colName) || "process".equals(colName) || "name_wash".equals(colName)) {
//            calculateRow(row);
//        }
//    }
//
//    private void attachReactiveBehavior(RowData row) {
//        row.getProperty("code").addListener((o, oldV, newV) -> {
//            String name = getNameByCode(newV == null ? "" : newV.trim());
//            row.setProperty("name", name);
//            calculateRow(row);
//            scheduleSaveAndRecalc();
//        });
//        row.getProperty("name_wash").addListener((o, oldV, newV) -> {
//            String process = row.getProperty("process").get() == null ? "" : row.getProperty("process").get().trim();
//            String rt = getRecipeTimeByProcess(process, newV == null ? "" : newV.trim());
//            row.setProperty("recipe_time", rt);
//            calculateRow(row);
//            scheduleSaveAndRecalc();
//        });
//        row.getProperty("process").addListener((o, oldV, newV) -> {
//            String nameWash = row.getProperty("name_wash").get() == null ? "" : row.getProperty("name_wash").get().trim();
//            String rt = getRecipeTimeByProcess(newV == null ? "" : newV.trim(), nameWash);
//            row.setProperty("recipe_time", rt);
//            calculateRow(row);
//            scheduleSaveAndRecalc();
//        });
//        String[] drivers = {"start_time", "end_time", "recipe_time", "date", "code"};
//        for (String k : drivers) {
//            row.getProperty(k).addListener((o, oldV, newV) -> {
//                if ("date".equals(k)) {
//                    String m = parseMonthNumber(newV == null ? "" : newV.trim());
//                    if (!m.isEmpty()) row.setProperty("month", m);
//                }
//                calculateRow(row);
//                scheduleSaveAndRecalc();
//            });
//        }
//    }
//
//    private void calculateRow(RowData row) {
//        String start = row.getProperty("start_time").get();
//        String end = row.getProperty("end_time").get();
//        String recipeStr = row.getProperty("recipe_time").get();
//
//        if (start == null) start = "";
//        if (end == null) end = "";
//
//        if (start.isEmpty() || end.isEmpty()) {
//            row.setProperty("hour", "");
//            row.setProperty("minute", "");
//            row.setProperty("fark", "");
//            row.setProperty("percentage", "");
//            return;
//        }
//
//        try {
//            LocalTime startTime = LocalTime.parse(start.toUpperCase(), TIME_FORMAT);
//            LocalTime endTime = LocalTime.parse(end.toUpperCase(), TIME_FORMAT);
//
//            if (endTime.isBefore(startTime)) endTime = endTime.plusHours(24);
//
//            Duration d = Duration.between(startTime, endTime);
//            long minutes = d.toMinutes();
//            row.setProperty("hour", String.format("%d:%02d", minutes / 60, minutes % 60));
//            row.setProperty("minute", String.valueOf(minutes));
//
//            int recipe = 0;
//            try {
//                recipe = Integer.parseInt(recipeStr == null ? "0" : recipeStr.trim());
//            } catch (NumberFormatException ignore) {}
//            long fark = Math.max(0, minutes - recipe);
//            row.setProperty("fark", String.valueOf(fark));
//
//            double perc = (minutes > 0 && recipe > 0) ? (100.0 * recipe / minutes) : 0;
//            row.setProperty("percentage", String.format(Locale.ENGLISH, "%.1f", perc));
//
//        } catch (DateTimeParseException e) {
//            showError("Invalid time format! Please use format like 8:30 AM or 12:15 PM");
//            row.setProperty("start_time", "");
//            row.setProperty("end_time", "");
//            row.setProperty("hour", "");
//            row.setProperty("minute", "");
//            row.setProperty("fark", "");
//            row.setProperty("percentage", "");
//        }
//    }
//
//    private void recalcAllFormulas() {
//        for (RowData r : data) {
//            String date = r.getProperty("date").get();
//            String code = r.getProperty("code").get();
//
//            if (date == null || date.isEmpty() || code == null || code.isEmpty()) {
//                r.setProperty("total_time_recipe", "");
//                r.setProperty("total_time", "");
//                r.setProperty("percentage_day", "");
//                continue;
//            }
//
//            double sumRecipe = 0;
//            double sumMinutes = 0;
//
//            for (RowData other : data) {
//                if (date.equals(other.getProperty("date").get()) &&
//                        code.equals(other.getProperty("code").get())) {
//                    try {
//                        String recipeStr = z(other.getProperty("recipe_time").get());
//                        if (!recipeStr.isEmpty()) {
//                            sumRecipe += Double.parseDouble(recipeStr);
//                        }
//                    } catch (NumberFormatException ignore) {}
//                    try {
//                        String minuteStr = z(other.getProperty("minute").get());
//                        if (!minuteStr.isEmpty()) {
//                            sumMinutes += Double.parseDouble(minuteStr);
//                        }
//                    } catch (NumberFormatException ignore) {}
//                }
//            }
//
//            r.setProperty("total_time_recipe", String.format(Locale.ENGLISH, "%.0f", sumRecipe));
//            r.setProperty("total_time", String.format(Locale.ENGLISH, "%.0f", sumMinutes));
//            String percDay = (sumMinutes > 0 && sumRecipe > 0) ? 
//                            String.format(Locale.ENGLISH, "%.1f", (100.0 * sumRecipe / sumMinutes)) : "";
//            r.setProperty("percentage_day", percDay);
//        }
//    }
//
//    private static String z(String s) {
//        return s == null ? "" : s.trim();
//    }
//
//    private void exportToExcel() {
//        try {
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("BONUS_PERSONAL_LAUNDRY");
//
//            DataFormat df = workbook.createDataFormat();
//            CellStyle timeHMM = workbook.createCellStyle();
//            timeHMM.setDataFormat(df.getFormat("h:mm"));
//            CellStyle pctStyle = workbook.createCellStyle();
//            pctStyle.setDataFormat(df.getFormat("0.0"));
//
//            Row header = sheet.createRow(0);
//            for (int j = 0; j < table.getColumns().size(); j++) {
//                org.apache.poi.ss.usermodel.Cell c = header.createCell(j);
//                c.setCellValue(table.getColumns().get(j).getText());
//            }
//
//            String[] letters = new String[table.getColumns().size()];
//            for (int j = 0; j < letters.length; j++) letters[j] = CellReference.convertNumToColString(j);
//
//            final int COL_DATE = 0;
//            final int COL_MONTH = 1;
//            final int COL_SHIFT = 2;
//            final int COL_CODE = 3;
//            final int COL_NAME = 4;
//            final int COL_NAME_WASH = 5;
//            final int COL_MACHINE_NUMBER = 6;
//            final int COL_TIME_MACHINE = 7;
//            final int COL_PROCESS = 8;
//            final int COL_START_TIME = 9;
//            final int COL_END_TIME = 10;
//            final int COL_HOUR = 11;
//            final int COL_MINUTE = 12;
//            final int COL_RECIPE_TIME = 13;
//            final int COL_FARK = 14;
//            final int COL_PERCENTAGE = 15;
//            final int COL_TOTAL_TIME_RECIPE = 16;
//            final int COL_TOTAL_TIME = 17;
//            final int COL_PERCENTAGE_DAY = 18;
//
//            for (int i = 0; i < table.getItems().size(); i++) {
//                RowData rd = table.getItems().get(i);
//                Row row = sheet.createRow(i + 1);
//                int excelRow = i + 2;
//
//                for (int j = 0; j < table.getColumns().size(); j++) {
//                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(j);
//
//                    boolean isComputed = j == COL_HOUR || j == COL_MINUTE || j == COL_FARK || j == COL_PERCENTAGE
//                            || j == COL_TOTAL_TIME_RECIPE || j == COL_TOTAL_TIME || j == COL_PERCENTAGE_DAY;
//
//                    if (!isComputed) {
//                        String val = table.getColumns().get(j).getCellData(i) == null ? "" :
//                                table.getColumns().get(j).getCellData(i).toString();
//                        cell.setCellValue(val);
//                        continue;
//                    }
//
//                    String A = letters[COL_DATE];
//                    String D = letters[COL_CODE];
//                    String J = letters[COL_START_TIME];
//                    String K = letters[COL_END_TIME];
//                    String L = letters[COL_HOUR];
//                    String M = letters[COL_MINUTE];
//                    String N = letters[COL_RECIPE_TIME];
//                    String P = letters[COL_PERCENTAGE];
//                    String Q = letters[COL_TOTAL_TIME_RECIPE];
//                    String R = letters[COL_TOTAL_TIME];
//
//                    switch (j) {
//                        case COL_MINUTE: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),MOD(TIMEVALUE(%s%d)-TIMEVALUE(%s%d),1)*1440,\"\")",
//                                    J, excelRow, K, excelRow, K, excelRow, J, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_HOUR: {
//                            String f = String.format("IF(%s%d<>\"\",%s%d/1440,\"\")", M, excelRow, M, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(timeHMM);
//                            break;
//                        }
//                        case COL_FARK: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),MAX(0,%s%d-%s%d),\"\")",
//                                    M, excelRow, N, excelRow, M, excelRow, N, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_PERCENTAGE: {
//                            String f = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,\"\")",
//                                    M, excelRow, N, excelRow, N, excelRow, M, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(pctStyle);
//                            break;
//                        }
//                        case COL_TOTAL_TIME_RECIPE: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d),\"\")",
//                                    A, excelRow, D, excelRow, N, N, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_TOTAL_TIME: {
//                            String f = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d),\"\")",
//                                    A, excelRow, D, excelRow, M, M, A, A, A, excelRow, D, D, D, excelRow);
//                            cell.setCellFormula(f);
//                            break;
//                        }
//                        case COL_PERCENTAGE_DAY: {
//                            String f = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,\"\")",
//                                    Q, excelRow, R, excelRow, Q, excelRow, R, excelRow);
//                            cell.setCellFormula(f);
//                            cell.setCellStyle(pctStyle);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            sheet.createFreezePane(0, 1);
//            sheet.setAutoFilter(new CellRangeAddress(0, table.getItems().size(), 0, table.getColumns().size() - 1));
//
//            for (int j = 0; j < table.getColumns().size(); j++) sheet.autoSizeColumn(j);
//
//            FileChooser dialog = new FileChooser();
//            dialog.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
//            dialog.setInitialFileName("BONUS_PERSONAL_LAUNDRY");
//            dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
//            File dialogResult = dialog.showSaveDialog(null);
//            if (dialogResult == null) return;
//
//            try (FileOutputStream out = new FileOutputStream(dialogResult)) {
//                workbook.write(out);
//            }
//            workbook.close();
//            Desktop.getDesktop().open(dialogResult);
//        } catch (Exception ff) {
//            ff.printStackTrace();
//        }
//    }
//
//    private void showError(String msg) {
//        Alert alert = new Alert(Alert.AlertType.ERROR, "");
//        alert.setTitle("Fatal Error");
//        alert.setContentText(msg);
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.setMinSize(600, 50);
//        styleDialog(dialogPane);
//        alert.showAndWait();
//    }
//
//    private void loadData() {
//        data.clear();
//        try (Connection conn = connect();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery("SELECT * FROM Bonus_" + Alpha_PlannerController.seluserr)) {
//            while (rs.next()) {
//                RowData row = new RowData();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    row.setProperty(rs.getMetaData().getColumnName(i),
//                            rs.getString(i) == null ? "" : rs.getString(i));
//                }
//                attachReactiveBehavior(row);
//                calculateRow(row);
//                data.add(row);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void saveData() {
//        try (Connection conn = connect()) {
//            conn.setAutoCommit(false);
//            conn.createStatement().execute("DELETE FROM Bonus_" + Alpha_PlannerController.seluserr);
//            PreparedStatement ps = conn.prepareStatement(
//                    "INSERT INTO Bonus_" + Alpha_PlannerController.seluserr + " (date,month,shift,code,name,name_wash," +
//                            "machine_number,time_machine,process,start_time,end_time," +
//                            "hour,minute,recipe_time,fark,percentage," +
//                            "total_time_recipe,total_time,percentage_day) VALUES (?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?)"
//            );
//            for (RowData row : data) {
//                for (int i = 0; i < row.values.length; i++) {
//                    ps.setString(i + 1, row.values[i]);
//                }
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            conn.commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private String getNameByCode(String code) {
//        try (Connection conn = connect();
//             PreparedStatement ps = conn.prepareStatement("SELECT name FROM Workers WHERE code=?")) {
//            ps.setString(1, code);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return rs.getString("name");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    private String getRecipeTimeByProcess(String process, String nameWash) {
//        String normalizedProcess = process == null ? "" : process.toLowerCase().replace("_", "").replace(" ", "");
//        String normalizedWash = nameWash == null ? "" : nameWash.toLowerCase().replace("_", "").replace(" ", "");
//
//        if (normalizedProcess.isEmpty() || normalizedProcess.equals("rigid")) {
//            return getFullRecipeTime(normalizedWash);
//        }
//
//        if (normalizedProcess.equals("moonwash") || normalizedProcess.equals("moonwashfoam")) {
//            return "120";
//        }
//        if (normalizedProcess.equals("rinse")) {
//            return "60";
//        }
//
//        String queryTimer = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
//                           "FROM Timer " +
//                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        String queryThreeShots = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
//                                "FROM Timer_Three_Shots " +
//                                "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        String queryPilot = "SELECT Shot, Time_In_Min " +
//                           "FROM Timer_Pilot " +
//                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        try (Connection conn = connect2()) {
//            if (normalizedProcess.equals("tint")) {
//                double total = 0.0;
//                boolean found = false;
//
//                try (PreparedStatement ps = conn.prepareStatement(queryTimer)) {
//                    ps.setString(1, normalizedWash);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            String shot = rs.getString("Shot");
//                            if ("2".equals(shot)) {
//                                String updated = rs.getString("Time_In_Min_Updated");
//                                String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                        ? rs.getString("Time_In_Min")
//                                        : updated;
//                                if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                    total += Double.parseDouble(timeStr);
//                                    found = true;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
//                    ps.setString(1, normalizedWash);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            String shot = rs.getString("Shot");
//                            if ("3".equals(shot)) {
//                                String updated = rs.getString("Time_In_Min_Updated");
//                                String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                        ? rs.getString("Time_In_Min")
//                                        : updated;
//                                if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                    total += Double.parseDouble(timeStr);
//                                    found = true;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (!found) {
//                    try (PreparedStatement ps = conn.prepareStatement(queryPilot)) {
//                        ps.setString(1, normalizedWash);
//                        try (ResultSet rs = ps.executeQuery()) {
//                            while (rs.next()) {
//                                String shot = rs.getString("Shot");
//                                if ("2".equals(shot) || "3".equals(shot)) {
//                                    String timeStr = rs.getString("Time_In_Min");
//                                    if (timeStr != null && !timeStr.trim().isEmpty()) {
//                                        total += Double.parseDouble(timeStr);
//                                        found = true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (found) return String.valueOf(total);
//            }
//
//            return getFullRecipeTime(normalizedProcess);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            Logger.getLogger(ExcelLikeTableApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return "";
//    }
//
//    private String getFullRecipeTime(String normalizedName) {
//        if (normalizedName.isEmpty()) {
//            return "";
//        }
//
//        String queryTimer = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
//                           "FROM Timer " +
//                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        String queryThreeShots = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
//                                "FROM Timer_Three_Shots " +
//                                "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        String queryPilot = "SELECT Shot, Time_In_Min " +
//                           "FROM Timer_Pilot " +
//                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";
//
//        try (Connection conn = connect2()) {
//            try (PreparedStatement ps = conn.prepareStatement(queryTimer)) {
//                ps.setString(1, normalizedName);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        String updated = rs.getString("Time_In_Min_Updated");
//                        return "Hasnot_Updated_Yet".equals(updated)
//                                ? rs.getString("Time_In_Min")
//                                : updated;
//                    }
//                }
//            }
//
//            double totalTime = 0.0;
//            boolean hasShot1 = false, hasShot2 = false;
//
//            try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
//                ps.setString(1, normalizedName);
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        String shot = rs.getString("Shot");
//                        String updated = rs.getString("Time_In_Min_Updated");
//                        String timeStr = "Hasnot_Updated_Yet".equals(updated)
//                                ? rs.getString("Time_In_Min")
//                                : updated;
//
//                        if (timeStr != null && !timeStr.trim().isEmpty()) {
//                            double t = Double.parseDouble(timeStr);
//                            if ("1".equals(shot)) {
//                                totalTime += t;
//                                hasShot1 = true;
//                            } else if ("2".equals(shot)) {
//                                totalTime += t;
//                                hasShot2 = true;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (hasShot1) return String.valueOf(totalTime);
//
//            try (PreparedStatement ps = conn.prepareStatement(queryPilot)) {
//                ps.setString(1, normalizedName);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        String timeStr = rs.getString("Time_In_Min");
//                        return timeStr;
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            Logger.getLogger(ExcelLikeTableApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return "";
//    }
//
//    public static class RowData {
//        private final SimpleStringProperty[] props;
//        private final String[] colNames = {
//                "date", "month", "shift", "code", "name", "name_wash",
//                "machine_number", "time_machine", "process", "start_time",
//                "end_time", "hour", "minute", "recipe_time", "fark",
//                "percentage", "total_time_recipe", "total_time", "percentage_day"
//        };
//        private final String[] values;
//
//        public RowData() {
//            props = new SimpleStringProperty[colNames.length];
//            values = new String[colNames.length];
//            for (int i = 0; i < colNames.length; i++) {
//                props[i] = new SimpleStringProperty("");
//                values[i] = "";
//                final int idx = i;
//                props[i].addListener((obs, old, val) -> values[idx] = val);
//            }
//        }
//
//        public SimpleStringProperty getProperty(String name) {
//            for (int i = 0; i < colNames.length; i++) {
//                if (colNames[i].equals(name)) return props[i];
//            }
//            return new SimpleStringProperty("");
//        }
//
//        public void setProperty(String name, String value) {
//            for (int i = 0; i < colNames.length; i++) {
//                if (colNames[i].equals(name)) props[i].set(value == null ? "" : value);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}



/////////////////////////////////////////////////////Third Version//////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////Q/H//////////////////////////////////////////////////////////////


package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

public class ExcelLikeTableApp extends Application {

    private final TableView<RowData> table = new TableView<>();
    private final ObservableList<RowData> data = FXCollections.observableArrayList();
    private JFXButton loadBtn;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> pendingSave;
    private TablePosition<RowData, ?> anchorCell; // Track anchor for range selection

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    private static String vvaall, vvaalll;

    private static final String[] COLUMNS = {
            "date", "month", "shift", "code", "name", "name_wash",
            "machine_number", "time_machine", "process", "start_time",
            "end_time", "hour", "minute", "recipe_time", "fark",
            "percentage", "total_time_recipe", "total_time", "percentage_day"
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

     
     private void pasteFromClipboardd() {
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
    
    private Connection connect2() throws Exception {
        String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            if (line.startsWith("Recipe_DB=")) {
                vvaalll = line.split("=", 2)[1];
                break;
            }
        }
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:" + vvaalll);
    }

    private String parseMonthNumber(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return "";

        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("dd-MM"),
                DateTimeFormatter.ofPattern("dd.MM"),
                DateTimeFormatter.ofPattern("d-M"),
                DateTimeFormatter.ofPattern("d.M"),
                DateTimeFormatter.ofPattern("dd-MMM"),
                DateTimeFormatter.ofPattern("d-MMM"),
                DateTimeFormatter.ofPattern("dd-MMMM"),
                DateTimeFormatter.ofPattern("d-MMMM"),
                DateTimeFormatter.ofPattern("dd-MM-yy"),
                DateTimeFormatter.ofPattern("dd.MM.yy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("d-M-yy"),
                DateTimeFormatter.ofPattern("d.M.yy"),
                DateTimeFormatter.ofPattern("d-M-yyyy"),
                DateTimeFormatter.ofPattern("d.M.yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("MMM-dd"),
                DateTimeFormatter.ofPattern("MMMM-dd")
        );

        for (DateTimeFormatter fmt : formatters) {
            try {
                if (!dateStr.contains("-20") && !dateStr.contains(".20") && !dateStr.contains("/20")) {
                    String currentYear = String.valueOf(LocalDate.now().getYear());
                    String fullDateStr = dateStr + "-" + currentYear;
                    List<DateTimeFormatter> fullFormatters = Arrays.asList(
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                            DateTimeFormatter.ofPattern("d-M-yyyy"),
                            DateTimeFormatter.ofPattern("d.M.yyyy"),
                            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
                            DateTimeFormatter.ofPattern("d-MMM-yyyy"),
                            DateTimeFormatter.ofPattern("dd-MMMM-yyyy"),
                            DateTimeFormatter.ofPattern("d-MMMM-yyyy")
                    );
                    for (DateTimeFormatter fullFmt : fullFormatters) {
                        try {
                            LocalDate d = LocalDate.parse(fullDateStr, fullFmt.withLocale(Locale.ENGLISH));
                            return String.format("%02d", d.getMonthValue());
                        } catch (Exception ignored) {}
                    }
                }
                LocalDate d = LocalDate.parse(dateStr, fmt.withLocale(Locale.ENGLISH));
                return String.format("%02d", d.getMonthValue());
            } catch (Exception ignored) {}
        }
        return "";
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
        
        
        JFXButton barcode = btn("");
        barcode.setGraphic(new ImageView(new Image(getClass().getResource("barcode.png").toExternalForm())));
        barcode.setOnAction(e -> {
            //Open BarCode Table
            
  try {
      
    Stage stg = new Stage();
    
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("TableViewer.fxml"));
    root.setStyle(
                "-fx-font-family: 'Cairo SemiBold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #333333;"
            );
    Scene sce = new Scene(root);
    
    //////////////////////////////Theme////////////////////////////////
    BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
    String themooo=bis.readLine();
    bis.close();
    // Check if CSS exists
    URL cssUrl = getClass().getResource(themooo);
    if (cssUrl == null) {
        System.err.println("ERROR: cupertino-dark.css not found in same package as controller!");
    } else {
        // Apply theme to both scene and root (ensures it always works)
        String cssPath = cssUrl.toExternalForm();
        sce.getStylesheets().add(cssPath);
        root.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stg.setTitle("❤❤❤");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.centerOnScreen();
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
      
  }
      
  
  catch (Exception gg) {}
            
            
        });
        
        
        JFXButton download = btn("");
        download.setGraphic(new ImageView(new Image(getClass().getResource("download.png").toExternalForm())));
        download.setOnAction(e -> {
            //Pull data
            
            //Alert ......
            
            Alert alert=new Alert (AlertType.INFORMATION);
            alert.setTitle("Date Alert");
            alert.setHeaderText("Enter Date:");
            alert.setContentText("Please enter date you wanna pull its data here to continue.");
            JFXTextField tfff=new JFXTextField ();
            tfff.setPromptText("Write Date Here");
            alert.setGraphic(tfff);
            // Load custom style
DialogPane dialogPane = alert.getDialogPane();
//dialogPane.setMinSize(600, 300);
  //////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
  if (themooo.equals("cupertino-dark.css")) {
      //Dark
      dialogPane.getStylesheets().add(
      getClass().getResource("cupertino-dark.css").toExternalForm());
  }
  else {
      //Light
      dialogPane.getStylesheets().add(
      getClass().getResource("cupertino-light.css").toExternalForm());
  }
  }
  catch (Exception re) {}
  ////////////////////////////////////////////////////////////////////////////////////////////////////// 
  alert.showAndWait();
  String datoo=tfff.getText().trim();
  System.out.println(datoo);
  saveDataByDate(datoo);    
            
        });
        
    

        ToolBar toolbar = new ToolBar(loadBtn, saveBtn, addRowBtn, addManyRowsBtn, deleteBtn, exportBtn,barcode,download);

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

        // Handle key shortcuts (Ctrl+V, Ctrl+D, Delete)
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
        stage.setTitle("😎 BONUS PERSONAL LAUNDRY");
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
            pasteFromClipboardd();
        } else if (e.isControlDown() && e.getCode() == KeyCode.D) {
            e.consume();
            fillDownFromFocusedCell();
        } else if (e.getCode() == KeyCode.DELETE) {
            e.consume();
            clearSelectedCells();
        }
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

        for (int r = 0; r < lines.length; r++) {
            String[] parts = lines[r].split("\t", -1);
            int targetRow = baseRow + r;

            while (targetRow >= data.size()) {
                RowData nr = new RowData();
                attachReactiveBehavior(nr);
                data.add(nr);
            }
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
            for (int r = baseRow; r < baseRow + lines.length && r < data.size(); r++) {
                for (int c = baseCol; c < baseCol + lines[0].split("\t", -1).length && c < table.getColumns().size(); c++) {
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
        if ("process".equals(colName) || "name_wash".equals(colName)) {
            String process = row.getProperty("process").get();
            String nameWash = row.getProperty("name_wash").get();
            String recipeTime = getRecipeTimeByProcess(process, nameWash);
            row.setProperty("recipe_time", recipeTime);
        }
        if ("date".equals(colName)) {
            String monthVal = parseMonthNumber(row.getProperty("date").get());
            if (!monthVal.isEmpty()) row.setProperty("month", monthVal);
        }

        if (recalcNow || "start_time".equals(colName) || "end_time".equals(colName)
                || "recipe_time".equals(colName) || "date".equals(colName)
                || "code".equals(colName) || "process".equals(colName) || "name_wash".equals(colName)) {
            calculateRow(row);
        }
    }

    private void attachReactiveBehavior(RowData row) {
        row.getProperty("code").addListener((o, oldV, newV) -> {
            String name = getNameByCode(newV == null ? "" : newV.trim());
            row.setProperty("name", name);
            calculateRow(row);
            scheduleSaveAndRecalc();
        });
        row.getProperty("name_wash").addListener((o, oldV, newV) -> {
            String process = row.getProperty("process").get() == null ? "" : row.getProperty("process").get().trim();
            String rt = getRecipeTimeByProcess(process, newV == null ? "" : newV.trim());
            row.setProperty("recipe_time", rt);
            calculateRow(row);
            scheduleSaveAndRecalc();
        });
        row.getProperty("process").addListener((o, oldV, newV) -> {
            String nameWash = row.getProperty("name_wash").get() == null ? "" : row.getProperty("name_wash").get().trim();
            String rt = getRecipeTimeByProcess(newV == null ? "" : newV.trim(), nameWash);
            row.setProperty("recipe_time", rt);
            calculateRow(row);
            scheduleSaveAndRecalc();
        });
        String[] drivers = {"start_time", "end_time", "recipe_time", "date", "code"};
        for (String k : drivers) {
            row.getProperty(k).addListener((o, oldV, newV) -> {
                if ("date".equals(k)) {
                    String m = parseMonthNumber(newV == null ? "" : newV.trim());
                    if (!m.isEmpty()) row.setProperty("month", m);
                }
                calculateRow(row);
                scheduleSaveAndRecalc();
            });
        }
    }

    private void calculateRow(RowData row) {
        String start = row.getProperty("start_time").get();
        String end = row.getProperty("end_time").get();
        String recipeStr = row.getProperty("recipe_time").get();

        if (start == null) start = "";
        if (end == null) end = "";

        if (start.isEmpty() || end.isEmpty()) {
            row.setProperty("hour", "");
            row.setProperty("minute", "");
            row.setProperty("fark", "");
            row.setProperty("percentage", "");
            return;
        }

        try {
            LocalTime startTime = LocalTime.parse(start.toUpperCase(), TIME_FORMAT);
            LocalTime endTime = LocalTime.parse(end.toUpperCase(), TIME_FORMAT);

            long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
            if (minutes < 0) {
                minutes += 1440L;
            }
            row.setProperty("hour", String.format("%d:%02d", minutes / 60, minutes % 60));
            row.setProperty("minute", String.valueOf(minutes));

            int recipe = 0;
            try {
                recipe = Integer.parseInt(recipeStr == null ? "0" : recipeStr.trim());
            } catch (NumberFormatException ignore) {}
            long fark = Math.max(0, minutes - recipe);
            row.setProperty("fark", String.valueOf(fark));

            double perc = (minutes > 0 && recipe > 0) ? (100.0 * recipe / minutes) : 0;
            row.setProperty("percentage", String.format(Locale.ENGLISH, "%.1f", perc));

        } catch (DateTimeParseException e) {
            showError("Invalid time format! Please use format like 8:30 AM or 12:15 PM");
            row.setProperty("start_time", "");
            row.setProperty("end_time", "");
            row.setProperty("hour", "");
            row.setProperty("minute", "");
            row.setProperty("fark", "");
            row.setProperty("percentage", "");
        }
    }

    private void recalcAllFormulas() {
        for (RowData r : data) {
            String date = r.getProperty("date").get();
            String code = r.getProperty("code").get();

            if (date == null || date.isEmpty() || code == null || code.isEmpty()) {
                r.setProperty("total_time_recipe", "");
                r.setProperty("total_time", "");
                r.setProperty("percentage_day", "");
                continue;
            }

            double sumRecipe = 0;
            double sumMinutes = 0;

            for (RowData other : data) {
                if (date.equals(other.getProperty("date").get()) &&
                        code.equals(other.getProperty("code").get())) {
                    try {
                        String recipeStr = z(other.getProperty("recipe_time").get());
                        if (!recipeStr.isEmpty()) {
                            sumRecipe += Double.parseDouble(recipeStr);
                        }
                    } catch (NumberFormatException ignore) {}
                    try {
                        String minuteStr = z(other.getProperty("minute").get());
                        if (!minuteStr.isEmpty()) {
                            sumMinutes += Double.parseDouble(minuteStr);
                        }
                    } catch (NumberFormatException ignore) {}
                }
            }

            r.setProperty("total_time_recipe", String.format(Locale.ENGLISH, "%.0f", sumRecipe));
            r.setProperty("total_time", String.format(Locale.ENGLISH, "%.0f", sumMinutes));

            String timeMachineStr = z(r.getProperty("time_machine").get());
            double timeMachine = 0;
            try {
                if (!timeMachineStr.isEmpty()) {
                    timeMachine = Double.parseDouble(timeMachineStr);
                }
            } catch (NumberFormatException ignore) {}
            String percDay = (sumRecipe > 0 && timeMachine > 0) ? 
                            String.format(Locale.ENGLISH, "%.1f", (100.0 * sumRecipe / timeMachine)) : "";
            r.setProperty("percentage_day", percDay);
        }
    }

    private static String z(String s) {
        return s == null ? "" : s.trim();
    }

    private void exportToExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("BONUS_PERSONAL_LAUNDRY");

            // === Styles ===
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setFontHeightInPoints((short) 11);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle numericStyle = workbook.createCellStyle();
            numericStyle.setAlignment(HorizontalAlignment.RIGHT);
            numericStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
            numericStyle.setBorderBottom(BorderStyle.THIN);
            numericStyle.setBorderTop(BorderStyle.THIN);
            numericStyle.setBorderLeft(BorderStyle.THIN);
            numericStyle.setBorderRight(BorderStyle.THIN);

            CellStyle pctStyle = workbook.createCellStyle();
            pctStyle.setAlignment(HorizontalAlignment.RIGHT);
            pctStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0"));
            pctStyle.setBorderBottom(BorderStyle.THIN);
            pctStyle.setBorderTop(BorderStyle.THIN);
            pctStyle.setBorderLeft(BorderStyle.THIN);
            pctStyle.setBorderRight(BorderStyle.THIN);

            CellStyle timeHMM = workbook.createCellStyle();
            timeHMM.setAlignment(HorizontalAlignment.RIGHT);
            timeHMM.setDataFormat(workbook.createDataFormat().getFormat("h:mm"));
            timeHMM.setBorderBottom(BorderStyle.THIN);
            timeHMM.setBorderTop(BorderStyle.THIN);
            timeHMM.setBorderLeft(BorderStyle.THIN);
            timeHMM.setBorderRight(BorderStyle.THIN);

            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setBorderBottom(BorderStyle.THIN);
            textStyle.setBorderTop(BorderStyle.THIN);
            textStyle.setBorderLeft(BorderStyle.THIN);
            textStyle.setBorderRight(BorderStyle.THIN);

            CellStyle zebraLight = workbook.createCellStyle();
            zebraLight.cloneStyleFrom(textStyle);
            zebraLight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            zebraLight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle zebraNumericLight = workbook.createCellStyle();
            zebraNumericLight.cloneStyleFrom(numericStyle);
            zebraNumericLight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            zebraNumericLight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle zebraPctLight = workbook.createCellStyle();
            zebraPctLight.cloneStyleFrom(pctStyle);
            zebraPctLight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            zebraPctLight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle zebraTimeLight = workbook.createCellStyle();
            zebraTimeLight.cloneStyleFrom(timeHMM);
            zebraTimeLight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            zebraTimeLight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle totalStyle = workbook.createCellStyle();
            XSSFFont totalFont = workbook.createFont();
            totalFont.setBold(true);
            totalFont.setColor(IndexedColors.WHITE.getIndex());
            totalStyle.setFont(totalFont);
            totalStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            totalStyle.setAlignment(HorizontalAlignment.RIGHT);
            totalStyle.setBorderBottom(BorderStyle.THIN);
            totalStyle.setBorderTop(BorderStyle.THIN);
            totalStyle.setBorderLeft(BorderStyle.THIN);
            totalStyle.setBorderRight(BorderStyle.THIN);

            // === Header Row ===
            Row headerRow = sheet.createRow(0);
            for (int j = 0; j < table.getColumns().size(); j++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(j);
                cell.setCellValue(table.getColumns().get(j).getText());
                cell.setCellStyle(headerStyle);
            }
            sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, table.getColumns().size() - 1));

            // === Column Indexes ===
            final int COL_DATE = 0;
            final int COL_MONTH = 1;
            final int COL_SHIFT = 2;
            final int COL_CODE = 3;
            final int COL_NAME = 4;
            final int COL_NAME_WASH = 5;
            final int COL_MACHINE_NUMBER = 6;
            final int COL_TIME_MACHINE = 7;
            final int COL_PROCESS = 8;
            final int COL_START_TIME = 9;
            final int COL_END_TIME = 10;
            final int COL_HOUR = 11;
            final int COL_MINUTE = 12;
            final int COL_RECIPE_TIME = 13;
            final int COL_FARK = 14;
            final int COL_PERCENTAGE = 15;
            final int COL_TOTAL_TIME_RECIPE = 16;
            final int COL_TOTAL_TIME = 17;
            final int COL_PERCENTAGE_DAY = 18;

            // === Data Rows ===
            int rowIndex = 1;
            for (int i = 0; i < table.getItems().size(); i++) {
                RowData rd = table.getItems().get(i);
                Row row = sheet.createRow(rowIndex);
                boolean isEvenRow = (i % 2 == 0);

                for (int j = 0; j < table.getColumns().size(); j++) {
                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(j);
                    String colName = table.getColumns().get(j).getText().toLowerCase();
                    boolean isComputed = j == COL_HOUR || j == COL_MINUTE || j == COL_FARK || j == COL_PERCENTAGE
                            || j == COL_TOTAL_TIME_RECIPE || j == COL_TOTAL_TIME || j == COL_PERCENTAGE_DAY;

                    if (isComputed) {
                        String A = CellReference.convertNumToColString(COL_DATE);
                        String D = CellReference.convertNumToColString(COL_CODE);
                        String H = CellReference.convertNumToColString(COL_TIME_MACHINE);
                        String J = CellReference.convertNumToColString(COL_START_TIME);
                        String K = CellReference.convertNumToColString(COL_END_TIME);
                        String L = CellReference.convertNumToColString(COL_HOUR);
                        String M = CellReference.convertNumToColString(COL_MINUTE);
                        String N = CellReference.convertNumToColString(COL_RECIPE_TIME);
                        String P = CellReference.convertNumToColString(COL_PERCENTAGE);
                        String Q = CellReference.convertNumToColString(COL_TOTAL_TIME_RECIPE);
                        String R = CellReference.convertNumToColString(COL_TOTAL_TIME);

                        switch (j) {
                            case COL_MINUTE:
                                String f1 = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),MOD(TIMEVALUE(%s%d)-TIMEVALUE(%s%d),1)*1440,\"\")",
                                        J, rowIndex + 1, K, rowIndex + 1, K, rowIndex + 1, J, rowIndex + 1);
                                cell.setCellFormula(f1);
                                cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);
                                break;
                            case COL_HOUR:
                                String f2 = String.format("IF(%s%d<>\"\",%s%d/1440,\"\")", M, rowIndex + 1, M, rowIndex + 1);
                                cell.setCellFormula(f2);
                                cell.setCellStyle(isEvenRow ? zebraTimeLight : timeHMM);
                                break;
                            case COL_FARK:
                                String f3 = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),MAX(0,%s%d-%s%d),\"\")",
                                        M, rowIndex + 1, N, rowIndex + 1, M, rowIndex + 1, N, rowIndex + 1);
                                cell.setCellFormula(f3);
                                cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);
                                break;
                            case COL_PERCENTAGE:
                                String f4 = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,\"\")",
                                        M, rowIndex + 1, N, rowIndex + 1, N, rowIndex + 1, M, rowIndex + 1);
                                cell.setCellFormula(f4);
                                cell.setCellStyle(isEvenRow ? zebraPctLight : pctStyle);
                                break;
                            case COL_TOTAL_TIME_RECIPE:
                                String f5 = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d),\"\")",
                                        A, rowIndex + 1, D, rowIndex + 1, N, N, A, A, A, rowIndex + 1, D, D, D, rowIndex + 1);
                                cell.setCellFormula(f5);
                                cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);
                                break;
                            case COL_TOTAL_TIME:
                                String f6 = String.format("IF(AND(%s%d<>\"\",%s%d<>\"\"),SUMIFS(%s:%s,%s:%s,%s%d,%s:%s,%s%d),\"\")",
                                        A, rowIndex + 1, D, rowIndex + 1, M, M, A, A, A, rowIndex + 1, D, D, D, rowIndex + 1);
                                cell.setCellFormula(f6);
                                cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);
                                break;
                            case COL_PERCENTAGE_DAY:
                                String f7 = String.format("IF(AND(%s%d>0,%s%d>0),%s%d/%s%d*100,\"\")",
                                        Q, rowIndex + 1, H, rowIndex + 1, Q, rowIndex + 1, H, rowIndex + 1);
                                cell.setCellFormula(f7);
                                cell.setCellStyle(isEvenRow ? zebraPctLight : pctStyle);
                                break;
                        }
                    } else {
                        String val = table.getColumns().get(j).getCellData(i) == null ? "" : table.getColumns().get(j).getCellData(i).toString();
                        if (val.matches("\\d+(\\.\\d+)?")) {
                            try {
                                double num = Double.parseDouble(val);
                                cell.setCellValue(num);
                                cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(val);
                                cell.setCellStyle(isEvenRow ? zebraLight : textStyle);
                            }
                        } else {
                            cell.setCellValue(val);
                            cell.setCellStyle(isEvenRow ? zebraLight : textStyle);
                        }
                    }
                }
                row.setHeight((short)300);
                rowIndex++;
            }

            // === Totals Row ===
            Row totalRow = sheet.createRow(rowIndex);
            org.apache.poi.ss.usermodel.Cell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("TOTAL OF QUANTITIES");
            totalLabelCell.setCellStyle(totalStyle);

            for (int j = 1; j < table.getColumns().size(); j++) {
                org.apache.poi.ss.usermodel.Cell cell = totalRow.createCell(j);
                if (j == COL_RECIPE_TIME || j == COL_MINUTE || j == COL_TOTAL_TIME_RECIPE || j == COL_TOTAL_TIME) {
                    String colLetter = CellReference.convertNumToColString(j);
                    String formula = String.format("SUM(%s2:%s%d)", colLetter, colLetter, rowIndex);
                    cell.setCellFormula(formula);
                    cell.setCellStyle(totalStyle);
                } else {
                    cell.setCellValue("");
                    cell.setCellStyle(totalStyle);
                }
            }

            // === Sheet Formatting ===
            sheet.createFreezePane(0, 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                sheet.autoSizeColumn(j);
            }

            // === Pivot Table Sheet ===
            XSSFSheet pivotSheet = workbook.createSheet("Bonus Pivot Table");
            AreaReference source = new AreaReference(
                    new CellReference(0, 0),
                    new CellReference(rowIndex - 1, COL_PERCENTAGE_DAY),
                    workbook.getSpreadsheetVersion()
            );
            CellReference pivotPosition = new CellReference(2, 0);
            Row titleRow = pivotSheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Laundry3 - Bonus Summary");
            titleCell.setCellStyle(headerStyle);
            pivotSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            XSSFPivotTable pivotTable = pivotSheet.createPivotTable(source, pivotPosition, sheet);
            pivotTable.addReportFilter(COL_MONTH);
            pivotTable.addReportFilter(COL_DATE);
            pivotTable.addRowLabel(COL_SHIFT);
            pivotTable.addRowLabel(COL_CODE);
            pivotTable.addRowLabel(COL_NAME);
            pivotTable.addColumnLabel(DataConsolidateFunction.AVERAGE, COL_PERCENTAGE_DAY, "Avg % Day");
            Row pivotHeaderRow = pivotSheet.getRow(2);
            if (pivotHeaderRow != null) {
                for (org.apache.poi.ss.usermodel.Cell cell : pivotHeaderRow) {
                    cell.setCellStyle(headerStyle);
                }
            }
            for (int i = 0; i < 6; i++) {
                pivotSheet.autoSizeColumn(i);
            }

            // === Save File ===
            FileChooser dialog = new FileChooser();
            dialog.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
            dialog.setInitialFileName("BONUS_PERSONAL_LAUNDRY");
            dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File dialogResult = dialog.showSaveDialog(null);
            if (dialogResult == null) return;

            try (FileOutputStream out = new FileOutputStream(dialogResult)) {
                workbook.write(out);
            }
            workbook.close();
            Desktop.getDesktop().open(dialogResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
private void saveDataByDate(String selectedDate) {

    if (selectedDate == null || selectedDate.trim().isEmpty()) {
        Notifications.create()
            .title("Error")
            .text("❌ Please enter a valid date.")
            .hideAfter(javafx.util.Duration.seconds(5))
            .position(Pos.CENTER)
            .showError();
        return;
    }

    selectedDate = selectedDate.trim();

    String userSuffix = (Alpha_PlannerController.seluserr == null) ? "default" : Alpha_PlannerController.seluserr;
    String bonusTable = "Bonus_" + userSuffix;
    String barcodeTable = "BarCode_P_" + userSuffix;

    System.out.println("🔍 Using tables: " + barcodeTable + " → " + bonusTable);
    System.out.println("🔍 Searching for date=[" + selectedDate + "]");

    try (Connection conn = connect()) {
        conn.setAutoCommit(false);

        // مسح البيانات القديمة من Bonus فقط
        try (PreparedStatement del = conn.prepareStatement(
                "DELETE FROM " + bonusTable + " WHERE TRIM(date) = ?")) {
            del.setString(1, selectedDate);
            int deleted = del.executeUpdate();
            System.out.println("🗑 Deleted rows from " + bonusTable + ": " + deleted);
        }

        // الأعمدة اللي عايز تجيبها من BarCode_P_
        final String[] BONUS_COLUMNS = {
            "date", "shift", "code", "name", "machine_number",
            "name_wash", "process", "start_time", "end_time"
        };

        // SELECT من BarCode_P_ حسب التاريخ
        String selectSql = "SELECT " + String.join(",", BONUS_COLUMNS) +
                           " FROM " + barcodeTable +
                           " WHERE TRIM(date) = ?";

        String insertSql = "INSERT INTO " + bonusTable +
                " (date,shift,code,name,machine_number,name_wash,process,start_time,end_time) " +
                " VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement psSelect = conn.prepareStatement(selectSql);
             PreparedStatement psInsert = conn.prepareStatement(insertSql)) {

            psSelect.setString(1, selectedDate);

            int rowCount = 0;
            try (ResultSet rs = psSelect.executeQuery()) {
                while (rs.next()) {
                    rowCount++;
                    System.out.println("✅ Found row: " +
                            rs.getString("date") + " | " +
                            rs.getString("shift") + " | " +
                            rs.getString("code") + " | " +
                            rs.getString("name"));

                    for (int i = 0; i < BONUS_COLUMNS.length; i++) {
                        psInsert.setString(i + 1, rs.getString(BONUS_COLUMNS[i]));
                    }
                    psInsert.addBatch();
                }
            }

            if (rowCount == 0) {
                conn.rollback(); // الغي التغييرات لو مفيش داتا
                Notifications.create()
                    .title("Error")
                    .text("❌ No data found for date: " + selectedDate)
                    .hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER)
                    .showError();
                return;
            }

            psInsert.executeBatch();
            conn.commit();

            Notifications.create()
                .title("Successful Query")
                .text("📦 Data copied from " + barcodeTable + " → " + bonusTable +
                      " for date [" + selectedDate + "], rows inserted: " + rowCount)
                .hideAfter(javafx.util.Duration.seconds(5))
                .position(Pos.CENTER)
                .showInformation();

            Platform.runLater(loadBtn::fire);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        Notifications.create()
            .title("Error")
            .text("❌ Something went wrong.")
            .hideAfter(javafx.util.Duration.seconds(5))
            .position(Pos.CENTER)
            .showError();
    }
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
             ResultSet rs = st.executeQuery("SELECT * FROM Bonus_" + Alpha_PlannerController.seluserr)) {
            while (rs.next()) {
                RowData row = new RowData();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.setProperty(rs.getMetaData().getColumnName(i),
                            rs.getString(i) == null ? "" : rs.getString(i));
                }
                attachReactiveBehavior(row);
                calculateRow(row);
                data.add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveData() {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            conn.createStatement().execute("DELETE FROM Bonus_" + Alpha_PlannerController.seluserr);
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Bonus_" + Alpha_PlannerController.seluserr + " (date,month,shift,code,name,name_wash," +
                            "machine_number,time_machine,process,start_time,end_time," +
                            "hour,minute,recipe_time,fark,percentage," +
                            "total_time_recipe,total_time,percentage_day) VALUES (?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?)"
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

    private String getRecipeTimeByProcess(String process, String nameWash) {
        String normalizedProcess = process == null ? "" : process.toLowerCase().replace("_", "").replace(" ", "");
        String normalizedWash = nameWash == null ? "" : nameWash.toLowerCase().replace("_", "").replace(" ", "");

        if (normalizedProcess.isEmpty() || normalizedProcess.equals("rigid")) {
            return getFullRecipeTime(normalizedWash);
        }

        if (normalizedProcess.equals("moonwash") || normalizedProcess.equals("moonwashfoam")) {
            return "120";
        }
        if (normalizedProcess.equals("rinse")) {
            return "60";
        }

        String queryTimer = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
                           "FROM Timer " +
                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";

        String queryThreeShots = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
                                "FROM Timer_Three_Shots " +
                                "WHERE LOWER(REPLACE(Name, '_', '')) = ?";

        String queryPilot = "SELECT Shot, Time_In_Min " +
                           "FROM Timer_Pilot " +
                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";

        try (Connection conn = connect2()) {
            if (normalizedProcess.equals("tint")) {
                double total = 0.0;
                boolean found = false;

                try (PreparedStatement ps = conn.prepareStatement(queryTimer)) {
                    ps.setString(1, normalizedWash);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String shot = rs.getString("Shot");
                            if ("2".equals(shot)) {
                                String updated = rs.getString("Time_In_Min_Updated");
                                String timeStr = "Hasnot_Updated_Yet".equals(updated)
                                        ? rs.getString("Time_In_Min")
                                        : updated;
                                if (timeStr != null && !timeStr.trim().isEmpty()) {
                                    total += Double.parseDouble(timeStr);
                                    found = true;
                                }
                            }
                        }
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
                    ps.setString(1, normalizedWash);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String shot = rs.getString("Shot");
                            if ("3".equals(shot)) {
                                String updated = rs.getString("Time_In_Min_Updated");
                                String timeStr = "Hasnot_Updated_Yet".equals(updated)
                                        ? rs.getString("Time_In_Min")
                                        : updated;
                                if (timeStr != null && !timeStr.trim().isEmpty()) {
                                    total += Double.parseDouble(timeStr);
                                    found = true;
                                }
                            }
                        }
                    }
                }

                if (!found) {
                    try (PreparedStatement ps = conn.prepareStatement(queryPilot)) {
                        ps.setString(1, normalizedWash);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                String shot = rs.getString("Shot");
                                if ("2".equals(shot) || "3".equals(shot)) {
                                    String timeStr = rs.getString("Time_In_Min");
                                    if (timeStr != null && !timeStr.trim().isEmpty()) {
                                        total += Double.parseDouble(timeStr);
                                        found = true;
                                    }
                                }
                            }
                        }
                    }
                }

                if (found) return String.valueOf(total);
            }

            return getFullRecipeTime(normalizedProcess);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(ExcelLikeTableApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    private String getFullRecipeTime(String normalizedName) {
        if (normalizedName.isEmpty()) {
            return "";
        }

        String queryTimer = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
                           "FROM Timer " +
                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";

        String queryThreeShots = "SELECT Shot, Time_In_Min_Updated, Time_In_Min " +
                                "FROM Timer_Three_Shots " +
                                "WHERE LOWER(REPLACE(Name, '_', '')) = ?";

        String queryPilot = "SELECT Shot, Time_In_Min " +
                           "FROM Timer_Pilot " +
                           "WHERE LOWER(REPLACE(Name, '_', '')) = ?";

        try (Connection conn = connect2()) {
            try (PreparedStatement ps = conn.prepareStatement(queryTimer)) {
                ps.setString(1, normalizedName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String updated = rs.getString("Time_In_Min_Updated");
                        return "Hasnot_Updated_Yet".equals(updated)
                                ? rs.getString("Time_In_Min")
                                : updated;
                    }
                }
            }

            double totalTime = 0.0;
            boolean hasShot1 = false, hasShot2 = false;

            try (PreparedStatement ps = conn.prepareStatement(queryThreeShots)) {
                ps.setString(1, normalizedName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String shot = rs.getString("Shot");
                        String updated = rs.getString("Time_In_Min_Updated");
                        String timeStr = "Hasnot_Updated_Yet".equals(updated)
                                ? rs.getString("Time_In_Min")
                                : updated;

                        if (timeStr != null && !timeStr.trim().isEmpty()) {
                            double t = Double.parseDouble(timeStr);
                            if ("1".equals(shot)) {
                                totalTime += t;
                                hasShot1 = true;
                            } else if ("2".equals(shot)) {
                                totalTime += t;
                                hasShot2 = true;
                            }
                        }
                    }
                }
            }

            if (hasShot1) return String.valueOf(totalTime);

            try (PreparedStatement ps = conn.prepareStatement(queryPilot)) {
                ps.setString(1, normalizedName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String timeStr = rs.getString("Time_In_Min");
                        return timeStr;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(ExcelLikeTableApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public static class RowData {
        private final SimpleStringProperty[] props;
        private final String[] colNames = {
                "date", "month", "shift", "code", "name", "name_wash",
                "machine_number", "time_machine", "process", "start_time",
                "end_time", "hour", "minute", "recipe_time", "fark",
                "percentage", "total_time_recipe", "total_time", "percentage_day"
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