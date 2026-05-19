package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Treatment;
import com.project.medical_record_system.service.TreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/treatments")
public class TreatmentApiController {

    private final TreatmentService treatmentService;

    @GetMapping
    public ResponseEntity<List<Treatment>> getAllTreatments() {
        return ResponseEntity.ok(treatmentService.getAllTreatments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable long id) {
        return ResponseEntity.ok(treatmentService.getTreatment(id));
    }

    @PostMapping
    public ResponseEntity<Treatment> createTreatment(@Valid @RequestBody Treatment treatment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(treatmentService.createTreatment(treatment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treatment> updateTreatment(@Valid @RequestBody Treatment treatment,
                                                     @PathVariable long id) {
        return ResponseEntity.ok(treatmentService.updateTreatment(treatment, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }
}
