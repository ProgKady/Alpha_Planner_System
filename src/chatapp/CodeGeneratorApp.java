package chatapp;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class CodeGeneratorApp extends Application {

    private TextArea textArea;
    private TextField numberField;
    private Label resultLabel;

    @Override
    public void start(Stage stage) {
        
        
        

        textArea = new TextArea();
        textArea.setPromptText("اكتب الأكواد هنا (كل كود في سطر)");

        numberField = new TextField();
        numberField.setPromptText("عدد الأكواد المطلوب توليدها");

        Button generateBtn = new Button("توليد أكواد جديدة");
        Button checkBtn = new Button("اختبار التكرار");

        resultLabel = new Label();

        generateBtn.setOnAction(e -> generateNewCodes());
        checkBtn.setOnAction(e -> checkDuplicates());

        HBox controls = new HBox(10, numberField, generateBtn, checkBtn);
        controls.setPadding(new Insets(10));

        VBox root = new VBox(10, textArea, controls, resultLabel);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("مولد الأكواد - JavaFX");
        stage.show();
    }

    private void generateNewCodes() {
        try {
            int toGenerate = Integer.parseInt(numberField.getText().trim());
            if (toGenerate <= 0) {
                resultLabel.setText("❌ أدخل رقم أكبر من صفر");
                return;
            }

            // ✅ تحميل الأكواد القديمة بدون تغييرها
            Set<String> allCodes = new LinkedHashSet<>();
            for (String line : textArea.getText().split("\n")) {
                String code = line.trim();
                if (code.matches("\\d{6}")) { // لازم 6 أرقام
                    allCodes.add(code);
                }
            }

            // ✅ توليد الأكواد الجديدة بدون تكرار
            Random random = new Random();
            int added = 0;

            while (added < toGenerate) {
                String code = String.format("%06d", random.nextInt(1_000_000));

                if (!allCodes.contains(code)) {
                    allCodes.add(code);
                    added++;
                }
            }

            // ✅ إعادة عرض الأكواد كلها
            textArea.clear();
            for (String c : allCodes) {
                textArea.appendText(c + "\n");
            }

            resultLabel.setText("✅ تم إضافة " + toGenerate + " كود جديد بدون تكرار");

        } catch (Exception ex) {
            resultLabel.setText("❌ أدخل رقم صحيح");
        }
    }


    private void checkDuplicates() {
        String[] lines = textArea.getText().split("\n");
        Map<String, Integer> map = new LinkedHashMap<>();

        for (String line : lines) {
            String code = line.trim();
            if (!code.isEmpty()) {
                map.put(code, map.getOrDefault(code, 0) + 1);
            }
        }

        List<String> duplicates = new ArrayList<>();

        for (String c : map.keySet()) {
            if (map.get(c) > 1) {
                duplicates.add(c + " ← مكرر " + map.get(c) + " مرّات");
            }
        }

        if (duplicates.isEmpty()) {
            resultLabel.setText("✅ لا يوجد أي تكرار في الأكواد");
        } else {
            resultLabel.setText("❌ أكواد مكررة:\n" + String.join("\n", duplicates));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
