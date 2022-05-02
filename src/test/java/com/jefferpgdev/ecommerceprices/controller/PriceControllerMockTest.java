package com.jefferpgdev.ecommerceprices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jefferpgdev.ecommerceprices.controller.dto.PriceRequest;
import com.jefferpgdev.ecommerceprices.controller.dto.PriceResponse;
import com.jefferpgdev.ecommerceprices.entity.Price;
import com.jefferpgdev.ecommerceprices.implement.PriceServiceImpl;
import com.jefferpgdev.ecommerceprices.utils.PriceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerMockTest {

    @MockBean private PriceServiceImpl priceServiceImpl;

    @Autowired private MockMvc mockMvc;

    private static PriceUtils priceUtils;

    @BeforeAll
    static void beforeAll() {
        priceUtils = new PriceUtils();
    }

    private static Stream<Arguments> providePriceRequestData() {
        return priceUtils.setPriceRequestParameters();
    }

    private String getJsonFromObject(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PriceResponse getObjectFromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, PriceResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest(name = "{index} => brandId {0}, productId {1}, date {2}, id result {3} Valid Data")
    @MethodSource("providePriceRequestData")
    void testGetWidgetsSuccess(Integer brandId, Integer productId, LocalDateTime date, Long id) throws Exception {
        // Set up our mocked
        PriceRequest priceRequest = PriceRequest.builder()
                .brandId(brandId)
                .productId(productId)
                .applicationDate(date)
                .build();

        Price price = priceUtils.getPriceById(id);

        PriceResponse priceResponse = PriceResponse.builder()
                .id(price.getId())
                .brandId(price.getBrandId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .applicationDate(priceRequest.getApplicationDate())
                .priceList(price.getProductId())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPrice())
                .currency(price.getCurrency()).build();

        doReturn(price).when(priceServiceImpl).getPrice(brandId, productId, date);

        // Execute the GET request
        String result = mockMvc.perform(get("/rest/v1/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonFromObject(priceRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Assert Equals
        assertEquals(priceResponse, getObjectFromJson(result));
    }
}