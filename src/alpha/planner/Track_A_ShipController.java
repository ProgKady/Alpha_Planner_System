
package alpha.planner;

import static alpha.planner.LogIn_GUI_Controller.selectedpositionn;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class Track_A_ShipController implements Initializable {

   
  Connection conn = null;
  
  ResultSet rs = null;
  
  PreparedStatement pst = null;
  
   @FXML
    private JFXTextField poo;
     


    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    void viewmapaction(ActionEvent event) throws FileNotFoundException, IOException {
        ObservableList<ObservableList<String>> rows = table.getItems();

        if (rows == null || rows.isEmpty()) {
            System.out.println("No rows found in the table!");
            return;
        }

        VBox mapContainer = new VBox(20);
        mapContainer.setPadding(new Insets(20));
        mapContainer.setAlignment(Pos.TOP_LEFT);
        mapContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f0f0f0);");

        // EXPORT BUTTON
        Button exportImageButton = new Button("📷 Export as Image");
        exportImageButton.setStyle("-fx-background-color: #2d89ef; -fx-text-fill: white; -fx-font-weight: bold;");
        exportImageButton.setOnAction(e -> {
            WritableImage snapshot = mapContainer.snapshot(new SnapshotParameters(), null);
            File file = new File(System.getProperty("user.home")+"\\Desktop\\shipment_map.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exported");
                alert.setHeaderText(null);
                alert.setContentText("✅ Image saved to:\n" + System.getProperty("user.home")+"\\Desktop\\shipment_map.png");
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        HBox header = new HBox(exportImageButton);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setPadding(new Insets(10, 0, 10, 0));
        mapContainer.getChildren().add(header);

        boolean leftSide = true;

        for (int i = 0; i < rows.size(); i++) {
            ObservableList<String> row = rows.get(i);
            String notes = row.size() > 4 ? row.get(4) : "";
            String date = row.size() > 1 ? row.get(1) : "Unknown Date";

            if (!notes.contains("From:") || !notes.contains("To:") || !notes.contains("Accepted")) continue;

            String from = extract(notes, "From:");
            String to = extract(notes, "To:");
            String po = extract(notes, "PO:");
            String style = extract(notes, "Style:");
            String wash = extract(notes, "Wash Name:");
            String qua = extract(notes, "Quantity:");

            HBox dotRow = new HBox(10);
            dotRow.setAlignment(Pos.CENTER_LEFT);

            Circle dot = new Circle(10, Color.DARKBLUE);
            dot.setStroke(Color.DARKSLATEGRAY);
            dot.setStrokeWidth(2);

            VBox infoBox = new VBox(5);
            infoBox.getChildren().addAll(
                styledText("📅 Date: " + date),
                styledText("📦 From: " + from + " → To: " + to),
                styledText("✨ PO: " + po + " | Style: " + style),
                styledText("💡 Wash: " + wash),
                styledText("🛒 Quantity: " + qua)
            );

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            if (leftSide) {
                dotRow.getChildren().addAll(dot, infoBox, spacer);
            } else {
                dotRow.getChildren().addAll(spacer, infoBox, dot);
            }

            mapContainer.getChildren().add(dotRow);

            // Zigzag line
            if (i < rows.size() - 1) {
                Line zigLine = new Line(0, 0, 30, 30);
                zigLine.setStrokeWidth(2);
                zigLine.setStroke(Color.GREY);

                VBox lineBox = new VBox(zigLine);
                lineBox.setAlignment(Pos.CENTER);
                mapContainer.getChildren().add(lineBox);
            }
            else {break;}

            leftSide = !leftSide;
        }

        ScrollPane scrollPane = new ScrollPane(mapContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");

        Stage stage = new Stage();
        stage.setTitle("📍 Shipment Path Map");
        Scene sce=new Scene (scrollPane,600,700);
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
        scrollPane.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
        stage.setScene(sce);
        stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        stage.setResizable(false);
        stage.show();
    }

    private String extract(String text, String label) {
        int start = text.indexOf(label);
        if (start == -1) return "";
        start += label.length();
        int end = text.indexOf(",", start);
        if (end == -1) end = text.length();
        return text.substring(start, end).trim();
    }

    private Text styledText(String content) {
        Text text = new Text(content);
        text.setStyle("-fx-font-size: 14px; -fx-fill: #333;");
        return text;
    }



    
    
    @FXML
    void exportaction(ActionEvent event) throws FileNotFoundException, IOException {

         /////////////////////////////////////////////////////////////////////////
    
        
        Workbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Tracked_Shipment_Data");
        Row row = spreadsheet.createRow(0);
        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }
        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }
        FileChooser dialog = new FileChooser();
        dialog.setInitialDirectory(new File(System.getProperty("user.home")+"\\Desktop"));
        dialog.setInitialFileName("Tracked_Shipment_Data");
        dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", new String[] { "*.xlsx" }));
        File dialogResult = dialog.showSaveDialog(null);
        String filePath = dialogResult.getAbsolutePath().toString();
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        Desktop desk=Desktop.getDesktop();
        desk.open(new File (filePath));
        
        /////////////////////////////////////////////////////////////////////////
        
        
    }

   

    @FXML
    void trackaction(ActionEvent event) {
 
        table.getColumns().clear();
ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

String positionValue = poo.getText();

try {
    // استخدام LIKE مع % لجلب كل ما يحتوي على القيمة
    String sql = "SELECT * FROM Audit WHERE Notes LIKE ?";
    pst = conn.prepareStatement(sql);
    pst.setString(1, "%" + positionValue + "%");
    rs = pst.executeQuery();

    // إنشاء الأعمدة تلقائيًا
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // تعبئة البيانات
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getString(i));
        }
        data.add(row);
    }

    table.setItems(data);

} catch (Exception e) {
    e.printStackTrace();
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// تطبيق فلتر للجدول
TableFilter<ObservableList<String>> filter = new TableFilter<>(table);

        
    }
  
    
   
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
        this.conn = db.java_db();
        
       
        
    }    
    
}
