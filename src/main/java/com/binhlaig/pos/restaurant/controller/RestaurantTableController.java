package com.binhlaig.pos.restaurant.controller;

import com.binhlaig.pos.restaurant.dto.RestaurantTableRequest;
import com.binhlaig.pos.restaurant.dto.RestaurantTableResponse;
import com.binhlaig.pos.restaurant.dto.StatusUpdateRequest;
import com.binhlaig.pos.restaurant.service.RestaurantTableService;
import com.binhlaig.pos.shopfeature.FeatureKey;
import com.binhlaig.pos.shopfeature.ShopFeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService tableService;
    private final ShopFeatureService shopFeatureService;

    @GetMapping
    public ResponseEntity<List<RestaurantTableResponse>> getTables(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTablesFeature(authorizationHeader);
        return ResponseEntity.ok(tableService.getAll(authorizationHeader));
    }

    @PostMapping
    public ResponseEntity<RestaurantTableResponse> createTable(
            @Valid @RequestBody RestaurantTableRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTablesFeature(authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tableService.create(request, authorizationHeader));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableResponse> updateTable(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantTableRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTablesFeature(authorizationHeader);
        return ResponseEntity.ok(tableService.update(id, request, authorizationHeader));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RestaurantTableResponse> updateTableStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTablesFeature(authorizationHeader);
        return ResponseEntity.ok(tableService.updateStatus(id, request.getStatus(), authorizationHeader));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTablesFeature(authorizationHeader);
        tableService.delete(id, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    private void requireTablesFeature(String authorizationHeader) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.RESTAURANT_TABLES);
    }
}
