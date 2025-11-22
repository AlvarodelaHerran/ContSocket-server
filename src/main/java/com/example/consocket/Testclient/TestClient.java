package com.example.consocket.Testclient;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8081;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Server: " + in.readLine());

            while (true) {
                System.out.print("Commande > ");
                String cmd = scanner.nextLine();

                if (cmd.equalsIgnoreCase("exit")) break;

                out.println(cmd);

                String response = in.readLine();
                System.out.println("Réponse: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
