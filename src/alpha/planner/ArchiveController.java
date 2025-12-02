
package alpha.planner;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class ArchiveController implements Initializable {

   
    
    @FXML
    private TableView<ObservableList<String>> table;
    Connection conn = null ;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public static String ppo,ssapcode,sstyle,ccustomer,wwashname,ppoamount,ccuttingamount,llaundrydate,xxfacdate;
    @FXML
    private ImageView refresh;

    public static String selsecc,seluserr;
    
    public static String diro;
    
   
    
     @FXML
    void resaction(MouseEvent event) {

        //Add to orders
     
    
           //Save To Database......................................
        
        if (ppo.isEmpty()||ssapcode.isEmpty()||sstyle.isEmpty()||ccustomer.isEmpty()||wwashname.isEmpty()||ppoamount.isEmpty()||ccuttingamount.isEmpty()||llaundrydate.isEmpty()||xxfacdate.isEmpty()) {
            
        Alert al=new Alert (AlertType.ERROR);
        al.setTitle("Fatal Error");
        al.setHeaderText("Fatal Error");
        al.setContentText("Please, Fill all info fields first.");
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
        
        else {
            
            //////////////////////////////Continue//////////////////////////////////////////////
            Date currentDate = GregorianCalendar.getInstance().getTime();
            DateFormat df = DateFormat.getDateInstance();
            String dateString = df.format(currentDate);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeString = sdf.format(d);
            String dayoftoday = timeString;
            
            try{
                
                
String reg = "INSERT INTO Orders (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, Incoming_Date, X_Fac_Date) VALUES (?,?,?,?,?,?,?,?,?)";
PreparedStatement pst = conn.prepareStatement(reg);
pst.setString(1, ppo);   // Change index from 0 to 1
pst.setString(2, ssapcode);
pst.setString(3, sstyle);
pst.setString(4, ccustomer);
pst.setString(5, wwashname);  // Ensure `order` is not a reserved keyword
pst.setString(6, ppoamount);
pst.setString(7, ccuttingamount);
pst.setString(8, llaundrydate);
pst.setString(9, xxfacdate);

int rowsInserted = pst.executeUpdate();  // Use executeUpdate() instead
                
    if (rowsInserted > 0) {
    Notifications noti = Notifications.create();
    noti.title("Successful");
    noti.text("Successful Restoration");
    noti.hideAfter(Duration.seconds(3));
    noti.position(Pos.CENTER);
    noti.showInformation();
} else {
     
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful Restoration");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
            
}   
               
              
                
            }
            catch (Exception e) 

            {
           
            }
            finally {
            
            try{
                rs.close();
                pst.close();
                
            }
            catch(Exception e){
                
            }
        }
        } 
            
            
            //Delete from Archive
            
             try {
  
            String sql = "delete from Archived_Orders where PO='"+ppo+"' and Sap_Code='"+ssapcode+"' and Style='"+sstyle+"' and Customer='"+ccustomer+"' and Wash_Name='"+wwashname+"' and PO_Amount='"+ppoamount+"' and Cutting_Amount='"+ccuttingamount+"' and Incoming_Date='"+llaundrydate+"' and X_Fac_Date='"+xxfacdate+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            
                 
              }
              catch (Exception e) {
          
        } finally {
          try {
            this.rs.close();
            this.pst.close();
          } catch (Exception exception) {}
        }  
            
        
             
          
             table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Archived_Orders";
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
    void exaction(MouseEvent event) throws FileNotFoundException, IOException {

           org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook();
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
        
        
    }
    
    
    
    @FXML
    void delaction(MouseEvent event) {

        
        
        
        //Delete
        
        
        
            String pol=  ppo;
            String sapcodel= ssapcode;
            String stylel=  sstyle;
            String ordernamel= wwashname;
            String customerrr= ccustomer;
            
            String e1= ppoamount;
            String e2= ccuttingamount;
            String e3= llaundrydate;
            String e4= xxfacdate;
        
        
         if (ordernamel.isEmpty()==true) {
              
              Notifications noti = Notifications.create();
              noti.title("طلب غير ناجح");
              noti.text("لم يتم الحذف.\nلو سمحت حدد اوردر علشان امسحه.");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
              
          }
         
         
         else {
             
                  
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("حذف اوردر");
      alert.setHeaderText("أنت عاوز بجد تمسح الاوردر ده؟\n\nخد بالك مش هتقدر ترجعه تاني خالص");
      alert.setContentText("Order Name is: "+ordernamel+"\n"+"Order PO is: "+pol+"\n"+"Order SapCode is: "+sapcodel+"\n"+"Order Style is: "+stylel+"\n"+"Order Customer is: "+customerrr+"\n"+"Order PO Amount is: "+e1+"\n"+"Order Cutting Amount is: "+e2+"\n"+"Order Laundry Date is: "+e3+"\n"+"Order X Fac Date is: "+e4);
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
      // option != null.
      Optional<ButtonType> option = alert.showAndWait();
      if (option.get() == null) {
         
      } else if (option.get() == ButtonType.OK) {
          
          
            try {
            
            String sql = "delete from Archived_Orders where PO='"+pol+"' and Sap_Code='"+sapcodel+"' and Style='"+stylel+"' and Customer='"+customerrr+"' and Wash_Name='"+ordernamel+"' and PO_Amount='"+e1+"' and Cutting_Amount='"+e2+"' and Incoming_Date='"+e3+"' and X_Fac_Date='"+e4+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            
              Notifications noti = Notifications.create();
              noti.title("طلب ناجح");
              noti.text("تم الحذف بنجاح يا معلم");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showInformation();
              
            
                                          
////////////////////////////////////////////////////////////
         
                    try {
    final Path path = Paths.get(diro + "\\Java\\bin\\Alpha_Logs.kady");
    Files.write(path, Arrays.asList(seluserr+" from "+selsecc+" has deleted an order permanently:\n"+"PO='"+pol+"'\nSap_Code='"+sapcodel+"'\nStyle='"+stylel+"'\nCustomer='"+customerrr+"'\nWash_Name='"+ordernamel+"'\nPO_Amount='"+e1+"'\nCutting_Amount='"+e2+"'\nIncoming_Date='"+e3+"'\nX_Fac_Date='"+e4+"'\n\n"), StandardCharsets.UTF_8,
        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
} catch (final IOException ioe) {
    // Add your own exception handling...
}
                    
////////////////////////////////////////////////////////////


            
              
            }catch(Exception e){
              Notifications notiv = Notifications.create();
              notiv.title("طلب غير ناجح");
              notiv.text("مفيش حاجة اتمسحت يا معلم");
              notiv.hideAfter(Duration.seconds(3));
              notiv.position(Pos.CENTER);
              notiv.showError();
            }
            finally {
            
            try{
                rs.close();
                pst.close();
                
            }
            catch(Exception e){
                
            }
        }
    
          
         
            
             
           
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
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" deleted an order permanently and its data is: Customer is: "+customerrr+", Order name is: "+ordernamel+" and Style is: "+stylel);
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
      else if (option.get() == ButtonType.CANCEL) {
      Notifications noti = Notifications.create();
      noti.title("عاوز تكنسل");
      noti.text("العملية اتلغت والاوردر زي ما هو موجود");
      noti.position(Pos.CENTER);
      noti.showInformation();
      
      }else {}
        
        
       
             
         }
        
        
         
         
         table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Archived_Orders";
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
          
         
         
         
        ///////////////////////////////
        
    }

   

    @FXML
    void refaction(MouseEvent event) {

        table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Archived_Orders";
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

   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
    conn = db.java_db();
    
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
            
            String sql ="select * from Archived_Orders";
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

        
        
    }    
    
       // Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}
    
}
