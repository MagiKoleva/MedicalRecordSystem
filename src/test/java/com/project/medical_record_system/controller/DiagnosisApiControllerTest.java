//package com.project.medical_record_system.controller;
//
//import com.project.medical_record_system.controller.api.DiagnosisApiController;
//import com.project.medical_record_system.data.entity.Diagnosis;
//import com.project.medical_record_system.service.DiagnosisService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(DiagnosisApiController.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class DiagnosisApiControllerTest {
//
//    @MockBean
//    private DiagnosisService diagnosisService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void getDiagnosisByIdTest() throws Exception {
//        long diagnosisId = 1L;
//
//        Diagnosis diagnosis = new Diagnosis();
//        diagnosis.setId(diagnosisId);
//        diagnosis.setName("Flu");
//        diagnosis.setDescription("Viral infection");
//
//        when(this.diagnosisService.getDiagnosis(diagnosisId))
//                .thenReturn(diagnosis);
//
//        this.mockMvc.perform(get("/api/diagnoses/" + diagnosisId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(diagnosisId))
//                .andExpect(jsonPath("$.name").value("Flu"));
//    }
//}