package com.binhlaig.pos.receiptsetting.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptSettingResponse {
    private Long id;

    private Long shopId;
    private String shopCode;

    private String shopName;
    private String address;
    private String phone;
    private String secondPhone;
    private String footerMessage;

    @Builder.Default
    private List<ReceiptAdResponse> ads = new ArrayList<>();
}