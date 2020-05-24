package ru.geekbrains.repo;

import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.OrderProduct;
import ru.geekbrains.entities.Product;

import java.util.List;
import java.util.Set;

public interface ShopRepo {
    Set<Product> getProductsByCustomerName(String name);

    List<OrderProduct> getOrderProductsByCustomerName(String name);

    Set<Customer> getCustomersByProductTitle(String name);

    List<OrderProduct> getOrderProductsByProductTitle(String name);

    void deleteProductByTitle(String title);

    void deleteCustomerByName(String name);
}
