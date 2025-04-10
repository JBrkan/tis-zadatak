package com.jbrkan.tiszadatak.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbrkan.tiszadatak.dto.ExchangeRateDto;
import com.jbrkan.tiszadatak.dto.PopularProductDto;
import com.jbrkan.tiszadatak.dto.ProductCreateDto;
import com.jbrkan.tiszadatak.dto.ProductViewDto;
import com.jbrkan.tiszadatak.entity.Product;
import com.jbrkan.tiszadatak.entity.Review;
import com.jbrkan.tiszadatak.repository.ProductRepository;
import com.jbrkan.tiszadatak.repository.ReviewRepository;
import com.jbrkan.tiszadatak.service.HNBApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @MockitoBean
    private HNBApi hnbApi;

    @Autowired
    private ObjectMapper objectMapper;
    private static final Currency USD = Currency.getInstance("USD");

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        productRepository.deleteAll();

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
                .code("PROD12345678912")
                .name("Mouse")
                .priceEur(new BigDecimal("60.99"))
                .priceUsd(new BigDecimal("70.99"))
                .description("High-performance mouse")
                .build();
        Product p4 = Product.builder()
                .code("PROD12345678929")
                .name("Keyboard")
                .priceEur(new BigDecimal("40.99"))
                .priceUsd(new BigDecimal("50.99"))
                .description("Latest model keyboard")
                .build();
        Product p5 = Product.builder()
                .code("PROD12345678930")
                .name("Monitor")
                .priceEur(new BigDecimal("499.99"))
                .priceUsd(new BigDecimal("550.99"))
                .description("4K Monitor with HDR")
                .build();
        Product p6 = Product.builder()
                .code("PROD12345678931")
                .name("Tablet")
                .priceEur(new BigDecimal("399.99"))
                .priceUsd(new BigDecimal("450.99"))
                .description("Powerful tablet for work and play")
                .build();
        Product p7 = Product.builder()
                .code("PROD12345678932")
                .name("Smartwatch")
                .priceEur(new BigDecimal("150.99"))
                .priceUsd(new BigDecimal("170.99"))
                .description("Fitness tracker and smartwatch")
                .build();

        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7));


        // Laptop: 5 stars, 5 stars, 5 stars
        Review review1 = Review.builder().product(p1).reviewer("John Doe").text("Great laptop, very fast!").rating(5).build();
        Review review2 = Review.builder().product(p1).reviewer("Jane Smith").text("Good value for money.").rating(5).build();
        Review review3 = Review.builder().product(p1).reviewer("Alex Lee").text("Excellent performance!").rating(5).build();

        // Smartphone: 5 stars, 5 stars, 5 stars, 4 stars
        Review review4 = Review.builder().product(p2).reviewer("Mike Johnson").text("Amazing camera quality.").rating(5).build();
        Review review5 = Review.builder().product(p2).reviewer("Emily Brown").text("Battery life is great.").rating(5).build();
        Review review6 = Review.builder().product(p2).reviewer("Anna White").text("Sleek and fast.").rating(4).build();
        Review review7 = Review.builder().product(p2).reviewer("Tom Wilson").text("Good but expensive.").rating(4).build();

        // Mouse: 3 stars, 5 stars
        Review review8 = Review.builder().product(p3).reviewer("Alex Lee").text("Affordable and reliable.").rating(3).build();
        Review review9 = Review.builder().product(p3).reviewer("Sarah Davis").text("Great mouse for the price.").rating(5).build();

        // Keyboard: 4 stars, 3 stars
        Review review10 = Review.builder().product(p4).reviewer("Tom Wilson").text("Solid keyboard, comfortable to type.").rating(4).build();
        Review review11 = Review.builder().product(p4).reviewer("Lisa Anderson").text("Not the best, but works well.").rating(3).build();

        // Monitor: 3 stars, 3 stars
        Review review12 = Review.builder().product(p5).reviewer("Peter Clark").text("Average color accuracy.").rating(3).build();
        Review review13 = Review.builder().product(p5).reviewer("Anna White").text("Affordable product.").rating(3).build();

        // Tablet: 3 stars, 4 stars
        Review review14 = Review.builder().product(p6).reviewer("James Miller").text("Average performance and screen.").rating(3).build();
        Review review15 = Review.builder().product(p6).reviewer("Olivia Brown").text("Tablet works perfectly for my work.").rating(4).build();

        // Smartwatch: 2 stars, 2 stars, 3 stars
        Review review16 = Review.builder().product(p7).reviewer("Rachel Green").text("Very bad product.").rating(2).build();
        Review review17 = Review.builder().product(p7).reviewer("Monica Geller").text("Bad battery life.").rating(2).build();
        Review review18 = Review.builder().product(p7).reviewer("Chandler Bing").text("Decent.").rating(3).build();

        reviewRepository.saveAll(List.of(
                review1, review2, review3, review4, review5, review6, review7, review8,
                review9, review10, review11, review12, review13, review14, review15,
                review16, review17, review18
        ));

        when(hnbApi.getExchangeRateForCurrency(USD))
                .thenReturn(new ExchangeRateDto("2024-05-02",
                        "HR",
                        "dollar",
                        "USD",
                        new BigDecimal("1.100023"),
                        new BigDecimal("1.100023"),
                        new BigDecimal("1.100023")));
    }


    @Test
    void testGetProductsByPopularity() throws Exception {
        var response = mockMvc.perform(get("/api/product/by-popularity"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<PopularProductDto> actualPopularProductDtos = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        List<PopularProductDto> expectedPopularProductDtos = List.of(
                new PopularProductDto("Laptop", 5.0),
                new PopularProductDto("Smartphone", 4.5),
                new PopularProductDto("Mouse", 4.0)
        );

        assertThat(actualPopularProductDtos).isEqualTo(expectedPopularProductDtos);
    }

    @Test
    void testGetProductsWithFilter() throws Exception {
        var response = mockMvc.perform(get("/api/product")
                        .param("codeNameFilter", "Lap"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        JsonNode root = objectMapper.readTree(response.getResponse().getContentAsString());
        JsonNode contentNode = root.get("content");

        List<ProductViewDto> actualProductViewDtos = objectMapper.readValue(
                contentNode.toString(),
                new TypeReference<>() {
                }
        );

        List<ProductViewDto> expectedProductViewDtos = List.of(
                new ProductViewDto(6L,
                        "PROD12345678901",
                        "Laptop",
                        new BigDecimal("999.99"),
                        new BigDecimal("1099.99"),
                        "High-performance laptop")
        );

        assertThat(actualProductViewDtos).isEqualTo(expectedProductViewDtos);
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductCreateDto newProduct = new ProductCreateDto(
                "PROD12345678123",
                "Special Mouse",
                new BigDecimal("29.99"),
                "Wireless mouse"
        );

        String newProductJson = objectMapper.writeValueAsString(newProduct);

        var response = mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        ProductViewDto actualProduct = objectMapper.readValue(response.getResponse().getContentAsString(), ProductViewDto.class);
        ProductViewDto expectedProduct = new ProductViewDto(
                actualProduct.id(),
                "PROD12345678123",
                "Special Mouse",
                new BigDecimal("29.99"),
                new BigDecimal("32.99"),
                "Wireless mouse");
      assertThat(actualProduct).isEqualTo(expectedProduct);
    }
}
