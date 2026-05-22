package com.binhlaig.pos.receiptsetting.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptSettingRequest {
    private String shopName;
    private String address;
    private String phone;
    private String secondPhone;
    private String footerMessage;

    @Builder.Default
    private List<ReceiptAdRequest> ads = new ArrayList<>();
}