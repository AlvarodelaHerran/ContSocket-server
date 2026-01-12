package com.example.contsocket.dto;

public class AssignRequestDto {

    private int totalDumpsters;
    private int filling;

    public AssignRequestDto() {}

    public int getFilling() {
        return filling;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }

    public int getTotalDumpsters() {
        return totalDumpsters;
    }

    public void setTotalDumpsters(int totalDumpsters) {
        this.totalDumpsters = totalDumpsters;
    }
}
