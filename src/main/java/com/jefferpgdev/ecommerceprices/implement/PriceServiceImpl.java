package com.jefferpgdev.ecommerceprices.implement;

import com.jefferpgdev.ecommerceprices.entity.Price;
import com.jefferpgdev.ecommerceprices.exception.InvalidDataException;
import com.jefferpgdev.ecommerceprices.repository.PriceRepository;
import com.jefferpgdev.ecommerceprices.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired private PriceRepository priceRepository;

    @Override
    public Price getPrice(Integer brandId, Integer productId, LocalDateTime date) {
        validateBrand(brandId);
        validateProductId(productId);
        validateDate(date);
        Optional<Price> optionalPrice = priceRepository.findPriceWithQuery(brandId, productId, date);
        return optionalPrice.orElseGet(Price::new);
    }

    private void validateBrand(Integer brandId) {
        if (isNull(brandId)) {
            throw new InvalidDataException("brandId is not valid.");
        }
    }

    private void validateProductId(Integer productId) {
        if (isNull(productId) || productId <= 0) {
            throw new InvalidDataException("productId is not valid.");
        }
    }

    private void validateDate(LocalDateTime date) {
        if (isNull(date)) {
            throw new InvalidDataException("date is not valid.");
        }
    }
}
