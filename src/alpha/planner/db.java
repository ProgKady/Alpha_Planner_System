package alpha.planner;

import java.awt.Component;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javax.swing.JOptionPane;

public class db {
   Connection conn = null;
   
   static String vvaall;

   
   public static Connection java_db() {
       
       
      try {
       
          
             
        String filePath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";;
        
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("DB=")) {
        vvaall = line.split("=", 2)[1];
        break;
        }} 
          
         Class.forName("org.sqlite.JDBC");
         Connection conn = DriverManager.getConnection("jdbc:sqlite:"+vvaall);
         return conn;
      } catch (Exception var1) {
         //JOptionPane.showMessageDialog((Component)null, var1);
         
        Alert alo = new Alert(Alert.AlertType.INFORMATION);
        alo.setTitle(var1.toString());
        alo.setResizable(false);
        alo.setHeaderText(var1.toString());
        alo.setContentText("Sorry we face a problem :\n"+"\""+var1.toString()+"\""+"\n\nPowered By Kadysoft Ltd - Ahmed Elkady.");
        //DialogPane dialogPane = alo.getDialogPane();
        //dialogPane.getStylesheets().add(
      //getClass().getResource("primer-dark.css").toExternalForm());
        alo.showAndWait();
         
         
         return null;
      }
   }
}


/*


Whisker=50
Scraping=25
Pocket Scraping=30
Tagging=40
Remove Tagging=22
Tying=45
Damage=98
Manual Destroy=77
Laser Marking&Destroy=78
Stone Wash+Bleaching=96
Stone Wash=12
Enzyme Wash=32
Desize Wash=69
Bleaching=32
Enzyme Wash+Bleaching=59
Waterless Enzyme+Bleach=56
Desize Wash+Bleaching=48
Rinse Wash=72
Tint+Softener=11
Neutralization+Tint=10
PP Spray Classic=9
PP Spray All Over=82
Pigment Spray=39
Pocket Spray=49
Resine=93
Laser Twin=72
Laser Table Whisker Scrap&Destroying=81
Mid Control=63
Final Control=15


*/