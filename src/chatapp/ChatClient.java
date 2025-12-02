package chatapp;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClient extends Application {
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    private TextArea chatArea = new TextArea();
    private TextField messageField = new TextField();
    private TextField toField = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Chat Client");

        // Login UI
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        Button loginBtn = new Button("Login");

        VBox loginBox = new VBox(10, usernameField, loginBtn);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(20));

        Scene loginScene = new Scene(loginBox, 300, 200);

        // Chat UI
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        toField.setPromptText("Send to (username)");
        messageField.setPromptText("Type your message");

        Button sendBtn = new Button("Send");
        HBox inputBox = new HBox(5, toField, messageField, sendBtn);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));

        VBox chatBox = new VBox(10, chatArea, inputBox);
        chatBox.setPadding(new Insets(10));
        Scene chatScene = new Scene(chatBox, 500, 400);

        // Login button action
        loginBtn.setOnAction(e -> {
            username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                try {
                    Socket socket = new Socket("localhost", 5555);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());

                    out.writeUTF("USERNAME:" + username);

                    // Start listening for messages
                    new Thread(() -> {
                        try {
                            String msg;
                            while ((msg = in.readUTF()) != null) {
                                String finalMsg = msg;
                                Platform.runLater(() -> chatArea.appendText(finalMsg + "\n"));
                            }
                        } catch (IOException ex) {
                            Platform.runLater(() -> chatArea.appendText("Disconnected.\n"));
                        }
                    }).start();

                    stage.setScene(chatScene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert("Connection Error", "Cannot connect to server.");
                }
            }
        });

        // Send button action
        sendBtn.setOnAction(e -> {
            String toUser = toField.getText().trim();
            String text = messageField.getText().trim();
            if (!toUser.isEmpty() && !text.isEmpty()) {
                try {
                    out.writeUTF("TO:" + toUser + "|" + text);
                    chatArea.appendText("Me ? " + toUser + ": " + text + "\n");
                    messageField.clear();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        stage.setScene(loginScene);
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
