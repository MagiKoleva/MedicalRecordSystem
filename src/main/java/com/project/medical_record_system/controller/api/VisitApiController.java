package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visits")
public class VisitApiController {

    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<Visit>> getAllVisits() {
        return ResponseEntity.ok(visitService.getAllVisits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable long id) {
        return ResponseEntity.ok(visitService.getVisit(id));
    }

    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestBody Visit visit) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(visitService.createVisit(visit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(@RequestBody Visit visit, @PathVariable long id) {
        return ResponseEntity.ok(visitService.updateVisit(visit, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }
}
