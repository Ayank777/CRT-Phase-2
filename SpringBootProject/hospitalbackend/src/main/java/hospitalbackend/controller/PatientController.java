package hospitalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hospitalbackend.entity.Patient;
import hospitalbackend.repository.PatientRepository;

@RestController
@RequestMapping("/patients")
@CrossOrigin("*")
public class PatientController {

    @Autowired
    private PatientRepository repo;

    // Add Patient
    @PostMapping
    public Patient addPatient(
            @RequestBody Patient patient) {

        return repo.save(patient);
    }

    // Get All Patients
    @GetMapping
    public List<Patient> getAllPatients() {

        return repo.findAll();
    }
}
