package com.binhlaig.pos.restaurant.payment;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class RestaurantPaymentListResponse {

    private Long id;
    private String paymentNo;
    private String orderNo;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private BigDecimal total;
    private BigDecimal totalAmount;
    private Long shopId;
    private String shopCode;
}
