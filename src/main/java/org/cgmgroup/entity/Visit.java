package org.cgmgroup.entity;

import jakarta.persistence.*;
import org.cgmgroup.entity.enums.VisitReason;
import org.cgmgroup.entity.enums.VisitType;

import java.time.LocalDateTime;
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime dateAndTime;
    private VisitType visitType;
    private VisitReason visitReason;
    private String familyHistory;
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public VisitReason getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(VisitReason visitReason) {
        this.visitReason = visitReason;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
