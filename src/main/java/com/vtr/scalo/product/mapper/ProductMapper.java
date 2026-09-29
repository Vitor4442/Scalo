package com.vtr.scalo.product.mapper;

import com.vtr.scalo.product.dto.ProductResponseDTO;
import com.vtr.scalo.product.dto.ProductRequestDTO;
import com.vtr.scalo.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto) {
        return Product.builder()
                .sku(dto.sku())
                .name(dto.name())
                .description(dto.description())
                .unit(dto.unit())
                .purchasePrice(dto.purchasePrice())
                .salePrice(dto.salePrice())
                .build();
    }

    public ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getUnit(),
                product.getPurchasePrice(),
                product.getSalePrice()
        );
    }
}