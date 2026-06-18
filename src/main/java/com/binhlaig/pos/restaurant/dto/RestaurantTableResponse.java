package com.binhlaig.pos.restaurant.dto;

import com.binhlaig.pos.restaurant.entity.RestaurantTable;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RestaurantTableResponse {

    private Long id;
    private String tableNo;
    private String tableName;
    private Integer seats;
    private String status;
    private String floorName;
    private String note;
    private Long shopId;
    private String shopCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RestaurantTableResponse from(RestaurantTable table) {
        return RestaurantTableResponse.builder()
                .id(table.getId())
                .tableNo(table.getTableNo())
                .tableName(table.getTableName())
                .seats(table.getSeats())
                .status(table.getStatus() == null ? null : table.getStatus().name())
                .floorName(table.getFloorName())
                .note(table.getNote())
                .shopId(table.getShopId())
                .shopCode(table.getShopCode())
                .createdAt(table.getCreatedAt())
                .updatedAt(table.getUpdatedAt())
                .build();
    }
}
