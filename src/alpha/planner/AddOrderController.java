
package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.spire.xls.CellRange;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class AddOrderController implements Initializable {

    Timer fileCheckTimer;
    
     @FXML
    private MFXTextField poamount;

    @FXML
    private MFXTextField washname;
    
    @FXML
    private ImageView messenger;
    
     @FXML
    private ImageView inbox;

    @FXML
    private MFXTextField po;

    @FXML
    private MFXDatePicker laundrydate;

    @FXML
    private MFXTextField cuttingamount;

    @FXML
    private MFXTextField sapcode;

    @FXML
    private MFXTextField style;

    @FXML
    private MFXTextField customer;

    @FXML
    private MFXDatePicker xfacdate;

    @FXML
    private MFXButton addorder;

    @FXML
    private ImageView excel;

    @FXML
    private Label excelpathlabel,excellink;

    @FXML
    private MFXTextField excelpathtf,excelcell;

    @FXML
    private MFXButton writedata;

    @FXML
    private MFXButton browse;
    
    @FXML
    private VBox wait;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    static String valll; 
    
    public static String selsecc,seluserr;
    
    public static String value1;
    
      @FXML
     private TableView<ObservableList<String>> table;
      
    @FXML
    private JFXButton refreshh,seto,archive,logout;
    
    public static String diro;
    
    public static String  poo,
        sapcodee,
        stylee,
        customerr,
        washnamee,
        poamountt,
        cuttingamountt,
        laundrydatee,
        xfacdatee;
    
    private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    static String databaseUrl; // Path to your SQLite DB
    private ObservableList<String> columnNames = FXCollections.observableArrayList();

    @FXML private VBox sidePanel1;
    @FXML private VBox shipmentList1;
    @FXML private SplitPane mainSplit1;
    
    private Timeline refreshTimeline3;
    
    
    
    @FXML
    void excelaction(MouseEvent event) throws FileNotFoundException, IOException {

        
         
        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook();
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
        
        
    }
    
    @FXML
    void archiveaction(ActionEvent event) throws IOException {

    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Archive.fxml"));
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
    stg.setTitle("Archived Orders");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.centerOnScreen();
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
    stg.setOnCloseRequest( tt -> {
        editt();
        //System.out.println("Good");
    });
        
    }
    
     @FXML
    void closepanel(ActionEvent event) throws IOException {
        
        mainSplit1.setVisible(false);
       
    
     try {
    String sqla = " UPDATE Message SET Delivered = 1 WHERE M_To = ? ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, selsecc);
          this.pst.executeUpdate();
              }
              catch (Exception e) {
          
        } finally {
          try {
            this.rs.close();
            this.pst.close();
          } catch (Exception exception) {}
        }  
    
    
    
        
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
    Stage jk = (Stage)this.table.getScene().getWindow();
    jk.close();
    
    //////////////////////////////////////////////////
    
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
    
    
      private void startAutoRefresh3 () {
        refreshTimeline3 = new Timeline(new KeyFrame(Duration.seconds(3), e -> {//Messages
            Platform.runLater(this::fofofo);
        }));
        refreshTimeline3.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline3.play();
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


                    
                }
                continue;
            }

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
    "-fx-text-fill: #333d29;" +
    "-fx-font-weight: bold;" +         
    "-fx-font-family: 'Cairo SemiBold';"
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
                "-fx-font-family: 'Cairo SemiBold';" +         
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
                "-fx-font-family: 'Cairo SemiBold';" +         
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
            
            
        Image img1 = new Image(getClass().getResource("inbox.png").toExternalForm());
        
      
            if (inbox.getImage() == img1) {
                inbox.setImage(img1);
            } else {
                inbox.setImage(img1);
            }
       
            

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

    
    ///////////////////////////////////////////////////////////
    
     

    
   private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
  
     
      @FXML
    void messengeraction(MouseEvent event) throws IOException {
        
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
void inboxaction(MouseEvent event) throws IOException {
    // toggle visibility
    mainSplit1.setVisible(!mainSplit1.isVisible());
}
   
     
     @FXML
    void deleteeaction(ActionEvent event) {

        
        
        
        //Delete
        
        
        
            String pol=  poo;
            String sapcodel= sapcodee;
            String stylel=  stylee;
            String ordernamel= washnamee;
            String customerrr= customerr;
            
            String e1= poamountt;
            String e2= cuttingamountt;
            String e3= laundrydatee;
            String e4= xfacdatee;
        
        
         if (ordernamel.isEmpty()==true) {
              
              Notifications noti = Notifications.create();
              noti.title("طلب غير ناجح");
              noti.text("لم يتم الحذف.\nلو سمحت حدد اوردر علشان امسحه.");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
              
          }
         
         
         else {
             
             
        Alert alertd = new Alert(Alert.AlertType.CONFIRMATION);
        alertd.setTitle("حذف اوردر");
        alertd.setHeaderText("عاوز تعمل ايه مع الاوردر ده");
        alertd.setContentText("ممكن توديه الارشيف او تمسحه خالص للابد");
        ButtonType buttonTypeOned = new ButtonType("الارشيف");
        ButtonType buttonTypeCanceld = new ButtonType("امسحه للابد");
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
            
            //Archive
            
                
                  
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("أرشفة اوردر");
      //alert.setHeaderText("أنت عاوز بجد تمسح الاوردر ده؟\n\nخد بالك مش هتقدر ترجعه تاني خالص");
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
          
                         
String reg = "INSERT INTO Archived_Orders (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, Incoming_Date, X_Fac_Date) VALUES (?,?,?,?,?,?,?,?,?)";
pst = conn.prepareStatement(reg);
pst.setString(1, pol);   // Change index from 0 to 1
pst.setString(2, sapcodel);
pst.setString(3, stylel);
pst.setString(4, customerrr);
pst.setString(5, ordernamel);  // Ensure `order` is not a reserved keyword
pst.setString(6, e1);
pst.setString(7, e2);
pst.setString(8, e3);
pst.setString(9, e4);
pst.execute();
            
            }catch(Exception e){
              Notifications notiv = Notifications.create();
              notiv.title("طلب غير ناجح");
              notiv.text("مفيش حاجة اتارشفت يا معلم");
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
    
          
         ///////////////////////////////////////Delete///////////////////////////////
            
               
            try {
            
            String sql = "delete from Orders where PO='"+pol+"' and Sap_Code='"+sapcodel+"' and Style='"+stylel+"' and Customer='"+customerrr+"' and Wash_Name='"+ordernamel+"' and PO_Amount='"+e1+"' and Cutting_Amount='"+e2+"' and Incoming_Date='"+e3+"' and X_Fac_Date='"+e4+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            
              Notifications noti = Notifications.create();
              noti.title("طلب ناجح");
              noti.text("تم الارشفة بنجاح يا معلم");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showInformation();
      
              
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
    
          
            po.clear();
            sapcode.clear();
            style.clear();
            washname.clear();
            customer.clear();
            
            poamount.clear();
            cuttingamount.clear();
            laundrydate.clear();
            xfacdate.clear();
           
            
             refreshh.fire();
           
       
           
             ////////////////////////Audit//////////////////////////
      
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    String value1 = timeString;
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" archived an order and its data is: Customer is: "+customerrr+", Order name is: "+ordernamel+" and Style is: "+stylel);
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
      noti.title("عاوز تكنسل");
      noti.text("العملية اتلغت والاوردر زي ما هو موجود");
      noti.position(Pos.CENTER);
      noti.showInformation();
      
      }else {}
        
            
            
        }
             
             
        else if (resultsd.isPresent() && resultsd.get() == buttonTypeCanceld) {
            
                       
             
             
                  
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("حذف اوردر");
      alert.setHeaderText("أنت عاوز بجد تمسح الاوردر ده؟\n\nخد بالك مش هتقدر ترجعه تاني");
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
              noti.title("طلب ناجح");
              noti.text("تم الحذف بنجاح يا معلم");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showInformation();
              
            
                                          
////////////////////////////////////////////////////////////
         
                    try {
    final Path path = Paths.get(diro + "\\Java\\bin\\Alpha_Logs.kady");
    Files.write(path, Arrays.asList(seluserr+" from "+selsecc+" has deleted order:\n"+"PO='"+pol+"'\nSap_Code='"+sapcodel+"'\nStyle='"+stylel+"'\nCustomer='"+customerrr+"'\nWash_Name='"+ordernamel+"'\nPO_Amount='"+e1+"'\nCutting_Amount='"+e2+"'\nIncoming_Date='"+e3+"'\nX_Fac_Date='"+e4+"'\n\n"), StandardCharsets.UTF_8,
        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
} catch (final IOException ioe) {
    // Add your own exception handling...
}
                    
////////////////////////////////////////////////////////////


            
              
            }catch(Exception e){
              Notifications notiv = Notifications.create();
              notiv.title("طلب غير ناجح");
              notiv.text("مفيش حاجة اتمسحت يا معلم");
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
           
            
            refreshh.fire();
           
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
      noti.title("عاوز تكنسل");
      noti.text("العملية اتلغت والاوردر زي ما هو موجود");
      noti.position(Pos.CENTER);
      noti.showInformation();
      
      }else {}
        
            
            
        }   
        
        else {
            return;
        }
             
  
        
       
             
         }
        
        
    }

    
    
   
   
   
    @FXML
    void filterraction(ActionEvent event) {
     
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

    
    

  @FXML
    void processessaction(ActionEvent event) {

        
        wait.setVisible(true);
        
        Platform.runLater( ()   -> {
            
             
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
       
            
            
            wait.setVisible(false);
            
        });
       
        
    }
    

   
     @FXML
    void updateeaction(ActionEvent event) {

        saveDataToDatabase();
        
    }
    
  
    public void editt()   {
        
        
        
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the database
            try (Connection conn = DriverManager.getConnection(databaseUrl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {

                // Clear previous data
                table.getColumns().clear();
                data.clear();
                columnNames.clear();

                // Define the columns that should use ComboBox (example: column index 1)
                //Set<Integer> comboBoxColumns = Set.of(1); // Adjust as needed
                
                Set<Integer> comboBoxColumns = new HashSet<>();
                comboBoxColumns.add(1); // Add column index 1 as a ComboBox


                // Get column names
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int colIndex = i;
                    String colName = rs.getMetaData().getColumnName(i + 1);
                    columnNames.add(colName);

                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(colName);
                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));

                    // Check if the column should use a ComboBox
                    if (comboBoxColumns.contains(colIndex)) {
                        
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                       // column.setCellFactory(col -> new ComboBoxTableCell<>(
                           //     FXCollections.observableArrayList("Option 1", "Option 2", "Option 3")));
                    } else {
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                    }

                    column.setOnEditCommit(eventt -> {
                        ObservableList<String> row = eventt.getRowValue();
                        row.set(colIndex, eventt.getNewValue());
                    });

                    //column.setContextMenu(createColumnContextMenu(colIndex));
                    table.getColumns().add(column);
                }

                // Get data rows
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }
                table.setItems(data);
                table.setEditable(true);

                // Add row context menu
                table.setRowFactory(tv -> {
                    TableRow<ObservableList<String>> row = new TableRow<>();
                    //row.setContextMenu(createRowContextMenu(row));
                    return row;
                });

            }
        } catch (ClassNotFoundException e) {
          //  showAlert("Error", "SQLite JDBC driver not found.");
        } catch (SQLException e) {
          //  showAlert("Error", "Failed to load data from the database: " + e.getMessage());
        }
        
        
    }
    
    
    
    
    
    public void editto()   {
        
        
        
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the database
            try (Connection conn = DriverManager.getConnection(databaseUrl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {

                // Clear previous data
                table.getColumns().clear();
                data.clear();
                columnNames.clear();

                // Define the columns that should use ComboBox (example: column index 1)
                //Set<Integer> comboBoxColumns = Set.of(1); // Adjust as needed
                
                Set<Integer> comboBoxColumns = new HashSet<>();
                comboBoxColumns.add(1); // Add column index 1 as a ComboBox


                // Get column names
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int colIndex = i;
                    String colName = rs.getMetaData().getColumnName(i + 1);
                    columnNames.add(colName);

                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(colName);
                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));

                    // Check if the column should use a ComboBox
                    if (comboBoxColumns.contains(colIndex)) {
                        
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                       // column.setCellFactory(col -> new ComboBoxTableCell<>(
                           //     FXCollections.observableArrayList("Option 1", "Option 2", "Option 3")));
                    } else {
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                    }

                    column.setOnEditCommit(eventt -> {
                        ObservableList<String> row = eventt.getRowValue();
                        row.set(colIndex, eventt.getNewValue());
                    });

                    //column.setContextMenu(createColumnContextMenu(colIndex));
                    table.getColumns().add(column);
                }

                // Get data rows
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }
                table.setItems(data);
                table.setEditable(true);

                // Add row context menu
                table.setRowFactory(tv -> {
                    TableRow<ObservableList<String>> row = new TableRow<>();
                    //row.setContextMenu(createRowContextMenu(row));
                    return row;
                });

            }
        } catch (ClassNotFoundException e) {
          //  showAlert("Error", "SQLite JDBC driver not found.");
        } catch (SQLException e) {
          //  showAlert("Error", "Failed to load data from the database: " + e.getMessage());
        }

        
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
    
    refreshh.fire();
    

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
              
              refreshh.fire();
        }
    }
    
    
    
    
   
     @FXML
    void refreshhaction(ActionEvent event) {

//        
//ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
//table.getColumns().clear();
//
//// Load Orders from first DB
//try {
//    String sql = "SELECT * FROM Orders";
//    pst = conn.prepareStatement(sql);
//    rs = pst.executeQuery();
//
//    // Create columns dynamically
//    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//        final int j = i;
//        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
//        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
//        table.getColumns().add(col);
//    }
//
//    // Fetch data into rows
//    while (rs.next()) {
//        ObservableList<String> row = FXCollections.observableArrayList();
//        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//            row.add(rs.getString(i));
//        }
//        data.add(row);
//    }
//
//    // Close first DB
//    rs.close();
//    pst.close();
//
//} catch (Exception e) {
//    e.printStackTrace();
//}
//
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
//
//// Set data to table
//table.setItems(data);
//
//// Add TableFilter if needed
//new TableFilter<>(table);
       
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
    Stage jk = (Stage)poamount.getScene().getWindow();
    //jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)poamount.getScene().getWindow();
    //jk.setOpacity(0.4);
        
    }
    
    
     @FXML
    void spellaction(ActionEvent event) throws IOException {

        
        FuzzyReplaceApp gfd = new FuzzyReplaceApp();
        gfd.start(new Stage());
        
    }

   
   
   
  //Manual
    
    @FXML
    void addorderaction(ActionEvent event) {

    String ppo=po.getText();
    String saapcoode=sapcode.getText();
    String sttyle=style.getText();
    String cuustomer=customer.getText();    
    String order= washname.getText();
    String poamt=poamount.getText();
    String cutamt=cuttingamount.getText();
    String laudate=laundrydate.getText();
    String xfadate=xfacdate.getText();
    
    
    
    
           //Save To Database......................................
        
        if (ppo.isEmpty()||saapcoode.isEmpty()||sttyle.isEmpty()||cuustomer.isEmpty()||order.isEmpty()||poamt.isEmpty()||cutamt.isEmpty()||laudate.isEmpty()||xfadate.isEmpty()) {
            
        Alert al=new Alert (AlertType.ERROR);
        al.setTitle("Fatal Error");
        al.setHeaderText("Fatal Error");
        al.setContentText("Please, Fill all info fields first.");
        al.setResizable(false);
        DialogPane dialogPane = al.getDialogPane();
        
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
        
        
        al.showAndWait(); 
        po.requestFocus();
          
        }
        
        
        
String numberRegex = "\\d+";
String dateRegex = "\\d{4}-\\d{2}-\\d{2}";  // YYYY-MM-DD

boolean ok =
        poamt.matches(numberRegex) &&
        cutamt.matches(numberRegex) &&
        laudate.matches(dateRegex) &&
        xfadate.matches(dateRegex);

if (!ok) {

    Alert al = new Alert(AlertType.ERROR);
    al.setTitle("Fatal Error");
    al.setHeaderText("Fatal Error");
    al.setContentText("You must enter number or date formats only.");
    al.setResizable(false);

    DialogPane dialogPane = al.getDialogPane();

    try {
        BufferedReader bis = new BufferedReader(new FileReader(
                System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));

        String theme = bis.readLine();
        bis.close();

        if (theme.equals("cupertino-dark.css")) {
            dialogPane.getStylesheets().add(getClass().getResource("cupertino-dark.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
        }
    } catch (Exception ignored) {}

    al.showAndWait();
    po.requestFocus();
}

        
        
        else {
            
            //////////////////////////////Continue//////////////////////////////////////////////
            Date currentDate = GregorianCalendar.getInstance().getTime();
            DateFormat df = DateFormat.getDateInstance();
            String dateString = df.format(currentDate);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeString = sdf.format(d);
            String dayoftoday = timeString;
            
            try{
                
                
String reg = "INSERT INTO Orders (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, Incoming_Date, X_Fac_Date) VALUES (?,?,?,?,?,?,?,?,?)";
PreparedStatement pst = conn.prepareStatement(reg);
pst.setString(1, ppo);   // Change index from 0 to 1
pst.setString(2, saapcoode);
pst.setString(3, sttyle);
pst.setString(4, cuustomer);
pst.setString(5, order);  // Ensure `order` is not a reserved keyword
pst.setString(6, poamt);
pst.setString(7, cutamt);
pst.setString(8, laudate);
pst.setString(9, xfadate);

int rowsInserted = pst.executeUpdate();  // Use executeUpdate() instead
                
    if (rowsInserted > 0) {
    Notifications noti = Notifications.create();
    noti.title("Successful");
    noti.text("Successful Addition");
    noti.hideAfter(Duration.seconds(3));
    noti.position(Pos.CENTER);
    noti.showInformation();
} else {
     
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful Addition");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
            
}   
               
              
                
            }
            catch (Exception e) 

            {
           
            }
            finally {
            
            try{
                rs.close();
                pst.close();
                
            }
            catch(Exception e){
                
            }
        }
            
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      //Save to PlanSteps and get all textarea steps.
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            po.clear();
            sapcode.clear();
            style.clear();
            washname.clear();
            customer.clear();
            poamount.clear();
            cuttingamount.clear();
            laundrydate.clear();
            xfacdate.clear();
            
            refreshh.fire();
            
              ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" added a new order called "+order+" in"+cuustomer+" Manually.");
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
    
              
        Alert al=new Alert (AlertType.INFORMATION);
        al.setTitle("Successful Order");
        al.setHeaderText("Successful Order");
        al.setContentText("We have inserted your order successfully.");
        al.setResizable(false);
        DialogPane dialogPane = al.getDialogPane();
        
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
        
        
        al.show(); 
      
      
            
        }
        
     
        
       
        
    }

    
    
  
    
    
    
    @FXML
    void exceliconaction(MouseEvent event) {

      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("New Excel Files", new String[]{"*.xlsx"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Old Excel Files", new String[]{"*.xls"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", new String[]{"*.*"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      this.excellink.setText(dirpathe);
      excelpathlabel.setTooltip(new Tooltip ("Hello "+System.getProperty("user.name")+", you can use me to get data from excel sheet.\nYou Opened: "+dirpathe));
      excelpathlabel.setText(dirpathe);
      excelcell.requestFocus();

          ////////////////////////Audit//////////////////////////
      
  
  
        
    }
    
    
    
    //One By One Excel
    
     @FXML
    void excelcellrel(KeyEvent event) {

        
    KeyCode keycode = event.getCode();
    if (keycode == KeyCode.ENTER)  {
        
        String cont=excelcell.getText();
        if (cont.isEmpty()) {
            
            po.clear();
            sapcode.clear();
            style.clear();
            washname.clear();
            customer.clear();
            poamount.clear();
            cuttingamount.clear();
            laundrydate.clear();
            xfacdate.clear();
            
          
        }
        else {
            //Enable All then get excel cells content.
            //1: Enable
            //2: Get Data
            //3: Clear ExcelCell Content.
            
            String rownumberrr=excelcell.getText(); 
            Workbook workbook = new Workbook();
            workbook.loadFromFile(excellink.getText());
            Worksheet sheet = workbook.getWorksheets().get(0);
            ////////////////////////////////////////////////////////
            
            CellRange cell1 = sheet.getRange().get("A"+rownumberrr);
            CellRange cell2 = sheet.getRange().get("B"+rownumberrr);
            CellRange cell3 = sheet.getRange().get("C"+rownumberrr);
            CellRange cell4 = sheet.getRange().get("D"+rownumberrr);
            CellRange cell5 = sheet.getRange().get("E"+rownumberrr);
            CellRange cell6 = sheet.getRange().get("F"+rownumberrr);
            CellRange cell7 = sheet.getRange().get("G"+rownumberrr);
            CellRange cell8 = sheet.getRange().get("H"+rownumberrr);
            CellRange cell9 = sheet.getRange().get("I"+rownumberrr);
//            CellRange cell10 = sheet.getRange().get("J"+rownumberrr);
//            CellRange cell11 = sheet.getRange().get("K"+rownumberrr);
//            CellRange cell12 = sheet.getRange().get("L"+rownumberrr);
//            CellRange cell13 = sheet.getRange().get("M"+rownumberrr);
            //CellRange cell14 = sheet.getRange().get("N"+rownumberrr);
            //CellRange cell15 = sheet.getRange().get("O"+rownumberrr);
            
            StringBuilder content1 = new StringBuilder();
            StringBuilder content2 = new StringBuilder();
            StringBuilder content3 = new StringBuilder();
            StringBuilder content4 = new StringBuilder();
            StringBuilder content5 = new StringBuilder();
            StringBuilder content6 = new StringBuilder();
            StringBuilder content7 = new StringBuilder();
            StringBuilder content8 = new StringBuilder();
            StringBuilder content9 = new StringBuilder();
//            StringBuilder content10 = new StringBuilder();
//            StringBuilder content11 = new StringBuilder();
//            StringBuilder content12 = new StringBuilder();
//            StringBuilder content13 = new StringBuilder();
            //StringBuilder content14 = new StringBuilder();
            //StringBuilder content15 = new StringBuilder();
            
            content1.append(cell1.getValue());
            content2.append(cell2.getValue());
            content3.append(cell3.getValue());
            content4.append(cell4.getValue());
            content5.append(cell5.getValue());
            content6.append(cell6.getValue());
            content7.append(cell7.getValue());
            content8.append(cell8.getValue());
            content9.append(cell9.getValue());
//            content10.append(cell10.getValue());
//            content11.append(cell11.getValue().toString());
//            content12.append(cell12.getValue().toString());
//            content13.append(cell13.getValue());
            //content14.append(cell14.getValue());
            //content15.append(cell15.getValue());
            
            po.setText(content1.toString());
            sapcode.setText(content2.toString());
            style.setText(content3.toString());
            customer.setText(content4.toString());
            washname.setText(content5.toString());
            poamount.setText(content6.toString());
            cuttingamount.setText(content7.toString());
            
//            laundrydate.setConverter(new StringConverter<LocalDate>() {
// String pattern = "yyyy-MM-dd";
// DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
//
// {
//     laundrydate.setPromptText(pattern.toLowerCase());
// }
//
// @Override public String toString(LocalDate date) {
//     if (date != null) {
//         return dateFormatter.format(date);
//     } else {
//         return "";
//     }
// }
//
// @Override public LocalDate fromString(String string) {
//     if (string != null && !string.isEmpty()) {
//         return LocalDate.parse(string, dateFormatter);
//     } else {
//         return null;
//     }
// }
//});
//
//xfacdate.setConverter(new StringConverter<LocalDate>() {
// String pattern = "yyyy-MM-dd";
// DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
//
// {
//     xfacdate.setPromptText(pattern.toLowerCase());
// }
//
// @Override public String toString(LocalDate date) {
//     if (date != null) {
//         return dateFormatter.format(date);
//     } else {
//         return "";
//     }
// }
//
// @Override public LocalDate fromString(String string) {
//     if (string != null && !string.isEmpty()) {
//         return LocalDate.parse(string, dateFormatter);
//     } else {
//         return null;
//     }
// }
//});
            laundrydate.setText(content8.toString());
            xfacdate.setText(content9.toString());  
//            line.getEditor().setText(content13.toString());
//            //machineamount.setText(content14.toString());
//            //dailycapasity.setText(content15.toString());
            ////////////////////////////////////////////////////////
            
            excelcell.clear();
            
            if (po.getText().isEmpty()) {
                Notifications noti = Notifications.create();
                noti.title("Fatal Error!");
                noti.text("Sorry, Maybe this row doesn't contain any data\nTry another row number.");
                noti.position(Pos.CENTER);
                noti.showError();
                
            
            }
            
               ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" added a new order called "+content5.toString()+" in"+content4.toString()+" via using excel row by row.");
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
    
    
    refreshh.fire();
    
        
        
    }

    
    
    
    
    
      
    @FXML
    void browseaction(ActionEvent event) {

      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("New Excel Files", new String[]{"*.xlsx"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Old Excel Files", new String[]{"*.xls"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", new String[]{"*.*"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      this.excelpathtf.setText(dirpathe);
      
        
        
    }
    
    
     @FXML
    void writedataaction(ActionEvent event) throws ClassNotFoundException, IOException {

        //Write All In One Here
        
         // Path to your Excel file and SQLite database
        String excelFilePath = excelpathtf.getText();        // adjust path as needed
        
        
        String filePath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";;
        
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("DB=")) {
        valll = line.split("=", 2)[1];
        break;
        }} 
          
         Class.forName("org.sqlite.JDBC");
         
         String sqliteDbUrl = "jdbc:sqlite:"+valll;

        // SQL INSERT statement for the orders table
        String insertSQL = "INSERT INTO Orders (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, Incoming_Date, X_Fac_Date) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Connect to SQLite database
        try (Connection conn = DriverManager.getConnection(sqliteDbUrl)) {
            // Disable auto-commit for batch processing
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                // Open the Excel workbook
                FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
                try (XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
                    // Get the first sheet (assuming data is on the first sheet)
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    // Assume the first row is a header row; data starts from the second row
                    boolean isHeader = true;
                    for (Row row : sheet) {
                        if (isHeader) {
                            isHeader = false;
                            continue;  // skip header row
                        }

                        // Read each cell value as a String (adjust indices if needed):
                        // Column order: 0: po, 1: sapcode, 2: style, 3: customer, 4: washname,
                        // 5: poamount, 6: cuttingamount, 7: laundrydate, 8: xfacdate.
                        String po           = getCellValueAsString(row.getCell(0));
                        String sapcode      = getCellValueAsString(row.getCell(1));
                        String style        = getCellValueAsString(row.getCell(2));
                        String customer     = getCellValueAsString(row.getCell(3));
                        String washname     = getCellValueAsString(row.getCell(4));
                        String poamount     = getCellValueAsString(row.getCell(5));
                        String cuttingamount= getCellValueAsString(row.getCell(6));
                        String laundrydate  = getCellValueAsString(row.getCell(7));
                        String xfacdate     = getCellValueAsString(row.getCell(8));

                        // Set parameters in the PreparedStatement
                        pstmt.setString(1, po);
                        pstmt.setString(2, sapcode);
                        pstmt.setString(3, style);
                        pstmt.setString(4, customer);
                        pstmt.setString(5, washname);
                        pstmt.setString(6, poamount);
                        pstmt.setString(7, cuttingamount);
                        pstmt.setString(8, laundrydate);
                        pstmt.setString(9, xfacdate);

                        // Add to batch for better performance
                        pstmt.addBatch();
                    }

                    // Execute the batch of inserts
                    pstmt.executeBatch();
                    // Commit the transaction
                    conn.commit();
                    System.out.println("Excel data successfully written to the orders table.");
                    
                    Notifications noti = Notifications.create();
              noti.title("Successful");
              noti.text("Successful Writing");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showInformation();
              
              
              
               ////////////////////////Audit//////////////////////////
      
  
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" added many orders via using all in one (Excel).");
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
            
              
              
                    
                } catch (Exception e) {
                    // If an error occurs reading the workbook, roll back the transaction.
                    conn.rollback();
                    System.err.println("Error processing Excel file: " + e.getMessage());
                    
                     Notifications notiv = Notifications.create();
              notiv.title("Unsuccessful");
              notiv.text("Unsuccessful Writing");
              notiv.hideAfter(Duration.seconds(3));
              notiv.position(Pos.CENTER);
              notiv.showError();
                    
                    e.printStackTrace();
                }
            }
        } catch (SQLException sqle) {
           // System.err.println("Database error: " + sqle.getMessage());
            
                 Notifications notiv = Notifications.create();
              notiv.title("Unsuccessful");
              notiv.text("Database Error");
              notiv.hideAfter(Duration.seconds(3));
              notiv.position(Pos.CENTER);
              notiv.showError();
            
            sqle.printStackTrace();
        } catch (Exception ex) {
           // System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        
        refreshh.fire();
        
        
    }
    

   private static String getCellValueAsString(Cell cell) {
    if (cell == null) {
        return "";
    }
    switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue().trim();
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format(cell.getDateCellValue());
            } else {
                double value = cell.getNumericCellValue();
                if (value == Math.floor(value)) {
                    return String.valueOf((long) value);
                } else {
                    return String.valueOf(value);
                }
            }
        case Cell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case Cell.CELL_TYPE_FORMULA:
            try {
                return cell.getStringCellValue();
            } catch (Exception e) {
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception ex) {
                    return "";
                }
            }
        case Cell.CELL_TYPE_BLANK:
            return "";
        default:
            return "";
    }
}
    
    
    
    


    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        String filePath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";;
        List<String> lines;
        try {
        lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
        if (line.startsWith("DB=")) {
        String value = line.split("=", 2)[1];
        databaseUrl="jdbc:sqlite:"+value;
        break;
        }} 
        } catch (IOException ex) {
        
        }
        
        
        try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 18);
        } catch (FileNotFoundException ex) {
          
        }
       
      
        conn = db.java_db();
        selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;
        
        if (selsecc.equals("Data_Entry")) {
            
            //Enable
            seto.setDisable(false);
            messenger.setDisable(false);
            inbox.setDisable(false);
            archive.setDisable(false);
            logout.setDisable(false);
            
        }
        
        else if (selsecc.equals("Alpha_Planner")) {
            
            //Disable
            seto.setDisable(true);
            messenger.setDisable(true);
            inbox.setDisable(true);
            //archive.setDisable(true);
            logout.setDisable(true);
            
        }
        
        else {
        
            //Enable
            seto.setDisable(false);
            messenger.setDisable(false);
            inbox.setDisable(false);
            archive.setDisable(false);
            logout.setDisable(false);
        
        }
   
 
DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("yyyy-MM-dd");     
// Add a listener to the date picker's value property
laundrydate.valueProperty().addListener((obs, oldDate, newDate) -> {
    // Use Platform.runLater to update the text after the default update
    Platform.runLater(() -> {
        if (newDate != null) {
            // Force the text field to use your format
            laundrydate.setText(newDate.format(formatterr));
        } else {
            laundrydate.clear();
        }
    });
});



//
//// Listen when popup is created and shown
//laundrydate.showingProperty().addListener((obs, oldValue, newValue) -> {
//    if (newValue) { // now showing
//        // Find popup node
//        Node popupNode = laundrydate.lookup(".mfx-date-picker-popup");
//        if (popupNode != null && popupNode.getScene() != null) {
//            popupNode.getScene().getStylesheets().add(
//                getClass().getResource("MFXX.css").toExternalForm()
//            );
//        }
//    }
//});

// Add a listener to the date picker's value property
xfacdate.valueProperty().addListener((obs, oldDate, newDate) -> {
    // Use Platform.runLater to update the text after the default update
    Platform.runLater(() -> {
        if (newDate != null) {
            // Force the text field to use your format
            xfacdate.setText(newDate.format(formatterr));
        } else {
            xfacdate.clear();
        }
    });
});     

//
//// Listen when popup is created and shown
//xfacdate.showingProperty().addListener((obs, oldValue, newValue) -> {
//    if (newValue) { // now showing
//        // Find popup node
//        Node popupNode = xfacdate.lookup(".mfx-date-picker-popup");
//        if (popupNode != null && popupNode.getScene() != null) {
//            popupNode.getScene().getStylesheets().add(
//                getClass().getResource("MFXX.css").toExternalForm()
//            );
//        }
//    }
//});


if (selsecc.equals("Alpha_Planner")) {
    //startAutoRefresh3();
}

else { 
    startAutoRefresh3();
}

        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;
 
      // Add a listener to update TextFields when the selection changes
table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Assuming each row is an ObservableList<String> with fixed column order
        ObservableList<String> row = newSelection;

        // Fill in your TextFields safely (use null/size checks if needed)
        poo=getCell(row, 0);
        sapcodee=getCell(row, 1);
        stylee=getCell(row, 2);
        customerr=getCell(row, 3);
        washnamee=getCell(row, 4);
        poamountt=getCell(row, 5);
        cuttingamountt=getCell(row, 6);
        laundrydatee=getCell(row, 7);
        xfacdatee=getCell(row, 8);
        

    }
});

wait.setVisible(true);
   
Platform.runLater(() -> {
   
//ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
//table.getColumns().clear();
//
//// Load Orders from first DB
//try {
//    String sql = "SELECT * FROM Orders";
//    pst = conn.prepareStatement(sql);
//    rs = pst.executeQuery();
//
//    // Create columns dynamically
//    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//        final int j = i;
//        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
//        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
//        table.getColumns().add(col);
//    }
//
//    // Fetch data into rows
//    while (rs.next()) {
//        ObservableList<String> row = FXCollections.observableArrayList();
//        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//            row.add(rs.getString(i));
//        }
//        data.add(row);
//    }
//
//    // Close first DB
//    rs.close();
//    pst.close();
//
//} catch (Exception e) {
//    e.printStackTrace();
//}
//
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
//
//// Set data to table
//table.setItems(data);
//
//// Add TableFilter if needed
//new TableFilter<>(table);
       
    editt();
    
    wait.setVisible(false);
   
     });


    
    
    
    
    

        //editt();
    
    
    
    //tooltips 
    
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


        
    }
    
    
private void shutdownApp() {
        fileCheckTimer.cancel(); // Stop the timer
        // Run on JavaFX thread
        Platform.runLater(() -> {
            Platform.exit(); // Close JavaFX
             
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
    
    
}
