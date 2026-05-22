package com.binhlaig.pos.receiptsetting.service;

import com.binhlaig.pos.receiptsetting.dto.ReceiptAdRequest;
import com.binhlaig.pos.receiptsetting.dto.ReceiptAdResponse;
import com.binhlaig.pos.receiptsetting.dto.ReceiptSettingRequest;
import com.binhlaig.pos.receiptsetting.dto.ReceiptSettingResponse;
import com.binhlaig.pos.receiptsetting.entity.ReceiptAd;
import com.binhlaig.pos.receiptsetting.entity.ReceiptSetting;
import com.binhlaig.pos.receiptsetting.repository.ReceiptSettingRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptSettingService {

    private final ReceiptSettingRepository receiptSettingRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(readOnly = true)
    public ReceiptSettingResponse getMyShopSettingByToken(String authorizationHeader) {
        TokenShopInfo shopInfo = getShopInfoFromToken(authorizationHeader);

        ReceiptSetting setting = receiptSettingRepository
                .findByShopId(shopInfo.shopId())
                .orElse(null);

        if (setting == null) {
            return ReceiptSettingResponse.builder()
                    .id(null)
                    .shopId(shopInfo.shopId())
                    .shopCode(shopInfo.shopCode())
                    .shopName("My POS Shop")
                    .address("")
                    .phone("")
                    .secondPhone("")
                    .footerMessage("Thank you for shopping with us!")
                    .ads(List.of())
                    .build();
        }

        return toResponse(setting);
    }

    @Transactional
    public ReceiptSettingResponse saveMyShopSettingByToken(
            String authorizationHeader,
            ReceiptSettingRequest request
    ) {
        TokenShopInfo shopInfo = getShopInfoFromToken(authorizationHeader);

        ReceiptSetting setting = receiptSettingRepository
                .findByShopId(shopInfo.shopId())
                .orElseGet(() -> ReceiptSetting.builder()
                        .shopId(shopInfo.shopId())
                        .shopCode(shopInfo.shopCode())
                        .ads(new ArrayList<>())
                        .build()
                );

        setting.setShopId(shopInfo.shopId());
        setting.setShopCode(shopInfo.shopCode());

        setting.setShopName(cleanOrDefault(request.getShopName(), "My POS Shop"));
        setting.setAddress(clean(request.getAddress()));
        setting.setPhone(clean(request.getPhone()));
        setting.setSecondPhone(clean(request.getSecondPhone()));
        setting.setFooterMessage(
                cleanOrDefault(request.getFooterMessage(), "Thank you for shopping with us!")
        );

        setting.clearAds();

        List<ReceiptAdRequest> adRequests =
                request.getAds() == null ? List.of() : request.getAds();

        for (int i = 0; i < adRequests.size(); i++) {
            ReceiptAdRequest adRequest = adRequests.get(i);

            if (adRequest.getMessage() == null || adRequest.getMessage().isBlank()) {
                continue;
            }

            ReceiptAd ad = ReceiptAd.builder()
                    .title(clean(adRequest.getTitle()))
                    .message(clean(adRequest.getMessage()))
                    .active(adRequest.getActive() == null || adRequest.getActive())
                    .sortOrder(i)
                    .build();

            setting.addAd(ad);
        }

        ReceiptSetting saved = receiptSettingRepository.save(setting);
        return toResponse(saved);
    }

    private TokenShopInfo getShopInfoFromToken(String authorizationHeader) {
        String token = extractBearer(authorizationHeader);

        try {
            String[] parts = token.split("\\.");

            if (parts.length < 2) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid token format"
                );
            }

            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8
            );

            JsonNode payload = objectMapper.readTree(payloadJson);

            Long shopId = readLong(payload, "shopId");
            if (shopId == null) shopId = readLong(payload, "shop_id");

            String shopCode = readText(payload, "shopCode");
            if (shopCode == null || shopCode.isBlank()) {
                shopCode = readText(payload, "shop_code");
            }

            if (shopId == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Token ထဲမှာ shopId မပါပါ။ ပြန် login ဝင်ပါ။"
                );
            }

            return new TokenShopInfo(shopId, shopCode);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token ဖတ်လို့မရပါ။ ပြန် login ဝင်ပါ။"
            );
        }
    }

    private String extractBearer(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authorization header မပါပါ။"
            );
        }

        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim();
        }

        return authorizationHeader.trim();
    }

    private Long readLong(JsonNode node, String field) {
        JsonNode value = node.get(field);

        if (value == null || value.isNull()) return null;

        if (value.isNumber()) return value.asLong();

        if (value.isTextual()) {
            try {
                return Long.parseLong(value.asText());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        return null;
    }

    private String readText(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) return null;
        return value.asText();
    }

    private ReceiptSettingResponse toResponse(ReceiptSetting setting) {
        List<ReceiptAdResponse> ads = setting.getAds() == null
                ? List.of()
                : setting.getAds().stream()
                .map(ad -> ReceiptAdResponse.builder()
                        .id(ad.getId())
                        .title(ad.getTitle())
                        .message(ad.getMessage())
                        .active(ad.getActive())
                        .sortOrder(ad.getSortOrder())
                        .build()
                )
                .toList();

        return ReceiptSettingResponse.builder()
                .id(setting.getId())
                .shopId(setting.getShopId())
                .shopCode(setting.getShopCode())
                .shopName(setting.getShopName())
                .address(setting.getAddress())
                .phone(setting.getPhone())
                .secondPhone(setting.getSecondPhone())
                .footerMessage(setting.getFooterMessage())
                .ads(ads)
                .build();
    }

    private String clean(String value) {
        if (value == null) return "";
        return value.trim();
    }

    private String cleanOrDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) return defaultValue;
        return value.trim();
    }

    private record TokenShopInfo(Long shopId, String shopCode) {}
}