package com.jefferpgdev.ecommerceprices.implement;

import com.jefferpgdev.ecommerceprices.entity.Price;
import com.jefferpgdev.ecommerceprices.exception.InvalidDataException;
import com.jefferpgdev.ecommerceprices.repository.PriceRepository;
import com.jefferpgdev.ecommerceprices.utils.PriceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class PriceServiceImplMockTest {

    @Autowired private PriceServiceImpl priceServiceImpl;

    @MockBean private PriceRepository priceRepository;

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
    void invalid_Data_Test(Integer brandId, Integer productId, LocalDateTime date) {
        // Assert validations not valid data
        assertThrows(InvalidDataException.class, () -> priceServiceImpl.getPrice(brandId, productId, date));
    }

    @ParameterizedTest(name = "{index} => brandId {0}, productId {1}, date {2}, id result {3} Valid Data")
    @MethodSource("providePriceRequestData")
    void get_valid_data_price(Integer brandId, Integer productId, LocalDateTime date, Long id) {
        // Set up our mocked service
        Price price = priceUtils.getPriceById(id);

        doReturn(Optional.of(price)).when(priceRepository).findPriceWithQuery(brandId, productId, date);

        // Consume the Service Implementation
        Price returnedPrice = priceServiceImpl.getPrice(brandId, productId, date);

        // Assert return data
        assertEquals(price, returnedPrice);
    }
}