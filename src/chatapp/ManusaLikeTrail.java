package chatapp;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;

public class ManusaLikeTrail extends Application {

    private static class Point {
        double x, y;
        double life;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
            this.life = 1.0;
        }
    }

    private final LinkedList<Point> trail = new LinkedList<>();
    private double hueOffset = 0;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(1024, 768);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setOnMouseMoved(e -> {
            trail.add(new Point(e.getX(), e.getY()));
            if (trail.size() > 200) {
                trail.removeFirst();
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(gc, canvas);
            }
        }.start();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manusa-like Trail Effect");
        stage.show();
    }

    private void draw(GraphicsContext gc, Canvas canvas) {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        // Instead of clearing fully, draw a semi-transparent rect to fade trails
        gc.setFill(Color.rgb(5, 5, 20, 0.2));
        gc.fillRect(0, 0, w, h);

        // Update life
        for (Point p : trail) {
            p.life -= 0.015;
        }
        trail.removeIf(p -> p.life <= 0);

        if (trail.size() >= 3) {
            for (int i = 2; i < trail.size(); i++) {
                Point p0 = trail.get(i - 2);
                Point p1 = trail.get(i - 1);
                Point p2 = trail.get(i);

                // Hue based on position + offset
                hueOffset += 0.4;
                if (hueOffset > 360) hueOffset -= 360;

                double hue = (hueOffset + (i * 2)) % 360;
                Color c = Color.hsb(hue, 0.8, 1.0, p2.life);

                gc.setStroke(c);
                gc.setLineWidth(1.5 + p2.life * 3);

                // Draw curve
                gc.beginPath();
                gc.moveTo(p0.x, p0.y);
                gc.quadraticCurveTo(p1.x, p1.y, p2.x, p2.y);
                gc.stroke();
            }
        }

        // Optionally: apply blur
        // Actually drawing blur per frame on canvas is tricky; better to overlay an effect node
        // Or use a separate Canvas and snapshot + GaussianBlur then redraw.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
