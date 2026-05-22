

package com.binhlaig.pos.staff.controller;

import com.binhlaig.pos.staff.dto.StaffRequest;
import com.binhlaig.pos.staff.dto.StaffResponse;
import com.binhlaig.pos.staff.service.StaffService;
import com.binhlaig.pos.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffService staffService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<StaffResponse> createStaff(
            @RequestBody StaffRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        StaffResponse response = staffService.createStaff(request, authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StaffResponse> createStaffWithImage(
            @RequestPart("data") StaffRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IOException {

        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.saveStaffImage(file);
            request.setImageUrl(imageUrl);
        }

        StaffResponse response = staffService.createStaff(request, authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponse> updateStaff(
            @PathVariable Long id,
            @RequestBody StaffRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        StaffResponse response = staffService.updateStaff(id, request, authorizationHeader);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/with-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StaffResponse> updateStaffWithImage(
            @PathVariable Long id,
            @RequestPart("data") StaffRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IOException {

        StaffResponse existing = staffService.getStaffById(id, authorizationHeader);

        if (file != null && !file.isEmpty()) {
            if (existing.getImageUrl() != null && !existing.getImageUrl().isBlank()) {
                fileStorageService.deleteFile(existing.getImageUrl());
            }

            String imageUrl = fileStorageService.saveStaffImage(file);
            request.setImageUrl(imageUrl);
        } else {
            request.setImageUrl(existing.getImageUrl());
        }

        StaffResponse response = staffService.updateStaff(id, request, authorizationHeader);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getStaffById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        StaffResponse response = staffService.getStaffById(id, authorizationHeader);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-staff-id/{staffId}")
    public ResponseEntity<StaffResponse> getStaffByStaffId(
            @PathVariable Long staffId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        StaffResponse response = staffService.getStaffByStaffId(staffId, authorizationHeader);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StaffResponse>> getAllStaff(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        List<StaffResponse> response = staffService.getAllStaff(authorizationHeader);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IOException {

        StaffResponse existing = staffService.getStaffById(id, authorizationHeader);

        if (existing.getImageUrl() != null && !existing.getImageUrl().isBlank()) {
            fileStorageService.deleteFile(existing.getImageUrl());
        }

        staffService.deleteStaff(id, authorizationHeader);

        return ResponseEntity.ok("Staff deleted successfully");
    }
}