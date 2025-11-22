package com.example.contsocket;

import com.example.consocket.server.SocketServer;

public class ContSocketApplication {
    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start(8081);
    }
}
