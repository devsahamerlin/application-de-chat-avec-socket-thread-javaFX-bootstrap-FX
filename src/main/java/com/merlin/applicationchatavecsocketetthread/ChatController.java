package com.merlin.applicationchatavecsocketetthread;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class ChatController {
    private ChatClient client;

    @FXML
    private TextField messageField, nameField;

    @FXML
    private ListView<String> chatListView;

    @FXML
    private Button joinButton, sendButton;

    public void initialize() {
        joinButton.getStyleClass().add("btn-primary");
        sendButton.getStyleClass().add("btn-info");
    }

    public void setClient(ChatClient client) {
        this.client = client;
    }

    @FXML
    public void handleJoinButtonClick() {
        String name = nameField.getText();
        if (name != null && !name.isEmpty()) {
            client.clientName = name;
            client.sendMessage(name); // Send name to the server
            nameField.setEditable(false); // Disable name field after join

            joinButton.setDisable(true); // Disables the button
            joinButton.setVisible(false); // Optionally hide the button
        }
    }

    @FXML
    public void handleSendButtonClick() {
        String message = messageField.getText();
        if (message != null && !message.isEmpty()) {
            client.sendMessage(message);
            messageField.clear();
        }
    }

    public void addMessageToChat(String message) {
        final ChatClient localClient = client;
        if (message.startsWith(localClient.clientName + ":")) {
            message = "Me: " + message.substring(localClient.clientName.length() + 1);
        }
        chatListView.getItems().add(message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

