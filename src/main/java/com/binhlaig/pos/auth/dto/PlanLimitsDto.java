package com.binhlaig.pos.auth.dto;

import com.binhlaig.pos.admin.dto.EffectiveLimitsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanLimitsDto {
    private Integer maxStaff;
    private Integer maxProducts;
    private Integer maxReceiptsPerMonth;
    private Integer maxStorageMb;
    private Integer maxDevices;
    private Integer maxBranches;

    public static PlanLimitsDto from(EffectiveLimitsResponse limits) {
        return PlanLimitsDto.builder()
                .maxStaff(limits.maxStaff())
                .maxProducts(limits.maxProducts())
                .maxReceiptsPerMonth(limits.maxReceiptsPerMonth())
                .maxStorageMb(limits.maxStorageMb())
                .maxDevices(limits.maxDevices())
                .maxBranches(limits.maxBranches())
                .build();
    }
}
