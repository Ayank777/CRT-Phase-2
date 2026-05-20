import java.util.*;

// Abstract Class
abstract class Hospital {

    int patientId;
    String patientName;

    Hospital(int patientId, String patientName) {

        this.patientId = patientId;
        this.patientName = patientName;
    }

    abstract int calculateFee(int days);
}

// Room Class
class Room extends Hospital {

    Room(int patientId, String patientName) {

        super(patientId, patientName);
    }

    @Override
    int calculateFee(int days) {

        // Doctor Fee = 700 per day
        return days * 700;
    }
}

// Doctor Class
class Doctor {

    String doctorName;
    String specialization;

    Doctor(String doctorName, String specialization) {

        this.doctorName = doctorName;
        this.specialization = specialization;
    }

    void displayDoctor() {

        System.out.println(
            "Doctor: " +
            doctorName +
            " | Specialization: " +
            specialization
        );
    }
}

// Bed Class
class Bed {

    int bedNumber;

    Room patient;

    Bed(int bedNumber) {

        this.bedNumber = bedNumber;
    }

    boolean isEmpty() {

        return patient == null;
    }
}

// Hospital Management Class
class HospitalManagement {

    ArrayList<Bed> beds = new ArrayList<>();

    Doctor doctor;

    HospitalManagement(int totalBeds, Doctor doctor) {

        this.doctor = doctor;

        for (int i = 1; i <= totalBeds; i++) {

            beds.add(new Bed(i));
        }
    }

    // Admit Patient
    void admitPatient(Room patient) {

        for (Bed bed : beds) {

            if (bed.isEmpty()) {

                bed.patient = patient;

                System.out.println(
                    patient.patientName +
                    " admitted to Bed " +
                    bed.bedNumber
                );

                return;
            }
        }

        System.out.println("Hospital Full");
    }

    // Discharge Patient
    void dischargePatient(int patientId, int days) {

        for (Bed bed : beds) {

            if (
                !bed.isEmpty() &&
                bed.patient.patientId == patientId
            ) {

                int fee =
                    bed.patient.calculateFee(days);

                System.out.println(
                    "\nPatient Discharged Successfully"
                );

                System.out.println(
                    "Patient Name: " +
                    bed.patient.patientName
                );

                System.out.println(
                    "Total Fees = $" + fee
                );

                bed.patient = null;

                return;
            }
        }

        System.out.println("Patient Not Found");
    }

    // Search Patient
    void searchPatient(int patientId) {

        for (Bed bed : beds) {

            if (
                !bed.isEmpty() &&
                bed.patient.patientId == patientId
            ) {

                System.out.println(
                    "Patient Found"
                );

                System.out.println(
                    "Name: " +
                    bed.patient.patientName
                );

                System.out.println(
                    "Bed Number: " +
                    bed.bedNumber
                );

                return;
            }
        }

        System.out.println("Patient Not Found");
    }

    // Display Hospital Status
    void displayStatus() {

        int totalPatients = 0;

        System.out.println(
            "\n===== Hospital Status ====="
        );

        for (Bed bed : beds) {

            if (bed.isEmpty()) {

                System.out.println(
                    "Bed " +
                    bed.bedNumber +
                    " is Empty"
                );

            } else {

                totalPatients++;

                System.out.println(
                    "Bed " +
                    bed.bedNumber +
                    " occupied by " +
                    bed.patient.patientName
                );
            }
        }

        System.out.println(
            "\nTotal Patients: " +
            totalPatients
        );

        System.out.println(
            "Available Beds: " +
            (beds.size() - totalPatients)
        );
    }
}

// Main Class
public class Hospitalsys {

    public static void main(String[] args) {

        // Doctor Object
        Doctor d1 =
            new Doctor(
                "Dr. Sharma ji ka beta",
                "Cardiologist"
            );

        d1.displayDoctor();

        // Hospital Object
        HospitalManagement h =
            new HospitalManagement(5, d1);

        // Patient Objects
        Room p1 =
            new Room(101, "Ayan");

        Room p2 =
            new Room(102, "ayman");

        Room p3 =
            new Room(103, "aymaan");

        // Admit Patients
        h.admitPatient(p1);

        h.admitPatient(p2);

        h.admitPatient(p3);

        // Display Status
        h.displayStatus();

        // Search Patient
        System.out.println(
            "\n===== Searching Patient ====="
        );

        h.searchPatient(102);

        // Discharge Patient
        System.out.println(
            "\n===== Discharging Patient ====="
        );

        h.dischargePatient(102, 3);

        // Final Status
        h.displayStatus();
    }
}