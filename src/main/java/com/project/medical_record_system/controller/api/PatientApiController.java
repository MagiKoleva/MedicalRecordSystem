package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Patient;
import com.project.medical_record_system.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientApiController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient, @PathVariable long id) {
        return ResponseEntity.ok(patientService.updatePatient(patient, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
