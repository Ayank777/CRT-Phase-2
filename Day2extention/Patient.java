public class Patient {

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

    public String getPatientName() {
        return patientName;
    }

    public int getDaysAdmitted() {
        return daysAdmitted;
    }

    public int getTotalFee() {
        return totalFee;
    }
}