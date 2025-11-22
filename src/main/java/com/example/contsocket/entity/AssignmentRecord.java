package com.example.contsocket.entity;

import java.time.LocalDate;

public class AssignmentRecord {

    private Long dumpsterId;
    private RecyclingPlant plant;
    private Long employeeId;
    private LocalDate date;
    private int filling;

    public AssignmentRecord() {}

    public AssignmentRecord(Long dumpsterId, RecyclingPlant plant,
                            Long employeeId, LocalDate date, int filling) {
        this.dumpsterId = dumpsterId;
        this.plant = plant;
        this.employeeId = employeeId;
        this.date = date;
        this.filling = filling;
    }

    public Long getDumpsterId() { return dumpsterId; }
    public void setDumpsterId(Long dumpsterId) { this.dumpsterId = dumpsterId; }

    public RecyclingPlant getPlant() { return plant; }
    public void setPlant(RecyclingPlant plant) { this.plant = plant; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getFilling() { return filling; }
    public void setFilling(int filling) { this.filling = filling; }
}
