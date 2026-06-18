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
public class MyShopResponse {
    private Long shopId;
    private String shopCode;
    private String shopName;
    private String address;
    private String businessType;
    private String status;
    private String subscriptionPlan;
    private LocalDate subscriptionEndDate;
}
