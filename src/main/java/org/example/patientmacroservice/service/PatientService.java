package org.example.patientmacroservice.service;

import org.example.patientmacroservice.model.Patient;
import org.example.patientmacroservice.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private Repository repository;

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public Optional<Patient> getPatientById(String id) {
        return repository.findById(id);
    }

    public Patient addPatient(Patient patient) {
        return repository.save(patient);
    }

    public Patient updatePatient(String id, Patient patientDetails) {
        return repository.findById(id)
                .map(patient -> {
                    patient.setFirstName(patientDetails.getFirstName());
                    patient.setFirstName(patientDetails.getLastName());
                    patient.setDateOfBirth(patientDetails.getDateOfBirth());
                    patient.setContactNumber(patientDetails.getContactNumber());
                    patient.setEmailAddress(patientDetails.getEmailAddress());
                    patient.setGender(patientDetails.getGender());
                    return repository.save(patient);
                })
                .orElseGet(() -> {
                    patientDetails.setId(id);
                    return repository.save(patientDetails);
                });
    }

    public void deletePatient(String id) {
        repository.deleteById(id);
    }
}
