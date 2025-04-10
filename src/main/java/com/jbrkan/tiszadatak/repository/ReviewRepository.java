package com.jbrkan.tiszadatak.repository;

import com.jbrkan.tiszadatak.dto.PopularProductDto;
import com.jbrkan.tiszadatak.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT new com.jbrkan.tiszadatak.dto.PopularProductDto(r.product.name, AVG(r.rating))
                        FROM Review r
                        GROUP BY r.product
                        ORDER BY AVG(r.rating) DESC
                        LIMIT 3
            """)
    List<PopularProductDto> findTop3MostPopularProducts();
}
