package com.binhlaig.pos.me.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeProfileResponse {
    private Long id;
    private String username;
    private String role;
    private Boolean active;
    private String imageUrl;
    private String avatarPath;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private String address;
    private String businessType;
}
