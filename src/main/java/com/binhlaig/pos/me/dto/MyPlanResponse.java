package com.binhlaig.pos.me.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPlanResponse {
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
