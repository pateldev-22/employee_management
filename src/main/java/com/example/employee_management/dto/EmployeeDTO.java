package com.example.employee_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String fullName;

    @NotNull(message = "Email is Required")
    @Email(message = "Invalid Email Format")
    private String email;


    private Double salary;

    private String profile_picture_path;

}
