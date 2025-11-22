package com.example.consocket.server;

import com.example.contsocket.service.RecyclingPlantService;
import com.example.contsocket.entity.AssignmentRecord;
import com.example.contsocket.entity.RecyclingPlant;
import com.example.contsocket.dto.RecyclingPlantDto;
import com.example.contsocket.dto.AssignRequestDto;
import com.example.contsocket.dto.AssignResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

            if (cmd.equals("PLANT")) {
                RecyclingPlant plant = service.getPlant();
                return objectMapper.writeValueAsString(RecyclingPlantDto.map(plant));
            }

            if (cmd.startsWith("ADD_ASSIGNMENT")) {
                String[] parts = cmd.split(";");
                Long dumpsterId = Long.parseLong(parts[2].split("=")[1]);
                Long employeeId = Long.parseLong(parts[3].split("=")[1]);
                int filling = Integer.parseInt(parts[4].split("=")[1]);
                AssignmentRecord record = service.assignDumpsterToPlant(
                		dumpsterId, 
                		employeeId, 
                		LocalDate.now(), 
                		filling);

                if (record == null) {
                    return objectMapper.writeValueAsString(
                        new ErrorResponse("Plant not found")
                    );
                }
                return objectMapper.writeValueAsString(AssignResponseDto.map(record));
            }if (cmd.startsWith("GET_CAPACITY")) {
                try {
                    String[] parts = cmd.split(";");
                    LocalDate date = null;
                    for (String part : parts) {
                        if (part.startsWith("date=")) {
                            date = LocalDate.parse(part.split("=")[1]);
                        }
                    }

                    if (date == null) {
                        return "ERROR Missing date";
                    }

                    Integer remaining = service.getRemainingCapacity(date);
                    return remaining != null ? remaining.toString() : "ERROR Plant not found";
                } catch (Exception e) {
                    return "ERROR Invalid date format. Use YYYY-MM-DD";
                }
            }


            return objectMapper.writeValueAsString(new ErrorResponse("Unknown command"));

        } catch (Exception e) {
            try {
                return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
                return "{\"error\":\"Unknown error\"}";
            }
        }
    }

    static class ErrorResponse {
        public String error;
        public ErrorResponse(String msg) { this.error = msg; }
    }
}
