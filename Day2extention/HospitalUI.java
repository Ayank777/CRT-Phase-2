import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;

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

        return days * 700;
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

    HospitalManagement(int totalBeds) {

        for (int i = 1; i <= totalBeds; i++) {

            beds.add(new Bed(i));
        }
    }

    // Admit Patient
    String admitPatient(Room patient) {

        for (Bed bed : beds) {

            if (bed.isEmpty()) {

                bed.patient = patient;

                return "SUCCESS: " +
                        patient.patientName +
                        " admitted to Bed " +
                        bed.bedNumber;
            }
        }

        return "Hospital Full";
    }

    // Search Patient
    String searchPatient(int patientId) {

        for (Bed bed : beds) {

            if (!bed.isEmpty() &&
                    bed.patient.patientId == patientId) {

                return "Patient Found\n\n"
                        + "Name: "
                        + bed.patient.patientName
                        + "\nBed Number: "
                        + bed.bedNumber;
            }
        }

        return "Patient Not Found";
    }

    // Discharge Patient
    String dischargePatient(int patientId, int days) {

        for (Bed bed : beds) {

            if (!bed.isEmpty() &&
                    bed.patient.patientId == patientId) {

                int fee =
                        bed.patient.calculateFee(days);

                String result =
                        "Patient Discharged\n\n"
                                + "Name: "
                                + bed.patient.patientName
                                + "\nTotal Fee: Rs."
                                + fee;

                bed.patient = null;

                return result;
            }
        }

        return "Patient Not Found";
    }

    // Display Status
    String displayStatus() {

        StringBuilder status =
                new StringBuilder();

        status.append(
                "===== Hospital Status =====\n\n");

        for (Bed bed : beds) {

            if (bed.isEmpty()) {

                status.append(
                        "Bed ")
                        .append(bed.bedNumber)
                        .append(" -> Empty\n");

            } else {

                status.append(
                        "Bed ")
                        .append(bed.bedNumber)
                        .append(" -> ")
                        .append(bed.patient.patientName)
                        .append("\n");
            }
        }

        return status.toString();
    }
}

// Main JavaFX UI Class
public class HospitalUI extends Application {

    HospitalManagement hospital =
            new HospitalManagement(5);

    @Override
    public void start(Stage stage) {

        // Title
        Label title =
                new Label("Hospital Management System");

        title.setFont(Font.font("Arial", 28));

        title.setTextFill(Color.DARKBLUE);

        // Text Fields
        TextField idField =
                new TextField();

        idField.setPromptText(
                "Enter Patient ID");

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Enter Patient Name");

        TextField daysField =
                new TextField();

        daysField.setPromptText(
                "Enter Number of Days");

        // Input Styling
        String inputStyle =
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10;";

        idField.setStyle(inputStyle);

        nameField.setStyle(inputStyle);

        daysField.setStyle(inputStyle);

        // Buttons
        Button admitBtn =
                new Button("Admit");

        Button searchBtn =
                new Button("Search");

        Button dischargeBtn =
                new Button("Discharge");

        Button statusBtn =
                new Button("Show Status");

        // Button Styling
        String buttonStyle =
                "-fx-background-color: #1565C0;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 12;" +
                "-fx-padding: 10 20 10 20;";

        admitBtn.setStyle(buttonStyle);

        searchBtn.setStyle(buttonStyle);

        dischargeBtn.setStyle(buttonStyle);

        statusBtn.setStyle(buttonStyle);

        // Output Area
        TextArea output =
                new TextArea();

        output.setEditable(false);

        output.setPrefHeight(250);

        output.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-control-inner-background: #F5F5F5;" +
                "-fx-background-radius: 15;"
        );

        // Admit Button Action
        admitBtn.setOnAction(e -> {

    try {

        // Get Input Values
        int id =
                Integer.parseInt(
                        idField.getText());

        String name =
                nameField.getText();

        int days =
                Integer.parseInt(
                        daysField.getText());

        // Fee Calculation
        int totalFee =
                days * 700;

        // Database Connection
        Connection con =
                DatabaseConnection.connect();

        // SQL Query
        String query =
                "INSERT INTO patients VALUES (?, ?, ?, ?)";

        PreparedStatement pst =
                con.prepareStatement(query);

        // Set Values
        pst.setInt(1, id);

        pst.setString(2, name);

        pst.setInt(3, days);

        pst.setInt(4, totalFee);

        // Execute Query
        int rows =
                pst.executeUpdate();

        if (rows > 0) {

            output.setText(
                    "Patient Added Successfully\n\n"
                    + "Total Fee: Rs. "
                    + totalFee);

        } else {

            output.setText(
                    "Failed To Add Patient");
        }

        // Close
        pst.close();

        con.close();

        // Clear Fields
        idField.clear();

        nameField.clear();

        daysField.clear();

    } catch (Exception ex) {

        ex.printStackTrace();

        output.setText(
                "Database Error");
    }
});

        // Search Button Action
        searchBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText());

                String result =
                        hospital.searchPatient(id);

                output.setText(result);

            } catch (Exception ex) {

                output.setText(
                        "Invalid ID");
            }
        });

        // Discharge Button Action
        dischargeBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText());

                int days =
                        Integer.parseInt(
                                daysField.getText());

                String result =
                        hospital.dischargePatient(id, days);

                output.setText(result);

            } catch (Exception ex) {

                output.setText(
                        "Invalid Input");
            }
        });

        // Status Button Action
        statusBtn.setOnAction(e -> {

            output.setText(
                    hospital.displayStatus());
        });

        // Button Layout
        HBox buttonBox =
                new HBox(15);

        buttonBox.setAlignment(
                Pos.CENTER);

        buttonBox.getChildren().addAll(
                admitBtn,
                searchBtn,
                dischargeBtn,
                statusBtn
        );

        // Main Layout
        VBox root =
                new VBox(20);

        root.setPadding(
                new Insets(25));

        root.setAlignment(
                Pos.TOP_CENTER);

        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #E3F2FD, #BBDEFB);"
        );

        root.getChildren().addAll(
                title,
                idField,
                nameField,
                daysField,
                buttonBox,
                output
        );

        // Scene
        Scene scene =
                new Scene(root, 600, 550);

        stage.setTitle(
                "Hospital Management System");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}