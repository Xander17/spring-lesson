package ru.geekbrains.shopspringboot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.geekbrains.shopspringboot.entities.Product;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private List<Product> data;
    private ProductPaging paging;
}
