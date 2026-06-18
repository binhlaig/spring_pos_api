package com.binhlaig.pos.restaurant.order;

import com.binhlaig.pos.restaurant.payment.RestaurantOrder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RestaurantOpenOrderResponse {

    private Long orderId;
    private String orderNo;
    private String orderType;
    private Long tableId;
    private String tableNo;
    private String staffId;
    private String staffName;
    private BigDecimal subtotal;
    private BigDecimal serviceCharge;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String status;
    private Long shopId;
    private String shopCode;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RestaurantOpenOrderItemResponse> items;

    public static RestaurantOpenOrderResponse from(
            RestaurantOrder order,
            List<RestaurantOpenOrderItemResponse> items
    ) {
        return RestaurantOpenOrderResponse.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .orderType(order.getOrderType())
                .tableId(order.getTableId())
                .tableNo(order.getTableNo())
                .staffId(order.getStaffId())
                .staffName(order.getStaffName())
                .subtotal(order.getSubtotal())
                .serviceCharge(order.getServiceCharge())
                .tax(order.getTax())
                .discount(order.getDiscount())
                .total(order.getTotal())
                .status(order.getStatus())
                .shopId(order.getShopId())
                .shopCode(order.getShopCode())
                .note(order.getNote())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(items)
                .build();
    }
}
