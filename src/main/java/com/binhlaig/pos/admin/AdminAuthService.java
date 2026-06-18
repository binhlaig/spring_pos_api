package com.binhlaig.pos.admin;

import com.binhlaig.pos.admin.dto.AdminLoginRequest;
import com.binhlaig.pos.admin.dto.AdminLoginResponse;
import com.binhlaig.pos.admin.dto.AdminRegisterRequest;
import com.binhlaig.pos.auth.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAuthService {

    private static final String SUPER_ADMIN = "SUPER_ADMIN";

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AdminLoginResponse login(AdminLoginRequest request) {
        String username = normalize(request.username());
        String password = request.password() == null ? "" : request.password();

        AdminUser adminUser = adminUserRepository.findByUsername(username)
                .orElseThrow(() -> unauthorized("Invalid username or password"));

        if (!Boolean.TRUE.equals(adminUser.getActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin account disabled");
        }

        if (!passwordEncoder.matches(password, adminUser.getPasswordHash())) {
            throw unauthorized("Invalid username or password");
        }

        if (!SUPER_ADMIN.equals(adminUser.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Super Admin");
        }

        adminUser.setLastLoginAt(OffsetDateTime.now());
        return toLoginResponse(adminUserRepository.save(adminUser));
    }

    @Transactional
    public AdminLoginResponse register(AdminRegisterRequest request) {
        String username = normalize(request.username());
        String email = normalizeNullable(request.email());
        String password = request.password() == null ? "" : request.password();

        if (adminUserRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (email != null && adminUserRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        AdminUser adminUser = AdminUser.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(SUPER_ADMIN)
                .active(true)
                .lastLoginAt(OffsetDateTime.now())
                .build();

        return toLoginResponse(adminUserRepository.save(adminUser));
    }

    private AdminLoginResponse toLoginResponse(AdminUser adminUser) {
        String token = jwtService.generateAdminToken(adminUser.getId(), adminUser.getUsername(), adminUser.getRole());

        return AdminLoginResponse.builder()
                .id(adminUser.getId())
                .username(adminUser.getUsername())
                .email(adminUser.getEmail())
                .role(adminUser.getRole())
                .accessToken(token)
                .build();
    }

    private ResponseStatusException unauthorized(String message) {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private String normalizeNullable(String value) {
        String normalized = normalize(value);
        return normalized.isBlank() ? null : normalized;
    }

}
