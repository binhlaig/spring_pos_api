package com.binhlaig.pos.auth.dto;

import com.binhlaig.pos.admin.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanFeaturesDto {
    private Boolean allowRestaurant;
    private Boolean allowFashion;
    private Boolean allowAnalytics;
    private Boolean allowKitchen;
    private Boolean allowTableOrder;

    public static PlanFeaturesDto from(SubscriptionPlan plan) {
        return PlanFeaturesDto.builder()
                .allowRestaurant(Boolean.TRUE.equals(plan.getAllowRestaurant()))
                .allowFashion(Boolean.TRUE.equals(plan.getAllowFashion()))
                .allowAnalytics(Boolean.TRUE.equals(plan.getAllowAnalytics()))
                .allowKitchen(Boolean.TRUE.equals(plan.getAllowKitchen()))
                .allowTableOrder(Boolean.TRUE.equals(plan.getAllowTableOrder()))
                .build();
    }
}
