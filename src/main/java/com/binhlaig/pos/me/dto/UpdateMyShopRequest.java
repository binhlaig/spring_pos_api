package com.binhlaig.pos.me.dto;

import lombok.Data;

@Data
public class UpdateMyShopRequest {
    private String shopName;
    private String address;
    private String phone;
}
