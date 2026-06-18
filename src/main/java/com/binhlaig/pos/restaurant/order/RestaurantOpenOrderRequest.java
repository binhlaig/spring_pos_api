package com.binhlaig.pos.restaurant.order;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestaurantOpenOrderRequest {

    @JsonAlias({"orderType", "order_type"})
    @NotBlank(message = "orderType is required")
    private String orderType;

    @JsonAlias({"tableId", "table_id"})
    private Long tableId;

    @JsonAlias({"tableNo", "table_no"})
    private String tableNo;

    @JsonAlias({"staffId", "staff_id"})
    private String staffId;

    @JsonAlias({"staffName", "staff_name"})
    private String staffName;

    private BigDecimal subtotal;

    @JsonAlias({"serviceCharge", "service_charge"})
    private BigDecimal serviceCharge;

    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String status;
    private String note;
    private List<RestaurantOpenOrderItemRequest> items;
}
