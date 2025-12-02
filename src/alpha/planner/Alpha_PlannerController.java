
package alpha.planner;

import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.Desktop;
import java.awt.Font;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class Alpha_PlannerController implements Initializable {

    Timer fileCheckTimer;
    
    Connection conn = null;
  
    ResultSet rs = null;
  
    PreparedStatement pst = null;
    
    @FXML
    private MenuItem refresh,deleteorder,updateorder,addorder;
    
    @FXML
    private JFXButton plansel,clearsel;

    @FXML
    private TableView<ObservableList<String>> table;
    
    private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    static String databaseUrl; // Path to your SQLite DB
    private ObservableList<String> columnNames = FXCollections.observableArrayList();

     @FXML
    private JFXTextField po;

    @FXML
    private JFXTextField sapcode;

    @FXML
    private JFXTextField style;

    @FXML
    private JFXTextField customer;

    @FXML
    private JFXTextField washname;
    
    @FXML
    private JFXTextField poamount;
    
    @FXML
    private JFXTextField linenumber;

    @FXML
    private JFXTextField cuttingamount;

    @FXML
    private JFXTextField laundrydate;

    @FXML
    private JFXTextField xfacdate;
    
    @FXML
    private JFXTextArea orderarea;
    
    public static String selsecc,seluserr;
    
     @FXML
    private Label ref;

    @FXML
    private Label upd;

    @FXML
    private Label add,plannedorders,skippedorders,choosedorders;

    @FXML
    private Label fil;

    @FXML
    private Label del;
    
    final private String plan_template="";
    
    static String vvaall;
    
    static String vaa,fgg,proco,timo,timooo,fggf;
    
    
    @FXML
    private ImageView rightarrow;

    @FXML
    private ImageView leftarrow;

   
    
     @FXML
    private ToolBar buttonbar;
     
     @FXML
    private TableView<ObservableList<String>> table2;
    
    private ObservableList<ObservableList<String>> dataaaaa;
    
    public static int oro=0;
    
    public static int alloro=0;
    
    public static int ski=0;
    
    public static int allski=0;
    
    public static String erra="";
    
    public static String allerra="";
    
    private Timeline refreshTimeline2;
    @FXML
    private Menu onlineusersmenu;
     
     
    
    
      @FXML
  void bonusaction(ActionEvent event) throws IOException {
      
      ExcelLikeTableApp nnc=new ExcelLikeTableApp();
      nnc.start(new Stage());
      
  }
    
    
    
    
    @FXML
  void logoutaction(ActionEvent event) throws IOException {
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("LogIn_GUI.fxml"));
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
    stg.setScene(sce);
    stg.centerOnScreen();
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
    Stage jk = (Stage)this.del.getScene().getWindow();
    jk.close(); 
    
  }
  
    
    
      @FXML
    void rightarrowaction(MouseEvent event) {

        
        table.setVisible(false);
        buttonbar.setVisible(false);
        
        table2.setVisible(true);
        leftarrow.setVisible(true);
        
        
    }
    
     @FXML
    void leftarrowaction(MouseEvent event) {

        table.setVisible(true);
        buttonbar.setVisible(true);
        
        table2.setVisible(false);
        leftarrow.setVisible(false);
        
        
    }
    
    @FXML
    void aboutaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("About.fxml"));
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
    stg.setTitle("About");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.setScene(sce);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setAlwaysOnTop(true);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(0.4);
        
    }

    
    
    @FXML
    void addorderaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("AddOrder.fxml"));
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
    stg.setTitle("Add Order");
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
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(0.4);
        
    }

    
    
    @FXML
    void clearselectionaction(ActionEvent event) {

    table.getSelectionModel().clearSelection();
    del.setVisible(false);
        
        
    }

    
    
    @FXML
    void creditsaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Credits.fxml"));
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
    stg.setTitle("Credits");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.setScene(sce);
    stg.setAlwaysOnTop(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(0.4);
    
        
    }

    
    
    
    @FXML
    void excelaction(ActionEvent event) throws FileNotFoundException, IOException {

        /////////////////////////////////////////////////////////////////////////
    
        if (table.isVisible()==true) {
            
            
        /////////////////////////////////////////////////////////////////////////
        
        Workbook workbook = new XSSFWorkbook();
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
        
        /////////////////////////////////////////////////////////////////////////
        
            
            
        }
        
        
        else {
            
            
        /////////////////////////////////////////////////////////////////////////
        
        Workbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("All_Selected_Plans");
        Row row = spreadsheet.createRow(0);
        for (int j = 0; j < table2.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table2.getColumns().get(j).getText());
        }
        for (int i = 0; i < table2.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table2.getColumns().size(); j++) {
                if(table2.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table2.getColumns().get(j).getCellData(i).toString()); 
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
        
        /////////////////////////////////////////////////////////////////////////
        
            
        }
        
        /////////////////////////////////////////////////////////////////////////
        
        
    }

    
    
    @FXML
    void pdfaction(ActionEvent event) throws IOException {

          ////////////////Create PDF///////////////
           if (table.isVisible()==true) {
            
              ////////////////////////////Start Report//////////////////////////////
        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String timeString = sdf.format(d);
    String value0 = timeString;
    String value00 = value0.replace("/", "_");
    String repname = "All_Selected_Orders_In_" + value00;
    String reppath = System.getProperty("user.home") + "\\Desktop";
    FileChooser dialog = new FileChooser();
    dialog.setInitialDirectory(new File(reppath));
    dialog.setInitialFileName(repname);
    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", new String[] { "*.pdf" }));
    File dialogResult = dialog.showSaveDialog(null);
    String filePath = dialogResult.getAbsolutePath().toString();
    try {
        
         String sql ="select * from Orders";
            
 
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
        
      DateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
      Date dd = new Date();
      String todate = dff.format(dd);
      Calendar cal = Calendar.getInstance();
      cal.add(5, -2);
      Date d1 = cal.getTime();
      String fromdate = dff.format(d1);
      com.itextpdf.text.Document myDocument = new com.itextpdf.text.Document();
      PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
      PdfPTable table = new PdfPTable(9);
      table.size();
      //table.setHorizontalAlignment(1);
      myDocument.open();
      float[] columnWidths = { 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F, 9.0F };
      table.setWidths(columnWidths);
      table.setWidthPercentage(100.0F);
      
      ColumnText.showTextAligned(myWriter.getDirectContentUnder(),
              
                Element.ALIGN_CENTER, new Phrase("T&C Garments By Kadysoft Ltd.", FontFactory.getFont("Times-Bold", 11.0F, 1)),
                297.5f, 421, myWriter.getPageNumber() % 2 == 1 ? 45 : -45);
      
      //myDocument.add((com.itextpdf.text.Element)new Paragraph("--------------------"));
      myDocument.add((com.itextpdf.text.Element)new Paragraph("Alpha Planner Orders Report", FontFactory.getFont("Times-Bold", 14.0F, 1)));
      myDocument.add((com.itextpdf.text.Element)new Paragraph(" "));
      table.addCell(new PdfPCell((Phrase)new Paragraph("PO", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Sap Code", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Style", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Customer", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Wash Name", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Po Amount", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Cutting Amount", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("Laundry Date", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      table.addCell(new PdfPCell((Phrase)new Paragraph("X Fac Date", FontFactory.getFont("Times-Roman", 9.0F, 1))));
      
      while (rs.next()) {
       // table.addCell(new PdfPCell(new Paragraph(rs.getString(0),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(7),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(8),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           table.addCell(new PdfPCell(new Paragraph(rs.getString(9),FontFactory.getFont(FontFactory.TIMES_ROMAN,7,Font.PLAIN))));
           
      } 
      myDocument.add((com.itextpdf.text.Element)table);
      myDocument.add((com.itextpdf.text.Element)new Paragraph("-------------------------------"));
      myDocument.setPageSize(PageSize.A4.rotate());
      myDocument.close();
      Alert alooo = new Alert(Alert.AlertType.CONFIRMATION);
      alooo.setTitle("Info");
      alooo.setHeaderText("Info!");
      alooo.setContentText("Report was generated successfully");
      alooo.setResizable(true);
      DialogPane dialogPaneef = alooo.getDialogPane();
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
      alooo.showAndWait();
    } catch (Exception e) {
    //  JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
       
      } catch (Exception e) {
     //   JOptionPane.showMessageDialog(null, e);
      } 
    } 
    Desktop de = Desktop.getDesktop();
    de.open(new File(reppath + "\\" + repname + ".pdf"));
    ////
        
        ////////////////////////////End Report////////////////////////////////

        
            
            
        }
        
        
        else {
            
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful export.\nWe canceled 'plan.pdf' this time, we hope add it in the future.");
              noti.hideAfter(Duration.seconds(5));
              noti.position(Pos.CENTER);
              noti.showError();
            
        }
        
       
        
        
    }
    
    
    
    
    @FXML
    void refreshaction(ActionEvent event) {

          
        table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Orders";
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
          //56  JOptionPane.showMessageDialog(null, e);
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
          editt();
          del.setVisible(false);
        
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
    stg.setScene(sce);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)plansel.getScene().getWindow();
    //jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)plansel.getScene().getWindow();
    //jk.setOpacity(0.4);
    
    
        
    }
    
    
    
    
    @FXML
    void showgarmentdetailsaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("GarmentDetails.fxml"));
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
    stg.setTitle("Garment Details");
    stg.centerOnScreen();
    stg.setResizable(false);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override
    public void handle(WindowEvent event) {
    //System.exit(0);
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(1);
    }});
    stg.show();
    Stage jk = (Stage)plansel.getScene().getWindow();
    jk.setOpacity(0.4);
        
        
    }
    
    
     
    @FXML
    void quitaction(ActionEvent event) {

        
         //Offline
                try {
    String sqla = "UPDATE USERS SET Status = 'Offline' WHERE Position = ? AND User = ?";
          pst = conn.prepareStatement(sqla);
          pst.setString(1, selsecc);
          pst.setString(2, seluserr);
          pst.executeUpdate();
              }
              catch (Exception e) {
          
        } finally {
          try {
            rs.close();
            pst.close();
          } catch (Exception exception) {}
        }  
        
        Platform.exit();
        
    }

    
    
    
    
    
    
    
    
//    @FXML
//    void tableaction(MouseEvent event) throws IOException {
//
//     // Get selected items from the TableView and display them in the TextArea
//ObservableList<ObservableList<String>> selectedItems = table.getSelectionModel().getSelectedItems();
//orderarea.clear();
//for (ObservableList<String> row : selectedItems) {
//    for (String cell : row) {
//        orderarea.appendText(cell + "\n");
//    }
//}
//
//// Save TextArea content to a temporary file
//File tempFile = new File(System.getProperty("user.home"), "Order.kady");
//try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
//    writer.print(orderarea.getText());
//} catch (IOException e) {
//    e.printStackTrace(); // Consider showing an alert instead
//    return;
//}
//
//// Read file content back and populate text fields
//try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
//    po.setText(reader.readLine());
//    sapcode.setText(reader.readLine());
//    style.setText(reader.readLine());
//    customer.setText(reader.readLine());
//    washname.setText(reader.readLine());
//    poamount.setText(reader.readLine());
//    cuttingamount.setText(reader.readLine());
//    laundrydate.setText(reader.readLine());
//    xfacdate.setText(reader.readLine());
//} catch (IOException e) {
//    e.printStackTrace(); // Consider handling file reading errors more gracefully
//} finally {
//    // Delete the temp file after use
//    tempFile.delete();
//}
//
//// Show the delete button (presumably for clearing or deleting order data)
//del.setVisible(true);
//        
//    }
    
     
   @FXML
void tableaction(MouseEvent event) {
    // Get the selected row
    ObservableList<String> selectedRow = table.getSelectionModel().getSelectedItem();

    // Clear existing content
    orderarea.clear();

    // If nothing is selected, hide delete button and exit
    if (selectedRow == null || selectedRow.isEmpty()) {
        del.setVisible(false);
        return;
    }

    // Fill the orderarea TextArea with row data
    for (String cell : selectedRow) {
        orderarea.appendText(cell + "\n");
    }

    // Safely populate text fields based on column index
    po.setText(getCelll(selectedRow, 0));
    sapcode.setText(getCelll(selectedRow, 1));
    style.setText(getCelll(selectedRow, 2));
    customer.setText(getCelll(selectedRow, 3));
    washname.setText(getCelll(selectedRow, 4));
    poamount.setText(getCelll(selectedRow, 5));
    cuttingamount.setText(getCelll(selectedRow, 6));
    laundrydate.setText(getCelll(selectedRow, 7));
    xfacdate.setText(getCelll(selectedRow, 8));

    // Show the delete button
    del.setVisible(true);
}

// Helper method to safely get a cell value from a row
private String getCelll(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}
    
    
    
    
    
    
    
    @FXML
    void deleteorderaction(ActionEvent event) {

      //Delete
        
        
        
        String pol=  po.getText();
            String sapcodel= sapcode.getText();
            String stylel=  style.getText();
            String ordernamel= washname.getText();
            String customerrr= customer.getText();
            
            String e1= poamount.getText();
            String e2= cuttingamount.getText();
            String e3= laundrydate.getText();
            String e4= xfacdate.getText();
        
        
         if (ordernamel.isEmpty()==true) {
              
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful Deletion.\nPlease choose an order first.");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
              
          }
         
         
         else {
             
                  
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Delete Order");
      alert.setHeaderText("Are you sure want to move this Order to the Recycle Bin?\n\nCaution: You can't undo or restore this order again.");
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
            
            String sql = "delete from Orders where PO='"+pol+"' and Sap_Code='"+sapcodel+"' and Style='"+stylel+"' and Customer='"+customerrr+"' and Wash_Name='"+ordernamel+"' and PO_Amount='"+e1+"' and Cutting_Amount='"+e2+"' and Incoming_Date='"+e3+"' and X_Fac_Date='"+e4+"' ";
            pst=conn.prepareStatement(sql);
            pst.execute();
            
              Notifications noti = Notifications.create();
              noti.title("Successful");
              noti.text("Successful Deletion");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showInformation();
            
              
            }catch(Exception e){
              Notifications notiv = Notifications.create();
              notiv.title("Unsuccessful");
              notiv.text("Unsuccessful Deletion");
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
    
          
            
            po.clear();
            sapcode.clear();
            style.clear();
            washname.clear();
            customer.clear();
            
            poamount.clear();
            cuttingamount.clear();
            laundrydate.clear();
            xfacdate.clear();
           
            
             refresh.fire();
             del.setVisible(false);
             
             
        
      
          
      }
      else if (option.get() == ButtonType.CANCEL) {
      Notifications noti = Notifications.create();
      noti.title("Cancel!");
      noti.text("Operation Cancelled, Order wasn't deleted.");
      noti.position(Pos.CENTER);
      noti.showInformation();
      del.setVisible(false);
      }else {del.setVisible(false);}
        
        
       
             
         }
        
        
        
        
        
        
    }
    
  

    
    
    @FXML
    void editorderaction(ActionEvent event) {

        saveDataToDatabase();
        
        
    }
    
    
    public void editt()   {
        
        
        
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the database
            try (Connection conn = DriverManager.getConnection(databaseUrl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {

                // Clear previous data
                table.getColumns().clear();
                data.clear();
                columnNames.clear();

                // Define the columns that should use ComboBox (example: column index 1)
                //Set<Integer> comboBoxColumns = Set.of(1); // Adjust as needed
                
                Set<Integer> comboBoxColumns = new HashSet<>();
                comboBoxColumns.add(1); // Add column index 1 as a ComboBox


                // Get column names
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int colIndex = i;
                    String colName = rs.getMetaData().getColumnName(i + 1);
                    columnNames.add(colName);

                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(colName);
                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));

                    // Check if the column should use a ComboBox
                    if (comboBoxColumns.contains(colIndex)) {
                        
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                       // column.setCellFactory(col -> new ComboBoxTableCell<>(
                           //     FXCollections.observableArrayList("Option 1", "Option 2", "Option 3")));
                    } else {
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                    }

                    column.setOnEditCommit(eventt -> {
                        ObservableList<String> row = eventt.getRowValue();
                        row.set(colIndex, eventt.getNewValue());
                    });

                    column.setContextMenu(createColumnContextMenu(colIndex));
                    table.getColumns().add(column);
                }

                // Get data rows
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }
                table.setItems(data);
                table.setEditable(true);

                // Add row context menu
                table.setRowFactory(tv -> {
                    TableRow<ObservableList<String>> row = new TableRow<>();
                    row.setContextMenu(createRowContextMenu(row));
                    return row;
                });

            }
        } catch (ClassNotFoundException e) {
          //  showAlert("Error", "SQLite JDBC driver not found.");
        } catch (SQLException e) {
          //  showAlert("Error", "Failed to load data from the database: " + e.getMessage());
        }
        
        
    }
    
    
    
     private ContextMenu createColumnContextMenu(int colIndex) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem changeColor = new MenuItem("Change Column Color");

        changeColor.setOnAction(e -> {
            ObservableList<String> selectedRow = table.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                JFXColorPicker colorPicker = new JFXColorPicker(Color.WHITE);
                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Select Column Color");
                dialog.getDialogPane().setContent(colorPicker);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();

                Color selectedColor = colorPicker.getValue();
                String colorStyle = "-fx-background-color: " + toRgbCode(selectedColor) + ";";
                TableColumn<ObservableList<String>, String> column = (TableColumn<ObservableList<String>, String>) table.getColumns().get(colIndex);
                column.setStyle(colorStyle);
            }
        });

        contextMenu.getItems().add(changeColor);
        return contextMenu;
    }

    
      private ContextMenu createRowContextMenu(TableRow<ObservableList<String>> row) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem changeRowColor = new MenuItem("Change Row Color");
        
        MenuItem showrecipe = new MenuItem("Show Recipe");
        MenuItem deleteorderr = new MenuItem("Delete Order");

        changeRowColor.setOnAction(e -> {
            JFXColorPicker colorPicker = new JFXColorPicker(Color.WHITE);
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Select Row Color");
            dialog.getDialogPane().setContent(colorPicker);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();

            Color selectedColor = colorPicker.getValue();
            String colorStyle = "-fx-background-color: " + toRgbCode(selectedColor) + ";";
            row.setStyle(colorStyle);
        });
        
        showrecipe.setOnAction(ey -> {
          
            
        
        
            String cust=customer.getText();
        String recname=washname.getText();
        String keyywordd1="PRODUCTION";
        String keyywordd2="PILOT";
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        List<String> lines;   
        try {
        lines = Files.readAllLines(Paths.get(settingsfile));
        for (String line : lines) {
        if (line.startsWith("Recipe_Path=")) {
        String value = line.split("=", 2)[1];
        vaa=value;
        break;
        }}
        } catch (IOException ex) {}
        
        String recipelink1=vaa+"\\"+keyywordd1+"\\"+cust+"\\"+recname+".ks";
        String recipelink2=vaa+"\\"+keyywordd2+"\\"+cust+"\\"+recname+".ks";
        
        File ff1=new File (recipelink1);  //PRODUCTION
        File ff2=new File (recipelink2);  //PILOT
        
        if (ff1.exists()) {
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
        //////////////////////////////////////Web View Alert ///////////////////////////////
    WebView webview=new WebView ();
    webview.setMinSize(1500, 650);
    orderarea.clear();
    InputStream inputinstream;
            try {
                inputinstream = new FileInputStream(recipelink1);
                BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        
        orderarea.appendText("\n"+lo
       .replace("ﬦ","A")
       .replace("ﬧ","B")
       .replace("ﬨ","C")
       .replace("﬩","D")
       .replace("שׁ","E")    
       .replace("שׂ","F")        
       .replace("שּׁ","G")         
       .replace("שּׂ","H")         
       .replace("אַ","I")         
       .replace("אָ","J")         
       .replace("אּ","K")         
       .replace("בּ","L")         
       .replace("גּ","M")         
       .replace("דּ","N")         
       .replace("הּ","O")         
       .replace("וּ","P")         
       .replace("זּ","Q")         
       .replace("טּ","R")         
       .replace("יּ","S")         
       .replace("ךּ","T")         
       .replace("כּ","U")         
       .replace("לּ","V")
       .replace("מּ","W")         
       .replace("נּ","X")         
       .replace("סּ","Y")         
       .replace("ףּ","Z")         
       .replace("פּ","0")         
       .replace("צּ","1")         
       .replace("קּ","2")         
       .replace("רּ","3")         
       .replace("שּ","4")         
       .replace("תּ","5")         
       .replace("וֹ","6")         
       .replace("בֿ","7")         
       .replace("כֿ","8")
       .replace("פֿ","9")
       .replace("&NBSP;","")        
                
               
      ); 


    }
    bi.close();
    
    String gf=orderarea.getText();
    //System.out.println(gf);
    OutputStream instreamm=new FileOutputStream(System.getProperty("user.home")+"\\"+recname+".html");
    PrintWriter pwe = new PrintWriter(new OutputStreamWriter (instreamm,"UTF-8"));
    pwe.println(gf);
    pwe.println("<script>\n" +
"  \n" +
"  window.addEventListener(`contextmenu`, (e) => {\n" +
"    e.preventDefault();\n" +
"});\n" +
"  \n" +
"  </script>");
    
    pwe.println("<script>\n" +
"            \n" +
"            document.addEventListener('keydown', event => {\n" +
"  console.log(`User pressed: ${event.key}`);\n" +
"  event.preventDefault();\n" +
"  return false;\n" +
"});\n" +
"            \n" +
"            </script>");
    
    pwe.close();
    orderarea.clear();
       
            } catch (FileNotFoundException ex) {
               // Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
              //  Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
          // إنشاء WebView وتحميل ملف HTML محلي
        WebView webviewt = new WebView();
        webviewt.setContextMenuEnabled(false);
        webviewt.setMinSize(1800, 800);
        webviewt.setZoom(1.0); // التكبير الافتراضي 100%

        String lkd = System.getProperty("user.home")+"\\"+recname+".html";
        URI uris = Paths.get(lkd).toAbsolutePath().toUri();
        webviewt.getEngine().load(uris.toString());

        // سلايدر للتحكم في الزوم من 10% إلى 200%
        Slider zoomSlider = new Slider(0.1, 2.0, 1.0);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(0.5);
        zoomSlider.setMinorTickCount(4);
        zoomSlider.setBlockIncrement(0.1);

        Label zoomLabel = new Label("Zoom: 100%");

        zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double zoom = newVal.doubleValue();
            webviewt.setZoom(zoom);
            zoomLabel.setText(String.format("Zoom: %.0f%%", zoom * 100));
        });

        // VBox يحتوي على WebView والسلايدر
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        VBox.setVgrow(webviewt, Priority.ALWAYS);
        vbox.getChildren().addAll(webviewt, zoomLabel, zoomSlider);

        // وضع VBox داخل GridPane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setAlignment(Pos.CENTER);
        gridpane.add(vbox, 0, 0);

        // إنشاء Alert لعرض المحتوى
        Alert alol = new Alert(Alert.AlertType.INFORMATION);
        alol.setTitle("Preview a recipe");
        DialogPane dialogPane = alol.getDialogPane();
        dialogPane.setContent(gridpane);
        alol.setResizable(true);
     
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
        alol.showAndWait();
        File nm=new File (System.getProperty("user.home")+"\\"+recname+".html");
        if ((System.getProperty("user.home")+"\\"+recname+".html").contains(".ks")) {
            nm.delete();
        }
        else {
            
        }
        
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    nm.delete();
        
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        
        else if (!ff1.exists()&&ff2.exists()) {
                 
        //////////////////////////////////////Web View Alert ///////////////////////////////
    WebView webview=new WebView ();
    webview.setMinSize(1500, 650);
    orderarea.clear();
    InputStream inputinstream;
            try {
                inputinstream = new FileInputStream(recipelink2);
                BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        
        orderarea.appendText("\n"+lo
       .replace("ﬦ","A")
       .replace("ﬧ","B")
       .replace("ﬨ","C")
       .replace("﬩","D")
       .replace("שׁ","E")    
       .replace("שׂ","F")        
       .replace("שּׁ","G")         
       .replace("שּׂ","H")         
       .replace("אַ","I")         
       .replace("אָ","J")         
       .replace("אּ","K")         
       .replace("בּ","L")         
       .replace("גּ","M")         
       .replace("דּ","N")         
       .replace("הּ","O")         
       .replace("וּ","P")         
       .replace("זּ","Q")         
       .replace("טּ","R")         
       .replace("יּ","S")         
       .replace("ךּ","T")         
       .replace("כּ","U")         
       .replace("לּ","V")
       .replace("מּ","W")         
       .replace("נּ","X")         
       .replace("סּ","Y")         
       .replace("ףּ","Z")         
       .replace("פּ","0")         
       .replace("צּ","1")         
       .replace("קּ","2")         
       .replace("רּ","3")         
       .replace("שּ","4")         
       .replace("תּ","5")         
       .replace("וֹ","6")         
       .replace("בֿ","7")         
       .replace("כֿ","8")
       .replace("פֿ","9")
       .replace("&NBSP;","")        
                
               
      ); 


    }
    bi.close();
    
    String gf=orderarea.getText();
    //System.out.println(gf);
    OutputStream instreamm=new FileOutputStream(System.getProperty("user.home")+"\\"+recname+".html");
    PrintWriter pwe = new PrintWriter(new OutputStreamWriter (instreamm,"UTF-8"));
    pwe.println(gf);
    pwe.println("<script>\n" +
"  \n" +
"  window.addEventListener(`contextmenu`, (e) => {\n" +
"    e.preventDefault();\n" +
"});\n" +
"  \n" +
"  </script>");
    
    pwe.println("<script>\n" +
"            \n" +
"            document.addEventListener('keydown', event => {\n" +
"  console.log(`User pressed: ${event.key}`);\n" +
"  event.preventDefault();\n" +
"  return false;\n" +
"});\n" +
"            \n" +
"            </script>");
    
    pwe.close();
    orderarea.clear();
       
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        URI uri = Paths.get(System.getProperty("user.home")+"\\"+recname+".html").toAbsolutePath().toUri();
        webview.getEngine().load(uri.toString());
        Alert alo = new Alert(Alert.AlertType.INFORMATION);
        alo.setTitle("Preview a recipe");
        alo.setGraphic(webview);
        alo.setResizable(false);
        DialogPane dialogPane = alo.getDialogPane();
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
        alo.showAndWait();
        File nm=new File (System.getProperty("user.home")+"\\"+recname+".html");
        if ((System.getProperty("user.home")+"\\"+recname+".html").contains(".ks")) {
            nm.delete();
        }
        else {
            
        }
        
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    nm.delete();
        
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        }
        
        else if (ff1.exists()&&ff2.exists()) {
                 
        //////////////////////////////////////Web View Alert ///////////////////////////////
    WebView webview=new WebView ();
    webview.setMinSize(1500, 650);
    orderarea.clear();
    InputStream inputinstream;
            try {
                inputinstream = new FileInputStream(recipelink1);
                BufferedReader bi=new BufferedReader (new InputStreamReader (inputinstream,"UTF-8"));
    String lo;
    while ((lo=bi.readLine())!=null) {
        
        orderarea.appendText("\n"+lo
       .replace("ﬦ","A")
       .replace("ﬧ","B")
       .replace("ﬨ","C")
       .replace("﬩","D")
       .replace("שׁ","E")    
       .replace("שׂ","F")        
       .replace("שּׁ","G")         
       .replace("שּׂ","H")         
       .replace("אַ","I")         
       .replace("אָ","J")         
       .replace("אּ","K")         
       .replace("בּ","L")         
       .replace("גּ","M")         
       .replace("דּ","N")         
       .replace("הּ","O")         
       .replace("וּ","P")         
       .replace("זּ","Q")         
       .replace("טּ","R")         
       .replace("יּ","S")         
       .replace("ךּ","T")         
       .replace("כּ","U")         
       .replace("לּ","V")
       .replace("מּ","W")         
       .replace("נּ","X")         
       .replace("סּ","Y")         
       .replace("ףּ","Z")         
       .replace("פּ","0")         
       .replace("צּ","1")         
       .replace("קּ","2")         
       .replace("רּ","3")         
       .replace("שּ","4")         
       .replace("תּ","5")         
       .replace("וֹ","6")         
       .replace("בֿ","7")         
       .replace("כֿ","8")
       .replace("פֿ","9")
       .replace("&NBSP;","")        
                
               
      ); 


    }
    bi.close();
    
    String gf=orderarea.getText();
    //System.out.println(gf);
    OutputStream instreamm=new FileOutputStream(System.getProperty("user.home")+"\\"+recname+".html");
    PrintWriter pwe = new PrintWriter(new OutputStreamWriter (instreamm,"UTF-8"));
    pwe.println(gf);
    pwe.println("<script>\n" +
"  \n" +
"  window.addEventListener(`contextmenu`, (e) => {\n" +
"    e.preventDefault();\n" +
"});\n" +
"  \n" +
"  </script>");
    
    pwe.println("<script>\n" +
"            \n" +
"            document.addEventListener('keydown', event => {\n" +
"  console.log(`User pressed: ${event.key}`);\n" +
"  event.preventDefault();\n" +
"  return false;\n" +
"});\n" +
"            \n" +
"            </script>");
    
    pwe.close();
    orderarea.clear();
       
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
          // إنشاء WebView وتحميل ملف HTML محلي
        WebView webviewt = new WebView();
        webviewt.setContextMenuEnabled(false);
        webviewt.setMinSize(1800, 800);
        webviewt.setZoom(1.0); // التكبير الافتراضي 100%

        String lkd = System.getProperty("user.home")+"\\"+recname+".html";
        URI uris = Paths.get(lkd).toAbsolutePath().toUri();
        webviewt.getEngine().load(uris.toString());

        // سلايدر للتحكم في الزوم من 10% إلى 200%
        Slider zoomSlider = new Slider(0.1, 2.0, 1.0);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(0.5);
        zoomSlider.setMinorTickCount(4);
        zoomSlider.setBlockIncrement(0.1);

        Label zoomLabel = new Label("Zoom: 100%");

        zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double zoom = newVal.doubleValue();
            webviewt.setZoom(zoom);
            zoomLabel.setText(String.format("Zoom: %.0f%%", zoom * 100));
        });

        // VBox يحتوي على WebView والسلايدر
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        VBox.setVgrow(webviewt, Priority.ALWAYS);
        vbox.getChildren().addAll(webviewt, zoomLabel, zoomSlider);

        // وضع VBox داخل GridPane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setAlignment(Pos.CENTER);
        gridpane.add(vbox, 0, 0);

        // إنشاء Alert لعرض المحتوى
        Alert alol = new Alert(Alert.AlertType.INFORMATION);
        alol.setTitle("Preview a recipe");
        DialogPane dialogPane = alol.getDialogPane();
        dialogPane.setContent(gridpane);
        alol.setResizable(true);
     
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
        alol.showAndWait();
        File nm=new File (System.getProperty("user.home")+"\\"+recname+".html");
        if ((System.getProperty("user.home")+"\\"+recname+".html").contains(".ks")) {
            nm.delete();
        }
        else {
            
        }
        
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WAREHOUSE_1_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    nm.delete();
        
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        }
        
        else if (!ff1.exists()&&!ff2.exists()) {
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful Request");
              noti.text("Unsuccessful Request, No recipe found or maybe name is incorrect.");
              noti.hideAfter(Duration.seconds(4));
              noti.position(Pos.CENTER);
              noti.showError();
        }
        
        
        
        
        
        
            
        });
        
        deleteorderr.setOnAction(e -> {
            deleteorder.fire();
        });

        contextMenu.getItems().addAll(changeRowColor,showrecipe,deleteorderr);
        return contextMenu;
    }

    private String toRgbCode(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    
    
     private void addRow() {
        ObservableList<String> newRow = FXCollections.observableArrayList();
        for (int i = 0; i < columnNames.size(); i++) {
            newRow.add(""); // Add empty values for each column
        }
        data.add(newRow);
    }

     
      private void saveDataToDatabase() {
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            try (Connection conn = DriverManager.getConnection(databaseUrl);
                 Statement stmt = conn.createStatement()) {

                conn.setAutoCommit(false); // Disable auto-commit for batch updates
                stmt.executeUpdate("DELETE FROM Orders"); // Clear existing data

                // Reinsert updated data
                for (ObservableList<String> row : data) {
                    StringBuilder values = new StringBuilder();
                    for (String cell : row) {
                        values.append("'").append(cell.replace("'", "''")).append("',");
                    }
                    values.setLength(values.length() - 1); // Remove trailing comma
                    String insertQuery = String.format("INSERT INTO Orders (%s) VALUES (%s)",
                            String.join(", ", columnNames), values.toString());
                    stmt.executeUpdate(insertQuery);
                }

                conn.commit(); // Commit changes
               // showAlert("Success", "Data saved successfully!");
               Notifications noti = Notifications.create();
    noti.title("Successful");
    noti.text("Successful Update");
    noti.hideAfter(Duration.seconds(3));
    noti.position(Pos.CENTER);
    noti.showInformation();
    
    refresh.fire();
    del.setVisible(false);

            }
        } catch (ClassNotFoundException e) {
         //   showAlert("Error", "SQLite JDBC driver not found.");
        } catch (SQLException e) {
          //  showAlert("Error", "Failed to save data to the database: " + e.getMessage());
              Notifications noti = Notifications.create();
              noti.title("Unsuccessful");
              noti.text("Unsuccessful update");
              noti.hideAfter(Duration.seconds(3));
              noti.position(Pos.CENTER);
              noti.showError();
              
              refresh.fire();
        }
    }
    
      
      
      
        @FXML
    void refaction(MouseEvent event) {
        
        refresh.fire();

    }
    
    
    
    @FXML
    void filaction(MouseEvent event) {
        
        
        table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from Orders";
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
            JOptionPane.showMessageDialog(null, e);
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
          //editt();
          del.setVisible(false);

    }
    
    
      @FXML
    void delaction(MouseEvent event) {
        
        deleteorder.fire();

    }
    
    
    @FXML
    void addaction(MouseEvent event) {
        
        addorder.fire();

    }
      
      
     @FXML
    void updaction(MouseEvent event) {

        updateorder.fire();
        
    }  
      
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    void planselectedaction(ActionEvent event) throws IOException, ClassNotFoundException {

        //Create Plan Here
        
        /*
        
        0: Show alert to choose plan selected order or plan all.
        1: Get Selected row and get order name.
        2: Search in DB about processes or get from recipe or show alert to write them if not found
        3: 
        
        */


if (table.getSelectionModel().getSelectedItem() != null) {
                
Alert alertd = new Alert(Alert.AlertType.CONFIRMATION);
alertd.setTitle("Create a plan!");
alertd.setHeaderText("Here we are, let's create a plan!");
alertd.setContentText("Do you wanna create a plan for : ......?");
ButtonType buttonTypeOned = new ButtonType("Selected One");
ButtonType buttonTypeCanceld = new ButtonType("All Filtered");
alertd.getButtonTypes().setAll(buttonTypeOned, buttonTypeCanceld);
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
//dialogPaneid.getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
Optional<ButtonType> resultsd = alertd.showAndWait();
if (resultsd.isPresent() && resultsd.get() == buttonTypeOned) {

//Selected One.

////////////////////////////START////////////////////////////////////

String wassh = washname.getText().trim();
String modo = customer.getText().trim();

// Load Recipe DB Path
String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
List<String> lines = Files.readAllLines(Paths.get(filePath));
for (String line : lines) {
    if (line.startsWith("Recipe_DB=")) {
        vvaall = line.split("=", 2)[1].trim();
        break;
    }
}

Platform.runLater(() -> {
    plannedorders.setText("1");
    choosedorders.setText("1");
    oro = 0;
    ski = 0;
    erra = "";
});

Set<String> processSteps = new LinkedHashSet<>();

Class.forName("org.sqlite.JDBC");
String url = "jdbc:sqlite:" + vvaall;
String query = "SELECT * FROM Recipe_Processes WHERE WashName LIKE ? AND Model LIKE ?";

System.out.println("\nProcesses\n");

try (Connection conn = DriverManager.getConnection(url)) {
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, "%" + wassh + "%");
        pstmt.setString(2, "%" + modo + "%");
        ResultSet rs = pstmt.executeQuery();
        boolean found = false;
        while (rs.next()) {
            found = true;
            String proc = rs.getString("Processes");
            String[] steps = proc.split(" - ");
            for (String step : steps) {
                step = step.trim();
                if (!step.isEmpty()) processSteps.add(step);
            }
        }

        if (!found) {
            Notifications noti = Notifications.create();
            noti.title("Fatal Error!");
            noti.text("No orders found matching the criteria. Ask KADINIO");
            noti.position(Pos.CENTER);
            noti.hideAfter(Duration.seconds(4));
            noti.showError();
            Platform.runLater(() -> {
                skippedorders.setText(Integer.toString(++ski));
                erra += "\nNo orders found matching the criteria. Ask KADINIO";
            });
        }
    }

    for (String step : processSteps) {
        System.out.println(step);
    }

    proco = String.join(" - ", processSteps);

    System.out.println("\n");

    ////////////////////// Time Query //////////////////////////

    String query2 = "SELECT * FROM Timer WHERE Name LIKE ? AND Model LIKE ? AND Shot LIKE ?";
    double totalTime = 0;
    boolean found2 = false;

    try (PreparedStatement pstmt2 = conn.prepareStatement(query2)) {
        pstmt2.setString(1, "%" + wassh + "%");
        pstmt2.setString(2, "%" + modo + "%");
        pstmt2.setString(3, "%1%");
        ResultSet rs2 = pstmt2.executeQuery();

        while (rs2.next()) {
            found2 = true;
            String proc2 = rs2.getString("Time_In_Min_Updated");
            if ("Hasnot_Updated_Yet".equals(proc2)) {
                proc2 = rs2.getString("Time_In_Min");
            }
            totalTime += Double.parseDouble(proc2);
        }
    }

    if (!found2) {
        // Try Shot 1 and Shot 2 from Timer_Three_Shots
        String[] shots = {"1", "2"};
        for (String shot : shots) {
            String queryShot = "SELECT Time_In_Min_Updated, Time_In_Min FROM Timer_Three_Shots WHERE Name LIKE ? AND Model LIKE ? AND Shot LIKE ?";
            try (PreparedStatement pstmtShot = conn.prepareStatement(queryShot)) {
                pstmtShot.setString(1, "%" + wassh + "%");
                pstmtShot.setString(2, "%" + modo + "%");
                pstmtShot.setString(3, "%" + shot + "%");
                ResultSet rsShot = pstmtShot.executeQuery();

                while (rsShot.next()) {
                    String procShot = rsShot.getString("Time_In_Min_Updated");
                    if ("Hasnot_Updated_Yet".equals(procShot)) {
                        procShot = rsShot.getString("Time_In_Min");
                    }
                    totalTime += Double.parseDouble(procShot);
                }
            }
        }
    }

    timo = String.valueOf(totalTime);

} catch (SQLException e) {
    System.out.println(e.getMessage());
    Platform.runLater(() -> {
        skippedorders.setText(Integer.toString(++ski));
        erra += "\nNo time for that order found. Ask KADINIO";
    });
}

/////////////////////// Machines or Workers /////////////////////////

System.out.println("\nMachines Or Workers\n");

String settingsfile = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
List<String> liness = Files.readAllLines(Paths.get(settingsfile));
for (String line : liness) {
    if (line.startsWith("Machines_File=")) {
        fgg = line.split("=", 2)[1].trim();
        break;
    }
}
Map<String, String> chemicalDictionary = loadChemicalDictionary(fgg);
if (chemicalDictionary.isEmpty()) {
    System.out.println("Dictionary is empty or not found!");
    return;
}

Set<String> handledSteps = new HashSet<>();
for (String step : processSteps) {
    if (!handledSteps.add(step)) continue;

    if (step.toLowerCase().contains("washing 1")) {
        String bestMatch = getBestMatch(step + " LINE " + linenumber.getText(), chemicalDictionary);
        System.out.println(step + " LINE " + linenumber.getText() + " -> " + bestMatch);
    } else if (!step.matches(".*WASHING [2-6].*")) {
        String bestMatch = getBestMatch(step, chemicalDictionary);
        System.out.println(step + " -> " + bestMatch);
    }
}

/////////////////////////// Sections Times ///////////////////////////

System.out.println("\nSections Times\n");

List<String> linesss = Files.readAllLines(Paths.get(settingsfile));
for (String line : linesss) {
    if (line.startsWith("Times=")) {
        timooo = line.split("=", 2)[1].trim();
        break;
    }
}

LevenshteinDistance levenshtein = new LevenshteinDistance();
int threshold = 1;

try (BufferedReader reader = new BufferedReader(new FileReader(timooo))) {
    List<String> linesu = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
        linesu.add(line);
    }

    Set<String> sectionSteps = new HashSet<>();
    for (String step : processSteps) {
        if (!sectionSteps.add(step)) continue;

        for (String record : linesu) {
            String[] parts = record.split("=");
            if (parts.length >= 3) {
                String key1 = parts[0].trim();
                String key2 = parts[1].trim();
                String value = parts[parts.length - 1].trim();

                if (wassh.equalsIgnoreCase(key1) && step.equalsIgnoreCase(key2)) {
                    System.out.println(step + " -- " + value);
                    break;
                }
                if (step.toLowerCase().contains("washing 1")) {
                    System.out.println(step + " -- " + timo);
                    break;
                }
                if (step.toLowerCase().contains("washing 2") || step.toLowerCase().contains("washing 3")) {
                    break;
                }

                int dist1 = levenshtein.apply(wassh.toUpperCase(), key1.toUpperCase());
                int dist2 = levenshtein.apply(step.toUpperCase(), key2.toUpperCase());

                if (dist1 <= threshold && dist2 <= threshold) {
                    System.out.println(step + " -- " + value);
                    break;
                }
            }
        }
    }
} catch (IOException e) {
    e.printStackTrace();
}

////////////////////////// Working Hours /////////////////////////////

System.out.println("\nWorking Hours\n");

for (String line : liness) {
    if (line.startsWith("Working_Hours=")) {
        fggf = line.split("=", 2)[1].trim();
        break;
    }
}
Map<String, String> chemicalDictionaryg = loadChemicalDictionary(fggf);
if (chemicalDictionaryg.isEmpty()) {
    System.out.println("Dictionary is empty or not found!");
    return;
}

Set<String> hourSteps = new HashSet<>();
for (String step : processSteps) {
    if (!hourSteps.add(step)) continue;

    if (step.toLowerCase().contains("washing 1")) {
        String bestMatch = getBestMatch(step + " LINE " + linenumber.getText(), chemicalDictionaryg);
        System.out.println(step + " LINE " + linenumber.getText() + " -> " + bestMatch);
    } else if (!step.matches(".*WASHING [2-6].*")) {
        String bestMatch = getBestMatch(step, chemicalDictionaryg);
        System.out.println(step + " -> " + bestMatch);
    }
}

/////////////////////// Final Result & Alert //////////////////////////

addRow(po.getText(), sapcode.getText(), style.getText(), customer.getText(), washname.getText(),
        poamount.getText(), cuttingamount.getText(), laundrydate.getText(), xfacdate.getText(),
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ");

Alert alooo = new Alert(Alert.AlertType.INFORMATION);
alooo.setTitle("Info");
//alooo.setHeaderText("Errors!");
alooo.setContentText(wassh + "\n\n" + (erra.isEmpty() ? "No errors found" : erra));
alooo.setResizable(true);
DialogPane dialogPaneef = alooo.getDialogPane();
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
alooo.showAndWait();

////////////////////////////END////////////////////////////////////



}













else {
    
Platform.runLater(() -> {
alloro=0;
allski=0;
allerra="";    
plannedorders.setText(Integer.toString(alloro));
skippedorders.setText(Integer.toString(allski));
choosedorders.setText(Integer.toString(table.getItems().size()));
});
    
    // Loop through all rows in the table
for (int i = 0; i < table.getItems().size(); i++) {
    
    int hjk=i;
    

    
    // Select row at index i
    table.getSelectionModel().select(i);

    // Get the selected row
    ObservableList<String> selectedRow = table.getSelectionModel().getSelectedItem();
    
    if (selectedRow != null) {
        orderarea.clear();
        orderarea.appendText(String.join("\n", selectedRow));
        
        // Assign values to text fields safely
        
        Platform.runLater(() -> {
plannedorders.setText(Integer.toString(hjk));
});
        
        int index = 0;
        po.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        sapcode.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        style.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        customer.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        washname.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        poamount.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        cuttingamount.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        laundrydate.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");
        xfacdate.setText(index < selectedRow.size() ? selectedRow.get(index++) : "");

        // Show delete button
        del.setVisible(true);

        // Print row data for debugging
        System.out.println("Row " + (i + 1) + ":");
        System.out.println("PO: " + po.getText());
        System.out.println("SAP Code: " + sapcode.getText());
        System.out.println("Style: " + style.getText());
        System.out.println("Customer: " + customer.getText());
        System.out.println("Wash Name: " + washname.getText());
        System.out.println("PO Amount: " + poamount.getText());
        System.out.println("Cutting Amount: " + cuttingamount.getText());
        System.out.println("Laundry Date: " + laundrydate.getText());
        System.out.println("X-Fac Date: " + xfacdate.getText());
        System.out.println("----");
        
        
        
        
  //////////////////////////////START////////////////////////////////////
        
        String wassh=washname.getText();
        String modo=customer.getText();
        //Connect to DB
        
        
        String filePath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";;
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
        if (line.startsWith("Recipe_DB=")) {
        vvaall = line.split("=", 2)[1];
        break;
        }} 

       

        
  //Processes Query      
        
Class.forName("org.sqlite.JDBC");
String url = "jdbc:sqlite:"+vvaall;
String query = "SELECT * FROM Recipe_Processes WHERE WashName LIKE ? AND Model LIKE ?";
String query2 = "SELECT * FROM Timer WHERE Name LIKE ? AND Model LIKE ? AND Shot LIKE ?";

try (Connection conn = DriverManager.getConnection(url)) {
    // First Query Execution
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, "%" + wassh + "%");
        pstmt.setString(2, "%" + modo + "%");
        ResultSet rs = pstmt.executeQuery();
        boolean found = false;
        while (rs.next()) {
            found = true;
            String proc = rs.getString("Processes");
            proco=proc;
            System.out.print(proc.replace(" - ", "\n"));
        }
        if (!found) {
            //System.out.println("No orders found matching the criteria.");
//            Notifications noti = Notifications.create();
//            noti.title("Fatal Error!");
//            noti.text("No orders found matching the criteria of "+wassh+". Ask KADINIO");
//            noti.position(Pos.CENTER);
//            noti.hideAfter(Duration.seconds(10));
//            noti.showError();
//    allerra=wassh+"\n"+allerra+"\n"+"No orders found matching the criteria of "+wassh+". Ask KADINIO";        
//    Platform.runLater(() -> {
//    skippedorders.setText(Integer.toString(allski+1));
//    });
            
        }
    } // rs and pstmt closed automatically

   System.out.println();
   System.out.println();
    //Time Query
    
    
    // Second Query Execution
    try (PreparedStatement pstmt2 = conn.prepareStatement(query2)) {
        pstmt2.setString(1, "%" + wassh + "%");
        pstmt2.setString(2, "%" + modo + "%");
        pstmt2.setString(3, "%1%");
        ResultSet rs2 = pstmt2.executeQuery();
        boolean found2 = false;
        while (rs2.next()) {
            found2 = true;
            String proc2 = rs2.getString("Time_In_Min_Updated");
            if (proc2.equals("Hasnot_Updated_Yet")) {
                String proc3 = rs2.getString("Time_In_Min");
                //System.out.println(proc3);
                timo=proc3;
            } else {
                //System.out.println(proc2);
                timo=proc2;
            }
        }
        if (!found2) {
            //System.out.println("No time for that order found.");
//            Notifications noti = Notifications.create();
//            noti.title("Fatal Error!");
//            noti.text("No time for that order found of "+wassh+". Ask KADINIO");
//            noti.position(Pos.CENTER);
//            noti.hideAfter(Duration.seconds(10));
//            noti.showError();
//    allerra=wassh+"\n"+allerra+"\n"+"No time for that order found. Ask KADINIO";        
//    Platform.runLater(() -> {
//    skippedorders.setText(Integer.toString(allski+1));
//    });
            
        }
    } // rs2 and pstmt2 closed automatically

} catch (SQLException e) {
    System.out.println(e.getMessage());
}

  
//Machines Or Workers Number

String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
List<String> liness = Files.readAllLines(Paths.get(settingsfile));
for (String line : liness) {   
if (line.startsWith("Machines_File=")) {
String value = line.split("=", 2)[1];
fgg=value;
break;
}}
Map<String, String> chemicalDictionary = loadChemicalDictionary(fgg);
if (chemicalDictionary.isEmpty()) {
System.out.println("Dictionary is empty or not found!");
return;
}         
String[] steps = proco.split(" - ");
for (String step : steps) {
if (step.contains("WASHING 1")||step.contains("washing 1")||step.contains("WASH 1")||step.contains("wash 1"))  {
    
if (linenumber.getText().equals("1")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("2")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("3")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("4")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("5")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("6")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("7")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionary);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else {
//String bestMatch = getBestMatch(step, chemicalDictionary);
//System.out.println(step + " -> " + bestMatch); 
System.out.println("No line found under this number "+linenumber.getText()); 
    }
 

}
else {
if (step.contains("WASHING 2")||step.contains("WASHING 3")||step.contains("WASHING 4")||step.contains("WASHING 5")||step.contains("WASHING 6"))  {
continue;   
}
else {
String bestMatch = getBestMatch(step, chemicalDictionary);
System.out.println(step + " -> " + bestMatch);     
}   
}
}



   System.out.println();
   System.out.println();

//Reading Times

String targetKey1 = wassh;   // First key (fuzzy match)
String[] stepss = proco.split(" - ");
//System.out.println(steppp);    
//String targetKey2 = steppp; // Second key (fuzzy match)
String settingsfilee=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
List<String> linesss = Files.readAllLines(Paths.get(settingsfilee));
for (String linee : linesss) {   
if (linee.startsWith("Times=")) {
String valuee = linee.split("=", 2)[1];
timooo=valuee;
break;
}}
String filePathtimo = timooo; // File path
// **Initialize fuzzy matching**
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        int threshold = 1; // Allow small typos
        // **Read and process the target file**
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathtimo))) {
            List<String> linesu = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                linesu.add(line);
            }
            // **Process each step in `proco`**
            for (String targetKey2 : stepss) {
                targetKey2 = targetKey2.trim(); // Trim spaces
                for (String record : linesu) {
                    String[] parts = record.split("="); // Split by "="
                    if (parts.length >= 3) { // Ensure valid format
                        String key1 = parts[0].trim();
                        String key2 = parts[1].trim();
                        String value = parts[parts.length - 1].trim(); // Last value
                        // **Exact Match (case-insensitive)**
                        if (targetKey1.equalsIgnoreCase(key1) && targetKey2.equalsIgnoreCase(key2)) {
                            System.out.println(targetKey2+" -- " + value);
                            continue;
                        }
                        if (targetKey2.contains("WASHING 1")||targetKey2.contains("washing 1")||targetKey2.contains("WASH 1")||targetKey2.contains("wash 1"))  {
                        System.out.println(targetKey2 + " -- " + timo);
                        break;
                        }
                        // **Fuzzy Match (allow typos/missing letters)**
                        int distance1 = levenshtein.apply(targetKey1.toUpperCase(), key1.toUpperCase());
                        int distance2 = levenshtein.apply(targetKey2.toUpperCase(), key2.toUpperCase());

                        if (distance1 <= threshold && distance2 <= threshold) {
                            System.out.println(targetKey2+" -- " + value/* +
                                    " (Keys: " + key1 + ", " + key2 + " | Distances: " + distance1 + ", " + distance2 + ")"*/);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        
   System.out.println();
   System.out.println();
        
////////////////////////////Working HOurs////////////////////////////////

//Working Hours

String settingssfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
List<String> linessr = Files.readAllLines(Paths.get(settingssfile));
for (String line : linessr) {   
if (line.startsWith("Working_Hours=")) {
String value = line.split("=", 2)[1];
fggf=value;
break;
}}
Map<String, String> chemicalDictionaryg = loadChemicalDictionary(fggf);
if (chemicalDictionaryg.isEmpty()) {
System.out.println("Dictionary is empty or not found!");
return;
}         
String[] stepsg = proco.split(" - ");
for (String step : stepsg) {
if (step.contains("WASHING 1")||step.contains("washing 1")||step.contains("WASH 1")||step.contains("wash 1"))  {
    
    
if (linenumber.getText().equals("1")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("2")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("3")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("4")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("5")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("6")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else if (linenumber.getText().equals("7")) {
String bestMatch = getBestMatch(step+" LINE "+linenumber.getText(), chemicalDictionaryg);
System.out.println(step+" LINE "+linenumber.getText() + " -> " + bestMatch); 
}

else {
//String bestMatch = getBestMatch(step, chemicalDictionary);
//System.out.println(step + " -> " + bestMatch); 
System.out.println("No line found under this number "+linenumber.getText()); 
    }
 

}

else {
    
if (step.contains("WASHING 2")||step.contains("WASHING 3")||step.contains("WASHING 4")||step.contains("WASHING 5")||step.contains("WASHING 6"))  {
continue;   
}

else {
String bestMatch = getBestMatch(step, chemicalDictionaryg);
System.out.println(step + " -> " + bestMatch);     
}

}}

        
        /////////////////////////////////////////////////////////////////////////
        
        
addRow(po.getText(),sapcode.getText(),style.getText(),customer.getText(),washname.getText(),poamount.getText(),cuttingamount.getText(),laundrydate.getText(),xfacdate.getText()," "," "," "," "," "," "," "," "," "," "," ");

        
            
/////////////////////////////////END//////////////////////////////////
System.out.println("--------------------------------------");
        
        
        
        
        
        // Optional: Add a short delay for debugging (remove in production)
        try { 
            Thread.sleep(10);  // Delay 0.5 sec for visibility (not needed for actual processing)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

 
//
//if (allerra.equals("")) {
//    
//Alert alooo = new Alert(Alert.AlertType.INFORMATION);
//alooo.setTitle("Info");
//alooo.setHeaderText("Errors!");
//alooo.setContentText("No errors found");
//alooo.setResizable(true);
//DialogPane dialogPaneef = alooo.getDialogPane();
//dialogPaneef.getStylesheets().add(
//getClass().getResource("cupertino-light.css").toExternalForm());
//alooo.showAndWait();
//    
//}
//
//else {
//    
//Alert alooo = new Alert(Alert.AlertType.INFORMATION);
//alooo.setTitle("Info");
//JFXTextArea ios=new JFXTextArea ();
//ios.setEditable(false);
//ios.setText(allerra);
//alooo.setHeaderText("Errors!");
//alooo.setContentText("");
//alooo.setGraphic(ios);
//alooo.setResizable(true);
//DialogPane dialogPaneef = alooo.getDialogPane();
//dialogPaneef.getStylesheets().add(
//getClass().getResource("cupertino-light.css").toExternalForm());
//alooo.showAndWait();   
//    
//}
            
        
        
            
    
}


      
            
            } else {
               
            
            Notifications noti = Notifications.create();
            noti.title("Fatal Error!");
            noti.text("No row is selected, please select row(s) first.");
            noti.position(Pos.CENTER);
            noti.hideAfter(Duration.seconds(4));
            noti.showError();
            
            
            }
    
            

        
        
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   
    
    
    private static Map<String, String> loadChemicalDictionary(String filePath) {
        Map<String, String> dictionary = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    dictionary.put(parts[0].trim().toUpperCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return dictionary;
    }

    
    
    private static String getBestMatch(String input, Map<String, String> dictionary) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        String bestMatch = "";
        int minDistance = Integer.MAX_VALUE;
        for (String key : dictionary.keySet()) {
            int distance = levenshtein.apply(input, key);
            if (distance < minDistance) {
                minDistance = distance;
                bestMatch = dictionary.get(key);
            }
        }
        return bestMatch;
    }
    
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
     private void startAutoRefresh2() {
        refreshTimeline2 = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            Platform.runLater(this::fetching);
        }));
        refreshTimeline2.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline2.play();
    }
    
    private void fetching() {
        


// حذف العناصر القديمة إن وُجدت
onlineusersmenu.getItems().clear();

try {
    String sql = "SELECT User, Position FROM USERS WHERE Status = 'Online'";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    while (rs.next()) {
        String username = rs.getString("User");
        String position = rs.getString("Position");

        // إنشاء العنصر مع النقطة الخضراء واسم المستخدم والمنصب
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
    statusDot.setFill(Color.LIMEGREEN); // لون النقطة (متصل)

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
            javafx.scene.text.Font cairoSemiBold = javafx.scene.text.Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
      
      conn = db.java_db();
      startAutoRefresh2();
      refresh.fire();
      del.setVisible(false);
      table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
      
      selsecc=LogIn_GUI_Controller.selectedpositionn;
      seluserr=LogIn_GUI_Controller.selecteduser;
      
        String filePath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";;
        List<String> lines;
        try {
        lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
        if (line.startsWith("DB=")) {
        String value = line.split("=", 2)[1];
        databaseUrl="jdbc:sqlite:"+value;
        break;
        }} 
        } catch (IOException ex) {
        Logger.getLogger(Alpha_PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        editt();
        
        
        ref.setTooltip(new Tooltip ("Refresh Orders"));
        upd.setTooltip(new Tooltip ("Update Modifications"));
        add.setTooltip(new Tooltip ("Add Order"));
        fil.setTooltip(new Tooltip ("Filter"));
        del.setTooltip(new Tooltip ("Delete Order"));
        linenumber.requestFocus();
        linenumber.setFocusTraversable(true);
        
        
        
        
        
        dataaaaa = FXCollections.observableArrayList();
        
        TableColumn<ObservableList<String>, String> Col0 = new TableColumn<>("PO");
        Col0.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(0)));
         
        TableColumn<ObservableList<String>, String> Col1 = new TableColumn<>("Sap Code");
        Col1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(1)));

        TableColumn<ObservableList<String>, String> Col2 = new TableColumn<>("Style");
        Col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(2)));
        
        TableColumn<ObservableList<String>, String> Col3 = new TableColumn<>("Customer");
        Col3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(3)));
        
        TableColumn<ObservableList<String>, String> Col4 = new TableColumn<>("Wash Name");
        Col4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(4)));
        
        TableColumn<ObservableList<String>, String> Col5 = new TableColumn<>("PO Amount");
        Col5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(5)));
        
        TableColumn<ObservableList<String>, String> Col6 = new TableColumn<>("Cutting Amount");
        Col6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(6)));
        
        TableColumn<ObservableList<String>, String> Col7 = new TableColumn<>("Laundry Date");
        Col7.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(7)));
        
        TableColumn<ObservableList<String>, String> Col8 = new TableColumn<>("X Fac Date");
        Col8.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(8)));
        
        TableColumn<ObservableList<String>, String> Col9 = new TableColumn<>("Scrapping");
        Col9.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(9)));
        
        TableColumn<ObservableList<String>, String> Col10 = new TableColumn<>("Whisker");
        Col10.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(10)));
        
        TableColumn<ObservableList<String>, String> Col11 = new TableColumn<>("Tagging");
        Col11.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(11)));
        
        TableColumn<ObservableList<String>, String> Col12 = new TableColumn<>("Washing");
        Col12.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(12)));
        
        TableColumn<ObservableList<String>, String> Col13 = new TableColumn<>("Laser");
        Col13.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(13)));
        
        TableColumn<ObservableList<String>, String> Col14 = new TableColumn<>("Spray");
        Col14.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(14)));
        
        TableColumn<ObservableList<String>, String> Col15 = new TableColumn<>("Net");
        Col15.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(15)));
        
        
        
        TableColumn<ObservableList<String>, String> Col16 = new TableColumn<>("Grinding");
        Col16.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(16)));
        
        TableColumn<ObservableList<String>, String> Col17 = new TableColumn<>("Laser Damage");
        Col17.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(17)));
        
        TableColumn<ObservableList<String>, String> Col18 = new TableColumn<>("Moon Wash");
        Col18.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(18)));
        
        TableColumn<ObservableList<String>, String> Col19 = new TableColumn<>("Tint");
        Col19.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(19)));
        

        table2.getColumns().addAll(Col0,Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11,Col12, Col13, Col14,Col15,Col16,Col17,Col18,Col19);
        table2.setItems(dataaaaa);
        //table2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
        TableFilter filter = new TableFilter(table2);
        
        
          // Add a listener to update TextFields when the selection changes
table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Assuming each row is an ObservableList<String> with fixed column order
        ObservableList<String> row = newSelection;

        // Fill in your TextFields safely (use null/size checks if needed)
        po.setText(getCell(row, 0));
        sapcode.setText(getCell(row, 1));
        style.setText(getCell(row, 2));
        customer.setText(getCell(row, 3));
        washname.setText(getCell(row, 4));
        poamount.setText(getCell(row, 5));
        cuttingamount.setText(getCell(row, 6));
        laundrydate.setText(getCell(row, 7));
        xfacdate.setText(getCell(row, 8));

        del.setVisible(true); // Show delete button (or any other UI logic)
    }
});
        
      

    
fileCheckTimer = new Timer(true); // Daemon thread
        fileCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                File file = new File("CAUTION.kady");
                if (!file.exists()) {
                    System.out.println("File not found. Exiting...");
                    shutdownApp();
                } else {
                    System.out.println("File found. App continues running.");
                }
            }
        }, 0, 2 * 60 * 1000); // Every 2 minutes
        
    }
    
     
private void shutdownApp() {
        fileCheckTimer.cancel(); // Stop the timer
        // Run on JavaFX thread
        Platform.runLater(() -> {
            Platform.exit(); // Close JavaFX
   
    
    if (refreshTimeline2 != null) {
        refreshTimeline2.stop();
    }
   
    if (fileCheckTimer != null) {
        fileCheckTimer.cancel();
    }
            System.exit(0);  // Kill all remaining threads
        });
    }
    
    
    
    
    
    // Helper method to safely get a cell from a row
private String getCell(ObservableList<String> row, int index) {
    return (index < row.size()) ? row.get(index) : "";
}
    
    
     private void addRow(String po, String sapcode, String style, String customer, String washname, String poamount, String cuttingamount, String laundrydate, String xfacdate, String scrapping, String whisker, String tagging, String washing, String laser, String spray, String net, String grinding, String laserdamage, String moonwash, String tint) {
       
        if (!po.isEmpty() && !sapcode.isEmpty() && !style.isEmpty() && !customer.isEmpty() && !washname.isEmpty() && !poamount.isEmpty() && !cuttingamount.isEmpty() && !laundrydate.isEmpty() && !xfacdate.isEmpty() && !scrapping.isEmpty() && !whisker.isEmpty() && !tagging.isEmpty() && !washing.isEmpty()&& !laser.isEmpty()&& !spray.isEmpty()&& !net.isEmpty()&& !grinding.isEmpty()&& !laserdamage.isEmpty()&& !moonwash.isEmpty()&& !tint.isEmpty()) {
            ObservableList<String> newRow = FXCollections.observableArrayList(po, sapcode, style, customer, washname, poamount, cuttingamount, laundrydate, xfacdate, scrapping, whisker, tagging, washing, laser, spray, net,grinding,laserdamage,moonwash,tint);
            dataaaaa.add(newRow);
        } else {
        } 
    }
    
    
    
    
}
