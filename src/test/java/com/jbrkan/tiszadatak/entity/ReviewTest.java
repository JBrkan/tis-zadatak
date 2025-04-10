package com.jbrkan.tiszadatak.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    void testEquals_sameId_returnsTrue() {
        Product product = Product.builder().id(1L).name("ProductA").code("ABC123XYZ789456").build();

        Review r1 = Review.builder()
                .id(1L)
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        Review r2 = Review.builder()
                .id(1L)
                .product(product)
                .reviewer("Jane")
                .rating(4)
                .text("Good")
                .build();

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testEquals_differentId_returnsFalse() {
        Product product = Product.builder().id(1L).name("ProductA").code("ABC123XYZ789456").build();

        Review r1 = Review.builder()
                .id(1L)
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        Review r2 = Review.builder()
                .id(2L)
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        assertNotEquals(r1, r2);
        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testEquals_bothNullId_returnsTrue() {
        Product product = Product.builder().id(1L).name("ProductA").code("ABC123XYZ789456").build();

        Review r1 = Review.builder()
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        Review r2 = Review.builder()
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testEquals_oneNullId_returnsFalse() {
        Product product = Product.builder().id(1L).name("ProductA").code("ABC123XYZ789456").build();

        Review r1 = Review.builder()
                .id(1L)
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        Review r2 = Review.builder()
                .product(product)
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        assertNotEquals(r1, r2);
        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testEquals_null_returnsFalse() {
        Review review = Review.builder()
                .id(1L)
                .product(Product.builder().id(1L).name("ProductA").code("ABC123XYZ789456").build())
                .reviewer("John")
                .rating(5)
                .text("Great")
                .build();

        assertNotEquals(null, review);
    }

}
