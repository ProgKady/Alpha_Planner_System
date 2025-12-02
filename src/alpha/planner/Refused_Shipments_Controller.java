


package alpha.planner;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;
import org.jsoup.Jsoup;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class Refused_Shipments_Controller implements Initializable {

   
    @FXML
    private TableView<ObservableList<String>> table;
    @FXML
    private JFXButton report;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    public static String val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11,val12,val13,val14,val15,val16,val17;
    public static String recpath,procccc;
    public static String selsecc,seluserr;
    public static String value1;
    
   

   

    
    
    
     @FXML
    void excelaction(ActionEvent event) throws FileNotFoundException, IOException {

     
        /////////////////////////////////////////////////////////////////////////
        
        Workbook workbook = new XSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Refused Shipments");
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
        dialog.setInitialFileName("Refused Shipments");
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
    
    
    

    @FXML
void reportaction(ActionEvent event) {

    FileChooser dialog = new FileChooser();
    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    dialog.setInitialDirectory(new File (System.getProperty("user.home")+"\\Desktop"));
    File dialogResult = dialog.showSaveDialog(null);
    if (dialogResult == null) return;
    String filePath = dialogResult.getAbsolutePath();
    if (!filePath.endsWith(".pdf")) filePath += ".pdf";

    try {
        Document myDocument = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
        myDocument.open();

        // Load images
        Image imageLeft = Image.getInstance(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Icons\\logo.png");
        imageLeft.scaleToFit(100, 40);

        Image imageRight;
        try {
            imageRight = Image.getInstance(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Icons\\"+val6+".bmp");
        } catch (Exception e) {
            imageRight = Image.getInstance(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Icons\\no_icon.png");
        }
        imageRight.scaleToFit(100, 40);

        // Table for logos and arrow
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        float[] widths = {2, 1, 2};
        headerTable.setWidths(widths);

        PdfPCell leftCell = new PdfPCell(imageLeft);
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        // Arrow cell
        PdfPCell arrowCell = new PdfPCell(new Phrase("→", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD)));
        arrowCell.setBorder(Rectangle.NO_BORDER);
        arrowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        arrowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell rightCell = new PdfPCell(imageRight);
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        headerTable.addCell(leftCell);
        headerTable.addCell(arrowCell);
        headerTable.addCell(rightCell);
        myDocument.add(headerTable);

        // Add circle with Kadinio text at top-right
PdfContentByte canvas = writer.getDirectContent();
float cx = myDocument.right() - 120;
float cy = myDocument.top() - 260;

// Bigger circle
canvas.setColorStroke(BaseColor.BLACK);
canvas.circle(cx, cy, 120); // increased radius
canvas.stroke();

// Bigger & bolder text inside the circle
Font circleFont = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BaseColor.BLACK);
ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
    new Phrase("Quantity: "+val17, circleFont),
    cx, cy - 5, 0);


        // Fonts
        BaseFont bf = BaseFont.createFont(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font arabicFont = new Font(bf, 20, Font.BOLD, BaseColor.BLACK);

        FontFactory.register(System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf", "Cairo");
        Font arabicFontt1 = FontFactory.getFont("Cairo", BaseFont.IDENTITY_H, true, 33, Font.BOLD, BaseColor.BLACK);
        Font arabicFontt2 = FontFactory.getFont("Cairo", BaseFont.IDENTITY_H, true, 18, Font.BOLD, BaseColor.BLACK);
        Font arabicFontt3 = FontFactory.getFont("Cairo", BaseFont.IDENTITY_H, true, 13, Font.BOLD, BaseColor.BLACK);
        
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = sdf.format(d);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.ofHours(3));
                    String timeString = dtf.format(now);
                    
                    String fulldate=dateString+"   at   "+timeString;
        

        myDocument.add(new Paragraph("                                     Refused Shipment", arabicFontt1));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("                                    " + val6 + " - " + val7 + " ", arabicFontt1));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  Created In:   " + fulldate, arabicFontt2));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  Unique ID:  " + val16, arabicFontt2));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  PO:  " + val3, arabicFontt2));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  Sap Code:  " + val4 + "   |   Style:  " + val5 + "   |   Customer:  " + val6 + " ", arabicFontt2));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  PO Amount:  " + val8 + "   |   Cutting Amount:  " + val9, arabicFontt2));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  Incoming Date:  " + val10 + "   |   X Fac Date:  " + val11 + " ", arabicFontt2));
        myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------------------------------"));
        myDocument.add(new Paragraph("  From Section:  " + val13 + "   |   To Section:  " + val14 + " ", arabicFontt2));

        PdfPTable table = new PdfPTable(2);
        float[] columnWidths = new float[] {20, 20};
        table.setWidths(columnWidths);
        table.getDefaultCell().setFixedHeight(60f);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorderWidth(1f);
        table.setWidthPercentage(100);
        myDocument.add(table);

      
        myDocument.add(new Paragraph("............................................."));
        myDocument.add(new Paragraph("Powered By Kadysoft Ltd.", arabicFontt3));
        myDocument.close();
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("Successful Operation");
        al.setHeaderText("Successfully Created");
        al.setContentText("Following Card was successfully generated.");
        al.setResizable(false);
        DialogPane dialogPanei = al.getDialogPane();
          //////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
  if (themooo.equals("cupertino-dark.css")) {
      //Dark
      dialogPanei.getStylesheets().add(
      getClass().getResource("cupertino-dark.css").toExternalForm());
  }
  else {
      //Light
      dialogPanei.getStylesheets().add(
      getClass().getResource("cupertino-light.css").toExternalForm());
  }
  }
  catch (Exception re) {}
  //////////////////////////////////////////////////////////////////////////////////////////////////////  
        al.showAndWait();

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            Desktop d = Desktop.getDesktop();
            d.open(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            javafx.scene.text.Font cairoSemiBold = javafx.scene.text.Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
          
        }
        
        this.conn = db.java_db();
        
        
        selsecc=LogIn_GUI_Controller.selectedpositionn;
        seluserr=LogIn_GUI_Controller.selecteduser;
        
        
        table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
        
        
         try{
            
            String sql ="select * from Refused_Shipments";
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
    
        
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
    if (newSel != null) {
        Object col1 = table.getColumns().get(0).getCellObservableValue(newSel).getValue();
        Object col2 = table.getColumns().get(1).getCellObservableValue(newSel).getValue();
        Object col3 = table.getColumns().get(2).getCellObservableValue(newSel).getValue();
        Object col4 = table.getColumns().get(3).getCellObservableValue(newSel).getValue();
        Object col5 = table.getColumns().get(4).getCellObservableValue(newSel).getValue();
        Object col6 = table.getColumns().get(5).getCellObservableValue(newSel).getValue();
        Object col7 = table.getColumns().get(6).getCellObservableValue(newSel).getValue();
        Object col8 = table.getColumns().get(7).getCellObservableValue(newSel).getValue();
        Object col9 = table.getColumns().get(8).getCellObservableValue(newSel).getValue();
        Object col10 = table.getColumns().get(9).getCellObservableValue(newSel).getValue();
        Object col11 = table.getColumns().get(10).getCellObservableValue(newSel).getValue();
        Object col12 = table.getColumns().get(11).getCellObservableValue(newSel).getValue();
        
        Object col13 = table.getColumns().get(12).getCellObservableValue(newSel).getValue();
        Object col14 = table.getColumns().get(13).getCellObservableValue(newSel).getValue();
        Object col15 = table.getColumns().get(14).getCellObservableValue(newSel).getValue();
        Object col16 = table.getColumns().get(15).getCellObservableValue(newSel).getValue();
        Object col17 = table.getColumns().get(16).getCellObservableValue(newSel).getValue();

        val1=col1.toString();
        val2=col2.toString();
        val3=col3.toString();
        val4=col4.toString();
        val5=col5.toString();
        val6=col6.toString();
        val7=col7.toString();
        val8=col8.toString();
        val9=col9.toString();
        val10=col10.toString();
        val11=col11.toString();
        
        val12=col12.toString();  //User
        val13=col13.toString(); //From
        val14=col14.toString(); //To
        val15=col15.toString(); //Deleivered
        
        val16=col16.toString(); //Unique ID
        val17=col17.toString(); //Q To Send
        
        
        
        
    }
});

        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;   
        
        
        
    }    
    
}
