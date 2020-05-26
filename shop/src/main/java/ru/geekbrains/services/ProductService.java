package ru.geekbrains.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(BigDecimal min, BigDecimal max, Pageable pageable) {
        if ((min == null && max == null)) return repository.findAll(pageable);
        else if (min != null && max == null) return repository.findAllByPriceGreaterThanEqual(min, pageable);
        else if (min == null && max != null) return repository.findAllByPriceLessThanEqual(max, pageable);
        else if (max.equals(min)) return repository.findAllByPrice(min, pageable);
        else if (max.compareTo(min) > 0) return repository.findAllByPriceBetween(min, max, pageable);
        else return new PageImpl<>(Collections.emptyList());
    }

    @Transactional
    public void add(Product product) {
        repository.save(product);
    }
}
