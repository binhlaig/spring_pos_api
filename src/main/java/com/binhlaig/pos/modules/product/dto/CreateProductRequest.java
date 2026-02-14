package com.binhlaig.pos.modules.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank
        String sku,

        @NotBlank
        String productName,   // ✅ change from name → productName

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal productPrice,  // ✅ price → productPrice

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal cost,

        @Min(0)
        Integer stock
) {}
