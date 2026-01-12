package com.example.contsocket.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RecyclingPlant {

    @Id
    @Column(unique = true, nullable = false)
    private String name;
    private int postalCode;
    private String location;
    private int maxCapacity;
    private int currentFill;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentRecord> assignments = new ArrayList<>();

    public RecyclingPlant() {}

    public RecyclingPlant(String name, String location, int postalCode, int maxCapacity) {
        this.name = name;
        this.location = location;
        this.postalCode = postalCode;
        this.maxCapacity = maxCapacity;
        this.currentFill = 0;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public int getCurrentFill() { return currentFill; }
    public void setCurrentFill(int currentFill) { this.currentFill = currentFill; }
    public List<AssignmentRecord> getAssignments() { return assignments; }

    public void addAssignment(AssignmentRecord record) {
        assignments.add(record);
        record.setPlant(this);
    }

    public void removeAssignment(AssignmentRecord record) {
        if (assignments.remove(record)) {
            record.setPlant(null);
        }
    }
}
