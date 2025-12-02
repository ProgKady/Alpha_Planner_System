
package alpha.planner;

import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.apache.commons.text.similarity.LevenshteinDistance;
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
public class WAREHOUSE_1_Controller implements Initializable {

    Timer fileCheckTimer;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    
    private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    static String databaseUrl; // Path to your SQLite DB
    private ObservableList<String> columnNames = FXCollections.observableArrayList();
    
     final private String plan_template="";
    
    static String vvaall;
    
    static String vaa,fgg,proco,timo,timooo,fggf;
   
    private ObservableList<ObservableList<String>> dataaaaa;
    
    public static int oro=0;
    
    public static int alloro=0;
    
    public static int ski=0;
    
    public static int allski=0;
    
    public static String erra="";
    
    public static String allerra="";
    
       @FXML
    private JFXButton inbox;

    
    
    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem refresh;

    @FXML
    private MenuItem addorder,stocky;

    @FXML
    private MenuItem updateorder;
    
    @FXML
    private Menu ships;

    @FXML
    private MenuItem deleteorder,rec;
    
    @FXML
    private Menu onlineusersmenu,fill,hel;

    @FXML
    private ToolBar buttonbar;
    
    public static String diro;

    @FXML
    private MFXButton clearsel;

    @FXML
    private Label ref;

    @FXML
    private Label upd;

    @FXML
    private Label add;

    @FXML
    private Label fil;

    @FXML
    private Label del;

    @FXML
    private JFXTextField po;
    
    @FXML
    private JFXButton momo;
    
    @FXML
    private JFXTextField sapcode;

    @FXML
    private JFXTextField style;

    @FXML
    private JFXTextField customer;

    @FXML
    private JFXTextField washname;

    @FXML
    private JFXTextField poamount;

    @FXML
    private JFXTextField cuttingamount;

    @FXML
    private JFXTextField laundrydate;

    @FXML
    private JFXTextField xfacdate;

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private JFXTextArea orderarea;
    
    public static String selsecc,seluserr;
    
    public static String value1;
    
    
   public static String ppo,ssapcode,sstyle,ccustomer,wwashname,ppoamount,ccuttingamount,llaundrydate,xxfacdate,receivedd,minuslinee;
   
   @FXML
    private JFXButton sendtowh1;
   
   private Timeline refreshTimeline2,refreshTimeline3;
   

    @FXML
    private JFXButton gotowh1,lllo;

    @FXML
    private JFXButton backtoorders;
    
    static int totalReceived;
    
    ContextMenu contextMenu;
    
    // Simple boolean flag
boolean[] contextMenuEnabled = {true};

    @FXML private VBox sidePanel1;
    @FXML private VBox shipmentList1;
    @FXML private SplitPane mainSplit1;
    
     @FXML
    private ImageView avatar;
    
    @FXML
    void avataraction(MouseEvent event) throws IOException  {
        
Stage stg = new Stage();
Parent root = FXMLLoader.<Parent>load(getClass().getResource("Profile.fxml"));
Scene sce = new Scene(root);
stg.setTitle("البروفايل بتاعي");
stg.centerOnScreen();
stg.setResizable(false);
stg.setMaximized(false);
stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
stg.setScene(sce);
stg.setAlwaysOnTop(true);
stg.centerOnScreen();
stg.show();
        
    }
    


    @FXML
    void proproaction(ActionEvent event) throws IOException {
        
        
                Set<String> warehouseKeys = new HashSet<>();
try {
    String checkSql = "SELECT PO, Sap_Code, Style FROM WareHouse_1";
    PreparedStatement pst2 = conn.prepareStatement(checkSql);
    ResultSet rs2 = pst2.executeQuery();
    while (rs2.next()) {
        String key = rs2.getString("PO") + "_" + rs2.getString("Sap_Code") + "_" + rs2.getString("Style");
        warehouseKeys.add(key);
    }
    rs2.close();
    pst2.close();
} catch (Exception e) {
    e.printStackTrace();
}

        
        
        
        
ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
table.getColumns().clear();

// Load Orders from first DB
try {
    String sql = "SELECT * FROM Orders";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    // Create columns dynamically
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // Fetch data into rows
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getString(i));
        }
        data.add(row);
    } 

    // Close first DB
    rs.close();
    pst.close();

} catch (Exception e) {
    e.printStackTrace();
}


table.setItems(data);


Platform.runLater(() ->{
    
    
  
//// Add new column "Processes"
TableColumn<ObservableList<String>, String> processCol = new TableColumn<>("Processes");
processCol.setCellValueFactory(param -> {
    ObservableList<String> row = param.getValue();
    String po = row.get(3);  // Column 4
    String sapCode = row.get(4);  // Column 5

    String result = getProcessForOrder(po, sapCode);  // Call helper method
    return new SimpleStringProperty(result);
});
table.getColumns().add(processCol);

// Set data to table
table.setItems(data);

// Add TableFilter if needed
new TableFilter<>(table);  
          
//          editt();
          
      
    refresh.setDisable(true);
    addorder.setDisable(true);
    updateorder.setDisable(true);
    deleteorder.setDisable(true);
    clearsel.setDisable(true);
    ref.setDisable(true);
    upd.setDisable(true);
    add.setDisable(true);
    del.setDisable(true);
    fil.setDisable(true);
    
momo.setDisable(true);
          
          
          
          del.setVisible(false);
          
//          refresh.setDisable(false);
//          addorder.setDisable(false);
//          updateorder.setDisable(false);
//          deleteorder.setDisable(false);
//          clearsel.setDisable(false);
//          ref.setDisable(false);
//          upd.setDisable(false);
//          add.setDisable(false);
//          del.setDisable(false);
//          fil.setDisable(false);
          
          
          sendtowh1.setDisable(false);
          
          lllo.setDisable(true);
          ships.setDisable(true);
          stocky.setDisable(true);
          
      //    editt();
          
//          table.setContextMenu(null);
//          contextMenu.hide();
//          //table.setContextMenu(contextMenu);

//contextMenuEnabled[0] = true;
          
        
  
});


        
        
    }
    
    

@FXML
    void messengeraction(ActionEvent event) throws IOException {
        
Stage stg = new Stage(StageStyle.TRANSPARENT);
Parent root = FXMLLoader.<Parent>load(getClass().getResource("Messenger.fxml"));
Scene sce = new Scene(root);

// خلي الخلفية بيضاء بدل شفاف
sce.setFill(Color.TRANSPARENT); // scene شفاف
root.setStyle("-fx-background-color: white; -fx-background-radius: 30;-fx-border-color:black;-fx-border-width:4px;");

// اعمل Clip مستدير يحدد حدود الـ root
Rectangle clip = new Rectangle(550, 950);
clip.setArcWidth(100);
clip.setArcHeight(100);
root.setClip(clip);

////////////////////////////// Theme //////////////////////////////
BufferedReader bis = new BufferedReader(
    new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady")
);
String themooo = bis.readLine();
bis.close();

// Check if CSS exists
URL cssUrl = getClass().getResource(themooo);
if (cssUrl == null) {
    System.err.println("ERROR: cupertino-dark.css not found in same package as controller!");
} else {
    String cssPath = cssUrl.toExternalForm();
    sce.getStylesheets().add(cssPath);
    root.getStylesheets().add(cssPath);
}
////////////////////////////////////////////////////////////////////

stg.setTitle("Kadysoft Messenger");
stg.centerOnScreen();
stg.setResizable(false);
stg.setMaximized(false);
stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
stg.setScene(sce);
stg.setAlwaysOnTop(true);
stg.centerOnScreen();
stg.show();


    }
    
    @FXML
void inboxaction(ActionEvent event) throws IOException {
    // toggle visibility
    mainSplit1.setVisible(!mainSplit1.isVisible());
}

    
    
    @FXML
    void closepanel(ActionEvent event) throws IOException {
        
        mainSplit1.setVisible(false);
//           try {
//    String sqla = " UPDATE Message SET Delivered = 1 WHERE M_To = ? ";
//          this.pst = this.conn.prepareStatement(sqla);
//          this.pst.setString(1, selsecc);
//          this.pst.executeUpdate();
//              }
//              catch (Exception e) {
//          
//        } finally {
//          try {
//            this.rs.close();
//            this.pst.close();
//          } catch (Exception exception) {}
//        }  
    }

   
       @FXML
    void autodelete(ActionEvent event) {
        
        sendtowh1.setDisable(true);
        lllo.setDisable(false);
        
        try {
    // Step 1: Delete from washing where received = 0
    String sqlDeleteWashing = "DELETE FROM WareHouse_1 WHERE Minus_Lines = 0 AND Received = 0";
    this.pst = this.conn.prepareStatement(sqlDeleteWashing);
    this.pst.executeUpdate();
    this.pst.close();
 
    // Step 3: Audit log entry
    String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?,?,?,?)";
    this.pst = this.conn.prepareStatement(sqla);
    this.pst.setString(1, value1); // date
    this.pst.setString(2, selsecc); // section
    this.pst.setString(3, seluserr); // user
    this.pst.setString(4, "Auto-deleted zero entries from WareHouse_1");
    this.pst.execute();
    
      
    Notifications.create()
        .title("Successful Query")
        .text("كل الحاجات اللي اتصفرت اتمسحت تمام ي معلم")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();

} catch (Exception e) {
    e.printStackTrace(); // or log it
} finally {
    try {
        if (this.rs != null) this.rs.close();
    } catch (Exception ignore) {}
    try {
        if (this.pst != null) this.pst.close();
    } catch (Exception ignore) {}
}

   
  
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // تحميل البيانات لجدول TableView
    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    table.getColumns().clear();

    try (Connection conn = DriverManager.getConnection(databaseUrl);
         Statement pragmaStmt = conn.createStatement()) {

        pragmaStmt.execute("PRAGMA busy_timeout = 3000;");

        String sql = "SELECT * FROM WareHouse_1";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Create columns dynamically
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> col =
                        new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                table.getColumns().add(col);
            }

            // Fetch data into rows
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Set data to table
    table.setItems(data);

    // Add TableFilter if needed
    new TableFilter<>(table);
        
    }
        
        


    @FXML
    void refshipsaction(ActionEvent event) throws IOException {
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Refused_Shipments.fxml"));
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
    stg.setTitle("Refused Shipments");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.centerOnScreen();
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
    }
    
    
     @FXML
    void llloaction(ActionEvent event) {
        rec.fire();
    }
    
    
    @FXML
    void stockyaction(ActionEvent event) {
        
        
        
    try {
    String sqlll = "SELECT SUM(Received) AS TotalReceived FROM WareHouse_1";
    pst = conn.prepareStatement(sqlll);
    rs = pst.executeQuery(); // ✅ Assign the result set

    int totalReceived = 0;
    if (rs.next()) {
        totalReceived = rs.getInt("TotalReceived");
    }

    Notifications.create()
        .title("Successful Query")
        .text("WareHouse 1 Stock Is:   " + totalReceived + "   PCS.")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();

} catch (Exception ex) {
    ex.printStackTrace();
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

        
        
        
        
    }
    
    
    
     
    @FXML
    void sendtowh1action(ActionEvent event) {

    
             
               //Manual
               
                   try {
    Parent content = FXMLLoader.load(getClass().getResource("Send_To_WH1.fxml"));

    Stage stage = new Stage();
    stage.setTitle("ارسال شحنة الي المخزن الاول");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}
               
       
             
//         }

        
        
    }
    
    @FXML
    void backtoordersaction(ActionEvent event) {
        
        
        
        
        
        
        Set<String> warehouseKeys = new HashSet<>();
try {
    String checkSql = "SELECT PO, Sap_Code, Style FROM WareHouse_1";
    PreparedStatement pst2 = conn.prepareStatement(checkSql);
    ResultSet rs2 = pst2.executeQuery();
    while (rs2.next()) {
        String key = rs2.getString("PO") + "_" + rs2.getString("Sap_Code") + "_" + rs2.getString("Style");
        warehouseKeys.add(key);
    }
    rs2.close();
    pst2.close();
} catch (Exception e) {
    e.printStackTrace();
}

        
        
        
        
ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
table.getColumns().clear();

// Load Orders from first DB
try {
    String sql = "SELECT * FROM Orders";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    // Create columns dynamically
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // Fetch data into rows
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getString(i));
        }
        data.add(row);
    } 

    // Close first DB
    rs.close();
    pst.close();

} catch (Exception e) {
    e.printStackTrace();
}


table.setItems(data);


Platform.runLater(() ->{
    
    
  
//// Add new column "Processes"
//TableColumn<ObservableList<String>, String> processCol = new TableColumn<>("Processes");
//processCol.setCellValueFactory(param -> {
//    ObservableList<String> row = param.getValue();
//    String po = row.get(3);  // Column 4
//    String sapCode = row.get(4);  // Column 5
//
//    String result = getProcessForOrder(po, sapCode);  // Call helper method
//    return new SimpleStringProperty(result);
//});
//table.getColumns().add(processCol);

// Set data to table
table.setItems(data);

// Add TableFilter if needed
new TableFilter<>(table);  
          
//          editt();
          
      
    refresh.setDisable(true);
    addorder.setDisable(true);
    updateorder.setDisable(true);
    deleteorder.setDisable(true);
    clearsel.setDisable(true);
    ref.setDisable(true);
    upd.setDisable(true);
    add.setDisable(true);
    del.setDisable(true);
    fil.setDisable(true);
    
momo.setDisable(true);
          
          
          
          del.setVisible(false);
          
//          refresh.setDisable(false);
//          addorder.setDisable(false);
//          updateorder.setDisable(false);
//          deleteorder.setDisable(false);
//          clearsel.setDisable(false);
//          ref.setDisable(false);
//          upd.setDisable(false);
//          add.setDisable(false);
//          del.setDisable(false);
//          fil.setDisable(false);
          
          
          sendtowh1.setDisable(false);
          
          lllo.setDisable(true);
          ships.setDisable(true);
          stocky.setDisable(true);
          
      //    editt();
          
//          table.setContextMenu(null);
//          contextMenu.hide();
//          //table.setContextMenu(contextMenu);

//contextMenuEnabled[0] = true;
          
        
  
});




    }
    
    
    
    
    
    
    
    @FXML
void gotowh1action(ActionEvent event) {
    
    
    table.getColumns().clear();
    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

    try {
        String sql = "SELECT * FROM WareHouse_1";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();

        // Create table columns
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
            table.getColumns().add(col);
        }

        // Populate data
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        table.setItems(data);

        // Find index of Minus_Lines
        int minusLinesIndex = -1;
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            String colName = rs.getMetaData().getColumnName(i + 1);
            if ("Minus_Lines".equalsIgnoreCase(colName.trim())) {
                minusLinesIndex = i;
                break;
            }
        }

        final int finalIndex = minusLinesIndex;
        final PseudoClass lowMinusLines = PseudoClass.getPseudoClass("low-minus-lines");

        // Row coloring using pseudoclass (works with Cupertino Light)
        if (finalIndex != -1) {
            table.setRowFactory(tv -> new TableRow<ObservableList<String>>() {
                @Override
                protected void updateItem(ObservableList<String> item, boolean empty) {
                    super.updateItem(item, empty);
                    pseudoClassStateChanged(lowMinusLines, false);

                    if (empty || item == null || item.size() <= finalIndex) return;

                    try {
                        String valueStr = item.get(finalIndex);
                        if (valueStr != null && !valueStr.trim().isEmpty()) {
                            int val = Integer.parseInt(valueStr.trim());
                            if (val < 1000) {
                                pseudoClassStateChanged(lowMinusLines, true);
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Invalid number format — ignore
                    }
                }
            });
        }

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

    // Filter and UI state control
    TableFilter filter = new TableFilter(table);
    del.setVisible(false);

    refresh.setDisable(true);
    addorder.setDisable(true);
    updateorder.setDisable(true);
    deleteorder.setDisable(true);
    clearsel.setDisable(true);
    ref.setDisable(true);
    upd.setDisable(true);
    add.setDisable(true);
    del.setDisable(true);
    fil.setDisable(true);
    sendtowh1.setDisable(true);
    stocky.setDisable(false);
    ships.setDisable(false);
    lllo.setDisable(false);
    momo.setDisable(false);
}

    
    @FXML
  void logoutaction(ActionEvent event) throws IOException {
      
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("LogIn_GUI.fxml"));
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
    stg.setTitle("LogIn Window");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.centerOnScreen();
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
    Stage jk = (Stage)this.del.getScene().getWindow();
    jk.close(); 
    
     //////////////////////////////////////////////////
   
    
    if (refreshTimeline2 != null) {
        refreshTimeline2.stop();
    }
    
    if (refreshTimeline3 != null) {
        refreshTimeline3.stop();
    }
    
    //////////////////////////////////////////////////
    
     //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selsecc);
          pst.setString(2, seluserr);
          pst.executeUpdate();
              }
              catch (Exception e) {
          
        } finally {
          try {
            rs.close();
            pst.close();
          } catch (Exception exception) {}
        }  
    
    
    
  }
  
    
    
   @FXML
void aboutaction(ActionEvent event) throws IOException {

    // Load FXML
    Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));

    // Create a new scene
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

    // Setup stage
    Stage stg = new Stage();
    stg.setTitle("About");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.setAlwaysOnTop(true);
    stg.getIcons().add(new javafx.scene.image.Image(
            AlphaPlanner.class.getResourceAsStream("alpha.png")));

    // Close handler: restore opacity of main window
    stg.setOnCloseRequest(event1 -> {
        Stage jk = (Stage) del.getScene().getWindow();
        jk.setOpacity(1);
    });

    // Set scene and show
    stg.setScene(sce);
    stg.show();

    // Dim the main window
    Stage jk = (Stage) del.getScene().getWindow();
    jk.setOpacity(0.4);
}


    
    
    @FXML
    void addorderaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("AddOrder.fxml"));
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
    stg.setTitle("Add Order");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)del.getScene().getWindow();
    jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)del.getScene().getWindow();
    jk.setOpacity(0.4);
        
    }

    
    
    @FXML
    void clearselectionaction(ActionEvent event) {

    table.getSelectionModel().clearSelection();
    del.setVisible(false);
        
        
    }

    
    
    @FXML
    void creditsaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Credits.fxml"));
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
    
    
    stg.setTitle("Credits");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.setAlwaysOnTop(true);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)del.getScene().getWindow();
    jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)del.getScene().getWindow();
    jk.setOpacity(0.4);
    
        
    }
    
    
    @FXML
    void trackshipmentaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Track_Shipment.fxml"));
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
    stg.setTitle("Track Shipment");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setMaximized(true);
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
        
        
    }


    
    
    
    @FXML
    void excelaction(ActionEvent event) throws FileNotFoundException, IOException {

        /////////////////////////////////////////////////////////////////////////
    
        
        Workbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("All_Selected_Orders");
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
        dialog.setInitialFileName("Kadysoft");
        dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", new String[] { "*.xlsx" }));
        File dialogResult = dialog.showSaveDialog(null);
        String filePath = dialogResult.getAbsolutePath().toString();
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        Desktop desk=Desktop.getDesktop();
        desk.open(new File (filePath));
        
        /////////////////////////////////////////////////////////////////////////
        
         ////////////////////////Audit//////////////////////////
      
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" Created Excel Report.");
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

    
    
    @FXML
    void pdfaction(ActionEvent event) throws IOException {

        
        
        if (sendtowh1.isDisable()==true) {
            
            //WH1
            
            ////////////////Create PDF///////////////
              ////////////////////////////Start Report//////////////////////////////
        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String timeString = sdf.format(d);
    String value0 = timeString;
    String value00 = value0.replace("/", "_");
    String repname = "All_Selected_Orders_In_" + value00;
    String reppath = System.getProperty("user.home") + "\\Desktop";
    FileChooser dialog = new FileChooser();
    dialog.setInitialDirectory(new File(reppath));
    dialog.setInitialFileName(repname);
    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", new String[] { "*.pdf" }));
    File dialogResult = dialog.showSaveDialog(null);
    String filePath = dialogResult.getAbsolutePath().toString();
    try {
        
         String sql ="select * from WareHouse_1";
            
 
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
        
      DateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
      Date dd = new Date();
      String todate = dff.format(dd);
      Calendar cal = Calendar.getInstance();
      cal.add(5, -2);
      Date d1 = cal.getTime();
      String fromdate = dff.format(d1);
      com.itextpdf.text.Document myDocument = new com.itextpdf.text.Document();
      PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
      PdfPTable table = new PdfPTable(11);
      table.size();
      //table.setHorizontalAlignment(1);
      myDocument.open();
      float[] columnWidths = { 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F };
      table.setWidths(columnWidths);
      table.setWidthPercentage(100.0F);
      
      ColumnText.showTextAligned(myWriter.getDirectContentUnder(),
              
                Element.ALIGN_CENTER, new Phrase("T&C Garments By Kadysoft Ltd.", FontFactory.getFont("Times-Bold", 11.0F, 1)),
                297.5f, 421, myWriter.getPageNumber() % 2 == 1 ? 45 : -45);
      
      //myDocument.add((com.itextpdf.text.Element)new Paragraph("--------------------"));
      myDocument.add((com.itextpdf.text.Element)new Paragraph("WareHouse 1 Orders Report", FontFactory.getFont("Times-Bold", 14.0F, 1)));
      myDocument.add((com.itextpdf.text.Element)new Paragraph(" "));
      table.addCell(new PdfPCell((Phrase)new Paragraph("PO", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Sap Code", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Style", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Customer", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Wash Name", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Po Amount", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Cutting Amount", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Laundry Date", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("X Fac Date", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Received", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Minus_Line", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      
      while (rs.next()) {
       // table.addCell(new PdfPCell(new Paragraph(rs.getString(0),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(7),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(8),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(9),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(10),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(11),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           
      } 
      myDocument.add((com.itextpdf.text.Element)table);
      myDocument.add((com.itextpdf.text.Element)new Paragraph("-------------------------------"));
      myDocument.setPageSize(PageSize.A4.rotate());
      myDocument.close();
      Alert alooo = new Alert(Alert.AlertType.CONFIRMATION);
      alooo.setTitle("Info");
      alooo.setHeaderText("Info!");
      alooo.setContentText("Report was generated successfully");
      alooo.setResizable(true);
      DialogPane dialogPaneef = alooo.getDialogPane();
        //////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
  if (themooo.equals("cupertino-dark.css")) {
      //Dark
      dialogPaneef.getStylesheets().add(
      getClass().getResource("cupertino-dark.css").toExternalForm());
  }
  else {
      //Light
      dialogPaneef.getStylesheets().add(
      getClass().getResource("cupertino-light.css").toExternalForm());
  }
  }
  catch (Exception re) {}
  //////////////////////////////////////////////////////////////////////////////////////////////////////  
      alooo.showAndWait();
    } catch (Exception e) {
    //  JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
       
      } catch (Exception e) {
     //   JOptionPane.showMessageDialog(null, e);
      } 
    } 
    Desktop de = Desktop.getDesktop();
    de.open(new File(reppath + "\\" + repname + ".pdf"));
    ////
        
        ////////////////////////////End Report////////////////////////////////

       
      ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" Created PDF Report.");
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
        
        else {
            ////////////////Create PDF///////////////
              ////////////////////////////Start Report//////////////////////////////
        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String timeString = sdf.format(d);
    String value0 = timeString;
    String value00 = value0.replace("/", "_");
    String repname = "All_Selected_Orders_In_" + value00;
    String reppath = System.getProperty("user.home") + "\\Desktop";
    FileChooser dialog = new FileChooser();
    dialog.setInitialDirectory(new File(reppath));
    dialog.setInitialFileName(repname);
    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", new String[] { "*.pdf" }));
    File dialogResult = dialog.showSaveDialog(null);
    String filePath = dialogResult.getAbsolutePath().toString();
    try {
        
         String sql ="select * from Orders";
            
 
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
        
      DateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
      Date dd = new Date();
      String todate = dff.format(dd);
      Calendar cal = Calendar.getInstance();
      cal.add(5, -2);
      Date d1 = cal.getTime();
      String fromdate = dff.format(d1);
      com.itextpdf.text.Document myDocument = new com.itextpdf.text.Document();
      PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
      PdfPTable table = new PdfPTable(9);
      table.size();
      //table.setHorizontalAlignment(1);
      myDocument.open();
      float[] columnWidths = { 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F };
      table.setWidths(columnWidths);
      table.setWidthPercentage(100.0F);
      
      ColumnText.showTextAligned(myWriter.getDirectContentUnder(),
              
                Element.ALIGN_CENTER, new Phrase("T&C Garments By Kadysoft Ltd.", FontFactory.getFont("Times-Bold", 11.0F, 1)),
                297.5f, 421, myWriter.getPageNumber() % 2 == 1 ? 45 : -45);
      
      //myDocument.add((com.itextpdf.text.Element)new Paragraph("--------------------"));
      myDocument.add((com.itextpdf.text.Element)new Paragraph("Orders Report", FontFactory.getFont("Times-Bold", 14.0F, 1)));
      myDocument.add((com.itextpdf.text.Element)new Paragraph(" "));
      table.addCell(new PdfPCell((Phrase)new Paragraph("PO", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Sap Code", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Style", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Customer", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Wash Name", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Po Amount", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Cutting Amount", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Laundry Date", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("X Fac Date", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      
      while (rs.next()) {
       // table.addCell(new PdfPCell(new Paragraph(rs.getString(0),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(7),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(8),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(9),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           
      } 
      myDocument.add((com.itextpdf.text.Element)table);
      myDocument.add((com.itextpdf.text.Element)new Paragraph("-------------------------------"));
      myDocument.setPageSize(PageSize.A4.rotate());
      myDocument.close();
      Alert alooo = new Alert(Alert.AlertType.CONFIRMATION);
      alooo.setTitle("Info");
      alooo.setHeaderText("Info!");
      alooo.setContentText("Report was generated successfully");
      alooo.setResizable(true);
      DialogPane dialogPaneef = alooo.getDialogPane();
        //////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
  if (themooo.equals("cupertino-dark.css")) {
      //Dark
      dialogPaneef.getStylesheets().add(
      getClass().getResource("cupertino-dark.css").toExternalForm());
  }
  else {
      //Light
      dialogPaneef.getStylesheets().add(
      getClass().getResource("cupertino-light.css").toExternalForm());
  }
  }
  catch (Exception re) {}
  //////////////////////////////////////////////////////////////////////////////////////////////////////  
      alooo.showAndWait();
    } catch (Exception e) {
    //  JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
       
      } catch (Exception e) {
     //   JOptionPane.showMessageDialog(null, e);
      } 
    } 
    Desktop de = Desktop.getDesktop();
    de.open(new File(reppath + "\\" + repname + ".pdf"));
    ////
        
        ////////////////////////////End Report////////////////////////////////

       
      ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" Created PDF Report.");
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
        
        
        
        
       
        
        
    }
    
    
    
    
    @FXML
    void refreshaction(ActionEvent event) {

          ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
table.getColumns().clear();

// Load Orders from first DB
try {
    String sql = "SELECT * FROM Orders";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    // Create columns dynamically
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // Fetch data into rows
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getString(i));
        }
        data.add(row);
    }

    // Close first DB
    rs.close();
    pst.close();

} catch (Exception e) {
    e.printStackTrace();
}

// Add new column "Processes"
TableColumn<ObservableList<String>, String> processCol = new TableColumn<>("Processes");
processCol.setCellValueFactory(param -> {
    ObservableList<String> row = param.getValue();
    String po = row.get(3);  // Column 4
    String sapCode = row.get(4);  // Column 5

    String result = getProcessForOrder(po, sapCode);  // Call helper method
    return new SimpleStringProperty(result);
});
table.getColumns().add(processCol);

// Set data to table
table.setItems(data);

// Add TableFilter if needed
new TableFilter<>(table);

          
          
          editt();
          del.setVisible(false);
          
          
//          refresh.setDisable(false);
//          addorder.setDisable(false);
//          updateorder.setDisable(false);
//          deleteorder.setDisable(false);
//          clearsel.setDisable(false);
//          ref.setDisable(false);
//          upd.setDisable(false);
//          add.setDisable(false);
//          del.setDisable(false);
//          fil.setDisable(false);
          
            refresh.setDisable(true);
    addorder.setDisable(true);
    updateorder.setDisable(true);
    deleteorder.setDisable(true);
    clearsel.setDisable(true);
    ref.setDisable(true);
    upd.setDisable(true);
    add.setDisable(true);
    del.setDisable(true);
    fil.setDisable(true);
          
          
          sendtowh1.setDisable(false);
          
          
          ships.setDisable(true);
          stocky.setDisable(true);
          
          editt();
        
        
    }

    
    
    
    @FXML
    void settingsaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Settings.fxml"));
    Scene sce = new Scene(root);
    //////////////////////////////Theme////////////////////////////////
//    BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//    String themooo=bis.readLine();
//    bis.close();
//    // Check if CSS exists
//    URL cssUrl = getClass().getResource(themooo);
//    if (cssUrl == null) {
//        System.err.println("ERROR: cupertino-dark.css not found in same package as controller!");
//    } else {
//        // Apply theme to both scene and root (ensures it always works)
//        String cssPath = cssUrl.toExternalForm();
//        sce.getStylesheets().add(cssPath);
//        root.getStylesheets().add(cssPath);
//    }
    ////////////////////////////////////////////////////////////////////
    stg.setTitle("Settings");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)del.getScene().getWindow();
    //jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)del.getScene().getWindow();
    //jk.setOpacity(0.4);
    
    
        
    }
    
    
  
    
     
    @FXML
    void quitaction(ActionEvent event) {

        //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selsecc);
          pst.setString(2, seluserr);
          pst.executeUpdate();
              }
              catch (Exception e) {
          
        } finally {
          try {
            rs.close();
            pst.close();
          } catch (Exception exception) {}
        } 
                
         try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LogIn_GUI_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }        
        Platform.exit();
        
    }

    
    
    
    
    
    
    
    
   @FXML
void tableaction(MouseEvent event) {
    // Get the selected row
    ObservableList<String> selectedRow = table.getSelectionModel().getSelectedItem();

    // Clear existing content
    orderarea.clear();

    // If nothing is selected, hide delete button and exit
    if (selectedRow == null || selectedRow.isEmpty()) {
          del.setVisible(false);
          po.clear();
            sapcode.clear();
            style.clear();
            washname.clear();
            customer.clear();
            
            poamount.clear();
            cuttingamount.clear();
            laundrydate.clear();
            xfacdate.clear();
        return;
    }

    // Fill the orderarea TextArea with row data
    for (String cell : selectedRow) {
        orderarea.appendText(cell + "\n");
    }

    // Safely populate text fields based on column index
    po.setText(getCelll(selectedRow, 0));
    sapcode.setText(getCelll(selectedRow, 1));
    style.setText(getCelll(selectedRow, 2));
    customer.setText(getCelll(selectedRow, 3));
    washname.setText(getCelll(selectedRow, 4));
    poamount.setText(getCelll(selectedRow, 5));
    cuttingamount.setText(getCelll(selectedRow, 6));
    laundrydate.setText(getCelll(selectedRow, 7));
    xfacdate.setText(getCelll(selectedRow, 8));
    
            
            ppo=po.getText();
            ssapcode=sapcode.getText();
            sstyle=style.getText();
            ccustomer=customer.getText();
            wwashname=washname.getText();
            ppoamount=poamount.getText();
            ccuttingamount=cuttingamount.getText();
            llaundrydate=laundrydate.getText();
            xxfacdate=xfacdate.getText();
            
//            System.out.println(ppo);
//            System.out.println(ssapcode);
//            System.out.println(sstyle);
//            System.out.println(ccustomer);
//            System.out.println(wwashname);
//            System.out.println(ppoamount);
//            System.out.println(ccuttingamount);
//            System.out.println(llaundrydate);
//            System.out.println(xxfacdate);
            
       

    // Show the delete button
    del.setVisible(false);
}

// Helper method to safely get a cell value from a row
private String getCelll(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}

    
    
    
   
    
    
    
    @FXML
    void deleteorderaction(ActionEvent event) {

     
        //Delete
        
        
        
        String pol=  po.getText();
            String sapcodel= sapcode.getText();
            String stylel=  style.getText();
            String ordernamel= washname.getText();
            String customerrr= customer.getText();
            
            String e1= poamount.getText();
            String e2= cuttingamount.getText();
            String e3= laundrydate.getText();
            String e4= xfacdate.getText();
        
        
         if (ordernamel.isEmpty()==true) {
              
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful Deletion.\nPlease choose an order first.");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
              
          }
         
         
         else {
             
                  
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Delete Order");
      alert.setHeaderText("Are you sure want to move this Order to the Recycle Bin?\n\nCaution: You can't undo or restore this order again.");
      alert.setContentText("Order Name is: "+ordernamel+"\n"+"Order PO is: "+pol+"\n"+"Order SapCode is: "+sapcodel+"\n"+"Order Style is: "+stylel+"\n"+"Order Customer is: "+customerrr+"\n"+"Order PO Amount is: "+e1+"\n"+"Order Cutting Amount is: "+e2+"\n"+"Order Laundry Date is: "+e3+"\n"+"Order X Fac Date is: "+e4);
      DialogPane dialogPane = alert.getDialogPane();
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
      // option != null.
      Optional<ButtonType> option = alert.showAndWait();
      if (option.get() == null) {
         
      } else if (option.get() == ButtonType.OK) {
          
          
         
           
            
            
            try {
            
            String sql = "delete from Orders where PO='"+pol+"' and Sap_Code='"+sapcodel+"' and Style='"+stylel+"' and Customer='"+customerrr+"' and Wash_Name='"+ordernamel+"' and PO_Amount='"+e1+"' and Cutting_Amount='"+e2+"' and Incoming_Date='"+e3+"' and X_Fac_Date='"+e4+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            
            
            
              Notifications noti = Notifications.create();
              noti.title("Successful");
              noti.text("Successful Deletion");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showInformation();
            
              
            }catch(Exception e){
              Notifications notiv = Notifications.create();
              notiv.title("Unsuccessful");
              notiv.text("Unsuccessful Deletion");
              notiv.hideAfter(Duration.seconds(3));
              notiv.position(Pos.CENTER);
              notiv.showError();
            }
            finally {
            
            try{
                rs.close();
                pst.close();
                
            }
            catch(Exception e){
                
            }
        }
    
          
            po.clear();
            sapcode.clear();
            style.clear();
            washname.clear();
            customer.clear();
            
            poamount.clear();
            cuttingamount.clear();
            laundrydate.clear();
            xfacdate.clear();
           
            
             refresh.fire();
             del.setVisible(false);
             
             
             ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" deleted an order and its data is: Customer is: "+customerrr+", Order name is: "+ordernamel+" and Style is: "+stylel);
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
      else if (option.get() == ButtonType.CANCEL) {
      Notifications noti = Notifications.create();
      noti.title("Cancel!");
      noti.text("Operation Cancelled, Order wasn't deleted.");
      noti.position(Pos.CENTER);
      noti.showInformation();
      del.setVisible(false);
      }else {del.setVisible(false);}
        
        
       
             
         }
        
        
        
        
    }
    
  

    
    
    @FXML
    void editorderaction(ActionEvent event) {

        saveDataToDatabase();
        
        
    }
    
    
    public void editt() {
    try {
        Class.forName("org.sqlite.JDBC");

        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {

            table.getColumns().clear();
            data.clear();
            columnNames.clear();

            Set<Integer> comboBoxColumns = new HashSet<>();
            comboBoxColumns.add(1); // Example comboBox column (adjust if needed)

            // Build columns dynamically from Orders table
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int colIndex = i;
                String colName = rs.getMetaData().getColumnName(i + 1);
                columnNames.add(colName);

                TableColumn<ObservableList<String>, String> column = new TableColumn<>(colName);
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));

                column.setCellFactory(TextFieldTableCell.forTableColumn());

                column.setOnEditCommit(event -> {
                    ObservableList<String> row = event.getRowValue();
                    row.set(colIndex, event.getNewValue());
                });

                column.setContextMenu(createColumnContextMenu(colIndex));
                table.getColumns().add(column);
            }

            // Fetch rows and add them to data list
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }

                // Get PO and SAP Code from column 4 and 5 (indexes 3 and 4)
                String po = row.size() > 3 ? row.get(3) : "";
                String sapCode = row.size() > 4 ? row.get(4) : "";
                String processResult = getProcessForOrder(po, sapCode);

                row.add(processResult); // Add "Processes" column value
                data.add(row);
            }

            // Add the "Processes" column manually
            int processIndex = rs.getMetaData().getColumnCount(); // It will be last column index
            TableColumn<ObservableList<String>, String> processCol = new TableColumn<>("Processes");
            processCol.setCellValueFactory(param -> {
                ObservableList<String> row = param.getValue();
                return new SimpleStringProperty(
                        row.size() > processIndex ? row.get(processIndex) : "ملقتش ريسيبي والله او في حاجة غلط بص ممكن يكون السبب متعملهاش مراحل لسه او الاسم غلط"
                );
            });
            processCol.setEditable(false);
            table.getColumns().add(processCol);

            // Set data to table
            table.setItems(data);
            table.setEditable(true);

            table.setRowFactory(tv -> {
                TableRow<ObservableList<String>> row = new TableRow<>();
                row.setContextMenu(createRowContextMenu(row));
                return row;
            });

        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace(); // Or show alert
    } catch (SQLException e) {
        e.printStackTrace(); // Or show alert
    }
}

    
    
    
     private ContextMenu createColumnContextMenu(int colIndex) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem changeColor = new MenuItem("Change Column Color");

        changeColor.setOnAction(e -> {
            ObservableList<String> selectedRow = table.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                JFXColorPicker colorPicker = new JFXColorPicker(Color.WHITE);
                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Select Column Color");
                dialog.getDialogPane().setContent(colorPicker);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();

                Color selectedColor = colorPicker.getValue();
                String colorStyle = "-fx-background-color: " + toRgbCode(selectedColor) + ";";
                TableColumn<ObservableList<String>, String> column = (TableColumn<ObservableList<String>, String>) table.getColumns().get(colIndex);
                column.setStyle(colorStyle);
            }
        });

        contextMenu.getItems().add(changeColor);
        return contextMenu;
    }

    
      private ContextMenu createRowContextMenu(TableRow<ObservableList<String>> row) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem changeRowColor = new MenuItem("Change Row Color");
        
        MenuItem showrecipe = new MenuItem("Show Recipe");
        MenuItem deleteorderr = new MenuItem("Delete Order");

        changeRowColor.setOnAction(e -> {
            JFXColorPicker colorPicker = new JFXColorPicker(Color.WHITE);
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Select Row Color");
            dialog.getDialogPane().setContent(colorPicker);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();

            Color selectedColor = colorPicker.getValue();
            String colorStyle = "-fx-background-color: " + toRgbCode(selectedColor) + ";";
            row.setStyle(colorStyle);
        });
        
        showrecipe.setOnAction(ey -> {
            
        String cust=customer.getText();
        String recname=washname.getText();
        String keyywordd1="PRODUCTION";
        String keyywordd2="PILOT";
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        List<String> lines;   
        try {
        lines = Files.readAllLines(Paths.get(settingsfile));
        for (String line : lines) {
        if (line.startsWith("Recipe_Path=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        } catch (IOException ex) {}
        
        String recipelink1=vaa+"\\"+keyywordd1+"\\"+cust+"\\"+recname+".ks";
        String recipelink2=vaa+"\\"+keyywordd2+"\\"+cust+"\\"+recname+".ks";
        
        File ff1=new File (recipelink1);  //PRODUCTION
        File ff2=new File (recipelink2);  //PILOT
        
        if (ff1.exists()) {
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
        //////////////////////////////////////Web View Alert ///////////////////////////////
    WebView webview=new WebView ();
    webview.setMinSize(1500, 650);
    orderarea.clear();
    InputStream inputinstream;
            try {
                inputinstream = new FileInputStream(recipelink1);
                BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        
        orderarea.appendText("\n"+lo
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
    
    String gf=orderarea.getText();
    //System.out.println(gf);
    OutputStream instreamm=new FileOutputStream(System.getProperty("user.home")+"\\"+recname+".html");
    PrintWriter pwe = new PrintWriter(new OutputStreamWriter (instreamm,"UTF-8"));
    pwe.println(gf);
    pwe.println("<script>\n" +
"  \n" +
"  window.addEventListener(`contextmenu`, (e) => {\n" +
"    e.preventDefault();\n" +
"});\n" +
"  \n" +
"  </script>");
    
    pwe.println("<script>\n" +
"            \n" +
"            document.addEventListener('keydown', event => {\n" +
"  console.log(`User pressed: ${event.key}`);\n" +
"  event.preventDefault();\n" +
"  return false;\n" +
"});\n" +
"            \n" +
"            </script>");
    
    pwe.close();
    orderarea.clear();
       
            } catch (FileNotFoundException ex) {
               // Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
              //  Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
          // إنشاء WebView وتحميل ملف HTML محلي
        WebView webviewt = new WebView();
        webviewt.setContextMenuEnabled(false);
        webviewt.setMinSize(1800, 800);
        webviewt.setZoom(1.0); // التكبير الافتراضي 100%

        String lkd = System.getProperty("user.home")+"\\"+recname+".html";
        URI uris = Paths.get(lkd).toAbsolutePath().toUri();
        webviewt.getEngine().load(uris.toString());

        // سلايدر للتحكم في الزوم من 10% إلى 200%
        Slider zoomSlider = new Slider(0.1, 2.0, 1.0);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(0.5);
        zoomSlider.setMinorTickCount(4);
        zoomSlider.setBlockIncrement(0.1);

        Label zoomLabel = new Label("Zoom: 100%");

        zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double zoom = newVal.doubleValue();
            webviewt.setZoom(zoom);
            zoomLabel.setText(String.format("Zoom: %.0f%%", zoom * 100));
        });

        // VBox يحتوي على WebView والسلايدر
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        VBox.setVgrow(webviewt, Priority.ALWAYS);
        vbox.getChildren().addAll(webviewt, zoomLabel, zoomSlider);

        // وضع VBox داخل GridPane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setAlignment(Pos.CENTER);
        gridpane.add(vbox, 0, 0);

        // إنشاء Alert لعرض المحتوى
        Alert alol = new Alert(Alert.AlertType.INFORMATION);
        alol.setTitle("Preview a recipe");
        DialogPane dialogPane = alol.getDialogPane();
        dialogPane.setContent(gridpane);
        alol.setResizable(true);
     
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
        alol.showAndWait();
        File nm=new File (System.getProperty("user.home")+"\\"+recname+".html");
        if ((System.getProperty("user.home")+"\\"+recname+".html").contains(".ks")) {
            nm.delete();
        }
        else {
            
        }
        
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    nm.delete();
        
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        
        else if (!ff1.exists()&&ff2.exists()) {
                 
        //////////////////////////////////////Web View Alert ///////////////////////////////
    WebView webview=new WebView ();
    webview.setMinSize(1500, 650);
    orderarea.clear();
    InputStream inputinstream;
            try {
                inputinstream = new FileInputStream(recipelink2);
                BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        
        orderarea.appendText("\n"+lo
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
    
    String gf=orderarea.getText();
    //System.out.println(gf);
    OutputStream instreamm=new FileOutputStream(System.getProperty("user.home")+"\\"+recname+".html");
    PrintWriter pwe = new PrintWriter(new OutputStreamWriter (instreamm,"UTF-8"));
    pwe.println(gf);
    pwe.println("<script>\n" +
"  \n" +
"  window.addEventListener(`contextmenu`, (e) => {\n" +
"    e.preventDefault();\n" +
"});\n" +
"  \n" +
"  </script>");
    
    pwe.println("<script>\n" +
"            \n" +
"            document.addEventListener('keydown', event => {\n" +
"  console.log(`User pressed: ${event.key}`);\n" +
"  event.preventDefault();\n" +
"  return false;\n" +
"});\n" +
"            \n" +
"            </script>");
    
    pwe.close();
    orderarea.clear();
       
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        URI uri = Paths.get(System.getProperty("user.home")+"\\"+recname+".html").toAbsolutePath().toUri();
        webview.getEngine().load(uri.toString());
        Alert alo = new Alert(Alert.AlertType.INFORMATION);
        alo.setTitle("Preview a recipe");
        alo.setGraphic(webview);
        alo.setResizable(false);
        DialogPane dialogPane = alo.getDialogPane();
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
        alo.showAndWait();
        File nm=new File (System.getProperty("user.home")+"\\"+recname+".html");
        if ((System.getProperty("user.home")+"\\"+recname+".html").contains(".ks")) {
            nm.delete();
        }
        else {
            
        }
        
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    nm.delete();
        
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        }
        
        else if (ff1.exists()&&ff2.exists()) {
                 
        //////////////////////////////////////Web View Alert ///////////////////////////////
    WebView webview=new WebView ();
    webview.setMinSize(1500, 650);
    orderarea.clear();
    InputStream inputinstream;
            try {
                inputinstream = new FileInputStream(recipelink1);
                BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        
        orderarea.appendText("\n"+lo
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
    
    String gf=orderarea.getText();
    //System.out.println(gf);
    OutputStream instreamm=new FileOutputStream(System.getProperty("user.home")+"\\"+recname+".html");
    PrintWriter pwe = new PrintWriter(new OutputStreamWriter (instreamm,"UTF-8"));
    pwe.println(gf);
    pwe.println("<script>\n" +
"  \n" +
"  window.addEventListener(`contextmenu`, (e) => {\n" +
"    e.preventDefault();\n" +
"});\n" +
"  \n" +
"  </script>");
    
    pwe.println("<script>\n" +
"            \n" +
"            document.addEventListener('keydown', event => {\n" +
"  console.log(`User pressed: ${event.key}`);\n" +
"  event.preventDefault();\n" +
"  return false;\n" +
"});\n" +
"            \n" +
"            </script>");
    
    pwe.close();
    orderarea.clear();
       
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
          // إنشاء WebView وتحميل ملف HTML محلي
        WebView webviewt = new WebView();
        webviewt.setContextMenuEnabled(false);
        webviewt.setMinSize(1800, 800);
        webviewt.setZoom(1.0); // التكبير الافتراضي 100%

        String lkd = System.getProperty("user.home")+"\\"+recname+".html";
        URI uris = Paths.get(lkd).toAbsolutePath().toUri();
        webviewt.getEngine().load(uris.toString());

        // سلايدر للتحكم في الزوم من 10% إلى 200%
        Slider zoomSlider = new Slider(0.1, 2.0, 1.0);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(0.5);
        zoomSlider.setMinorTickCount(4);
        zoomSlider.setBlockIncrement(0.1);

        Label zoomLabel = new Label("Zoom: 100%");

        zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double zoom = newVal.doubleValue();
            webviewt.setZoom(zoom);
            zoomLabel.setText(String.format("Zoom: %.0f%%", zoom * 100));
        });

        // VBox يحتوي على WebView والسلايدر
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        VBox.setVgrow(webviewt, Priority.ALWAYS);
        vbox.getChildren().addAll(webviewt, zoomLabel, zoomSlider);

        // وضع VBox داخل GridPane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setAlignment(Pos.CENTER);
        gridpane.add(vbox, 0, 0);

        // إنشاء Alert لعرض المحتوى
        Alert alol = new Alert(Alert.AlertType.INFORMATION);
        alol.setTitle("Preview a recipe");
        DialogPane dialogPane = alol.getDialogPane();
        dialogPane.setContent(gridpane);
        alol.setResizable(true);
     
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
        alol.showAndWait();
        File nm=new File (System.getProperty("user.home")+"\\"+recname+".html");
        if ((System.getProperty("user.home")+"\\"+recname+".html").contains(".ks")) {
            nm.delete();
        }
        else {
            
        }
        
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    nm.delete();
        
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        }
        
        else if (!ff1.exists()&&!ff2.exists()) {
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful Request");
              noti.text("Unsuccessful Request, No recipe found or maybe name is incorrect.");
              noti.hideAfter(Duration.seconds(4));
              noti.position(Pos.CENTER);
              noti.showError();
        }
        
            
        });
        
        deleteorderr.setOnAction(e -> {
            deleteorder.fire();
        });

        contextMenu.getItems().addAll(changeRowColor,showrecipe);
        return contextMenu;
    }

    private String toRgbCode(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    
    
     private void addRow() {
        ObservableList<String> newRow = FXCollections.observableArrayList();
        for (int i = 0; i < columnNames.size(); i++) {
            newRow.add(""); // Add empty values for each column
        }
        data.add(newRow);
    }

     
      private void saveDataToDatabase() {
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            try (Connection conn = DriverManager.getConnection(databaseUrl);
                 Statement stmt = conn.createStatement()) {

                conn.setAutoCommit(false); // Disable auto-commit for batch updates
                stmt.executeUpdate("DELETE FROM Orders"); // Clear existing data

                // Reinsert updated data
                for (ObservableList<String> row : data) {
                    StringBuilder values = new StringBuilder();
                    for (String cell : row) {
                        values.append("'").append(cell.replace("'", "''")).append("',");
                    }
                    values.setLength(values.length() - 1); // Remove trailing comma
                    String insertQuery = String.format("INSERT INTO Orders (%s) VALUES (%s)",
                            String.join(", ", columnNames), values.toString());
                    stmt.executeUpdate(insertQuery);
                }

                conn.commit(); // Commit changes
               // showAlert("Success", "Data saved successfully!");
               Notifications noti = Notifications.create();
    noti.title("Successful");
    noti.text("Successful Update");
    noti.hideAfter(Duration.seconds(3));
    noti.position(Pos.CENTER);
    noti.showInformation();
    
    refresh.fire();
    del.setVisible(false);
    
    
    ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" updated an order.");
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
        } catch (ClassNotFoundException e) {
         //   showAlert("Error", "SQLite JDBC driver not found.");
        } catch (SQLException e) {
          //  showAlert("Error", "Failed to save data to the database: " + e.getMessage());
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful update");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
              
              refresh.fire();
        }
        
        
        
        
    }
    
      
      
      
        @FXML
    void refaction(MouseEvent event) {
        
        refresh.fire();

    }
    
    
    
    @FXML
    void filaction(MouseEvent event) {
        
        
ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
table.getColumns().clear();

// Load Orders from first DB
try {
    String sql = "SELECT * FROM Orders";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    // Create columns dynamically
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // Fetch data into rows
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getString(i));
        }
        data.add(row);
    }

    // Close first DB
    rs.close();
    pst.close();

} catch (Exception e) {
    e.printStackTrace();
}

// Add new column "Processes"
TableColumn<ObservableList<String>, String> processCol = new TableColumn<>("Processes");
processCol.setCellValueFactory(param -> {
    ObservableList<String> row = param.getValue();
    String po = row.get(3);  // Column 4
    String sapCode = row.get(4);  // Column 5

    String result = getProcessForOrder(po, sapCode);  // Call helper method
    return new SimpleStringProperty(result);
});
table.getColumns().add(processCol);

// Set data to table
table.setItems(data);

// Add TableFilter if needed
new TableFilter<>(table);
       


//editt();
          del.setVisible(false);

    }
    
    
      @FXML
    void delaction(MouseEvent event) {
        
        deleteorder.fire();

    }
    
    
    @FXML
    void addaction(MouseEvent event) {
        
        addorder.fire();

    }
      
      
     @FXML
    void updaction(MouseEvent event) {

        updateorder.fire();
        
    }  
      
    
    @FXML
    void shipmentpathaction1(ActionEvent event) {
        
//         if (washname.getText().isEmpty()==true) {
//              Notifications noti = Notifications.create();
//              noti.title("Unsuccessful");
//              noti.text("Unsuccessful Operation.\nPlease choose an order first.");
//              noti.hideAfter(Duration.seconds(3));
//              noti.position(Pos.CENTER);
//              noti.showError();
//         }
//        
//         else {
//             
             
               //Manual
        try {
    Parent content = FXMLLoader.load(getClass().getResource("ShipmentPath2.fxml"));

    Stage stage = new Stage();
    stage.setTitle("مسار الشحنة يدويا");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}

             
//         }
        

    }

     @FXML
    void shipmentpathaction2(ActionEvent event) {
        
//        if (washname.getText().isEmpty()==true) {
//              Notifications noti = Notifications.create();
//              noti.title("Unsuccessful");
//              noti.text("Unsuccessful Operation.\nPlease choose an order first.");
//              noti.hideAfter(Duration.seconds(3));
//              noti.position(Pos.CENTER);
//              noti.showError();
//         }
//        
//        else {
            
             //Recipe
        
             
             
             
        try {
    Parent content = FXMLLoader.load(getClass().getResource("ShipmentPath1.fxml"));

    Stage stage = new Stage();
    stage.setTitle("مسار الشحنة عن طريق الريسيبي");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}

          
        
        
//        }
        

    }
   
    
    private static Map<String, String> loadChemicalDictionary(String filePath) {
        Map<String, String> dictionary = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    dictionary.put(parts[0].trim().toUpperCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return dictionary;
    }

    
    
    private static String getBestMatch(String input, Map<String, String> dictionary) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        String bestMatch = "";
        int minDistance = Integer.MAX_VALUE;
        for (String key : dictionary.keySet()) {
            int distance = levenshtein.apply(input, key);
            if (distance < minDistance) {
                minDistance = distance;
                bestMatch = dictionary.get(key);
            }
        }
        return bestMatch;
    }
    
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

     
  
    
    private void startAutoRefresh2() {
        refreshTimeline2 = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            Platform.runLater(this::fetching);
        }));
        refreshTimeline2.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline2.play();
    }
    
    private void startAutoRefresh3 () {
        refreshTimeline3 = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            Platform.runLater(this::fofofo);
        }));
        refreshTimeline3.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline3.play();
    }
    
    private void fetching() {
        


// حذف العناصر القديمة إن وُجدت
onlineusersmenu.getItems().clear();

try {
    String sql = "SELECT User, Position FROM USERS WHERE Status = 'Online'";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    while (rs.next()) {
        String username = rs.getString("User");
        String position = rs.getString("Position");

        // إنشاء العنصر مع النقطة الخضراء واسم المستخدم والمنصب
        MenuItem userItem = createUserWithGreenDot(username, position);
        onlineusersmenu.getItems().add(userItem);
    }

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
       
    }

     
//    private void fetching() {
//        
//
//
//// حذف العناصر القديمة إن وُجدت
//onlineusersmenu.getItems().clear();
//
//try {
//    String sql = "SELECT User, Position FROM USERS WHERE Status = 'Online'";
//    pst = conn.prepareStatement(sql);
//    rs = pst.executeQuery();
//
//    while (rs.next()) {
//        String username = rs.getString("User");
//        String position = rs.getString("Position");
//
//        // إنشاء العنصر مع النقطة الخضراء واسم المستخدم والمنصب
//        MenuItem userItem = createUserWithGreenDot(username, position);
//        onlineusersmenu.getItems().add(userItem);
//    }
//
//} catch (Exception e) {
//    e.printStackTrace();
//} finally {
//    try {
//        if (rs != null) rs.close();
//        if (pst != null) pst.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//
//        
//    }
    
    private MenuItem createUserWithGreenDot(String username, String position) {
    Circle statusDot = new Circle(5);
    statusDot.setFill(Color.LIMEGREEN); // لون النقطة (متصل)

    Text nameText = new Text(username + "  (" + position + ")");
    HBox hbox = new HBox(6, statusDot, nameText);
    MenuItem item = new MenuItem();
    item.setGraphic(hbox);
    return item;
}
    
    
  private void fofofo() {
    mainSplit1.setDisable(false);

    try (PreparedStatement pst = conn.prepareStatement(
            "SELECT * FROM Message WHERE M_To = ?")) {

        pst.setString(1, selsecc);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("ID");
            int delivered = rs.getInt("Delivered");
            String dat = rs.getString("Date");
            String tim = rs.getString("Time");
            String secfrom = rs.getString("Sec_From");
            String mfrom = rs.getString("M_From");
            String mto = rs.getString("M_To");
            String msg = rs.getString("Message");

            // لو Delivered = 1 → نمسح الرسالة من قاعدة البيانات
            if (delivered == 1) {
                try (PreparedStatement delpst = conn.prepareStatement(
                        "DELETE FROM Message WHERE ID = ?")) {
                    delpst.setInt(1, id);
                    delpst.executeUpdate();
                    
                                             
////////////////////////////////////////////////////////////
         
                    try {
    final Path path = Paths.get(diro + "\\Java\\bin\\Alpha_Logs.kady");
    Files.write(path, Arrays.asList("Message Deleted: \nDate: "+dat+"\nTime: "+tim+"\nFrom Section: "+secfrom+"\nFrom User: "+mfrom+"\nMessage To: "+mto+"\nMessage Content: "+msg+"\n\n"), StandardCharsets.UTF_8,
        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
} catch (final IOException ioe) {
    // Add your own exception handling...
}
                    
////////////////////////////////////////////////////////////


                    
////////////////////////////////////////////////////////////
         
                    try {
    final Path path = Paths.get(diro + "\\Java\\bin\\Alpha_Logs.kady");
    Files.write(path, Arrays.asList(), StandardCharsets.UTF_8,
        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
} catch (final IOException ioe) {
    // Add your own exception handling...
}
                    
////////////////////////////////////////////////////////////

                }
                continue;
            }

//            // === رسالة مع word wrap ===
//            Label cb = new Label(
//                "Date: " + dat + "\n" +
//                "Time: " + tim + "\n" +
//                "From Section: " + secfrom + "\n" +
//                "From User: " + mfrom + "\n" +
//                "To Section: " + mto + "\n\n\n" +
//                "Message Body: " + msg
//            );
//            cb.setWrapText(true); // <<< يخلي النص يلف تلقائي
//            cb.setMaxWidth(500);  // <<< عرض الكارد علشان يلف جوّه
//
//            try (BufferedReader bis = new BufferedReader(new FileReader(
//                    System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
//                String themooo = bis.readLine();
//                if ("cupertino-dark.css".equals(themooo)) {
//                    cb.setStyle(
//                        "-fx-font-size: 14px;" +
//                        "-fx-font-weight: bold;" +
//                        "-fx-font-family: 'Arial';" +
//                        "-fx-text-fill: white;"
//                    );
//                } else {
//                    cb.setStyle(
//                        "-fx-font-size: 14px;" +
//                        "-fx-font-weight: bold;" +
//                        "-fx-font-family: 'Arial';"
//                    );
//                }
//            } catch (Exception re) {}
//
//            VBox card = new VBox(cb);
//            card.setSpacing(10);
//            card.setPadding(new Insets(10));
//            card.setMaxWidth(420);
//
//            try (BufferedReader bis = new BufferedReader(new FileReader(
//                    System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
//                String themooo = bis.readLine();
//                if ("cupertino-dark.css".equals(themooo)) {
//                    card.setStyle(
//                        "-fx-background-color: #1C1C1E;" +
//                        "-fx-background-radius: 12;" +
//                        "-fx-border-radius: 12;" +
//                        "-fx-border-color: rgba(255,255,255,0.08);" +
//                        "-fx-border-width: 1;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);" +
//                        "-fx-padding: 10;"
//                    );
//                } else {
//                    card.setStyle(
//                        "-fx-background-color: white;" +
//                        "-fx-background-radius: 12;" +
//                        "-fx-border-radius: 12;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);"
//                    );
//                }
//            } catch (Exception re) {}
//
//            card.setAlignment(Pos.CENTER_LEFT);



       // === Header Info (صغير ولونه رمادي) ===
Label header = new Label(
    "Date: " + dat + " | " +
    "Time: " + tim + "\n" +
    "From: " + mfrom + ", In: " + secfrom + " → To: " + mto
);
header.setWrapText(true);
header.setMaxWidth(400);
header.setStyle(
    "-fx-font-size: 13px;" +
    "-fx-font-family: 'Cairo SemiBold';" +        
    "-fx-text-fill: #333d29;" +
    "-fx-font-weight: bold;"         
    
);

// === Message Body (Bubble) ===
Label message = new Label(msg);
message.setWrapText(true);
message.setMaxWidth(500);  // عرض البالونة

try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        message.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-family: 'Cairo SemiBold';" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +   // أزرق زي iMessage
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 12 8 12;"
        );
    } else {
        message.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-family: 'Cairo SemiBold';" +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +  // رمادي فاتح زي iOS
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 12 8 12;"
        );
    }
} catch (Exception re) {
    message.setStyle(
        "-fx-font-size: 15px;" +
        "-fx-font-family: 'Cairo SemiBold';" +
        "-fx-text-fill: black;" +
        "-fx-font-weight: bold;" +        
        "-fx-background-color: #E5E5EA;" +
        "-fx-background-radius: 15;" +
        "-fx-padding: 8 12 8 12;"
    );
}

// === Final Card ===
VBox card = new VBox(5, header, message);
card.setPadding(new Insets(10));
card.setMaxWidth(420);

// خلفية الكارد نفسها (تقدر تخليها شفافة لو عايز زي واتساب)
try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        card.setStyle(
            "-fx-background-color: transparent;" +   // شفافة زي شات
            "-fx-border-radius: 12;"
        );
    } else {
        card.setStyle(
            "-fx-background-color: transparent;" +   // شفافة برضو
            "-fx-border-radius: 12;"
        );
    }
} catch (Exception re) {}

card.setAlignment(Pos.TOP_LEFT);


            shipmentList1.getChildren().add(card);
            mainSplit1.setVisible(true);

            // === إشعار منبثق ===
            String allText = "لقد تم استلام رسالة جديدة من المستخدم " + mfrom + " في القسم " + secfrom +
                             "\n\n" +
                             "Date: " + dat + "\n" +
                             "Time: " + tim + "\n" +
                             "From Section: " + secfrom + "\n" +
                             "From User: " + mfrom + "\n" +
                             "To Section: " + mto + "\n\n" +
                             "Message:\n" + msg;

            Popup notificationPopup = new Popup();
            VBox mainContainer = new VBox(10);
            mainContainer.setPadding(new Insets(20));
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.setStyle(
                "-fx-background-color: white;" +
                        
                "-fx-background-radius: 16;" +
                "-fx-border-color: #e0e0e0;" +
                "-fx-border-radius: 16;" +
                "-fx-border-width: 1;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0.4, 0, 6);"
            );

            Label notificationLabel = new Label(allText);
            notificationLabel.setWrapText(true);
            notificationLabel.setMaxWidth(500);
            notificationLabel.setTextAlignment(TextAlignment.RIGHT);
            notificationLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            notificationLabel.setStyle(
                "-fx-font-family: 'Cairo SemiBold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #333333;"
            );

            Button closeButton = new Button("Close");
            closeButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #d32f2f;" +
                "-fx-font-size: 16px;" +
                "-fx-cursor: hand;"
            );
            closeButton.setOnAction(e -> notificationPopup.hide());

            mainContainer.getChildren().addAll(notificationLabel, closeButton);
            notificationPopup.getContent().add(mainContainer);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            mainContainer.applyCss();
            mainContainer.layout();
            double popupWidth = mainContainer.getWidth();
            double popupHeight = mainContainer.getHeight();
            double popupX = (bounds.getWidth() - popupWidth) / 2;
            double popupY = (bounds.getHeight() - popupHeight) / 2;

            Stage stage = (Stage) table.getScene().getWindow();
            notificationPopup.show(stage);
            notificationPopup.setX(popupX);
            notificationPopup.setY(popupY);

            mainContainer.setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), mainContainer);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\message.mp3").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
            
            
             // Load icons from classpath
        Image icon1 = new Image(getClass().getResource("inbox1.png").toExternalForm());
       

        ImageView view = new ImageView(icon1);
        view.setFitWidth(30);
        view.setFitHeight(30);
        inbox.setGraphic(view);

      

            try (PreparedStatement pst2 = conn.prepareStatement(
                    "UPDATE Message SET Delivered = 1 WHERE ID = ?")) {
                pst2.setInt(1, id);
                pst2.executeUpdate();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    
    
    @Override
public void initialize(URL url, ResourceBundle rb) {
    
    
     try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            javafx.scene.text.Font cairoSemiBold = javafx.scene.text.Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WASHINGController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    conn = db.java_db();
    
    // اقرأ من Settings.kady الأول (قبل أي اتصال DB)
    String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
    try {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            if (line.startsWith("DB=")) {
                String value = line.split("=", 2)[1];
                databaseUrl = "jdbc:sqlite:" + value;
                break;
            }
        }
    } catch (IOException ex) {
        Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Auto refresh
    startAutoRefresh2();
    startAutoRefresh3();
    selsecc = LogIn_GUI_Controller.selectedpositionn;
    seluserr = LogIn_GUI_Controller.selecteduser;

    // التاريخ الحالي
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    value1 = sdf.format(d);

    
    // تحميل البيانات لجدول TableView
    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    table.getColumns().clear();

    try (Connection conn = DriverManager.getConnection(databaseUrl);
         Statement pragmaStmt = conn.createStatement()) {

        pragmaStmt.execute("PRAGMA busy_timeout = 3000;");

        String sql = "SELECT * FROM WareHouse_1";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Create columns dynamically
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> col =
                        new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                table.getColumns().add(col);
            }

            // Fetch data into rows
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Set data to table
    table.setItems(data);

    // Add TableFilter if needed
    new TableFilter<>(table);

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // UI Settings
    del.setVisible(false);

    refresh.setDisable(true);
    addorder.setDisable(true);
    updateorder.setDisable(true);
    deleteorder.setDisable(true);
    clearsel.setDisable(true);
    ref.setDisable(true);
    upd.setDisable(true);
    add.setDisable(true);
    del.setDisable(true);
    fil.setDisable(true);
    sendtowh1.setDisable(true);
    stocky.setDisable(false);

    ref.setTooltip(new Tooltip("Refresh Orders - تحديث"));
    upd.setTooltip(new Tooltip("Update Modifications - تحديث الشحنات"));
    add.setTooltip(new Tooltip("Add Order - اضافة شحنة"));
    fil.setTooltip(new Tooltip("Filter - تصفية الشحنات"));
    del.setTooltip(new Tooltip("Delete Order - حذف الشحنة"));

    // Add a listener to update TextFields when the selection changes
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
            
            

            del.setVisible(false);
        }
    });

    // Set icons
    fill.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("file.png"))));
    hel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("helpdesk.png"))));
    ships.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("purchase-order.png"))));
    onlineusersmenu.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("read.png"))));

    gotowh1.setFocusTraversable(true);
    
    
    try {
            String path = AlphaPlanner.class.getProtectionDomain()
            .getCodeSource().getLocation().toURI().getPath();
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
            File file = new File(decodedPath);
            String dir = file.isFile() ? file.getParent() : file.getPath();
            diro = dir;
        } catch (Exception e) {
            e.printStackTrace();
        }

    
    
    
  
fileCheckTimer = new Timer(true); // Daemon thread
fileCheckTimer.scheduleAtFixedRate(new TimerTask() {
    @Override
    public void run() {
        File file = new File("CAUTION.kady");
        if (!file.exists()) {

            //////////////////////////////////////////////////////////////////////////

            Platform.runLater(() -> {
                String title = "🚧 System Maintenance | صيانة النظام 🚧";
                String header = "⚠ Service Unavailable | الخدمة غير متاحة";
                String content =
                        "Dear User,\n" +
                        "🛠 We are currently upgrading and improving the system to serve you better.\n" +
                        "⏳ During this maintenance, the system will not be available.\n" +
                        "🙏 Thank you for your patience and understanding.\n\n" +
                        "—---------------------------------------------\n\n" +
                        "عزيزي المستخدم،\n" +
                        "🛠 نحن نقوم حالياً بتطوير وتحسين النظام لنخدمك بشكل أفضل.\n" +
                        "⏳ خلال فترة الصيانة لن تكون الخدمة متاحة.\n" +
                        "🙏 نشكرك على صبرك وتفهمك.\n\n" +
                        "💡 Please try again later | الرجاء المحاولة لاحقاً";

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);
                alert.setResizable(true); // يسمح بتوسيع النافذة

                DialogPane dialogPaneo = alert.getDialogPane();
                dialogPaneo.getStylesheets().add(
                        getClass().getResource("cupertino-light.css").toExternalForm()
                );

                // show alert (non-blocking)
                alert.show();

                // countdown 5 seconds then close alert and shutdown
                javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(15));
                delay.setOnFinished(e -> {
                    alert.close();
                    shutdownApp();
                });
                delay.play();
            });

            //////////////////////////////////////////////////////////////////////////

        } else {
            System.out.println("File found. App continues running.");
        }
    }
}, 0, 1 * 60 * 1000); // كل دقيقة (انت كاتب 1 * 60 * 1000 = 1 دقيقة مش 2 😉)



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
    pst.setString(1, seluserr);
    pst.setString(2, selsecc);
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

        
    }
    
        
private void shutdownApp() {
        fileCheckTimer.cancel(); // Stop the timer
        // Run on JavaFX thread
        Platform.runLater(() -> {
            Platform.exit(); // Close JavaFX
   
    
    if (refreshTimeline2 != null) {
        refreshTimeline2.stop();
    }
    
    if (refreshTimeline3 != null) {
        refreshTimeline3.stop();
    }
    if (fileCheckTimer != null) {
        fileCheckTimer.cancel();
    }
            System.exit(0);  // Kill all remaining threads
        });
    }
    
    




    
    
// Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}



private String getProcessForOrder(String po, String sapCode) {
    String result = "ملقتش ريسيبي والله او في حاجة غلط بص ممكن يكون السبب متعملهاش مراحل لسه او الاسم غلط";
    File settings = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady");
    String recipeDBPath = getRecipeDBPath(settings);
    String db2Path = "jdbc:sqlite:"+recipeDBPath+""; // update this path
    String query = "SELECT Processes FROM Recipe_Processes WHERE Model = ? AND WashName = ?";

    try (Connection conn2 = DriverManager.getConnection(db2Path);
         PreparedStatement pst2 = conn2.prepareStatement(query)) {

        pst2.setString(1, po);
        pst2.setString(2, sapCode);

        try (ResultSet rs2 = pst2.executeQuery()) {
            if (rs2.next()) {
                result = rs2.getString("Processes");  // or whatever column you want
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return result;
}


public String getRecipeDBPath(File settingsFile) {
    try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Recipe_DB=")) {
                return line.substring("Recipe_DB=".length()).trim();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null; // Or throw an exception / default value
}



    
    
}
