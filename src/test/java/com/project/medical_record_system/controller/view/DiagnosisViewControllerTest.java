package com.project.medical_record_system.controller.view;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DiagnosisViewControllerTest {

    @Mock
    private DiagnosisService diagnosisService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DiagnosisViewController(this.diagnosisService)).build();
    }

    @Test
    void getDiagnosesReturnsDiagnosesView() throws Exception {
        Mockito.when(this.diagnosisService.getAllDiagnoses()).thenReturn(List.of(new Diagnosis()));

        this.mockMvc.perform(get("/web/diagnoses"))
                .andExpect(status().isOk())
                .andExpect(view().name("diagnoses"))
                .andExpect(model().attributeExists("diagnoses"));
    }

    @Test
    void showCreateFormReturnsDiagnosisForm() throws Exception {
        this.mockMvc.perform(get("/web/diagnoses/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("diagnosis-form"))
                .andExpect(model().attributeExists("diagnosis"));
    }

    @Test
    void createDiagnosisRedirectsToDiagnoses() throws Exception {
        this.mockMvc.perform(post("/web/diagnoses/create")
                        .param("name", "Flu")
                        .param("description", "Viral infection"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/diagnoses"));

        Mockito.verify(this.diagnosisService).createDiagnosis(any(Diagnosis.class));
    }

    @Test
    void showEditFormReturnsDiagnosisForm() throws Exception {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Flu");

        Mockito.when(this.diagnosisService.getDiagnosis(1L)).thenReturn(diagnosis);

        this.mockMvc.perform(get("/web/diagnoses/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("diagnosis-form"))
                .andExpect(model().attributeExists("diagnosis"));
    }

    @Test
    void updateDiagnosisRedirectsToDiagnoses() throws Exception {
        this.mockMvc.perform(post("/web/diagnoses/edit/1")
                        .param("name", "Updated Flu")
                        .param("description", "Updated description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/diagnoses"));

        Mockito.verify(this.diagnosisService).updateDiagnosis(any(Diagnosis.class), eq(1L));
    }

    @Test
    void deleteDiagnosisRedirectsToDiagnoses() throws Exception {
        this.mockMvc.perform(get("/web/diagnoses/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/diagnoses"));

        Mockito.verify(this.diagnosisService).deleteDiagnosis(1L);
    }
}
