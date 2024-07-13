package org.example.patientmacroservice.repository;

import org.example.patientmacroservice.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Repository extends MongoRepository<Patient, String> {
}
