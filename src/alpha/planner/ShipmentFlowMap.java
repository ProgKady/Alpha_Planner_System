package alpha.planner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ShipmentFlowMap extends Application {

    // Example shipment step data class
    static class ShipmentStep {
        String date;
        String section;
        String po;
        String style;
        String orderName;

        public ShipmentStep(String date, String section, String po, String style, String orderName) {
            this.date = date;
            this.section = section;
            this.po = po;
            this.style = style;
            this.orderName = orderName;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Button openMapButton = new Button("Show Shipment Map");

        openMapButton.setOnAction(e -> {
            List<ShipmentStep> steps = parseSampleData(); // Simulated table data
            showMapWindow(steps);
        });

        StackPane root = new StackPane(openMapButton);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMapWindow(List<ShipmentStep> steps) {
        Pane mapPane = new Pane();
        double startX = 100;
        double startY = 100;
        double verticalSpacing = 100;

        for (int i = 0; i < steps.size(); i++) {
            ShipmentStep step = steps.get(i);
            double y = startY + i * verticalSpacing;

            // Create circle (dot)
            Circle circle = new Circle(startX, y, 10, Color.DODGERBLUE);

            // Info label
            String info = String.format("%s\nSection: %s\nPO: %s\nStyle: %s",
                    step.date, step.section, step.po, step.style);
            Text label = new Text(startX + 20, y + 5, info);

            mapPane.getChildren().addAll(circle, label);

            // Draw line from previous point
            if (i > 0) {
                Line line = new Line(startX, y - verticalSpacing, startX, y);
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(2);
                mapPane.getChildren().add(line);
            }
        }

        Scene scene = new Scene(mapPane, 600, 600);
        Stage mapStage = new Stage();
        mapStage.setTitle("Shipment Flow Map");
        mapStage.setScene(scene);
        mapStage.setResizable(false);
        mapStage.show();
    }

    private List<ShipmentStep> parseSampleData() {
        // Simulated parsed data (normally you'd pull from TableView or DB)
        List<ShipmentStep> steps = new ArrayList<>();
        steps.add(new ShipmentStep("2025-08-05", "WAREHOUSE_1", "F43T34434", "UO84999", "A"));
        steps.add(new ShipmentStep("2025-08-05", "WASHING", "F43T34434", "UO84999", "A"));
        steps.add(new ShipmentStep("2025-08-05", "DRYING", "F43T34434", "UO84999", "A")); // example extra step
        return steps;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
