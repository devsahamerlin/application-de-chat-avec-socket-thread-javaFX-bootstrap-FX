package com.merlin.applicationchatavecsocketetthread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketThreadHandler extends Thread{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private static List<PrintWriter> clientWriters = new ArrayList<>();

    public ClientSocketThreadHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (clientWriters) {
                clientWriters.add(out);
            }

            // Read client name
            clientName = in.readLine();
            out.println("Welcome " + clientName + "! You can now start chatting.");
            broadcastMessage(clientName + " has joined the chat!");

            String message;
            while ((message = in.readLine()) != null) {
                broadcastMessage(clientName + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}
