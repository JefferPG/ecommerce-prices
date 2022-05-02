package com.jefferpgdev.ecommerceprices.repository;

import com.jefferpgdev.ecommerceprices.entity.Price;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired private PriceRepository priceRepository;


    @ParameterizedTest
    @CsvSource(value = {
            "1,35455,2020-06-14T10:00:00"
    })
    void validationCorrectDataSaveProject(Integer brandId, Integer productId, LocalDateTime date) {
        Optional<Price> price = priceRepository.findPriceWithQuery(brandId, productId, date);
        assertTrue(price.isPresent());
    }
}