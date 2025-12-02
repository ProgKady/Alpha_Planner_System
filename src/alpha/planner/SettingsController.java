
package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */

public class SettingsController implements Initializable {

    
   
     @FXML
    private JFXTextField sectiontf;

    @FXML
    private JFXButton addsection;

    @FXML
    private JFXListView<String> sections;

    @FXML
    private JFXButton renamesection;

    @FXML
    private JFXButton deletesection;

    @FXML
    private JFXTextField systemdb,workinghourstf;

    @FXML
    private JFXButton browse1;

    @FXML
    private JFXButton test1;

    @FXML
    private JFXTextField recipedb;

    @FXML
    private JFXButton browse2;

    @FXML
    private JFXButton test2,addvalueee,deletevalueee;

    @FXML
    private JFXTextField valuetf,valuetfff;

    @FXML
    private JFXButton addvalue;

    @FXML
    private JFXListView<String> values,valuesss;

    @FXML
    private JFXButton editvalue,browsewh;

    @FXML
    private JFXButton deletevalue;

    @FXML
    private JFXTextField recipepath;

    @FXML
    private JFXTextField times,sectionstf,machines;

    @FXML
    private JFXButton browse3;
    
    @FXML
    private JFXButton edittime,edittime2;

    @FXML
    private JFXButton browse4;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton forget;

    @FXML
    private JFXTextArea logarea;
    
    static String vaa;
    
    public static String selsecc,seluserr;
    
    public static String value1;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
     @FXML
    private MFXToggleButton themo;


   
   
    @FXML
    void browsewhaction(ActionEvent event) throws IOException {
        
      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kadysoft Files", new String[]{"*.kady"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      workinghourstf.setText(dirpathe);
        
    }
    
    
    
     @FXML
    void addsectionaction(ActionEvent event) throws IOException {
        
        //Add to List 
        //Write to File
        
        
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("Sections_File=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        sections.getItems().add(sectiontf.getText());
        ObservableList<String> items = sections.getItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vaa))) {
            // Iterate through each item in the ListView and write it to the file.
            for (String item : items) {
                writer.write(item);
                writer.newLine();  // Write a newline after each item.
            }
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = vaa;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        sections.setItems(liness);

        sectiontf.clear();
        
    }

    @FXML
    void addvalueaction(ActionEvent event) throws IOException {
        
        
 //Add to List 
        //Write to File
        
        
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("Machines_File=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        values.getItems().add(valuetf.getText());
        ObservableList<String> items = values.getItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vaa))) {
            // Iterate through each item in the ListView and write it to the file.
            for (String item : items) {
                writer.write(item);
                writer.newLine();  // Write a newline after each item.
            }
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = vaa;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        values.setItems(liness);
        
        valuetf.clear();
        
        
    }
    
    
    
    
    
    
     @FXML
    void addvalueeeaction(ActionEvent event) throws IOException {
        
        
 //Add to List 
        //Write to File
        
        
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("Working_Hours=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        valuesss.getItems().add(valuetfff.getText());
        ObservableList<String> items = valuesss.getItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vaa))) {
            // Iterate through each item in the ListView and write it to the file.
            for (String item : items) {
                writer.write(item);
                writer.newLine();  // Write a newline after each item.
            }
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = vaa;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        valuesss.setItems(liness);
        
        valuetfff.clear();
        
        
    }
    
    
    
    
    
    
    
    

    @FXML
    void deletesectionaction(ActionEvent event) throws IOException {

        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("Sections_File=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        sections.getItems().remove(sections.getSelectionModel().getSelectedIndex());
        ObservableList<String> items = sections.getItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vaa))) {
            // Iterate through each item in the ListView and write it to the file.
            for (String item : items) {
                writer.write(item);
                writer.newLine();  // Write a newline after each item.
            }
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = vaa;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        sections.setItems(liness);
        
    }

    @FXML
    void deletevalueaction(ActionEvent event) throws IOException {

        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("Machines_File=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        values.getItems().remove(values.getSelectionModel().getSelectedIndex());
        ObservableList<String> items = values.getItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vaa))) {
            // Iterate through each item in the ListView and write it to the file.
            for (String item : items) {
                writer.write(item);
                writer.newLine();  // Write a newline after each item.
            }
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = vaa;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        values.setItems(liness);
        
    }
    
    
    
    
    
    
    
     @FXML
    void deletevalueeeaction(ActionEvent event) throws IOException {

        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("Working_Hours=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        valuesss.getItems().remove(valuesss.getSelectionModel().getSelectedIndex());
        ObservableList<String> items = valuesss.getItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vaa))) {
            // Iterate through each item in the ListView and write it to the file.
            for (String item : items) {
                writer.write(item);
                writer.newLine();  // Write a newline after each item.
            }
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = vaa;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        valuesss.setItems(liness);
        
    }

    
    
    
    
    
    
    
    @FXML
    void editvalueaction(ActionEvent event) {}

    @FXML
    void forgetaction(ActionEvent event) {

        Stage jk = (Stage)this.times.getScene().getWindow();
        jk.close();
        
    }

    @FXML
    void renamesectionaction(ActionEvent event) {}
    
      
    @FXML
    void edittimeaction(ActionEvent event) throws IOException {

            Desktop ddd;
            ddd=Desktop.getDesktop();
            ddd.open(new File (times.getText()));
        
    }
    
    
     @FXML
    void edittime2action(ActionEvent event) throws IOException {

    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("EditTime.fxml"));
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
    stg.setTitle("");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.show();
        
    }
    
    
     @FXML
    void browse5action(ActionEvent event) {

      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kadysoft Files", new String[]{"*.kady"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      sectionstf.setText(dirpathe);
        
    }

    @FXML
    void browse6action(ActionEvent event) {

      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kadysoft Files", new String[]{"*.kady"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      machines.setText(dirpathe);
        
    }
    

    @FXML
    void browse1action(ActionEvent event) {
        
      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite Files", new String[]{"*.db"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite Files", new String[]{"*.sqlite"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite Files", new String[]{"*.sqlite3"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      systemdb.setText(dirpathe);
      

    }

    @FXML
    void browse2action(ActionEvent event) {
        
      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite Files", new String[]{"*.db"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite Files", new String[]{"*.sqlite"}));
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite Files", new String[]{"*.sqlite3"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      recipedb.setText(dirpathe);

    }

    @FXML
    void browse3action(ActionEvent event) {
        
      DirectoryChooser fcho = new DirectoryChooser();
      fcho.setTitle("Kady Choose");
      File f = fcho.showDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      recipepath.setText(dirpathe);

    }

    @FXML
    void browse4action(ActionEvent event) {
        
      FileChooser fcho = new FileChooser();
      fcho.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kadysoft Files", new String[]{"*.kady"}));
      fcho.setTitle("Kady Choose");
      File f = fcho.showOpenDialog((Window)null);
      String dirpathe = f.getAbsolutePath().toString();
      times.setText(dirpathe);

    }
    
    
    
    
    
    
    
    @FXML
    void save1action(ActionEvent event) throws IOException {

        //Save Data about DB and Times
        
        String dbb=systemdb.getText();
        String recdb=recipedb.getText();
        String recipepathth=recipepath.getText();
        String tim=times.getText();
        String secc=sectionstf.getText();
        String mach=machines.getText();
        String whh=workinghourstf.getText();
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String themo_file=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady";
        PrintWriter pp=new PrintWriter (new FileWriter (settingsfile));
        pp.println("DB="+dbb);
        pp.println("Recipe_DB="+recdb);
        pp.println("Recipe_Path="+recipepathth);
        pp.println("Times="+tim);
        pp.println("Sections_File="+secc);
        pp.println("Machines_File="+mach);
        pp.print("Working_Hours="+whh);
        
        //Continue Saving
        
        pp.close();
        
        PrintWriter ppp=new PrintWriter (new FileWriter (themo_file));
        
        if (themo.isSelected()==true) {
          ppp.print("cupertino-light.css");  //Light Theme.
        }
        
        else {
          ppp.print("cupertino-dark.css");   //Dark Theme.
        }
        
          ppp.close();
        
        
        logarea.appendText("We have applied the settings successfully.");
        Notifications noti = Notifications.create();
        noti.title("Successful");
        noti.text("We have applied the settings successfully.");
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
          this.pst.setString(4, seluserr+" applied the settings successfully.");
          this.pst.execute();
              }
              catch (Exception e) {
          
        } finally {
          try {
            this.rs.close();
            this.pst.close();
          } catch (Exception exception) {}
        }  
      
//      ///////////////////////////////////////////////////////
//          
//      // Example: wait 3 seconds then run code
//PauseTransition pauset = new PauseTransition(Duration.seconds(2));
//pauset.setOnFinished(eventy -> {
//    
//    Stage jk = (Stage)this.save.getScene().getWindow();
//    jk.close();
//    
//});
//pauset.play();
//      
//      
        
        
    }
    
    
    
    
    
    

    @FXML
    void saveaction(ActionEvent event) throws IOException {

        //Save Data about DB and Times
        
        String dbb=systemdb.getText();
        String recdb=recipedb.getText();
        String recipepathth=recipepath.getText();
        String tim=times.getText();
        String secc=sectionstf.getText();
        String mach=machines.getText();
        String whh=workinghourstf.getText();
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String themo_file=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady";
        PrintWriter pp=new PrintWriter (new FileWriter (settingsfile));
        pp.println("DB="+dbb);
        pp.println("Recipe_DB="+recdb);
        pp.println("Recipe_Path="+recipepathth);
        pp.println("Times="+tim);
        pp.println("Sections_File="+secc);
        pp.println("Machines_File="+mach);
        pp.print("Working_Hours="+whh);
        
        //Continue Saving
        
        pp.close();
        
        PrintWriter ppp=new PrintWriter (new FileWriter (themo_file));
        
        if (themo.isSelected()==true) {
          ppp.print("cupertino-light.css");  //Light Theme.
        }
        
        else {
          ppp.print("cupertino-dark.css");   //Dark Theme.
        }
        
          ppp.close();
        
        
        logarea.appendText("We have updated the settings successfully.");
        Notifications noti = Notifications.create();
        noti.title("Successful");
        noti.text("We have updated the settings successfully.");
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
          this.pst.setString(4, seluserr+" updated the settings successfully.");
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
          
      // Example: wait 3 seconds then run code
PauseTransition pauset = new PauseTransition(Duration.seconds(2));
pauset.setOnFinished(eventy -> {
    
    Stage jk = (Stage)this.save.getScene().getWindow();
    jk.close();
    
});
pauset.play();
      
      
        
        
    }

    @FXML
    void test1action(ActionEvent event) {

        try {
         Class.forName("org.sqlite.JDBC");
         Connection conn = DriverManager.getConnection("jdbc:sqlite:"+systemdb.getText());
         if (conn != null) {
         logarea.clear();
         logarea.appendText("\nSystem DataBase Connected Successfully.\n");
         }
         } catch (Exception var1) {
         logarea.clear();
         logarea.appendText("\nConnection Failed!\n");
         }
        
    }

    @FXML
    void test2action(ActionEvent event) {

        try {
         Class.forName("org.sqlite.JDBC");
         Connection conn = DriverManager.getConnection("jdbc:sqlite:"+recipedb.getText());
         if (conn != null) {
         logarea.clear();
         logarea.appendText("\nRecipe DataBase Connected Successfully.\n");
         }
         } catch (Exception var1) {
         logarea.clear();
         logarea.appendText("\nConnection Failed!\n");
         }
        
    }
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        //Read All Here.
        
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
          conn = db.java_db();
        
        String settingspath=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning";
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        File f1=new File (settingspath);
        File f2=new File (settingsfile);
        if (!f1.exists()) {
            f1.mkdir();
        }
        if (!f2.exists()) {
            try {
             f2.createNewFile();
             
             try {
             PrintWriter pw1=new PrintWriter (new FileWriter (settingsfile));
             pw1.print("DB=\n" +
             "Recipe_DB=\n" +
             "Recipe_Path=\n" +
             "Times=\n"
                     + "Sections_File=\n"
                     + "Machines_File");
             pw1.close();
         } catch (IOException ex) {
         Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
         }
             
            } catch (IOException ex) {
            }
        }
        
        
        
        
        
        
        
        
         try {
             String themo_filey=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady";
             
             BufferedReader nee=new BufferedReader (new FileReader (themo_filey));
             String asa=nee.readLine();
             
             if (asa.equals("cupertino-light.css")) {
                 themo.setSelected(true);
             }
             
             else {
                 themo.setSelected(false);
             }
             
             
         } catch (IOException ex) {
             Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
         //Read 
         
       
        String filePath = settingsfile;
        try {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
        if (line.startsWith("DB=")) {
        String value = line.split("=", 2)[1];
        systemdb.setText(value);
        break;
        }}
        
        for (String line : lines) {
        if (line.startsWith("Recipe_DB=")) {
        String value = line.split("=", 2)[1];
        recipedb.setText(value);
        break;
        }}
        
        for (String line : lines) {
        if (line.startsWith("Recipe_Path=")) {
        String value = line.split("=", 2)[1];
        recipepath.setText(value);
        break;
        }}
        
        for (String line : lines) {
        if (line.startsWith("Times=")) {
        String value = line.split("=", 2)[1];
        times.setText(value);
        break;
        }}
        
        for (String line : lines) {
        if (line.startsWith("Sections_File=")) {
        String value = line.split("=", 2)[1];
        sectionstf.setText(value);
        //Read in Sections Here
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = value;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        sections.setItems(liness);
        
        break;
        }}
        
        for (String line : lines) {
        if (line.startsWith("Machines_File=")) {
        String value = line.split("=", 2)[1];
        machines.setText(value);
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = value;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        values.setItems(liness);
        
        break;
        }}
        
        for (String line : lines) {
        if (line.startsWith("Working_Hours=")) {
        String value = line.split("=", 2)[1];
        workinghourstf.setText(value);
        
        ObservableList<String> liness = FXCollections.observableArrayList();
        String filePathk = value;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathk))) {
        String lineg;
        while ((lineg = reader.readLine()) != null) {
        liness.add(lineg);
        }
        } catch (IOException e) {
        e.printStackTrace();}
        valuesss.setItems(liness);
        
        
        break;
        }}
        
        } catch (IOException e) {e.printStackTrace();}
         
         
        //Read Sections And Times
        
        
        
       selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;
        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;
         
        
    }    
    
}
