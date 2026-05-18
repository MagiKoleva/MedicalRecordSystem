package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.SickLeave;
import com.project.medical_record_system.dto.DoctorSickLeaveCountDto;
import com.project.medical_record_system.dto.MonthSickLeaveCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SickLeaveRepository extends JpaRepository<SickLeave,Long> {

    // sick leaves by a given doctor
    List<SickLeave> findByVisitDoctorUserId (long doctorUserId);

    // number of sick leaves by a given doctor
    long countByVisitDoctorUserId (long doctorUserId);

    // month with the highest number of sick leaves
    @Query("""
            SELECT new com.project.medical_record_system.dto.MonthSickLeaveCountDto(
                MONTH(s.fromDate),
                COUNT(s)
            )
            FROM SickLeave s
            GROUP BY MONTH(s.fromDate)
            ORDER BY COUNT(s) DESC
            """)
    List<MonthSickLeaveCountDto> countSickLeavesByMonth();

    // doctor with the highest number of sick leaves given
    @Query("""
            SELECT new com.project.medical_record_system.dto.DoctorSickLeaveCountDto(
                s.visit.doctor.userId,
                s.visit.doctor.name,
                COUNT(s)
            )
            FROM SickLeave s
            GROUP BY s.visit.doctor.userId, s.visit.doctor.name
            ORDER BY COUNT(s) DESC
            """)
    List<DoctorSickLeaveCountDto> countSickLeavesByDoctor();
}
