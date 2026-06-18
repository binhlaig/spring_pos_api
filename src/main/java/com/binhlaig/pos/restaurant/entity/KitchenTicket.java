package com.binhlaig.pos.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kitchen_tickets", indexes = {
        @Index(name = "idx_kitchen_tickets_shop_id", columnList = "shop_id"),
        @Index(name = "idx_kitchen_tickets_shop_code", columnList = "shop_code"),
        @Index(name = "idx_kitchen_tickets_status", columnList = "status"),
        @Index(name = "idx_kitchen_tickets_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_no", nullable = false, unique = true, length = 40)
    private String ticketNo;

    @Column(name = "order_type", nullable = false, length = 50)
    private String orderType;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "table_no", length = 50)
    private String tableNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private KitchenTicketStatus status;

    @Column(nullable = false, length = 30)
    private String priority;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_code", nullable = false, length = 100)
    private String shopCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<KitchenTicketItem> items = new ArrayList<>();

    public void addItem(KitchenTicketItem item) {
        items.add(item);
        item.setTicket(this);
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = KitchenTicketStatus.NEW;
        }
        if (priority == null) {
            priority = "NORMAL";
        }
        clean();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
        clean();
    }

    private void clean() {
        ticketNo = cleanRequired(ticketNo);
        orderType = cleanRequired(orderType);
        tableNo = blankToNull(tableNo);
        priority = cleanRequired(priority);
        note = blankToNull(note);
        shopCode = cleanRequired(shopCode);
    }

    private String cleanRequired(String value) {
        return value == null ? null : value.trim();
    }

    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
