package com.jbrkan.tiszadatak.mapper;

import com.jbrkan.tiszadatak.dto.ProductViewDto;
import com.jbrkan.tiszadatak.entity.Product;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements Mapper<ProductViewDto, Product> {
    @Override
    public ProductViewDto map(@NonNull Product item) {
        return new ProductViewDto(item.getId(),
                item.getCode(),
                item.getName(),
                item.getPriceEur(),
                item.getPriceUsd(),
                item.getDescription());
    }
}
