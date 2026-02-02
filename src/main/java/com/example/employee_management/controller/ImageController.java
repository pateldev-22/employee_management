package com.example.employee_management.controller;

import com.example.employee_management.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/images")
@Tag(name = "Image Management", description = "APIs for image upload and retrieval")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Upload employee profile image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String filename = imageService.saveImage(file);

        Map<String, String> response = new HashMap<>();
        response.put("filename", filename);
        response.put("message", "Image uploaded successfully");
        response.put("url", "/api/v1/images/" + filename);
        response.put("path", "uploads/images/" + filename);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{filename}")
    @Cacheable(value = "images", key = "#filename")
    @Operation(summary = "Retrieve employee profile image (cached)")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource resource = imageService.loadImage(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
