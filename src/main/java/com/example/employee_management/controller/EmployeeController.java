package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.service.EmployeeService;
import com.example.employee_management.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Validated(ValidationGroups.Create.class) @RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,@Validated(ValidationGroups.Update.class) @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.updateEmployee(id,employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<EmployeeDTO> patchEmployee(@RequestBody Map<String, Object> updates) {
//        if (updates.size() != 1) {
//            throw new IllegalArgumentException("Only one field can be updated at a time");
//        }
//        Map.Entry<String, Object> entry = updates.entrySet().iterator().next();
//        String field = entry.getKey();
//        Object value = entry.getValue();
//        return ResponseEntity.ok(employeeService.patchEmployee(id, field, value));
//    }

    @GetMapping("/cookie")
    public ResponseEntity<Map<String, String>> setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("session_id", "abc123xyz456");
        cookie.setMaxAge(3600); // 1 hour
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Cookie has been set successfully");
        result.put("cookieName", "session_id");
        result.put("cookieValue", "abc123xyz456");
        return ResponseEntity.ok(result);
    }



    //Header-based Versioning

    @GetMapping(value = "/test-header-version", headers = "X-API-VERSION=2")
    public ResponseEntity<Map<String, String>> testHeaderVersioning() {

        Map<String, String> response = new HashMap<>();

        response.put("version", "2.0");

        response.put("type", "Header-based versioning");

        response.put("message", "This endpoint responds only when X-API-VERSION=2 header is present");

        return ResponseEntity.ok(response);

    }



    //Parameter-based Versioning

    @GetMapping(value = "/test-param-version", params = "version=3")
    public ResponseEntity<Map<String, String>> testParamVersioning() {

        Map<String, String> response = new HashMap<>();

        response.put("version", "3.0");

        response.put("type", "Parameter-based versioning");

        response.put("message", "This endpoint responds only when version=3 query parameter is present");

        return ResponseEntity.ok(response);

    }



    // Example 4: Accept Header (Content Negotiation) Versioning

    @GetMapping(value = "/test-accept-version", produces = "application/devpatel.v4+json")
    public ResponseEntity<Map<String, String>> testAcceptVersioning() {

        Map<String, String> response = new HashMap<>();

        response.put("version", "4.0");

        response.put("type", "Accept header versioning");

        response.put("message", "This endpoint responds only when Accept header is application/[vnd.company](http://vnd.company).v4+json");

        return ResponseEntity.ok(response);

    }
}
