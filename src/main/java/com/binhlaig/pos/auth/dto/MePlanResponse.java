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
public class MePlanResponse {
    private Long shopId;
    private String shopCode;
    private String businessType;
    private String shopStatus;
    private String subscriptionPlan;
    private LocalDate subscriptionEndDate;
    private PlanFeaturesDto features;
    private PlanLimitsDto limits;
    private PlanUsageDto usage;
}
