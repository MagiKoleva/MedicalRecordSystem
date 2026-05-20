//package com.project.medical_record_system.data.repository;
//
//import com.project.medical_record_system.data.entity.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//class VisitRepositoryTest {
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private VisitRepository visitRepository;
//
//    @Test
//    void findByDoctorUserIdReturnsOnlyDoctorVisits() {
//        TestData data = createBaseData();
//
//        Visit firstVisit = createVisit(data.patient(), data.doctor(), data.diagnosis(), LocalDate.now().plusDays(1), PaidBy.NZOK);
//        this.testEntityManager.persistAndFlush(firstVisit);
//
//        assertThat(this.visitRepository.findByDoctorUserId(data.doctor().getUserId()))
//                .hasSize(1)
//                .first()
//                .extracting(Visit::getDoctor)
//                .isEqualTo(data.doctor());
//    }
//
//    @Test
//    void findByPatientUserIdReturnsPatientHistory() {
//        TestData data = createBaseData();
//
//        Visit firstVisit = createVisit(data.patient(), data.doctor(), data.diagnosis(), LocalDate.now().plusDays(1), PaidBy.NZOK);
//        Visit secondVisit = createVisit(data.patient(), data.doctor(), data.diagnosis(), LocalDate.now().plusDays(2), PaidBy.NZOK);
//
//        this.testEntityManager.persist(firstVisit);
//        this.testEntityManager.persistAndFlush(secondVisit);
//
//        assertThat(this.visitRepository.findByPatientUserId(data.patient().getUserId())).hasSize(2);
//    }
//
//    @Test
//    void findByDateBetweenReturnsVisitsInPeriod() {
//        TestData data = createBaseData();
//
//        Visit mayVisit = createVisit(data.patient(), data.doctor(), data.diagnosis(), LocalDate.now().plusDays(5), PaidBy.NZOK);
//        this.testEntityManager.persistAndFlush(mayVisit);
//
//        assertThat(this.visitRepository.findByDateBetween(LocalDate.now(), LocalDate.now().plusDays(10)))
//                .hasSize(1);
//    }
//
//    private TestData createBaseData() {
//        Role doctorRole = new Role();
//        doctorRole.setName(RoleName.DOCTOR);
//        this.testEntityManager.persist(doctorRole);
//
//        Role patientRole = new Role();
//        patientRole.setName(RoleName.PATIENT);
//        this.testEntityManager.persist(patientRole);
//
//        User doctorUser = new User();
//        doctorUser.setUsername("doctor_repo_test");
//        doctorUser.setPassword("password");
//        doctorUser.setRole(doctorRole);
//        this.testEntityManager.persist(doctorUser);
//
//        User patientUser = new User();
//        patientUser.setUsername("patient_repo_test");
//        patientUser.setPassword("password");
//        patientUser.setRole(patientRole);
//        this.testEntityManager.persist(patientUser);
//
//        Doctor doctor = new Doctor();
//        doctor.setUser(doctorUser);
//        doctor.setUserId(doctorUser.getId());
//        doctor.setName("Dr. Ivan Ivanov");
//        doctor.setDoctorIdentifier("DOC-REPO-1");
//        doctor.setGeneralPractitioner(true);
//        this.testEntityManager.persist(doctor);
//
//        Patient patient = new Patient();
//        patient.setUser(patientUser);
//        patient.setUserId(patientUser.getId());
//        patient.setName("Maria Petrova");
//        patient.setEgn("1234567890");
//        patient.setHealthInsurancePaid(true);
//        patient.setGeneralPractitioner(doctor);
//        this.testEntityManager.persist(patient);
//
//        Diagnosis diagnosis = new Diagnosis();
//        diagnosis.setName("Flu");
//        diagnosis.setDescription("Viral infection");
//        this.testEntityManager.persistAndFlush(diagnosis);
//
//        return new TestData(doctor, patient, diagnosis);
//    }
//
//    private Visit createVisit(Patient patient, Doctor doctor, Diagnosis diagnosis, LocalDate date, PaidBy paidBy) {
//        Visit visit = new Visit();
//        visit.setDate(date);
//        visit.setPrice(BigDecimal.valueOf(50));
//        visit.setPaidBy(paidBy);
//        visit.setPatient(patient);
//        visit.setDoctor(doctor);
//        visit.setDiagnosis(diagnosis);
//        visit.setNotes("Repository test visit");
//        return visit;
//    }
//
//    private record TestData(Doctor doctor, Patient patient, Diagnosis diagnosis) {
//    }
//}
