package com.project.medical_record_system.dto;

import com.project.medical_record_system.data.entity.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRegisterDto {

    @NotBlank(message = "Username must be provided!")
    private String username;

    @NotBlank(message = "Password must be provided!")
    private String password;

    @NotBlank(message = "Doctor name must be provided!")
    private String name;

    @NotNull
    private String doctorIdentifier;

    @NotNull
    private Boolean generalPractitioner;

    @NotNull
    private Long specialtyId;
}
