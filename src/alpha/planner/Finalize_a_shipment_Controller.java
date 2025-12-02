
package alpha.planner;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class Finalize_a_shipment_Controller implements Initializable {

   
   

   @FXML
    private JFXTextField po;

    @FXML
    private JFXTextField sapcode;

    @FXML
    private JFXTextField customer;

    @FXML
    private JFXTextField cuttingamount;

    @FXML
    private JFXTextField laundrydate;

    @FXML
    private JFXTextField washname;

    @FXML
    private JFXTextField style;

    @FXML
    private JFXTextField poamount;

    @FXML
    private JFXTextField xfacdate;
    
    @FXML
    private JFXTextField qtosend;
    
    @FXML
    private JFXTextField received;
    
    @FXML
    private JFXTextField minusq;
    
    @FXML
    private JFXTextField notes;
    
    @FXML
    private JFXTextField packing;
    
    @FXML
    private JFXTextField seconddegree;

    @FXML
    private TableView<ObservableList<String>> table;
    
    

    @FXML
    private JFXButton setpath;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    public static String ppoo,ssapcodee,sstylee,ccustomerr,wwashnamee,ppoamountt,ccuttingamountt,llaundrydatee,xxfacdatee,recpath,procccc,qtosendd,qoqo;
    public static String selsecc,seluserr;
    public static String value1;

    
    
    @FXML
    void mouseclick(MouseEvent event) {

        qoqo=minusq.getText();
        
    }
    
    
    
    
    
    
    @FXML
void setpathaction(ActionEvent event) throws SQLException {
    // Gather input fields
    ppoo = po.getText().trim();
    ssapcodee = sapcode.getText().trim();
    sstylee = style.getText().trim();
    ccustomerr = customer.getText().trim();
    wwashnamee = washname.getText().trim();
    ppoamountt = poamount.getText().trim();
    ccuttingamountt = cuttingamount.getText().trim();
    llaundrydatee = laundrydate.getText().trim();
    xxfacdatee = xfacdate.getText().trim();
    qtosendd = qtosend.getText().trim();

    // ✅ Step 1: Validate that no fields are empty
    if (ppoo.isEmpty() || ssapcodee.isEmpty() || sstylee.isEmpty() || ccustomerr.isEmpty() ||
        wwashnamee.isEmpty() || ppoamountt.isEmpty() || ccuttingamountt.isEmpty() ||
        llaundrydatee.isEmpty() || xxfacdatee.isEmpty() || qtosendd.isEmpty()) {

        showThemedAlert(Alert.AlertType.WARNING, "Missing Data",
                "البيانات غير مكتملة", "يرجى تعبئة جميع الحقول قبل المتابعة");
        return; // Stop execution if any field is empty
    }

    // ✅ Step 2: Validate qtosend not zero or "-"
    try {
        int qty = Integer.parseInt(qtosendd);
        if (qty <= 0) {
            showThemedAlert(Alert.AlertType.WARNING, "Invalid Quantity",
                    "قيمة الإرسال غير صحيحة", "لا يمكن أن تكون الكمية صفر أو سالبة");
            return;
        }
    } catch (NumberFormatException e) {
        showThemedAlert(Alert.AlertType.WARNING, "Invalid Quantity",
                "قيمة الإرسال غير صحيحة", "يرجى إدخال رقم صحيح");
        return;
    }

    // ✅ Step 3: Continue to update
    updateShipment();
    
    
    
     table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from "+selsecc+"";
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

// Helper method to show alert with theme
private void showThemedAlert(Alert.AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);

    DialogPane dialogPane = alert.getDialogPane();

    // Apply theme
    try {
        BufferedReader bis = new BufferedReader(new FileReader(
                System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
        String themooo = bis.readLine();
        bis.close();

        if ("cupertino-dark.css".equals(themooo)) {
            dialogPane.getStylesheets().add(getClass().getResource("cupertino-dark.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
        }
    } catch (Exception ignored) {}

    alert.showAndWait();
}






private void updateShipment() throws SQLException {
    
    String del=qtosend.getText();
    String min=minusq.getText();
    String not=notes.getText();
    
    String sqlUpdate = "UPDATE WareHouse_2 SET Delivered = ?, Minus_Q = ?, Notes = ? " +
            "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ? " +
            "AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ? ";
    pst = conn.prepareStatement(sqlUpdate);
    
    pst.setString(1, del);
    pst.setString(2, min);
    pst.setString(3, not);

    
    pst.setString(4, ppoo);
    pst.setString(5, ssapcodee);
    pst.setString(6, sstylee);
    pst.setString(7, ccustomerr);
    pst.setString(8, wwashnamee);
    pst.setString(9, ppoamountt);
    pst.setString(10, ccuttingamountt);
    pst.setString(11, llaundrydatee);
    pst.setString(12, xxfacdatee);
  
    pst.executeUpdate();
    pst.close();

    Notifications.create().title("تحديث").text("الشحنة اتحدثت").hideAfter(Duration.seconds(3)).position(Pos.CENTER).showInformation();
    
  
  

    
        ////////////////////////Audit//////////////////////////
      
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" sent a shipment To: "+packing.getText()+", and "+packing.getText()+" Accepted it, info and its data is: its data is : Quantity:"+qtosend.getText()+", PO: "+ppoo+", Sap Code: "+ssapcodee+", Style: "+sstylee+", Customer: "+ccustomerr+", Wash Name: "+wwashnamee+", PO Amount: "+ppoamountt+", Cutting Amount: "+ccuttingamountt+", From: "+selsecc+", More Info: "+notes.getText()+".");
          this.pst.execute();
              }
              catch (Exception e) {
          
        } finally {
          try {
            this.rs.close();
            this.pst.close();
          } catch (Exception exception) {}
        }  
      
      ///////////////////////////////////////////////////////
        
   po.clear();
   sapcode.clear();
   style.clear();
   customer.clear();
   washname.clear();
   poamount.clear();
   cuttingamount.clear();
   laundrydate.clear();
   xfacdate.clear();
   received.clear();
   qtosend.clear();
   minusq.clear();
   notes.clear();
    
}










 @FXML
void rekeyaction(KeyEvent event) {
    
    
    
    if (qoqo.equals("Not_Updated_Yet")) {
        
         try {
        String s1 = received.getText();
        String s2 = qtosend.getText();

        // Default to 0 if input is empty or not a number
        int num1 = (s1 != null && !s1.trim().isEmpty()) ? Integer.parseInt(s1.trim()) : 0;
        int num2 = (s2 != null && !s2.trim().isEmpty()) ? Integer.parseInt(s2.trim()) : 0;

        int num3 = num1 - num2;
        
        String saa=String.valueOf(num3);
        if (saa.contains("-")) {
            minusq.clear();
            qtosend.clear();
            notes.clear();
        Notifications.create()
        .title("خطا فادح")
        .text("المفروض الكمية اللي هتبعتها للتعبئة كده اكتر من اللي موجودة عندك في المخزن ودا غلط")
        .hideAfter(Duration.seconds(5))
        .position(Pos.CENTER)
        .showError();
        }
        
        else {
        minusq.setText(String.valueOf(num3));
        notes.setText("تم ارسال "+qtosend.getText()+" قطعة الي قسم "+packing.getText()+" ومتبقي "+minusq.getText()+" قطعة قد تم تعيينها ك "+seconddegree.getText()+"");
        }
        
        
        

    } catch (NumberFormatException e) {
        // Optional: show warning if input is not valid
        minusq.setText("0"); // fallback
        System.out.println("Invalid number format: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
    
    
    else {
        
        
         try {
        String s1 = qoqo;
        String s2 = qtosend.getText();

        // Default to 0 if input is empty or not a number
        int num1 = (s1 != null && !s1.trim().isEmpty()) ? Integer.parseInt(s1.trim()) : 0;
        int num2 = (s2 != null && !s2.trim().isEmpty()) ? Integer.parseInt(s2.trim()) : 0;

        int num3 = num1 - num2;
        String saa=String.valueOf(num3);
        if (saa.contains("-")) {
            minusq.clear();
            qtosend.clear();
            notes.clear();
        Notifications.create()
        .title("خطا فادح")
        .text("المفروض الكمية اللي هتبعتها للتعبئة كده اكتر من اللي موجودة عندك في المخزن ودا غلط")
        .hideAfter(Duration.seconds(5))
        .position(Pos.CENTER)
        .showError();
        }
        
        else {
        minusq.setText(String.valueOf(num3));
        notes.setText("تم ارسال "+qtosend.getText()+" قطعة الي قسم "+packing.getText()+" ومتبقي "+minusq.getText()+" قطعة قد تم تعيينها ك "+seconddegree.getText()+"");
        }

    } catch (NumberFormatException e) {
        // Optional: show warning if input is not valid
        minusq.setText("0"); // fallback
        System.out.println("Invalid number format: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
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
          
        selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;
   
        
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
            
            String sql ="select * from "+selsecc+"";
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
        poamount.setText(getCell(row, 5));
        cuttingamount.setText(getCell(row, 6));
        laundrydate.setText(getCell(row, 7));
        xfacdate.setText(getCell(row, 8));
        received.setText(getCell(row, 9));
        
        qtosend.setText(getCell(row, 10));
        minusq.setText(getCell(row, 11));
        notes.setText(getCell(row, 12));
        
        //qtosend.setText("Packing");
       

    }
});
       

      
        
        
    }

// Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}    
    
}
