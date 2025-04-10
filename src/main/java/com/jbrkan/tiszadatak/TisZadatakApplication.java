package com.jbrkan.tiszadatak;

import com.jbrkan.tiszadatak.entity.Product;
import com.jbrkan.tiszadatak.entity.Review;
import com.jbrkan.tiszadatak.repository.ProductRepository;
import com.jbrkan.tiszadatak.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class TisZadatakApplication {

    public static void main(String[] args) {
        SpringApplication.run(TisZadatakApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, ReviewRepository reviewRepository) {
        return args -> {
            Product p1 = Product.builder()
                    .code("PROD12345678901")
                    .name("Laptop")
                    .priceEur(new BigDecimal("999.99"))
                    .priceUsd(new BigDecimal("1099.99"))
                    .description("High-performance laptop")
                    .build();

            Product p2 = Product.builder()
                    .code("PROD12345678902")
                    .name("Smartphone")
                    .priceEur(new BigDecimal("699.99"))
                    .priceUsd(new BigDecimal("749.99"))
                    .description("Latest model smartphone")
                    .build();

            Product p3 = Product.builder()
                    .code("PROD12345678903")
                    .name("Headphones")
                    .priceEur(new BigDecimal("149.99"))
                    .priceUsd(new BigDecimal("159.99"))
                    .description("Noise-cancelling headphones")
                    .build();

            Product p4 = Product.builder()
                    .code("PROD12345678904")
                    .name("Monitor")
                    .priceEur(new BigDecimal("299.99"))
                    .priceUsd(new BigDecimal("329.99"))
                    .description("4K Ultra HD monitor")
                    .build();

            Product p5 = Product.builder()
                    .code("PROD12345678905")
                    .name("Keyboard")
                    .priceEur(new BigDecimal("89.99"))
                    .priceUsd(new BigDecimal("99.99"))
                    .description("Mechanical gaming keyboard")
                    .build();

            List<Product> products = List.of(p1, p2, p3, p4, p5);
            productRepository.saveAll(products);

            List<Review> reviews = List.of(
                    Review.builder().product(p1).reviewer("John Doe").text("Great laptop, very fast!").rating(5).build(),
                    Review.builder().product(p1).reviewer("Jane Smith").text("Good value for money.").rating(4).build(),
                    Review.builder().product(p2).reviewer("Mike Johnson").text("Amazing camera quality.").rating(5).build(),
                    Review.builder().product(p2).reviewer("Emily Brown").text("Battery life could be better.").rating(3).build(),
                    Review.builder().product(p3).reviewer("Alex Lee").text("Perfect sound isolation.").rating(5).build(),
                    Review.builder().product(p3).reviewer("Sarah Davis").text("Comfortable fit.").rating(4).build(),
                    Review.builder().product(p4).reviewer("Tom Wilson").text("Stunning display quality.").rating(5).build(),
                    Review.builder().product(p4).reviewer("Lisa Anderson").text("A bit pricey but worth it.").rating(4).build(),
                    Review.builder().product(p5).reviewer("Peter Clark").text("Great typing experience.").rating(5).build(),
                    Review.builder().product(p5).reviewer("Anna White").text("Keys are very responsive.").rating(4).build()
            );

            reviewRepository.saveAll(reviews);
        };
    }
}
