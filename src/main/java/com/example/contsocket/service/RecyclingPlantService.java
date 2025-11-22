package com.example.contsocket.service;

import org.springframework.stereotype.Service;

import com.example.contsocket.entity.AssignmentRecord;
import com.example.contsocket.entity.RecyclingPlant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RecyclingPlantService {

    private final Map<Long, RecyclingPlant> plants = new HashMap<>();
    private final Map<Long, AssignmentRecord> assignments = new HashMap<>();

    private final AtomicLong assignmentIdGenerator = new AtomicLong(0);

    public RecyclingPlantService() {
        RecyclingPlant r1 = new RecyclingPlant("ContSocket", "Bilbao", 48010, 5000);
        createPlant(r1);

        AssignmentRecord a1 = new AssignmentRecord(1L, r1, 101L, LocalDate.now(), 100);
        assignDumpsterToPlant(a1.getDumpsterId(), a1.getEmployeeId(), a1.getDate(), a1.getFilling());
    }

    public List<RecyclingPlant> getAllPlants() {
        return new ArrayList<>(plants.values());
    }

    public RecyclingPlant createPlant(RecyclingPlant plant) {
        plant.setId(1L);
        plants.put(plant.getId(), plant);
        return plant;
    }

    public AssignmentRecord assignDumpsterToPlant(Long dumpsterId, Long employeeId, LocalDate date, int filling) {
        RecyclingPlant plant = plants.get(1L);

        AssignmentRecord record = new AssignmentRecord(dumpsterId, plant, employeeId, date, filling);
        long assignmentId = assignmentIdGenerator.incrementAndGet();
        assignments.put(assignmentId, record);

        plant.addAssignment(record);
        return record;
    }

    public Integer getRemainingCapacity(LocalDate date) {
        RecyclingPlant plant = plants.get(1L);
        if (plant == null) return null;
        int maxCapacity = plant.getMaxCapacity();
        for (AssignmentRecord record : assignments.values()) {
            if (record.getPlant().getId().equals(1L) && record.getDate().equals(date)) {
            	maxCapacity -= record.getFilling();
            }
        }

        return maxCapacity;
    }
}
