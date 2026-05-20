package com.project.medical_record_system.controller.view;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SpecialtyViewControllerTest {

    @Mock
    private SpecialtyService specialtyService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SpecialtyViewController(this.specialtyService)).build();
    }

    @Test
    void getSpecialtiesReturnsSpecialtiesView() throws Exception {
        Mockito.when(this.specialtyService.getAllSpecialties()).thenReturn(List.of(new Specialty()));

        this.mockMvc.perform(get("/web/specialties"))
                .andExpect(status().isOk())
                .andExpect(view().name("specialties"))
                .andExpect(model().attributeExists("specialties"));
    }

    @Test
    void showCreateFormReturnsSpecialtyForm() throws Exception {
        this.mockMvc.perform(get("/web/specialties/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("specialty-form"))
                .andExpect(model().attributeExists("specialty"));
    }

    @Test
    void createSpecialtyRedirectsToSpecialties() throws Exception {
        this.mockMvc.perform(post("/web/specialties/create")
                        .param("name", "Cardiology")
                        .param("description", "Heart diseases"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/specialties"));

        Mockito.verify(this.specialtyService).createSpecialty(any(Specialty.class));
    }

    @Test
    void showEditFormReturnsSpecialtyForm() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Cardiology");

        Mockito.when(this.specialtyService.getSpecialty(1L)).thenReturn(specialty);

        this.mockMvc.perform(get("/web/specialties/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("specialty-form"))
                .andExpect(model().attributeExists("specialty"));
    }

    @Test
    void updateSpecialtyRedirectsToSpecialties() throws Exception {
        this.mockMvc.perform(post("/web/specialties/edit/1")
                        .param("name", "Neurology")
                        .param("description", "Nervous system"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/specialties"));

        Mockito.verify(this.specialtyService).updateSpecialty(any(Specialty.class), eq(1L));
    }

    @Test
    void deleteSpecialtyRedirectsToSpecialties() throws Exception {
        this.mockMvc.perform(get("/web/specialties/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/specialties"));

        Mockito.verify(this.specialtyService).deleteSpecialty(1L);
    }
}
