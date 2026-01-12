package com.example.consocket.server;

import com.example.contsocket.service.RecyclingPlantService;
import com.example.contsocket.entity.AssignmentRecord;
import com.example.contsocket.entity.RecyclingPlant;
import com.example.contsocket.dto.RecyclingPlantDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final RecyclingPlantService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientHandler(Socket client, RecyclingPlantService service) {
        this.client = client;
        this.service = service;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            out.println("CONNECTED");

            String line;
            while ((line = in.readLine()) != null) {
                out.println(handleCommand(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String handleCommand(String cmd) {
        try {
            if (cmd == null || cmd.trim().isEmpty()) {
                throw new IllegalArgumentException("Commande vide");
            }

            if (cmd.equals("PLANT")) {
                RecyclingPlant plant = service.getPlant();
                return objectMapper.writeValueAsString(RecyclingPlantDto.map(plant));
            }

            if (cmd.startsWith("ADD_ASSIGNMENT")) {
                String[] parts = cmd.split(";");
                
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Format invalide pour ADD_ASSIGNMENT. Utilisez: ADD_ASSIGNMENT;totalDumpsters=X;filling=Y");
                }
                
                String totalDumpstersStr = extractValue(parts[1], "totalDumpsters");
                String fillingStr = extractValue(parts[2], "filling");
                
                if (totalDumpstersStr == null || fillingStr == null) {
                    throw new IllegalArgumentException("Paramètres manquants. Utilisez: ADD_ASSIGNMENT;totalDumpsters=X;filling=Y");
                }
                
                try {
                    int totalDumpsters = Integer.parseInt(totalDumpstersStr);
                    int filling = Integer.parseInt(fillingStr);
                    
                    if (totalDumpsters <= 0) {
                        throw new IllegalArgumentException("totalDumpsters doit être positif");
                    }
                    if (filling <= 0) {
                        throw new IllegalArgumentException("filling doit être supérieur a 0");
                    }

                    AssignmentRecord record = service.assignDumpsterToPlant(totalDumpsters, filling);
                    return record != null ? "200" : "404";
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Les valeurs totalDumpsters et filling doivent être des nombres entiers");
                }
            }

            if (cmd.startsWith("GET_CAPACITY")) {
                String[] parts = cmd.split(";");
                
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Format invalide pour GET_CAPACITY. Utilisez: GET_CAPACITY;date=YYYY-MM-DD");
                }
                
                LocalDate date = null;
                boolean dateFound = false;
                
                for (String part : parts) {
                    if (part.startsWith("date=")) {
                        dateFound = true;
                        String dateStr = part.substring(5);
                        
                        if (dateStr == null || dateStr.isEmpty()) {
                            throw new IllegalArgumentException("La date ne peut pas être vide");
                        }
                        
                        try {
                            date = LocalDate.parse(dateStr);
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Format de date invalide. Utilisez YYYY-MM-DD");
                        }
                        break;
                    }
                }
                
                if (!dateFound) {
                    throw new IllegalArgumentException("Paramètre 'date' manquant. Utilisez: GET_CAPACITY;date=YYYY-MM-DD");
                }

                Integer remaining = service.getRemainingCapacity(date);
                return remaining != null ? remaining.toString() : "404";
            }

            throw new IllegalArgumentException("Commande inconnue: " + cmd);

        } catch (IllegalArgumentException e) {
            return "400: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "500: " + e.getMessage();
        }
    }
    private String extractValue(String param, String expectedKey) {
        if (param == null || !param.contains("=")) {
            return null;
        }
        
        String[] keyValue = param.split("=", 2);
        if (keyValue.length != 2 || !keyValue[0].equals(expectedKey)) {
            return null;
        }
        
        return keyValue[1];
    }
}