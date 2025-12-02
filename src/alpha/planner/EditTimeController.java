/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package alpha.planner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author ahmed.elkady
 */
public class EditTimeController implements Initializable {

   
    @FXML
    private JFXButton updatebtn;

    @FXML
    private JFXTextArea replace;

    @FXML
    private JFXTextField searchword;

    @FXML
    private JFXTextArea with;
    
    private List<String> lines; // Stores file content
    
    public static String linkk;
    
    
    @FXML
    void searchwordaction(KeyEvent event) { 
    }

    
    
    
    
    
    
    
    @FXML
    void updatebtnaction(ActionEvent event) throws IOException {
        
       String coco=String.join("\n", lines);
       String newwtext=coco.replace(replace.getText(),with.getText());
       
       PrintWriter pw=new PrintWriter (new FileWriter (linkk));
       pw.println(newwtext);
       pw.close();
       
       replace.setText("Updated Successfully!\n\n");
       with.setText("Updated Successfully!\n\n");
       
       //Update list of text
       
       try {
            lines = Files.readAllLines(Paths.get(linkk)); // Change file path if needed
        } catch (IOException e) {
            replace.setText("Error loading file!");
            with.setText("Error loading file!");
            return;
        }
       
        
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        String settingsfile=System.getProperty("user.home")+"\\AppData\\Roaming\\Alpha_Planning\\Settings.kady";
        String filePath = settingsfile;
        try {
        List<String> liness = Files.readAllLines(Paths.get(filePath));
        for (String line : liness) {
        if (line.startsWith("Times=")) {
        String value = line.split("=", 2)[1];
        linkk=value;
        break;
        }}
        } catch (IOException e) {e.printStackTrace();}
        
        
        
        
        
        
        try {
            lines = Files.readAllLines(Paths.get(linkk)); // Change file path if needed
        } catch (IOException e) {
            replace.setText("Error loading file!");
            with.setText("Error loading file!");
            return;
        }
        
        
        searchword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                String searchText = newValue.toUpperCase().trim();
                List<String> filteredLines = lines.stream()
                        .filter(line -> line.startsWith(searchText)) // Filter lines
                        .collect(Collectors.toList());

                replace.setText(String.join("\n", filteredLines)); // Update TextArea
                with.setText(String.join("\n", filteredLines)); // Update TextArea
            } else {
                replace.clear();
                with.clear();
            }
        });
        
        
    }

    
    
}
