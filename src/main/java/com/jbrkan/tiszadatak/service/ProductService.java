package com.jbrkan.tiszadatak.service;

import com.jbrkan.tiszadatak.dto.ExchangeRateDto;
import com.jbrkan.tiszadatak.dto.PopularProductDto;
import com.jbrkan.tiszadatak.dto.ProductCreateDto;
import com.jbrkan.tiszadatak.dto.ProductViewDto;
import com.jbrkan.tiszadatak.entity.Product;
import com.jbrkan.tiszadatak.mapper.ProductDtoMapper;
import com.jbrkan.tiszadatak.mapper.ProductMapper;
import com.jbrkan.tiszadatak.repository.ProductRepository;
import com.jbrkan.tiszadatak.repository.ProductSpecifications;
import com.jbrkan.tiszadatak.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Currency USD = Currency.getInstance("USD");

    private final HNBApi hnbApi;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductDtoMapper productDtoMapper;

    @Transactional
    public ProductViewDto createProduct(ProductCreateDto productCreateDto) {
        ExchangeRateDto exchangeRateDto = hnbApi.getExchangeRateForCurrency(USD);

        Product product = productMapper.map(productCreateDto);
        product.setPriceUsd(product.getPriceEur().multiply(exchangeRateDto.srednjiTecaj()));

        return productDtoMapper.map(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public Page<ProductViewDto> getProductsByCodeNameFilter(Pageable pageable, String codeNameFilter) {
        Specification<Product> productSpecification = ProductSpecifications.codeOrNameContains(codeNameFilter);

        return productDtoMapper.mapPage(productRepository.findAll(productSpecification, pageable));
    }

    @Transactional(readOnly = true)
    public List<PopularProductDto> getPopularProducts() {
        return reviewRepository.findTop3MostPopularProducts();
    }
}
