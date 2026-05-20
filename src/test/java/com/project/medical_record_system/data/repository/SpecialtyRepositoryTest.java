//package com.project.medical_record_system.data.repository;
//
//import com.project.medical_record_system.data.entity.Specialty;
//import com.project.medical_record_system.data.repository.SpecialtyRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//class SpecialtyRepositoryTest {
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private SpecialtyRepository specialtyRepository;
//
//    @Test
//    void findAllReturnsPersistedSpecialties() {
//        Specialty specialty = new Specialty();
//        specialty.setName("Cardiology");
//        specialty.setDescription("Heart diseases");
//
//        this.testEntityManager.persistAndFlush(specialty);
//
//        assertThat(this.specialtyRepository.findAll()).hasSize(1);
//        assertThat(this.specialtyRepository.findAll().getFirst().getName()).isEqualTo("Cardiology");
//    }
//}
