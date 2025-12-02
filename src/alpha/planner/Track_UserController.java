
package alpha.planner;

import static alpha.planner.LogIn_GUI_Controller.selectedpositionn;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
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
public class Track_UserController implements Initializable {

   
  Connection conn = null;
  
  ResultSet rs = null;
  
  PreparedStatement pst = null;
  
  
  
   @FXML
    private ComboBox<String> section;
   
   @FXML
    private ImageView avatar;


    @FXML
    private ComboBox<String> user;

    @FXML
    private TableView<ObservableList<String>> table;

    
    
    
    
    
    @FXML
    void exportaction(ActionEvent event) throws FileNotFoundException, IOException {

         /////////////////////////////////////////////////////////////////////////
    
        
        Workbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Tracked_User_Data");
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
        dialog.setInitialFileName("Tracked_User_Data");
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

String positionValue = section.getSelectionModel().getSelectedItem().toString();
String usernameValue = user.getSelectionModel().getSelectedItem().toString();


/////////////////////////////////////////////////////////////////////////////////////////



// === Avatar ImageView ===
avatar.setFitWidth(50);
avatar.setFitHeight(50);
avatar.setPreserveRatio(false); // يخلي الصورة تتقصّ عشان تبقى مربعة

// Clip دائري للصورة (يخليها مدورة)
Circle clip = new Circle(25, 25, 25); 
avatar.setClip(clip);

// Border دائري (أسود + سميك)
Circle border = new Circle(25, 25, 27); 
border.setStyle(
    "-fx-fill: transparent;" +
    "-fx-stroke: black;" +
    "-fx-stroke-width: 4;" // تخانة البوردر
);

// === Load avatar from DB ===
try {
    String sql = "SELECT Avatar FROM USERS WHERE User = ? AND Position = ?";
    pst = conn.prepareStatement(sql);
    pst.setString(1, user.getSelectionModel().getSelectedItem().toString());
    pst.setString(2, section.getSelectionModel().getSelectedItem().toString());
    rs = pst.executeQuery();

    if (rs.next()) {
        try (InputStream is = rs.getBinaryStream("Avatar")) {
            if (is != null) {
                // اقرأ الصورة كلها في byte[]
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] datao = new byte[2048];
                int nRead;
                while ((nRead = is.read(datao, 0, datao.length)) != -1) {
                    buffer.write(datao, 0, nRead);
                }
                buffer.flush();

                byte[] imgBytes = buffer.toByteArray();

                // استخدم ByteArrayInputStream عشان JavaFX يقرأ الصورة
                ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);
                Image img = new Image(bis, 80, 80, true, true);
                avatar.setImage(img);
            } else {
                avatar.setImage(new Image(getClass().getResourceAsStream("user.png")));
            }
        }
        
    }

} catch (OutOfMemoryError oom) {
    System.err.println("⚠️ الصورة كبيرة جدًا: " + oom.getMessage());
    avatar.setImage(new Image(getClass().getResourceAsStream("user.png")));
} catch (Exception e) {
    e.printStackTrace();
    avatar.setImage(new Image(getClass().getResourceAsStream("user.png")));
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (Exception ignored) {}
}



/////////////////////////////////////////////////////////////////////////////////////////



try {
    String sql = "SELECT * FROM Audit WHERE Section = ? AND User = ?";
    pst = conn.prepareStatement(sql);
    pst.setString(1, positionValue);
    pst.setString(2, usernameValue);
    rs = pst.executeQuery();

    // إنشاء الأعمدة تلقائيًا بناءً على أسماء الأعمدة من قاعدة البيانات
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param ->
            new SimpleStringProperty(param.getValue().get(j))
        );
        table.getColumns().add(col);
    }

    // تعبئة البيانات في ObservableList
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

// تطبيق فلتر على الجدول (مكتبة ControlsFX أو مشابه)
TableFilter<ObservableList<String>> filter = new TableFilter<>(table);

        
        
        
        
    }
  
    
    
    @FXML
    void positionaction(Event event) {
   
String sql = "SELECT User FROM USERS WHERE Position = ?";
try {
    // Clear the usersbox before adding new entries
    user.getItems().clear();
    String selectedPosition = section.getSelectionModel().getSelectedItem().toString();
    selectedpositionn=selectedPosition;
    this.pst = this.conn.prepareStatement(sql);
    this.pst.setString(1, selectedPosition);
    this.rs = this.pst.executeQuery();
    while (this.rs.next()) {
        String userName = this.rs.getString("User");
        user.getItems().add(userName); // Add each name to usersbox
    }
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

        
        
    }
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
        this.conn = db.java_db();
        
        this.section.getItems().clear();
    try {
      BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Positions.kady"));
      String line;
      while ((line = buf.readLine()) != null) {
        this.section.getItems().addAll(new String[] { line });
      } 
      buf.close();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (IOException iOException) {}
        
    }    
    
}
