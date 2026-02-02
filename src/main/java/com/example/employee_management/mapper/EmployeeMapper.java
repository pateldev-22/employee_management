package com.example.employee_management.mapper;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.entities.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeDTO dto){
        if(dto == null) return null;

        Employee employee = new Employee();

        employee.setId(dto.getId());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setFullName(dto.getFullName());
        employee.setProfilePicturePath(dto.getProfile_picture_path());

        return employee;
    }
    public EmployeeDTO toDTO(Employee employee){
        if(employee == null) return null;

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setProfile_picture_path(employee.getProfilePicturePath());

        return employeeDTO;
    }

    public void updateEntity(Employee employee,EmployeeDTO dto){
        if(dto == null) return;

        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setProfilePicturePath(dto.getProfile_picture_path());
        employee.setSalary(dto.getSalary());
    }
}
