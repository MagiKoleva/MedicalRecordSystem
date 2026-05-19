package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.service.SpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/specialties")
public class SpecialtyApiController {

    private final SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> getAllSpecialties(){
        return  ResponseEntity.ok(specialtyService.getAllSpecialties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable long id) {
        return ResponseEntity.ok(specialtyService.getSpecialty(id));
    }

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@Valid @RequestBody Specialty specialty) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(specialtyService.createSpecialty(specialty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@Valid @RequestBody Specialty specialty,
                                                     @PathVariable long id) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(specialty, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }
}
