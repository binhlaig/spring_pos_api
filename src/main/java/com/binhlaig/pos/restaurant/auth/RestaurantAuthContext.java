package com.binhlaig.pos.restaurant.auth;

import com.binhlaig.pos.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class RestaurantAuthContext {

    private final JwtService jwtService;

    public RestaurantSession fromAuthorizationHeader(String authorizationHeader) {
        String token = extractBearer(authorizationHeader);
        Long shopId = jwtService.extractShopId(token);
        String shopCode = jwtService.extractShopCode(token);

        if (shopId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Shop ID not found in token");
        }

        if (shopCode == null || shopCode.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Shop code not found in token");
        }

        return new RestaurantSession(shopId, shopCode.trim());
    }

    private String extractBearer(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7).trim();
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empty bearer token");
        }

        return token;
    }
}
