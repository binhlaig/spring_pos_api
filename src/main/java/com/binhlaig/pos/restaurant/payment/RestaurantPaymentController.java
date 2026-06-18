package com.binhlaig.pos.restaurant.payment;

import com.binhlaig.pos.shopfeature.FeatureKey;
import com.binhlaig.pos.shopfeature.ShopFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/payments")
@RequiredArgsConstructor
public class RestaurantPaymentController {

    private final RestaurantPaymentService restaurantPaymentService;
    private final ShopFeatureService shopFeatureService;

    @PostMapping
    public RestaurantPaymentResponse createPayment(
            @RequestBody RestaurantPaymentRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.RESTAURANT_POS);
        return restaurantPaymentService.createPayment(request, authorizationHeader);
    }

    @GetMapping
    public List<RestaurantPaymentListResponse> getPayments(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.RESTAURANT_ORDERS);
        return restaurantPaymentService.getShopPayments(authorizationHeader);
    }

    @GetMapping("/shop")
    public List<RestaurantPaymentListResponse> getShopPayments(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.RESTAURANT_ORDERS);
        return restaurantPaymentService.getShopPayments(authorizationHeader);
    }

    @GetMapping("/my")
    public List<RestaurantPaymentListResponse> getMyPayments(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.RESTAURANT_ORDERS);
        return restaurantPaymentService.getShopPayments(authorizationHeader);
    }
}
