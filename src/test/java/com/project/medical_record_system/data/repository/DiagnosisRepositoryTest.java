//package com.project.medical_record_system.data.repository;
//
//import com.project.medical_record_system.data.entity.Diagnosis;
//import com.project.medical_record_system.data.repository.DiagnosisRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//class DiagnosisRepositoryTest {
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private DiagnosisRepository diagnosisRepository;
//
//    @Test
//    void findAllReturnsPersistedDiagnoses() {
//        Diagnosis diagnosis = new Diagnosis();
//        diagnosis.setName("Flu");
//        diagnosis.setDescription("Viral infection");
//
//        this.testEntityManager.persistAndFlush(diagnosis);
//
//        assertThat(this.diagnosisRepository.findAll()).hasSize(1);
//    }
//
//    @Test
//    void findByIdReturnsPersistedDiagnosis() {
//        Diagnosis diagnosis = new Diagnosis();
//        diagnosis.setName("Hypertension");
//        diagnosis.setDescription("High blood pressure");
//
//        Diagnosis saved = this.testEntityManager.persistAndFlush(diagnosis);
//
//        assertThat(this.diagnosisRepository.findById(saved.getId())).isPresent();
//        assertThat(this.diagnosisRepository.findById(saved.getId()).get().getName())
//                .isEqualTo("Hypertension");
//    }
//}
