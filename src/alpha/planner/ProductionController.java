
package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class ProductionController implements Initializable {

   @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private JFXTextField po;

    @FXML
    private JFXTextField sapcode;

    @FXML
    private JFXTextField style;

    @FXML
    private JFXTextField customer;

    @FXML
    private JFXTextField washname;

    @FXML
    private JFXTextField received;

    @FXML
    private JFXTextField finished;

    @FXML
    private JFXTextField notfinished;

   
    
     @FXML
    private JFXButton export;

    @FXML
    private JFXButton refresh;


    @FXML
    private JFXButton update;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    //public static String ppoo,ssapcodee,sstylee,ccustomerr,wwashnamee,ppoamountt,ccuttingamountt,llaundrydatee,xxfacdatee,recpath,procccc,qtosendd,qoqo;
    public static String selseccc,seluserrr;
    public static String value1,undoreceivedvalue,undofinishedvalue,undonotfinishedvalue,finmouse;
    
    

    
    
    
    
     
   @FXML
void updateaction(ActionEvent event) {
    String poo = po.getText().trim();
    String sap = sapcode.getText().trim();
    String stylee = style.getText().trim();
    String cust = customer.getText().trim();
    String wash = washname.getText().trim();
    String fino = finished.getText().trim();
    String notfino = notfinished.getText().trim();

    // ✅ Check if any field is empty
    if (poo.isEmpty() || sap.isEmpty() || stylee.isEmpty() || cust.isEmpty() ||
        wash.isEmpty() || fino.isEmpty() || notfino.isEmpty()) {
        
        Notifications.create()
            .title("خطأ")
            .text("يرجى ملء جميع الحقول قبل التحديث")
            .hideAfter(Duration.seconds(3))
            .position(Pos.CENTER)
            .showError();
        
        return; // ⛔ Stop execution if any field is empty
    }

    try {
        String sqla = "UPDATE " + selseccc + "_Production SET Finished = ?, Not_Finished = ? " +
                      "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
        this.pst = this.conn.prepareStatement(sqla);

        this.pst.setString(1, fino);
        this.pst.setString(2, notfino);
        this.pst.setString(3, poo);
        this.pst.setString(4, sap);
        this.pst.setString(5, stylee);
        this.pst.setString(6, cust);
        this.pst.setString(7, wash);

        int rowsUpdated = this.pst.executeUpdate();

        if (rowsUpdated > 0) {
            Notifications.create()
                .title("نجاح")
                .text("تم تحديث البيانات بنجاح")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showInformation();
            clearall ();
        } else {
            Notifications.create()
                .title("تنبيه")
                .text("لم يتم العثور على البيانات للتحديث")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showWarning();
            clearall ();
        }
    } catch (Exception e) {
        e.printStackTrace();
        Notifications.create()
            .title("خطأ")
            .text("حدث خطأ أثناء التحديث")
            .hideAfter(Duration.seconds(3))
            .position(Pos.CENTER)
            .showError();
        clearall ();
    } finally {
        try {
            if (this.rs != null) this.rs.close();
            if (this.pst != null) this.pst.close();
        } catch (Exception exception) {}
    }
}

    
    
    
    
    
    
    
    @FXML
    void refreshaction(ActionEvent event) {

        
        
        
    table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from "+selseccc+"_Production ";
            pst=conn.prepareStatement(sql);  
            rs=pst.executeQuery();
            
        ///////////////////////////////////////////////////////////////
            
        for (int i=0;i<rs.getMetaData().getColumnCount();i++) {
            final int j=i;
            TableColumn col=new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                     return new SimpleStringProperty(param.getValue().get(j).toString());
                     
                }
                
            });
            table.getColumns().addAll(col);
            
        }
        
        //While getting info
        
        while (rs.next()) {
            ObservableList<String> row=FXCollections.observableArrayList();
            for (int i=1;i<=rs.getMetaData().getColumnCount();i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
            
        }
        
        table.setItems((ObservableList)data);
          
       ////////////////////////////////////////////////////////////////
            
        }catch(Exception e){
          
        }
        finally {
            
            try{
                
                rs.close();
                pst.close();

            }
            catch(Exception e){
                
            }
         } 
         
        // getauditbtn.setDisable(true);
        
          TableFilter filter = new TableFilter(table);
          
        
        
    }
    
    
    
    
    
      @FXML
    void exportaction(ActionEvent event) throws FileNotFoundException, IOException {

        
        
          /////////////////////////////////////////////////////////////////////////
    
        
        Workbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Production");
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
        dialog.setInitialFileName("Production");
        dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", new String[] { "*.xlsx" }));
        File dialogResult = dialog.showSaveDialog(null);
        String filePath = dialogResult.getAbsolutePath().toString();
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        Desktop desk=Desktop.getDesktop();
        desk.open(new File (filePath));
        
        
        
        
        
    }
    
    
    
    
    
    
    
    @FXML
void finkeyaction(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        try {

            setupFinishedNotFinishedLogic(received, finished, notfinished, update,refresh);
            

            
        } catch (NumberFormatException e) {
            Notifications.create()
                    .title("خطأ")
                    .text("حدث خطأ ما يجب عليك اصلاحه")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER)
                    .showError();
            clearall ();
        }
    }
}


private void setupFinishedNotFinishedLogic(JFXTextField receivedField, JFXTextField finishedField, JFXTextField notFinishedField, JFXButton update, JFXButton refresh) {
    finishedField.setOnAction(e -> {
        try {
            int received = Integer.parseInt(receivedField.getText().trim());
            int currentNotFinished = Integer.parseInt(notFinishedField.getText().trim().isEmpty() ? "0" : notFinishedField.getText().trim());

            // لو لسه في not finished يبقي نقفل العملية
            if (currentNotFinished == 0 && !finishedField.getText().trim().contains("+")) {
                Notifications.create()
                        .title("تنبيه")
                        .text("العملية خلصت بالفعل ومفيش باقي عشان تخصمه تاني.")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER)
                        .showWarning();
                clearall ();
                return;
            }

            // Handle multiple numbers with '+'
            String[] parts = finishedField.getText().trim().split("\\+");
            int totalFinished = 0;
            for (String part : parts) {
                if (!part.trim().isEmpty()) {
                    totalFinished += Integer.parseInt(part.trim());
                }
            }

            int notFinished = received - totalFinished;

            // Prevent negative values
            if (notFinished < 0) {
                clearall ();
                Notifications.create()
                        .title("انت عبيط")
                        .text("يوجد قيم تحتوي على سالب ومينفعش اللي خلصت يبقي اكتر من اللي استلمته")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER)
                        .showError();

                finishedField.setText("0");
                notFinishedField.setText(String.valueOf(received));
                return;
            }

            // تحديث القيم
            finishedField.setText(String.valueOf(totalFinished));
            notFinishedField.setText(String.valueOf(notFinished));

            // بس نعمل update لما يكون في باقي يتخصم
            if (notFinished > 0) {
                update.fire();
                refresh.fire();
            } else {
                Notifications.create()
                        .title("تمام")
                        .text("خلصت الكمية كلها ✅")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER)
                        .showInformation();
            update.fire();
            refresh.fire();
            clearall ();
            }
            

        } catch (NumberFormatException ex) {
            finishedField.setText("0");
            notFinishedField.setText(receivedField.getText());
            refresh.fire();
        }
    });
}


public void clearall () {
    
        po.clear();
        sapcode.clear();
        style.clear();
        customer.clear();
        washname.clear();
        received.clear();
        finished.clear();
        notfinished.clear();
    
}




    @FXML
    void finmouseaction(MouseEvent event) {

//        int m1=Integer.parseInt(received.getText());
//        int m2=Integer.parseInt(notfinished.getText());
//        
//        int m3=m1-m2;
//        
//        finished.setText(String.valueOf(m3));
        
        finmouse=finished.getText();
        
        
        
        
    }
    
    
//    
//    
//   @FXML
//void undoaction(ActionEvent event) {
//    // Restore old values
//    received.setText(undoreceivedvalue);
//    finished.setText(undofinishedvalue);
//    notfinished.setText(undonotfinishedvalue);
//
//    String poo = po.getText().trim();
//    String sap = sapcode.getText().trim();
//    String stylee = style.getText().trim();
//    String cust = customer.getText().trim();
//    String wash = washname.getText().trim();
//    String fino = finished.getText().trim();
//    String notfino = notfinished.getText().trim();
//
//    // ✅ Empty field check
//    if (poo.isEmpty() || sap.isEmpty() || stylee.isEmpty() || cust.isEmpty() ||
//        wash.isEmpty() || fino.isEmpty() || notfino.isEmpty()) {
//        
//        Notifications.create()
//            .title("خطأ")
//            .text("يرجى ملء جميع الحقول قبل التراجع")
//            .hideAfter(Duration.seconds(3))
//            .position(Pos.CENTER)
//            .showError();
//        
//        return; // ⛔ Stop execution
//    }
//
//    try {
//        String sqla = "UPDATE " + selseccc + "_Production SET Finished = ?, Not_Finished = ? " +
//                      "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
//        this.pst = this.conn.prepareStatement(sqla);
//
//        this.pst.setString(1, fino);
//        this.pst.setString(2, notfino);
//        this.pst.setString(3, poo);
//        this.pst.setString(4, sap);
//        this.pst.setString(5, stylee);
//        this.pst.setString(6, cust);
//        this.pst.setString(7, wash);
//
//        int rowsUpdated = this.pst.executeUpdate();
//
//        if (rowsUpdated > 0) {
//            Notifications.create()
//                .title("نجاح")
//                .text("تم التراجع عن البيانات بنجاح")
//                .hideAfter(Duration.seconds(3))
//                .position(Pos.CENTER)
//                .showInformation();
//        } else {
//            Notifications.create()
//                .title("تنبيه")
//                .text("لم يتم العثور على البيانات للتراجع")
//                .hideAfter(Duration.seconds(3))
//                .position(Pos.CENTER)
//                .showWarning();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        Notifications.create()
//            .title("خطأ")
//            .text("حدث خطأ أثناء التراجع")
//            .hideAfter(Duration.seconds(3))
//            .position(Pos.CENTER)
//            .showError();
//    } finally {
//        try { if (this.rs != null) this.rs.close(); } catch (Exception e) {}
//        try { if (this.pst != null) this.pst.close(); } catch (Exception e) {}
//    }
//}
//

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
              
        selseccc=WASHINGController.selsecc;
        seluserrr=WASHINGController.seluserr;
   
        
    this.conn = db.java_db();
    
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;
    
    table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from "+selseccc+"_Production ";
            pst=conn.prepareStatement(sql);  
            rs=pst.executeQuery();
            
        ///////////////////////////////////////////////////////////////
            
        for (int i=0;i<rs.getMetaData().getColumnCount();i++) {
            final int j=i;
            TableColumn col=new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                     return new SimpleStringProperty(param.getValue().get(j).toString());
                     
                }
                
            });
            table.getColumns().addAll(col);
            
        }
        
        //While getting info
        
        while (rs.next()) {
            ObservableList<String> row=FXCollections.observableArrayList();
            for (int i=1;i<=rs.getMetaData().getColumnCount();i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
            
        }
        
        table.setItems((ObservableList)data);
          
       ////////////////////////////////////////////////////////////////
            
        }catch(Exception e){
          
        }
        finally {
            
            try{
                
                rs.close();
                pst.close();

            }
            catch(Exception e){
                
            }
         } 
         
        // getauditbtn.setDisable(true);
        
          TableFilter filter = new TableFilter(table);
          
    
           // Add a listener to update TextFields when the selection changes
table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Assuming each row is an ObservableList<String> with fixed column order
        ObservableList<String> row = newSelection;
//System.out.println("Selection changed 2");
      //   System.out.println("Row selected: " + row);  // تأكد أن الصف فعلاً يحتوي بيانات
        
        // Fill in your TextFields safely (use null/size checks if needed)
        
        po.setText(getCell(row, 0));
        sapcode.setText(getCell(row, 1));
        style.setText(getCell(row, 2));
        customer.setText(getCell(row, 3));
        washname.setText(getCell(row, 4));
        received.setText(getCell(row, 5));
        finished.setText(getCell(row, 6));
        notfinished.setText(getCell(row, 7));
        
        int m1=Integer.parseInt(getCell(row, 5));
        int m2=Integer.parseInt(getCell(row, 7));
        
        int m3=m1-m2;
        
        finished.setText(String.valueOf(m3));
        
        undoreceivedvalue=getCell(row, 5);
        undofinishedvalue=getCell(row, 6);
        undonotfinishedvalue=getCell(row, 7);
       
    }
});
       

        
    }    
    
    // Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}    
    
}
