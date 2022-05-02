package com.jefferpgdev.ecommerceprices.repository;

import com.jefferpgdev.ecommerceprices.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query(value = "SELECT ID, BRAND_ID, PRODUCT_ID, START_DATE, END_DATE, PRICE_LIST, PRICE, PRIORITY, CURR FROM PRICES WHERE BRAND_ID=:brandId AND PRODUCT_ID=:productId AND :date BETWEEN START_DATE AND END_DATE ORDER BY PRIORITY DESC LIMIT 1", nativeQuery = true)
    Optional<Price> findPriceWithQuery(
            @Param("brandId") Integer brandId,
            @Param("productId") Integer productId,
            @Param("date") LocalDateTime date);

}
