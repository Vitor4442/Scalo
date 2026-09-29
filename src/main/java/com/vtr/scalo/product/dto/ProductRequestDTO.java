package com.vtr.scalo.product.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "O SKU é obrigatório")
        @Size(max = 100, message = "O SKU deve ter no máximo 100 caracteres")
        String sku,

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
        String name,

        String description,

        @NotBlank(message = "A unidade é obrigatória")
        @Size(max = 20, message = "A unidade deve ter no máximo 20 caracteres")
        String unit,

        @NotNull(message = "O preço de compra é obrigatório")
        @DecimalMin(value = "0.0", message = "O preço de compra não pode ser negativo")
        BigDecimal purchasePrice,

        @NotNull(message = "O preço de venda é obrigatório")
        @DecimalMin(value = "0.0", message = "O preço de venda não pode ser negativo")
        BigDecimal salePrice
) {
}