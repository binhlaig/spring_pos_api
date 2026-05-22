package com.binhlaig.pos.receiptsetting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptAdResponse {
    private Long id;
    private String title;
    private String message;
    private Boolean active;
    private Integer sortOrder;
}