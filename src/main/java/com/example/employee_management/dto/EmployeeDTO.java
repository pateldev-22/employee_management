package com.example.employee_management.dto;
import com.example.employee_management.validation.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @Null(groups = ValidationGroups.Create.class, message = "ID must be null for new employees")
    @NotNull(groups = ValidationGroups.Update.class, message = "ID is required for updates")
    private Long id;

    @NotBlank(message = "Name is required", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String fullName;

    @NotNull(message = "Email is required", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    private String profilePicturePath;
}

