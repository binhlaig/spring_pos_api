

package com.binhlaig.pos.staff.service;

import com.binhlaig.pos.auth.JwtService;
import com.binhlaig.pos.staff.dto.StaffRequest;
import com.binhlaig.pos.staff.dto.StaffResponse;
import com.binhlaig.pos.staff.entity.Staff;
import com.binhlaig.pos.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private Long extractShopId(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new RuntimeException("Authorization header is missing");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7).trim();

        Long shopId = jwtService.extractShopId(token);
        if (shopId == null) {
            throw new RuntimeException("Shop ID not found in token");
        }

        return shopId;
    }

    private String extractShopCode(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new RuntimeException("Authorization header is missing");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7).trim();

        String shopCode = jwtService.extractShopCode(token);
        if (shopCode == null || shopCode.isBlank()) {
            throw new RuntimeException("Shop code not found in token");
        }

        return shopCode;
    }

    @Override
    public StaffResponse createStaff(StaffRequest request, String authorizationHeader) {
        Long shopId = extractShopId(authorizationHeader);
        String shopCode = extractShopCode(authorizationHeader);

        if (request.getEmail() != null &&
                staffRepository.existsByEmailAndShopId(request.getEmail(), shopId)) {
            throw new RuntimeException("Email already exists in this shop");
        }

        if (request.getStaffId() != null &&
                staffRepository.existsByStaffIdAndShopId(request.getStaffId(), shopId)) {
            throw new RuntimeException("Staff ID already exists in this shop");
        }

        Staff staff = Staff.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .nrc(request.getNrc())
                .staffId(request.getStaffId())
                .password(
                        request.getPassword() != null && !request.getPassword().isBlank()
                                ? passwordEncoder.encode(request.getPassword())
                                : null
                )
                .dateOfBirth(request.getDateOfBirth())
                .shopId(shopId)
                .shopCode(shopCode)
                .role(request.getRole())
                .branch(request.getBranch())
                .status(request.getStatus())
                .startDate(request.getStartDate())
                .salary(request.getSalary())
                .address(request.getAddress())
                .emergencyContact(request.getEmergencyContact())
                .emergencyPhone(request.getEmergencyPhone())
                .note(request.getNote())
                .imageUrl(request.getImageUrl())
                .build();

        Staff saved = staffRepository.save(staff);
        return mapToResponse(saved);
    }

    @Override
    public StaffResponse updateStaff(Long id, StaffRequest request, String authorizationHeader) {
        Long shopId = extractShopId(authorizationHeader);
        String shopCode = extractShopCode(authorizationHeader);

        Staff staff = staffRepository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (request.getEmail() != null &&
                !request.getEmail().equals(staff.getEmail()) &&
                staffRepository.existsByEmailAndShopId(request.getEmail(), shopId)) {
            throw new RuntimeException("Email already used by another staff in this shop");
        }

        if (request.getStaffId() != null &&
                !request.getStaffId().equals(staff.getStaffId()) &&
                staffRepository.existsByStaffIdAndShopId(request.getStaffId(), shopId)) {
            throw new RuntimeException("Staff ID already used by another staff in this shop");
        }

        staff.setFullName(request.getFullName());
        staff.setEmail(request.getEmail());
        staff.setPhone(request.getPhone());
        staff.setNrc(request.getNrc());
        staff.setStaffId(request.getStaffId());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            staff.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        staff.setDateOfBirth(request.getDateOfBirth());
        staff.setShopId(shopId);
        staff.setShopCode(shopCode);
        staff.setRole(request.getRole());
        staff.setBranch(request.getBranch());
        staff.setStatus(request.getStatus());
        staff.setStartDate(request.getStartDate());
        staff.setSalary(request.getSalary());
        staff.setAddress(request.getAddress());
        staff.setEmergencyContact(request.getEmergencyContact());
        staff.setEmergencyPhone(request.getEmergencyPhone());
        staff.setNote(request.getNote());

        if (request.getImageUrl() != null) {
            staff.setImageUrl(request.getImageUrl());
        }

        Staff updated = staffRepository.save(staff);
        return mapToResponse(updated);
    }

    @Override
    public StaffResponse getStaffById(Long id, String authorizationHeader) {
        Long shopId = extractShopId(authorizationHeader);

        Staff staff = staffRepository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        return mapToResponse(staff);
    }

    @Override
    public StaffResponse getStaffByStaffId(Long staffId, String authorizationHeader) {
        Long shopId = extractShopId(authorizationHeader);

        Staff staff = staffRepository.findByStaffIdAndShopId(staffId, shopId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        return mapToResponse(staff);
    }

    @Override
    public List<StaffResponse> getAllStaff(String authorizationHeader) {
        Long shopId = extractShopId(authorizationHeader);

        return staffRepository.findByShopId(shopId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteStaff(Long id, String authorizationHeader) {
        Long shopId = extractShopId(authorizationHeader);

        Staff staff = staffRepository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staffRepository.delete(staff);
    }

    private StaffResponse mapToResponse(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .fullName(staff.getFullName())
                .email(staff.getEmail())
                .phone(staff.getPhone())
                .nrc(staff.getNrc())
                .staffId(staff.getStaffId())
                .dateOfBirth(staff.getDateOfBirth())
                .shopId(staff.getShopId())
                .shopCode(staff.getShopCode())
                .role(staff.getRole())
                .branch(staff.getBranch())
                .status(staff.getStatus())
                .startDate(staff.getStartDate())
                .salary(staff.getSalary())
                .address(staff.getAddress())
                .emergencyContact(staff.getEmergencyContact())
                .emergencyPhone(staff.getEmergencyPhone())
                .note(staff.getNote())
                .imageUrl(staff.getImageUrl())
                .build();
    }
}