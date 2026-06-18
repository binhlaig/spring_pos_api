package com.binhlaig.pos.shopfeature.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopFeatureUpdateRequest {
    private Boolean dashboardEnabled;
    private Boolean productsEnabled;
    private Boolean posRegisterEnabled;
    private Boolean receiptsEnabled;
    private Boolean staffEnabled;
    private Boolean tasksEnabled;
    private Boolean timecardEnabled;
    private Boolean restaurantPosEnabled;
    private Boolean restaurantTablesEnabled;
    private Boolean restaurantKitchenEnabled;
    private Boolean restaurantOrdersEnabled;
    private Boolean allowRestaurant;
    private Boolean allowKitchen;
    private Boolean allowTableOrder;
    private Boolean settingsEnabled;
}
