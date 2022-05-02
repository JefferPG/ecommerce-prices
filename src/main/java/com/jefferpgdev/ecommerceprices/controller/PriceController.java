package com.jefferpgdev.ecommerceprices.controller;

import com.jefferpgdev.ecommerceprices.controller.dto.PriceRequest;
import com.jefferpgdev.ecommerceprices.controller.dto.PriceResponse;
import com.jefferpgdev.ecommerceprices.entity.Price;
import com.jefferpgdev.ecommerceprices.exception.PriceRequestException;
import com.jefferpgdev.ecommerceprices.implement.PriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/rest/v1/price")
public class PriceController {

    @Autowired private PriceServiceImpl priceServiceImpl;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(@RequestBody PriceRequest priceRequest) {
        validateBrandId(priceRequest.getBrandId());
        validateProductId(priceRequest.getProductId());
        validateApplicationDate(priceRequest.getApplicationDate());
        Price price = priceServiceImpl.getPrice(priceRequest.getBrandId(), priceRequest.getProductId(), priceRequest.getApplicationDate());
        return ResponseEntity.ok(PriceResponse.builder()
                .id(price.getId())
                .brandId(price.getBrandId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .applicationDate(priceRequest.getApplicationDate())
                .priceList(price.getProductId())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build());
    }

    private void validateBrandId(Integer brandId) {
        if (isNull(brandId)) {
            throw new PriceRequestException("brandId is not valid");
        }
    }

    private void validateProductId(Integer productId) {
        if (isNull(productId) || productId <= 0) {
            throw new PriceRequestException("productId is not valid");
        }
    }

    private void validateApplicationDate(LocalDateTime applicationDate) {
        if (isNull(applicationDate)) {
            throw new PriceRequestException("applicationDate is not valid");
        }
    }
}
