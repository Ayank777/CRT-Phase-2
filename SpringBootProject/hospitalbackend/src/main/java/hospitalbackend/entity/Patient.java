package hospitalbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Patient {

    @Id
    private int patientId;

    private String patientName;

    private int daysAdmitted;

    private int totalFee;

    public Patient() {
    }

    public Patient(int patientId,
                   String patientName,
                   int daysAdmitted,
                   int totalFee) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.daysAdmitted = daysAdmitted;
        this.totalFee = totalFee;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDaysAdmitted() {
        return daysAdmitted;
    }

    public void setDaysAdmitted(int daysAdmitted) {
        this.daysAdmitted = daysAdmitted;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }
}
