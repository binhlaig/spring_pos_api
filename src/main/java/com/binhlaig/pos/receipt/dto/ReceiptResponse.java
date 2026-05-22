package com.binhlaig.pos.receipt.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptResponse {
    private Long id;
    private String receiptNo;
    private String status;
    private BigDecimal grandTotal;
    private LocalDateTime createdAt;
}