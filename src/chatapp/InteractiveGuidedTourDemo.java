package chatapp;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import javafx.scene.Node;

/**
 * Retool-style interactive guided tour demo.
 * - Tooltip with blue style and pointer
 * - Smooth animated movement to each target node
 * - Does NOT block clicks on the underlying target (user can interact)
 * - Auto-advance when condition becomes true (Button click or TextField text match)
 * - Also supports pressing ENTER to validate
 */
public class InteractiveGuidedTourDemo extends Application {

    @Override
    public void start(Stage stage) {
        // --- Demo UI ---
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        Label instruction = new Label("Demo Area");
        instruction.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        TextField input = new TextField();
        input.setPromptText("Type Flutter here");

        Button runButton = new Button("Run");
        runButton.setStyle("-fx-background-color:#1E88E5; -fx-text-fill:white; -fx-font-size:14px; -fx-background-radius:6;");

        Label result = new Label();

        layout.getChildren().addAll(instruction, input, runButton, result);

        // root must be a Pane so we can add overlay layers easily
        StackPane root = new StackPane(layout);
        Scene scene = new Scene(root, 700, 450);
        stage.setScene(scene);
        stage.setTitle("Interactive Retool-Style Guided Tour");
        stage.show();

        // Example action for runButton (simulate work)
        runButton.setOnAction(ev -> result.setText("Run clicked ✓"));

        
        
        
        
        
        // --- Build tour ---
        GuidedTour tour = new GuidedTour(scene, root);

        // Step 1: type "Flutter" into input
        tour.addStep("Let's build our first app.\nType 'Flutter' in the box.",
                input,
                () -> "Flutter".equalsIgnoreCase(input.getText()));

        // Step 2: click the Run button (auto-detected)
        tour.addStep("Great! Now click the Run button.",
                runButton,
                () -> runButton.isFocused() || "Run clicked ✓".equals(result.getText()) /* fallback */);

        // Step 3: final message
        tour.addStep("Awesome 🎉 You finished the tour!", result, () -> true);

        // Start the tour after layout is ready
        Platform.runLater(tour::start);
        
        
        
        
        
        
        
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    // -------------------------
    // GuidedTour implementation
    // -------------------------
    public static class GuidedTour {
        private final Scene scene;
        private final Pane rootPane;          // app root (must be Pane or subclass)
        private final Rectangle dimRect;      // dim background (mouseTransparent so underlying controls are clickable)
        private final Pane dimLayer;          // contains dimRect
        private final Pane uiLayer;           // contains tooltip + pointer (small bounds only)
        private final VBox tooltip;
        private final Label messageLabel;
        private final Label hintLabel;
        private final Polygon pointer;
        private final List<TourStep> steps = new ArrayList<>();
        private int currentIndex = -1;
        private Timeline moveAnim;
        private TourStep activeStep = null;

        public GuidedTour(Scene scene, Pane rootPane) {
            this.scene = scene;
            this.rootPane = rootPane;

            // Dim layer (visual only) — allow mouse to pass through so user can click targets
            dimRect = new Rectangle();
            dimRect.setFill(Color.rgb(0, 0, 0, 0.45));
            dimRect.setMouseTransparent(true); // very important: let clicks go through
            dimLayer = new Pane(dimRect);
            dimLayer.setPickOnBounds(false); // don't intercept clicks

            // UI layer (holds tooltip + pointer) — only occupies area of tooltip/pointer
            uiLayer = new Pane();
            uiLayer.setPickOnBounds(false);

            // Tooltip styling (blue box like Retool)
            messageLabel = new Label();
            messageLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            messageLabel.setWrapText(true);

            hintLabel = new Label("Press Enter or complete the action");
            hintLabel.setStyle("-fx-text-fill: #E3F2FD; -fx-font-size: 12px;");

            tooltip = new VBox(8, messageLabel, hintLabel);
            tooltip.setPadding(new Insets(10));
            tooltip.setMaxWidth(300);
            tooltip.setBackground(new Background(new BackgroundFill(Color.web("#2D8BF0"), new CornerRadii(8), Insets.EMPTY)));
            tooltip.setEffect(new DropShadow(10, Color.rgb(0,0,0,0.35)));
            tooltip.setMouseTransparent(false); // must receive mouse events for Next buttons if added later

            // Pointer triangle (visual only) — let clicks pass through pointer
            pointer = new Polygon(0, 0, 14, 10, 0, 20);
            pointer.setFill(Color.web("#2D8BF0"));
            pointer.setMouseTransparent(true); // don't block clicks to underlying nodes

            // Add UI elements to layers
            uiLayer.getChildren().addAll(pointer, tooltip);

            // Add both layers to root (dim underneath, uiLayer on top)
            rootPane.getChildren().addAll(dimLayer, uiLayer);

            // Keep dimRect sized to scene
            scene.widthProperty().addListener((o,a,b) -> resizeDim());
            scene.heightProperty().addListener((o,a,b) -> resizeDim());
            resizeDim();

            // keyboard validate
            scene.setOnKeyPressed(ev -> {
                if (ev.getCode() == KeyCode.ENTER) {
                    validateCurrentStep();
                }
            });
        }

        public void addStep(String text, Region target, BooleanSupplier condition) {
            steps.add(new TourStep(text, target, condition));
        }

        public void start() {
            if (steps.isEmpty()) return;
            showStep(0);
        }

        private void showStep(int index) {
            // remove listeners on previous active step
            detachListenersFromActiveStep();

            if (index < 0 || index >= steps.size()) return;
            currentIndex = index;
            activeStep = steps.get(index);

            messageLabel.setText(activeStep.text);

            // ensure tooltip has measured size before computing placement
            // schedule on next pulse to let layout compute sizes
            Platform.runLater(() -> {
                // ensure tooltip gets its CSS applied and layout bounds computed
                tooltip.applyCss();
                tooltip.autosize();
                tooltip.layout();

                moveTooltipToTarget(activeStep.target);
                // attach auto listeners for common controls (Button, TextField)
                attachAutoListenersFor(activeStep);
            });
        }

        private void moveTooltipToTarget(Region target) {
            // compute bounds of target in root coordinate space
            Bounds targetSceneBounds = target.localToScene(target.getBoundsInLocal());
            Point2D targetTopLeftInRoot = rootPane.sceneToLocal(targetSceneBounds.getMinX(), targetSceneBounds.getMinY());
            Point2D targetBottomRightInRoot = rootPane.sceneToLocal(targetSceneBounds.getMaxX(), targetSceneBounds.getMaxY());

            double tX = targetTopLeftInRoot.getX();
            double tY = targetTopLeftInRoot.getY();
            double tW = targetBottomRightInRoot.getX() - tX;
            double tH = targetBottomRightInRoot.getY() - tY;

            double gap = 12;

            // ensure tooltip measured size
            double tw = tooltip.getWidth() <= 0 ? tooltip.prefWidth(-1) : tooltip.getWidth();
            double th = tooltip.getHeight() <= 0 ? tooltip.prefHeight(-1) : tooltip.getHeight();

            // decide to place tooltip left or right depending on available space
            double sceneW = scene.getWidth();
            double preferredRightX = tX + tW + gap;
            double preferredLeftX  = tX - gap - tw;

            boolean placeRight = (preferredRightX + tw + 20) <= sceneW; // +20 margin
            double tooltipX = placeRight ? preferredRightX : Math.max(8, preferredLeftX);
            double tooltipY = tY + (tH - th) / 2.0;

            // clamp tooltipY within scene vertical bounds
            tooltipY = Math.max(8, Math.min(tooltipY, scene.getHeight() - th - 8));

            // pointer position: place between target center and tooltip edge
            double targetCenterY = tY + tH / 2.0;
            double pointerY = targetCenterY - 10; // 10 is half pointer height
            double pointerX;
            if (placeRight) {
                pointerX = tX + tW + gap + 4; // a bit near tooltip-left
                pointer.setRotate(0); // pointing right (triangle apex to the right)
            } else {
                // when tooltip on left, place pointer to the right of tooltip and rotate 180
                pointerX = tooltipX + tw + 4;
                pointer.setRotate(180);
            }

            // If pointer or tooltip layout change, animate properties smoothly
            if (moveAnim != null) moveAnim.stop();

            // Make sure tooltip/pointer have initial layout values
            if (Double.isNaN(tooltip.getLayoutX())) tooltip.setLayoutX(tooltipX);
            if (Double.isNaN(tooltip.getLayoutY())) tooltip.setLayoutY(tooltipY);
            if (Double.isNaN(pointer.getLayoutX())) pointer.setLayoutX(pointerX);
            if (Double.isNaN(pointer.getLayoutY())) pointer.setLayoutY(pointerY);

            // Build animation timeline
            KeyValue kvTooltipX = new KeyValue(tooltip.layoutXProperty(), tooltipX, Interpolator.EASE_BOTH);
            KeyValue kvTooltipY = new KeyValue(tooltip.layoutYProperty(), tooltipY, Interpolator.EASE_BOTH);
            KeyValue kvPointerX = new KeyValue(pointer.layoutXProperty(), pointerX, Interpolator.EASE_BOTH);
            KeyValue kvPointerY = new KeyValue(pointer.layoutYProperty(), pointerY, Interpolator.EASE_BOTH);

            moveAnim = new Timeline(new KeyFrame(Duration.millis(450), kvTooltipX, kvTooltipY, kvPointerX, kvPointerY));
            moveAnim.play();
        }

        private void validateCurrentStep() {
            if (activeStep == null) return;
            if (activeStep.condition.getAsBoolean()) {
                // advance
                currentIndex++;
                if (currentIndex < steps.size()) {
                    showStep(currentIndex);
                } else {
                    finish();
                }
            } else {
                // visual feedback
                shake(tooltip);
            }
        }

        private void finish() {
            detachListenersFromActiveStep();
            FadeTransition ft = new FadeTransition(Duration.millis(350), uiLayer);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(e -> {
                rootPane.getChildren().remove(uiLayer);
                rootPane.getChildren().remove(dimLayer);
            });
            ft.play();
        }

        private void resizeDim() {
            dimRect.setWidth(scene.getWidth());
            dimRect.setHeight(scene.getHeight());
        }

        private void shake(Node node) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(60), node);
            tt.setFromX(0);
            tt.setByX(10);
            tt.setCycleCount(6);
            tt.setAutoReverse(true);
            tt.play();
        }

        // --------------------------------------------------
        // Auto-listener management: attach/detach convenience
        // --------------------------------------------------
        private void attachAutoListenersFor(TourStep step) {
            // If target is Button -> add a temporary ActionEvent handler
            if (step.target instanceof Button) {
                Button btn = (Button) step.target;
                EventHandler<ActionEvent> handler = e -> {
                    // small delay to let the button's own action run
                    Platform.runLater(() -> {
                        if (step.condition.getAsBoolean()) {
                            validateCurrentStep();
                        }
                    });
                };
                btn.addEventHandler(ActionEvent.ACTION, handler);
                step.buttonHandler = handler;
            }

            // If target is TextInputControl (TextField/TextArea) -> watch text changes
            if (step.target instanceof TextInputControl) {
                TextInputControl tf = (TextInputControl) step.target;
                ChangeListener<String> txtListener = (obs, oldV, newV) -> {
                    if (step.condition.getAsBoolean()) {
                        Platform.runLater(this::validateCurrentStep);
                    }
                };
                tf.textProperty().addListener(txtListener);
                step.textListener = txtListener;
            }

            // You can add more control-specific listeners (ComboBox.valueProperty etc.) here
        }

        private void detachListenersFromActiveStep() {
            if (activeStep == null) return;
            if (activeStep.buttonHandler != null && activeStep.target instanceof Button) {
                ((Button) activeStep.target).removeEventHandler(ActionEvent.ACTION, activeStep.buttonHandler);
                activeStep.buttonHandler = null;
            }
            if (activeStep.textListener != null && activeStep.target instanceof TextInputControl) {
                ((TextInputControl) activeStep.target).textProperty().removeListener(activeStep.textListener);
                activeStep.textListener = null;
            }
            activeStep = null;
        }

        // --------------------------------------
        // TourStep container (holds temporary listeners)
        // --------------------------------------
        private static class TourStep {
            final String text;
            final Region target;
            final BooleanSupplier condition;

            // temporary listeners so we can remove them later
            EventHandler<ActionEvent> buttonHandler = null;
            ChangeListener<String> textListener = null;

            TourStep(String text, Region target, BooleanSupplier condition) {
                this.text = text;
                this.target = target;
                this.condition = condition;
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
