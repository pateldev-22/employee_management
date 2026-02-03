package com.example.employee_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Dummy token authentication")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Login to get dummy token")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username != null && password != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", "dummy-token-12345");
            response.put("type", "Bearer");
            response.put("username", username);
            response.put("message", "Login successful! Use this token in all requests: Bearer dummy-token-12345");
            return ResponseEntity.ok(response);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "dummy-user",
                null,
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(401).body(Map.of("error", "Username and password required"));
    }
}
