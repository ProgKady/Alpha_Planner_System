
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
public class ShipmentPath1Controller implements Initializable {

   
    @FXML
    private JFXTextArea recipedata;

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

    
    
//    // 🟢 متغيرات جلوبال
//private boolean qqiCaptured = false;
//private int qqi = 0; // الكمية المستلمة (تتخزن مرة واحدة فقط)
//
//// 🟢 دالة لعرض الرسائل
//private void showNotification(String title, String message) {
//    Notifications.create()
//        .title(title)
//        .text(message)
//        .hideAfter(Duration.seconds(5))
//        .position(Pos.CENTER)
//        .showError();
//}

@FXML
void mmkeyaction(KeyEvent event) {
//    String ccuttingamou = cuttingamount.getText().trim();
//    String qtose = qtosend.getText().trim();
//
//    // ✅ التحقق من الإدخالات الفارغة
//    if (ccuttingamou.isEmpty() || qtose.isEmpty()) {
//        return;
//    }
//
//    try {
//        // 🟢 تخزين قيمة qtosend أول مرة فقط
//        if (!qqiCaptured) {
//            qqi = Integer.parseInt(qtose);
//            qqiCaptured = true;
//        }
//
//        int cutAmount = Integer.parseInt(ccuttingamou); // الكمية المقطوعة
//        int received  = qqi;                            // الكمية المستلمة (ثابتة من أول مرة)
//        int toSend    = Integer.parseInt(qtose);        // الكمية المرسلة (بتتغير مع الكتابة)
//
//        // ✅ شرط 1: المرسل > 0
//        if (toSend <= 0) {
//            qtosend.clear();
//            showNotification("خطأ", "الكمية المرسلة يجب أن تكون أكبر من صفر");
//            qqiCaptured = false; // السماح بالتقاط قيمة جديدة بعد المسح
//            return;
//        }
//
//        // ✅ شرط 2: المرسل أكبر من المقطوع
//        if (toSend > cutAmount) {
//            qtosend.clear();
//            showNotification("خطأ", "لا يمكن أن تكون الكمية المرسلة أكبر من الكمية المقطوعة");
//            return;
//        }
//
//        // ✅ شرط 3: المرسل أكبر من المستلم
//        if (toSend > received) {
//            qtosend.clear();
//            showNotification("خطأ", "الكمية المرسلة يجب أن تكون أقل من أو تساوي الكمية المستلمة");
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
//        // ✅ شرط 5: لا يوجد كمية متبقية
//        if (remaining <= 0) {
//            qtosend.clear();
//            showNotification("لا يمكن الإرسال", "تم استهلاك الكمية المستلمة بالكامل. يجب رفض بعض الشحنات أولاً");
//            return;
//        }
//
//        // 🟢 هنا لو كل الشروط عدّت → الإدخال صحيح
//        // تقدر تكمل منطقك (حفظ في DB أو أي عملية أخرى)
//
//    } catch (NumberFormatException e) {
//        qtosend.clear();
//        showNotification("خطأ في الإدخال", "من فضلك أدخل أرقام صحيحة فقط");
//        qqiCaptured = false; // إعادة ضبط علشان يقدر يلتقط قيمة جديدة
//    }
}

    
    
    
    

@FXML
void mmclick(MouseEvent event) {
//    // ينفذ مرة واحدة فقط
//    if (!qqiCaptured) {
//        qqi = qtosend.getText();  // ياخد القيمة أول مرة
//        qtosend.clear();          // يمسح الحقل
//        qqiCaptured = true;       // قفل التكرار
    }
//}

    
    
    
    
    
    
    
//    @FXML
//    void setpathaction(ActionEvent event) throws SQLException {
//        
//       ppoo=po.getText();
//       ssapcodee=sapcode.getText();
//       sstylee=style.getText();
//       ccustomerr=customer.getText();
//       wwashnamee=washname.getText();
//       ppoamountt=poamount.getText();
//       ccuttingamountt=cuttingamount.getText();
//       llaundrydatee=laundrydate.getText();
//       xxfacdatee=xfacdate.getText();
//        
//        
//// Get selected path from ComboBox
//String too = "";
//if (choosepath.getSelectionModel().getSelectedItem() != null) {
//    too = choosepath.getSelectionModel().getSelectedItem().toString();
//} else {
//    System.out.println("No path selected!");
//    return;
//}
//
//qtosendd=qtosend.getText();
//
//// Generate current date and time strings
//Date d1 = new Date();
//SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
//SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//String dateString2 = sdfDate.format(d1);
//
//// Check existence
//String fann = "";
//
//try {
//    String sql0 = "SELECT * FROM Shipment_Path WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? " +
//                  "AND Wash_Name = ? AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ? AND \"From\" = ? AND \"To\" = ?";
//    pst = conn.prepareStatement(sql0);
//    pst.setString(1, ppoo);
//    pst.setString(2, ssapcodee);
//    pst.setString(3, sstylee);
//    pst.setString(4, ccustomerr);
//    pst.setString(5, wwashnamee);
//    pst.setString(6, ppoamountt);
//    pst.setString(7, ccuttingamountt);
//    pst.setString(8, llaundrydatee);
//    pst.setString(9, xxfacdatee);
//    pst.setString(10, selsecc);
//    pst.setString(11, too);
//    rs = pst.executeQuery();
//
//    fann = rs.next() ? "found" : "not_found";
//} catch (Exception exception) {
//    exception.printStackTrace();
//} finally {
//    try {
//        if (rs != null) rs.close();
//        if (pst != null) pst.close();
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    }
//}
//
//// ------------------- UPDATE -------------------
//if (fann.equals("found")) {
//    try {
//        String sqlUpdate = "UPDATE Shipment_Path SET Date = ?, PO = ?, Sap_Code = ?, Style = ?, Customer = ?, Wash_Name = ?, " +
//              "PO_Amount = ?, Cutting_Amount = ?, Incoming_Date = ?, X_Fac_Date = ?, User = ?, \"From\" = ?, \"To\" = ?, Delivered = ?, Q_To_Send = ? " +
//              "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ? AND PO_Amount = ? " +
//              "AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ? AND \"From\" = ?";
//        pst = conn.prepareStatement(sqlUpdate);
//
//        // Set updated values
//        pst.setString(1, dateString2);
//        pst.setString(2, ppoo);
//        pst.setString(3, ssapcodee);
//        pst.setString(4, sstylee);
//        pst.setString(5, ccustomerr);
//        pst.setString(6, wwashnamee);
//        pst.setString(7, ppoamountt);
//        pst.setString(8, ccuttingamountt);
//        pst.setString(9, llaundrydatee);
//        pst.setString(10, xxfacdatee);
//        pst.setString(11, seluserr);
//        pst.setString(12, selsecc);
//        pst.setString(13, too);
//        pst.setString(14, "0");
//        pst.setString(15, qtosendd);
//
//        // WHERE clause values
//        pst.setString(16, ppoo);
//        pst.setString(17, ssapcodee);
//        pst.setString(18, sstylee);
//        pst.setString(19, ccustomerr);
//        pst.setString(20, wwashnamee);
//        pst.setString(21, ppoamountt);
//        pst.setString(22, ccuttingamountt);
//        pst.setString(23, llaundrydatee);
//        pst.setString(24, xxfacdatee);
//        pst.setString(25, selsecc);
//
//        pst.executeUpdate();
//
//        Notifications.create()
//            .title("Successful")
//            .text("Shipment updated successfully.")
//            .hideAfter(Duration.seconds(3))
//            .position(Pos.CENTER)
//            .showInformation();
//        
//               ////////////////////////Audit//////////////////////////
//      
//  
//    try {
//    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
//          this.pst = this.conn.prepareStatement(sqla);
//          this.pst.setString(1, value1);
//          this.pst.setString(2, selsecc);
//          this.pst.setString(3, seluserr);
//          this.pst.setString(4, seluserr+" updated a shipment, its data is : PO: "+ppoo+", Sap Code: "+ssapcodee+", Style: "+sstylee+", Customer: "+ccustomerr+", Wash Name: "+wwashnamee+", PO Amount: "+ppoamountt+", Cutting Amount: "+ccuttingamountt+"");
//          this.pst.execute();
//              }
//              catch (Exception e) {
//          
//        } finally {
//          try {
//            this.rs.close();
//            this.pst.close();
//          } catch (Exception exception) {}
//        }  
//      
//      ///////////////////////////////////////////////////////
//        
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//        try {
//            if (pst != null) pst.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//// ------------------- INSERT -------------------
//else if (fann.equals("not_found")) {
//    try {
//        // Generate a unique 10-digit random number
//        String uniqueId = "";
//        Random rand = new Random();
//        boolean isUnique = false;
//
//        while (!isUnique) {
//            uniqueId = String.format("%010d", rand.nextLong() % 1_000_000_0000L);
//            if (uniqueId.startsWith("-")) uniqueId = uniqueId.substring(1);
//
//            String checkId = "SELECT COUNT(*) FROM Shipment_Path WHERE Unique_ID = ?";
//            pst = conn.prepareStatement(checkId);
//            pst.setString(1, uniqueId);
//            rs = pst.executeQuery();
//            if (rs.next() && rs.getInt(1) == 0) {
//                isUnique = true;
//            }
//            rs.close();
//            pst.close();
//        }
//
//        String insertSql = "INSERT INTO Shipment_Path (Date, PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
//                           "Incoming_Date, X_Fac_Date, User, \"From\", \"To\", Delivered, Unique_ID, Q_To_Send) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        pst = conn.prepareStatement(insertSql);
//
//        pst.setString(1, dateString2);
//        pst.setString(2, ppoo);
//        pst.setString(3, ssapcodee);
//        pst.setString(4, sstylee);
//        pst.setString(5, ccustomerr);
//        pst.setString(6, wwashnamee);
//        pst.setString(7, ppoamountt);
//        pst.setString(8, ccuttingamountt);
//        pst.setString(9, llaundrydatee);
//        pst.setString(10, xxfacdatee);
//        pst.setString(11, seluserr);
//        pst.setString(12, selsecc);
//        pst.setString(13, too);
//        pst.setString(14, "0");
//        pst.setString(15, uniqueId);
//        pst.setString(16, qtosendd);
//
//        pst.executeUpdate();
//
//        Notifications.create()
//            .title("Successful")
//            .text("New shipment inserted successfully with ID: " + uniqueId)
//            .hideAfter(Duration.seconds(3))
//            .position(Pos.CENTER)
//            .showInformation();
//        
//          ////////////////////////Audit//////////////////////////
//      
//  
//    try {
//    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
//          this.pst = this.conn.prepareStatement(sqla);
//          this.pst.setString(1, value1);
//          this.pst.setString(2, selsecc);
//          this.pst.setString(3, seluserr);
//          this.pst.setString(4, seluserr+" added a new shipment, its data is : PO: "+ppoo+", Sap Code: "+ssapcodee+", Style: "+sstylee+", Customer: "+ccustomerr+", Wash Name: "+wwashnamee+", PO Amount: "+ppoamountt+", Cutting Amount: "+ccuttingamountt+"");
//          this.pst.execute();
//              }
//              catch (Exception e) {
//          
//        } finally {
//          try {
//            this.rs.close();
//            this.pst.close();
//          } catch (Exception exception) {}
//        }  
//      
//      ///////////////////////////////////////////////////////
//
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    } finally {
//        try {
//            if (pst != null) pst.close();
//            if (rs != null) rs.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
//
//        
//        
//        ////////////////////////////////////////////////////////////////////////////////
//
//    }
//    
    
    
 

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
                  "يرجى ملء جميع الحقول قبل المتابعة.");
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
        showNotification("لا يمكن الإرسال", "تم الوصول للحد الأقصى المسموح (" + maxAllowed + "). ولو في شحنات متعلقة عندك بلغ المستلم يقبلها او يرفضها");
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
          this.pst.setString(4, seluserr+" Inserted a new shipment , Quantity: "+qtosendd+", info and its data is: its data is : PO: "+ppoo+", Sap Code: "+ssapcodee+", Style: "+sstylee+", Customer: "+ccustomerr+", Wash Name: "+wwashnamee+", PO Amount: "+ppoamountt+", Cutting Amount: "+ccuttingamountt+", From: "+selsecc+", To: "+choosepath.getSelectionModel().getSelectedItem().toString()+".");
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
    
        //System.out.println("Section "+selsecc);
        
        
        if (selsecc.equals("WAREHOUSE_1")) {
            
            
            
            
            ppoo=WAREHOUSE_1_Controller.ppo;
            ssapcodee=WAREHOUSE_1_Controller.ssapcode;
            sstylee=WAREHOUSE_1_Controller.sstyle;
            ccustomerr=WAREHOUSE_1_Controller.ccustomer;
            wwashnamee=WAREHOUSE_1_Controller.wwashname;
            ppoamountt=WAREHOUSE_1_Controller.ppoamount;
            ccuttingamountt=WAREHOUSE_1_Controller.ccuttingamount;
            llaundrydatee=WAREHOUSE_1_Controller.llaundrydate;
            xxfacdatee=WAREHOUSE_1_Controller.xxfacdate;
            
            
        }
        
        else {
            
            
            ppoo=WASHINGController.ppo;
            ssapcodee=WASHINGController.ssapcode;
            sstylee=WASHINGController.sstyle;
            ccustomerr=WASHINGController.ccustomer;
            wwashnamee=WASHINGController.wwashname;
            ppoamountt=WASHINGController.ppoamount;
            ccuttingamountt=WASHINGController.ccuttingamount;
            llaundrydatee=WASHINGController.llaundrydate;
            xxfacdatee=WASHINGController.xxfacdate;
            
            
        }
        
        
            
//          po.setText(ppoo);
//          sapcode.setText(ssapcodee);
//          style.setText(sstylee);
//          customer.setText(ccustomerr);
//          washname.setText(wwashnamee);
//          poamount.setText(ppoamountt);
//          cuttingamount.setText(ccuttingamountt);
//          laundrydate.setText(llaundrydatee);
//          xfacdate.setText(xxfacdatee);

    //orderdata.setText(ppoo+" - "+ssapcodee+" - "+sstylee+" - "+ccustomerr+" - "+wwashnamee+" - "+ppoamountt+" - "+ccuttingamountt+" - "+llaundrydatee+" - "+xxfacdatee);
            
            ////////////////////////////////////////////////////////////////////////////////////////////
    
         
      //String recipenami=wwashnamee;
      
//      
//      /
//      
//      
//      
//      
      try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Recipe_Path=")) {
                    recpath=line.substring("Recipe_Path=".length()).trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      
      
       ///Decrypt////////////////////////////////////
         
    recipedata.clear();
    String dirpathe=recpath+"\\PRODUCTION\\"+ccustomerr+"\\"+wwashnamee+".ks";
    String dirpathe2=recpath+"\\PILOT\\"+ccustomerr+"\\"+wwashnamee+".ks";
//    
//    System.out.println("one "+dirpathe);
//    System.out.println("one "+dirpathe2);
//    
//    if (new File (dirpathe).exists()&&new File (dirpathe2).exists()) {
//        //Pro
//        
//        try {InputStream inputinstream=new FileInputStream(dirpathe);
//    BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
//    String lo;
//    while ((lo=bi.readLine())!=null) {
//        recipedata.appendText("\n"+lo
//       .replace("ﬦ","A")
//       .replace("ﬧ","B")
//       .replace("ﬨ","C")
//       .replace("﬩","D")
//       .replace("שׁ","E")    
//       .replace("שׂ","F")        
//       .replace("שּׁ","G")         
//       .replace("שּׂ","H")         
//       .replace("אַ","I")         
//       .replace("אָ","J")         
//       .replace("אּ","K")         
//       .replace("בּ","L")         
//       .replace("גּ","M")         
//       .replace("דּ","N")         
//       .replace("הּ","O")         
//       .replace("וּ","P")         
//       .replace("זּ","Q")         
//       .replace("טּ","R")         
//       .replace("יּ","S")         
//       .replace("ךּ","T")         
//       .replace("כּ","U")         
//       .replace("לּ","V")
//       .replace("מּ","W")         
//       .replace("נּ","X")         
//       .replace("סּ","Y")         
//       .replace("ףּ","Z")         
//       .replace("פּ","0")         
//       .replace("צּ","1")         
//       .replace("קּ","2")         
//       .replace("רּ","3")         
//       .replace("שּ","4")         
//       .replace("תּ","5")         
//       .replace("וֹ","6")         
//       .replace("בֿ","7")         
//       .replace("כֿ","8")
//       .replace("פֿ","9")
//       .replace("&NBSP;","")               
//      ); 
//    }
//    bi.close();
//        }catch (Exception g) {}
//        //////////////////////////////////////////////
//
//                            
//        
//       String stages=null;
//       int bathnumzzz=0;
//       String modu,comment;
//    
//        int ds=1;
//                            org.jsoup.nodes.Document docy = Jsoup.parse(recipedata.getText());
//                            for (Element table : docy.select("table")) {
//                            for (Element row : table.select("tr")) {
//                            Elements tds = row.select("td");
//                            if (tds.get(3).text().contains("/")||tds.get(3).text().contains("\\")||tds.get(3).text().isEmpty()||tds.get(3).text().contains("TEMP")||tds.get(3).text().contains("OPERATOR")||tds.get(3).text().contains("temp")||tds.get(3).text().contains("operator")/*||tds.get(3).text().contains("extract")||tds.get(3).text().contains("EXTRACT")||tds.get(3).text().contains("extraction")||tds.get(3).text().contains("EXTRACTION")*/||tds.get(3).text().matches("[0-9]+")||tds.get(3).text().contains("REMOV")||tds.get(3).text().contains("REMOVE")||tds.get(3).text().contains("BATH")||tds.get(3).text().contains("SAME")||tds.get(3).text().contains("PATH")||tds.get(3).text().contains("SAM")||tds.get(3).text().contains("RPM")||tds.get(3).text().contains("KG")||tds.get(3).text().contains("PCS")||tds.get(3).text().contains("DRAIN")||tds.get(3).text().contains("RIMOV")||tds.get(3).text().contains("RIMOVE")) {}
//                            else {
//                            String tempo=tds.get(3).text();
//                            if (tempo.contains("EXTRACT")||tempo.contains("Extract")||tempo.contains("extract")) {
//                               
//                                stages=stages+"\n"+"WASHING "+Integer.toString(ds++);
//                                
//                            }
//                            
//                            else {
//                                
//                                stages=stages+"\n"+tempo;
//                                
//                            }
//                            }
//                            
//
//                           }}
//                            
//                            
//                            
//                            
//        String arabicRegex = "[\\u0600-\\u06FF]+";
//        Pattern pattern = Pattern.compile(arabicRegex);
//        
//        Matcher matcher = pattern.matcher(stages + "");
//        String modifiedLine = matcher.replaceAll("\n");
//        String lone = modifiedLine.replace("null", "\n");
//        
//        StringBuilder result = new StringBuilder();
//        String[] lines = lone.split("\n");
//        int nonEmptyCount = 0;
//        // Count non-empty lines to handle the last one differently
//        for (String line : lines) {
//            if (!line.trim().isEmpty()) {
//                nonEmptyCount++;
//            }
//        }
//        
//        int currentNonEmpty = 0;
//        for (String line : lines) {
//            if (!line.trim().isEmpty()) {
//                currentNonEmpty++;
//                result.append(line);
//                // Append " - " only if it's not the last non-empty line
//                if (currentNonEmpty < nonEmptyCount) {
//                    result.append(" - ");
//                }
//            }
//        }                                   
//                            
//      procccc=result.toString();                
//      recipedata.clear();
//      //recipedata.setText(procccc);
//      System.out.println(procccc);
//         
//        
//    }
//    
//    
//    
//    
//    else if (new File (dirpathe).exists()&&!new File (dirpathe2).exists()) {
//        //Pro
//        System.out.println("Production Recipe only");
//        try {InputStream inputinstream=new FileInputStream(dirpathe);
//    BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
//    String lo;
//    while ((lo=bi.readLine())!=null) {
//        recipedata.appendText("\n"+lo
//       .replace("ﬦ","A")
//       .replace("ﬧ","B")
//       .replace("ﬨ","C")
//       .replace("﬩","D")
//       .replace("שׁ","E")    
//       .replace("שׂ","F")        
//       .replace("שּׁ","G")         
//       .replace("שּׂ","H")         
//       .replace("אַ","I")         
//       .replace("אָ","J")         
//       .replace("אּ","K")         
//       .replace("בּ","L")         
//       .replace("גּ","M")         
//       .replace("דּ","N")         
//       .replace("הּ","O")         
//       .replace("וּ","P")         
//       .replace("זּ","Q")         
//       .replace("טּ","R")         
//       .replace("יּ","S")         
//       .replace("ךּ","T")         
//       .replace("כּ","U")         
//       .replace("לּ","V")
//       .replace("מּ","W")         
//       .replace("נּ","X")         
//       .replace("סּ","Y")         
//       .replace("ףּ","Z")         
//       .replace("פּ","0")         
//       .replace("צּ","1")         
//       .replace("קּ","2")         
//       .replace("רּ","3")         
//       .replace("שּ","4")         
//       .replace("תּ","5")         
//       .replace("וֹ","6")         
//       .replace("בֿ","7")         
//       .replace("כֿ","8")
//       .replace("פֿ","9")
//       .replace("&NBSP;","")               
//      ); 
//    }
//    bi.close();
//        }catch (Exception g) {}
//        //////////////////////////////////////////////
//
//                            
//        
//       String stages=null;
//       int bathnumzzz=0;
//       String modu,comment;
//       int ds=1;
//        
//        
//                            org.jsoup.nodes.Document docy = Jsoup.parse(recipedata.getText());
//                            for (org.jsoup.nodes.Element tableu : docy.select("table")) {
//                            for (org.jsoup.nodes.Element row : tableu.select("tr")) {
//                            org.jsoup.select.Elements tds = row.select("td");
//                            if (tds.get(3).text().contains("/")||tds.get(3).text().contains("\\")||tds.get(3).text().isEmpty()||tds.get(3).text().contains("TEMP")||tds.get(3).text().contains("OPERATOR")||tds.get(3).text().contains("temp")||tds.get(3).text().contains("operator")/*||tds.get(3).text().contains("extract")||tds.get(3).text().contains("EXTRACT")||tds.get(3).text().contains("extraction")||tds.get(3).text().contains("EXTRACTION")*/||tds.get(3).text().matches("[0-9]+")||tds.get(3).text().contains("REMOV")||tds.get(3).text().contains("REMOVE")||tds.get(3).text().contains("BATH")||tds.get(3).text().contains("SAME")||tds.get(3).text().contains("PATH")||tds.get(3).text().contains("SAM")||tds.get(3).text().contains("RPM")||tds.get(3).text().contains("KG")||tds.get(3).text().contains("PCS")||tds.get(3).text().contains("DRAIN")||tds.get(3).text().contains("RIMOV")||tds.get(3).text().contains("RIMOVE")) {}
//                            else {
//                            String tempo=tds.get(3).text();
//                            if (tempo.contains("EXTRACT")||tempo.contains("Extract")||tempo.contains("extract")) {
//                               
//                                stages=stages+"\n"+"WASHING "+Integer.toString(ds++);
//                                
//                            }
//                            
//                            else {
//                                
//                                stages=stages+"\n"+tempo;
//                                
//                            }
//                            }
//                           }}
//                            
//                            
//                            
//                            
//        String arabicRegex = "[\\u0600-\\u06FF]+";
//        Pattern pattern = Pattern.compile(arabicRegex);
//        
//        Matcher matcher = pattern.matcher(stages + "");
//        String modifiedLine = matcher.replaceAll("\n");
//        String lone = modifiedLine.replace("null", "\n");
//        
//        StringBuilder result = new StringBuilder();
//        String[] lines = lone.split("\n");
//        int nonEmptyCount = 0;
//        // Count non-empty lines to handle the last one differently
//        for (String line : lines) {
//            if (!line.trim().isEmpty()) {
//                nonEmptyCount++;
//            }
//        }
//        
//        int currentNonEmpty = 0;
//        for (String line : lines) {
//            if (!line.trim().isEmpty()) {
//                currentNonEmpty++;
//                result.append(line);
//                // Append " - " only if it's not the last non-empty line
//                if (currentNonEmpty < nonEmptyCount) {
//                    result.append(" - ");
//                }
//            }
//        }                                   
//                            
//      procccc=result.toString();                
//      recipedata.clear();
//     // recipedata.setText(procccc);
//      System.out.println(procccc);
//         
//    }
//    
//    
//    
//    
//    
//    else if (!new File (dirpathe).exists()&&new File (dirpathe2).exists()) {
//        //Pilot
//        
//        try {InputStream inputinstream=new FileInputStream(dirpathe2);
//    BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
//    String lo;
//    while ((lo=bi.readLine())!=null) {
//        recipedata.appendText("\n"+lo
//       .replace("ﬦ","A")
//       .replace("ﬧ","B")
//       .replace("ﬨ","C")
//       .replace("﬩","D")
//       .replace("שׁ","E")    
//       .replace("שׂ","F")        
//       .replace("שּׁ","G")         
//       .replace("שּׂ","H")         
//       .replace("אַ","I")         
//       .replace("אָ","J")         
//       .replace("אּ","K")         
//       .replace("בּ","L")         
//       .replace("גּ","M")         
//       .replace("דּ","N")         
//       .replace("הּ","O")         
//       .replace("וּ","P")         
//       .replace("זּ","Q")         
//       .replace("טּ","R")         
//       .replace("יּ","S")         
//       .replace("ךּ","T")         
//       .replace("כּ","U")         
//       .replace("לּ","V")
//       .replace("מּ","W")         
//       .replace("נּ","X")         
//       .replace("סּ","Y")         
//       .replace("ףּ","Z")         
//       .replace("פּ","0")         
//       .replace("צּ","1")         
//       .replace("קּ","2")         
//       .replace("רּ","3")         
//       .replace("שּ","4")         
//       .replace("תּ","5")         
//       .replace("וֹ","6")         
//       .replace("בֿ","7")         
//       .replace("כֿ","8")
//       .replace("פֿ","9")
//       .replace("&NBSP;","")               
//      ); 
//    }
//    bi.close();
//        }catch (Exception g) {}
//        //////////////////////////////////////////////
//
//                            
//        
//       String stages=null;
//       int bathnumzzz=0;
//       String modu,comment;
//    
//        int ds=1;
//                            org.jsoup.nodes.Document docy = Jsoup.parse(recipedata.getText());
//                            for (Element table : docy.select("table")) {
//                            for (Element row : table.select("tr")) {
//                            Elements tds = row.select("td");
//                            if (tds.get(3).text().contains("/")||tds.get(3).text().contains("\\")||tds.get(3).text().isEmpty()||tds.get(3).text().contains("TEMP")||tds.get(3).text().contains("OPERATOR")||tds.get(3).text().contains("temp")||tds.get(3).text().contains("operator")/*||tds.get(3).text().contains("extract")||tds.get(3).text().contains("EXTRACT")||tds.get(3).text().contains("extraction")||tds.get(3).text().contains("EXTRACTION")*/||tds.get(3).text().matches("[0-9]+")||tds.get(3).text().contains("REMOV")||tds.get(3).text().contains("REMOVE")||tds.get(3).text().contains("BATH")||tds.get(3).text().contains("SAME")||tds.get(3).text().contains("PATH")||tds.get(3).text().contains("SAM")||tds.get(3).text().contains("RPM")||tds.get(3).text().contains("KG")||tds.get(3).text().contains("PCS")||tds.get(3).text().contains("DRAIN")||tds.get(3).text().contains("RIMOV")||tds.get(3).text().contains("RIMOVE")) {}
//                            else {
//                            String tempo=tds.get(3).text();
//                            if (tempo.contains("EXTRACT")||tempo.contains("Extract")||tempo.contains("extract")) {
//                               
//                                stages=stages+"\n"+"WASHING "+Integer.toString(ds++);
//                                
//                            }
//                            
//                            else {
//                                
//                                stages=stages+"\n"+tempo;
//                                
//                            }
//                            }
//                            
//
//                           }}
//                            
//                            
//                            
//                            
//        String arabicRegex = "[\\u0600-\\u06FF]+";
//        Pattern pattern = Pattern.compile(arabicRegex);
//        
//        Matcher matcher = pattern.matcher(stages + "");
//        String modifiedLine = matcher.replaceAll("\n");
//        String lone = modifiedLine.replace("null", "\n");
//        
//        StringBuilder result = new StringBuilder();
//        String[] lines = lone.split("\n");
//        int nonEmptyCount = 0;
//        // Count non-empty lines to handle the last one differently
//        for (String line : lines) {
//            if (!line.trim().isEmpty()) {
//                nonEmptyCount++;
//            }
//        }
//        
//        int currentNonEmpty = 0;
//        for (String line : lines) {
//            if (!line.trim().isEmpty()) {
//                currentNonEmpty++;
//                result.append(line);
//                // Append " - " only if it's not the last non-empty line
//                if (currentNonEmpty < nonEmptyCount) {
//                    result.append(" - ");
//                }
//            }
//        }                                   
//                            
//      procccc=result.toString();                
//      recipedata.clear();                      
//      //recipedata.setText(procccc);
//      System.out.println(procccc);
//         
//    }
//    
//    else if (!new File (dirpathe).exists()&&!new File (dirpathe2).exists()) {
//        //Not Fount
//        System.out.println("Recipe not found");
//        recipedata.clear();
//       // recipedata.setText("الريسيبي غير موجودة");
//        //tff.setText("الريسيبي غير موجودة");
//    }
//    
//    else {
//      
//    }
//  
         
        
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
          
//          Platform.runLater(() -> {
//    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
//      //  System.out.println("Selection changed 1");
//        // باقي الكود
//    });
//});

          
          
          
          
          
          
          
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
        qtosend.setText(getCell(row, 9));
        
        
      //  System.out.println(getCell(row, 4));
      String recipenamiy=getCell(row, 4);
      
      //System.out.println("Hello "+recipenamiy);
      
      Platform.runLater(()-> {
        
              
      try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Recipe_Path=")) {
                    recpath=line.substring("Recipe_Path=".length()).trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      
      
       ///Decrypt////////////////////////////////////
         
    recipedata.clear();
    String dirpathey=recpath+"\\PRODUCTION\\"+customer.getText()+"\\"+recipenamiy+".ks";
    String dirpathe2y=recpath+"\\PILOT\\"+customer.getText()+"\\"+recipenamiy+".ks";
    
    System.out.println("Hello "+dirpathey);
    System.out.println("Hello "+dirpathe2y);
    
    if (new File (dirpathey).exists()&&new File (dirpathe2y).exists()) {
        //Pro
        
        try {InputStream inputinstream=new FileInputStream(dirpathey);
    BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        recipedata.appendText("\n"+lo
       .replace("ﬦ","A")
       .replace("ﬧ","B")
       .replace("ﬨ","C")
       .replace("﬩","D")
       .replace("שׁ","E")    
       .replace("שׂ","F")        
       .replace("שּׁ","G")         
       .replace("שּׂ","H")         
       .replace("אַ","I")         
       .replace("אָ","J")         
       .replace("אּ","K")         
       .replace("בּ","L")         
       .replace("גּ","M")         
       .replace("דּ","N")         
       .replace("הּ","O")         
       .replace("וּ","P")         
       .replace("זּ","Q")         
       .replace("טּ","R")         
       .replace("יּ","S")         
       .replace("ךּ","T")         
       .replace("כּ","U")         
       .replace("לּ","V")
       .replace("מּ","W")         
       .replace("נּ","X")         
       .replace("סּ","Y")         
       .replace("ףּ","Z")         
       .replace("פּ","0")         
       .replace("צּ","1")         
       .replace("קּ","2")         
       .replace("רּ","3")         
       .replace("שּ","4")         
       .replace("תּ","5")         
       .replace("וֹ","6")         
       .replace("בֿ","7")         
       .replace("כֿ","8")
       .replace("פֿ","9")
       .replace("&NBSP;","")               
      ); 
    }
    bi.close();
        }catch (Exception g) {}
        //////////////////////////////////////////////

                            
        
       String stages=null;
       int bathnumzzz=0;
       String modu,comment;
    
        int ds=1;
                            org.jsoup.nodes.Document docy = Jsoup.parse(recipedata.getText());
                            for (Element table : docy.select("table")) {
                            for (Element rowu : table.select("tr")) {
                            Elements tds = rowu.select("td");
                            if (tds.get(3).text().contains("/")||tds.get(3).text().contains("\\")||tds.get(3).text().isEmpty()||tds.get(3).text().contains("TEMP")||tds.get(3).text().contains("OPERATOR")||tds.get(3).text().contains("temp")||tds.get(3).text().contains("operator")/*||tds.get(3).text().contains("extract")||tds.get(3).text().contains("EXTRACT")||tds.get(3).text().contains("extraction")||tds.get(3).text().contains("EXTRACTION")*/||tds.get(3).text().matches("[0-9]+")||tds.get(3).text().contains("REMOV")||tds.get(3).text().contains("REMOVE")||tds.get(3).text().contains("BATH")||tds.get(3).text().contains("SAME")||tds.get(3).text().contains("PATH")||tds.get(3).text().contains("SAM")||tds.get(3).text().contains("RPM")||tds.get(3).text().contains("KG")||tds.get(3).text().contains("PCS")||tds.get(3).text().contains("DRAIN")||tds.get(3).text().contains("RIMOV")||tds.get(3).text().contains("RIMOVE")) {}
                            else {
                            String tempo=tds.get(3).text();
                            if (tempo.contains("EXTRACT")||tempo.contains("Extract")||tempo.contains("extract")) {
                               
                                stages=stages+"\n"+"WASHING "+Integer.toString(ds++);
                                
                            }
                            
                            else {
                                
                                stages=stages+"\n"+tempo;
                                
                            }
                            }
                            

                           }}
                            
                            
                            
                            
        String arabicRegex = "[\\u0600-\\u06FF]+";
        Pattern pattern = Pattern.compile(arabicRegex);
        
        Matcher matcher = pattern.matcher(stages + "");
        String modifiedLine = matcher.replaceAll("\n");
        String lone = modifiedLine.replace("null", "\n");
        
        StringBuilder result = new StringBuilder();
        String[] lines = lone.split("\n");
        int nonEmptyCount = 0;
        // Count non-empty lines to handle the last one differently
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                nonEmptyCount++;
            }
        }
        
        int currentNonEmpty = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                currentNonEmpty++;
                result.append(line);
                // Append " - " only if it's not the last non-empty line
                if (currentNonEmpty < nonEmptyCount) {
                    result.append(" - ");
                }
            }
        }                                   
                            
      procccc=result.toString();                
                            
      recipedata.setText(procccc);
      System.out.println(procccc);
         
        
    }
    
    
    
    
    else if (new File (dirpathe).exists()&&!new File (dirpathe2).exists()) {
        //Pro
        System.out.println("Production Recipe only");
        try {InputStream inputinstream=new FileInputStream(dirpathe);
    BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        recipedata.appendText("\n"+lo
       .replace("ﬦ","A")
       .replace("ﬧ","B")
       .replace("ﬨ","C")
       .replace("﬩","D")
       .replace("שׁ","E")    
       .replace("שׂ","F")        
       .replace("שּׁ","G")         
       .replace("שּׂ","H")         
       .replace("אַ","I")         
       .replace("אָ","J")         
       .replace("אּ","K")         
       .replace("בּ","L")         
       .replace("גּ","M")         
       .replace("דּ","N")         
       .replace("הּ","O")         
       .replace("וּ","P")         
       .replace("זּ","Q")         
       .replace("טּ","R")         
       .replace("יּ","S")         
       .replace("ךּ","T")         
       .replace("כּ","U")         
       .replace("לּ","V")
       .replace("מּ","W")         
       .replace("נּ","X")         
       .replace("סּ","Y")         
       .replace("ףּ","Z")         
       .replace("פּ","0")         
       .replace("צּ","1")         
       .replace("קּ","2")         
       .replace("רּ","3")         
       .replace("שּ","4")         
       .replace("תּ","5")         
       .replace("וֹ","6")         
       .replace("בֿ","7")         
       .replace("כֿ","8")
       .replace("פֿ","9")
       .replace("&NBSP;","")               
      ); 
    }
    bi.close();
        }catch (Exception g) {}
        //////////////////////////////////////////////

                            
        
       String stages=null;
       int bathnumzzz=0;
       String modu,comment;
       int ds=1;
        
        
                            org.jsoup.nodes.Document docy = Jsoup.parse(recipedata.getText());
                            for (org.jsoup.nodes.Element tableu : docy.select("table")) {
                            for (org.jsoup.nodes.Element rowt : tableu.select("tr")) {
                            org.jsoup.select.Elements tds = rowt.select("td");
                            if (tds.get(3).text().contains("/")||tds.get(3).text().contains("\\")||tds.get(3).text().isEmpty()||tds.get(3).text().contains("TEMP")||tds.get(3).text().contains("OPERATOR")||tds.get(3).text().contains("temp")||tds.get(3).text().contains("operator")/*||tds.get(3).text().contains("extract")||tds.get(3).text().contains("EXTRACT")||tds.get(3).text().contains("extraction")||tds.get(3).text().contains("EXTRACTION")*/||tds.get(3).text().matches("[0-9]+")||tds.get(3).text().contains("REMOV")||tds.get(3).text().contains("REMOVE")||tds.get(3).text().contains("BATH")||tds.get(3).text().contains("SAME")||tds.get(3).text().contains("PATH")||tds.get(3).text().contains("SAM")||tds.get(3).text().contains("RPM")||tds.get(3).text().contains("KG")||tds.get(3).text().contains("PCS")||tds.get(3).text().contains("DRAIN")||tds.get(3).text().contains("RIMOV")||tds.get(3).text().contains("RIMOVE")) {}
                            else {
                            String tempo=tds.get(3).text();
                            if (tempo.contains("EXTRACT")||tempo.contains("Extract")||tempo.contains("extract")) {
                               
                                stages=stages+"\n"+"WASHING "+Integer.toString(ds++);
                                
                            }
                            
                            else {
                                
                                stages=stages+"\n"+tempo;
                                
                            }
                            }
                           }}
                            
                            
                            
                            
        String arabicRegex = "[\\u0600-\\u06FF]+";
        Pattern pattern = Pattern.compile(arabicRegex);
        
        Matcher matcher = pattern.matcher(stages + "");
        String modifiedLine = matcher.replaceAll("\n");
        String lone = modifiedLine.replace("null", "\n");
        
        StringBuilder result = new StringBuilder();
        String[] lines = lone.split("\n");
        int nonEmptyCount = 0;
        // Count non-empty lines to handle the last one differently
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                nonEmptyCount++;
            }
        }
        
        int currentNonEmpty = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                currentNonEmpty++;
                result.append(line);
                // Append " - " only if it's not the last non-empty line
                if (currentNonEmpty < nonEmptyCount) {
                    result.append(" - ");
                }
            }
        }                                   
                            
      procccc=result.toString();                
      //tff.setText(procccc);
      recipedata.setText(procccc);
      System.out.println(procccc);
         
    }
    
    
    
    
    
    else if (!new File (dirpathe).exists()&&new File (dirpathe2).exists()) {
        //Pilot
        
        try {InputStream inputinstream=new FileInputStream(dirpathe2);
    BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        recipedata.appendText("\n"+lo
       .replace("ﬦ","A")
       .replace("ﬧ","B")
       .replace("ﬨ","C")
       .replace("﬩","D")
       .replace("שׁ","E")    
       .replace("שׂ","F")        
       .replace("שּׁ","G")         
       .replace("שּׂ","H")         
       .replace("אַ","I")         
       .replace("אָ","J")         
       .replace("אּ","K")         
       .replace("בּ","L")         
       .replace("גּ","M")         
       .replace("דּ","N")         
       .replace("הּ","O")         
       .replace("וּ","P")         
       .replace("זּ","Q")         
       .replace("טּ","R")         
       .replace("יּ","S")         
       .replace("ךּ","T")         
       .replace("כּ","U")         
       .replace("לּ","V")
       .replace("מּ","W")         
       .replace("נּ","X")         
       .replace("סּ","Y")         
       .replace("ףּ","Z")         
       .replace("פּ","0")         
       .replace("צּ","1")         
       .replace("קּ","2")         
       .replace("רּ","3")         
       .replace("שּ","4")         
       .replace("תּ","5")         
       .replace("וֹ","6")         
       .replace("בֿ","7")         
       .replace("כֿ","8")
       .replace("פֿ","9")
       .replace("&NBSP;","")               
      ); 
    }
    bi.close();
        }catch (Exception g) {}
        //////////////////////////////////////////////

                            
        
       String stages=null;
       int bathnumzzz=0;
       String modu,comment;
    
        int ds=1;
                            org.jsoup.nodes.Document docy = Jsoup.parse(recipedata.getText());
                            for (Element table : docy.select("table")) {
                            for (Element rowy : table.select("tr")) {
                            Elements tds = rowy.select("td");
                            if (tds.get(3).text().contains("/")||tds.get(3).text().contains("\\")||tds.get(3).text().isEmpty()||tds.get(3).text().contains("TEMP")||tds.get(3).text().contains("OPERATOR")||tds.get(3).text().contains("temp")||tds.get(3).text().contains("operator")/*||tds.get(3).text().contains("extract")||tds.get(3).text().contains("EXTRACT")||tds.get(3).text().contains("extraction")||tds.get(3).text().contains("EXTRACTION")*/||tds.get(3).text().matches("[0-9]+")||tds.get(3).text().contains("REMOV")||tds.get(3).text().contains("REMOVE")||tds.get(3).text().contains("BATH")||tds.get(3).text().contains("SAME")||tds.get(3).text().contains("PATH")||tds.get(3).text().contains("SAM")||tds.get(3).text().contains("RPM")||tds.get(3).text().contains("KG")||tds.get(3).text().contains("PCS")||tds.get(3).text().contains("DRAIN")||tds.get(3).text().contains("RIMOV")||tds.get(3).text().contains("RIMOVE")) {}
                            else {
                            String tempo=tds.get(3).text();
                            if (tempo.contains("EXTRACT")||tempo.contains("Extract")||tempo.contains("extract")) {
                               
                                stages=stages+"\n"+"WASHING "+Integer.toString(ds++);
                                
                            }
                            
                            else {
                                
                                stages=stages+"\n"+tempo;
                                
                            }
                            }
                            

                           }}
                            
                            
                            
                            
        String arabicRegex = "[\\u0600-\\u06FF]+";
        Pattern pattern = Pattern.compile(arabicRegex);
        
        Matcher matcher = pattern.matcher(stages + "");
        String modifiedLine = matcher.replaceAll("\n");
        String lone = modifiedLine.replace("null", "\n");
        
        StringBuilder result = new StringBuilder();
        String[] lines = lone.split("\n");
        int nonEmptyCount = 0;
        // Count non-empty lines to handle the last one differently
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                nonEmptyCount++;
            }
        }
        
        int currentNonEmpty = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                currentNonEmpty++;
                result.append(line);
                // Append " - " only if it's not the last non-empty line
                if (currentNonEmpty < nonEmptyCount) {
                    result.append(" - ");
                }
            }
        }                                   
                            
      procccc=result.toString();                
                            
      recipedata.setText(procccc);
      System.out.println(procccc);
         
    }
    
    
   else if (!new File (dirpathe).exists()&&!new File (dirpathe2).exists()) {
        //Not Fount
        System.out.println("Recipe not found");
        recipedata.setText("الريسيبي غير موجودة");
        //tff.setText("الريسيبي غير موجودة");
    }
    
    else {
      
    }
    
          
          
    });
  
    
  
          
        

    }
});
       

      
        
        
    }

// Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}    
    
}
