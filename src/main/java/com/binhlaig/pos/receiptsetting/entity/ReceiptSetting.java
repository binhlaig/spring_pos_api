package com.binhlaig.pos.receiptsetting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "receipt_settings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_receipt_settings_shop_id", columnNames = "shop_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_code", length = 100)
    private String shopCode;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 100)
    private String phone;

    @Column(name = "second_phone", length = 100)
    private String secondPhone;

    @Column(name = "footer_message", columnDefinition = "TEXT")
    private String footerMessage;

    @Builder.Default
    @OneToMany(
            mappedBy = "receiptSetting",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("sortOrder ASC, id ASC")
    private List<ReceiptAd> ads = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addAd(ReceiptAd ad) {
        ads.add(ad);
        ad.setReceiptSetting(this);
    }

    public void clearAds() {
        for (ReceiptAd ad : ads) {
            ad.setReceiptSetting(null);
        }
        ads.clear();
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.footerMessage == null || this.footerMessage.isBlank()) {
            this.footerMessage = "Thank you for shopping with us!";
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}