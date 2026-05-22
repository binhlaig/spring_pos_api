package com.binhlaig.pos.shop;

import com.binhlaig.pos.shop.dto.ShopSettingsRequest;
import com.binhlaig.pos.shop.dto.ShopSettingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop/settings")
@RequiredArgsConstructor
public class ShopSettingsController {

    private final ShopSettingsService shopSettingsService;

    @GetMapping
    public ShopSettingsResponse getSettings(Authentication authentication) {
        return shopSettingsService.getSettings(authentication);
    }

    @PutMapping
    public ShopSettingsResponse updateSettings(
            @RequestBody ShopSettingsRequest request,
            Authentication authentication
    ) {
        return shopSettingsService.updateSettings(request, authentication);
    }
}