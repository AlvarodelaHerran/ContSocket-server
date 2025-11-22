package com.example.contsocket.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.contsocket.entity.AssignmentRecord;

public class AssignResponseDto {
    private Long plantId;
    private Long dumpsterId;
    private Long employeeId;
    private LocalDate date;

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public static AssignResponseDto map(AssignmentRecord record) {
        AssignResponseDto response = new AssignResponseDto();
        response.setPlantId(record.getPlant().getId());
        response.setDumpsterId(record.getDumpsterId());
        response.setEmployeeId(record.getEmployeeId());
        response.setDate(record.getDate());
        return response;
    }


    public static List<AssignResponseDto> map(List<AssignmentRecord> assignmentRecords) {
        List<AssignResponseDto> response = new ArrayList<>();
        for (AssignmentRecord record : assignmentRecords) {
            response.add(map(record));
        }
        return response;
    }    
}
