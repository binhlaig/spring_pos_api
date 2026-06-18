package com.binhlaig.pos.shopfeature;

import com.binhlaig.pos.shopfeature.dto.ShopFeatureResponse;
import com.binhlaig.pos.shopfeature.dto.ShopFeatureUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/shops")
@RequiredArgsConstructor
public class AdminShopFeatureController {

    private final ShopFeatureService shopFeatureService;

    @GetMapping("/{shopId}/features")
    public ShopFeatureResponse getShopFeatures(@PathVariable Long shopId) {
        return shopFeatureService.getByShopId(shopId);
    }

    @PutMapping("/{shopId}/features")
    public ShopFeatureResponse updateShopFeatures(
            @PathVariable Long shopId,
            @RequestBody ShopFeatureUpdateRequest request
    ) {
        return shopFeatureService.updateByShopId(shopId, request);
    }
}
