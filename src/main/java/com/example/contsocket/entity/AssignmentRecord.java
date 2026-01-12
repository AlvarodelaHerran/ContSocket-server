package com.example.contsocket.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AssignmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private RecyclingPlant plant;
    private LocalDate date;
    private int filling;
    private int totalDumpster;

    public AssignmentRecord() {}

    public AssignmentRecord(RecyclingPlant plant, LocalDate date, int filling, int totalDumpster) {
    	this.plant = plant;
        this.filling = filling;
        this.date = date;
        this.totalDumpster = totalDumpster;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getTotalDumpster() { return totalDumpster; }
    public void setTotalDumpster(int totalDumpster) { this.totalDumpster = totalDumpster; }
    public RecyclingPlant getPlant() { return plant; }
    public void setPlant(RecyclingPlant plant) { this.plant = plant; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getFilling() { return filling; }
    public void setFilling(int filling) { this.filling = filling; }
}
