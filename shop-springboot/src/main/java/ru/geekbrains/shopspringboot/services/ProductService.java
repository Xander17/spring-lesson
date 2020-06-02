package ru.geekbrains.shopspringboot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.shopspringboot.entities.Product;
import ru.geekbrains.shopspringboot.repositories.ProductRepository;
import ru.geekbrains.shopspringboot.services.filters.ProductFilter;
import ru.geekbrains.shopspringboot.services.filters.ProductSpecification;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(ProductFilter filter, Pageable pageable) {
        return repository.findAll(ProductSpecification.get(filter), pageable);
    }

    @Transactional
    public void save(Product product) {
        repository.save(product);
    }

    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
