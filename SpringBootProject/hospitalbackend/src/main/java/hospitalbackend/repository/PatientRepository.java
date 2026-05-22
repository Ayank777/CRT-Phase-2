package hospitalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hospitalbackend.entity.Patient;

public interface PatientRepository
        extends JpaRepository<Patient, Integer> {
}