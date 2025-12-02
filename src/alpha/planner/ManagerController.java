
package alpha.planner;

import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.table.TableFilter;



/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */



public class ManagerController implements Initializable {

    Timer fileCheckTimer;
   
    @FXML
    private HBox sectionspanel;

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private JFXButton export,reportall,logout;
    

    @FXML
    private JFXButton customreport;
    
    Connection conn = null ;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    public static String tableselected;
    
    @FXML
    private ImageView messenger;
    
     @FXML
    private ImageView inbox;
     
      @FXML
    private Label caution;
     
     @FXML private VBox sidePanel1;
    @FXML private VBox shipmentList1;
    @FXML private SplitPane mainSplit1;
    
    private Timeline refreshTimeline3;
    
    public static String diro;

    private void startAutoRefresh3 () {
        refreshTimeline3 = new Timeline(new KeyFrame(Duration.seconds(3), e -> {//Messages
            Platform.runLater(this::fofofo);
        }));
        refreshTimeline3.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline3.play();
    }
    public static String selsecc,seluserr;
    
    
    
    
     @FXML
    void imgsetaction(MouseEvent event) throws IOException {
        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Settings.fxml"));
    Scene sce = new Scene(root);
//    //////////////////////////////Theme////////////////////////////////
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
    stg.setTitle("Settings");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
        
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
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.centerOnScreen();
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
    Stage jk = (Stage)this.table.getScene().getWindow();
    jk.close();
    
    //////////////////////////////////////////////////
    
    if (refreshTimeline3 != null) {
        refreshTimeline3.stop();
    }
    
    //////////////////////////////////////////////////
    
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
    
    
  }
    
    
    
    
   private void fofofo() {
    mainSplit1.setDisable(false);

    try (PreparedStatement pst = conn.prepareStatement(
            "SELECT * FROM Message WHERE M_To = ?")) {

        pst.setString(1, selsecc);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("ID");
            int delivered = rs.getInt("Delivered");
            String dat = rs.getString("Date");
            String tim = rs.getString("Time");
            String secfrom = rs.getString("Sec_From");
            String mfrom = rs.getString("M_From");
            String mto = rs.getString("M_To");
            String msg = rs.getString("Message");

            // لو Delivered = 1 → نمسح الرسالة من قاعدة البيانات
            if (delivered == 1) {
                try (PreparedStatement delpst = conn.prepareStatement(
                        "DELETE FROM Message WHERE ID = ?")) {
                    delpst.setInt(1, id);
                    delpst.executeUpdate();
                    
                                               
////////////////////////////////////////////////////////////
         
                    try {
    final Path path = Paths.get(diro + "\\Java\\bin\\Alpha_Logs.kady");
    Files.write(path, Arrays.asList("Message Deleted: \nDate: "+dat+"\nTime: "+tim+"\nFrom Section: "+secfrom+"\nFrom User: "+mfrom+"\nMessage To: "+mto+"\nMessage Content: "+msg+"\n\n"), StandardCharsets.UTF_8,
        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
} catch (final IOException ioe) {
    // Add your own exception handling...
}
                    
////////////////////////////////////////////////////////////


                    
                }
                continue;
            }

         // === Header Info (صغير ولونه رمادي) ===
Label header = new Label(
    "Date: " + dat + " | " +
    "Time: " + tim + "\n" +
    "From: " + mfrom + ", In: " + secfrom + " → To: " + mto
);
header.setWrapText(true);
header.setMaxWidth(400);
header.setStyle(
    "-fx-font-size: 13px;" +
    "-fx-text-fill: #333d29;" +
    "-fx-font-weight: bold;" +         
    "-fx-font-family: 'Cairo SemiBold';"
);

// === Message Body (Bubble) ===
Label message = new Label(msg);
message.setWrapText(true);
message.setMaxWidth(500);  // عرض البالونة

try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        message.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-family: 'Cairo SemiBold';" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +   // أزرق زي iMessage
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 12 8 12;"
        );
    } else {
        message.setStyle(
            "-fx-font-size: 15px;" +
           "-fx-font-family: 'Cairo SemiBold';" +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +  // رمادي فاتح زي iOS
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 12 8 12;"
        );
    }
} catch (Exception re) {
    message.setStyle(
        "-fx-font-size: 15px;" +
       "-fx-font-family: 'Cairo SemiBold';" +
        "-fx-text-fill: black;" +
        "-fx-font-weight: bold;" +        
        "-fx-background-color: #E5E5EA;" +
        "-fx-background-radius: 15;" +
        "-fx-padding: 8 12 8 12;"
    );
}

// === Final Card ===
VBox card = new VBox(5, header, message);
card.setPadding(new Insets(10));
card.setMaxWidth(420);

// خلفية الكارد نفسها (تقدر تخليها شفافة لو عايز زي واتساب)
try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        card.setStyle(
            "-fx-background-color: transparent;" +   // شفافة زي شات
            "-fx-border-radius: 12;"
        );
    } else {
        card.setStyle(
            "-fx-background-color: transparent;" +   // شفافة برضو
            "-fx-border-radius: 12;"
        );
    }
} catch (Exception re) {}

card.setAlignment(Pos.TOP_LEFT);

            
            shipmentList1.getChildren().add(card);
            mainSplit1.setVisible(true);

            // === إشعار منبثق ===
            String allText = "لقد تم استلام رسالة جديدة من المستخدم " + mfrom + " في القسم " + secfrom +
                             "\n\n" +
                             "Date: " + dat + "\n" +
                             "Time: " + tim + "\n" +
                             "From Section: " + secfrom + "\n" +
                             "From User: " + mfrom + "\n" +
                             "To Section: " + mto + "\n\n" +
                             "Message:\n" + msg;

            Popup notificationPopup = new Popup();
            VBox mainContainer = new VBox(10);
            mainContainer.setPadding(new Insets(20));
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #e0e0e0;" +
                "-fx-border-radius: 16;" +
                "-fx-border-width: 1;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0.4, 0, 6);"
            );

            Label notificationLabel = new Label(allText);
            notificationLabel.setWrapText(true);
            notificationLabel.setMaxWidth(500);
            notificationLabel.setTextAlignment(TextAlignment.RIGHT);
            notificationLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            notificationLabel.setStyle(
               "-fx-font-family: 'Cairo SemiBold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #333333;"
            );

            Button closeButton = new Button("Close");
            closeButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #d32f2f;" +
                "-fx-font-size: 16px;" +
                "-fx-cursor: hand;"
            );
            closeButton.setOnAction(e -> notificationPopup.hide());

            mainContainer.getChildren().addAll(notificationLabel, closeButton);
            notificationPopup.getContent().add(mainContainer);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            mainContainer.applyCss();
            mainContainer.layout();
            double popupWidth = mainContainer.getWidth();
            double popupHeight = mainContainer.getHeight();
            double popupX = (bounds.getWidth() - popupWidth) / 2;
            double popupY = (bounds.getHeight() - popupHeight) / 2;

            Stage stage = (Stage) table.getScene().getWindow();
            notificationPopup.show(stage);
            notificationPopup.setX(popupX);
            notificationPopup.setY(popupY);

            mainContainer.setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), mainContainer);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\message.mp3").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
            
            
        Image img1 = new Image(getClass().getResource("inbox.png").toExternalForm());
        
      
            if (inbox.getImage() == img1) {
                inbox.setImage(img1);
            } else {
                inbox.setImage(img1);
            }
       
            

            try (PreparedStatement pst2 = conn.prepareStatement(
                    "UPDATE Message SET Delivered = 1 WHERE ID = ?")) {
                pst2.setInt(1, id);
                pst2.executeUpdate();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    ///////////////////////////////////////////////////////////
    
     
      @FXML
    void messengeraction(MouseEvent event) throws IOException {
        
   Stage stg = new Stage(StageStyle.TRANSPARENT);
Parent root = FXMLLoader.<Parent>load(getClass().getResource("Messenger.fxml"));
Scene sce = new Scene(root);

// خلي الخلفية بيضاء بدل شفاف
sce.setFill(Color.TRANSPARENT); // scene شفاف
root.setStyle("-fx-background-color: white; -fx-background-radius: 30;-fx-border-color:black;-fx-border-width:4px;");

// اعمل Clip مستدير يحدد حدود الـ root
Rectangle clip = new Rectangle(550, 950);
clip.setArcWidth(100);
clip.setArcHeight(100);
root.setClip(clip);

////////////////////////////// Theme //////////////////////////////
BufferedReader bis = new BufferedReader(
    new FileReader(System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady")
);
String themooo = bis.readLine();
bis.close();

// Check if CSS exists
URL cssUrl = getClass().getResource(themooo);
if (cssUrl == null) {
    System.err.println("ERROR: cupertino-dark.css not found in same package as controller!");
} else {
    String cssPath = cssUrl.toExternalForm();
    sce.getStylesheets().add(cssPath);
    root.getStylesheets().add(cssPath);
}
////////////////////////////////////////////////////////////////////

stg.setTitle("Kadysoft Messenger");
stg.centerOnScreen();
stg.setResizable(false);
stg.setMaximized(false);
stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
stg.setScene(sce);
stg.setAlwaysOnTop(true);
stg.centerOnScreen();
stg.show();


    }
    
      @FXML
void inboxaction(MouseEvent event) throws IOException {
    // toggle visibility
    mainSplit1.setVisible(!mainSplit1.isVisible());
}
   
    
     @FXML
    void reportallaction(ActionEvent event) {

        loadProductionReportAndAudit();
        caution.setVisible(true);
        tableselected="report";
        
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(60));
                delay.setOnFinished(e -> {
                caution.setVisible(false);    
                });
                delay.play();
        
    }
    
    
    
    
    public void loadProductionReportAndAudit() {
    table.getColumns().clear();
    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    try {
        // 1️⃣ Get all tables ending with _production
        List<String> prodTables = new ArrayList<>();
        String tableQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name LIKE '%_Production'";
        pst = conn.prepareStatement(tableQuery);
        rs = pst.executeQuery();
        while (rs.next()) {
            prodTables.add(rs.getString("name"));
        }
        rs.close();
        pst.close();

        if (prodTables.isEmpty()) {
            System.out.println("No production tables found.");
            return;
        }

        // 2️⃣ Build UNION ALL query with table name
        StringBuilder unionSQL = new StringBuilder();
        for (int i = 0; i < prodTables.size(); i++) {
            if (i > 0) unionSQL.append(" UNION ALL ");
            unionSQL.append("SELECT '").append(prodTables.get(i)).append("' AS Section_Name, ")
                    .append("PO, Sap_Code, Style, Customer, Wash_Name, Received, Finished, Not_Finished ")
                    .append("FROM ").append(prodTables.get(i));
        }

        // 3️⃣ Wrap in GROUP BY to sum quantities per table name
        String finalSQL = "SELECT Section_Name, PO, Sap_Code, Style, Customer, Wash_Name, " +
                "SUM(Received) AS Total_Received, " +
                "SUM(Finished) AS Total_Finished, " +
                "SUM(Not_Finished) AS Total_Not_Finished " +
                "FROM (" + unionSQL.toString() + ") " +
                "GROUP BY Section_Name, PO, Sap_Code, Style, Customer, Wash_Name " +
                "ORDER BY Section_Name, PO";

        pst = conn.prepareStatement(finalSQL);
        rs = pst.executeQuery();

        // 4️⃣ Dynamically create columns
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
    final int j = i;
    TableColumn<ObservableList<String>, String> col =
            new TableColumn<>(rs.getMetaData().getColumnName(i + 1));

    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
            Object cellValue = param.getValue().get(j);
            return new SimpleStringProperty(cellValue != null ? cellValue.toString() : "");
        }
    });

    table.getColumns().add(col);
}


        // 5️⃣ Add rows to data
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        table.setItems((ObservableList)data);

        // 6️⃣ Add TableFilter
        TableFilter filter = new TableFilter(table);

        

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

    
    
    
    
    
    
    
    
    
    
    @FXML
    void customreportaction(ActionEvent event) {

        
        
        if (tableselected.equals("stock")) {
            
            addTotalsToTable();
            
        }
        
        else if (tableselected.equals("productivity")) {
            
            productivityyy();
            
        }
        
        else if (tableselected.equals("report")) {
            
            productivityyyyy();
            
        }
        
        else {
            return;
        }
        
        
    }
    
    
    
    
    private void addTotalsToTable() {
    ObservableList<ObservableList<String>> currentItems = table.getItems();

    if (currentItems == null || currentItems.isEmpty()) return;

    // Make a copy to modify
    ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList(currentItems);

    // Remove old total/empty rows if already added
    if (rows.size() >= 2) {
        ObservableList<String> last = rows.get(rows.size() - 1);
        ObservableList<String> secondLast = rows.get(rows.size() - 2);
        if (last.contains("TOTAL")) rows.remove(rows.size() - 1);
        if (secondLast.stream().allMatch(String::isEmpty)) rows.remove(rows.size() - 1);
    }

    double totalReceived = 0;
    double totalMinusLines = 0;
    double totalDelivered = 0;
    double totalMinusQ = 0;

    int receivedIndex = -1;
    int minusLinesIndex = -1;
    int deliveredIndex = -1;
    int minusQIndex = -1;

    // Get column indexes
    for (int i = 0; i < table.getColumns().size(); i++) {
        String name = table.getColumns().get(i).getText();
        if (name.equalsIgnoreCase("received")) receivedIndex = i;
        if (name.equalsIgnoreCase("minus_lines")) minusLinesIndex = i;
        if (name.equalsIgnoreCase("delivered")) deliveredIndex = i;
        if (name.equalsIgnoreCase("minus_q")) minusQIndex = i;
    }

    // Calculate totals from visible data
    for (ObservableList<String> row : rows) {
        if (receivedIndex >= 0) {
            try { totalReceived += Double.parseDouble(row.get(receivedIndex)); } catch (Exception ignored) {}
        }
        if (minusLinesIndex >= 0) {
            try { totalMinusLines += Double.parseDouble(row.get(minusLinesIndex)); } catch (Exception ignored) {}
        }
        if (deliveredIndex >= 0) {
            try { totalDelivered += Double.parseDouble(row.get(deliveredIndex)); } catch (Exception ignored) {}
        }
        if (minusQIndex >= 0) {
            try { totalMinusQ += Double.parseDouble(row.get(minusQIndex)); } catch (Exception ignored) {}
        }
    }

    // Add empty row
    ObservableList<String> emptyRow = FXCollections.observableArrayList();
    for (int i = 0; i < table.getColumns().size(); i++) emptyRow.add("");
    rows.add(emptyRow);

    // Add total row
    ObservableList<String> totalRow = FXCollections.observableArrayList();
    for (int i = 0; i < table.getColumns().size(); i++) {
        if (i == receivedIndex) totalRow.add(String.valueOf(totalReceived));
        else if (i == minusLinesIndex) totalRow.add(String.valueOf(totalMinusLines));
        else if (i == deliveredIndex) totalRow.add(String.valueOf(totalDelivered));
        else if (i == minusQIndex) totalRow.add(String.valueOf(totalMinusQ));
        else if (i == 0) totalRow.add("TOTAL OF QUANTITIES");
        else totalRow.add("");
    }
    rows.add(totalRow);

    // Show updated table
    table.setItems(rows);
}


    
    private void productivityyy() {
    ObservableList<ObservableList<String>> currentItems = table.getItems();

    if (currentItems == null || currentItems.isEmpty()) return;

    // Make a copy to modify
    ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList(currentItems);

    // Remove old total/empty rows if already added
    if (rows.size() >= 2) {
        ObservableList<String> last = rows.get(rows.size() - 1);
        ObservableList<String> secondLast = rows.get(rows.size() - 2);
        if (last.contains("TOTAL")) rows.remove(rows.size() - 1);
        if (secondLast.stream().allMatch(String::isEmpty)) rows.remove(rows.size() - 1);
    }

    double totalReceived = 0;
    double totalMinusLines = 0;
    double totalDelivered = 0;
    double totalMinusQ = 0;

    int receivedIndex = -1;
    int minusLinesIndex = -1;
    int deliveredIndex = -1;
    int minusQIndex = -1;

    // Get column indexes
    for (int i = 0; i < table.getColumns().size(); i++) {
        String name = table.getColumns().get(i).getText();
        if (name.equalsIgnoreCase("received")) receivedIndex = i;
        if (name.equalsIgnoreCase("finished")) minusLinesIndex = i;
        if (name.equalsIgnoreCase("not_finished")) deliveredIndex = i;
        //if (name.equalsIgnoreCase("minus_q")) minusQIndex = i;
    }

    // Calculate totals from visible data
    for (ObservableList<String> row : rows) {
        if (receivedIndex >= 0) {
            try { totalReceived += Double.parseDouble(row.get(receivedIndex)); } catch (Exception ignored) {}
        }
        if (minusLinesIndex >= 0) {
            try { totalMinusLines += Double.parseDouble(row.get(minusLinesIndex)); } catch (Exception ignored) {}
        }
        if (deliveredIndex >= 0) {
            try { totalDelivered += Double.parseDouble(row.get(deliveredIndex)); } catch (Exception ignored) {}
        }
//        if (minusQIndex >= 0) {
//            try { totalMinusQ += Double.parseDouble(row.get(minusQIndex)); } catch (Exception ignored) {}
//        }
    }

    // Add empty row
    ObservableList<String> emptyRow = FXCollections.observableArrayList();
    for (int i = 0; i < table.getColumns().size(); i++) emptyRow.add("");
    rows.add(emptyRow);

    // Add total row
    ObservableList<String> totalRow = FXCollections.observableArrayList();
    for (int i = 0; i < table.getColumns().size(); i++) {
        if (i == receivedIndex) totalRow.add(String.valueOf(totalReceived));
        else if (i == minusLinesIndex) totalRow.add(String.valueOf(totalMinusLines));
        else if (i == deliveredIndex) totalRow.add(String.valueOf(totalDelivered));
//        else if (i == minusQIndex) totalRow.add(String.valueOf(totalMinusQ));
        else if (i == 0) totalRow.add("TOTAL OF QUANTITIES");
        else totalRow.add("");
    }
    rows.add(totalRow);

    // Show updated table
    table.setItems(rows);
}
    
    
    
    private void productivityyyyy() {
    ObservableList<ObservableList<String>> currentItems = table.getItems();

    if (currentItems == null || currentItems.isEmpty()) return;

    // Make a copy to modify
    ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList(currentItems);

    // Remove old total/empty rows if already added
    if (rows.size() >= 2) {
        ObservableList<String> last = rows.get(rows.size() - 1);
        ObservableList<String> secondLast = rows.get(rows.size() - 2);
        if (last.contains("TOTAL")) rows.remove(rows.size() - 1);
        if (secondLast.stream().allMatch(String::isEmpty)) rows.remove(rows.size() - 1);
    }

    double totalReceived = 0;
    double totalMinusLines = 0;
    double totalDelivered = 0;
    double totalMinusQ = 0;

    int receivedIndex = -1;
    int minusLinesIndex = -1;
    int deliveredIndex = -1;
    int minusQIndex = -1;

    // Get column indexes
    for (int i = 0; i < table.getColumns().size(); i++) {
        String name = table.getColumns().get(i).getText();
        if (name.equalsIgnoreCase("total_received")) receivedIndex = i;
        if (name.equalsIgnoreCase("total_finished")) minusLinesIndex = i;
        if (name.equalsIgnoreCase("total_not_finished")) deliveredIndex = i;
        //if (name.equalsIgnoreCase("minus_q")) minusQIndex = i;
    }

    // Calculate totals from visible data
    for (ObservableList<String> row : rows) {
        if (receivedIndex >= 0) {
            try { totalReceived += Double.parseDouble(row.get(receivedIndex)); } catch (Exception ignored) {}
        }
        if (minusLinesIndex >= 0) {
            try { totalMinusLines += Double.parseDouble(row.get(minusLinesIndex)); } catch (Exception ignored) {}
        }
        if (deliveredIndex >= 0) {
            try { totalDelivered += Double.parseDouble(row.get(deliveredIndex)); } catch (Exception ignored) {}
        }
//        if (minusQIndex >= 0) {
//            try { totalMinusQ += Double.parseDouble(row.get(minusQIndex)); } catch (Exception ignored) {}
//        }
    }

    // Add empty row
    ObservableList<String> emptyRow = FXCollections.observableArrayList();
    for (int i = 0; i < table.getColumns().size(); i++) emptyRow.add("");
    rows.add(emptyRow);

    // Add total row
    ObservableList<String> totalRow = FXCollections.observableArrayList();
    for (int i = 0; i < table.getColumns().size(); i++) {
        if (i == receivedIndex) totalRow.add(String.valueOf(totalReceived));
        else if (i == minusLinesIndex) totalRow.add(String.valueOf(totalMinusLines));
        else if (i == deliveredIndex) totalRow.add(String.valueOf(totalDelivered));
//        else if (i == minusQIndex) totalRow.add(String.valueOf(totalMinusQ));
        else if (i == 0) totalRow.add("TOTAL OF QUANTITIES");
        else totalRow.add("");
    }
    rows.add(totalRow);

    // Show updated table
    table.setItems(rows);
}

    
    
    
   // Helper method to find column index by name in JavaFX table
private int findColumnIndex(String name) {
    for (int i = 0; i < table.getColumns().size(); i++) {
        if (table.getColumns().get(i).getText().equalsIgnoreCase(name)) {
            return i;
        }
    }
    return -1;
}
 
    
    
    
    @FXML
    void exportaction(ActionEvent event) throws FileNotFoundException, IOException {

        
        
        
        if (tableselected.equals("report")) {
  

            try {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Production_Report");

    // === Modern Styles ===

    // Header style
    CellStyle headerStyle = workbook.createCellStyle();
    XSSFFont headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setColor(IndexedColors.WHITE.getIndex());
    headerFont.setFontHeightInPoints((short) 11);
    headerStyle.setFont(headerFont);
    headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headerStyle.setBorderBottom(BorderStyle.THIN);
    headerStyle.setBorderTop(BorderStyle.THIN);
    headerStyle.setBorderLeft(BorderStyle.THIN);
    headerStyle.setBorderRight(BorderStyle.THIN);

    // Numeric style with comma separator
    CellStyle numericStyle = workbook.createCellStyle();
    numericStyle.setAlignment(HorizontalAlignment.RIGHT);
    numericStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
    numericStyle.setBorderBottom(BorderStyle.THIN);
    numericStyle.setBorderTop(BorderStyle.THIN);
    numericStyle.setBorderLeft(BorderStyle.THIN);
    numericStyle.setBorderRight(BorderStyle.THIN);

    // Text cell style
    CellStyle textStyle = workbook.createCellStyle();
    textStyle.setBorderBottom(BorderStyle.THIN);
    textStyle.setBorderTop(BorderStyle.THIN);
    textStyle.setBorderLeft(BorderStyle.THIN);
    textStyle.setBorderRight(BorderStyle.THIN);

    // Zebra row styles
    CellStyle zebraLight = workbook.createCellStyle();
    zebraLight.cloneStyleFrom(textStyle);
    zebraLight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    zebraLight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    CellStyle zebraNumericLight = workbook.createCellStyle();
    zebraNumericLight.cloneStyleFrom(numericStyle);
    zebraNumericLight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    zebraNumericLight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    // Total row style
    CellStyle totalStyle = workbook.createCellStyle();
    XSSFFont totalFont = workbook.createFont();
    totalFont.setBold(true);
    totalFont.setColor(IndexedColors.WHITE.getIndex());
    totalStyle.setFont(totalFont);
    totalStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
    totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    totalStyle.setAlignment(HorizontalAlignment.RIGHT);
    totalStyle.setBorderBottom(BorderStyle.THIN);
    totalStyle.setBorderTop(BorderStyle.THIN);
    totalStyle.setBorderLeft(BorderStyle.THIN);
    totalStyle.setBorderRight(BorderStyle.THIN);

    // === Header Row ===
    Row headerRow = sheet.createRow(0);
    for (int j = 0; j < table.getColumns().size(); j++) {
        Cell cell = headerRow.createCell(j);
        cell.setCellValue(table.getColumns().get(j).getText());
        cell.setCellStyle(headerStyle);
    }
    sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, table.getColumns().size() - 1));

    // === Column Indexes ===
    int colReceived = findColumnIndex("Total_Received");
    int colNotFinished = findColumnIndex("Total_Not_Finished");
    int colFinished = findColumnIndex("Total_Finished");

    // === Data Rows ===
    int rowIndex = 1;
    for (int i = 0; i < table.getItems().size(); i++) {
        Row row = sheet.createRow(rowIndex);
        boolean isEvenRow = (i % 2 == 0);

        for (int j = 0; j < table.getColumns().size(); j++) {
            Object value = table.getColumns().get(j).getCellData(i);
            Cell cell = row.createCell(j);

            String colName = table.getColumns().get(j).getText().toLowerCase();

            if (j == colFinished) {
                // Formula: Total_Finished = Total_Received - Total_Not_Finished
                String formula = String.format("%s%d-%s%d",
                        CellReference.convertNumToColString(colReceived),
                        rowIndex + 1,
                        CellReference.convertNumToColString(colNotFinished),
                        rowIndex + 1
                );
                cell.setCellFormula(formula);
                cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);

            } else {
                if (value != null && value.toString().matches("\\d+(\\.\\d+)?")) {
                    double num = Double.parseDouble(value.toString());
                    cell.setCellValue(num);
                    cell.setCellStyle(isEvenRow ? zebraNumericLight : numericStyle);
                } else {
                    cell.setCellValue(value != null ? value.toString() : "");
                    cell.setCellStyle(isEvenRow ? zebraLight : textStyle);
                }
            }
        }
        row.setHeightInPoints(20);
        rowIndex++;
    }

    // === Totals Row ===
    Row totalRow = sheet.createRow(rowIndex);
    Cell totalLabelCell = totalRow.createCell(0);
    totalLabelCell.setCellValue("TOTAL OF QUANTITIES");
    totalLabelCell.setCellStyle(totalStyle);

    for (int j = 1; j < table.getColumns().size(); j++) {
        Cell cell = totalRow.createCell(j);
        String colName = table.getColumns().get(j).getText().toLowerCase();

        if (j == colReceived) {
            // SUM of Total_Received
            String formula = String.format("SUM(%s2:%s%d)",
                    CellReference.convertNumToColString(colReceived),
                    CellReference.convertNumToColString(colReceived),
                    rowIndex
            );
            cell.setCellFormula(formula);

        } else if (j == colNotFinished) {
            // SUM of Total_Not_Finished
            String formula = String.format("SUM(%s2:%s%d)",
                    CellReference.convertNumToColString(colNotFinished),
                    CellReference.convertNumToColString(colNotFinished),
                    rowIndex
            );
            cell.setCellFormula(formula);

        } else if (j == colFinished) {
            // Total_Finished = SUM(Received) - SUM(Not_Finished)
            String formula = String.format("%s%d-%s%d",
                    CellReference.convertNumToColString(colReceived), rowIndex + 1,
                    CellReference.convertNumToColString(colNotFinished), rowIndex + 1
            );
            cell.setCellFormula(formula);

        } else {
            cell.setCellValue("");
        }
        cell.setCellStyle(totalStyle);
    }

    // Auto size columns
    for (int j = 0; j < table.getColumns().size(); j++) {
        sheet.autoSizeColumn(j);
    }

    // === Pivot Table Sheet ===
    XSSFSheet pivotSheet = workbook.createSheet("Production Pivot Table");

    AreaReference source = new AreaReference(
            new CellReference(0, 0),
            new CellReference(rowIndex, table.getColumns().size() - 1),
            workbook.getSpreadsheetVersion()
    );

    CellReference pivotPosition = new CellReference(0, 0);
    XSSFPivotTable pivotTable = pivotSheet.createPivotTable(source, pivotPosition, sheet);

    pivotTable.addRowLabel(0);
    pivotTable.addColumnLabel(DataConsolidateFunction.SUM, colReceived, "Sum of Received");
    pivotTable.addColumnLabel(DataConsolidateFunction.SUM, colFinished, "Sum of Finished");
    pivotTable.addColumnLabel(DataConsolidateFunction.SUM, colNotFinished, "Sum of Not Finished");

    // Style pivot table header
    Row pivotHeaderRow = pivotSheet.getRow(0);
    if (pivotHeaderRow != null) {
        for (Cell cell : pivotHeaderRow) {
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }
    for (int i = 0; i < 6; i++) {
        pivotSheet.autoSizeColumn(i);
    }

    // === Save File ===
    FileChooser dialog = new FileChooser();
    dialog.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
    dialog.setInitialFileName("Production_Report");
    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
    File file = dialog.showSaveDialog(null);
    if (file == null) return;

    try (FileOutputStream fileOut = new FileOutputStream(file)) {
        workbook.write(fileOut);
    }
    workbook.close();

    Desktop.getDesktop().open(file);

} catch (Exception e) {
    e.printStackTrace();
}

            
            
        }
        
        
        
        else {
            
             
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
            
        }
        
        
        
       
        
        
    }
    
     @FXML
    void closepanel(ActionEvent event) throws IOException {
        
        mainSplit1.setVisible(false);
       
    
     try {
    String sqla = " UPDATE Message SET Delivered = 1 WHERE M_To = ? ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, selsecc);
          this.pst.executeUpdate();
              }
              catch (Exception e) {
          
        } finally {
          try {
            this.rs.close();
            this.pst.close();
          } catch (Exception exception) {}
        }  
    
    
    
        
    }
     
      

    
    
    
   @Override
public void initialize(URL url, ResourceBundle rb) {
    
      try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 18);
        } catch (FileNotFoundException ex) {
          
        }
       

    conn = db.java_db();
    startAutoRefresh3();

    // Path to file
    String path = System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Positions.kady";

    List<String> lines = readLinesFromFile(path);

    for (String line : lines) {
        String label = line.trim();

        // Skip empty lines
        if (label.isEmpty()) continue;

        // Skip reserved words
        if (label.equalsIgnoreCase("Manager") ||
            label.equalsIgnoreCase("ADMIN") ||
            label.equalsIgnoreCase("Alpha_Planner")||label.equalsIgnoreCase("------------------------------------------------------------")||label.contains("Production")||
            label.equalsIgnoreCase("Data_Entry")) {
            continue;
        }

     selsecc=LogIn_GUI_Controller.selectedpositionn;
     seluserr=LogIn_GUI_Controller.selecteduser;
        
        
        // Create styled JFXButton
        JFXButton button = new JFXButton(label);
        button.setButtonType(JFXButton.ButtonType.RAISED);
        if (label.equalsIgnoreCase("WareHouse_1")) {
            button.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("warehouse.png"))));
        }
        else if (label.equalsIgnoreCase("WASHING")) {
            button.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("washing.png"))));
        }
        else if (label.equalsIgnoreCase("LAZER")) {
            button.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("laser.png"))));
        }
        else if (label.equalsIgnoreCase("SPRAY")) {
            button.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("spray.png"))));
        }
        else if (label.equalsIgnoreCase("WareHouse_2")) {
            button.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("warehouse.png"))));
        }
        else {
            button.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("anything.png"))));
        }
        button.setStyle("-fx-background-color: #dde5b6; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius:3em");
        button.setPrefHeight(40);
        button.setPrefWidth(180);
        button.setOnAction(e-> {
            
            
        Alert alertd = new Alert(Alert.AlertType.CONFIRMATION);
        alertd.setTitle("");
        alertd.setHeaderText("أحلي مسا عليك ي مدير ");
        alertd.setContentText("ماذا تريد ان تفعل الان؟");
        ButtonType buttonTypeOned = new ButtonType("استوك الشحنات");
        ButtonType buttonTypeCanceld = new ButtonType("الانتاجية");
        ButtonType cancelBtn = new ButtonType("الغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertd.getButtonTypes().setAll(buttonTypeOned, buttonTypeCanceld, cancelBtn);
        DialogPane dialogPaneid = alertd.getDialogPane();
        dialogPaneid.setMinSize(800, 200);
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
         
            tableselected="stock";
   ////////////////////////////////////START///////////////////////////////////////////
            
   table.getColumns().clear();
ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

double totalReceived = 0;
double totalMinusLines = 0;
double totalDelivered = 0;
double totalMinusQ = 0;

try {
    String sql = "select * from " + button.getText();
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    // Create table columns
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // Fill table data and calculate totals
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            String value = rs.getString(i);
            row.add(value);

            String columnName = rs.getMetaData().getColumnName(i);

            try {
                double numericValue = Double.parseDouble(value);

                if (columnName.equalsIgnoreCase("received")) {
                    totalReceived += numericValue;
                } else if (columnName.equalsIgnoreCase("minus_lines")) {
                    totalMinusLines += numericValue;
                } else if (columnName.equalsIgnoreCase("Delivered")) {
                    totalDelivered += numericValue;
                } else if (columnName.equalsIgnoreCase("Minus_Q")) {
                    totalMinusQ += numericValue;
                }
            } catch (NumberFormatException ignored) {
                // ignore non-numeric values
            }
        }
        data.add(row);
    }

    // Add an empty row before total row
    ObservableList<String> emptyRow = FXCollections.observableArrayList();
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
        emptyRow.add("");
    }
    data.add(emptyRow);

    // Add total row
    ObservableList<String> totalRow = FXCollections.observableArrayList();
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
        String columnName = rs.getMetaData().getColumnName(i);

        if (columnName.equalsIgnoreCase("received")) {
            totalRow.add(String.valueOf(totalReceived));
        } else if (columnName.equalsIgnoreCase("minus_lines")) {
            totalRow.add(String.valueOf(totalMinusLines));
        } else if (columnName.equalsIgnoreCase("Delivered")) {
            totalRow.add(String.valueOf(totalDelivered));
        } else if (columnName.equalsIgnoreCase("Minus_Q")) {
            totalRow.add(String.valueOf(totalMinusQ));
        } else if (i == 1) {
            totalRow.add("TOTAL OF QUANTITIES");
        } else {
            totalRow.add("");
        }
    }
    data.add(totalRow);

    table.setItems(data);

    rs.close();
    pst.close();

    TableFilter filter = new TableFilter(table);

} catch (Exception fd) {
    fd.printStackTrace();
}

    ///////////////////////////////////////////END////////////////////////////////////////
            
        }
            
        else if (resultsd.isPresent() && resultsd.get() == buttonTypeCanceld) {
                tableselected="productivity";
                 ////////////////////////////////////START///////////////////////////////////////////
            
   table.getColumns().clear();
ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

double totalReceived = 0;
double totalMinusLines = 0;
double totalDelivered = 0;
double totalMinusQ = 0;

try {
    String sql = "select * from " + button.getText()+"_Production";
    pst = conn.prepareStatement(sql);
    rs = pst.executeQuery();

    // Create table columns
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
        table.getColumns().add(col);
    }

    // Fill table data and calculate totals
    while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            String value = rs.getString(i);
            row.add(value);

            String columnName = rs.getMetaData().getColumnName(i);

            try {
                double numericValue = Double.parseDouble(value);

                if (columnName.equalsIgnoreCase("received")) {
                    totalReceived += numericValue;
                } else if (columnName.equalsIgnoreCase("finished")) {
                    totalMinusLines += numericValue;
                } else if (columnName.equalsIgnoreCase("not_finished")) {
                    totalDelivered += numericValue;
                } 
//                else if (columnName.equalsIgnoreCase("Minus_Q")) {
//                    totalMinusQ += numericValue;
//                }
            } catch (NumberFormatException ignored) {
                // ignore non-numeric values
            }
        }
        data.add(row);
    }

    // Add an empty row before total row
    ObservableList<String> emptyRow = FXCollections.observableArrayList();
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
        emptyRow.add("");
    }
    data.add(emptyRow);

    // Add total row
    ObservableList<String> totalRow = FXCollections.observableArrayList();
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
        String columnName = rs.getMetaData().getColumnName(i);

        if (columnName.equalsIgnoreCase("received")) {
            totalRow.add(String.valueOf(totalReceived));
        } else if (columnName.equalsIgnoreCase("finished")) {
            totalRow.add(String.valueOf(totalMinusLines));
        } else if (columnName.equalsIgnoreCase("not_finished")) {
            totalRow.add(String.valueOf(totalDelivered));
        } 
//        else if (columnName.equalsIgnoreCase("Minus_Q")) {
//            totalRow.add(String.valueOf(totalMinusQ));
//        } 
        
        else if (i == 1) {
            totalRow.add("TOTAL OF QUANTITIES");
        } else {
            totalRow.add("");
        }
    }
    data.add(totalRow);

    table.setItems(data);

    rs.close();
    pst.close();

    TableFilter filter = new TableFilter(table);

} catch (Exception fd) {
    fd.printStackTrace();
}

    ///////////////////////////////////////////END////////////////////////////////////////
        
            
        }
            
        else {
            return;
        }    
            
                
        
            
            ////////////////////////////////////////////////////////////////////
            
        });
        sectionspanel.getChildren().add(button);
    }
    
    

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
    
    





private List<String> readLinesFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }
    
    
}
