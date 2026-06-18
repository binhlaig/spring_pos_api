package com.binhlaig.pos.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KitchenTicketCreateRequest {

    @JsonAlias({"orderType", "order_type"})
    @NotBlank(message = "orderType is required")
    private String orderType;

    @JsonAlias({"tableId", "table_id"})
    private Long tableId;

    @JsonAlias({"tableNo", "table_no"})
    private String tableNo;

    private String priority;
    private String note;

    @Valid
    private List<KitchenTicketItemRequest> items;
}
