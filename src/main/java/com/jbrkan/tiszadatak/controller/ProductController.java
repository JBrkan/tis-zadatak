package com.jbrkan.tiszadatak.controller;

import com.jbrkan.tiszadatak.config.logging.RequestLogger;
import com.jbrkan.tiszadatak.dto.PopularProductDto;
import com.jbrkan.tiszadatak.dto.ProductCreateDto;
import com.jbrkan.tiszadatak.dto.ProductViewDto;
import com.jbrkan.tiszadatak.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    @RequestLogger
    public ResponseEntity<Page<ProductViewDto>> getProducts(Pageable pageable,
                                                            @RequestParam(required = false) String codeNameFilter) {
        return ResponseEntity.ok(productService.getProductsByCodeNameFilter(pageable, codeNameFilter));
    }

    @GetMapping("/product/by-popularity")
    @RequestLogger
    public ResponseEntity<List<PopularProductDto>> getProductsByPopularity() {
        return ResponseEntity.ok(productService.getPopularProducts());
    }

    @PostMapping("/product")
    @RequestLogger
    public ResponseEntity<ProductViewDto> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        ProductViewDto createdProduct = productService.createProduct(productCreateDto);

        return ResponseEntity
                .created(URI.create("/api/product/" + createdProduct.id().toString()))
                .body(createdProduct);
    }
}
