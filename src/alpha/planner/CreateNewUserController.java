
package alpha.planner;

import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
//import java.util.Arrays;
//import java.util.Arrays;
import java.util.ResourceBundle;
///import javafx.collections.FXCollections;
//import javafx.collections.FXCollections;
//import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;
//import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author KADY
 */
public class CreateNewUserController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox selectposition;

    @FXML
    private JFXTextField addname;

    @FXML
    private JFXPasswordField addpassword;

     @FXML
    private ComboBox selectquestion;

    @FXML
    private JFXTextField answer;

    @FXML
    private JFXButton signup;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
     @FXML
    private ImageView avatar;
     
     public static String avatarpath;
     
       @FXML
    private JFXTextArea bio;
       
        @FXML
    void bioaction(MouseEvent event) {
        
        String seco=selectposition.getSelectionModel().getSelectedItem().toString();
        String uuuo=addname.getText();
        bio.setText("\"أنا "+uuuo+"، شغال في قسم "+seco+"، واحد بسيط بحب شغلي وبسعى أتعلم كل يوم حاجة جديدة. شايف إن الطموح هو السكة اللي بتوصل للنجاح، ومهما كانت الظروف صعبة الواحد لازم يفضل مكمل. بحلم أحقق إنجازات تخليني أفتخر بيها قدام نفسي وأهلي. اللي يعرفني عارف إني بحب التعاون والضحكة الحلوة، وأي فرصة قدامي بحاول أستغلها للآخر. النجاح بالنسبالي مش بس شغل، النجاح إنك تعيش حياتك برضا وفرحة.\"");
        
    }

    

    @FXML
    void selectquestionaction(Event event) {
        String toool = selectquestion.getSelectionModel().getSelectedItem().toString();
        answer.setDisable(false);
        signup.setDisable(false);

    }

    @FXML
    void signupaction(ActionEvent event) throws IOException, SQLException {
        
         if (addname.getText().equals("")) {
        //JOptionPane.showMessageDialog(null, "Username Field is empty");
        Alert al=new Alert (Alert.AlertType.ERROR);
        al.setTitle("Log In Information");
        al.setHeaderText("LogIn Error");
        al.setContentText("Username Field is empty");
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
    } else if (addpassword.getText().equals("")) {
        //JOptionPane.showMessageDialog(null, "Password Field is empty");
        Alert al=new Alert (Alert.AlertType.ERROR);
        al.setTitle("Log In Information");
        al.setHeaderText("LogIn Error");
        al.setContentText("Password Field is empty");
        al.setResizable(false);
        DialogPane dialogPane = al.getDialogPane();
        dialogPane.getStylesheets().add(
      getClass().getResource("cupertino-light.css").toExternalForm());
        al.showAndWait();
    }else {
        
        
        ////////////////////////////////////////////////////////
        
String toool1 = selectquestion.getSelectionModel().getSelectedItem().toString(); //Question
String toool2 = selectposition.getSelectionModel().getSelectedItem().toString(); //Position
String username=addname.getText(); //Name
String userpassword=addpassword.getText(); //Password
String answerr=answer.getText(); //Answer
        
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeString = sdf.format(d);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            String dateString = dtf.format(now);
            
            String value0 = timeString;
            String value1 = dateString;
            
            String baio=bio.getText();
            
            try{
                String reg= "insert into Audit (Date, Section, User, Notes) values ('"+value0+"','"+"ADMIN"+"','"+"KADINIO"+"','"+"KADINIO added a new user called "+username+" in "+toool2+" section."+"')";
                pst=conn.prepareStatement(reg);
                pst.execute();
            }
            catch (Exception e)

            {
              String reg= "insert into Audit (Date, Section, User, Notes) values ('"+value0+"','"+"ADMIN"+"','"+"KADINIO"+"','"+"KADINIO added a new user called "+"UNKNOWN"+" in "+"UNKNOWN"+" section."+"')";
              pst=conn.prepareStatement(reg);
              pst.execute();
            }

        try {
            
             
            String sql ="insert into USERS (Position,User,Password,Question,Answer,Status,BIO,Avatar) values (?,?,?,?,?,?,?,?) ";

            pst=conn.prepareStatement(sql);
            pst.setString(1,toool2);
            pst.setString(2,username);
            pst.setString(3,userpassword);
            pst.setString(4,toool1);
            pst.setString(5,answerr);
            pst.setString(6,"Not_Signed");
            pst.setString(7,baio);
            
File file = new File(avatarpath);
try (FileInputStream fis = new FileInputStream(file)) {
    pst.setBinaryStream(8, fis, (int) file.length());
    pst.executeUpdate();
    System.out.println("Image inserted: " + file.length() + " bytes");
}
            
            System.out.println("Image inserted into database!");
          
          
        Alert al=new Alert (Alert.AlertType.INFORMATION);
        al.setTitle("User has been created");
        al.setHeaderText("Creating User");
        al.setContentText("User has been created successfully!");
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
        

        }
        catch (Exception e)

        {
//         String sql ="insert into USERS (Position,User,Password,Question,Answer,Status) values (?,?,?,?,?,?) ";
//
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,"UNKNOWN");
//            pst.setString(2,"UNKNOWN");
//            pst.setString(3,"UNKNOWN");
//            pst.setString(4,"UNKNOWN");
//            pst.setString(5,"UNKNOWN");
//            pst.setString(6,"Not_Signed");
//
//            pst.execute();
        }
        finally {

            try{
                rs.close();
                pst.close();

            }
            catch(Exception e){

            }
        }
        
        
     //   }
   // Stage jk = (Stage)this.addname.getScene().getWindow();
  //  jk.close();
  
        
    }
        

    //////////////////////////////////////////////
    }

    
      @FXML
    void avataraction(MouseEvent event) {

      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new ExtensionFilter("Image Files", new String[]{"*.jpg"}));
      fcho.getExtensionFilters().add(new ExtensionFilter("Image Files", new String[]{"*.jpeg"}));
      fcho.getExtensionFilters().add(new ExtensionFilter("Image Files", new String[]{"*.png"}));
      fcho.getExtensionFilters().add(new ExtensionFilter("Image Files", new String[]{"*.bmp"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      avatar.setImage(new Image((new File(dirpathe)).toURI().toString()));
      avatarpath=dirpathe;
      
    }

    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
      
        //selectquestion.getItems().addAll("What was the name of your first pet?","What is your mother’s name?","What was the name of your elementary/primary school?","What is the name of the town where you were born?","What is the name of your favorite childhood friend?");
        
        selectquestion.getItems().addAll(
    "ما هو اسم أول حيوان أليف امتلكته؟",
    "ما هو اسم والدتك؟",
    "ما هو اسم مدرستك الابتدائية؟",
    "ما هو اسم المدينة التي وُلدت فيها؟",
    "ما هو اسم صديقك المفضل في الطفولة؟",
    "ما هي وظيفتك الأولى؟",
    "ما هو لقب جدك أو جدتك؟",
    "ما هي هوايتك المفضلة في الصغر؟",
    "ما هو اسم معلمك المفضل في المدرسة؟",
    "ما هي لعبتك المفضلة عندما كنت طفلاً؟"
);

        
        
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
        
       
        conn=db.java_db();
        
        // Avatar image
avatar.setFitWidth(80);
avatar.setFitHeight(80);
avatar.setPreserveRatio(true);

// Circular clip (80x80 → radius = 40)
Circle clip = new Circle(40, 40, 40); // centerX, centerY, radius
avatar.setClip(clip);

// Gradient border (Instagram-like)
Circle border = new Circle(40, 40, 44); // slightly bigger than avatar
border.setStyle(
    "-fx-fill: transparent;" +
    "-fx-stroke: linear-gradient(to right, #feda75, #fa7e1e, #d62976, #962fbf, #4f5bd5);" +
    "-fx-stroke-width: 4;"
);
        
        
    }    
    
}
