package com.jefferpgdev.ecommerceprices.implement;

import com.jefferpgdev.ecommerceprices.config.TestConfiguration;
import com.jefferpgdev.ecommerceprices.entity.Price;
import com.jefferpgdev.ecommerceprices.exception.InvalidDataException;
import com.jefferpgdev.ecommerceprices.utils.PriceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
class PriceServiceImplTest {

    @Autowired private PriceServiceImpl priceServiceImpl;

    private static PriceUtils priceUtils;

    @BeforeAll
    static void beforeAll() {
        priceUtils = new PriceUtils();
    }

    private static Stream<Arguments> providePriceRequestData() {
        return priceUtils.setPriceRequestParameters();
    }

    @ParameterizedTest(name = "{index} => brandId {0}, productId {1}, date {2} Invalid Data Exception")
    @CsvSource(value = {
            "null,35455,2020-06-14T10:00:00",
            "1,null,2022-06-14T10:00:00",
            "1,35455,null"
    }, nullValues = "null")
    void send_invalid_data_test(Integer brandId, Integer productId, LocalDateTime date) {
        // Assert validations not valid data
        assertThrows(InvalidDataException.class, () -> priceServiceImpl.getPrice(brandId, productId, date));
    }

    @ParameterizedTest(name = "{index} => brandId {0}, productId {1}, date {2}, id result {3} Valid Data")
    @MethodSource("providePriceRequestData")
    void get_valid_data_price_whit_jpa(Integer brandId, Integer productId, LocalDateTime date, Long id) {
        // Consume the Service Implementation
        Price returnedPrice = priceServiceImpl.getPrice(brandId, productId, date);
        Price expectedPrice = priceUtils.getPriceById(id);

        // Assert return data
        assertEquals(expectedPrice, returnedPrice);
    }
}