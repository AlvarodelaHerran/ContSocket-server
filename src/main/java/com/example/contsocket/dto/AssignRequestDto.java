package com.example.contsocket.dto;

public class AssignRequestDto {

    private Long plantId;
    private Long dumpsterId;
    private Long employeeId;
    private int filling;

    public AssignRequestDto() {}

    public AssignRequestDto(Long plantId, Long dumpsterId, Long employeeId, int filling) {
        this.plantId = plantId;
        this.dumpsterId = dumpsterId;
        this.employeeId = employeeId;
        this.filling = filling;
    }

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

    public int getFilling() {
        return filling;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }
}
