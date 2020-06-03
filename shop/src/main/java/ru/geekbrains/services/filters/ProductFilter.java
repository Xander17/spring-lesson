package ru.geekbrains.services.filters;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFilter {

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String titlePart;
}
