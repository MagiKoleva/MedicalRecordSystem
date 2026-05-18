package com.project.medical_record_system.data.repository;

import com.project.medical_record_system.data.entity.PaidBy;
import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.dto.DiagnosisCountDto;
import com.project.medical_record_system.dto.DoctorPaymentSumDto;
import com.project.medical_record_system.dto.DoctorVisitCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit,Long> {

    // patients with given diagnosis
    List<Visit> findByDiagnosisId (long diagnosisId);

    // history of visits of a patient
    List<Visit> findByPatientUserId (long patientUserId);

    // visits by given doctor
    List<Visit> findByDoctorUserId (long doctorUserId);

    // visits during given period
    List<Visit> findByDateBetween(LocalDate start, LocalDate end);

    // visits by given doctor during given period
    List<Visit> findByDoctorUserIdAndDateBetween(long doctorUserId, LocalDate start, LocalDate end);

    // visits paid by a patient (or not)
    List<Visit> findByPaidBy(PaidBy paidBy);

    // most common diagnosis
    @Query("""
            SELECT new com.project.medical_record_system.dto.DiagnosisCountDto(
                v.diagnosis.id,
                v.diagnosis.name,
                COUNT(v)
            )
            FROM Visit v
            GROUP BY v.diagnosis.id, v.diagnosis.name
            ORDER BY COUNT(v) DESC
            """)
    List<DiagnosisCountDto> findMostCommonDiagnoses();

    // total sum of the visits paid by a patient
    @Query("""
            SELECT SUM(v.price)
            FROM Visit v 
            WHERE v.paidBy = 'PATIENT'            
            """)
    BigDecimal sumPatientPaidVisits();

    // total sum of the visits paid by a patient by given doctor
    @Query("""
            SELECT new com.project.medical_record_system.dto.DoctorPaymentSumDto(
                v.doctor.userId,
                v.doctor.name,
                SUM(v.price)
            )
            FROM Visit v
            WHERE v.paidBy = 'PATIENT'
            GROUP BY v.doctor.userId, v.doctor.name
            """)
    List<DoctorPaymentSumDto> sumPatientPaidVisitsByDoctor();

    // number of visits of a given doctor
    @Query("""
            SELECT new com.project.medical_record_system.dto.DoctorVisitCountDto(
                v.doctor.userId,
                v.doctor.name,
                COUNT(v)
            )
            FROM Visit v
            GROUP BY v.doctor.userId, v.doctor.name
            """)
    List<DoctorVisitCountDto> countVisitsByDoctor();
}
