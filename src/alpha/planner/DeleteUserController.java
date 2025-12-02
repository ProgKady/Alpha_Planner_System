
package alpha.planner;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author KADY
 */
public class DeleteUserController implements Initializable {

    
    @FXML
    private ComboBox selectuser;
    
    @FXML
    private ComboBox selectuser1;

    @FXML
    private JFXButton deletebtn;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    
    
      @FXML
    void positionaction(Event event) {

            
String sql = "SELECT User FROM USERS WHERE Position = ?";
try {
    // Clear the usersbox before adding new entries
    selectuser1.getItems().clear();
    String selectedPosition = selectuser.getSelectionModel().getSelectedItem().toString();
    this.pst = this.conn.prepareStatement(sql);
    this.pst.setString(1, selectedPosition);
    this.rs = this.pst.executeQuery();
    while (this.rs.next()) {
        String userName = this.rs.getString("User");
        selectuser1.getItems().add(userName); // Add each name to usersbox
    }
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

        
        
    }
    
    
    
    

    @FXML
    void deletebtnaction(ActionEvent event) {
        
        
        
          Alert alertd = new Alert(Alert.AlertType.CONFIRMATION);
        alertd.setTitle("Delete User");
        alertd.setHeaderText("Are you sure?");
        alertd.setContentText("Are you sure that you wanna delete this user?");
        ButtonType buttonTypeOned = new ButtonType("Delete");
        //ButtonType buttonTypeCanceld = new ButtonType("");
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertd.getButtonTypes().setAll(buttonTypeOned, cancelBtn);
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
            
              
        
        
        
        String toool = selectuser1.getSelectionModel().getSelectedItem().toString(); //User
        String tooo2 = selectuser.getSelectionModel().getSelectedItem().toString(); //Section
        
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeString = sdf.format(d);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            String dateString = dtf.format(now);
            
            String value0 = timeString;
            String value1 = dateString;
            
            try{
                String reg= "insert into Audit (Date, Section, User, Notes) values ('"+value0+"','"+"ADMIN"+"','"+"KADINIO"+"','"+"KADINIO deleted a user called "+toool+" in "+tooo2+" section."+"')";
                pst=conn.prepareStatement(reg);
                pst.execute();
            }
            catch (Exception e)

            {
               // JOptionPane.showMessageDialog(null,e);
            }
            
            
            
         String sql ="delete from USERS where Position=? and User=? ";
        try{
            pst=conn.prepareStatement(sql);
            pst.setString(1, tooo2);
            pst.setString(2, toool);
            pst.execute();
           // JOptionPane.showMessageDialog(null,"User Deleted");
            
        Alert al=new Alert (Alert.AlertType.INFORMATION);
        al.setTitle("User has been deleted");
        al.setHeaderText("Deleting User");
        al.setContentText("User has been deleted successfully!");
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
            
            
        }catch(Exception e){
            
        //    JOptionPane.showMessageDialog(null, e);
        }finally {
            
            try{
                rs.close();
                pst.close();
                
            }
            catch(Exception e){
                
            }
        }
        
        
        Stage ere=(Stage) deletebtn.getScene().getWindow();
        ere.close();
        
      
            
        }
        
       
        
        
        else {
            
            
        }
        
      
       
        

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
         try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
        
      
        //selectuser.getItems().addAll("Admin","WareHouse1","Dry Process","Washing BarCode");
        
          this.selectuser.getItems().clear();
    try {
      BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Positions.kady"));
      String line;
      while ((line = buf.readLine()) != null) {
        this.selectuser.getItems().addAll(new String[] { line });
      } 
      buf.close();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (IOException iOException) {}
        
        conn=db.java_db();
        
        
    }    
    
}
