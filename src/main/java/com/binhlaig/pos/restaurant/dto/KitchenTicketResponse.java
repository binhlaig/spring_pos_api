package com.binhlaig.pos.restaurant.dto;

import com.binhlaig.pos.restaurant.entity.KitchenTicket;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class KitchenTicketResponse {

    private Long id;
    private String ticketNo;
    private String orderType;
    private Long tableId;
    private String tableNo;
    private String status;
    private String priority;
    private String note;
    private Long shopId;
    private String shopCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<KitchenTicketItemResponse> items;

    public static KitchenTicketResponse from(KitchenTicket ticket) {
        return KitchenTicketResponse.builder()
                .id(ticket.getId())
                .ticketNo(ticket.getTicketNo())
                .orderType(ticket.getOrderType())
                .tableId(ticket.getTableId())
                .tableNo(ticket.getTableNo())
                .status(ticket.getStatus() == null ? null : ticket.getStatus().name())
                .priority(ticket.getPriority())
                .note(ticket.getNote())
                .shopId(ticket.getShopId())
                .shopCode(ticket.getShopCode())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .items(ticket.getItems() == null
                        ? List.of()
                        : ticket.getItems().stream()
                                .map(KitchenTicketItemResponse::from)
                                .toList())
                .build();
    }
}
