// WASHINGController.java
package alpha.planner;


import com.gluonhq.charm.glisten.animation.BounceOutLeftTransition;
import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import com.gluonhq.charm.glisten.animation.BounceOutRightTransition;
import com.gluonhq.charm.glisten.animation.PulseTransition;
import com.gluonhq.charm.glisten.animation.ShakeTransition;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

public class WAREHOUSE_2_Controller implements Initializable {

    @FXML private VBox sidePanel;
    @FXML private VBox shipmentList;
    @FXML private MFXToggleButton togglePanelBtn2;
    
    Timer fileCheckTimer;
    
    
    @FXML private VBox sidePanel1;
    @FXML private VBox shipmentList1;
    @FXML private SplitPane mainSplit1;
    
    
    @FXML
    private SplitPane mainSplit;
    private final Map<Integer, CheckBox> shipmentCheckboxes = new HashMap<>();
    private Timeline refreshTimeline,refreshTimeline2,refreshTimeline3;
    @FXML
    private Menu onlineusersmenu,ships,fill,hel;
    @FXML
    private MenuItem refresh,qitt,excc,shippo,setto;
    @FXML
    private MenuItem stocky,loogy;
    @FXML
    private TableView<ObservableList<String>> table;
    Connection conn = null ;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public static String selsecc,seluserr;
    public static String value1;
    public static String ppo,ssapcode,sstyle,ccustomer,wwashname,ppoamount,ccuttingamount,llaundrydate,xxfacdate,receivedd,minuslinee;
    ContextMenu contextMenu;
    public static String diro;
    
    @FXML
    private VBox sido;
    
    @FXML
    private ImageView messenger;

    @FXML
    private ImageView inbox;
    
    PulseTransition pt;
    ShakeTransition st; 
    
    @FXML
    private ImageView reffo;

    @FXML
    private ImageView zeroo;

    @FXML
    private ImageView stocko;

    @FXML
    private ImageView exportyo;

    @FXML
    private ImageView shipa;

    @FXML
    private ImageView settaa;
    
    @FXML
    private ImageView logiout;

    @FXML
    private ImageView exito;
    
      //////////////////////////////////////////////////////////////////////////////
    
    @FXML
    private ImageView avatar;
    
    @FXML
    private Label username;
    
    @FXML
    void tutorialsaction(ActionEvent event) {

        
        File ffd=new File (System.getProperty("user.home")+"\\"+"config.txt");
        ffd.delete();
        
        //Noti
        
        Notifications.create()
        .title("Successful")
        .text("البرنامج هيقفل حالا وافتحه تاني هيعرض الارشادات")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();
        
        // Example: wait 3 seconds then run code
PauseTransition pauset = new PauseTransition(Duration.seconds(3));
pauset.setOnFinished(eventy -> {
    
    
    try {
      
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
    
    if (refreshTimeline != null) {
        refreshTimeline.stop();
    }
    
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
    
        
    }catch (Exception md){}
    
    
});
pauset.play();
        
        
    }
    
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



private int currentStep = 0;
private List<TourStep> steps = new ArrayList<>();
private Pane overlayPane;
String CONFIG_FILE = System.getProperty("user.home")+"\\"+"config.txt";
@FXML private StackPane mainRoot;


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

private boolean isFirstLaunch() {
    File file = new File(CONFIG_FILE);
    return !file.exists();
}

private void saveFirstLaunchFlag() {
    try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
        writer.write("launched=true");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void showStep(StackPane root, Scene scene) {
    // ✅ لو خلصت الخطوات شيل الـ overlay
    if (currentStep >= steps.size()) {
        if (overlayPane != null) {
            fadeOutAndRemove(root, overlayPane);
            overlayPane = null;
        }
        return;
    }

    TourStep step = steps.get(currentStep);
    Node target = step.node;

    // شيل القديم
    if (overlayPane != null) {
        root.getChildren().remove(overlayPane);
    }

    overlayPane = new Pane();
    overlayPane.setPickOnBounds(false);

    // خلفية غامقة
    Rectangle background = new Rectangle();
    background.widthProperty().bind(scene.widthProperty());
    background.heightProperty().bind(scene.heightProperty());
    background.setFill(Color.rgb(0, 0, 0, 0.6));

    // مربع التحديد (Highlight)
    Rectangle highlight = new Rectangle();
    highlight.setArcWidth(20);
    highlight.setArcHeight(20);
    highlight.setFill(Color.TRANSPARENT);
    highlight.setStroke(Color.web("#03A9F4"));
    highlight.setStrokeWidth(3);
    highlight.setEffect(new DropShadow(20, Color.web("#03A9F4")));

    // كارت المعلومات
    Label info = new Label(step.message);
    info.setWrapText(true);
    info.setMaxWidth(300);
    info.setStyle(
        "-fx-background-color: linear-gradient(to bottom right, #ff7eb9, #ff758c, #ff3c7e);" + // تدرج خيالي
        "-fx-padding: 18 25 18 25;" + // padding أكبر للتأثير
        "-fx-font-size: 18px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: white;" + // لون النص أبيض
        "-fx-background-radius: 30 5 30 5;" + // حواف غير متماثلة لإحساس فني
        "-fx-border-radius: 30 5 30 5;" +
        "-fx-border-width: 2;" +
        "-fx-border-color: rgba(255,255,255,0.5);" + // حدود شبه شفافة
        "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.4), 15, 0.5, 0, 0)," +
                      " innershadow(gaussian, rgba(0,0,0,0.25), 6, 0.5, 0, 2);" // تداخل ظل خارجي وداخلي
);
    
//    info.setStyle(
//            "-fx-background-color: white; " +
//            "-fx-padding: 15; " +
//            "-fx-font-size: 16px; " +
//            "-fx-font-weight: bold; " +
//            "-fx-background-radius: 12; " +
//            "-fx-border-radius: 12; " +
//            "-fx-border-color: #E0E0E0;" +
//            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 8, 0, 0, 2);"
//    );

    javafx.scene.control.Button nextBtn = new javafx.scene.control.Button(
            currentStep == steps.size() - 1 ? "انهاء" : "التالي →"
    );
    nextBtn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 6 18;");
    nextBtn.setOnAction(e -> {
        currentStep++;
        showStep(root, scene);
    });

    javafx.scene.control.Button skipBtn = new javafx.scene.control.Button("تخطي");
    skipBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-underline: true;");
    skipBtn.setOnAction(e -> {
        fadeOutAndRemove(root, overlayPane);
        overlayPane = null;
    });

    VBox card = new VBox(10, info, nextBtn, skipBtn);
    card.setStyle("-fx-font-family:'Cairo SemiBold';");
    card.setAlignment(Pos.CENTER);

    overlayPane.getChildren().addAll(background, highlight, card);
    root.getChildren().add(overlayPane); // ضيف overlay فوق الكل

    // تحديث مكان highlight والكارت
    Runnable updateHighlight = () -> {
        Bounds bounds = target.localToScene(target.getBoundsInLocal());
        Point2D point = overlayPane.sceneToLocal(bounds.getMinX(), bounds.getMinY());

        highlight.setX(point.getX() - 10);
        highlight.setY(point.getY() - 10);
        highlight.setWidth(bounds.getWidth() + 20);
        highlight.setHeight(bounds.getHeight() + 20);

        card.setLayoutX(point.getX());
        card.setLayoutY(point.getY() + bounds.getHeight() + 20);
    };

    updateHighlight.run();

    scene.widthProperty().addListener((obs, o, n) -> updateHighlight.run());
    scene.heightProperty().addListener((obs, o, n) -> updateHighlight.run());
    target.layoutBoundsProperty().addListener((obs, o, n) -> updateHighlight.run());

    // حركة fade in
    FadeTransition fade = new FadeTransition(Duration.seconds(0.4), overlayPane);
    fade.setFromValue(0);
    fade.setToValue(1);
    fade.play();
}

// Smooth fade out and remove
private void fadeOutAndRemove(StackPane root, Pane pane) {
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), pane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setOnFinished(ev -> root.getChildren().remove(pane));
    fadeOut.play();
}

// كلاس يمثل خطوة من خطوات التور
private static class TourStep {
    Node node;
    String message;
    TourStep(Node node, String message) {
        this.node = node;
        this.message = message;
    }
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    
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
    
    
    
    //////////////////////////////////////////////////////////////////////////////
    
    
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
    
    
    
     
     ////////////////////////////////////////////////////////////////////////////////////////////////
     
    @FXML
    void imgexitaction(MouseEvent event) {
        
        qitt.fire();

    }

    @FXML
    void imgexpaction(MouseEvent event) {
        
        excc.fire();

    }

    @FXML
    void imgrefraction(MouseEvent event) {
        
        refresh.fire();

    }

    @FXML
    void imgsetaction(MouseEvent event) {
        
        setto.fire();
        
    }

    @FXML
    void imgshipaction(MouseEvent event) {
        
        shippo.fire();

    }

    @FXML
    void imgstkaction(MouseEvent event) {
        
        stocky.fire();

    }

    @FXML
    void imglogaction(MouseEvent event) {
        
        loogy.fire();

    }
     
     ////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
      
       @FXML
    void autodelete(MouseEvent event) {
        
        
 /////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   try {
    // Step 1: Delete from washing where received = 0
    String sqlDeleteWashing = "DELETE FROM "+selsecc+" WHERE Minus_Q = 0";
    this.pst = this.conn.prepareStatement(sqlDeleteWashing);
    this.pst.executeUpdate();
    this.pst.close();
    

    // Step 3: Audit log entry
    String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?,?,?,?)";
    this.pst = this.conn.prepareStatement(sqla);
    this.pst.setString(1, value1); // date
    this.pst.setString(2, selsecc); // section
    this.pst.setString(3, seluserr); // user
    this.pst.setString(4, "Auto-deleted zero entries from " + selsecc + " ");
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

    
    
    
    
    @FXML
    void refreshaction(ActionEvent event) {
        
        
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
    void stockyaction(ActionEvent event) {
        
        
        
    try {
    String sqlll = "SELECT SUM(Received) AS TotalReceived FROM "+selsecc+"";
    pst = conn.prepareStatement(sqlll);
    rs = pst.executeQuery(); // ✅ Assign the result set

    int totalReceived = 0;
    if (rs.next()) {
        totalReceived = rs.getInt("TotalReceived");
    }

    Notifications.create()
        .title("Successful Query")
        .text("Stock in "+selsecc+" is:   " + totalReceived + "   PCS.")
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
    void shipmentpathaction2(ActionEvent event) throws IOException {
        
        
        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Finalize_a_shipment.fxml"));
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
    stg.setTitle("Finalize a shipment - أنهي الشحنة");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.setAlwaysOnTop(true);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
     
        
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
    stg.show();
    
        
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
    
    if (refreshTimeline != null) {
        refreshTimeline.stop();
    }
    
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

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("About.fxml"));
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
    stg.setTitle("About");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.setScene(sce);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setAlwaysOnTop(true);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
   
    }
    
    
     @FXML
    void toggleShipmentPanel2(MouseEvent event) {

        if (togglePanelBtn2.isSelected()==true) {
            mainSplit.setVisible(true);
        }
        else {
            mainSplit.setVisible(false);
        }
        
    }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WASHINGController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        conn = db.java_db();
        // Start refreshing every 5,3 seconds.
        startAutoRefresh();
        startAutoRefresh2();
        startAutoRefresh3();
        // Initial fetch after 5 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> fetchAndDisplayShipments());
        delay.play();
        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;
    
    selsecc=LogIn_GUI_Controller.selectedpositionn;
    seluserr=LogIn_GUI_Controller.selecteduser;
    
    
     // Add a listener to update TextFields when the selection changes
table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Assuming each row is an ObservableList<String> with fixed column order
        ObservableList<String> row = newSelection;

        // Fill in your TextFields safely (use null/size checks if needed)
        ppo=getCell(row, 0);
        ssapcode=getCell(row, 1);
        sstyle=getCell(row, 2);
        ccustomer=getCell(row, 3);
        wwashname=getCell(row, 4);
        ppoamount=getCell(row, 5);
        ccuttingamount=getCell(row, 6);
        llaundrydate=getCell(row, 7);
        xxfacdate=getCell(row, 8);
        

    }
});
      


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
          

//////////////////////////////////////////////////////////////////////////////////////////
          
contextMenu = new ContextMenu();
MenuItem viewItem = new MenuItem("Manual Shipment Path - مسار الشحنة يدويا");
MenuItem deleteItem = new MenuItem("Shipment Path -مسار الشحنة ");
contextMenu.getItems().addAll( deleteItem);

// Simple boolean flag
boolean[] contextMenuEnabled = {true};

viewItem.setOnAction(e -> {
    //Manual
    
     if (wwashname.isEmpty()==true) {
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful Operation.\nPlease choose an order first.");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
         }
        
         else {
             
             
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

             
         }
    
});

deleteItem.setOnAction(e -> {
    //Recipe
    
    try {   
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Finalize_a_shipment.fxml"));
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
    stg.setTitle("Finalize a shipment - أنهي الشحنة");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.setAlwaysOnTop(true);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
    }
    catch (Exception mjf) {}

});


// Attach context menu to rows only (not empty space)
table.setRowFactory(tv -> {
    TableRow<ObservableList<String>> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
            table.getSelectionModel().select(row.getItem());
            contextMenu.show(row, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
    });
    return row;
});
          
          
//////////////////////////////////////////////////////////////////////////////////////////
//
//fill.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("file.png"))));
//hel.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("helpdesk.png"))));
//ships.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("purchase-order.png"))));
//onlineusersmenu.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("read.png"))));
//


  //////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
//  if (themooo.equals("cupertino-dark.css")) {
//      //Dark
//      sido.setStyle("-fx-background-color:#15616d");
//  }
//  else {
//      //Light
//      sido.setStyle("-fx-background-color:#0fa3b1");
//  }

if (themooo.equals("cupertino-dark.css")) {
      //Dark
      sido.setStyle("-fx-background-color: linear-gradient(to bottom, #222532, #2f333f);");
  }
  else {
      //Light
      sido.setStyle("-fx-background-color:white;");
  }

  }
  catch (Exception re) {}
  //////////////////////////////////////////////////////////////////////////////////////////////////////  
  
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



////////////////////////////////////////////////////////////////////////////////////
//Reading Avatar 
// Avatar image
avatar.setFitWidth(50);
avatar.setFitHeight(50);
avatar.setPreserveRatio(false); // force square scaling
// Circular clip → perfectly round avatar
Circle clip = new Circle(25, 25, 25); // center (width/2, height/2), radius
avatar.setClip(clip);
// Gradient border (Instagram-like)
Circle border = new Circle(25, 25, 27); // a little bigger than avatar radius
border.setStyle(
    "-fx-fill: transparent;" +
    "-fx-stroke: linear-gradient(to right, #feda75, #fa7e1e, #d62976, #962fbf, #4f5bd5);" +
    "-fx-stroke-width: 3;"
);

try {
String sql = "SELECT Avatar FROM USERS WHERE User = ? and Position = ?";
pst = conn.prepareStatement(sql);
pst.setString(1, seluserr);
pst.setString(2, selsecc);
ResultSet rs = pst.executeQuery();
if (rs.next()) {
    InputStream is = rs.getBinaryStream("Avatar");
    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home")+"\\userpic.jpg");
    byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = is.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
    }
    fos.close();
    is.close();

    //System.out.println("Image restored to check.png");
    avatar.setImage(new Image((new File(System.getProperty("user.home")+"\\userpic.jpg")).toURI().toString()));
    username.setText(seluserr);
}

        } catch (Exception e) {
            e.printStackTrace();
            //avatar.setImage(new Image(getClass().getResourceAsStream("user.png")));
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
                
            } catch (Exception ignored) {}
        }

////////////////////////////////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

try {
        if (isFirstLaunch()) {
            steps.add(new TourStep(avatar, "هنا هتظهر صورتك الشخصية وممكن تكلم احمد القاضي يغيرهالك"));
            steps.add(new TourStep(username, "دا اسمك اللي اتعمل بيه اسم الاكونت بتاعك وبرضو ممكن القاضي يغيرهولك"));
            steps.add(new TourStep(reffo, "دوس هنا علشان تحدث بيانات الجدول من الداتا بيز"));
            steps.add(new TourStep(zeroo, "من خلال الايقونة دي هتقدر تمسح كل الاوردرات اللي كميتها وصلت صفر عندك دفعة واحدة"));
            steps.add(new TourStep(stocko, "اعرض كمية الاستوك اللي عندك كله مرة واحدة ولو عاوز تفاصيل شوفها من خلال الجدول او اسحب الداتا اكسيل وشوف"));
            //steps.add(new TourStep(expotyo, "من خلال الايقونة دي هتقدر تسحب الداتا مرة واحدة اللي قدامك في الجدول علي شيت اكسيل"));
            steps.add(new TourStep(shipa, "دا اختصار علشان تبعت شحنة خلصت عندك لقسم تاني"));
            //steps.add(new TourStep(traco, "ودا اختصار علشان تشوف مين وافق ومين موافقش وشحناتك المتعلقة لحين الموافقة او الرفض"));
            //steps.add(new TourStep(prodo, "سجل انتاجك هنا اول باول علشان تشوف الستوك بتاعك في اخر تحديث"));
            steps.add(new TourStep(settaa, "دي الاعدادات ويفضل متلعبش فيها بنفسك ولو احتاجت حاجة كلم القاضي"));
            steps.add(new TourStep(inbox, "هنا هتلاقي الرسايل اللي وصلتك طول الجلسة"));
            steps.add(new TourStep(messenger, "ابعت رسالة من هنا لاي حد في اي قسم "));
            steps.add(new TourStep(togglePanelBtn2, "اعرض او اخفي شحناتك اللي اتبعتتلك وهتلاقي فيها اوبشن للقبول واوبشن للرفض وصلي علي النبي في الاخر محمد صلي الله عليه وسلم"));
            //steps.add(new TourStep(logiout, "سجل خروجك من البرنامج والجلسة واقفل او روح لمستخدم تاني"));
            //steps.add(new TourStep(exito, "اقفل البرنامج وصلي علي النبي في الاخر محمد صلي الله عليه وسلم"));
            
            
            

            saveFirstLaunchFlag();

            // ✅ استنى بعد ما كل حاجة تتحمل
            Platform.runLater(() -> {
                Scene scene = mainRoot.getScene();
                if (scene != null) {
                    showStep(mainRoot, scene); // استخدم الـ StackPane الأساسي
                }
            });
        }
    } catch (Exception ee) {
        ee.printStackTrace();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        
    }
    
        
private void shutdownApp() {
        fileCheckTimer.cancel(); // Stop the timer
        // Run on JavaFX thread
        Platform.runLater(() -> {
            Platform.exit(); // Close JavaFX
             
    if (refreshTimeline != null) {
        refreshTimeline.stop();
    }
    
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
   
    private void startAutoRefresh() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            Platform.runLater(this::fetchAndDisplayShipments);
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
    
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
    
    private MenuItem createUserWithGreenDot(String username, String position) {
    Circle statusDot = new Circle(5);
    statusDot.setFill(Color.LIMEGREEN); // لون النقطة (متصل)

    Text nameText = new Text(username + "  (" + position + ")");
    HBox hbox = new HBox(6, statusDot, nameText);
    MenuItem item = new MenuItem();
    item.setGraphic(hbox);
    return item;
}
    @FXML
    private void acceptSelectedShipments() {
        processSelectedShipments(true);
    }

    @FXML
    private void refuseSelectedShipments() {
        processSelectedShipments(false);
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


    
    

    private void fetchAndDisplayShipments() {
    mainSplit.setDisable(false);    
    try (PreparedStatement pst = conn.prepareStatement(
            "SELECT * FROM Shipment_Path WHERE Delivered = 0 AND `To` = '" + selsecc + "'")) {

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("ID");
            if (shipmentCheckboxes.containsKey(id)) continue;

            String po = rs.getString("PO");
            String sap = rs.getString("Sap_Code");
            String style = rs.getString("Style");
            int qToSend = rs.getInt("Q_To_Send");
            String customer = rs.getString("Customer");
            String washName = rs.getString("Wash_Name");
            String fromy = rs.getString("From");
            String usery = rs.getString("User");
            
            
            
            
               // === Header Info (صغير ولونه رمادي) ===
JFXCheckBox header = new JFXCheckBox("لقد استلمت شحنة جديدة بياناتها كالتالي");
header.setWrapText(true);
header.setMaxWidth(400);
header.setStyle(
    "-fx-font-size: 14px;" +
    "-fx-text-fill: darkgrey;" +
    "-fx-font-weight: bold;" +         
    "-fx-font-family: 'Cairo SemiBold';" 
);

// === Message Body (Bubble) ===
Label message = new Label("PO: " + po + "\nSAP: " + sap + "\nStyle: " + style +
                       "\nQuantity: " + qToSend + "\nCustomer: " + customer + "\nWash Name: " + washName);
message.setWrapText(true);
message.setMaxWidth(500);  // عرض البالونة

try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        message.setStyle(
//            "-fx-font-size: 16px;" +
//            "-fx-font-family: 'Arial';" +
//            "-fx-text-fill: white;" +
//            "-fx-font-weight: bold;" +        
//            "-fx-background-color: #0A84FF;" +   // أزرق زي iMessage
//            "-fx-background-radius: 15;" +
//            "-fx-padding: 8 12 8 12;"
                     "-fx-font-weight: bold;" +
    "-fx-font-size: 15px;" +
    "-fx-effect: dropshadow(gaussian, #c3c3c3, 10, 0.6, 21, 21);"
        );
    } else {
        message.setStyle(
//            "-fx-font-size: 15px;" +
//            "-fx-font-family: 'Arial';" +
//            "-fx-text-fill: black;" +
//            "-fx-font-weight: bold;" +        
//            "-fx-background-color: #E5E5EA;" +  // رمادي فاتح زي iOS
//            "-fx-background-radius: 15;" +
//            "-fx-padding: 8 12 8 12;"
                     "-fx-font-weight: bold;" +
    "-fx-font-size: 15px;" +
    "-fx-effect: dropshadow(gaussian, #c3c3c3, 10, 0.6, 21, 21);"
        );
    }
} catch (Exception re) {
    message.setStyle(
//        "-fx-font-size: 15px;" +
//        "-fx-font-family: 'Arial';" +
//        "-fx-text-fill: black;" +
//        "-fx-font-weight: bold;" +        
//        "-fx-background-color: #E5E5EA;" +
//        "-fx-background-radius: 15;" +
//        "-fx-padding: 8 12 8 12;"
                 "-fx-font-weight: bold;" +
    "-fx-font-size: 15px;" +
    "-fx-effect: dropshadow(gaussian, #c3c3c3, 10, 0.6, 21, 21);"
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


//            JFXCheckBox cb = new JFXCheckBox();
//            cb.setText("PO: " + po + "\nSAP: " + sap + "\nStyle: " + style +
//                       "\nQuantity: " + qToSend + "\nCustomer: " + customer + "\nWash Name: " + washName);
//            cb.setWrapText(true);
//            //////////////////////////////////////////////////////////////////////////////////////////////////////
//  try {  
//  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//  String themooo=bis.readLine();
//  bis.close();
//  if (themooo.equals("cupertino-dark.css")) {
//      //Dark
//      cb.setStyle(
//    "-fx-font-size: 14px;" +
//    "-fx-font-weight: bold;" +
//    "-fx-font-family: 'Arial';" +
//    "-fx-text-fill: white;" // Ensure text is readable in dark mode
//);
//  }
//  else {
//      //Light
//      cb.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
//  }
//  }
//  catch (Exception re) {}
//  //////////////////////////////////////////////////////////////////////////////////////////////////////  
//            
//
//            VBox card = new VBox(cb);
//            card.setSpacing(10);
//            card.setPadding(new Insets(10));
//            //////////////////////////////////////////////////////////////////////////////////////////////////////
//  try {  
//  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//  String themooo=bis.readLine();
//  bis.close();
//  if (themooo.equals("cupertino-dark.css")) {
//      //Dark
//      card.setStyle(
//    "-fx-background-color: #1C1C1E;" + // Dark gray background for cards
//    "-fx-background-radius: 12;" +
//    "-fx-border-radius: 12;" +
//    "-fx-border-color: rgba(255,255,255,0.08);" + // Subtle light border
//    "-fx-border-width: 1;" +
//    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);" +
//    "-fx-padding: 10;"
//);
//  }
//  else {
//      //Light
//      card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
//  }
//  }
//  catch (Exception re) {}
//  //////////////////////////////////////////////////////////////////////////////////////////////////////  
//            card.setAlignment(Pos.CENTER_LEFT);
//
//            
            
            
            shipmentList.getChildren().add(card);
            shipmentCheckboxes.put(id, header);
            mainSplit.setVisible(true);
            togglePanelBtn2.setSelected(true);
            
            pt=new PulseTransition (togglePanelBtn2);
            pt.setAutoReverse(true);
            pt.setCycleCount(Timeline.INDEFINITE);
            pt.play();
            
            st=new ShakeTransition (togglePanelBtn2);
            st.setAutoReverse(true);
            st.setCycleCount(Timeline.INDEFINITE);
            st.play();

            String allll = "لقد تم استلام شحنة جديدة من خلال المستخدم " + usery + " الموجود بقسم " + fromy +
                           "\nوبياناتها كالتالي\n\n" +
                           "PO: " + po + "\nSAP CODE: " + sap + "\nStyle: " + style +
                           "\nQuantity: " + qToSend + "\nCustomer: " + customer + "\nWash Name: " + washName;

            Popup notificationPopup = new Popup();
            VBox mainContainer = new VBox();
            mainContainer.setPadding(new Insets(10));
            mainContainer.setSpacing(10);
            mainContainer.setStyle(
                "-fx-background-color: #f9f9f9;" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #dddddd;" +
                "-fx-border-radius: 16;" +
                "-fx-border-width: 1;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0.2, 0, 4);"
            );

            HBox topBar = new HBox();
            topBar.setAlignment(Pos.TOP_RIGHT);
            javafx.scene.control.Button closeButton = new javafx.scene.control.Button("\u2716");
            closeButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
            );
            closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: lightgray;" +
                "-fx-text-fill: darkred;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
            ));
            closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
            ));
            closeButton.setOnAction(e -> notificationPopup.hide());
            topBar.getChildren().add(closeButton);

            HBox notificationBox = new HBox(15);
            notificationBox.setPadding(new Insets(10));
            notificationBox.setAlignment(Pos.CENTER_LEFT);

            Label notificationLabel = new Label();
            notificationLabel.setWrapText(true);
            notificationLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            notificationLabel.setStyle(
                "-fx-font-family: 'Cairo SemiBold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18px;" +
                "-fx-text-fill: #222222;" +
                "-fx-max-width: 600;" +
                "-fx-alignment: center-right;"
            );
            notificationLabel.setTextAlignment(TextAlignment.RIGHT);
            notificationLabel.setText(allll);
            notificationBox.getChildren().add(notificationLabel);

            mainContainer.getChildren().addAll(topBar, notificationBox);
            notificationPopup.getContent().add(mainContainer);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double screenWidth = bounds.getWidth();
            double bottomY = bounds.getHeight() - 120;

            mainContainer.applyCss();
            mainContainer.layout();
            double popupWidth = mainContainer.getWidth();
            double popupX = (screenWidth - popupWidth) / 2;

            Stage stage = (Stage) table.getScene().getWindow();
            notificationPopup.show(stage);
            notificationPopup.setX(popupX);
            notificationPopup.setY(bottomY);

            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\noti.wav").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    
    
    
    
   
    private void processSelectedShipments(boolean accept) {
    List<Node> cardsToRemove = new ArrayList<>();

    Iterator<Map.Entry<Integer, CheckBox>> iterator = shipmentCheckboxes.entrySet().iterator();

    while (iterator.hasNext()) {
        Map.Entry<Integer, CheckBox> entry = iterator.next();
        int id = entry.getKey();
        CheckBox cb = entry.getValue();

        if (cb.isSelected()) {
            try {
                
                
                
         
                if (accept) {
    // Step 0: Get Shipment Data first to validate
    PreparedStatement ps = conn.prepareStatement(
        "SELECT PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
        "Incoming_Date, X_Fac_Date, Q_To_Send, \"From\" FROM Shipment_Path WHERE ID = ?");
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        String po = rs.getString("PO");
        String sap = rs.getString("Sap_Code");
        String style = rs.getString("Style");
        String customer = rs.getString("Customer");
        String washName = rs.getString("Wash_Name");
        String poAmount = rs.getString("PO_Amount");
        int cuttingAmount = rs.getInt("Cutting_Amount");
        String laundryDate = rs.getString("Incoming_Date");
        String xfacDate = rs.getString("X_Fac_Date");
        int qToSend = rs.getInt("Q_To_Send");
        String fromSection = rs.getString("From");

        // Step 1: Check if adding Q_To_Send will exceed Cutting_Amount
        PreparedStatement checkReceivedStmt = conn.prepareStatement(
            "SELECT IFNULL(SUM(CAST(Received AS INTEGER)),0) FROM " + selsecc +
            " WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        checkReceivedStmt.setString(1, po);
        checkReceivedStmt.setString(2, sap);
        checkReceivedStmt.setString(3, style);
        ResultSet recRs = checkReceivedStmt.executeQuery();

        int currentReceived = 0;
        if (recRs.next()) {
            currentReceived = recRs.getInt(1);
        }
        recRs.close();
        checkReceivedStmt.close();

        if (currentReceived + qToSend > cuttingAmount) {
            // Show alert and stop
            Notifications.create()
                .title("تنبيه")
                .text("الكمية المطلوبة (" + qToSend + ") ستتجاوز الكمية المقطوعة (" + cuttingAmount + ")! العملية مرفوضة.")
                .hideAfter(Duration.seconds(4))
                .position(Pos.CENTER)
                .showWarning();
            rs.close();
            ps.close();
            return; // stop here
        }

        // Step 2: Mark Delivered
        PreparedStatement pst = conn.prepareStatement(
            "UPDATE Shipment_Path SET Delivered = 1 WHERE ID = ?");
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();

        
        
        // Step 3: Subtract from From section
        PreparedStatement subtractStmt = conn.prepareStatement(
            "UPDATE " + fromSection + " SET Received = Received - ? " +
            "WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        subtractStmt.setInt(1, qToSend);
        subtractStmt.setString(2, po);
        subtractStmt.setString(3, sap);
        subtractStmt.setString(4, style);
        subtractStmt.executeUpdate();
        subtractStmt.close();
        
        
        
               
if (fromSection.equalsIgnoreCase("WareHouse_1")) {
    
    
    
}

else {
    
// Always subtract from Finished in <fromSection>_Production
PreparedStatement subtractFinishedStmt = conn.prepareStatement(
    "UPDATE " + fromSection + "_Production " +
    "SET Finished = CASE WHEN Finished - ? < 0 THEN 0 ELSE Finished - ? END " +
    "WHERE PO = ? AND Sap_Code = ? AND Style = ? AND Customer = ? AND Wash_Name = ?"
);
subtractFinishedStmt.setInt(1, qToSend);
subtractFinishedStmt.setInt(2, qToSend);
subtractFinishedStmt.setString(3, po);
subtractFinishedStmt.setString(4, sap);
subtractFinishedStmt.setString(5, style);
subtractFinishedStmt.setString(6, customer);
subtractFinishedStmt.setString(7, washName);
subtractFinishedStmt.executeUpdate();
subtractFinishedStmt.close();

    
}
        
        

        // Step 4: Check if record exists in target section
        PreparedStatement checkStmt = conn.prepareStatement(
            "SELECT COUNT(*) FROM " + selsecc + " WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        checkStmt.setString(1, po);
        checkStmt.setString(2, sap);
        checkStmt.setString(3, style);
        ResultSet checkRs = checkStmt.executeQuery();

        if (checkRs.next() && checkRs.getInt(1) > 0) {
            // ✅ Update existing record
            PreparedStatement updateStmt = conn.prepareStatement(
    "UPDATE " + selsecc + " " +
    "SET Received = CAST(Received AS INTEGER) + ?, " +
    "    Minus_Q = CAST(Minus_Q AS INTEGER) + ? " +
    "WHERE PO = ? AND Sap_Code = ? AND Style = ?");

updateStmt.setInt(1, qToSend);  // for Received
updateStmt.setInt(2, qToSend);  // for Minus_Q
updateStmt.setString(3, po);
updateStmt.setString(4, sap);
updateStmt.setString(5, style);

            updateStmt.executeUpdate();
            updateStmt.close();
            
            
              
            
            //Get data here and send
            
            
            PreparedStatement getdata = conn.prepareStatement("select Received, Minus_Lines from WareHouse_1 where PO='"+po+"' and Sap_Code='"+sap+"' and Style='"+style+"' and Customer='"+customer+"' and Wash_Name='"+washName+"'");
            ResultSet rsu = getdata.executeQuery();
            if (rsu.next()) {
            String rero = rsu.getString("Received");
            String mono = rsu.getString("Minus_Lines");
            
            System.out.println(rero);
            System.out.println(mono);
            
            if (rero.equals("0")&&mono.equals("0")) {
                
                //Send Msg To Data Entry Here.
                
                ////////////////////////////////////////////////////////////////////////////////
                
                   // Send to single user
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(d);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.ofHours(3));
            String timeString = dtf.format(now);

            String insertSql = "INSERT INTO Message (Date, Time, Sec_From, M_From, M_To, Message, Delivered) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(insertSql);

            pst.setString(1, dateString);
            pst.setString(2, timeString);
            pst.setString(3, selsecc);
            pst.setString(4, seluserr);
            pst.setString(5, "Data_Entry");
            pst.setString(6, "الاوردر اللي بياناته تحت وصل منه كذا دفعة كده لمخزن التسليم"
                    + "\nوكمان المخزن اللي استلم بعت كل الكميات لبقية الاقسام وكمان خد دي الخطوط بعتت كل الكميات وبكده الاوردر كميته خلصت"
                    + "\nدلوقتي عندك فرصه تمسحه وتودية الارشيف او تمسحه للابد من الاوردرات"
                    + "\nدي الداتا بتاعت الاوردر اللي بكلمك عنه"
                    + "\n"
            + "\nPO: '"+po+"'"
            + "\nSap Code: '"+sap+"'"
            + "\nStyle: '"+style+"'"
            + "\nCustomer: '"+customer+"'"
            + "\nWash Name: '"+washName+"'"
            );
            pst.setString(7, "0");
            pst.executeUpdate();
            pst.close();

            Notifications.create()
                .title("عملية ناجحة")
                .text("تم ارسال الرسالة للمستخدم المحدد")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showInformation();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ignore) {}
        }
                
                ////////////////////////////////////////////////////////////////////////////////
                
            }
            
            
            }
            getdata.close();
            rsu.close();
            
            
            
            
            
            
            
            
            
        } 
        
        else {
            // ✅ Insert new record
            PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO " + selsecc + " (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, " +
                "Cutting_Amount, Incoming_Date, X_Fac_Date, Received, Delivered, Minus_Q, Notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStmt.setString(1, po);
            insertStmt.setString(2, sap);
            insertStmt.setString(3, style);
            insertStmt.setString(4, customer);
            insertStmt.setString(5, washName);
            insertStmt.setString(6, poAmount);
            insertStmt.setString(7, String.valueOf(cuttingAmount));
            insertStmt.setString(8, laundryDate);
            insertStmt.setString(9, xfacDate);
            insertStmt.setString(10, String.valueOf(qToSend)); // Received
            insertStmt.setString(11, "Not_Updated_Yet");
            insertStmt.setString(12, String.valueOf(qToSend));
            insertStmt.setString(13, "Not_Updated_Yet");
            insertStmt.executeUpdate();
            insertStmt.close();
            
            
            //Get data here and send
            
            
            PreparedStatement getdata = conn.prepareStatement("select Received, Minus_Lines from WareHouse_1 where PO='"+po+"' and Sap_Code='"+sap+"' and Style='"+style+"' and Customer='"+customer+"' and Wash_Name='"+washName+"'");
            ResultSet rsu = getdata.executeQuery();
            if (rsu.next()) {
            String rero = rsu.getString("Received");
            String mono = rsu.getString("Minus_Lines");
            
            System.out.println(rero);
            System.out.println(mono);
            
            if (rero.equals("0")&&mono.equals("0")) {
                
                //Send Msg To Data Entry Here.
                
                ////////////////////////////////////////////////////////////////////////////////
                
                   // Send to single user
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(d);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.ofHours(3));
            String timeString = dtf.format(now);

            String insertSql = "INSERT INTO Message (Date, Time, Sec_From, M_From, M_To, Message, Delivered) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(insertSql);

            pst.setString(1, dateString);
            pst.setString(2, timeString);
            pst.setString(3, selsecc);
            pst.setString(4, seluserr);
            pst.setString(5, "Data_Entry");
            pst.setString(6, "الاوردر اللي بياناته تحت وصل منه اول دفعة لمخزن التسليم"
                    + "\nوكمان المخزن اللي استلم بعت كل الكميات لبقية الاقسام وكمان خد دي الخطوط بعتت كل الكميات وبكده الاوردر كميته خلصت"
                    + "\nدلوقتي عندك فرصه تمسحه وتودية الارشيف او تمسحه للابد من الاوردرات"
                    + "\nدي الداتا بتاعت الاوردر اللي بكلمك عنه"
                    + "\n"
            + "\nPO: '"+po+"'"
            + "\nSap Code: '"+sap+"'"
            + "\nStyle: '"+style+"'"
            + "\nCustomer: '"+customer+"'"
            + "\nWash Name: '"+washName+"'"
            );
            pst.setString(7, "0");
            pst.executeUpdate();
            pst.close();

            Notifications.create()
                .title("عملية ناجحة")
                .text("تم ارسال الرسالة للمستخدم المحدد")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showInformation();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ignore) {}
        }
                
                ////////////////////////////////////////////////////////////////////////////////
                
            }
            
            
            }
            getdata.close();
            rsu.close();
            
            
            
            
            
        }

        checkStmt.close();
        checkRs.close();

        // Step 5: Audit
        try {
            String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?, ?, ?, ?)";
            this.pst = this.conn.prepareStatement(sqla);
            this.pst.setString(1, value1);
            this.pst.setString(2, selsecc);
            this.pst.setString(3, seluserr);
            this.pst.setString(4, seluserr + " Accepted shipment, Quantity: " + qToSend +
                ", info and its data is: PO: " + po +
                ", Sap Code: " + sap +
                ", Style: " + style +
                ", Customer: " + customer +
                ", Wash Name: " + washName +
                ", PO Amount: " + poAmount +
                ", Cutting Amount: " + cuttingAmount +
                ", From: " + fromSection +
                ", To: " + selsecc + ".");
            this.pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ignored) {}
        }

        // Step 6: Notification
        Notifications.create()
            .title("تم بنجاح")
            .text("تم قبول الشحنة ونقل الكمية إلى " + selsecc)
            .hideAfter(Duration.seconds(3))
            .position(Pos.CENTER)
            .showInformation();
    }

    rs.close();
    ps.close();
}

                
                
                
                   else {
    // Step 1: Mark as Refused
    PreparedStatement pst = conn.prepareStatement(
        "UPDATE Shipment_Path SET Delivered = 'Refused' WHERE ID = ?");
    pst.setInt(1, id);
    pst.executeUpdate();
    pst.close();

    // Step 2: Get Shipment Data
    PreparedStatement ps = conn.prepareStatement(
        "SELECT PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
        "Incoming_Date, X_Fac_Date, Q_To_Send, \"From\" FROM Shipment_Path WHERE ID = ?");
    ps.setInt(1, id);
    ResultSet rss = ps.executeQuery();

    if (rss.next()) {
        String po = rss.getString("PO");
        String sap = rss.getString("Sap_Code");
        String style = rss.getString("Style");
        String customer = rss.getString("Customer");
        String washName = rss.getString("Wash_Name");
        String poAmount = rss.getString("PO_Amount");
        String cuttingAmount = rss.getString("Cutting_Amount");
        int qToSend = rss.getInt("Q_To_Send");
        String fromSection = rss.getString("From");

        // Step 3: Audit with full details
        try {
            String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?, ?, ?, ?)";
            this.pst = this.conn.prepareStatement(sqla);
            this.pst.setString(1, value1);
            this.pst.setString(2, selsecc);
            this.pst.setString(3, seluserr);
            this.pst.setString(4, seluserr + " Refused a shipment, Quantity: " + qToSend +
                ", info and its data is: PO: " + po +
                ", Sap Code: " + sap +
                ", Style: " + style +
                ", Customer: " + customer +
                ", Wash Name: " + washName +
                ", PO Amount: " + poAmount +
                ", Cutting Amount: " + cuttingAmount +
                ", From: " + fromSection +
                ", To: " + selsecc + ".");
            this.pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ignored) {}
        }
    }

    rs.close();
    ps.close();

    // Step 4: Show Notification
    Notifications.create()
        .title("تم الرفض")
        .text("تم رفض الشحنة بنجاح.")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();

                    
                       /////////////////Alert to note////////////////

               
            // Create a text field for user notes
JFXTextField noteField = new JFXTextField("");
noteField.setLabelFloat(true);
noteField.setPromptText("اضف ملاحظات");
noteField.setStyle("-fx-font-weight:bold;-fx-font-size:12;");

// Create alert dialog
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("اوردر مرفوض");
alert.setHeaderText("لقد تم رفض الاوردر الحالي");
alert.setContentText("عاوز تضيفه لقائمة المرفوض ولا لا؟");
alert.setGraphic(noteField);

// Add buttons
ButtonType addButton = new ButtonType("اضافة");
ButtonType cancelButton = new ButtonType("الغاء");
alert.getButtonTypes().setAll(addButton, cancelButton);

// Load custom style
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

// Show dialog and wait
Optional<ButtonType> result = alert.showAndWait();

// Determine the note content
String noteText = "";
if (result.isPresent() && result.get() == addButton) {
    noteText = noteField.getText().trim();
    if (noteText.isEmpty()) {
        noteText = "تم الرفض بدون كتابة ملاحظة";
    }
} else if (result.isPresent() && result.get() == cancelButton) {
    noteText = "الشحنة اترفضت لاسباب غير معروفة";
} else {
    // Dialog was closed or canceled
    return;
}

// Fetch the refused shipment data
try (PreparedStatement getDataStmt = conn.prepareStatement(
    "SELECT PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
    "Incoming_Date, X_Fac_Date, Q_To_Send, \"From\", \"To\", Unique_ID " +
    "FROM Shipment_Path WHERE ID = ?")) {

    getDataStmt.setInt(1, id);

    try (ResultSet rs = getDataStmt.executeQuery()) {
        if (rs.next()) {
            String po = rs.getString("PO");
            String sap = rs.getString("Sap_Code");
            String style = rs.getString("Style");
            String customer = rs.getString("Customer");
            String washName = rs.getString("Wash_Name");
            String poAmount = rs.getString("PO_Amount");
            String cuttingAmount = rs.getString("Cutting_Amount");
            String incomingDate = rs.getString("Incoming_Date");
            String xFacDate = rs.getString("X_Fac_Date");
            int qToSend = rs.getInt("Q_To_Send");
            String from = rs.getString("From");
            String to = rs.getString("To");
            String uniqueId = rs.getString("Unique_ID");

            // Get today's date
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // Insert into Refused_Shipments table
            try (PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO Refused_Shipments (" +
                "\"Date\", PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
                "Incoming_Date, X_Fac_Date, \"User\", \"From\", \"To\", Delivered, Unique_ID, Q_To_Send, Note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                insertStmt.setString(1, currentDate);      // Date
                insertStmt.setString(2, po);
                insertStmt.setString(3, sap);
                insertStmt.setString(4, style);
                insertStmt.setString(5, customer);
                insertStmt.setString(6, washName);
                insertStmt.setString(7, poAmount);
                insertStmt.setString(8, cuttingAmount);
                insertStmt.setString(9, incomingDate);
                insertStmt.setString(10, xFacDate);
                insertStmt.setString(11, seluserr);        // Current user
                insertStmt.setString(12, from);            // "From" field
                insertStmt.setString(13, to);              // "To" field
                insertStmt.setString(14, "Refused");       // Delivered = Refused
                insertStmt.setString(15, uniqueId);
                insertStmt.setInt(16, qToSend);
                insertStmt.setString(17, noteText);        // User note

                insertStmt.executeUpdate();
                System.out.println("✅ Refused shipment inserted.");
            }

        } else {
            System.err.println("❌ Shipment with ID = " + id + " not found.");
        }
    }

} catch (Exception e) {
    e.printStackTrace();
}
                    
                    
                    
                }

                // Find the parent card (like HBox/VBox)
                Node card = cb.getParent();
                if (card != null) {
                    
                    
                            
                            BounceOutRightTransition pt4=new BounceOutRightTransition (card);
                            pt4.setAutoReverse(false);
                            pt4.setCycleCount(1);
                            pt4.setOnFinished(event -> shipmentList.getChildren().remove(card));
                            pt4.play();
                            
                            
                            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\delete.wav").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
                    
                    cardsToRemove.add(card); // Mark for cleanup from map
                }

                cb.setSelected(false);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Remove checkboxes from map after animation
    for (Node card : cardsToRemove) {
        shipmentCheckboxes.entrySet().removeIf(entry -> entry.getValue().getParent() == card);
    }

    // Hide main panel if done
    if (shipmentCheckboxes.isEmpty()) {

    // Delay before animation
    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    
    pause.setOnFinished(event -> {
        BounceOutLeftTransition pt4 = new BounceOutLeftTransition(mainSplit);
        pt4.setAutoReverse(false);
        pt4.setCycleCount(1);
        pt4.setOnFinished(e -> {
            mainSplit.setVisible(false);
            togglePanelBtn2.setSelected(false);
            
            pt.stop();
            st.stop();
            
        });
        pt4.play();
        
                 
                            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\close.wav").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
        
        
    });

    pause.play();

    // Optional: disable interaction during delay
    mainSplit.setDisable(true);
}


    // Delete accepted/refused
    try {
        PreparedStatement deleteStmt = conn.prepareStatement(
            "DELETE FROM Shipment_Path WHERE Delivered = 1 OR Delivered = 'Refused'");
        deleteStmt.executeUpdate();
        deleteStmt.close();
        
   
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Refresh Table
    try {
        table.getColumns().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM " + selsecc);
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
