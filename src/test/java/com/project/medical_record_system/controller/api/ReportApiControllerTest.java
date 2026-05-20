package com.project.medical_record_system.controller.api;

import com.project.medical_record_system.dto.DiagnosisCountDto;
import com.project.medical_record_system.dto.DoctorPaymentSumDto;
import com.project.medical_record_system.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReportApiControllerTest {

    @Mock
    private ReportService reportService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ReportApiController(this.reportService)).build();
    }

    @Test
    void getMostCommonDiagnosisReturnsOk() throws Exception {
        Mockito.when(this.reportService.getMostCommonDiagnosis())
                .thenReturn(new DiagnosisCountDto(1L, "Flu", 3L));

        this.mockMvc.perform(get("/api/reports/diagnoses/most-common"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosisName").value("Flu"))
                .andExpect(jsonPath("$.count").value(3L));
    }

    @Test
    void getTotalPaidByPatientsReturnsOk() throws Exception {
        Mockito.when(this.reportService.getTotalPaidByPatients()).thenReturn(BigDecimal.valueOf(100));

        this.mockMvc.perform(get("/api/reports/payments/patient-paid/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }

    @Test
    void getPatientPaidVisitsSumByDoctorReturnsOk() throws Exception {
        Mockito.when(this.reportService.getPatientPaidVisitsSumByDoctor())
                .thenReturn(List.of(new DoctorPaymentSumDto(1L, "Dr. Ivan Ivanov", BigDecimal.valueOf(80))));

        this.mockMvc.perform(get("/api/reports/payments/patient-paid/by-doctor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorName").value("Dr. Ivan Ivanov"));
    }
}
