import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    String admitPatient(Room patient) {

        for (Bed bed : beds) {

            if (bed.isEmpty()) {

                bed.patient = patient;

                return "SUCCESS: "
                        + patient.patientName
                        + " admitted to Bed "
                        + bed.bedNumber;
            }
        }

        return "Hospital Full";
    }

    String searchPatient(int patientId) {

        for (Bed bed : beds) {

            if (!bed.isEmpty()
                    && bed.patient.patientId == patientId) {

                return "Patient Found\n\n"
                        + "Name: "
                        + bed.patient.patientName
                        + "\nBed Number: "
                        + bed.bedNumber;
            }
        }

        return "Patient Not Found";
    }

    String dischargePatient(int patientId, int days) {

        for (Bed bed : beds) {

            if (!bed.isEmpty()
                    && bed.patient.patientId == patientId) {

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
                "-fx-font-size: 14px;"
                        + "-fx-background-radius: 10;"
                        + "-fx-padding: 10;";

        idField.setStyle(inputStyle);

        nameField.setStyle(inputStyle);

        daysField.setStyle(inputStyle);

        // Button Styling
        String buttonStyle =
                "-fx-background-color: #1565C0;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-size: 14px;"
                        + "-fx-background-radius: 12;"
                        + "-fx-padding: 10 20 10 20;";

        // Buttons
        Button admitBtn =
                new Button("Admit");

        Button searchBtn =
                new Button("Search");

        Button dischargeBtn =
                new Button("Discharge");

        Button statusBtn =
                new Button("Show Status");

        Button loadBtn =
                new Button("Load Patients");

        Button deleteBtn =
        new Button("Delete");

        admitBtn.setStyle(buttonStyle);

        searchBtn.setStyle(buttonStyle);

        dischargeBtn.setStyle(buttonStyle);

        statusBtn.setStyle(buttonStyle);

        loadBtn.setStyle(buttonStyle);

        deleteBtn.setStyle(buttonStyle);

        // Output Area
        TextArea output =
                new TextArea();

        output.setEditable(false);

        output.setPrefHeight(150);

        // TableView
        TableView<Patient> table =
                new TableView<>();

        // ID Column
        TableColumn<Patient, Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "patientId"));

        // Name Column
        TableColumn<Patient, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "patientName"));

        // Days Column
        TableColumn<Patient, Integer> daysColumn =
                new TableColumn<>("Days");

        daysColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "daysAdmitted"));

        // Fee Column
        TableColumn<Patient, Integer> feeColumn =
                new TableColumn<>("Fee");

        feeColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "totalFee"));

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                daysColumn,
                feeColumn);

        table.setPrefHeight(250);

        // Admit Button Action
        admitBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText());

                String name =
                        nameField.getText();

                int days =
                        Integer.parseInt(
                                daysField.getText());

                int totalFee =
                        days * 700;

                Patient patient =
                        new Patient(
                                id,
                                name,
                                days,
                                totalFee);

                String response =
                        ApiService.addPatient(patient);

                output.setText(
                        "Patient Added Successfully\n\n"
                                + response);

            } catch (Exception ex) {

                ex.printStackTrace();

                output.setText(
                        "Invalid Input");
            }
        });

        // Search
        searchBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText());

                String result =
                        hospital.searchPatient(id);

                output.setText(result);

            } catch (Exception ex) {

                output.setText("Invalid ID");
            }
        });

        // Discharge
        dischargeBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText());

                int days =
                        Integer.parseInt(
                                daysField.getText());

                String result =
                        hospital.dischargePatient(
                                id,
                                days);

                output.setText(result);

            } catch (Exception ex) {

                output.setText(
                        "Invalid Input");
            }
        });

        // Status
        statusBtn.setOnAction(e -> {

            output.setText(
                    hospital.displayStatus());
        });

        // Load Patients
        loadBtn.setOnAction(e -> {

            try {

                ObservableList<Patient> data =
                        FXCollections.observableArrayList(
                                ApiService.getPatients());

                table.setItems(data);

            } catch (Exception ex) {

                ex.printStackTrace();

                output.setText(
                        "Failed To Load Patients");
            }
        });

        deleteBtn.setOnAction(e -> {

    try {

        int id =
                Integer.parseInt(
                        idField.getText());

        String response =
                ApiService.deletePatient(id);

        output.setText(response);

        ObservableList<Patient> data =
                FXCollections.observableArrayList(
                        ApiService.getPatients());

        table.setItems(data);

    } catch (Exception ex) {

        ex.printStackTrace();

        output.setText(
                "Delete Failed");
    }
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
                statusBtn,
                loadBtn,
                deleteBtn
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
                table,
                output
        );

        // Scene
        Scene scene =
                new Scene(root, 700, 650);

        stage.setTitle(
                "Hospital Management System");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}