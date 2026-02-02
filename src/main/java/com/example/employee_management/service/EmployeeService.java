package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.entities.Employee;
import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.mapper.EmployeeMapper;
import com.example.employee_management.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeMapper employeeMapper){
        this.employeeRepository=employeeRepository;
        this.employeeMapper=employeeMapper;
    }

    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployee(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return employeeMapper.toDTO(employee);
    }

    public EmployeeDTO createEmployee(EmployeeDTO dto){

        Employee employee = employeeMapper.toEntity(dto);
        Employee created = employeeRepository.save(employee);
        return employeeMapper.toDTO(created);
    }

    public EmployeeDTO updateEmployee(Long id,EmployeeDTO dto){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
        employeeMapper.updateEntity(employee,dto);
        Employee update = employeeRepository.save(employee);
        return employeeMapper.toDTO(update);
    }

    public void deleteEmployeeById(Long id){
        if(!employeeRepository.existsById(id)){
            throw new ResourceNotFoundException("Employee not found with id : " + id);
        }
        employeeRepository.deleteById(id);
    }

}
