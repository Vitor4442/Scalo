package com.vtr.scalo.product.repository;

import com.vtr.scalo.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByCompanyIdAndSku(Integer companyId, String sku);
    Optional<Product> findByIdAndCompanyId(Integer id, Integer companyId);
    List<Product> findByCompanyId(Integer companyId);
}
