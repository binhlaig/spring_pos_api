package com.binhlaig.pos.restaurant.controller;

import com.binhlaig.pos.restaurant.dto.KitchenTicketCreateRequest;
import com.binhlaig.pos.restaurant.dto.KitchenTicketItemResponse;
import com.binhlaig.pos.restaurant.dto.KitchenTicketResponse;
import com.binhlaig.pos.restaurant.dto.StatusUpdateRequest;
import com.binhlaig.pos.restaurant.service.KitchenService;
import com.binhlaig.pos.shopfeature.FeatureKey;
import com.binhlaig.pos.shopfeature.ShopFeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;
    private final ShopFeatureService shopFeatureService;

    @GetMapping("/tickets")
    public ResponseEntity<List<KitchenTicketResponse>> getTickets(
            @RequestParam(value = "status", required = false) String status,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireKitchenFeature(authorizationHeader);
        return ResponseEntity.ok(kitchenService.getTickets(status, authorizationHeader));
    }

    @PostMapping("/tickets")
    public ResponseEntity<KitchenTicketResponse> createTicket(
            @Valid @RequestBody KitchenTicketCreateRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireKitchenFeature(authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(kitchenService.createTicket(request, authorizationHeader));
    }

    @PatchMapping("/tickets/{ticketId}/status")
    public ResponseEntity<KitchenTicketResponse> updateTicketStatus(
            @PathVariable Long ticketId,
            @Valid @RequestBody StatusUpdateRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireKitchenFeature(authorizationHeader);
        return ResponseEntity.ok(kitchenService.updateTicketStatus(
                ticketId,
                request.getStatus(),
                authorizationHeader
        ));
    }

    @PatchMapping("/items/{itemId}/status")
    public ResponseEntity<KitchenTicketItemResponse> updateItemStatus(
            @PathVariable Long itemId,
            @Valid @RequestBody StatusUpdateRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireKitchenFeature(authorizationHeader);
        return ResponseEntity.ok(kitchenService.updateItemStatus(
                itemId,
                request.getStatus(),
                authorizationHeader
        ));
    }

    private void requireKitchenFeature(String authorizationHeader) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.RESTAURANT_KITCHEN);
    }
}
