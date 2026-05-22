package com.binhlaig.pos.receiptsetting.controller;

import com.binhlaig.pos.receiptsetting.dto.ReceiptSettingRequest;
import com.binhlaig.pos.receiptsetting.dto.ReceiptSettingResponse;
import com.binhlaig.pos.receiptsetting.service.ReceiptSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipt-settings")
@RequiredArgsConstructor
public class ReceiptSettingController {

    private final ReceiptSettingService receiptSettingService;

    @GetMapping("/my-shop")
    public ReceiptSettingResponse getMyShopSetting(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return receiptSettingService.getMyShopSettingByToken(authorizationHeader);
    }

    @PutMapping("/my-shop")
    public ReceiptSettingResponse saveMyShopSetting(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ReceiptSettingRequest request
    ) {
        return receiptSettingService.saveMyShopSettingByToken(
                authorizationHeader,
                request
        );
    }
}