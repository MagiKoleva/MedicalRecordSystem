package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Diagnosis;
import com.project.medical_record_system.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/diagnoses")
public class DiagnosisApiController {

    private final DiagnosisService diagnosisService;

    @GetMapping
    public ResponseEntity<List<Diagnosis>> getAllDiagnoses() {
        return ResponseEntity.ok(diagnosisService.getAllDiagnoses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosisById(@PathVariable long id) {
        return ResponseEntity.ok(diagnosisService.getDiagnosis(id));
    }

    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(@Valid @RequestBody Diagnosis diagnosis) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diagnosisService.createDiagnosis(diagnosis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diagnosis> updateDiagnosis(@Valid @RequestBody Diagnosis diagnosis,
                                                     @PathVariable long id) {
        return ResponseEntity.ok(this.diagnosisService.updateDiagnosis(diagnosis, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable long id) {
        diagnosisService.deleteDiagnosis(id);
        return ResponseEntity.noContent().build();
    }
}
