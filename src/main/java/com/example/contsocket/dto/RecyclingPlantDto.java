package com.example.contsocket.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.contsocket.entity.RecyclingPlant;

public class RecyclingPlantDto {

    private Long id;
    private String name;
    private String location;
    private int postalCode;
    private int maxCapacity;
    private int currentFill;
    private List<AssignResponseDto> assignments = new ArrayList<>();

    public RecyclingPlantDto() {}

    public RecyclingPlantDto(Long id, String name, String location, int postalCode, int maxCapacity, int currentFill) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.postalCode = postalCode;
        this.maxCapacity = maxCapacity;
        this.currentFill = currentFill;
        this.currentFill = currentFill;
    }

    public RecyclingPlantDto(Long id, String name, String location, int postalCode, int maxCapacity, int currentFill, List<AssignResponseDto> assignments) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.postalCode = postalCode;
        this.maxCapacity = maxCapacity;
        this.currentFill = currentFill;
        this.currentFill = currentFill;
        this.assignments = assignments;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getCurrentFill() { return currentFill; }
    public void setCurrentFill(int currentFill) { this.currentFill = currentFill; }

    public List<AssignResponseDto> getAssignments() { return assignments; }
    public void addAssignment(AssignResponseDto assignments) { this.assignments.add(assignments); }

    public static RecyclingPlantDto map(RecyclingPlant plant) {
    	return new RecyclingPlantDto(
            plant.getId(),
            plant.getName(),
            plant.getLocation(),
            plant.getPostalCode(),
            plant.getMaxCapacity(),
            plant.getCurrentFill(),
            AssignResponseDto.map(plant.getAssignments())
        );
    }
}
