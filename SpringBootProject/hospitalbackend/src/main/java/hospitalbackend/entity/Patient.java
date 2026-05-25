package hospitalbackend.entity;

import jakarta.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer patientId;

    private String patientName;

    private Integer daysAdmitted;

    private Integer totalFee;

    public Patient() {
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getDaysAdmitted() {
        return daysAdmitted;
    }

    public void setDaysAdmitted(Integer daysAdmitted) {
        this.daysAdmitted = daysAdmitted;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }
}