package ru.geekbrains.services.filters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entities.Product;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecification {
    public static Specification<Product> get(ProductFilter filter) {
        return withTitle(filter.getTitlePart())
                .and(withMinPrice(filter.getMinPrice()))
                .and(withMaxPrice(filter.getMaxPrice()));
    }

    private static Specification<Product> withTitle(String title) {
        return (root, query, cb) -> title == null ? null : cb.like(root.get("title"), "%" + title + "%");
    }

    private static Specification<Product> withMinPrice(BigDecimal minPrice) {
        return (root, query, cb) -> minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> withMaxPrice(BigDecimal maxPrice) {
        return (root, query, cb) -> maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
