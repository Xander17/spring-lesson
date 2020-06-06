package ru.geekbrains.shopspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductPaging {
    private Integer page;
    private Integer totalPages;
    private Integer pageSize;
    private Long totalElements;
}
