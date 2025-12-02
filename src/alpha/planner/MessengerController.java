

package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class MessengerController implements Initializable {

    @FXML
    private JFXTextArea messagepreview;

    @FXML
    private JFXTextField message;
    
    @FXML
    private HBox onlinepanel;

    @FXML
    private JFXButton emoji;

    @FXML
    private JFXButton send;

    @FXML
    private JFXButton keyboard;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    public static String selsecc,seluserr;
    
     @FXML
    private ComboBox<String> selectposition;
     
      @FXML
    private MFXCheckbox sendall;
      
      private Timeline refreshTimeline2;
    
    
      @FXML
    void clooseeaction(MouseEvent event) {

        if (refreshTimeline2!=null) {
            refreshTimeline2.stop();
        }
        
        Stage ddsa=(Stage)send.getScene().getWindow();
        ddsa.close();
        
    }
    
      
    
    @FXML
    void emojiaction(ActionEvent event) {

        EmojiPanelWin8.openEmojiPanel();
        message.setFocusTraversable(true);
        message.requestFocus();
        
    }

    @FXML
    void keyboardaction(ActionEvent event) {

        //KeyBoard
        openKeyboard();
        
    }

    
    @FXML
    void messagekey(KeyEvent event) {

        KeyCode keej=event.getCode();
        if (keej.equals(KeyCode.ENTER)) {
            send.fire();
        }
        
    }

    
    @FXML
void sendaction(ActionEvent event) {

    // === Step 1: Get values ===
    String msg = messagepreview.getText().trim();
    String user = (selectposition.getSelectionModel().getSelectedItem() != null) 
            ? selectposition.getSelectionModel().getSelectedItem().toString().trim() 
            : "";

    // === Step 2: Validate message ===
    if (msg.isEmpty()) {
        showNotification("بيانات ناقصة", "مينفعش تبعت مسدج فاضية");
        return;
    }

    // === Step 3: Check if send all is selected ===
    if (sendall.isSelected()) {
        // Send to all users
        try (BufferedReader buf = new BufferedReader(
                new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Positions.kady"))) {

            String line;
            while ((line = buf.readLine()) != null) {
                
//                if (line.equals("ADMIN")) {continue;}
//                if (line.equals("Manager")) {continue;}
//                if (line.equals("Alpha_Planner")) {continue;}
//                if (line.equals("Data_Entry")) {continue;}
                if (line.equals("------------------------------------------------------------")) {continue;}
                if (line.equals(selsecc)) {continue;}
                
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
                    pst.setString(5, line); // send to each user
                    pst.setString(6, "رسالة جماعية جديدة\n\n\n"+msg);
                    pst.setString(7, "0");
                    pst.executeUpdate();
                    pst.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (this.rs != null) this.rs.close();
                        if (this.pst != null) this.pst.close();
                    } catch (Exception ignore) {}
                }
            }

            Notifications.create()
                .title("عملية ناجحة")
                .text("تم ارسال الرسالة للجميع بنجاح")
                .hideAfter(Duration.seconds(3))
                .position(Pos.CENTER)
                .showInformation();

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            showNotification("خطأ", "ملف المستخدمين غير موجود");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    } else {
        // === Step 4: Validate user selection if not sending to all ===
        if (user.isEmpty()) {
            showNotification("بيانات ناقصة", "لازم تحدد مستخدم أو تختار إرسال للجميع");
            return;
        }
        
//        if (user.equals("ADMIN")) {
//        
//        showNotification("خطأ", "هذا المستخدم لا يمكنه ارسال او استقبال رسائل فقط الرسائل بين اقسام الانتاج");
//        return;    
//        }
//        if (user.equals("Manager")) {
//        
//        showNotification("خطأ", "هذا المستخدم لا يمكنه ارسال او استقبال رسائل فقط الرسائل بين اقسام الانتاج");
//        return;
//        }
//        if (user.equals("Alpha_Planner")) {
//        
//        showNotification("خطأ", "هذا المستخدم لا يمكنه ارسال او استقبال رسائل فقط الرسائل بين اقسام الانتاج");
//        return;
//        }
//        if (user.equals("Data_Entry")) {
//        
//        showNotification("خطأ", "هذا المستخدم لا يمكنه ارسال او استقبال رسائل فقط الرسائل بين اقسام الانتاج");
//        return;    
//        }
        if (user.equals("------------------------------------------------------------")) {
        
        showNotification("خطأ", "دا مش اسم مستخدم ي رايق دا فاصل عادي مصنوع للتفرقة البصرية بلاش هبد");
        return;    
        }
        
        else {
            
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
            
            if (selsecc.equals(user)) {
            pst.setString(1, dateString);
            pst.setString(2, timeString);
            pst.setString(3, selsecc);
            pst.setString(4, seluserr);
            pst.setString(5, user);
            pst.setString(6, "رسالة فردية جديدة\n(كدا انت بتبعت رسالة لنفسك)\n\n"+msg);
            pst.setString(7, "0");
            }
            else {
            pst.setString(1, dateString);
            pst.setString(2, timeString);
            pst.setString(3, selsecc);
            pst.setString(4, seluserr);
            pst.setString(5, user);
            pst.setString(6, "رسالة فردية جديدة\n\n\n"+msg);
            pst.setString(7, "0");
            }

            
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
            
        }

       
    }
    
    
    startAutoRefresh2();
    
}

    
    
       
    
    
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
        .hideAfter(Duration.seconds(5))
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
    
    
    
      private void openKeyboard() {
        try {
            
            new ProcessBuilder("cmd", "/c", "start", "osk").start();
            // Fallback to classic OSK
            File osk = new File("C:\\Windows\\System32\\osk.exe");
            if (osk.exists()) {
                new ProcessBuilder(osk.getAbsolutePath()).start();
                return;
            }
            System.out.println("No on-screen keyboard found!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
      }
      
      
      private void startAutoRefresh2() {//Online or Offline
        refreshTimeline2 = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            Platform.runLater(this::fetching);
        }));
        refreshTimeline2.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline2.play();
    }
      
     
      //Lite Version
      
      private void fetching() {
    onlinepanel.getChildren().clear();
    try {
        String sql = "SELECT Avatar, User, Position FROM USERS WHERE Status = 'Online'";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            // === Read avatar stream ===
            InputStream is = rs.getBinaryStream("Avatar");
            File tempFile = new File(System.getProperty("user.home"), "userpic.jpg");

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[2048]; // أصغر من 4096 لتقليل استهلاك الذاكرة
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            is.close();

            // === Load scaled image (max 80x80) ===
            Image image = new Image(tempFile.toURI().toString(), 80, 80, true, true);

            ImageView avatar = new ImageView(image);
            avatar.setFitWidth(50);
            avatar.setFitHeight(50);
            avatar.setPreserveRatio(true);

            // Clip دائري
            Circle clip = new Circle(25, 25, 25);
            avatar.setClip(clip);

            // Labels
            String uuu = rs.getString("User");
            String poso = rs.getString("Position");

            Label posLabel = new Label(poso);
            posLabel.setStyle("-fx-font-family: 'Kabel'; -fx-font-weight: bold; -fx-font-size: 16px;");

            Label nameLabel = new Label(uuu);
            nameLabel.setStyle("-fx-font-family: 'Kabel'; -fx-font-weight: bold; -fx-font-size: 14px;");

            VBox vbox = new VBox(5, avatar, posLabel, nameLabel);
            vbox.setAlignment(Pos.CENTER);

            onlinepanel.getChildren().add(vbox);
        }

    } catch (OutOfMemoryError oom) {
        System.err.println("⚠️ الصورة كبيرة جدًا: " + oom.getMessage());
        showNotification("خطأ", "الصورة أكبر من اللازم، حاول رفع صورة بحجم أصغر.");
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

      
      
      
      
      
      
     //Big Version 
      
//      private void fetching() {
//        
//
//
//// حذف العناصر القديمة إن وُجدت
//onlinepanel.getChildren().clear();
//try {
//    String sql = "SELECT Avatar, User, Position FROM USERS WHERE Status = 'Online'";
//    pst = conn.prepareStatement(sql);
//    rs = pst.executeQuery();
//    while (rs.next()) {
//////////////////////////////////////////////////////////////////////////////////////
////Reading Avatar 
//// Avatar image
//ImageView avatar=new ImageView();
//avatar.setFitWidth(50);
//avatar.setFitHeight(50);
//avatar.setPreserveRatio(false); // force square scaling
//// Circular clip → perfectly round avatar
//Circle clip = new Circle(25, 25, 25); // center (width/2, height/2), radius
//avatar.setClip(clip);
//// Gradient border (Instagram-like)
//Circle border = new Circle(25, 25, 27); // a little bigger than avatar radius
//border.setStyle(
//    "-fx-fill: transparent;" +
//    "-fx-stroke: linear-gradient(to right, #feda75, #fa7e1e, #d62976, #962fbf, #4f5bd5);" +
//    "-fx-stroke-width: 3;"
//);
//
//    String uuu=rs.getString("User");
//    String poso=rs.getString("Position");
//    InputStream is = rs.getBinaryStream("Avatar");
//    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home")+"\\userpic.jpg");
//    byte[] buffer = new byte[4096];
//    int bytesRead;
//    while ((bytesRead = is.read(buffer)) != -1) {
//        fos.write(buffer, 0, bytesRead);
//    }
//    fos.close();
//    is.close();
//
//    //System.out.println("Image restored to check.png");
//    avatar.setImage(new Image((new File(System.getProperty("user.home")+"\\userpic.jpg")).toURI().toString()));
////    onlinepanel.getChildren().addAll(new VBox (avatar,new Label(uuu)));
//
//Label posLabel = new Label(poso);
//posLabel.setStyle(
//    "-fx-font-family: 'Kabel';" +
//    "-fx-font-weight: bold;" +
//    "-fx-font-size: 16px;" // adjust size as needed
//);
//
//
//Label nameLabel = new Label(uuu);
//nameLabel.setStyle(
//    "-fx-font-family: 'Kabel';" +
//    "-fx-font-weight: bold;" +
//    "-fx-font-size: 14px;" // adjust size as needed
//);
//
//VBox vbox = new VBox(5, avatar,posLabel, nameLabel); // 5 = spacing
//vbox.setAlignment(Pos.CENTER); // center both image + text
//
//onlinepanel.getChildren().add(vbox);
//
//
//////////////////////////////////////////////////////////////////////////////////////  
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
//    }
      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
        
        this.conn = db.java_db();
        
        startAutoRefresh2();
        
        messagepreview.textProperty().bind(message.textProperty());
        selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;

        this.selectposition.getItems().clear();
    try {
      BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Positions.kady"));
      String line;
      while ((line = buf.readLine()) != null) {
        this.selectposition.getItems().addAll(new String[] { line });
      } 
      buf.close();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (IOException iOException) {}
        
        
    }    
    
}
