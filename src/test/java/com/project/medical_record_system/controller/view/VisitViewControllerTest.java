package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.Visit;
import com.project.medical_record_system.service.DiagnosisService;
import com.project.medical_record_system.service.DoctorService;
import com.project.medical_record_system.service.PatientService;
import com.project.medical_record_system.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitViewControllerTest {

    @Mock
    private VisitService visitService;

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private DiagnosisService diagnosisService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new VisitViewController(this.visitService, this.patientService, this.doctorService, this.diagnosisService)
        ).build();
    }

    @Test
    void getVisitsReturnsVisitsView() throws Exception {
        Mockito.when(this.visitService.getAllVisits()).thenReturn(List.of(new Visit()));
        Mockito.when(this.doctorService.getAllDoctors()).thenReturn(List.of());

        this.mockMvc.perform(get("/web/visits"))
                .andExpect(status().isOk())
                .andExpect(view().name("visits"))
                .andExpect(model().attributeExists("visits"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    void showCreateVisitFormReturnsVisitForm() throws Exception {
        Mockito.when(this.patientService.getAllPatients()).thenReturn(List.of());
        Mockito.when(this.doctorService.getAllDoctors()).thenReturn(List.of());
        Mockito.when(this.diagnosisService.getAllDiagnoses()).thenReturn(List.of());

        this.mockMvc.perform(get("/web/visits/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("visit-form"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("diagnoses"));
    }

    @Test
    void filterVisitsByDoctorAndPeriodReturnsVisitsView() throws Exception {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(10);

        Mockito.when(this.visitService.getVisitsByDoctorAndPeriod(1L, start, end)).thenReturn(List.of(new Visit()));
        Mockito.when(this.doctorService.getAllDoctors()).thenReturn(List.of());

        this.mockMvc.perform(get("/web/visits/filter")
                        .param("doctorId", "1")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("visits"))
                .andExpect(model().attributeExists("visits"));
    }
}
