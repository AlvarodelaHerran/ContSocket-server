package com.example.contsocket.entity;

import java.io.Serializable;
import java.util.Objects;

public class AssignmentRecordId implements Serializable {
    private Long dumpsterId;
    private Long plant;

    public AssignmentRecordId() {}
    public AssignmentRecordId(Long dumpsterId, Long plant) {
        this.dumpsterId = dumpsterId;
        this.plant = plant;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof AssignmentRecordId)) return false;
        AssignmentRecordId that = (AssignmentRecordId) o;
        return Objects.equals(dumpsterId, that.dumpsterId) &&
               Objects.equals(plant, that.plant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dumpsterId, plant);
    }
}
