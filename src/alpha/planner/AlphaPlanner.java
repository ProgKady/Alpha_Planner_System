package alpha.planner;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class AlphaPlanner extends Application {

    public static String diro;
    private static boolean DARK_MODE = true;
    MediaView mediaView;
    VBox root;
    Label subTitle;
    JFXProgressBar pb;
    Label title;
    Label subTitlee;
    ImageView splashImage;
    Stage splashStage;
    
    MediaPlayer audioPlayer;
    
    FadeTransition fade;
    
    private boolean muted = false;

    @Override
    public void start(Stage stage) throws Exception {
        showSplash(stage);
    }
    
     // Helper: Convert Color to RGB string for CSS
    private String toRgbString(Color c) {
        return String.format("rgb(%d, %d, %d)",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }
    
    
    // ✅ Extracts a resource (video/image/css/etc.) from JAR to a temp file
private File extractResource(String resourceName) throws IOException {
    InputStream in = getClass().getResourceAsStream(resourceName);
    if (in == null) throw new IOException("Resource not found: " + resourceName);

    // Detect extension from resource name
    String extension = "";
    int dot = resourceName.lastIndexOf('.');
    if (dot != -1) {
        extension = resourceName.substring(dot);
    }

    File tempFile = File.createTempFile("resource_", extension);
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


   private void showSplash(Stage primaryStage) throws IOException {
       
       try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 18);
        } catch (FileNotFoundException ex) {
          
        }
       
       
    // Load theme from settings
    BufferedReader bis = new BufferedReader(
            new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
    String themooo = bis.readLine();
    bis.close();

    URL cssUrl = getClass().getResource(themooo);
    DARK_MODE = "cupertino-dark.css".equals(themooo);

    // Splash image
    splashImage = new ImageView(new Image(getClass().getResourceAsStream("splash.png")));
    splashImage.setFitWidth(200);
    splashImage.setFitHeight(400);
    splashImage.setPreserveRatio(true);

    // Colors for text
    Color mainTextColor = DARK_MODE ? Color.web("#FFFFFF") : Color.web("#000000");
    Color subTextColor = DARK_MODE ? Color.web("#8E8E93") : Color.web("#6E6E73");

    // Labels
    subTitlee = new Label("اهلا يا " + System.getProperty("user.name"));
    subTitlee.setStyle(
            "-fx-text-fill: black;" +
            "-fx-font-family: 'Cairo SemiBold';" +        
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +  // رمادي فاتح زي iOS
            "-fx-background-radius: 35;" +
            "-fx-font-size: 18 px;"+        
            "-fx-padding: 8 12 8 12;"
        );
    subTitlee.setTextFill(subTextColor);

    title = new Label("Alpha Planner");
    title.setFont(Font.font("Cairo SemiBold", FontWeight.BLACK, 70));
    title.setTextFill(mainTextColor);
    
    Label l1 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l2 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l3 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l4 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l5 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l6 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l7 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l8 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l9 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l10 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l11 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l12 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l13 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l14 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);
    
    Label l15 = new Label("");
    //l1.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 60));
    //l1.setTextFill(mainTextColor);

    subTitle = new Label("© جميع الحقوق محفوظة 2025 — Kadysoft Ltd — أحمد القاضي");
    subTitle.setStyle(
            "-fx-text-fill: black;" +
            "-fx-font-family: 'Cairo SemiBold';" +        
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +  // رمادي فاتح زي iOS
            "-fx-background-radius: 35;" +
            "-fx-font-size: 17 px;"+        
            "-fx-padding: 8 12 8 12;"
        );
    subTitle.setTextFill(subTextColor);

    pb = new JFXProgressBar();
    pb.setMinSize(300, 20);
    
    
    HBox conta=new HBox ();
    conta.setPadding(new Insets (30,15,0,0));
    conta.setAlignment(Pos.CENTER_RIGHT);
    conta.setSpacing(25);
    
    Hyperlink skip=new Hyperlink("Skip Intro");
    skip.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
    skip.setOnAction(oo -> {
        
        //Skip Video
        audioPlayer.stop();
        splashStage.close();
        fade.stop();
        
    try {
        showLogin(primaryStage);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        
    });
    
    
    
    
    FontIcon volumeIcon = new FontIcon(FontAwesomeSolid.VOLUME_UP); // unmute icon
    volumeIcon.setIconSize(24);
    volumeIcon.setIconColor(javafx.scene.paint.Color.DODGERBLUE);

    
    Button mute=new Button ("", volumeIcon);
    mute.setStyle("-fx-background-color:transparent");
    //mute.setStyle("-fx-background-radius: 50; -fx-background-color: #f0f0f0; -fx-padding: 10;");

    mute.setOnAction(ds -> {
        
        //Mute Audio
        
        muted = !muted;
            if (muted) {
                volumeIcon.setIconLiteral("fas-volume-mute"); // mute icon
                volumeIcon.setIconColor(javafx.scene.paint.Color.GRAY);
                
                audioPlayer.setMute(true);
                
            } else {
                volumeIcon.setIconLiteral("fas-volume-up"); // unmute icon
                volumeIcon.setIconColor(javafx.scene.paint.Color.DODGERBLUE);
                
                audioPlayer.setMute(false);
                
            }
        
        
        
        
    });
    
    conta.getChildren().addAll(mute,skip);

    // VBox for text + splash image
    root = new VBox(5, conta,splashImage, subTitlee, title,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13, subTitle);
    root.setPadding(new Insets (3,0,0,0));
    root.setAlignment(Pos.CENTER);
    root.setBackground(Background.EMPTY);
    //root.setStyle("-fx-background-color:transparent;");

    // Video background
    mediaView = new MediaView();
    File videoFile = extractResource("BKAPWDuZ_fixed.mp4");
    Media media = new Media(videoFile.toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    mediaView.setMediaPlayer(mediaPlayer);
    mediaView.setPreserveRatio(false);

    // Debug
    mediaPlayer.setOnError(() -> System.err.println("MediaPlayer error: " + mediaPlayer.getError()));
    media.setOnError(() -> System.err.println("Media error: " + media.getError()));

    // Sound (short intro)
    //Media sound = new Media(getClass().getResource("intro.mp3").toExternalForm());
    //MediaPlayer audioPlayer = new MediaPlayer(sound);
    //audioPlayer.setStopTime(Duration.seconds(15)); // only play 10s
    //audioPlayer.stop();
    
    // Sound (short intro) 
Media sound = new Media(getClass().getResource("intro.mp3").toExternalForm());
audioPlayer = new MediaPlayer(sound);
//audioPlayer = new MediaPlayer(sound);
audioPlayer.setStopTime(Duration.seconds(15)); // play only 15s

// Start only when ready
audioPlayer.setOnReady(() -> {
    audioPlayer.play();
});

// Debug errors
audioPlayer.setOnError(() ->
    System.err.println("Audio error: " + audioPlayer.getError())
);

    

    // Autoplay video loop
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.setOnReady(mediaPlayer::play);
    audioPlayer.play();

    // StackPane (video + overlay)
    StackPane sp = new StackPane(mediaView, root);

    // Scene setup
    Scene splashScene = new Scene(sp, 500, 950);
    splashScene.setFill(Color.TRANSPARENT);

    // Rounded corner clipping
    Rectangle clip = new Rectangle(500, 950);
    clip.setArcWidth(100); 
    clip.setArcHeight(100);
    sp.setClip(clip);

    // Bind video to scene size
    mediaView.fitWidthProperty().bind(splashScene.widthProperty());
    mediaView.fitHeightProperty().bind(splashScene.heightProperty());

    // Modern iPhone-like border (glossy effect)
    sp.setStyle(
        "-fx-border-color: linear-gradient(to bottom, #A0A0A5, #E0E0E5); " +
        "-fx-border-width: 3; " +
        "-fx-border-radius: 100; " +
        "-fx-background-radius: 100; " +
        "-fx-background-color: transparent;"
    );

    // Stage setup
    splashStage = new Stage(StageStyle.TRANSPARENT);
    if (cssUrl != null) {
        splashScene.getStylesheets().add(cssUrl.toExternalForm());
    }

    splashStage.setScene(splashScene);
    splashStage.centerOnScreen();
    splashStage.show();

    // Fade transition
    fade = new FadeTransition(Duration.seconds(4), sp);
    //fade.setFromValue(0.0);
    //fade.setToValue(1.0);
    fade.setCycleCount(4);
    fade.setAutoReverse(true);

    fade.setOnFinished(e -> {
    // Smooth fade out audio
    Timeline volumeFade = new Timeline(
        new KeyFrame(Duration.seconds(0), 
            new KeyValue(audioPlayer.volumeProperty(), audioPlayer.getVolume())),
        new KeyFrame(Duration.seconds(2), 
            new KeyValue(audioPlayer.volumeProperty(), 0))
    );
    volumeFade.setOnFinished(ev -> audioPlayer.stop());
    volumeFade.play();

    splashStage.close();
    try {
        showLogin(primaryStage);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});


    fade.play();
}


    

    private void showLogin(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn_GUI.fxml"));
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
        stage.setScene(scene);
        stage.setTitle("LogIN Window");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
        stage.setOnCloseRequest((WindowEvent event) -> System.exit(0));
        stage.show();

        checkExpiration(stage);
    }

    private void checkExpiration(Stage stage) throws Exception {
        
        
        //NEWE
        
        
        
//        Date currentDate = GregorianCalendar.getInstance().getTime();
//        DateFormat df = DateFormat.getDateInstance();
//        df.format(currentDate);
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String value1 = sdf.format(d);
//
//        try {
//            String path = AlphaPlanner.class.getProtectionDomain()
//                    .getCodeSource().getLocation().toURI().getPath();
//            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
//            File file = new File(decodedPath);
//            String dir = file.isFile() ? file.getParent() : file.getPath();
//            diro = dir;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        File ff = new File(diro + "\\Java\\bin\\java.cfg");
//
//        if (ff.exists()) {
//            BufferedReader buff = new BufferedReader(new FileReader(ff));
//            String line = buff.readLine();
//            buff.close();
//            LocalDate d1 = LocalDate.parse(line, DateTimeFormatter.ISO_LOCAL_DATE);
//            LocalDate d2 = LocalDate.parse(value1, DateTimeFormatter.ISO_LOCAL_DATE);
//            long diffDays = ChronoUnit.DAYS.between(d1, d2);
//
//            if (diffDays >= 30) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Session Expired");
//                alert.setResizable(false);
//                DialogPane dialogPaneef = alert.getDialogPane();
//                  //////////////////////////////////////////////////////////////////////////////////////////////////////
//  try {  
//  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//  String themooo=bis.readLine();
//  bis.close();
//  if (themooo.equals("cupertino-dark.css")) {
//      //Dark
//      dialogPaneef.getStylesheets().add(
//      getClass().getResource("cupertino-dark.css").toExternalForm());
//  }
//  else {
//      //Light
//      dialogPaneef.getStylesheets().add(
//      getClass().getResource("cupertino-light.css").toExternalForm());
//  }
//  }
//  catch (Exception re) {}
//  //////////////////////////////////////////////////////////////////////////////////////////////////////  
//
//                Label l1 = new Label("Sorry, your expiration date has been ended.");
//                l1.setEffect(new DropShadow());
//
//                Label l2 = new Label("Please, register to continue.");
//                l2.setEffect(new DropShadow());
//
//                Label l3 = new Label("Or click 'OK' or 'EXIT' to exit and cancel the operation.");
//                l3.setEffect(new DropShadow());
//
//                Hyperlink hy = new Hyperlink("Register");
//                hy.setEffect(new DropShadow());
//
//                JFXPasswordField jfx = new JFXPasswordField();
//                jfx.setAlignment(Pos.CENTER);
//                jfx.setLabelFloat(true);
//                jfx.setPromptText("Enter Serial Key!!!.");
//                jfx.setMinSize(300, 30);
//                jfx.setStyle("-fx-background-color:lightblue;-fx-font-weight:bold;-fx-font-size:15;");
//                jfx.setEffect(new DropShadow());
//
//                VBox pane = new VBox();
//                pane.getChildren().addAll(l1, l2, l3);
//                pane.setSpacing(20);
//
//                alert.setGraphic(pane);
//
//                Optional<ButtonType> option = alert.showAndWait();
//
//                if (!option.isPresent() || option.get() == ButtonType.OK) {
//                    stage.close();
//                } else if (option.get() == ButtonType.CANCEL) {
//                    Notifications noti = Notifications.create();
//                    noti.title("Cancel!");
//                    noti.text("Operation Cancelled, nothing will happen.");
//                    noti.position(Pos.CENTER);
//                    noti.showInformation();
//                    stage.close();
//                }
//            }
//        } else {
//            ff.createNewFile();
//            PrintWriter pw = new PrintWriter(new FileWriter(ff));
//            pw.print("2023-10-20");
//            pw.close();
//        }
//        
        
        
        
        
        
        
        //OLD
             
      
    //////////////////////////Add All Codes Here////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Read first and compare then write
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    String value1 = timeString;

    File ff=new File (System.getProperty("user.home")+"\\AppData\\Roaming\\.store_cfg");
    
    if (ff.exists()) {
      //Read then compare if equals 30 or more show alert and exit, if less don't do anything.
      BufferedReader buff=new BufferedReader(new FileReader(ff));
      String line;
      line=buff.readLine();
      buff.close();
      LocalDate d1 = LocalDate.parse(line, DateTimeFormatter.ISO_LOCAL_DATE);
      LocalDate d2 = LocalDate.parse(value1, DateTimeFormatter.ISO_LOCAL_DATE);
      java.time.Duration diff = java.time.Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
      long diffDays = diff.toDays();
      
      if (diffDays>=30) {
          //Show Alert to register or close.
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Session Expired");
          alert.setResizable(false);
          DialogPane dialogPaneef = alert.getDialogPane();
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
          Label l1=new Label("Sorry, your expiration date has been ended.");
          //l1.setEffect(new DropShadow());
          l1.setStyle("-fx-font-weight:bold;-fx-font-size:15;-fx-background-radius:3em;");
          
          Label l2=new Label("Please, enter the serial key to start another period.");
          //l2.setEffect(new DropShadow());
          l2.setStyle("-fx-font-weight:bold;-fx-font-size:15;-fx-background-radius:3em;");
          
          Label l3=new Label("Or click 'OK' or 'EXIT' to exit and cancel the operation.");
          //l3.setEffect(new DropShadow());
          l3.setStyle("-fx-font-weight:bold;-fx-font-size:15;-fx-background-radius:3em;");
         
          JFXPasswordField jfx=new JFXPasswordField ();
          jfx.setAlignment(Pos.CENTER);
          jfx.setLabelFloat(true);
          jfx.setPromptText("Enter Serial Key ...");
          jfx.setMinSize(250, 30);
          jfx.setStyle("-fx-font-weight:bold;-fx-font-size:15;-fx-background-radius:3em;");
          jfx.setEffect(new DropShadow());
          
          VBox pane=new VBox();
          pane.getChildren().addAll(l1,l2,l3,jfx);
          pane.setSpacing(20);

          alert.setGraphic(pane);
          
          Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
         stage.close();
      } else if (option.get() == ButtonType.OK) {
      ///////////////////////////
      
      String textt=jfx.getText();
    
          if (textt.equals("WE LOVE KADYSOFT")) {
             //Show noti and change date in file. 
              
             ff.createNewFile();
             PrintWriter pw=new PrintWriter (new FileWriter (ff));
             pw.print(value1);
             pw.close();
             
             Notifications noti = Notifications.create();
             noti.title("Congratulations!");
             noti.text("Congratulations!, you have started a new period\nEnjoy using our software.\n\n\nPowered By Kadysoft Ltd, Ahmed Elkady - CEO.");
             noti.position(Pos.CENTER);
             noti.hideAfter(javafx.util.Duration.minutes(1));
             noti.showInformation();
             
             alert.close();
          }
          
          else {
          Notifications noti = Notifications.create();
          noti.title("Wrong Serial Key!");
          noti.text("Sorry, I will close.");
          noti.position(Pos.CENTER);
          noti.showInformation();
          alert.close();
          stage.close();
          }  
      ///////////////////////////
      }
      else if (option.get() == ButtonType.CANCEL) {
      Notifications noti = Notifications.create();
      noti.title("Cancel!");
      noti.text("Operation Cancelled, nothing will happen.");
      noti.position(Pos.CENTER);
      noti.showInformation();
      stage.close();
      }
      else {
         
      }
         
          ///////////////////////////////////////////////////////////////////////////////////////////////////
          
      }
      
      else {
          // Do Nothing
      }
    }
    else {
        //Write date for the first time.
        ff.createNewFile();
        PrintWriter pw=new PrintWriter (new FileWriter (ff));
        pw.print("2023-10-20");
        pw.close();
    }
      
      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
  
   