package com.example.contsocket.dto;

import com.example.contsocket.entity.RecyclingPlant;

public class RecyclingPlantDto {

    private String name;
    private String location;
    private int postalCode;
    private int maxCapacity;
    private int currentFill;

    public RecyclingPlantDto() {}

    public RecyclingPlantDto(String name, String location, int postalCode, int maxCapacity, int currentFill) {
        this.name = name;
        this.location = location;
        this.postalCode = postalCode;
        this.maxCapacity = maxCapacity;
        this.currentFill = currentFill;
        this.currentFill = currentFill;
    }

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

    public static RecyclingPlantDto map(RecyclingPlant plant) {
        return new RecyclingPlantDto(
            plant.getName(),
            plant.getLocation(),
            plant.getPostalCode(),
            plant.getMaxCapacity(),
            plant.getCurrentFill()
        );
    }
}
