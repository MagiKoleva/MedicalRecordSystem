package com.project.medical_record_system.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.medical_record_system.data.entity.Specialty;
import com.project.medical_record_system.service.SpecialtyService;
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
class SpecialtyApiControllerTest {

    @Mock
    private SpecialtyService specialtyService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SpecialtyApiController(this.specialtyService)).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getAllSpecialtiesReturnsOk() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Cardiology");

        Mockito.when(this.specialtyService.getAllSpecialties()).thenReturn(List.of(specialty));

        this.mockMvc.perform(get("/api/specialties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cardiology"));
    }

    @Test
    void getSpecialtyByIdReturnsOk() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Cardiology");

        Mockito.when(this.specialtyService.getSpecialty(1L)).thenReturn(specialty);

        this.mockMvc.perform(get("/api/specialties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cardiology"));
    }

    @Test
    void createSpecialtyReturnsCreated() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Cardiology");

        Mockito.when(this.specialtyService.createSpecialty(any(Specialty.class))).thenReturn(specialty);

        this.mockMvc.perform(post("/api/specialties")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(specialty)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Cardiology"));
    }

    @Test
    void updateSpecialtyReturnsOk() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Neurology");

        Mockito.when(this.specialtyService.updateSpecialty(any(Specialty.class), eq(1L))).thenReturn(specialty);

        this.mockMvc.perform(put("/api/specialties/1")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(specialty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Neurology"));
    }

    @Test
    void deleteSpecialtyReturnsNoContent() throws Exception {
        this.mockMvc.perform(delete("/api/specialties/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(this.specialtyService).deleteSpecialty(1L);
    }
}
