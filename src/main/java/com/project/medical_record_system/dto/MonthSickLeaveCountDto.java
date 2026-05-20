package com.project.medical_record_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthSickLeaveCountDto {

    Integer month;
    long count;

    public String getMonthName() {
        return java.time.Month.of(this.month)
                .getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
    }
}
