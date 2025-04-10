package com.jbrkan.tiszadatak.mapper;

import com.jbrkan.tiszadatak.dto.ProductCreateDto;
import com.jbrkan.tiszadatak.entity.Product;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductCreateDto> {

    @Override
    public Product map(@NonNull ProductCreateDto item) {
        return Product.builder()
                .name(item.name())
                .description(item.description())
                .code(item.code())
                .priceEur(item.priceEur())
                .build();
    }
}
