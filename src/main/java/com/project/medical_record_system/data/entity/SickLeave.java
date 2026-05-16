package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sick_leave")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SickLeave extends BaseEntity {

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private int days;

    //@JsonBackReference
    @OneToOne(optional = false)
    @JoinColumn(name = "visit_id", unique = true)
    private Visit visit;
}
