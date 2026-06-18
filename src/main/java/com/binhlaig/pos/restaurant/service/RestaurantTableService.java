package com.binhlaig.pos.restaurant.service;

import com.binhlaig.pos.admin.PlanLimitService;
import com.binhlaig.pos.restaurant.auth.RestaurantAuthContext;
import com.binhlaig.pos.restaurant.auth.RestaurantSession;
import com.binhlaig.pos.restaurant.dto.RestaurantTableRequest;
import com.binhlaig.pos.restaurant.dto.RestaurantTableResponse;
import com.binhlaig.pos.restaurant.entity.RestaurantTable;
import com.binhlaig.pos.restaurant.entity.RestaurantTableStatus;
import com.binhlaig.pos.restaurant.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantTableService {

    private final RestaurantTableRepository tableRepository;
    private final RestaurantAuthContext authContext;
    private final PlanLimitService planLimitService;

    @Transactional(readOnly = true)
    public List<RestaurantTableResponse> getAll(String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseTableOrder(session.shopId());
        return tableRepository.findByShopIdOrderByFloorNameAscTableNoAsc(session.shopId())
                .stream()
                .map(RestaurantTableResponse::from)
                .toList();
    }

    public RestaurantTableResponse create(RestaurantTableRequest request, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseTableOrder(session.shopId());
        String tableNo = required(request.getTableNo(), "tableNo is required");

        if (tableRepository.existsByShopIdAndTableNoIgnoreCase(session.shopId(), tableNo)) {
            throw new RuntimeException("Table number already exists in this shop");
        }

        RestaurantTable table = RestaurantTable.builder()
                .tableNo(tableNo)
                .tableName(blankToNull(request.getTableName()))
                .seats(request.getSeats())
                .status(parseTableStatus(request.getStatus()))
                .floorName(blankToNull(request.getFloorName()))
                .note(blankToNull(request.getNote()))
                .shopId(session.shopId())
                .shopCode(session.shopCode())
                .build();

        return RestaurantTableResponse.from(tableRepository.save(table));
    }

    public RestaurantTableResponse update(Long id, RestaurantTableRequest request, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseTableOrder(session.shopId());
        RestaurantTable table = getTable(id, session.shopId());
        String tableNo = required(request.getTableNo(), "tableNo is required");

        if (tableRepository.existsByShopIdAndTableNoIgnoreCaseAndIdNot(session.shopId(), tableNo, id)) {
            throw new RuntimeException("Table number already exists in this shop");
        }

        table.setTableNo(tableNo);
        table.setTableName(blankToNull(request.getTableName()));
        table.setSeats(request.getSeats());
        table.setStatus(parseTableStatus(request.getStatus()));
        table.setFloorName(blankToNull(request.getFloorName()));
        table.setNote(blankToNull(request.getNote()));
        table.setShopId(session.shopId());
        table.setShopCode(session.shopCode());

        return RestaurantTableResponse.from(tableRepository.save(table));
    }

    public RestaurantTableResponse updateStatus(Long id, String status, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseTableOrder(session.shopId());
        RestaurantTable table = getTable(id, session.shopId());
        table.setStatus(parseTableStatus(required(status, "status is required")));
        return RestaurantTableResponse.from(tableRepository.save(table));
    }

    public void delete(Long id, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseTableOrder(session.shopId());
        RestaurantTable table = getTable(id, session.shopId());
        tableRepository.delete(table);
    }

    private RestaurantTable getTable(Long id, Long shopId) {
        return tableRepository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new RuntimeException("Restaurant table not found"));
    }

    private RestaurantTableStatus parseTableStatus(String value) {
        if (value == null || value.isBlank()) {
            return RestaurantTableStatus.FREE;
        }

        try {
            return RestaurantTableStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid table status: " + value);
        }
    }

    private String required(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(message);
        }
        return value.trim();
    }

    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
