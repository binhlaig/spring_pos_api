package com.binhlaig.pos.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateRequest {

    @JsonAlias({"status", "ticketStatus", "ticket_status", "itemStatus", "item_status"})
    @NotBlank(message = "status is required")
    private String status;
}
