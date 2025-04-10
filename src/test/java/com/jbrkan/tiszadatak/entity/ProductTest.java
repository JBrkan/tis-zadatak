package com.jbrkan.tiszadatak.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductTest {

    @Test
    void testEquals_sameIdNameCode_returnsTrue() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .priceEur(new BigDecimal("10.50"))
                .priceUsd(new BigDecimal("11.00"))
                .description("Description 1")
                .build();

        Product p2 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .priceEur(new BigDecimal("20.00"))
                .priceUsd(new BigDecimal("22.00"))
                .description("Description 2")
                .build();

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_differentId_sameNameCode_returnsFalse() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .id(2L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_sameId_differentName_returnsFalse() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .id(1L)
                .name("ProductB")
                .code("ABC123XYZ789456")
                .build();

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_sameId_differentCode_returnsFalse() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("DEF456XYZ789123")
                .build();

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_allNullIds_sameNameCode_returnsTrue() {
        Product p1 = Product.builder()
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_oneNullId_sameNameCode_returnsFalse() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_sameIdName_differentCodeNull_returnsFalse() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code(null)
                .build();

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_sameIdCode_differentNameNull_returnsFalse() {
        Product p1 = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        Product p2 = Product.builder()
                .id(1L)
                .name(null)
                .code("ABC123XYZ789456")
                .build();

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEquals_null_returnsFalse() {
        Product product = Product.builder()
                .id(1L)
                .name("ProductA")
                .code("ABC123XYZ789456")
                .build();

        assertNotEquals(null, product);
    }
}
