package com.binhlaig.pos.restaurant.service;

import com.binhlaig.pos.admin.PlanLimitService;
import com.binhlaig.pos.restaurant.auth.RestaurantAuthContext;
import com.binhlaig.pos.restaurant.auth.RestaurantSession;
import com.binhlaig.pos.restaurant.dto.KitchenTicketCreateRequest;
import com.binhlaig.pos.restaurant.dto.KitchenTicketItemRequest;
import com.binhlaig.pos.restaurant.dto.KitchenTicketItemResponse;
import com.binhlaig.pos.restaurant.dto.KitchenTicketResponse;
import com.binhlaig.pos.restaurant.entity.*;
import com.binhlaig.pos.restaurant.repository.KitchenTicketItemRepository;
import com.binhlaig.pos.restaurant.repository.KitchenTicketRepository;
import com.binhlaig.pos.restaurant.repository.RestaurantTableRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class KitchenService {

    private static final DateTimeFormatter TICKET_NO_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final KitchenTicketRepository ticketRepository;
    private final KitchenTicketItemRepository itemRepository;
    private final RestaurantTableRepository tableRepository;
    private final RestaurantAuthContext authContext;
    private final ObjectMapper objectMapper;
    private final PlanLimitService planLimitService;

    @Transactional(readOnly = true)
    public List<KitchenTicketResponse> getTickets(String status, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseKitchen(session.shopId());

        if (status == null || status.isBlank()) {
            return ticketRepository.findByShopIdOrderByCreatedAtDesc(session.shopId())
                    .stream()
                    .map(KitchenTicketResponse::from)
                    .toList();
        }

        KitchenTicketStatus ticketStatus = parseTicketStatus(status);
        return ticketRepository.findByShopIdAndStatusOrderByCreatedAtDesc(session.shopId(), ticketStatus)
                .stream()
                .map(KitchenTicketResponse::from)
                .toList();
    }

    public KitchenTicketResponse createTicket(KitchenTicketCreateRequest request, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseKitchen(session.shopId());
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("At least one kitchen ticket item is required");
        }

        String tableNo = blankToNull(request.getTableNo());
        if (request.getTableId() != null) {
            RestaurantTable table = tableRepository.findByIdAndShopId(request.getTableId(), session.shopId())
                    .orElseThrow(() -> new RuntimeException("Restaurant table not found"));
            tableNo = table.getTableNo();
        }

        KitchenTicket ticket = KitchenTicket.builder()
                .ticketNo(generateTicketNo())
                .orderType(normalizeOrderType(request.getOrderType()))
                .tableId(request.getTableId())
                .tableNo(tableNo)
                .status(KitchenTicketStatus.NEW)
                .priority(normalizePriority(request.getPriority()))
                .note(blankToNull(request.getNote()))
                .shopId(session.shopId())
                .shopCode(session.shopCode())
                .build();

        request.getItems().forEach(itemRequest -> ticket.addItem(toItem(itemRequest)));

        return KitchenTicketResponse.from(ticketRepository.save(ticket));
    }

    public KitchenTicketResponse updateTicketStatus(Long ticketId, String status, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseKitchen(session.shopId());
        KitchenTicket ticket = ticketRepository.findByIdAndShopId(ticketId, session.shopId())
                .orElseThrow(() -> new RuntimeException("Kitchen ticket not found"));

        ticket.setStatus(parseTicketStatus(status));
        return KitchenTicketResponse.from(ticketRepository.save(ticket));
    }

    public KitchenTicketItemResponse updateItemStatus(Long itemId, String status, String authorizationHeader) {
        RestaurantSession session = authContext.fromAuthorizationHeader(authorizationHeader);
        planLimitService.assertCanUseKitchen(session.shopId());
        KitchenTicketItem item = itemRepository.findByIdAndTicketShopId(itemId, session.shopId())
                .orElseThrow(() -> new RuntimeException("Kitchen ticket item not found"));

        item.setStatus(parseItemStatus(status));
        return KitchenTicketItemResponse.from(itemRepository.save(item));
    }

    private KitchenTicketItem toItem(KitchenTicketItemRequest request) {
        return KitchenTicketItem.builder()
                .menuItemId(request.getMenuItemId())
                .itemName(required(request.getItemName(), "itemName is required"))
                .quantity(request.getQuantity() == null ? 1 : request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .modifiers(toText(request.getModifiers()))
                .kitchenNote(blankToNull(request.getKitchenNote()))
                .status(KitchenItemStatus.NEW)
                .build();
    }

    private String generateTicketNo() {
        return "KT-" + LocalDateTime.now().format(TICKET_NO_FORMAT);
    }

    private KitchenTicketStatus parseTicketStatus(String value) {
        try {
            return KitchenTicketStatus.valueOf(required(value, "status is required").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid kitchen ticket status: " + value);
        }
    }

    private KitchenItemStatus parseItemStatus(String value) {
        try {
            return KitchenItemStatus.valueOf(required(value, "status is required").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid kitchen item status: " + value);
        }
    }

    private String required(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(message);
        }
        return value.trim();
    }

    private String normalizeOrderType(String value) {
        String orderType = value;
        if (orderType == null || orderType.isBlank()) {
            orderType = "DINE_IN";
        }

        String normalized = orderType.trim().toUpperCase(Locale.ROOT);
        if (!normalized.equals("DINE_IN")
                && !normalized.equals("TAKEAWAY")
                && !normalized.equals("DELIVERY")) {
            throw new RuntimeException("Invalid orderType: " + value);
        }

        return normalized;
    }

    private String normalizePriority(String value) {
        String priority = value;
        if (priority == null || priority.isBlank()) {
            priority = "NORMAL";
        }

        String normalized = priority.trim().toUpperCase(Locale.ROOT);
        if (!normalized.equals("LOW")
                && !normalized.equals("NORMAL")
                && !normalized.equals("HIGH")) {
            throw new RuntimeException("Invalid kitchen ticket priority: " + value);
        }

        return normalized;
    }

    private String toText(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String text) {
            return blankToNull(text);
        }

        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Invalid modifiers", ex);
        }
    }

    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
