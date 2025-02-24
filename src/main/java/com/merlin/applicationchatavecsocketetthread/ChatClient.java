package com.merlin.applicationchatavecsocketetthread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Application {
    String clientName;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ChatController controller;
    private static String SERVER_IP = "localhost";
    private static int SERVER_PORT = 9090;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat_ui.fxml"));
        VBox root = loader.load();
        controller = loader.getController();
        controller.setClient(this);

        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("Chat Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Connect to the server on a separate thread
        new Thread(this::connectToServer).start();

    }

    private void connectToServer() {
        try {

            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read the server's welcome message
            String response = in.readLine();
            Platform.runLater(() -> {
                controller.addMessageToChat(response);  // Display the server's welcome message
            });

            // Listen for incoming messages from the server
            String message;
            while ((message = in.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> {
                    // Update the chat UI with the new message
                    controller.addMessageToChat(finalMessage);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null && !message.isEmpty()) {
            out.println(message);
        }
    }
}





