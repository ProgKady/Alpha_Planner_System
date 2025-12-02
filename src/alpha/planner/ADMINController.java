package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ADMINController<T extends Comparable<T>> implements Initializable {

    @FXML
    private Menu filemenu;

    @FXML
    private MenuItem audit;

    @FXML
    private MenuItem logout;
    
    @FXML
    private JFXButton activate;

    @FXML
    private MenuItem exit;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private MediaView mediaView;

    @FXML
    private MenuBar bar;

    private Timeline refreshTimeline2;

    @FXML
    private Menu onlineusersmenu;

    public static String selsecc, seluserr;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    public static String diro;
    
    @FXML private VBox sidePanel1;
    @FXML private VBox shipmentList1;
    @FXML private SplitPane mainSplit1;
    
     @FXML
    private MenuItem inbox;
     
     private Timeline refreshTimeline3;
    
    
      @FXML
void inboxaction(ActionEvent event) throws IOException {
    // toggle visibility
    mainSplit1.setVisible(!mainSplit1.isVisible());
}

 @FXML
    void enterraction(KeyEvent event) {
        
        KeyCode kc=event.getCode();
        if (kc==KeyCode.ENTER) {
            activate.fire();
        }

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
    
     private void startAutoRefresh3 () {
        refreshTimeline3 = new Timeline(new KeyFrame(Duration.seconds(3), e -> {//Messages
            Platform.runLater(this::fofofo);
        }));
        refreshTimeline3.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline3.play();
    }
     
     
    ///////////////////////////////////////////////////////////
    
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

            Stage stage = (Stage) bar.getScene().getWindow();
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
        
//      
//            if (inbox.getImage() == img1) {
//                inbox.setImage(img1);
//            } else {
//                inbox.setImage(img1);
//            }
       
            

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
    void viewlogsaction(ActionEvent event) throws IOException {

        
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

        File ff = new File(diro + "\\Java\\bin\\Alpha_Logs.kady");

        if (ff.exists()) {
            logspreview(diro + "\\Java\\bin\\Alpha_Logs.kady");
        }
        
        else {
            //Noti
        Notifications.create()
        .title("Fatal Error")
        .text("Logs File Not Found")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showError();
        }
        
        
    }
    
    
      @FXML
    void clearlogsaction(ActionEvent event) throws IOException {

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
        
        
        PrintWriter pw=new PrintWriter (new FileWriter (diro + "\\Java\\bin\\Alpha_Logs.kady"));
        pw.println();
        pw.close();
        
        Notifications.create()
        .title("Successful")
        .text("We Successfully cleared logs.")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();
        
    }
    
    public void logspreview(String pathss) throws IOException {
    Stage stage = new Stage();

    JFXTextArea logarea = new JFXTextArea();
    logarea.setLabelFloat(true);
    logarea.setPromptText("Logs Content");
    logarea.setStyle("-fx-font-weight:bold;-fx-font-size:12px;");

    try {
        InputStream inputinstream=new FileInputStream(pathss);
        BufferedReader buff=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
        String line;
        while ((line = buff.readLine()) != null) {
            logarea.appendText(line + "\n");
        }
    }
    catch (Exception f){}

    BorderPane bp = new BorderPane();
    bp.setCenter(logarea);

    Scene s = new Scene(bp, 450, 750);
    s.getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
    stage.setScene(s);

    stage.setTitle("View Logs");
    stage.setAlwaysOnTop(true);
    stage.centerOnScreen();
    stage.show();
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
    Stage jk = (Stage)mediaView.getScene().getWindow();
    //jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)mediaView.getScene().getWindow();
    //jk.setOpacity(0.4);
        
    }
    

    // Extracts a resource from JAR to a temp file
    private File extractResource(String resourceName) throws IOException {
        InputStream in = getClass().getResourceAsStream(resourceName);
        if (in == null) throw new IOException("Resource not found: " + resourceName);

        File tempFile = File.createTempFile("video", ".mp4");
        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    @FXML
    void fixdataaction(ActionEvent event) throws IOException {
        FuzzyReplaceApp gfd = new FuzzyReplaceApp();
        gfd.start(new Stage());
    }

    @FXML
    void trackashipmentaction(ActionEvent event) throws IOException {
        Stage kady = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Track_A_Ship.fxml"));
        Scene scene = new Scene(root);
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
        scene.getStylesheets().add(cssPath);
        root.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
        kady.setTitle("Track Shipment");
        kady.centerOnScreen();
        kady.setResizable(false);
        kady.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        kady.setScene(scene);
        kady.show();
    }

    
    @FXML
void activateaction(ActionEvent event) {
    String passy = pass.getText().trim();

    if (passy.equals("لا اله الا الله") ||
        passy.equals("gh hgi hgh hggi") ||
        passy.equals("GH HGI HGH HGGI")) {

        filemenu.setDisable(false);  // افتح القائمة
    } 
    else {
        filemenu.setDisable(true);   // اقفل القائمة
    }
}


    @FXML
    void auditaction(ActionEvent event) throws IOException {
        Stage kady = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Audit_Window.fxml"));
        Scene scene = new Scene(root);
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
        scene.getStylesheets().add(cssPath);
        root.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
        kady.setTitle("Audit Viewer");
        kady.centerOnScreen();
        kady.setResizable(true);
        kady.setMaximized(true);
        kady.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        kady.setScene(scene);
        kady.show();
    }

    @FXML
    void newuseraction(ActionEvent event) throws IOException {
        Stage kady = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CreateNewUser.fxml"));
        Scene scene = new Scene(root);
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
        scene.getStylesheets().add(cssPath);
        root.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
        kady.setTitle("Create New User");
        kady.centerOnScreen();
        kady.setResizable(false);
        kady.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        kady.setScene(scene);
        kady.show();
    }

    @FXML
    void deleteuseraction(ActionEvent event) throws IOException {
        Stage kady = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
        Scene scene = new Scene(root);
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
        scene.getStylesheets().add(cssPath);
        root.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
        kady.setTitle("Delete User");
        kady.centerOnScreen();
        kady.setResizable(false);
        kady.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        kady.setScene(scene);
        kady.show();
    }

    @FXML
    void trackuseraction(ActionEvent event) throws IOException {
        Stage kady = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Track_User.fxml"));
        Scene scene = new Scene(root);
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
        scene.getStylesheets().add(cssPath);
        root.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
        kady.setTitle("Track User");
        kady.centerOnScreen();
        kady.setResizable(false);
        kady.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        kady.setScene(scene);
        kady.show();
    }

    @FXML
    void logoutaction(ActionEvent event) throws IOException {
        try {
            String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
            pst = conn.prepareStatement(sqla);
            pst.setString(1, selsecc);
            pst.setString(2, seluserr);
            pst.executeUpdate();
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception exception) {
            }
        }

        Stage stg = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogIn_GUI.fxml"));
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
        stg.show();
        Stage jk = (Stage) this.bar.getScene().getWindow();
        jk.close();
    }

    @FXML
    void exitaction(ActionEvent event) {
        try {
            String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
            pst = conn.prepareStatement(sqla);
            pst.setString(1, selsecc);
            pst.setString(2, seluserr);
            pst.executeUpdate();
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception exception) {
            }
        }
        Platform.exit();
    }

    private void startAutoRefresh2() {
        refreshTimeline2 = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            Platform.runLater(this::fetching);
        }));
        refreshTimeline2.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline2.play();
    }

    private void fetching() {
        onlineusersmenu.getItems().clear();
        try {
            String sql = "SELECT User, Position FROM USERS WHERE Status = 'Online'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String username = rs.getString("User");
                String position = rs.getString("Position");
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
        statusDot.setFill(Color.LIMEGREEN);
        Text nameText = new Text(username + "  (" + position + ")");
        HBox hbox = new HBox(6, statusDot, nameText);
        MenuItem item = new MenuItem();
        item.setGraphic(hbox);
        return item;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 18);
        } catch (FileNotFoundException ex) {
          
        }
       
        
        
        
        conn = db.java_db();
        selsecc = LogIn_GUI_Controller.selectedpositionn;
        seluserr = LogIn_GUI_Controller.selecteduser;
        startAutoRefresh2();
        startAutoRefresh3();

        Platform.runLater(() -> {
            try {
                // Extract video from JAR to temp file
                File videoFile = extractResource("Video3.mp4");
                Media media = new Media(videoFile.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);

//                      //////////////////////////////////////////////////
//                   
//                      // Sound (short intro) 
//Media sound = new Media(getClass().getResource("battle.mp3").toExternalForm());
//MediaPlayer audioPlayer = new MediaPlayer(sound);
////audioPlayer = new MediaPlayer(sound);
//audioPlayer.setStopTime(Duration.seconds(14)); // play only 15s
//
//// Start only when ready
//audioPlayer.setOnReady(() -> {
//    audioPlayer.play();
//});
//
//// Debug errors
//audioPlayer.setOnError(() ->
//    System.err.println("Audio error: " + audioPlayer.getError())
//);
//audioPlayer.play();
//                    
//                   ////////////////////////////////////////////////// 
                    
                
                mediaPlayer.setOnReady(() -> {
                    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    mediaPlayer.play();
                    
             
                });

                mediaView.setMediaPlayer(mediaPlayer);
                mediaView.setPreserveRatio(true);

                Scene scene = bar.getScene();
                mediaView.fitWidthProperty().bind(scene.widthProperty());
                mediaView.fitHeightProperty().bind(scene.heightProperty());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        
        
        
        
        
        
    }
}
