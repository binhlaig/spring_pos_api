package com.binhlaig.pos.me.dto;

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
}
