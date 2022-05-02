package com.jefferpgdev.ecommerceprices.utils;

import com.jefferpgdev.ecommerceprices.entity.Price;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PriceUtils {

    private static List<Price> priceList;

    public PriceUtils() {
        if (priceList == null || priceList.isEmpty()) {
            priceList = new ArrayList<>(Arrays.asList(
                    Price.builder().id(1L).brandId(1).startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0)).endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59)).priceList(1).productId(35455).priority(0).price(35.50D).currency("EUR").build(),
                    Price.builder().id(2L).brandId(1).startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0)).endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0)).priceList(2).productId(35455).priority(1).price(25.45D).currency("EUR").build(),
                    Price.builder().id(3L).brandId(1).startDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0)).endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0)).priceList(3).productId(35455).priority(1).price(30.50D).currency("EUR").build(),
                    Price.builder().id(4L).brandId(1).startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0)).endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59)).priceList(4).productId(35455).priority(1).price(38.95D).currency("EUR").build(),
                    Price.builder().id(5L).brandId(1).startDate(LocalDateTime.of(2021, 1, 1, 16, 0, 0)).endDate(LocalDateTime.of(2021, 1, 14, 23, 59, 59)).priceList(4).productId(35455).priority(2).price(45.75D).currency("EUR").build()
            ));
        }
    }

    public static Stream<Arguments> setPriceRequestParameters() {
        return Stream.of(
                Arguments.of(1, 35455, LocalDateTime.of(2020,6,14,10,0,0), 1L),
                Arguments.of(1, 35455, LocalDateTime.of(2020,6,14,16,0,0), 2L),
                Arguments.of(1, 35455, LocalDateTime.of(2020,6,14,21,0,0), 1L),
                Arguments.of(1, 35455, LocalDateTime.of(2020,6,15,10,0,0), 3L),
                Arguments.of(1, 35455, LocalDateTime.of(2020,6,16,16,0,0), 4L)
        );
    }

    public static Price getPriceById(Long id) {
        return priceList.stream().filter(p -> p.getId() == id).findFirst().orElseGet(Price::new);
    }
}
