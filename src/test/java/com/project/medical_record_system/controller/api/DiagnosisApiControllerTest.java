package com.project.medical_record_system.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.medical_record_system.data.entity.Diagnosis;
import com.project.medical_record_system.service.DiagnosisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DiagnosisApiControllerTest {

    @Mock
    private DiagnosisService diagnosisService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DiagnosisApiController(this.diagnosisService)).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getAllDiagnosesReturnsOkAndDiagnoses() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Flu");

        Mockito.when(this.diagnosisService.getAllDiagnoses()).thenReturn(List.of(diagnosis));

        this.mockMvc.perform(get("/api/diagnoses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Flu"));
    }

    @Test
    void getDiagnosisByIdReturnsOkAndDiagnosis() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Flu");

        Mockito.when(this.diagnosisService.getDiagnosis(1L)).thenReturn(diagnosis);

        this.mockMvc.perform(get("/api/diagnoses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Flu"));
    }

    @Test
    void createDiagnosisReturnsCreated() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Flu");
        diagnosis.setDescription("Viral infection");

        Mockito.when(this.diagnosisService.createDiagnosis(any(Diagnosis.class))).thenReturn(diagnosis);

        this.mockMvc.perform(post("/api/diagnoses")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(diagnosis)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Flu"));
    }

    @Test
    void updateDiagnosisReturnsOk() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Updated flu");

        Mockito.when(this.diagnosisService.updateDiagnosis(any(Diagnosis.class), eq(1L))).thenReturn(diagnosis);

        this.mockMvc.perform(put("/api/diagnoses/1")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(diagnosis)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated flu"));
    }

    @Test
    void deleteDiagnosisReturnsNoContent() throws Exception {
        this.mockMvc.perform(delete("/api/diagnoses/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(this.diagnosisService).deleteDiagnosis(1L);
    }
}
