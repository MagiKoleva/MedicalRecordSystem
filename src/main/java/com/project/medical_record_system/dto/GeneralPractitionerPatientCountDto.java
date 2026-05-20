package com.project.medical_record_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralPractitionerPatientCountDto {

    private long doctorId;
    private String doctorName;
    private long count;
}
