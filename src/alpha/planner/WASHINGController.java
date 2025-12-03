// WASHINGController.java
package alpha.planner;

import com.gluonhq.charm.glisten.animation.BounceOutLeftTransition;
import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import com.gluonhq.charm.glisten.animation.BounceOutRightTransition;
import com.gluonhq.charm.glisten.animation.PulseTransition;
import com.gluonhq.charm.glisten.animation.ShakeTransition;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.BooleanSupplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.annotation.PreDestroy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

public class WASHINGController implements Initializable {

    @FXML private VBox sidePanel;
    @FXML private VBox shipmentList;
    
    Timer fileCheckTimer;
    
    @FXML private VBox sidePanel1;
    @FXML private VBox shipmentList1;
    @FXML private SplitPane mainSplit1;
    
    @FXML private MFXToggleButton togglePanelBtn2;
    @FXML
    private SplitPane mainSplit;
    private final Map<Integer, CheckBox> shipmentCheckboxes = new HashMap<>();
    private Timeline refreshTimeline,refreshTimeline2,refreshTimeline3;
    @FXML
    private Menu onlineusersmenu,ships,fill,hel;
    @FXML
    private MenuItem refresh,qitt,excc,shippo,trashippo,setto;
    @FXML
    private MenuItem stocky,loogy,barcode;
    @FXML
    private TableView<ObservableList<String>> table;
    Connection conn = null ;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public static String selsecc,seluserr;
    public static String value1;
    public static String ppo,ssapcode,sstyle,ccustomer,wwashname,ppoamount,ccuttingamount,llaundrydate,xxfacdate,receivedd,minuslinee;
    ContextMenu contextMenu;
    @FXML
    private VBox sido;
    @FXML
    private ImageView messenger;

    @FXML
    private ImageView inbox;
    
    @FXML
    private Label result;
    
    public static String diro;
    
    PulseTransition pt;
    ShakeTransition st;
    
    
    public static Font cairoSemiBold;
    
    
    //////////////////////////////////////////////////////////////////////////////
    
    @FXML
    private ImageView avatar;
    
    @FXML
    private Label username;
    
    
    @FXML
    private ImageView reffo;

    @FXML
    private ImageView zeroo;

    @FXML
    private ImageView stocko;

    @FXML
    private ImageView expotyo;

    @FXML
    private ImageView shipa;

    @FXML
    private ImageView traco;

    @FXML
    private ImageView prodo;

    @FXML
    private ImageView settaa;
    
    @FXML
    private ImageView logiout;

    @FXML
    private ImageView exito;
    
    @FXML
    private VBox gggg;
    
    
    
//    
//    // -------------------------
//    // GuidedTour implementation
//    // -------------------------
//    public static class GuidedTour {
//        private final Scene scene;
//        private final Pane rootPane;          // app root (must be Pane or subclass)
//        private final Rectangle dimRect;      // dim background (mouseTransparent so underlying controls are clickable)
//        private final Pane dimLayer;          // contains dimRect
//        private final Pane uiLayer;           // contains tooltip + pointer (small bounds only)
//        private final VBox tooltip;
//        private final Label messageLabel;
//        private final Label hintLabel;
//        private final Polygon pointer;
//        private final List<TourStep> steps = new ArrayList<>();
//        private int currentIndex = -1;
//        private Timeline moveAnim;
//        private TourStep activeStep = null;
//        private Scene scenei;
//
//        public GuidedTour(Scene scene, Pane rootPane) {
//            
//             if (scene == null || rootPane == null) {
//        throw new IllegalArgumentException("Scene or root is null — GuidedTour cannot start yet!");
//    }
//    this.scene = scene;
//    this.rootPane = rootPane;
//            
//         //   this.scene = scene;
//          //  this.rootPane = rootPane;
//
//            // Dim layer (visual only) — allow mouse to pass through so user can click targets
//            dimRect = new Rectangle();
//            dimRect.setFill(Color.rgb(0, 0, 0, 0.45));
//            dimRect.setMouseTransparent(true); // very important: let clicks go through
//            dimLayer = new Pane(dimRect);
//            dimLayer.setPickOnBounds(false); // don't intercept clicks
//
//            // UI layer (holds tooltip + pointer) — only occupies area of tooltip/pointer
//            uiLayer = new Pane();
//            uiLayer.setPickOnBounds(false);
//
//            // Tooltip styling (blue box like Retool)
//            messageLabel = new Label();
//            messageLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
//            messageLabel.setWrapText(true);
//
//            hintLabel = new Label("Press Enter or complete the action");
//            hintLabel.setStyle("-fx-text-fill: #E3F2FD; -fx-font-size: 12px;");
//
//            tooltip = new VBox(8, messageLabel, hintLabel);
//            tooltip.setPadding(new Insets(10));
//            tooltip.setMaxWidth(300);
//            tooltip.setBackground(new Background(new BackgroundFill(Color.web("#2D8BF0"), new CornerRadii(8), Insets.EMPTY)));
//            tooltip.setEffect(new DropShadow(10, Color.rgb(0,0,0,0.35)));
//            tooltip.setMouseTransparent(false); // must receive mouse events for Next buttons if added later
//
//            // Pointer triangle (visual only) — let clicks pass through pointer
//            pointer = new Polygon(0, 0, 14, 10, 0, 20);
//            pointer.setFill(Color.web("#2D8BF0"));
//            pointer.setMouseTransparent(true); // don't block clicks to underlying nodes
//
//            // Add UI elements to layers
//            uiLayer.getChildren().addAll(pointer, tooltip);
//
//            // Add both layers to root (dim underneath, uiLayer on top)
//            rootPane.getChildren().addAll(dimLayer, uiLayer);
//
//            // Keep dimRect sized to scene
//            scene.widthProperty().addListener((o,a,b) -> resizeDim());
//            scene.heightProperty().addListener((o,a,b) -> resizeDim());
//            resizeDim();
//
//            // keyboard validate
//            scene.setOnKeyPressed(ev -> {
//                if (ev.getCode() == KeyCode.ENTER) {
//                    validateCurrentStep();
//                }
//            });
//        }
//
//        public void addStep(String text, Region target, BooleanSupplier condition) {
//            steps.add(new TourStep(text, target, condition));
//        }
//
//        public void start() {
//            if (steps.isEmpty()) return;
//            showStep(0);
//        }
//
//        private void showStep(int index) {
//            // remove listeners on previous active step
//            detachListenersFromActiveStep();
//
//            if (index < 0 || index >= steps.size()) return;
//            currentIndex = index;
//            activeStep = steps.get(index);
//
//            messageLabel.setText(activeStep.text);
//
//            // ensure tooltip has measured size before computing placement
//            // schedule on next pulse to let layout compute sizes
//            Platform.runLater(() -> {
//                // ensure tooltip gets its CSS applied and layout bounds computed
//                tooltip.applyCss();
//                tooltip.autosize();
//                tooltip.layout();
//
//                moveTooltipToTarget(activeStep.target);
//                // attach auto listeners for common controls (Button, TextField)
//                attachAutoListenersFor(activeStep);
//            });
//        }
//
//        private void moveTooltipToTarget(Region target) {
//            // compute bounds of target in root coordinate space
//            Bounds targetSceneBounds = target.localToScene(target.getBoundsInLocal());
//            Point2D targetTopLeftInRoot = rootPane.sceneToLocal(targetSceneBounds.getMinX(), targetSceneBounds.getMinY());
//            Point2D targetBottomRightInRoot = rootPane.sceneToLocal(targetSceneBounds.getMaxX(), targetSceneBounds.getMaxY());
//
//            double tX = targetTopLeftInRoot.getX();
//            double tY = targetTopLeftInRoot.getY();
//            double tW = targetBottomRightInRoot.getX() - tX;
//            double tH = targetBottomRightInRoot.getY() - tY;
//
//            double gap = 12;
//
//            // ensure tooltip measured size
//            double tw = tooltip.getWidth() <= 0 ? tooltip.prefWidth(-1) : tooltip.getWidth();
//            double th = tooltip.getHeight() <= 0 ? tooltip.prefHeight(-1) : tooltip.getHeight();
//
//            // decide to place tooltip left or right depending on available space
//            double sceneW = scene.getWidth();
//            double preferredRightX = tX + tW + gap;
//            double preferredLeftX  = tX - gap - tw;
//
//            boolean placeRight = (preferredRightX + tw + 20) <= sceneW; // +20 margin
//            double tooltipX = placeRight ? preferredRightX : Math.max(8, preferredLeftX);
//            double tooltipY = tY + (tH - th) / 2.0;
//
//            // clamp tooltipY within scene vertical bounds
//            tooltipY = Math.max(8, Math.min(tooltipY, scene.getHeight() - th - 8));
//
//            // pointer position: place between target center and tooltip edge
//            double targetCenterY = tY + tH / 2.0;
//            double pointerY = targetCenterY - 10; // 10 is half pointer height
//            double pointerX;
//            if (placeRight) {
//                pointerX = tX + tW + gap + 4; // a bit near tooltip-left
//                pointer.setRotate(0); // pointing right (triangle apex to the right)
//            } else {
//                // when tooltip on left, place pointer to the right of tooltip and rotate 180
//                pointerX = tooltipX + tw + 4;
//                pointer.setRotate(180);
//            }
//
//            // If pointer or tooltip layout change, animate properties smoothly
//            if (moveAnim != null) moveAnim.stop();
//
//            // Make sure tooltip/pointer have initial layout values
//            if (Double.isNaN(tooltip.getLayoutX())) tooltip.setLayoutX(tooltipX);
//            if (Double.isNaN(tooltip.getLayoutY())) tooltip.setLayoutY(tooltipY);
//            if (Double.isNaN(pointer.getLayoutX())) pointer.setLayoutX(pointerX);
//            if (Double.isNaN(pointer.getLayoutY())) pointer.setLayoutY(pointerY);
//
//            // Build animation timeline
//            KeyValue kvTooltipX = new KeyValue(tooltip.layoutXProperty(), tooltipX, Interpolator.EASE_BOTH);
//            KeyValue kvTooltipY = new KeyValue(tooltip.layoutYProperty(), tooltipY, Interpolator.EASE_BOTH);
//            KeyValue kvPointerX = new KeyValue(pointer.layoutXProperty(), pointerX, Interpolator.EASE_BOTH);
//            KeyValue kvPointerY = new KeyValue(pointer.layoutYProperty(), pointerY, Interpolator.EASE_BOTH);
//
//            moveAnim = new Timeline(new KeyFrame(Duration.millis(450), kvTooltipX, kvTooltipY, kvPointerX, kvPointerY));
//            moveAnim.play();
//        }
//
//        private void validateCurrentStep() {
//            if (activeStep == null) return;
//            if (activeStep.condition.getAsBoolean()) {
//                // advance
//                currentIndex++;
//                if (currentIndex < steps.size()) {
//                    showStep(currentIndex);
//                } else {
//                    finish();
//                }
//            } else {
//                // visual feedback
//                shake(tooltip);
//            }
//        }
//        
//          
//  public void setScene(Scene scene) {
//    this.scenei = scene;
//}
//
//
//        private void finish() {
//            detachListenersFromActiveStep();
//            FadeTransition ft = new FadeTransition(Duration.millis(350), uiLayer);
//            ft.setFromValue(1);
//            ft.setToValue(0);
//            ft.setOnFinished(e -> {
//                rootPane.getChildren().remove(uiLayer);
//                rootPane.getChildren().remove(dimLayer);
//            });
//            ft.play();
//        }
//
//        private void resizeDim() {
//            dimRect.setWidth(scene.getWidth());
//            dimRect.setHeight(scene.getHeight());
//        }
//
//        private void shake(Node node) {
//            TranslateTransition tt = new TranslateTransition(Duration.millis(60), node);
//            tt.setFromX(0);
//            tt.setByX(10);
//            tt.setCycleCount(6);
//            tt.setAutoReverse(true);
//            tt.play();
//        }
//
//        // --------------------------------------------------
//        // Auto-listener management: attach/detach convenience
//        // --------------------------------------------------
//        private void attachAutoListenersFor(TourStep step) {
//            // If target is Button -> add a temporary ActionEvent handler
//            if (step.target instanceof Button) {
//                Button btn = (Button) step.target;
//                EventHandler<ActionEvent> handler = e -> {
//                    // small delay to let the button's own action run
//                    Platform.runLater(() -> {
//                        if (step.condition.getAsBoolean()) {
//                            validateCurrentStep();
//                        }
//                    });
//                };
//                btn.addEventHandler(ActionEvent.ACTION, handler);
//                step.buttonHandler = handler;
//            }
//
//            // If target is TextInputControl (TextField/TextArea) -> watch text changes
//            if (step.target instanceof TextInputControl) {
//                TextInputControl tf = (TextInputControl) step.target;
//                ChangeListener<String> txtListener = (obs, oldV, newV) -> {
//                    if (step.condition.getAsBoolean()) {
//                        Platform.runLater(this::validateCurrentStep);
//                    }
//                };
//                tf.textProperty().addListener(txtListener);
//                step.textListener = txtListener;
//            }
//
//            // You can add more control-specific listeners (ComboBox.valueProperty etc.) here
//        }
//
//        private void detachListenersFromActiveStep() {
//            if (activeStep == null) return;
//            if (activeStep.buttonHandler != null && activeStep.target instanceof Button) {
//                ((Button) activeStep.target).removeEventHandler(ActionEvent.ACTION, activeStep.buttonHandler);
//                activeStep.buttonHandler = null;
//            }
//            if (activeStep.textListener != null && activeStep.target instanceof TextInputControl) {
//                ((TextInputControl) activeStep.target).textProperty().removeListener(activeStep.textListener);
//                activeStep.textListener = null;
//            }
//            activeStep = null;
//        }
//
//        // --------------------------------------
//        // TourStep container (holds temporary listeners)
//        // --------------------------------------
//        private static class TourStep {
//            final String text;
//            final Region target;
//            final BooleanSupplier condition;
//
//            // temporary listeners so we can remove them later
//            EventHandler<ActionEvent> buttonHandler = null;
//            ChangeListener<String> textListener = null;
//
//            TourStep(String text, Region target, BooleanSupplier condition) {
//                this.text = text;
//                this.target = target;
//                this.condition = condition;
//            }
//        }
//    }
//    
//    
    
    
    
    
    
    
    
    
    
    @FXML
    void tutorialsaction(ActionEvent event) {

        
        File ffd=new File (System.getProperty("user.home")+"\\"+"config.txt");
        ffd.delete();
        
        //Noti
        
        Notifications.create()
        .title("Successful")
        .text("البرنامج هيقفل حالا وافتحه تاني هيعرض الارشادات")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();
        
        // Example: wait 3 seconds then run code
PauseTransition pauset = new PauseTransition(Duration.seconds(3));
pauset.setOnFinished(eventy -> {
    
    
    try {
      
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
    
    if (refreshTimeline != null) {
        refreshTimeline.stop();
    }
    
    if (refreshTimeline2 != null) {
        refreshTimeline2.stop();
    }
    
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
    
        
    }catch (Exception md){}
    
    
});
pauset.play();
        
        
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



private int currentStep = 0;
private List<TourStep> steps = new ArrayList<>();
private Pane overlayPane;
String CONFIG_FILE = System.getProperty("user.home")+"\\"+"config.txt";
@FXML private StackPane mainRoot;


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

private boolean isFirstLaunch() {
    File file = new File(CONFIG_FILE);
    return !file.exists();
}

private void saveFirstLaunchFlag() {
    try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
        writer.write("launched=true");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void showStep(StackPane root, Scene scene) {
    // ✅ لو خلصت الخطوات شيل الـ overlay
    if (currentStep >= steps.size()) {
        if (overlayPane != null) {
            fadeOutAndRemove(root, overlayPane);
            overlayPane = null;
        }
        return;
    }

    TourStep step = steps.get(currentStep);
    Node target = step.node;

    // شيل القديم
    if (overlayPane != null) {
        root.getChildren().remove(overlayPane);
    }

    overlayPane = new Pane();
    overlayPane.setPickOnBounds(false);

    // خلفية غامقة
    Rectangle background = new Rectangle();
    background.widthProperty().bind(scene.widthProperty());
    background.heightProperty().bind(scene.heightProperty());
    background.setFill(Color.rgb(0, 0, 0, 0.6));

    // مربع التحديد (Highlight)
    Rectangle highlight = new Rectangle();
    highlight.setArcWidth(20);
    highlight.setArcHeight(20);
    highlight.setFill(Color.TRANSPARENT);
    highlight.setStroke(Color.web("#03A9F4"));
    highlight.setStrokeWidth(3);
    highlight.setEffect(new DropShadow(20, Color.web("#03A9F4")));

    // كارت المعلومات
    Label info = new Label(step.message);
    info.setWrapText(true);
    info.setMaxWidth(300);
    info.setStyle(
        "-fx-background-color: linear-gradient(to bottom right, #ff7eb9, #ff758c, #ff3c7e);" + // تدرج خيالي
        "-fx-padding: 18 25 18 25;" + // padding أكبر للتأثير
        "-fx-font-size: 18px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: white;" + // لون النص أبيض
        "-fx-background-radius: 30 5 30 5;" + // حواف غير متماثلة لإحساس فني
        "-fx-border-radius: 30 5 30 5;" +
        "-fx-border-width: 2;" +
        "-fx-border-color: rgba(255,255,255,0.5);" + // حدود شبه شفافة
        "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.4), 15, 0.5, 0, 0)," +
                      " innershadow(gaussian, rgba(0,0,0,0.25), 6, 0.5, 0, 2);" // تداخل ظل خارجي وداخلي
);

//    info.setStyle(
//            "-fx-background-color: white; " +
//            "-fx-padding: 15; " +
//            "-fx-font-size: 16px; " +
//            "-fx-font-weight: bold; " +
//            "-fx-background-radius: 12; " +
//            "-fx-border-radius: 12; " +
//            "-fx-border-color: #E0E0E0;" +
//            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 8, 0, 0, 2);"
//    );

    javafx.scene.control.Button nextBtn = new javafx.scene.control.Button(
            currentStep == steps.size() - 1 ? "انهاء" : "التالي →"
    );
    nextBtn.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 6 18;");
    nextBtn.setOnAction(e -> {
        currentStep++;
        showStep(root, scene);
    });

    javafx.scene.control.Button skipBtn = new javafx.scene.control.Button("تخطي");
    skipBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-underline: true;");
    skipBtn.setOnAction(e -> {
        fadeOutAndRemove(root, overlayPane);
        overlayPane = null;
    });

    VBox card = new VBox(10, info, nextBtn, skipBtn);
    card.setStyle("-fx-font-family:'Cairo SemiBold';");
    card.setAlignment(Pos.CENTER);

    overlayPane.getChildren().addAll(background, highlight, card);
    root.getChildren().add(overlayPane); // ضيف overlay فوق الكل

    // تحديث مكان highlight والكارت
    Runnable updateHighlight = () -> {
        Bounds bounds = target.localToScene(target.getBoundsInLocal());
        Point2D point = overlayPane.sceneToLocal(bounds.getMinX(), bounds.getMinY());

        highlight.setX(point.getX() - 10);
        highlight.setY(point.getY() - 10);
        highlight.setWidth(bounds.getWidth() + 20);
        highlight.setHeight(bounds.getHeight() + 20);

        card.setLayoutX(point.getX());
        card.setLayoutY(point.getY() + bounds.getHeight() + 20);
    };

    updateHighlight.run();

    scene.widthProperty().addListener((obs, o, n) -> updateHighlight.run());
    scene.heightProperty().addListener((obs, o, n) -> updateHighlight.run());
    target.layoutBoundsProperty().addListener((obs, o, n) -> updateHighlight.run());

    // حركة fade in
    FadeTransition fade = new FadeTransition(Duration.seconds(0.4), overlayPane);
    fade.setFromValue(0);
    fade.setToValue(1);
    fade.play();
}

// Smooth fade out and remove
private void fadeOutAndRemove(StackPane root, Pane pane) {
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), pane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setOnFinished(ev -> root.getChildren().remove(pane));
    fadeOut.play();
}

// كلاس يمثل خطوة من خطوات التور
private static class TourStep {
    Node node;
    String message;
    TourStep(Node node, String message) {
        this.node = node;
        this.message = message;
    }
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    
    
    
    
    @FXML
    void avataraction(MouseEvent event) throws IOException  {
        
Stage stg = new Stage();
Parent root = FXMLLoader.<Parent>load(getClass().getResource("Profile.fxml"));
Scene sce = new Scene(root);
stg.setTitle("البروفايل بتاعي");
stg.centerOnScreen();
stg.setResizable(false);
stg.setMaximized(false);
stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
stg.setScene(sce);
stg.setAlwaysOnTop(true);
stg.centerOnScreen();
stg.show();
        
    }
    
    
    
    //////////////////////////////////////////////////////////////////////////////
      
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
     
      
      
     @FXML
    void refshipsaction(ActionEvent event) throws IOException {
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Refused_Shipments.fxml"));
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
    stg.setTitle("Refused Shipments");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.centerOnScreen();
    //stg.getIcons().add(new Image(Main.class.getResourceAsStream("washing.png")));
    stg.show();
    }
    
     ////////////////////////////////////////////////////////////////////////////////////////////////
     
    @FXML
    void imgexitaction(MouseEvent event) {
        
        qitt.fire();

    }

    @FXML
    void imgexpaction(MouseEvent event) {
        
        excc.fire();

    }

    @FXML
    void imgrefraction(MouseEvent event) {
        
        refresh.fire();

    }

    @FXML
    void imgsetaction(MouseEvent event) {
        
        setto.fire();
        
    }

    @FXML
    void imgshipaction(MouseEvent event) {
        
        shippo.fire();

    }

    @FXML
    void imgshiptraaction(MouseEvent event) {
        
        trashippo.fire();

    }

    @FXML
    void imgstkaction(MouseEvent event) {
        
        stocky.fire();

    }

    @FXML
    void imglogaction(MouseEvent event) {
        
        loogy.fire();

    }
    
    
    
       @FXML
    void autodelete(MouseEvent event) {

         
   
   /////////////////////////////////////////////////////////////////////////////////////////////////////
   
   try {
    // Step 1: Delete from washing where received = 0
    String sqlDeleteWashing = "DELETE FROM " + selsecc + " WHERE Received = 0";
    try (PreparedStatement pst1 = conn.prepareStatement(sqlDeleteWashing)) {
        int deleted1 = pst1.executeUpdate();
        System.out.println("Deleted " + deleted1 + " rows from " + selsecc);
        
 
        
    }

    // Step 2: Delete from washing_production where not_finished = 0
    String sqlDeleteWashingProd = "DELETE FROM " + selsecc + "_Production WHERE Not_Finished = 0";
    try (PreparedStatement pst2 = conn.prepareStatement(sqlDeleteWashingProd)) {
        int deleted2 = pst2.executeUpdate();
        System.out.println("Deleted " + deleted2 + " rows from " + selsecc + "_Production");
        

        
    }

    // Step 3: Audit log entry
    String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?,?,?,?)";
    try (PreparedStatement pst3 = conn.prepareStatement(sqla)) {
        pst3.setString(1, value1);   // date
        pst3.setString(2, selsecc);  // section
        pst3.setString(3, seluserr); // user
        pst3.setString(4, "Auto-deleted zero entries from " + selsecc + " & " + selsecc + "_Production.");
        pst3.executeUpdate();
    }
    
    
    
    Notifications.create()
        .title("Successful Query")
        .text("كل الحاجات اللي اتصفرت اتمسحت تمام ي معلم")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();
    

} catch (SQLException e) {
    e.printStackTrace();
    System.err.println("SQL error: " + e.getMessage());
}

   
   
    ///////////////////////////////////////////////////////////////////////////////////////////////////
   

        table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from "+selsecc+"";
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
    void productionn(MouseEvent event) throws IOException {
        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Production.fxml"));
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
    stg.setTitle("تسجيل الانتاج");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();

    }
     
     ////////////////////////////////////////////////////////////////////////////////////////////////
    
    @FXML
    void refreshaction(ActionEvent event) {
        

          table.getColumns().clear();
        
        ////////////////////////////////////////////////////////////////////
        ObservableList <ObservableList> data;
        data=FXCollections.observableArrayList();
        
        ////////////////////////////////////////////////////////////////////
       
         try{
            
            String sql ="select * from "+selsecc+"";
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
    void settingsaction(ActionEvent event) throws IOException {

        
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
    void excelaction(ActionEvent event) throws FileNotFoundException, IOException {

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
         ////////////////////////Audit//////////////////////////
      
    try {
    String sqla = "insert into Audit (Date, Section, User, Notes) values (?,?,?,?) ";
          this.pst = this.conn.prepareStatement(sqla);
          this.pst.setString(1, value1);
          this.pst.setString(2, selsecc);
          this.pst.setString(3, seluserr);
          this.pst.setString(4, seluserr+" Created Excel Report.");
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
    
    
    
    @FXML
    void stockyaction(ActionEvent event) {
        
        
        
    try {
    String sqlll = "SELECT SUM(Received) AS TotalReceived FROM "+selsecc+"";
    pst = conn.prepareStatement(sqlll);
    rs = pst.executeQuery(); // ✅ Assign the result set

    int totalReceived = 0;
    if (rs.next()) {
        totalReceived = rs.getInt("TotalReceived");
    }

    Notifications.create()
        .title("Successful Query")
        .text("Stock in "+selsecc+" is:   " + totalReceived + "   PCS.")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();

} catch (Exception ex) {
    ex.printStackTrace();
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}    
        
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
                
                
         try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LogIn_GUI_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }        
        Platform.exit();
        
    }
    
    @FXML
    void shipmentpathaction1(ActionEvent event) {
        
               //Manual
        try {
    Parent content = FXMLLoader.load(getClass().getResource("ShipmentPath2.fxml"));

    Stage stage = new Stage();
    stage.setTitle("مسار الشحنة يدويا");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

//    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}

             
//         }
        

    }

     @FXML
    void shipmentpathaction2(ActionEvent event) {
        
            
             //Recipe
             
             try {
    Parent content = FXMLLoader.load(getClass().getResource("ShipmentPath1.fxml"));

    Stage stage = new Stage();
    stage.setTitle("مسار الشحنة عن طريق الريسيبي");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

//    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}

      
        

//        }
        
    }
   
    
    
    @FXML
    void trackshipmentaction(ActionEvent event) throws IOException {

        
    Stage stg = new Stage();
    Parent root = FXMLLoader.<Parent>load(getClass().getResource("Track_Shipment.fxml"));
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
    stg.setTitle("Track Shipment");
    stg.centerOnScreen();
    stg.setResizable(true);
    stg.setMaximized(true);
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
        
        
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
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.setAlwaysOnTop(true);
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
    
    if (refreshTimeline != null) {
        refreshTimeline.stop();
    }
    
    if (refreshTimeline2 != null) {
        refreshTimeline2.stop();
    }
    
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
    stg.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));
    stg.setScene(sce);
    stg.setAlwaysOnTop(true);
    //stg.getIcons().add(new Image(AlphaPlanner.class.getResourceAsStream(".png")));
    stg.show();
   
    }
    
    
     @FXML
    void toggleShipmentPanel2(MouseEvent event) {

        if (togglePanelBtn2.isSelected()==true) {
            mainSplit.setVisible(true);
        }
        else {
            mainSplit.setVisible(false);
        }
        
    }
    
    
    
    
  
      @FXML
  void barcodeaction(ActionEvent event) throws IOException {
      
      if (seluserr.equals("Laundry"+"1")||seluserr.equals("Laundry"+"2")||seluserr.equals("Laundry"+"3")||seluserr.equals("Laundry"+"4")||seluserr.equals("Laundry"+"5")||seluserr.equals("Laundry"+"6")
              ||seluserr.equals("Laundry"+"7")||seluserr.equals("Laundry"+"8")||seluserr.equals("Laundry"+"9")||seluserr.equals("Laundry"+"10")) {
      BarCode_Production nnc=new BarCode_Production();
      nnc.start(new Stage());
      }
      
      else {
      barcode.setDisable(true);
      }
      
      
      
  }
    
  
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
               
        try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WASHINGController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        conn = db.java_db();
        
        
        
//        /////////////////////////////////////////////////////////////////////////////////////////////////
//        
//      // Defer the tour setup until the scene is ready
//    Platform.runLater(() -> {
//        Scene senoo = table.getScene();
//        if (senoo == null) {
//            System.out.println("Scene still null!");
//            return;
//        }
//
//        
//        GuidedTour tour = new GuidedTour(senoo, mainRoot);
//        
//        // Step 1: type "Flutter" into input
//        tour.addStep("Let's build our first app.\nType 'Flutter' in the box.",
//                username,
//                () -> true);
//        
//        
////        StackPane imgWrapper = new StackPane(reffo);
////tour.addStep("Look at this image!", imgWrapper, () -> true);
//
//        
//        tour.addStep("Look at this image!",
//                gggg,
//                () -> true);
//
////        // Step 2: click the Run button (auto-detected)
////        tour.addStep("Great! Now click the Run button.",
////                togglePanelBtn2,
////                () -> togglePanelBtn2.isFocused() || "Run clicked ✓".equals(result.getText()) /* fallback */);
//
//        // Step 3: final message
//        tour.addStep("Awesome 🎉 You finished the tour!", result, () -> true);
//
//        tour.start();
//    });
//        
//        
//       /////////////////////////////////////////////////////////////////////////////////////////////////// 
//        
        
        
        
        // Start refreshing every 5,3 seconds.
        startAutoRefresh();
        startAutoRefresh2();
        startAutoRefresh3();
        // Initial fetch after 5 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> fetchAndDisplayShipments());
        delay.play();
        
    Date currentDate = GregorianCalendar.getInstance().getTime();
    DateFormat df = DateFormat.getDateInstance();
    String dateString = df.format(currentDate);
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeString = sdf.format(d);
    value1 = timeString;
    
    selsecc=LogIn_GUI_Controller.selectedpositionn;
    seluserr=LogIn_GUI_Controller.selecteduser;
    
    if (selsecc.toLowerCase().contains("washing")) {
        
        //Enable
        
        barcode.setDisable(false);
    }
    
    else {
        
        //Disable
        
        barcode.setDisable(true);
    }
    
    
    //DB////
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    
   try {
    // Check if main table exists
    String checkTableSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + selsecc + "'";
    this.pst = this.conn.prepareStatement(checkTableSql);
    ResultSet rs = this.pst.executeQuery();
    boolean tableExists = rs.next();
    rs.close();
    this.pst.close();

    if (!tableExists) {
        // Create main table
        String sqlCreate = "CREATE TABLE " + selsecc + " (" +
                "PO TEXT NOT NULL, " +
                "Sap_Code TEXT NOT NULL, " +
                "Style TEXT NOT NULL, " +
                "Customer TEXT NOT NULL, " +
                "Wash_Name TEXT NOT NULL, " +
                "PO_Amount TEXT NOT NULL, " +
                "Cutting_Amount TEXT NOT NULL, " +
                "Incoming_Date TEXT NOT NULL, " +
                "X_Fac_Date TEXT NOT NULL, " +
                "Received TEXT NOT NULL" +
                ")";
        this.pst = this.conn.prepareStatement(sqlCreate);
        this.pst.execute();
        this.pst.close();
    }

    // Check if production table exists
    String checkProdSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + selsecc + "_Production'";
    this.pst = this.conn.prepareStatement(checkProdSql);
    rs = this.pst.executeQuery();
    boolean prodExists = rs.next();
    rs.close();
    this.pst.close();

    if (!prodExists) {
        // Create production table
        String sqlCreate2 = "CREATE TABLE " + selsecc + "_Production (" +
                "PO TEXT NOT NULL, " +
                "Sap_Code TEXT NOT NULL, " +
                "Style TEXT NOT NULL, " +
                "Customer TEXT NOT NULL, " +
                "Wash_Name TEXT NOT NULL, " +
                "Received TEXT NOT NULL, " +
                "Finished TEXT NOT NULL, " +
                "Not_Finished TEXT NOT NULL" +
                ")";
        this.pst = this.conn.prepareStatement(sqlCreate2);
        this.pst.execute();
        this.pst.close();
    }

} catch (Exception e) {
    e.printStackTrace();
} finally {
    try {
        if (this.pst != null) this.pst.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

   
    
    
   
  
    
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
            
            String sql ="select * from "+selsecc+"";
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
          

//////////////////////////////////////////////////////////////////////////////////////////
          
contextMenu = new ContextMenu();
MenuItem viewItem = new MenuItem("Manual Shipment Path - مسار الشحنة يدويا");
MenuItem deleteItem = new MenuItem("Recipe Shipment Path - مسار الشحنة من الريسيبي");
contextMenu.getItems().addAll(viewItem, deleteItem);

// Simple boolean flag
boolean[] contextMenuEnabled = {true};

viewItem.setOnAction(e -> {
    //Manual
//    
             
               //Manual
        try {
    Parent content = FXMLLoader.load(getClass().getResource("ShipmentPath2.fxml"));

    Stage stage = new Stage();
    stage.setTitle("مسار الشحنة يدويا");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}

             
//         }
    
});

deleteItem.setOnAction(e -> {
    //Recipe
            
    //Recipe
        
           try {
    Parent content = FXMLLoader.load(getClass().getResource("ShipmentPath1.fxml"));

    Stage stage = new Stage();
    stage.setTitle("مسار الشحنة عن طريق الريسيبي");
    Scene sce=new Scene (content);
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
        content.getStylesheets().add(cssPath);
    }
    ////////////////////////////////////////////////////////////////////
    stage.setScene(sce);
    stage.getIcons().add(new javafx.scene.image.Image(AlphaPlanner.class.getResourceAsStream("alpha.png")));

    // Set light theme if needed
//    stage.getScene().getStylesheets().add(
//        getClass().getResource("cupertino-light.css").toExternalForm()
//    );

    stage.setMaximized(true); // Real full window
    stage.initModality(Modality.APPLICATION_MODAL); // Block other windows like Alert
    stage.showAndWait();
} catch (Exception ex) {
    ex.printStackTrace();
}

          
        
        
//        }
    
});


// Attach context menu to rows only (not empty space)
table.setRowFactory(tv -> {
    TableRow<ObservableList<String>> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
            table.getSelectionModel().select(row.getItem());
            contextMenu.show(row, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
    });
    return row;
});
          
          
//////////////////////////////////////////////////////////////////////////////////////////
//
//fill.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("file.png"))));
//hel.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("helpdesk.png"))));
//ships.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("purchase-order.png"))));
//onlineusersmenu.setGraphic(new ImageView (new Image(getClass().getResourceAsStream("read.png"))));


//////////////////////////////////////////////////////////////////////////////////////////////////////
  try {  
  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
  String themooo=bis.readLine();
  bis.close();
  if (themooo.equals("cupertino-dark.css")) {
      //Dark
      sido.setStyle("-fx-background-color: linear-gradient(to bottom, #222532, #2f333f);");
  }
  else {
      //Light
      sido.setStyle("-fx-background-color:white;");
  }
  }
  catch (Exception re) {}
  //////////////////////////////////////////////////////////////////////////////////////////////////////  

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


//New Lite Version

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
        username.setText(seluserr);
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

// === StackPane to combine border + avatar ===
//StackPane avatarWithBorder = new StackPane(border, avatar);
//avatarWithBorder.setAlignment(Pos.CENTER);






//Old Big Version

//////////////////////////////////////////////////////////////////////////////////////
////Reading Avatar 
//// Avatar image
//avatar.setFitWidth(50);
//avatar.setFitHeight(50);
//avatar.setPreserveRatio(false); // force square scaling
//// Circular clip → perfectly round avatar
//Circle clip = new Circle(25, 25, 25); // center (width/2, height/2), radius
//avatar.setClip(clip);
//// Gradient border (Instagram-like)
//Circle border = new Circle(25, 25, 27); // a little bigger than avatar radius
//border.setStyle(
//    "-fx-fill: transparent;" +
//    "-fx-stroke: linear-gradient(to right, #feda75, #fa7e1e, #d62976, #962fbf, #4f5bd5);" +
//    "-fx-stroke-width: 3;"
//);
//
//try {
//String sql = "SELECT Avatar FROM USERS WHERE User = ? and Position = ?";
//pst = conn.prepareStatement(sql);
//pst.setString(1, seluserr);
//pst.setString(2, selsecc);
//ResultSet rs = pst.executeQuery();
//if (rs.next()) {
//    InputStream is = rs.getBinaryStream("Avatar");
//    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home")+"\\userpic.jpg");
//    byte[] buffer = new byte[4096];
//    int bytesRead;
//    while ((bytesRead = is.read(buffer)) != -1) {
//        fos.write(buffer, 0, bytesRead);
//    }
//    fos.close();
//    is.close();
//
//    //System.out.println("Image restored to check.png");
//    avatar.setImage(new Image((new File(System.getProperty("user.home")+"\\userpic.jpg")).toURI().toString()));
//    username.setText(seluserr);
//}
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            //avatar.setImage(new Image(getClass().getResourceAsStream("user.png")));
//        } finally {
//            try {
//                if (this.rs != null) this.rs.close();
//                if (this.pst != null) this.pst.close();
//                
//            } catch (Exception ignored) {}
//        }
//
//////////////////////////////////////////////////////////////////////////////////////

      


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

try {
        if (isFirstLaunch()) {
            steps.add(new TourStep(avatar, "هنا هتظهر صورتك الشخصية وممكن تكلم احمد القاضي يغيرهالك"));
            steps.add(new TourStep(username, "دا اسمك اللي اتعمل بيه اسم الاكونت بتاعك وبرضو ممكن القاضي يغيرهولك"));
            steps.add(new TourStep(reffo, "دوس هنا علشان تحدث بيانات الجدول من الداتا بيز"));
            steps.add(new TourStep(zeroo, "من خلال الايقونة دي هتقدر تمسح كل الاوردرات اللي كميتها وصلت صفر عندك دفعة واحدة"));
            steps.add(new TourStep(stocko, "اعرض كمية الاستوك اللي عندك كله مرة واحدة ولو عاوز تفاصيل شوفها من خلال الجدول او اسحب الداتا اكسيل وشوف"));
            steps.add(new TourStep(expotyo, "من خلال الايقونة دي هتقدر تسحب الداتا مرة واحدة اللي قدامك في الجدول علي شيت اكسيل"));
            steps.add(new TourStep(shipa, "دا اختصار علشان تبعت شحنة خلصت عندك لقسم تاني"));
            steps.add(new TourStep(traco, "ودا اختصار علشان تشوف مين وافق ومين موافقش وشحناتك المتعلقة لحين الموافقة او الرفض"));
            steps.add(new TourStep(prodo, "سجل انتاجك هنا اول باول علشان تشوف الستوك بتاعك في اخر تحديث"));
            steps.add(new TourStep(settaa, "دي الاعدادات ويفضل متلعبش فيها بنفسك ولو احتاجت حاجة كلم القاضي"));
            steps.add(new TourStep(inbox, "هنا هتلاقي الرسايل اللي وصلتك طول الجلسة"));
            steps.add(new TourStep(messenger, "ابعت رسالة من هنا لاي حد في اي قسم وصلي علي النبي في الاخر محمد صلي الله عليه وسلم"));
            //steps.add(new TourStep(togglePanelBtn2, "اعرض او اخفي شحناتك اللي اتبعتتلك وهتلاقي فيها اوبشن للقبول واوبشن للرفض"));
            //steps.add(new TourStep(logiout, "سجل خروجك من البرنامج والجلسة واقفل او روح لمستخدم تاني"));
            //steps.add(new TourStep(exito, "اقفل البرنامج وصلي علي النبي في الاخر محمد صلي الله عليه وسلم"));
            
            
            

            saveFirstLaunchFlag();

            // ✅ استنى بعد ما كل حاجة تتحمل
            Platform.runLater(() -> {
                Scene scene = mainRoot.getScene();
                if (scene != null) {
                    showStep(mainRoot, scene); // استخدم الـ StackPane الأساسي
                }
            });
        }
    } catch (Exception ee) {
        ee.printStackTrace();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    }
    
    private void showNotification(String title, String text) {
    Notifications.create()
        .title(title)
        .text(text)
        .hideAfter(Duration.seconds(5))
        .position(Pos.CENTER)
        .showError();
}
    
    
private void shutdownApp() {
        fileCheckTimer.cancel(); // Stop the timer
        // Run on JavaFX thread
        Platform.runLater(() -> {
            Platform.exit(); // Close JavaFX
             
    if (refreshTimeline != null) {
        refreshTimeline.stop();
    }
    
    if (refreshTimeline2 != null) {
        refreshTimeline2.stop();
    }
    
    if (refreshTimeline3 != null) {
        refreshTimeline3.stop();
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
   
    private void startAutoRefresh() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {//Shipments
            Platform.runLater(this::fetchAndDisplayShipments);
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
        
    }
    
    private void startAutoRefresh2() {//Online or Offline
        refreshTimeline2 = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            Platform.runLater(this::fetching);
        }));
        refreshTimeline2.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline2.play();
    }
    
    private void startAutoRefresh3 () {
        refreshTimeline3 = new Timeline(new KeyFrame(Duration.seconds(3), e -> {//Messages
            Platform.runLater(this::fofofo);
        }));
        refreshTimeline3.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline3.play();
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
    @FXML
    private void acceptSelectedShipments() throws SQLException {
        processSelectedShipments(true);
    }

    @FXML
    private void refuseSelectedShipments() throws SQLException {
        processSelectedShipments(false);
    }

    

    
    
    ///////////////////////////////////////////////////////////
    
   private void fofofo() {
    mainSplit1.setDisable(false);
    
    
       
            
        try {
            String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
            cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WASHINGController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
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
//header.setFont(cairoSemiBold);
header.setStyle(
    "-fx-font-size: 13px;" +
    "-fx-text-fill: #333d29;" +
    "-fx-font-weight: bold;"         
);

// === Message Body (Bubble) ===
Label message = new Label(msg);
message.setWrapText(true);
//message.setFont(cairoSemiBold);
message.setMaxWidth(500);  // عرض البالونة

try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        message.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-family: 'Cairo SemiBold';" +        
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +   // أزرق زي iMessage
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 12 8 12;"
        );
    } else {
        message.setStyle(
            "-fx-text-fill: black;" +
            "-fx-font-family: 'Cairo SemiBold';" +        
            "-fx-font-weight: bold;" +        
            "-fx-background-color: #0A84FF;" +  // رمادي فاتح زي iOS
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 12 8 12;"
        );
    }
} catch (Exception re) {
    message.setStyle(
        "-fx-text-fill: black;" +
        "-fx-font-family: 'Cairo SemiBold';" +        
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
                "-fx-font-family: 'Cairo SemiBold';" +        
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
                "-fx-font-family: 'Cairo SemiBold';" +        
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
    
    
    
    private void fetchAndDisplayShipments() {
    mainSplit.setDisable(false);    
    try (PreparedStatement pst = conn.prepareStatement(
            "SELECT * FROM Shipment_Path WHERE Delivered = 0 AND `To` = '" + selsecc + "'")) {

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("ID");
            if (shipmentCheckboxes.containsKey(id)) continue;

            String po = rs.getString("PO");
            String sap = rs.getString("Sap_Code");
            String style = rs.getString("Style");
            int qToSend = rs.getInt("Q_To_Send");
            String customer = rs.getString("Customer");
            String washName = rs.getString("Wash_Name");
            String fromy = rs.getString("From");
            String usery = rs.getString("User");
            
            
            
            

//            JFXCheckBox cb = new JFXCheckBox();
//            cb.setText("PO: " + po + "\nSAP: " + sap + "\nStyle: " + style +
//                       "\nQuantity: " + qToSend + "\nCustomer: " + customer + "\nWash Name: " + washName);
//            cb.setWrapText(true);
//            
//  //////////////////////////////////////////////////////////////////////////////////////////////////////
//  try {  
//  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//  String themooo=bis.readLine();
//  bis.close();
//  if (themooo.equals("cupertino-dark.css")) {
//      //Dark
//      cb.setStyle(
//    "-fx-font-size: 14px;" +
//    "-fx-font-weight: bold;" +
//    "-fx-font-family: 'Arial';" +
//    "-fx-text-fill: white;" // Ensure text is readable in dark mode
//);
//  }
//  else {
//      //Light
//      cb.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
//  }
//  }
//  catch (Exception re) {}
//  //////////////////////////////////////////////////////////////////////////////////////////////////////  
//            
//            
//
//            VBox card = new VBox(cb);
//            card.setSpacing(10);
//            card.setPadding(new Insets(10));
//            
//  //////////////////////////////////////////////////////////////////////////////////////////////////////
//  try {  
//  BufferedReader bis=new BufferedReader (new FileReader (System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"));
//  String themooo=bis.readLine();
//  bis.close();
//  if (themooo.equals("cupertino-dark.css")) {
//      //Dark
//      card.setStyle(
//    "-fx-background-color: #1C1C1E;" + // Dark gray background for cards
//    "-fx-background-radius: 12;" +
//    "-fx-border-radius: 12;" +
//    "-fx-border-color: rgba(255,255,255,0.08);" + // Subtle light border
//    "-fx-border-width: 1;" +
//    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);" +
//    "-fx-padding: 10;"
//);
//  }
//  else {
//      //Light
//      card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
//  }
//  }
//  catch (Exception re) {}
//  //////////////////////////////////////////////////////////////////////////////////////////////////////  
//            
//            
//            card.setAlignment(Pos.CENTER_LEFT);
//
//  



            
               // === Header Info (صغير ولونه رمادي) ===
JFXCheckBox header = new JFXCheckBox("لقد استلمت شحنة جديدة بياناتها كالتالي");
header.setWrapText(true);
header.setMaxWidth(400);
header.setStyle(
    "-fx-font-size: 14px;" +
    "-fx-text-fill: darkgrey;" +
    "-fx-font-weight: bold;" +         
    "-fx-font-family: 'Cairo SemiBold';" 
);

// === Message Body (Bubble) ===
Label message = new Label("PO: " + po + "\nSAP: " + sap + "\nStyle: " + style +
                       "\nQuantity: " + qToSend + "\nCustomer: " + customer + "\nWash Name: " + washName);
message.setWrapText(true);
message.setMaxWidth(500);  // عرض البالونة

try (BufferedReader bis = new BufferedReader(new FileReader(
        System.getProperty("user.home") + "\\AppData\\Roaming\\Alpha_Planning\\Themes.kady"))) {
    String themooo = bis.readLine();
    if ("cupertino-dark.css".equals(themooo)) {
        message.setStyle(
//            "-fx-font-size: 16px;" +
//            "-fx-font-family: 'Arial';" +
//            "-fx-text-fill: white;" +
//            "-fx-font-weight: bold;" +        
//            "-fx-background-color: #0A84FF;" +   // أزرق زي iMessage
//            "-fx-background-radius: 15;" +
//            "-fx-padding: 8 12 8 12;"
                  "-fx-font-weight: bold;" +
    "-fx-font-size: 15px;" +
    "-fx-font-family: 'Cairo SemiBold';" +                      
    "-fx-effect: dropshadow(gaussian, #c3c3c3, 10, 0.6, 21, 21);"
        );
    } else {
        message.setStyle(
//            "-fx-font-size: 15px;" +
//            "-fx-font-family: 'Arial';" +
//            "-fx-text-fill: black;" +
//            "-fx-font-weight: bold;" +        
//            "-fx-background-color: #E5E5EA;" +  // رمادي فاتح زي iOS
//            "-fx-background-radius: 15;" +
//            "-fx-padding: 8 12 8 12;"
                  "-fx-font-weight: bold;" +
    "-fx-font-size: 15px;" +
    "-fx-font-family: 'Cairo SemiBold';" +                   
    "-fx-effect: dropshadow(gaussian, #c3c3c3, 10, 0.6, 21, 21);"
        );
    }
} catch (Exception re) {
    message.setStyle(
//        "-fx-font-size: 15px;" +
//        "-fx-font-family: 'Arial';" +
//        "-fx-text-fill: black;" +
//        "-fx-font-weight: bold;" +        
//        "-fx-background-color: #E5E5EA;" +
//        "-fx-background-radius: 15;" +
//        "-fx-padding: 8 12 8 12;"
            
              "-fx-font-weight: bold;" +
    "-fx-font-size: 15px;" +
    "-fx-font-family: 'Cairo SemiBold';" +                
    "-fx-effect: dropshadow(gaussian, #c3c3c3, 10, 0.6, 21, 21);"
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

            
            
            
            
            shipmentList.getChildren().add(card);
            shipmentCheckboxes.put(id, header);
            mainSplit.setVisible(true);
            togglePanelBtn2.setSelected(true);
            
            //Anim Here.
            
            pt=new PulseTransition (togglePanelBtn2);
            pt.setAutoReverse(true);
            pt.setCycleCount(Timeline.INDEFINITE);
            pt.play();
            
            st=new ShakeTransition (togglePanelBtn2);
            st.setAutoReverse(true);
            st.setCycleCount(Timeline.INDEFINITE);
            st.play();

            String allll = "لقد تم استلام شحنة جديدة من خلال المستخدم " + usery + " الموجود بقسم " + fromy +
                           "\nوبياناتها كالتالي\n\n" +
                           "PO: " + po + "\nSAP CODE: " + sap + "\nStyle: " + style +
                           "\nQuantity: " + qToSend + "\nCustomer: " + customer + "\nWash Name: " + washName;

            Popup notificationPopup = new Popup();
            VBox mainContainer = new VBox();
            mainContainer.setPadding(new Insets(10));
            mainContainer.setSpacing(10);
            mainContainer.setStyle(
                "-fx-background-color: #f9f9f9;" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #dddddd;" +
                "-fx-border-radius: 16;" +
                "-fx-border-width: 1;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0.2, 0, 4);"
            );

            HBox topBar = new HBox();
            topBar.setAlignment(Pos.TOP_RIGHT);
            javafx.scene.control.Button closeButton = new javafx.scene.control.Button("\u2716");
            closeButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
            );
            closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: lightgray;" +
                "-fx-text-fill: darkred;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
            ));
            closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
            ));
            closeButton.setOnAction(e -> notificationPopup.hide());
            topBar.getChildren().add(closeButton);

            HBox notificationBox = new HBox(15);
            notificationBox.setPadding(new Insets(10));
            notificationBox.setAlignment(Pos.CENTER_LEFT);

            Label notificationLabel = new Label();
            notificationLabel.setWrapText(true);
            notificationLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            notificationLabel.setStyle(
                "-fx-font-family: 'Cairo SemiBold';" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18px;" +
                "-fx-text-fill: #222222;" +
                "-fx-max-width: 600;" +
                "-fx-alignment: center-right;"
            );
            notificationLabel.setTextAlignment(TextAlignment.RIGHT);
            notificationLabel.setText(allll);
            notificationBox.getChildren().add(notificationLabel);

            mainContainer.getChildren().addAll(topBar, notificationBox);
            notificationPopup.getContent().add(mainContainer);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double screenWidth = bounds.getWidth();
            double bottomY = bounds.getHeight() - 120;

            mainContainer.applyCss();
            mainContainer.layout();
            double popupWidth = mainContainer.getWidth();
            double popupX = (screenWidth - popupWidth) / 2;

            Stage stage = (Stage) table.getScene().getWindow();
            notificationPopup.show(stage);
            notificationPopup.setX(popupX);
            notificationPopup.setY(bottomY);

            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\noti.wav").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    
    
    
    

   
    
    
    
    
    
    private void processSelectedShipments(boolean accept) throws SQLException {
    List<Node> cardsToRemove = new ArrayList<>();

    Iterator<Map.Entry<Integer, CheckBox>> iterator = shipmentCheckboxes.entrySet().iterator();

    while (iterator.hasNext()) {
        Map.Entry<Integer, CheckBox> entry = iterator.next();
        int id = entry.getKey();
        CheckBox cb = entry.getValue();

        if (cb.isSelected()) {
            try {
                
                
if (accept) {
    // Step 1: Mark Delivered
    PreparedStatement pst = conn.prepareStatement(
        "UPDATE Shipment_Path SET Delivered = 1 WHERE ID = ?");
    pst.setInt(1, id);
    pst.executeUpdate();
    pst.close();

    // Step 2: Get Shipment Data
    PreparedStatement ps = conn.prepareStatement(
        "SELECT PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
        "Incoming_Date, X_Fac_Date, Q_To_Send, \"From\" FROM Shipment_Path WHERE ID = ?");
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        String po = rs.getString("PO");
        String sap = rs.getString("Sap_Code");
        String style = rs.getString("Style");
        String customer = rs.getString("Customer");
        String washName = rs.getString("Wash_Name");
        String poAmount = rs.getString("PO_Amount");
        int cuttingAmount = rs.getInt("Cutting_Amount");
        String laundryDate = rs.getString("Incoming_Date");
        String xfacDate = rs.getString("X_Fac_Date");
        int qToSend = rs.getInt("Q_To_Send");
        String fromSection = rs.getString("From");

        // *** Step 2.5: Check if Received + Q_To_Send > CuttingAmount ***
        PreparedStatement checkOverStmt = conn.prepareStatement(
            "SELECT COALESCE(Received,0) FROM " + selsecc +
            " WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        checkOverStmt.setString(1, po);
        checkOverStmt.setString(2, sap);
        checkOverStmt.setString(3, style);
        ResultSet overRs = checkOverStmt.executeQuery();

        int currentReceived = 0;
        if (overRs.next()) {
            currentReceived = overRs.getInt(1);
        }
        overRs.close();
        checkOverStmt.close();

        if (currentReceived + qToSend > cuttingAmount) {
            Notifications.create()
                .title("خطأ")
                .text("الكمية المطلوبة (" + qToSend + ") ستجعل الإجمالي (" +
                      (currentReceived + qToSend) + ") يتجاوز كمية القص (" + cuttingAmount + ").")
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER)
                .showError();
            rs.close();
            ps.close();
            return;
        }

        // Step 3: Subtract from From section main table
        PreparedStatement subtractStmt = conn.prepareStatement(
            "UPDATE " + fromSection + " SET Received = Received - ? " +
            "WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        subtractStmt.setInt(1, qToSend);
        subtractStmt.setString(2, po);
        subtractStmt.setString(3, sap);
        subtractStmt.setString(4, style);
        subtractStmt.executeUpdate();
        subtractStmt.close();

        
if (fromSection.equalsIgnoreCase("WareHouse_1")) {
    
    
    
}

else {
    
      // *** NEW Step 3.5: Subtract from Finished in <fromSection>_Production ***
        PreparedStatement subtractFinishedStmt = conn.prepareStatement(
            "UPDATE " + fromSection + "_Production " +
            "SET Finished = CASE WHEN Finished - ? < 0 THEN 0 ELSE Finished - ? END " +
            "WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        subtractFinishedStmt.setInt(1, qToSend);
        subtractFinishedStmt.setInt(2, qToSend);
        subtractFinishedStmt.setString(3, po);
        subtractFinishedStmt.setString(4, sap);
        subtractFinishedStmt.setString(5, style);
        subtractFinishedStmt.executeUpdate();
        subtractFinishedStmt.close();
    
}
        
      

        // Step 4: Handle main section table (insert or update)
        PreparedStatement checkStmt = conn.prepareStatement(
            "SELECT COUNT(*) FROM " + selsecc +
            " WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        checkStmt.setString(1, po);
        checkStmt.setString(2, sap);
        checkStmt.setString(3, style);
        ResultSet checkRs = checkStmt.executeQuery();
        boolean mainExists = checkRs.next() && checkRs.getInt(1) > 0;
        checkRs.close();
        checkStmt.close();

        if (mainExists) {
            PreparedStatement updateStmt = conn.prepareStatement(
                "UPDATE " + selsecc +
                " SET Received = Received + ? WHERE PO = ? AND Sap_Code = ? AND Style = ?");
            updateStmt.setInt(1, qToSend);
            updateStmt.setString(2, po);
            updateStmt.setString(3, sap);
            updateStmt.setString(4, style);
            updateStmt.executeUpdate();
            updateStmt.close();
        } else {
            PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO " + selsecc +
                " (PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, " +
                "Cutting_Amount, Incoming_Date, X_Fac_Date, Received) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStmt.setString(1, po);
            insertStmt.setString(2, sap);
            insertStmt.setString(3, style);
            insertStmt.setString(4, customer);
            insertStmt.setString(5, washName);
            insertStmt.setString(6, poAmount);
            insertStmt.setInt(7, cuttingAmount);
            insertStmt.setString(8, laundryDate);
            insertStmt.setString(9, xfacDate);
            insertStmt.setInt(10, qToSend);
            insertStmt.executeUpdate();
            insertStmt.close();
        }

        // Step 5: Handle _Production table for main section
        PreparedStatement checkProdStmt = conn.prepareStatement(
            "SELECT COUNT(*) FROM " + selsecc + "_Production " +
            "WHERE PO = ? AND Sap_Code = ? AND Style = ?");
        checkProdStmt.setString(1, po);
        checkProdStmt.setString(2, sap);
        checkProdStmt.setString(3, style);
        ResultSet prodRs = checkProdStmt.executeQuery();
        boolean prodExists = prodRs.next() && prodRs.getInt(1) > 0;
        prodRs.close();
        checkProdStmt.close();

        if (prodExists) {
            PreparedStatement updateProd = conn.prepareStatement(
                "UPDATE " + selsecc + "_Production " +
                "SET Received = Received + ?, Not_Finished = Not_Finished + ? " +
                "WHERE PO = ? AND Sap_Code = ? AND Style = ?");
            updateProd.setInt(1, qToSend);
            updateProd.setInt(2, qToSend);
            updateProd.setString(3, po);
            updateProd.setString(4, sap);
            updateProd.setString(5, style);
            updateProd.executeUpdate();
            updateProd.close();
        } else {
            PreparedStatement insertProd = conn.prepareStatement(
                "INSERT INTO " + selsecc + "_Production " +
                "(PO, Sap_Code, Style, Customer, Wash_Name, Received, Finished, Not_Finished) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            insertProd.setString(1, po);
            insertProd.setString(2, sap);
            insertProd.setString(3, style);
            insertProd.setString(4, customer);
            insertProd.setString(5, washName);
            insertProd.setInt(6, qToSend);
            insertProd.setInt(7, 0);
            insertProd.setInt(8, qToSend);
            insertProd.executeUpdate();
            insertProd.close();
        }

        // Step 6: Audit log
        try {
            String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?, ?, ?, ?)";
            this.pst = this.conn.prepareStatement(sqla);
            this.pst.setString(1, value1);
            this.pst.setString(2, selsecc);
            this.pst.setString(3, seluserr);
            this.pst.setString(4, seluserr + " Accepted shipment, Quantity: " + qToSend +
                ", info and its data is: PO: " + po +
                ", Sap Code: " + sap +
                ", Style: " + style +
                ", Customer: " + customer +
                ", Wash Name: " + washName +
                ", PO Amount: " + poAmount +
                ", Cutting Amount: " + cuttingAmount +
                ", From: " + fromSection +
                ", To: " + selsecc + ".");
            this.pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ignored) {}
        }

        Notifications.create()
            .title("تم بنجاح")
            .text("تم قبول الشحنة ونقل الكمية إلى " + selsecc)
            .hideAfter(Duration.seconds(3))
            .position(Pos.CENTER)
            .showInformation();
    }

    rs.close();
    ps.close();
}

         
                else {
    // Step 1: Mark as Refused
    PreparedStatement pst = conn.prepareStatement(
        "UPDATE Shipment_Path SET Delivered = 'Refused' WHERE ID = ?");
    pst.setInt(1, id);
    pst.executeUpdate();
    pst.close();

    // Step 2: Get Shipment Data
    PreparedStatement ps = conn.prepareStatement(
        "SELECT PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
        "Incoming_Date, X_Fac_Date, Q_To_Send, \"From\" FROM Shipment_Path WHERE ID = ?");
    ps.setInt(1, id);
    ResultSet rss = ps.executeQuery();

    if (rss.next()) {
        String po = rss.getString("PO");
        String sap = rss.getString("Sap_Code");
        String style = rss.getString("Style");
        String customer = rss.getString("Customer");
        String washName = rss.getString("Wash_Name");
        String poAmount = rss.getString("PO_Amount");
        String cuttingAmount = rss.getString("Cutting_Amount");
        int qToSend = rss.getInt("Q_To_Send");
        String fromSection = rss.getString("From");

        // Step 3: Audit with full details
        try {
            String sqla = "INSERT INTO Audit (Date, Section, User, Notes) VALUES (?, ?, ?, ?)";
            this.pst = this.conn.prepareStatement(sqla);
            this.pst.setString(1, value1);
            this.pst.setString(2, selsecc);
            this.pst.setString(3, seluserr);
            this.pst.setString(4, seluserr + " Refused a shipment, Quantity: " + qToSend +
                ", info and its data is: PO: " + po +
                ", Sap Code: " + sap +
                ", Style: " + style +
                ", Customer: " + customer +
                ", Wash Name: " + washName +
                ", PO Amount: " + poAmount +
                ", Cutting Amount: " + cuttingAmount +
                ", From: " + fromSection +
                ", To: " + selsecc + ".");
            this.pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.rs != null) this.rs.close();
                if (this.pst != null) this.pst.close();
            } catch (Exception ignored) {}
        }
    }

    rs.close();
    ps.close();

    // Step 4: Show Notification
    Notifications.create()
        .title("تم الرفض")
        .text("تم رفض الشحنة بنجاح.")
        .hideAfter(Duration.seconds(3))
        .position(Pos.CENTER)
        .showInformation();


                    
                    /////////////////Alert to note////////////////

               
            // Create a text field for user notes
JFXTextField noteField = new JFXTextField("");
noteField.setLabelFloat(true);
noteField.setPromptText("اضف ملاحظات");
noteField.setStyle("-fx-font-weight:bold;-fx-font-size:12;");

// Create alert dialog
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("اوردر مرفوض");
alert.setHeaderText("لقد تم رفض الاوردر الحالي");
alert.setContentText("عاوز تضيفه لقائمة المرفوض ولا لا؟");
alert.setGraphic(noteField);

// Add buttons
ButtonType addButton = new ButtonType("اضافة");
ButtonType cancelButton = new ButtonType("الغاء");
alert.getButtonTypes().setAll(addButton, cancelButton);

// Load custom style
DialogPane dialogPane = alert.getDialogPane();
dialogPane.setMinSize(600, 300);
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

// Show dialog and wait
Optional<ButtonType> result = alert.showAndWait();

// Determine the note content
String noteText = "";
if (result.isPresent() && result.get() == addButton) {
    noteText = noteField.getText().trim();
    if (noteText.isEmpty()) {
        noteText = "تم الرفض بدون كتابة ملاحظة";
    }
} else if (result.isPresent() && result.get() == cancelButton) {
    noteText = "الشحنة اترفضت لاسباب غير معروفة";
} else {
    // Dialog was closed or canceled
    return;
}

// Fetch the refused shipment data
try (PreparedStatement getDataStmt = conn.prepareStatement(
    "SELECT PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
    "Incoming_Date, X_Fac_Date, Q_To_Send, \"From\", \"To\", Unique_ID " +
    "FROM Shipment_Path WHERE ID = ?")) {

    getDataStmt.setInt(1, id);

    try (ResultSet rs = getDataStmt.executeQuery()) {
        if (rs.next()) {
            String po = rs.getString("PO");
            String sap = rs.getString("Sap_Code");
            String style = rs.getString("Style");
            String customer = rs.getString("Customer");
            String washName = rs.getString("Wash_Name");
            String poAmount = rs.getString("PO_Amount");
            String cuttingAmount = rs.getString("Cutting_Amount");
            String incomingDate = rs.getString("Incoming_Date");
            String xFacDate = rs.getString("X_Fac_Date");
            int qToSend = rs.getInt("Q_To_Send");
            String from = rs.getString("From");
            String to = rs.getString("To");
            String uniqueId = rs.getString("Unique_ID");

            // Get today's date
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // Insert into Refused_Shipments table
            try (PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO Refused_Shipments (" +
                "\"Date\", PO, Sap_Code, Style, Customer, Wash_Name, PO_Amount, Cutting_Amount, " +
                "Incoming_Date, X_Fac_Date, \"User\", \"From\", \"To\", Delivered, Unique_ID, Q_To_Send, Note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                insertStmt.setString(1, currentDate);      // Date
                insertStmt.setString(2, po);
                insertStmt.setString(3, sap);
                insertStmt.setString(4, style);
                insertStmt.setString(5, customer);
                insertStmt.setString(6, washName);
                insertStmt.setString(7, poAmount);
                insertStmt.setString(8, cuttingAmount);
                insertStmt.setString(9, incomingDate);
                insertStmt.setString(10, xFacDate);
                insertStmt.setString(11, seluserr);        // Current user
                insertStmt.setString(12, from);            // "From" field
                insertStmt.setString(13, to);              // "To" field
                insertStmt.setString(14, "Refused");       // Delivered = Refused
                insertStmt.setString(15, uniqueId);
                insertStmt.setInt(16, qToSend);
                insertStmt.setString(17, noteText);        // User note

                insertStmt.executeUpdate();
                System.out.println("✅ Refused shipment inserted.");
            }

        } else {
            System.err.println("❌ Shipment with ID = " + id + " not found.");
        }
    }

} catch (Exception e) {
    e.printStackTrace();
}

                  
                    ///////////////////////////////////////////////
                }

                // Find the parent card (like HBox/VBox)
                Node card = cb.getParent();
                if (card != null) {
                    
                    
                            
                            BounceOutRightTransition pt4=new BounceOutRightTransition (card);
                            pt4.setAutoReverse(false);
                            pt4.setCycleCount(1);
                            pt4.setOnFinished(event -> shipmentList.getChildren().remove(card));
                            pt4.play();
                            
                            
                            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\delete.wav").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
                    
                    cardsToRemove.add(card); // Mark for cleanup from map
                }

                cb.setSelected(false);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Remove checkboxes from map after animation
    for (Node card : cardsToRemove) {
        shipmentCheckboxes.entrySet().removeIf(entry -> entry.getValue().getParent() == card);
    }

    // Hide main panel if done
    if (shipmentCheckboxes.isEmpty()) {

    // Delay before animation
    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    
    pause.setOnFinished(event -> {
        BounceOutLeftTransition pt4 = new BounceOutLeftTransition(mainSplit);
        pt4.setAutoReverse(false);
        pt4.setCycleCount(1);
        pt4.setOnFinished(e -> {
            mainSplit.setVisible(false);
            togglePanelBtn2.setSelected(false);
            
            pt.stop();
            st.stop();
            
        });
        pt4.play();
        
                 
                            try {
                AudioClip clip = new AudioClip(new File(System.getProperty("user.home") +
                        "\\AppData\\Roaming\\Alpha_Planning\\close.wav").toURI().toString());
                clip.play();
            } catch (Exception audioEx) {
                System.err.println("Sound error: " + audioEx.getMessage());
            }
        
        
    });

    pause.play();

    // Optional: disable interaction during delay
    mainSplit.setDisable(true);
}

    // Delete accepted/refused
    try {
        PreparedStatement deleteStmt = conn.prepareStatement(
            "DELETE FROM Shipment_Path WHERE Delivered = 1 OR Delivered = 'Refused'");
        deleteStmt.executeUpdate();
        deleteStmt.close();
        
        
        
    } catch (SQLException e) {
        
        e.printStackTrace();
        
    }

    // Refresh Table
    try {
        table.getColumns().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM " + selsecc);
        ResultSet rs = pst.executeQuery();

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
            table.getColumns().add(col);
        }

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        table.setItems(data);
        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Re-apply filter
    new TableFilter<>(table);
}
    
       
}
