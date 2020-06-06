package ru.geekbrains.shopspringboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ru.geekbrains.shopspringboot.services.filters.ProductFilter;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {

    private ProductFilter filter;
    private Integer page;
    private Integer pageSize;
}
