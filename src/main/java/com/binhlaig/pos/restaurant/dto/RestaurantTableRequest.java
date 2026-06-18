package com.binhlaig.pos.restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantTableRequest {

    @NotBlank(message = "tableNo is required")
    @Size(max = 50, message = "tableNo must be less than 50 characters")
    private String tableNo;

    @Size(max = 120, message = "tableName must be less than 120 characters")
    private String tableName;

    @Min(value = 1, message = "seats must be at least 1")
    private Integer seats;

    private String status;

    @Size(max = 120, message = "floorName must be less than 120 characters")
    private String floorName;

    private String note;
}
