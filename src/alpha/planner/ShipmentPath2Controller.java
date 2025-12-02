
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
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Random;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class ShipmentPath2Controller implements Initializable {


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
    private ComboBox<String> choosepath;
    
    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private JFXButton setpath;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    public static String ppoo,ssapcodee,sstylee,ccustomerr,wwashnamee,ppoamountt,ccuttingamountt,llaundrydatee,xxfacdatee,recpath,procccc,qtosendd;
    public static String selsecc,seluserr;
    public static String value1;

    
    
    
      
    
    
   @FXML
void mmkeyaction(KeyEvent event) {
    
    
//    
//    
//    String ccuttingamou = cuttingamount.getText().trim();
//    String qtose = qtosend.getText().trim();
//
//    // ✅ التحقق من الإدخالات الفارغة
//    if (ccuttingamou.isEmpty() || qtose.isEmpty()) {
//        return;
//    }
//
//    try {
//        int cutAmount   = Integer.parseInt(ccuttingamou); // الكمية المقطوعة
//        int received    = Integer.parseInt(qqi);          // الكمية المستلمة
//        int toSend      = Integer.parseInt(qtose);        // الكمية المرسلة
//
//        // ✅ شرط 1: المرسل > 0
//        if (toSend <= 0) {
//            qtosend.clear();
//            Notifications.create()
//                .title("خطأ")
//                .text("الكمية المرسلة يجب أن تكون أكبر من صفر")
//                .hideAfter(Duration.seconds(5))
//                .position(Pos.CENTER)
//                .showError();
//            return;
//        }
//
//        // ✅ شرط 2: المرسل أكبر من المقطوع
//        if (toSend > cutAmount) {
//            qtosend.clear();
//            Notifications.create()
//                .title("خطأ")
//                .text("لا يمكن أن تكون الكمية المرسلة أكبر من الكمية المقطوعة")
//                .hideAfter(Duration.seconds(5))
//                .position(Pos.CENTER)
//                .showError();
//            return;
//        }
//
//        // ✅ شرط 3: المرسل أكبر من المستلم
//        if (toSend > received) {
//            qtosend.clear();
//            Notifications.create()
//                .title("خطأ")
//                .text("الكمية المرسلة يجب أن تكون أقل من أو تساوي الكمية المستلمة")
//                .hideAfter(Duration.seconds(5))
//                .position(Pos.CENTER)
//                .showError();
//            return;
//        }
//
//        // ✅ شرط 4: التحقق من الشحنات السابقة
//        int totalQToSend = 0;
//        try {
//            String sql = "SELECT SUM(Q_To_Send) AS total FROM Shipment_Path " +
//                         "WHERE PO = ? AND SAP_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
//            pst = conn.prepareStatement(sql);
//            pst.setString(1, po.getText().trim());
//            pst.setString(2, sapcode.getText().trim());
//            pst.setString(3, style.getText().trim());
//            pst.setString(4, customer.getText().trim());
//            pst.setString(5, washname.getText().trim());
//
//            rs = pst.executeQuery();
//            if (rs.next()) {
//                totalQToSend = rs.getInt("total");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try { if (rs != null) rs.close(); } catch (Exception ex) {}
//            try { if (pst != null) pst.close(); } catch (Exception ex) {}
//        }
//
//        int remaining = received - totalQToSend;
//
//        if (remaining <= 0) {
//            qtosend.clear();
//            Notifications.create()
//                .title("لا يمكن الإرسال")
//                .text("تم استهلاك الكمية المستلمة بالكامل. يجب رفض بعض الشحنات أولاً")
//                .hideAfter(Duration.seconds(5))
//                .position(Pos.CENTER)
//                .showError();
//            return;
//        }
//
//        // ✅ هنا لو كل الشروط عدّت → الإدخال صحيح
//        // تقدر تكمل المنطق الخاص بيك (حفظ DB أو متابعة العملية)
//        
//    } catch (NumberFormatException e) {
//        qtosend.clear();
//        Notifications.create()
//            .title("خطأ في الإدخال")
//            .text("من فضلك أدخل أرقام صحيحة فقط")
//            .hideAfter(Duration.seconds(5))
//            .position(Pos.CENTER)
//            .showError();
//    }
}




//    
//  // متغير فلاغ
//private boolean qqiCaptured = false;
//
@FXML
void mmclick(MouseEvent event) {
//    // ينفذ مرة واحدة فقط
//    if (!qqiCaptured) {
//        qqi = qtosend.getText();  // ياخد القيمة أول مرة
//        qtosend.clear();          // يمسح الحقل
//        qqiCaptured = true;       // قفل التكرار
    }
//}

    
//    
//    
//    
//    private void showThemedAlert(Alert.AlertType type, String title, String header, String content) {
//    Alert alert = new Alert(type);
//    alert.setTitle(title);
//    alert.setHeaderText(header);
//    alert.setContentText(content);
//
//    try {
//        BufferedReader bis = new BufferedReader(new FileReader(System.getProperty("user.home") +
//                "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//        String theme = bis.readLine();
//        bis.close();
//        if ("cupertino-dark.css".equals(theme)) {
//            alert.getDialogPane().getStylesheets().add(getClass().getResource("cupertino-dark.css").toExternalForm());
//        } else {
//            alert.getDialogPane().getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
//        }
//    } catch (Exception ignored) {}
//
//    alert.showAndWait();
//}

    
   

@FXML
private void setpathaction(ActionEvent event) throws SQLException {
    // === Step 0: Gather input fields ===
    ppoo           = po.getText().trim();
    ssapcodee      = sapcode.getText().trim();
    sstylee        = style.getText().trim();
    ccustomerr     = customer.getText().trim();
    wwashnamee     = washname.getText().trim();
    ppoamountt     = poamount.getText().trim();
    ccuttingamountt= cuttingamount.getText().trim();
    llaundrydatee  = laundrydate.getText().trim();
    xxfacdatee     = xfacdate.getText().trim();
    qtosendd       = qtosend.getText().trim();

    // === Step 1: Validate empty fields ===
    if (ppoo.isEmpty() || ssapcodee.isEmpty() || sstylee.isEmpty() || ccustomerr.isEmpty() ||
        wwashnamee.isEmpty() || ppoamountt.isEmpty() || ccuttingamountt.isEmpty() ||
        llaundrydatee.isEmpty() || xxfacdatee.isEmpty() || qtosendd.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "البيانات ناقصة", "البيانات غير مكتملة",
                  "يرجى ملء جميع الحقول قبل المتابعة ✨❤");
        return;
    }

    // === Step 2: Validate qtosend numeric > 0 ===
    int qtosendValue;
    int cuttingValue;
    try {
        qtosendValue = Integer.parseInt(qtosendd);
        cuttingValue = Integer.parseInt(ccuttingamountt);

        if (qtosendValue <= 0) {
            showNotification("خطأ في الكمية", "يجب أن تكون الكمية أكبر من صفر.");
            return;
        }

        if (qtosendValue > cuttingValue) {
            showNotification("خطأ في الكمية", "الكمية المرسلة يجب أن تكون أقل من أو تساوي الكمية المقطوعة.");
            return;
        }
    } catch (NumberFormatException e) {
        showNotification("قيمة غير صالحة", "الكمية يجب أن تكون رقم صحيح.");
        return;
    }

    // === Step 3: Check shipments already sent ===
    int totalSent = 0;
    String sqlTotal = "SELECT SUM(Q_To_Send) AS total FROM Shipment_Path " +
                      "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
    try (PreparedStatement pst = conn.prepareStatement(sqlTotal)) {
        pst.setString(1, ppoo);
        pst.setString(2, ssapcodee);
        pst.setString(3, sstylee);
        pst.setString(4, ccustomerr);
        pst.setString(5, wwashnamee);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                totalSent = rs.getInt("total");
            }
        }
    }

     int receivedValue = 0;
    if (selsecc.equalsIgnoreCase("WareHouse_1")) {
        
         // === Step 4: Check Received from DB ===
    String sqlReceived = "SELECT Received FROM WareHouse_1 WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
    try (PreparedStatement pst = conn.prepareStatement(sqlReceived)) {
        pst.setString(1, ppoo);
        pst.setString(2, ssapcodee);
        pst.setString(3, sstylee);
        pst.setString(4, ccustomerr);
        pst.setString(5, wwashnamee);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                receivedValue = rs.getInt("Received");
            }
        }
    }
    
    }
    
    else {
        
          // === Step 4: Check Received from DB ===
    String sqlReceived = "SELECT Finished FROM "+selsecc+"_Production WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
    try (PreparedStatement pst = conn.prepareStatement(sqlReceived)) {
        pst.setString(1, ppoo);
        pst.setString(2, ssapcodee);
        pst.setString(3, sstylee);
        pst.setString(4, ccustomerr);
        pst.setString(5, wwashnamee);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                receivedValue = rs.getInt("Finished");
            }
        }
    }
        
    }
    

    // === Step 5: Final checks ===
    // الشرط النهائي إن المجموع مايعديش لا المقطوع ولا المستلم
    int maxAllowed = Math.min(cuttingValue, receivedValue);

    if (totalSent >= maxAllowed) {
        showNotification("لا يمكن الإرسال", "تم الوصول للحد الأقصى المسموح (" + maxAllowed + "). ولو في شحنات متعلقة عندك بلغ المستلم يقبلها او يرفضها او شغل الشغل الاول علشان تبعت");
        return;
    }

    if (totalSent + qtosendValue > maxAllowed) {
        showNotification("خطأ", "إجمالي الكميات المرسلة (" + (totalSent + qtosendValue) + 
                        ") سيتجاوز الحد المسموح (" + maxAllowed + ").");
        return;
    }

    // === Step 6: Validate against Finished in Production ===
    if (!selsecc.equalsIgnoreCase("WareHouse_1")) {
        String query = "SELECT Finished FROM " + selsecc + "_Production WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, ppoo);
            pst.setString(2, ssapcodee);
            pst.setString(3, sstylee);
            pst.setString(4, ccustomerr);
            pst.setString(5, wwashnamee);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int finishedValue = rs.getInt("Finished");
                    if (qtosendValue > finishedValue) {
                        showAlert(Alert.AlertType.ERROR, "كمية غير صحيحة",
                                  "لا يمكن إتمام العملية",
                                  "الكمية المطلوبة (" + qtosendValue + ") أكبر من الكمية المتاحة (" + finishedValue + ").");
                        return;
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, "لا يوجد بيانات", "لم يتم العثور على سجل",
                              "لا يوجد بيانات مطابقة في الإنتاج.");
                    return;
                }
            }
        }
    }

    // === Step 7: Validate path selection ===
    String too;
    if (choosepath.getSelectionModel().getSelectedItem() != null) {
        too = choosepath.getSelectionModel().getSelectedItem().toString();
    } else {
        showAlert(Alert.AlertType.WARNING, "المسار غير محدد", "المسار غير محدد",
                  "اختار الاول هتودي الشحنة دي علي فين وابقي كمل ي غالي");
        return;
    }

    String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    // === Step 8: Check if shipment exists in DB ===
    boolean existsDataMatch = false;
    boolean existsFullMatch = false;

    String sql = "SELECT * FROM Shipment_Path WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? " +
                 "AND Wash_Name = ? AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ?";

    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, ppoo);
        pst.setString(2, ssapcodee);
        pst.setString(3, sstylee);
        pst.setString(4, ccustomerr);
        pst.setString(5, wwashnamee);
        pst.setString(6, ppoamountt);
        pst.setString(7, ccuttingamountt);
        pst.setString(8, llaundrydatee);
        pst.setString(9, xxfacdatee);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                existsDataMatch = true;
                String fromDb = rs.getString("From");
                String toDb   = rs.getString("To");
                if (fromDb.equals(selsecc) && toDb.equals(too)) {
                    existsFullMatch = true;
                    break;
                }
            }
        }
    }

    // === Step 9: Decide insert/update ===
    if (existsFullMatch) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("الشحنة موجودة");
        alert.setHeaderText("لقد وجدنا نفس الشحنة بتفاصيلها");
        alert.setContentText("هل تريد التحديث بالكمية الجديدة؟");

        ButtonType updateBtn = new ButtonType("تحديث");
        ButtonType insertBtn = new ButtonType("إضافة جديد");
        ButtonType cancelBtn = new ButtonType("إلغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(updateBtn, insertBtn, cancelBtn);

        applyTheme(alert.getDialogPane());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == updateBtn) {
                updateShipment(dateString2);
            } else if (result.get() == insertBtn) {
                insertShipment(dateString2);
            }
        }
    } else {
        insertShipment(dateString2);
    }
    
    
    
    
     table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sqlm ="select * from "+selsecc+"";
            pst=conn.prepareStatement(sqlm);  
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

/**
 * Helper method to show styled alerts
 */
private void showAlert(Alert.AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    applyTheme(alert.getDialogPane());
    alert.showAndWait();
}

/**
 * Helper method to show notifications
 */
private void showNotification(String title, String text) {
    Notifications.create()
        .title(title)
        .text(text)
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showError();
}

/**
 * Apply custom theme to dialog
 */
private void applyTheme(DialogPane dialogPane) {
    try (BufferedReader bis = new BufferedReader(
            new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
        String theme = bis.readLine();
        if ("cupertino-dark.css".equals(theme)) {
            dialogPane.getStylesheets().add(getClass().getResource("cupertino-dark.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
        }
    } catch (Exception ignored) {}
}

    



private void updateShipment(String dateString) throws SQLException {
    String sqlUpdate = "UPDATE Shipment_Path SET Date = ?, User = ?, Delivered = ?, Q_To_Send = ? " +
            "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ? " +
            "AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ? AND \"From\" = ? AND \"To\" = ?";
    pst = conn.prepareStatement(sqlUpdate);
    pst.setString(1, dateString);
    pst.setString(2, seluserr);
    pst.setString(3, "0");
    pst.setString(4, qtosendd);
    pst.setString(5, ppoo);
    pst.setString(6, ssapcodee);
    pst.setString(7, sstylee);
    pst.setString(8, ccustomerr);
    pst.setString(9, wwashnamee);
    pst.setString(10, ppoamountt);
    pst.setString(11, ccuttingamountt);
    pst.setString(12, llaundrydatee);
    pst.setString(13, xxfacdatee);
    pst.setString(14, selsecc);
    pst.setString(15, choosepath.getSelectionModel().getSelectedItem().toString());
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
          this.pst.setString(4, seluserr+" Updated a shipment info and its data is: its data is : PO: "+ppoo+", Sap Code: "+ssapcodee+", Style: "+sstylee+", Customer: "+ccustomerr+", Wash Name: "+wwashnamee+", PO Amount: "+ppoamountt+", Cutting Amount: "+ccuttingamountt+", From: "+selsecc+", To: "+choosepath.getSelectionModel().getSelectedItem().toString()+".");
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
    
    
}






private void insertShipment(String dateString) throws SQLException {
    // Generate unique ID logic (كما هو في كودك)
    String uniqueId = String.format("%010d", new Random().nextInt(1_000_000_000));

    String insertSql = "INSERT INTO Shipment_Path (Date, PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
            "Incoming_Date, X_Fac_Date, User, \"From\", \"To\", Delivered, Unique_ID, Q_To_Send) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    pst = conn.prepareStatement(insertSql);
    pst.setString(1, dateString);
    pst.setString(2, ppoo);
    pst.setString(3, ssapcodee);
    pst.setString(4, sstylee);
    pst.setString(5, ccustomerr);
    pst.setString(6, wwashnamee);
    pst.setString(7, ppoamountt);
    pst.setString(8, ccuttingamountt);
    pst.setString(9, llaundrydatee);
    pst.setString(10, xxfacdatee);
    pst.setString(11, seluserr);
    pst.setString(12, selsecc);
    pst.setString(13, choosepath.getSelectionModel().getSelectedItem().toString());
    pst.setString(14, "0");
    pst.setString(15, uniqueId);
    pst.setString(16, qtosendd);
    pst.executeUpdate();
    pst.close();

    Notifications.create().title("اضافة").text("في شحنة جديدة اتضافت").hideAfter(Duration.seconds(3)).position(Pos.CENTER).showInformation();
    
       ////////////////////////Audit//////////////////////////
      
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" Inserted a new shipment, Quantity: "+qtosendd+", info and its data is: its data is : PO: "+ppoo+", Sap Code: "+ssapcodee+", Style: "+sstylee+", Customer: "+ccustomerr+", Wash Name: "+wwashnamee+", PO Amount: "+ppoamountt+", Cutting Amount: "+ccuttingamountt+", From: "+selsecc+", To: "+choosepath.getSelectionModel().getSelectedItem().toString()+".");
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
    
}

    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
       
          this.choosepath.getItems().clear();
    try {
      BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Positions.kady"));
      String line;
      while ((line = buf.readLine()) != null) {
        this.choosepath.getItems().addAll(new String[] { line });
      } 
      buf.close();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (IOException iOException) {}
    
         selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;
    
            ppoo=WAREHOUSE_1_Controller.ppo;
            ssapcodee=WAREHOUSE_1_Controller.ssapcode;
            sstylee=WAREHOUSE_1_Controller.sstyle;
            ccustomerr=WAREHOUSE_1_Controller.ccustomer;
            wwashnamee=WAREHOUSE_1_Controller.wwashname;
            ppoamountt=WAREHOUSE_1_Controller.ppoamount;
            ccuttingamountt=WAREHOUSE_1_Controller.ccuttingamount;
            llaundrydatee=WAREHOUSE_1_Controller.llaundrydate;
            xxfacdatee=WAREHOUSE_1_Controller.xxfacdate;
            
//            System.out.println(ppoo);
//            System.out.println(ssapcodee);
//            System.out.println(sstylee);
//            System.out.println(ccustomerr);
//            System.out.println(wwashnamee);
//            System.out.println(ppoamountt);
//            System.out.println(ccuttingamountt);
//            System.out.println(llaundrydatee);
//            System.out.println(xxfacdatee);
            
//          po.setText(ppoo);
//          sapcode.setText(ssapcodee);
//          style.setText(sstylee);
//          customer.setText(ccustomerr);
//          washname.setText(wwashnamee);
//          poamount.setText(ppoamountt);
//          cuttingamount.setText(ccuttingamountt);
//          laundrydate.setText(llaundrydatee);
//          xfacdate.setText(xxfacdatee);
          

        
        ////////////////////////////////////////////////////////////////////////////////////////////////
        
        
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
          
          System.out.println("Selection changed 0");
          
          Platform.runLater(() -> {
    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
        System.out.println("Selection changed 1");
        // باقي الكود
    });
});

          
          
          
          
          
          
          
           // Add a listener to update TextFields when the selection changes
table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Assuming each row is an ObservableList<String> with fixed column order
        ObservableList<String> row = newSelection;
System.out.println("Selection changed 2");
         System.out.println("Row selected: " + row);  // تأكد أن الصف فعلاً يحتوي بيانات
        
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
        qtosend.setText(getCell(row, 9));
        
        
        System.out.println(getCell(row, 4));
      String recipenamiy=getCell(row, 4);
      
      

    }
});
        
        
    } 
    
    // Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}   
    
}
