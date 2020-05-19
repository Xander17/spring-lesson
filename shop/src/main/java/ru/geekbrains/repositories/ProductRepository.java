package ru.geekbrains.repositories;

import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepository {

    private AtomicInteger lastId;

    private Map<Long, Product> products;

    public ProductRepository() {
        lastId = new AtomicInteger(0);
        products = new ConcurrentHashMap<>();

        add(new Product(-1, "Product1", "Description1", 10));
        add(new Product(-1, "Product2", "Description2", 20));
        add(new Product(-1, "Product3", "Description3", 30));
        add(new Product(-1, "Product4", "Description4", 40));
        add(new Product(-1, "Product5", "Description5", 50));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findById(long id) {
        return products.get(id);
    }

    public void add(Product product) {
        long id = lastId.incrementAndGet();
        product.setId(id);
        products.put(id, product);
    }
}
