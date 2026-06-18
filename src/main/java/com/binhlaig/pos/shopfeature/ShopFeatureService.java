package com.binhlaig.pos.shopfeature;

import com.binhlaig.pos.admin.Shop;
import com.binhlaig.pos.admin.ShopRepository;
import com.binhlaig.pos.auth.JwtService;
import com.binhlaig.pos.shopfeature.dto.ShopFeatureResponse;
import com.binhlaig.pos.shopfeature.dto.ShopFeatureUpdateRequest;
import com.binhlaig.pos.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopFeatureService {

    private final ShopFeatureRepository shopFeatureRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public ShopFeatureResponse getOrCreateForCurrentShop(Long shopId, String shopCode) {
        return ShopFeatureResponse.from(getOrCreateEntity(shopId, shopCode));
    }

    @Transactional
    public ShopFeatureResponse getByShopId(Long shopId) {
        return ShopFeatureResponse.from(getOrCreateEntity(shopId, resolveShopCodeOrThrow(shopId)));
    }

    @Transactional
    public ShopFeatureResponse updateByShopId(Long shopId, ShopFeatureUpdateRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feature update request is required");
        }

        ShopFeature feature = getOrCreateEntity(shopId, resolveShopCodeOrThrow(shopId));

        if (request.getDashboardEnabled() != null) feature.setDashboardEnabled(request.getDashboardEnabled());
        if (request.getProductsEnabled() != null) feature.setProductsEnabled(request.getProductsEnabled());
        if (request.getPosRegisterEnabled() != null) feature.setPosRegisterEnabled(request.getPosRegisterEnabled());
        if (request.getReceiptsEnabled() != null) feature.setReceiptsEnabled(request.getReceiptsEnabled());
        if (request.getStaffEnabled() != null) feature.setStaffEnabled(request.getStaffEnabled());
        if (request.getTasksEnabled() != null) feature.setTasksEnabled(request.getTasksEnabled());
        if (request.getTimecardEnabled() != null) feature.setTimecardEnabled(request.getTimecardEnabled());
        if (request.getRestaurantPosEnabled() != null) feature.setRestaurantPosEnabled(request.getRestaurantPosEnabled());
        if (request.getRestaurantTablesEnabled() != null) feature.setRestaurantTablesEnabled(request.getRestaurantTablesEnabled());
        if (request.getRestaurantKitchenEnabled() != null) feature.setRestaurantKitchenEnabled(request.getRestaurantKitchenEnabled());
        if (request.getRestaurantOrdersEnabled() != null) feature.setRestaurantOrdersEnabled(request.getRestaurantOrdersEnabled());
        if (request.getAllowRestaurant() != null) feature.setAllowRestaurant(request.getAllowRestaurant());
        if (request.getAllowKitchen() != null) feature.setAllowKitchen(request.getAllowKitchen());
        if (request.getAllowTableOrder() != null) feature.setAllowTableOrder(request.getAllowTableOrder());
        if (Boolean.TRUE.equals(request.getRestaurantPosEnabled())) feature.setAllowRestaurant(true);
        if (Boolean.TRUE.equals(request.getRestaurantKitchenEnabled())) feature.setAllowKitchen(true);
        if (Boolean.TRUE.equals(request.getRestaurantTablesEnabled())) feature.setAllowTableOrder(true);
        if (request.getSettingsEnabled() != null) feature.setSettingsEnabled(request.getSettingsEnabled());

        return ShopFeatureResponse.from(shopFeatureRepository.save(feature));
    }

    @Transactional
    public void requireFeature(Long shopId, FeatureKey featureKey) {
        requireFeature(shopId, null, featureKey);
    }

    @Transactional
    public void requireFeature(Long shopId, String shopCode, FeatureKey featureKey) {
        ShopFeature feature = getOrCreateEntity(shopId, resolveShopCode(shopId, shopCode));
        boolean enabled = isEnabled(feature, featureKey);
        log.info(
                "Shop feature guard shopId={} shopCode={} featureName={} value={}",
                feature.getShopId(),
                feature.getShopCode(),
                featureKey,
                enabled
        );
        if (!enabled) {
            throw new FeatureDisabledException();
        }
    }

    @Transactional
    public void requireFeatureFromAuthorization(String authorizationHeader, FeatureKey featureKey) {
        String token = extractBearer(authorizationHeader);
        requireFeature(jwtService.extractShopId(token), jwtService.extractShopCode(token), featureKey);
    }

    private ShopFeature getOrCreateEntity(Long shopId, String shopCode) {
        if (shopId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Shop id not found in token");
        }

        String finalShopCode = cleanShopCode(shopCode);

        return findExistingFeature(shopId, finalShopCode)
                .orElseGet(() -> createDefaultFeature(shopId, finalShopCode));
    }

    private java.util.Optional<ShopFeature> findExistingFeature(Long shopId, String shopCode) {
        return shopFeatureRepository.findByShopId(shopId)
                .or(() -> shopFeatureRepository.findByShopCode(shopCode));
    }

    private ShopFeature createDefaultFeature(Long shopId, String shopCode) {
        try {
            return shopFeatureRepository.save(defaultFeature(shopId, shopCode));
        } catch (DataIntegrityViolationException ex) {
            return shopFeatureRepository.findByShopId(shopId)
                    .orElseThrow(() -> ex);
        }
    }

    private ShopFeature defaultFeature(Long shopId, String shopCode) {
        return ShopFeature.builder()
                .shopId(shopId)
                .shopCode(shopCode)
                .dashboardEnabled(true)
                .productsEnabled(true)
                .posRegisterEnabled(true)
                .receiptsEnabled(true)
                .staffEnabled(false)
                .tasksEnabled(false)
                .timecardEnabled(false)
                .restaurantPosEnabled(false)
                .restaurantTablesEnabled(false)
                .restaurantKitchenEnabled(false)
                .restaurantOrdersEnabled(false)
                .allowRestaurant(true)
                .allowKitchen(true)
                .allowTableOrder(true)
                .settingsEnabled(true)
                .build();
    }

    private boolean isEnabled(ShopFeature feature, FeatureKey featureKey) {
        return switch (featureKey) {
            case DASHBOARD -> Boolean.TRUE.equals(feature.getDashboardEnabled());
            case PRODUCTS -> Boolean.TRUE.equals(feature.getProductsEnabled());
            case POS_REGISTER -> Boolean.TRUE.equals(feature.getPosRegisterEnabled());
            case RECEIPTS -> Boolean.TRUE.equals(feature.getReceiptsEnabled());
            case STAFF -> Boolean.TRUE.equals(feature.getStaffEnabled());
            case TASKS -> Boolean.TRUE.equals(feature.getTasksEnabled());
            case TIMECARD -> Boolean.TRUE.equals(feature.getTimecardEnabled());
            case RESTAURANT_POS -> Boolean.TRUE.equals(feature.getRestaurantPosEnabled())
                    || Boolean.TRUE.equals(feature.getAllowRestaurant());
            case RESTAURANT_TABLES -> Boolean.TRUE.equals(feature.getRestaurantTablesEnabled())
                    || Boolean.TRUE.equals(feature.getAllowTableOrder());
            case RESTAURANT_KITCHEN -> Boolean.TRUE.equals(feature.getRestaurantKitchenEnabled())
                    || Boolean.TRUE.equals(feature.getAllowKitchen());
            case RESTAURANT_ORDERS -> Boolean.TRUE.equals(feature.getRestaurantOrdersEnabled())
                    || Boolean.TRUE.equals(feature.getAllowRestaurant());
            case SETTINGS -> Boolean.TRUE.equals(feature.getSettingsEnabled());
        };
    }

    private String extractBearer(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
        }

        String token = authorizationHeader.substring(7).trim();
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empty bearer token");
        }

        return token;
    }

    private String resolveShopCode(Long shopId, String providedShopCode) {
        String cleanProvided = cleanShopCodeOrNull(providedShopCode);
        if (cleanProvided != null) {
            return cleanProvided;
        }

        return resolveShopCodeOrThrow(shopId);
    }

    private String resolveShopCodeOrThrow(Long shopId) {
        if (shopId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Shop id not found in token");
        }

        String shopCode = shopRepository.findById(shopId)
                .map(Shop::getShopCode)
                .or(() -> userRepository.findFirstByShopId(shopId).map(user -> user.getShopCode()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found: " + shopId));

        return cleanShopCode(shopCode);
    }

    private String cleanShopCode(String shopCode) {
        String clean = cleanShopCodeOrNull(shopCode);
        if (clean == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Shop code not found");
        }
        return clean;
    }

    private String cleanShopCodeOrNull(String shopCode) {
        if (shopCode == null || shopCode.isBlank()) {
            return null;
        }
        return shopCode.trim();
    }
}
