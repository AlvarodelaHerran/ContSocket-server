package com.example.contsocket.service;

import org.springframework.stereotype.Service;

import com.example.contsocket.entity.AssignmentRecord;
import com.example.contsocket.entity.RecyclingPlant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RecyclingPlantService {

    private final Map<String, RecyclingPlant> plants = new HashMap<>();
    private final Map<Long, AssignmentRecord> assignments = new HashMap<>();

    private final AtomicLong assignmentIdGenerator = new AtomicLong(0);

    public RecyclingPlantService() {
        RecyclingPlant r1 = new RecyclingPlant("ContSocket", "Bilbao", 48010, 5000);
        createPlant(r1);

        AssignmentRecord a1 = new AssignmentRecord(r1, LocalDate.now().minusDays(3), 1000, 1);
        AssignmentRecord a2 = new AssignmentRecord(r1, LocalDate.now(), 1100, 1);

        r1.addAssignment(a1);
        r1.addAssignment(a2);

        createAssignment(a1);
        createAssignment(a2);
        
    }

    public RecyclingPlant createPlant(RecyclingPlant plant) {
        plants.put(plant.getName(), plant);
        return plant;
    }

    public AssignmentRecord createAssignment(AssignmentRecord assignmentRecord) {
        assignments.put(assignmentIdGenerator.incrementAndGet(), assignmentRecord);
        return assignmentRecord;
    }

    public RecyclingPlant getPlant() {
    	RecyclingPlant plant = plants.get("ContSocket");

        List<AssignmentRecord> filteredAssignments = plant.getAssignments()
                .stream()
                .filter(a -> a.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
        
        plant.getAssignments().clear();
        plant.getAssignments().addAll(filteredAssignments);
        plant.setCurrentFill(filteredAssignments
        		.stream()
        		.mapToInt(AssignmentRecord::getFilling)
                .sum());

        return plant;
    }
    
    public AssignmentRecord assignDumpsterToPlant( int totalDumpster, int filling) {
        RecyclingPlant plant = plants.get("ContSocket");

        AssignmentRecord record = new AssignmentRecord(plant, LocalDate.now(), filling, totalDumpster);
        assignments.put(assignmentIdGenerator.incrementAndGet(), record);

        plant.addAssignment(record);
        return record;
    }

    public Integer getRemainingCapacity(LocalDate date) {
        RecyclingPlant plant = plants.get("ContSocket");
        if (plant == null) return null;

        int usedCapacity = assignments.values()
                .stream()
                .filter(a -> a.getDate().equals(date))
                .mapToInt(AssignmentRecord::getFilling)
                .sum();

        return plant.getMaxCapacity() - usedCapacity;
    }
}