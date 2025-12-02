package chatapp;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CairoFontDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        // حمل خط Cairo SemiBold من مسار محدد
        String fontPath = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
        Font cairoSemiBold = Font.loadFont(new FileInputStream(fontPath), 18);

        // حمل خط Cairo Bold من مسار محدد
        String fontPathBold = System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Cairo.ttf"; // غيّر المسار حسب مكان الخط عندك
        Font cairoBold = Font.loadFont(new FileInputStream(fontPathBold), 18);

        // TextArea بخط Cairo Bold
        TextArea boldArea = new TextArea("تجربة خط Cairo Bold من ملف");
        boldArea.setFont(cairoBold);
        boldArea.setWrapText(true);

        // TextArea بخط Cairo SemiBold
        TextArea semiBoldArea = new TextArea("تجربة خط Cairo SemiBold من ملف");
        semiBoldArea.setFont(cairoSemiBold);
        semiBoldArea.setWrapText(true);

        VBox root = new VBox(20, boldArea, semiBoldArea);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f9f9f9;");

        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Cairo Font Test (from file)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
