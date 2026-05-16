package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Doctor;
import com.project.medical_record_system.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorApiController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById (@PathVariable long id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor (@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.createDoctor(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor (@RequestBody Doctor doctor, @PathVariable long id) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctor, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor (@PathVariable long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
