package com.binhlaig.pos.shopfeature.dto;

import com.binhlaig.pos.shopfeature.ShopFeature;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopFeatureResponse {
    private Long id;
    private Long shopId;
    private String shopCode;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ShopFeatureResponse from(ShopFeature feature) {
        return ShopFeatureResponse.builder()
                .id(feature.getId())
                .shopId(feature.getShopId())
                .shopCode(feature.getShopCode())
                .dashboardEnabled(feature.getDashboardEnabled())
                .productsEnabled(feature.getProductsEnabled())
                .posRegisterEnabled(feature.getPosRegisterEnabled())
                .receiptsEnabled(feature.getReceiptsEnabled())
                .staffEnabled(feature.getStaffEnabled())
                .tasksEnabled(feature.getTasksEnabled())
                .timecardEnabled(feature.getTimecardEnabled())
                .restaurantPosEnabled(feature.getRestaurantPosEnabled())
                .restaurantTablesEnabled(feature.getRestaurantTablesEnabled())
                .restaurantKitchenEnabled(feature.getRestaurantKitchenEnabled())
                .restaurantOrdersEnabled(feature.getRestaurantOrdersEnabled())
                .allowRestaurant(feature.getAllowRestaurant())
                .allowKitchen(feature.getAllowKitchen())
                .allowTableOrder(feature.getAllowTableOrder())
                .settingsEnabled(feature.getSettingsEnabled())
                .createdAt(feature.getCreatedAt())
                .updatedAt(feature.getUpdatedAt())
                .build();
    }
}
