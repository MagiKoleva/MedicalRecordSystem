package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.SickLeave;
import com.project.medical_record_system.service.SickLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sick-leaves")
public class SickLeaveApiController {

    private final SickLeaveService sickLeaveService;

    @GetMapping
    public ResponseEntity<List<SickLeave>> getAllSickLeaves() {
        return ResponseEntity.ok(sickLeaveService.getAllSickLeaves());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SickLeave> getSickLeaveById(@PathVariable long id) {
        return ResponseEntity.ok(sickLeaveService.getSickLeave(id));
    }

    @PostMapping
    public ResponseEntity<SickLeave> createSickLeave(@RequestBody SickLeave sickLeave) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sickLeaveService.createSickLeave(sickLeave));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SickLeave> updateSickLeave(@RequestBody SickLeave sickLeave,
                                                     @PathVariable long id) {
        return ResponseEntity.ok(sickLeaveService.updateSickLeave(sickLeave, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSickLeave(@PathVariable long id) {
        sickLeaveService.deleteSickLeave(id);
        return ResponseEntity.noContent().build();
    }
}
