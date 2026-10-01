package com.vtr.scalo.product.service;

import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.company.repository.CompanyRepository;
import com.vtr.scalo.product.dto.ProductRequestDTO;
import com.vtr.scalo.product.dto.ProductResponseDTO;
import com.vtr.scalo.product.entity.Product;
import com.vtr.scalo.product.mapper.ProductMapper;
import com.vtr.scalo.product.repository.ProductRepository;
import com.vtr.scalo.shared.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
//todo refatorar como ele pega o companyId
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CompanyRepository companyRepository;

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Integer companyId = UserUtils.getCurrentCompanyId();
        Company company =  companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        Product product = productMapper.toEntity(dto);
        product.setCompany(company);
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {

        Integer companyId = UserUtils.getCurrentCompanyId();

        return productRepository.findByCompanyId(companyId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Integer id) {

        Integer companyId = UserUtils.getCurrentCompanyId();

        Product product = productRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado")
                );

        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponseDTO update(Integer id, ProductRequestDTO dto) {

        Integer companyId = UserUtils.getCurrentCompanyId();
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        validateCompany(product, companyId);

        product.setSku(dto.sku());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setUnit(dto.unit());
        product.setPurchasePrice(dto.purchasePrice());
        product.setSalePrice(dto.salePrice());

        return productMapper.toResponse(product);
    }



    @Transactional
    public void delete(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado")
                );

        productRepository.delete(product);
    }

    private static void validateCompany(Product product, Integer companyId) {
        if (!Objects.equals(product.getCompany().getId(), companyId)) {
            throw new RuntimeException("Sua empresa não pode editar esse produto");
        }
    }
}