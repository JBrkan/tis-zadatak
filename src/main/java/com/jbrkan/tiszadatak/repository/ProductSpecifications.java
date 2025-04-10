package com.jbrkan.tiszadatak.repository;

import com.jbrkan.tiszadatak.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecifications {

    public static Specification<Product> codeOrNameContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            String searchFilter = "%" + filter.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), searchFilter),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchFilter)
            );
        };
    }
}
