package com.project.medical_record_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRegisterDto {

    @NotBlank(message = "Username must be provided!")
    private String username;

    @NotBlank(message = "Password must be provided!")
    private String password;

    @NotBlank(message = "Patient name must be provided!")
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "EGN must contain exactly 10 digits.")
    private String egn;

    @NotNull
    private Boolean healthInsurancePaid;

    @NotBlank(message = "General practitioner must be given!")
    private String generalPractitionerName;
}
