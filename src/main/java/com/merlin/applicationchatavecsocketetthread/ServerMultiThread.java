package com.merlin.applicationchatavecsocketetthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultiThread {
    private static final int PORT = 9090;

    public static void main(String[] args) {
        System.out.println("Server is running on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientSocketThreadHandler socketThread = new ClientSocketThreadHandler(socket);
                socketThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


