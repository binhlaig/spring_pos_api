package com.binhlaig.pos.me.dto;

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
}
