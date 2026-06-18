package com.binhlaig.pos.admin;

import com.binhlaig.pos.admin.dto.SubscriptionPlanRequest;
import com.binhlaig.pos.admin.dto.SubscriptionPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/plans")
public class AdminPlanController {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @GetMapping
    public List<SubscriptionPlanResponse> getPlans() {
        return subscriptionPlanRepository.findByActiveTrueOrderByPriceMonthlyAsc()
                .stream()
                .map(SubscriptionPlanResponse::from)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public SubscriptionPlanResponse createPlan(@RequestBody SubscriptionPlanRequest request) {
        if (request == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Plan request is required");
        }

        String code = cleanRequired(request.code(), "Plan code").toUpperCase();
        if (subscriptionPlanRepository.findByCode(code).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Plan code already exists");
        }

        SubscriptionPlan plan = SubscriptionPlan.builder()
                .code(code)
                .name(cleanRequired(request.name(), "Plan name"))
                .priceMonthly(request.priceMonthly() == null ? BigDecimal.ZERO : request.priceMonthly())
                .maxStaff(request.maxStaff())
                .maxProducts(request.maxProducts())
                .maxReceiptsPerMonth(request.maxReceiptsPerMonth())
                .maxStorageMb(request.maxStorageMb())
                .maxDevices(request.maxDevices())
                .maxBranches(request.maxBranches())
                .allowRestaurant(defaultFalse(request.allowRestaurant()))
                .allowFashion(defaultFalse(request.allowFashion()))
                .allowAnalytics(defaultFalse(request.allowAnalytics()))
                .allowKitchen(defaultFalse(request.allowKitchen()))
                .allowTableOrder(defaultFalse(request.allowTableOrder()))
                .active(request.active() == null || request.active())
                .build();

        return SubscriptionPlanResponse.from(subscriptionPlanRepository.save(plan));
    }

    @PutMapping("/{id}")
    @Transactional
    public SubscriptionPlanResponse updatePlan(
            @PathVariable Long id,
            @RequestBody SubscriptionPlanRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Plan request is required");
        }

        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subscription plan not found"));

        String code = cleanRequired(request.code(), "Plan code").toUpperCase();
        subscriptionPlanRepository.findByCode(code)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(BAD_REQUEST, "Plan code already exists");
                });

        plan.setCode(code);
        plan.setName(cleanRequired(request.name(), "Plan name"));
        plan.setPriceMonthly(request.priceMonthly() == null ? BigDecimal.ZERO : request.priceMonthly());
        plan.setMaxStaff(request.maxStaff());
        plan.setMaxProducts(request.maxProducts());
        plan.setMaxReceiptsPerMonth(request.maxReceiptsPerMonth());
        plan.setMaxStorageMb(request.maxStorageMb());
        plan.setMaxDevices(request.maxDevices());
        plan.setMaxBranches(request.maxBranches());
        plan.setAllowRestaurant(defaultFalse(request.allowRestaurant()));
        plan.setAllowFashion(defaultFalse(request.allowFashion()));
        plan.setAllowAnalytics(defaultFalse(request.allowAnalytics()));
        plan.setAllowKitchen(defaultFalse(request.allowKitchen()));
        plan.setAllowTableOrder(defaultFalse(request.allowTableOrder()));
        plan.setActive(request.active() == null || request.active());
        plan.setUpdatedAt(OffsetDateTime.now());

        return SubscriptionPlanResponse.from(subscriptionPlanRepository.save(plan));
    }

    @PatchMapping("/{id}/disable")
    @Transactional
    public SubscriptionPlanResponse disablePlan(@PathVariable Long id) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Subscription plan not found"));
        plan.setActive(false);
        plan.setUpdatedAt(OffsetDateTime.now());
        return SubscriptionPlanResponse.from(subscriptionPlanRepository.save(plan));
    }

    private String cleanRequired(String value, String fieldName) {
        String cleaned = cleanNullable(value);
        if (cleaned == null) {
            throw new ResponseStatusException(BAD_REQUEST, fieldName + " is required");
        }
        return cleaned;
    }

    private String cleanNullable(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private boolean defaultFalse(Boolean value) {
        return value != null && value;
    }
}
