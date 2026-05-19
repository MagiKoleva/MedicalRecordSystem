package com.project.medical_record_system.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "treatment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Treatment extends BaseEntity {

    @NotBlank(message = "Specify medication or write 'nothing'")
    private String prescribedMedication;

    @Size(max = 255)
    private String description;

    @NotNull(message = "Start date of the treatment must be specified!")
    @FutureOrPresent(message = "Start date of treatment cannot be in the past!")
    private LocalDate startDate;

    @NotNull(message = "End date of the treatment must be specified!")
    @FutureOrPresent(message = "End date of treatment cannot be in the past!")
    private LocalDate endDate;

    //@JsonBackReference
    @OneToOne(optional = false)
    @JoinColumn(name = "visit_id", unique = true)
    private Visit visit;
}
