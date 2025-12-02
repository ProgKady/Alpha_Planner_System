/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package alpha.planner;

import static alpha.planner.WASHINGController.selsecc;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class TableViewerController implements Initializable {
   
   Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    public static String selsecc,seluserr;
    
    @FXML
    private TableView<ObservableList<String>> table;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
       
         this.conn = db.java_db();
        
        
        selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;
        
        
    // Refresh Table
    try {
        table.getColumns().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM BarCode_P_" + seluserr);
        ResultSet rs = pst.executeQuery();

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
            table.getColumns().add(col);
        }

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        table.setItems(data);
        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Re-apply filter
    new TableFilter<>(table);
        
        
    }    

}
