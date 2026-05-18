package com.project.medical_record_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPaymentSumDto {

    long doctorId;
    String doctorName;
    BigDecimal totalSum;
}
