package com.jefferpgdev.ecommerceprices.service;

import com.jefferpgdev.ecommerceprices.entity.Price;

import java.time.LocalDateTime;

public interface PriceService {

    Price getPrice(Integer brandId, Integer productId, LocalDateTime date);

}
