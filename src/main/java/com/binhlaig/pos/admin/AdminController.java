package com.binhlaig.pos.admin;

import com.binhlaig.pos.admin.dto.AdminDashboardStatsResponse;
import com.binhlaig.pos.admin.dto.AdminUserResponse;
import com.binhlaig.pos.auth.JwtService;
import com.binhlaig.pos.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/me")
    public AdminUserResponse me(HttpServletRequest request) {
        Long adminId = extractAdminId(request);
        AdminUser adminUser = adminUserRepository.findById(adminId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        if (!Boolean.TRUE.equals(adminUser.getActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin account disabled");
        }

        if (!"SUPER_ADMIN".equals(adminUser.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Super Admin");
        }

        return AdminUserResponse.builder()
                .id(adminUser.getId())
                .username(adminUser.getUsername())
                .email(adminUser.getEmail())
                .role(adminUser.getRole())
                .active(adminUser.getActive())
                .createdAt(adminUser.getCreatedAt())
                .lastLoginAt(adminUser.getLastLoginAt())
                .build();
    }

    @GetMapping("/dashboard/stats")
    public AdminDashboardStatsResponse dashboardStats() {
        long totalUsers = userRepository.count();

        return AdminDashboardStatsResponse.builder()
                .totalShops(totalUsers)
                .activeShops(totalUsers)
                .expiredShops(0)
                .suspendedShops(0)
                .totalUsers(totalUsers)
                .todaySales(BigDecimal.ZERO)
                .monthlyRevenue(BigDecimal.ZERO)
                .build();
    }

    private Long extractAdminId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Long adminId = jwtService.extractAdminId(authHeader.substring(7));
        if (adminId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Super Admin");
        }
        return adminId;
    }
}
