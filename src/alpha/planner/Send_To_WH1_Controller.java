
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class Send_To_WH1_Controller implements Initializable {

   

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
    private JFXTextField received;

    @FXML
    private JFXTextField minusline;
    
    @FXML
    private TableView<ObservableList<String>> table;


    @FXML
    private JFXButton setpath;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    public static String ppoo,ssapcodee,sstylee,ccustomerr,wwashnamee,ppoamountt,ccuttingamountt,llaundrydatee,xxfacdatee,recpath,procccc,receivedd,minuslinee;
    public static String selsecc,seluserr;
    public static String value1;

    @FXML
    private Label newminus;
    
    
    
    
    @FXML
void rekeyaction(KeyEvent event) {
    try {
        // Parse cutting amount & received amount safely
        String s1 = cuttingamount.getText();
        String s2 = received.getText();

        int cuttingAmount = (s1 != null && !s1.trim().isEmpty()) ? Integer.parseInt(s1.trim()) : 0;
        int receivedVal = (s2 != null && !s2.trim().isEmpty()) ? Integer.parseInt(s2.trim()) : 0;

        // Get minus_lines from DB for matching PO, SAP Code, Style, Customer, Wash Name
        int minusLinesFromDB = 0;

        try {
            String sql = "SELECT Received FROM WareHouse_1 " +
                         "WHERE PO=? AND Sap_Code=? AND Style=? AND Customer=? AND Wash_Name=?";

            this.pst = this.conn.prepareStatement(sql);
            this.pst.setString(1, po.getText().trim());
            this.pst.setString(2, sapcode.getText().trim());
            this.pst.setString(3, style.getText().trim());
            this.pst.setString(4, customer.getText().trim());
            this.pst.setString(5, washname.getText().trim());

            this.rs = this.pst.executeQuery();
            if (this.rs.next()) {
                minusLinesFromDB = this.rs.getInt("Received");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ex) {}
        }

        // Calculate new minus_lines using your formula
        int newMinusLines = cuttingAmount - (receivedVal + minusLinesFromDB);

        if (newMinusLines < 0) {
            minusline.clear();
            newminus.setText("0");
            received.clear();
            Notifications.create()
                .title("خطا فادح")
                .text("المفروض الكمية المقصوصة كده اقل من اللي هتبعته للمخزن ودا غلط")
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER)
                .showError();
            
        }
        
        else if (s2.contains("-")) {
            minusline.clear();
            newminus.setText("0");
            received.clear();
            Notifications.create()
                .title("خطا فادح")
                .text("مينفعش الرقم يكون فيه اي سالب ي معلم")
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER)
                .showError();
        }
        else {
            
            minusline.setText(String.valueOf(newMinusLines));
            
            
            Platform.runLater(() -> {
            
            newminus.setText(String.valueOf(newMinusLines));
            
            if (String.valueOf(newMinusLines).equals("0")) {
                Notifications.create()
                .title("الكمية خلصت")
                .text(" ✨❤بص المفروض كده الاوردر مفيش حاجة لسه في الخطوط فلو عاوز تمسحة من المخزن عندك ايقونة الحذف فوق علي اليمين")
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER)
                .showError();
            }
            else {}
            
            
        });
            
            
        
        }

    } catch (NumberFormatException e) {
        minusline.setText("0");
        newminus.setText("0");
        System.out.println("Invalid number format: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    
    
    
//     @FXML
//void rekeyaction(KeyEvent event) {
//    try {
//        String s1 = cuttingamount.getText();
//        String s2 = received.getText();
//
//        // Default to 0 if input is empty or not a number
//        int num1 = (s1 != null && !s1.trim().isEmpty()) ? Integer.parseInt(s1.trim()) : 0;
//        int num2 = (s2 != null && !s2.trim().isEmpty()) ? Integer.parseInt(s2.trim()) : 0;
//
//        int num3 = num1 - num2;
//        String saa=String.valueOf(num3);
//        if (saa.contains("-")) {
//            minusline.clear();
//            received.clear();
//        Notifications.create()
//        .title("خطا فادح")
//        .text("المفروض الكمية المقصوصة كده اقل من اللي هتبعته للمخزن ودا غلط")
//        .hideAfter(Duration.seconds(5))
//        .position(Pos.CENTER)
//        .showError();
//        }
//        
//        else {
//            minusline.setText(String.valueOf(num3));
//        }
//        
//        
//
//    } catch (NumberFormatException e) {
//        // Optional: show warning if input is not valid
//        minusline.setText("0"); // fallback
//        System.out.println("Invalid number format: " + e.getMessage());
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}

    
    
    
    
    
  @FXML
void setpathaction(ActionEvent event) {

    // --- Get field values ---
    ppoo = po.getText();
    ssapcodee = sapcode.getText();
    sstylee = style.getText();
    ccustomerr = customer.getText();
    wwashnamee = washname.getText();
    ppoamountt = poamount.getText();
    ccuttingamountt = cuttingamount.getText();
    llaundrydatee = laundrydate.getText();
    xxfacdatee = xfacdate.getText();
    
    receivedd = received.getText();
    minuslinee = minusline.getText();

    // --- Validate empty fields ---
    if (ppoo.isEmpty() || ssapcodee.isEmpty() || sstylee.isEmpty() ||
        ccustomerr.isEmpty() || wwashnamee.isEmpty() || ppoamountt.isEmpty() ||
        ccuttingamountt.isEmpty() || llaundrydatee.isEmpty() || xxfacdatee.isEmpty() ||
        receivedd.isEmpty() || minuslinee.isEmpty()) {

        Notifications.create()
            .title("خطأ فادح")
            .text("كمل كل البيانات علشان نكمل لان فيه بيانات ناقصة")
            .hideAfter(Duration.seconds(3))
            .position(Pos.CENTER)
            .showError();
        return;
    }

    // --- Validate positive values ---
    try {
        int receivedValue = Integer.parseInt(receivedd);
        int minuslineValue = Integer.parseInt(minuslinee);

        if (receivedValue <= 0 ) {
            Notifications.create()
                .title("خطأ فادح")
                .text("مينفعش تبعت صفر للمخزن مفيش شحنات مصفرة بتتقبل ي معلم")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showError();
            return;
        }

    } catch (NumberFormatException e) {
        Notifications.create()
            .title("خطأ فادح")
            .text("الكمية المستلمة والباقية في الخطوط لازم يكونوا قيم صحيحة")
            .hideAfter(Duration.seconds(3))
            .position(Pos.CENTER)
            .showError();
        return;
    }

    // Check existence
    String fann = "";

    try {
        String sql0 = "SELECT * FROM WareHouse_1 WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? " +
                      "AND Wash_Name = ? AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ?";
        pst = conn.prepareStatement(sql0);
        pst.setString(1, ppoo);
        pst.setString(2, ssapcodee);
        pst.setString(3, sstylee);
        pst.setString(4, ccustomerr);
        pst.setString(5, wwashnamee);
        pst.setString(6, ppoamountt);
        pst.setString(7, ccuttingamountt);
        pst.setString(8, llaundrydatee);
        pst.setString(9, xxfacdatee);
        rs = pst.executeQuery();

        fann = rs.next() ? "found" : "not_found";
    } catch (Exception exception) {
        exception.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ------------------- UPDATE -------------------
    if (fann.equals("found")) {

        Alert alertd = new Alert(Alert.AlertType.CONFIRMATION);
        alertd.setTitle("Receive Order");
        alertd.setHeaderText("لقد وجدنا اوردر مسبقا بنفس البيانات");
        alertd.setContentText("ماذا تريد ان تفعل الان؟");
        ButtonType buttonTypeOned = new ButtonType("تحديث");
        ButtonType buttonTypeCanceld = new ButtonType("اضافة جديد");
        ButtonType cancelBtn = new ButtonType("الغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertd.getButtonTypes().setAll(buttonTypeOned, buttonTypeCanceld, cancelBtn);
        DialogPane dialogPaneid = alertd.getDialogPane();
          //////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
  if (themooo.equals("cupertino-dark.css")) {
      //Dark
      dialogPaneid.getStylesheets().add(
      getClass().getResource("cupertino-dark.css").toExternalForm());
  }
  else {
      //Light
      dialogPaneid.getStylesheets().add(
      getClass().getResource("cupertino-light.css").toExternalForm());
  }
  }
  catch (Exception re) {}
  //////////////////////////////////////////////////////////////////////////////////////////////////////  
        Optional<ButtonType> resultsd = alertd.showAndWait();

        if (resultsd.isPresent() && resultsd.get() == buttonTypeOned) {

            try {
                String rreceivedd = received.getText();

                String sqlSelect = "SELECT Received, Minus_Lines FROM WareHouse_1 WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ? AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ?";
                PreparedStatement selectStmt = conn.prepareStatement(sqlSelect);
                selectStmt.setString(1, ppoo);
                selectStmt.setString(2, ssapcodee);
                selectStmt.setString(3, sstylee);
                selectStmt.setString(4, ccustomerr);
                selectStmt.setString(5, wwashnamee);
                selectStmt.setString(6, ppoamountt);
                selectStmt.setString(7, ccuttingamountt);
                selectStmt.setString(8, llaundrydatee);
                selectStmt.setString(9, xxfacdatee);

                ResultSet rs = selectStmt.executeQuery();

                int currentReceived = 0;
                int currentMinus = 0;

                if (rs.next()) {
                    currentReceived = rs.getInt("Received");
                    currentMinus = rs.getInt("Minus_Lines");
                }

                rs.close();
                selectStmt.close();

                int newReceivedQty = Integer.parseInt(rreceivedd);
                int updatedReceived = currentReceived + newReceivedQty;
                int updatedMinus = currentMinus - newReceivedQty;

                if (updatedMinus < 0) {
                    Notifications.create()
                        .title("خطأ في التحديث")
                        .text("لا يمكن التحديث لأن قيمة وهتكون قيمتها " + updatedMinus + " Minus_Lines ستصبح سالبة!")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER)
                        .showError();
                    return;
                }

                String sqlUpdate = "UPDATE WareHouse_1 SET Received = ?, Minus_Lines = ? WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ? AND PO_Amount = ? AND Cutting_Amount = ? AND Incoming_Date = ? AND X_Fac_Date = ?";
                pst = conn.prepareStatement(sqlUpdate);
                pst.setInt(1, updatedReceived);
                pst.setInt(2, updatedMinus);
                pst.setString(3, ppoo);
                pst.setString(4, ssapcodee);
                pst.setString(5, sstylee);
                pst.setString(6, ccustomerr);
                pst.setString(7, wwashnamee);
                pst.setString(8, ppoamountt);
                pst.setString(9, ccuttingamountt);
                pst.setString(10, llaundrydatee);
                pst.setString(11, xxfacdatee);

                pst.executeUpdate();

                Notifications.create()
                    .title("Successful")
                    .text("تم تحديث البيانات بنجاح")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER)
                    .showInformation();

                try {
                    String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?,?,?,?)";
                    this.pst = this.conn.prepareStatement(sqla);
                    this.pst.setString(1, value1);
                    this.pst.setString(2, selsecc);
                    this.pst.setString(3, seluserr);
                    this.pst.setString(4, seluserr + " updated received for PO: " + ppoo +
                        ". New received: " + updatedReceived + ", Minus: " + updatedMinus);
                    this.pst.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (this.rs != null) this.rs.close();
                        if (this.pst != null) this.pst.close();
                    } catch (Exception ex) {}
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pst != null) pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (resultsd.isPresent() && resultsd.get() == buttonTypeCanceld) {

            try {
                String rreceivedd = received.getText();
                String mminuslinee = minusline.getText();

                String insertSql = "INSERT INTO WareHouse_1 (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, Incoming_Date, X_Fac_Date, Received, Minus_Lines) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(insertSql);
                pst.setString(1, ppoo);
                pst.setString(2, ssapcodee);
                pst.setString(3, sstylee);
                pst.setString(4, ccustomerr);
                pst.setString(5, wwashnamee);
                pst.setString(6, ppoamountt);
                pst.setString(7, ccuttingamountt);
                pst.setString(8, llaundrydatee);
                pst.setString(9, xxfacdatee);
                pst.setString(10, rreceivedd);
                pst.setString(11, mminuslinee);
                pst.executeUpdate();

                Notifications.create()
                    .title("Successful")
                    .text("أوردر جديد تم اضافته للمحزن الاول")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER)
                    .showInformation();

                try {
                    String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?,?,?,?)";
                    this.pst = this.conn.prepareStatement(sqla);
                    this.pst.setString(1, value1);
                    this.pst.setString(2, selsecc);
                    this.pst.setString(3, seluserr);
                    this.pst.setString(4, seluserr + " added a new order to warehouse 1, its data is : PO: " + ppoo +
                        ", Sap Code: " + ssapcodee + ", Style: " + sstylee + ", Customer: " + ccustomerr +
                        ", Wash Name: " + wwashnamee + ", PO Amount: " + ppoamountt + ", Cutting Amount: " +
                        ccuttingamountt + ", Received: " + rreceivedd + ", Minus Line: " + mminuslinee);
                    this.pst.execute();
                } catch (Exception e) {
                } finally {
                    try {
                        this.rs.close();
                        this.pst.close();
                    } catch (Exception exception) {}
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (pst != null) pst.close();
                    if (rs != null) rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else {
            return;
        }
    }

    // ------------------- INSERT -------------------
    else if (fann.equals("not_found")) {
        try {
            String rreceivedd = received.getText();
            String mminuslinee = minusline.getText();

            String insertSql = "INSERT INTO WareHouse_1 (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, Incoming_Date, X_Fac_Date, Received, Minus_Lines) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(insertSql);
            pst.setString(1, ppoo);
            pst.setString(2, ssapcodee);
            pst.setString(3, sstylee);
            pst.setString(4, ccustomerr);
            pst.setString(5, wwashnamee);
            pst.setString(6, ppoamountt);
            pst.setString(7, ccuttingamountt);
            pst.setString(8, llaundrydatee);
            pst.setString(9, xxfacdatee);
            pst.setString(10, rreceivedd);
            pst.setString(11, mminuslinee);
            pst.executeUpdate();

            Notifications.create()
                .title("Successful")
                .text("أوردر جديد تم اضافته للمحزن الاول")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showInformation();

            try {
                String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?,?,?,?)";
                this.pst = this.conn.prepareStatement(sqla);
                this.pst.setString(1, value1);
                this.pst.setString(2, selsecc);
                this.pst.setString(3, seluserr);
                this.pst.setString(4, seluserr + " added a new order to warehouse 1, its data is : PO: " + ppoo +
                    ", Sap Code: " + ssapcodee + ", Style: " + sstylee + ", Customer: " + ccustomerr +
                    ", Wash Name: " + wwashnamee + ", PO Amount: " + ppoamountt + ", Cutting Amount: " +
                    ccuttingamountt + ", Received: " + rreceivedd + ", Minus Line: " + mminuslinee);
                this.pst.execute();
            } catch (Exception e) {
            } finally {
                try {
                    this.rs.close();
                    this.pst.close();
                } catch (Exception exception) {}
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (rs != null) rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    received.clear();
    minusline.clear();
    
    
      table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Orders";
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





    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
       
          this.conn = db.java_db();
    
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;
    
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
            
            
//          po.setText(ppoo);
//          sapcode.setText(ssapcodee);
//          style.setText(sstylee);
//          customer.setText(ccustomerr);
//          washname.setText(wwashnamee);
//          poamount.setText(ppoamountt);
//          cuttingamount.setText(ccuttingamountt);
//          laundrydate.setText(llaundrydatee);
//          xfacdate.setText(xxfacdatee);
          
          
          
          
          
           table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Orders";
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
          
          
          
          
          
             
table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
     
        ObservableList<String> row = newSelection;
        
        po.setText(getCell(row, 0));
        sapcode.setText(getCell(row, 1));
        style.setText(getCell(row, 2));
        customer.setText(getCell(row, 3));
        washname.setText(getCell(row, 4));
        poamount.setText(getCell(row, 5));
        cuttingamount.setText(getCell(row, 6));
        laundrydate.setText(getCell(row, 7));
        xfacdate.setText(getCell(row, 8));
        
        received.setText("0");
        minusline.setText("0");
        newminus.setText("0");
        
//        received.setText(getCell(row, 8));
//        
//        
//        try {
//        String s1 = cuttingamount.getText();
//        String s2 = received.getText();
//
//        // Default to 0 if input is empty or not a number
//        int num1 = (s1 != null && !s1.trim().isEmpty()) ? Integer.parseInt(s1.trim()) : 0;
//        int num2 = (s2 != null && !s2.trim().isEmpty()) ? Integer.parseInt(s2.trim()) : 0;
//
//        int num3 = num1 - num2;
//        minusline.setText(String.valueOf(num3));
//
//    } catch (NumberFormatException e) {
//        // Optional: show warning if input is not valid
//        minusline.setText("0"); // fallback
//        System.out.println("Invalid number format: " + e.getMessage());
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
     
          
        

    }
});
       
      
  
        
        
    }  
    
// Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}    
    
    
    
}
