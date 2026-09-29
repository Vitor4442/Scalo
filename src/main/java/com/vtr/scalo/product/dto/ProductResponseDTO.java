package com.vtr.scalo.product.dto;


import java.math.BigDecimal;

public record ProductResponseDTO(
        String sku,
        String name,
        String description,
        String unit,
        BigDecimal purchasePrice,
        BigDecimal salePrice
        ) {
}
