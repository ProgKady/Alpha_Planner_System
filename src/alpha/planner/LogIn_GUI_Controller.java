package alpha.planner;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

public class LogIn_GUI_Controller  implements Initializable {
  @FXML
  private ResourceBundle resources;
  
  Timer fileCheckTimer;
  
  @FXML
  private URL location;
  
  @FXML
  private ComboBox positionbox;
  
  
  @FXML
  private ComboBox namefield;
  
  @FXML
  private JFXPasswordField passwordfield;
  
  @FXML
  private JFXButton loginbtn;
  
  
  
  
  Connection conn = null;
  
  ResultSet rs = null;
  
  PreparedStatement pst = null;
  
  public static String theuserr,passwordy,selecteduser,selectedpositionn;

  
  
  
   @FXML
    void pospos(KeyEvent eventt) {

    KeyCode keycodee = eventt.getCode();
    if (keycodee == KeyCode.UP||keycodee == KeyCode.DOWN) {
        
        String sql = "SELECT User FROM USERS WHERE Position = ?";
try {
    // Clear the usersbox before adding new entries
    namefield.getItems().clear();
    String selectedPosition = positionbox.getSelectionModel().getSelectedItem().toString();
    selectedpositionn=selectedPosition;
    this.pst = this.conn.prepareStatement(sql);
    this.pst.setString(1, selectedPosition);
    this.rs = this.pst.executeQuery();
    while (this.rs.next()) {
        String userName = this.rs.getString("User");
        namefield.getItems().add(userName); // Add each name to usersbox
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
        
    }

  
  
  
  
  @FXML
  void enterpasswordkeypressed(KeyEvent event) {
    KeyCode keycode = event.getCode();
    if (keycode == KeyCode.ENTER)
      this.loginbtn.fire(); 
  }
  
    @FXML
    void positionaction(Event event) {

            
String sql = "SELECT User FROM USERS WHERE Position = ?";
try {
    // Clear the usersbox before adding new entries
    namefield.getItems().clear();
    String selectedPosition = positionbox.getSelectionModel().getSelectedItem().toString();
    selectedpositionn=selectedPosition;
    this.pst = this.conn.prepareStatement(sql);
    this.pst.setString(1, selectedPosition);
    this.rs = this.pst.executeQuery();
    while (this.rs.next()) {
        String userName = this.rs.getString("User");
        namefield.getItems().add(userName); // Add each name to usersbox
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
  void loginbtnaction(ActionEvent event) throws IOException {
      
    if (this.namefield.getSelectionModel().getSelectedItem()==null) {
      Image img = new Image(getClass().getResourceAsStream("kadysoft.png"));
      ImageView imgview = new ImageView();
      imgview.setImage(img);
      Notifications noti = Notifications.create();
      noti.title("LogIn Error");
      noti.text("Username Field is empty.");
      noti.hideAfter(Duration.minutes(1.0D));
      noti.graphic(imgview);
      noti.position(Pos.CENTER);
      noti.show();
     
   
    } else if (this.passwordfield.getText().equals("")) {
      Image img = new Image(getClass().getResourceAsStream("kadysoft.png"));
      ImageView imgview = new ImageView();
      imgview.setImage(img);
      Notifications noti = Notifications.create();
      noti.title("LogIn Error");
      noti.text("Password Field is empty.");
      noti.hideAfter(Duration.minutes(1.0D));
      noti.graphic(imgview);
      noti.position(Pos.CENTER);
      noti.show();
    
      
    } 
    
    else {
        
theuserr = namefield.getSelectionModel().getSelectedItem().toString();


try {
    String sql = "SELECT * FROM USERS WHERE User = ? AND Position = ?";
    pst = conn.prepareStatement(sql);
    pst.setString(1, theuserr);
    pst.setString(2, selectedpositionn);
    rs = pst.executeQuery();

    if (rs.next()) {
        String dbPassword = rs.getString("Password");

        if (passwordfield.getText().equals(dbPassword)) {
            // ✅ Login success
            Image img = new Image(getClass().getResourceAsStream("kadysoft.png"));
            ImageView imgview = new ImageView(img);

            Notifications noti = Notifications.create()
                .title("LogIn")
                .text("Successful Login")
                .hideAfter(Duration.minutes(1))
                .graphic(imgview)
                .position(Pos.CENTER);

            noti.show();

            selecteduser = theuserr;
      
      //Open FXML here related to user..
      //FXML file name==Position or section selected name
      
      
      if (selectedpositionn.equals("ADMIN")) {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"ADMIN"+".fxml"));
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
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      //stg.setMaximized(true);
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                
          
                System.exit(0);
                
                
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
      else if (selectedpositionn.equals("Alpha_Planner")) {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"Alpha_Planner"+".fxml"));
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
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.setMaximized(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                System.exit(0);
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
      
      
      
      
      
      
      
      
        else if (selectedpositionn.equals("Data_Entry")) {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"AddOrder"+".fxml"));
      Scene sce = new Scene(root);
//      //////////////////////////////Theme////////////////////////////////
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
//    ////////////////////////////////////////////////////////////////////
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.setMaximized(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                System.exit(0);
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
      
      
      
      
      
      
      
      
      else if (selectedpositionn.equals("WAREHOUSE_1")) {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"WAREHOUSE_1"+".fxml"));
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
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.setMaximized(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                System.exit(0);
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
      
         else if (selectedpositionn.equals("WAREHOUSE_2")) {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"WAREHOUSE_2"+".fxml"));
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
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.setMaximized(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                System.exit(0);
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
      
        else if (selectedpositionn.equals("Manager")) {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"Manager"+".fxml"));
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
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.setMaximized(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                System.exit(0);
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
      
      
      
      else {
          
      Stage stg = new Stage();
      Parent root = FXMLLoader.<Parent>load(getClass().getResource(""+"WASHING"+".fxml"));
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
      stg.setTitle(""+selectedpositionn+"   -   Logged In By:   "+selecteduser);
      stg.centerOnScreen();
      stg.setResizable(true);
      stg.setMaximized(true);
      stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
      stg.setScene(sce);
      stg.centerOnScreen();
      stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                

                //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
                System.exit(0);
                
              
            }

      });
      //stg.getIcons().add(new Image(LogIn_GUI.class.getResourceAsStream("washing.png")));
      stg.show();
      Stage jk = (Stage)this.loginbtn.getScene().getWindow();
      jk.close();
      //Online
      try {
    String sqla = "UPDATE USERS SET Status = 'Online' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selectedpositionn);
          pst.setString(2, selecteduser);
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
      
    
      //Audit Here.
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
          this.pst.setString(2, selectedpositionn);
          this.pst.setString(3, selecteduser);
          this.pst.setString(4, selecteduser+" has logged in successfully.");
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
     
            
            
            
        } else {
           
            
             //Failed
      Image img = new Image(getClass().getResourceAsStream("kadysoft.png"));
      ImageView imgview = new ImageView();
      imgview.setImage(img);
      Notifications noti = Notifications.create();
      noti.title("LogIn");
      noti.text("Unsuccessful Login, please try again later!!!.");
      noti.hideAfter(Duration.minutes(1.0D));
      noti.graphic(imgview);
      noti.position(Pos.CENTER);
      noti.show();
      selecteduser=theuserr;
      
       //Audit Here.
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
          this.pst.setString(2, selectedpositionn);
          this.pst.setString(3, selecteduser);
          this.pst.setString(4, selecteduser+" didn't login successfully. (Unsuccessful login)");
          this.pst.execute();
              }
              catch (Exception e) {
          
        } finally {
          try {
            this.rs.close();
            this.pst.close();
          } catch (Exception exception) {}
        }  
      
            
        }
    } else {
        System.out.println("User not found.");
    }
} catch (Exception exception) {
    exception.printStackTrace();
} finally {
    try { if (rs != null) rs.close(); } catch (Exception e) {}
    try { if (pst != null) pst.close(); } catch (Exception e) {}
}

   
   
    
    ////////////////////////////////////////////////////////////////////////
                   
    }
  }
  
  
  public void initialize(URL url, ResourceBundle rb) {
      
        try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 18);
        } catch (FileNotFoundException ex) {
          
        }
       
       
    this.positionbox.getItems().clear();
    try {
      BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Positions.kady"));
      String line;
      while ((line = buf.readLine()) != null) {
        this.positionbox.getItems().addAll(new String[] { line });
      } 
      buf.close();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (IOException iOException) {}
  
              
    //this.positionbox.getItems().addAll(new Object[] { "ADMIN","WAREHOUSE_1", "WASHING", "LASER", "SPRAY", "Alpha_Planner" }); // For Me Only. //Developer
    //this.positionbox.getSelectionModel().select(0);
    this.conn = db.java_db();
    this.positionbox.requestFocus();
    
    
    
 
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
    
    
    
  
  
}