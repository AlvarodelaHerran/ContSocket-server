package com.example.consocket.server;

import com.example.contsocket.service.RecyclingPlantService;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private final RecyclingPlantService service = new RecyclingPlantService();

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");
                new Thread(new ClientHandler(clientSocket, service)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
