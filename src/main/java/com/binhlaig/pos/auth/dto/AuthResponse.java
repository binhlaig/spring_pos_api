package com.binhlaig.pos.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String tokenType;

    private String username;
    private String role;

    private Long shopId;
    private String shopCode;
    private String businessType;

    // ✅ ADD THIS (important for staff)
    private Long staffId;

    private String imageUrl;

    private String shopStatus;
    private String subscriptionPlan;
    private LocalDate subscriptionEndDate;
    private PlanFeaturesDto features;
    private PlanLimitsDto limits;
}
