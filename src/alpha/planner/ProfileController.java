


package alpha.planner;

import com.jfoenix.controls.JFXTextArea;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class ProfileController implements Initializable {

   @FXML
    private ImageView avatar;
   
    public static String selsecc,seluserr;
   
    Connection conn = null ;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
      @FXML
    private Label user;

    @FXML
    private Label section;

    @FXML
    private JFXTextArea info;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            javafx.scene.text.Font cairoSemiBold = javafx.scene.text.Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WASHINGController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        conn = db.java_db();
        
    selsecc=LogIn_GUI_Controller.selectedpositionn;
    seluserr=LogIn_GUI_Controller.selecteduser;
        
        
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
        user.setText(seluserr);
        section.setText(selsecc);
        
        try {
            
    String sqlo = "SELECT BIO FROM USERS WHERE User = ? AND Position = ?";
    pst = conn.prepareStatement(sqlo);
    pst.setString(1, seluserr);
    pst.setString(2, selsecc);
    rs = pst.executeQuery();
    
    String boio=rs.getString("BIO");
    info.setText(boio);
        //info.setText("\"أنا "+seluserr+"، شغال في قسم "+selsecc+"، واحد بسيط بحب شغلي وبسعى أتعلم كل يوم حاجة جديدة. شايف إن الطموح هو السكة اللي بتوصل للنجاح، ومهما كانت الظروف صعبة الواحد لازم يفضل مكمل. بحلم أحقق إنجازات تخليني أفتخر بيها قدام نفسي وأهلي. اللي يعرفني عارف إني بحب التعاون والضحكة الحلوة، وأي فرصة قدامي بحاول أستغلها للآخر. النجاح بالنسبالي مش بس شغل، النجاح إنك تعيش حياتك برضا وفرحة.\"");
        
            
        }
        
        catch (Exception e) {
    e.printStackTrace();
    avatar.setImage(new Image(getClass().getResourceAsStream("user.png")));
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (Exception ignored) {}
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
    
}
