package ru.geekbrains.repo;

import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.OrderProduct;
import ru.geekbrains.entities.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopRepoImpl implements ShopRepo {

    private final EntityManager em;

    public ShopRepoImpl(EntityManager em) {
        this.em = em;
    }

    public Set<Product> getProductsByCustomerName(String name) {
        List<OrderProduct> orders = getOrderProductsByCustomerName(name);
        if (orders == null) return null;
        else return orders.stream()
                .map(OrderProduct::getProduct)
                .collect(Collectors.toSet());
    }

    public List<OrderProduct> getOrderProductsByCustomerName(String name) {
        Customer customer = getCustomerByName(name);
        if (customer == null) return null;
        else return customer.getOrderProducts();
    }

    public Customer getCustomerByName(String name) {
        List<Customer> list = em.createNamedQuery("Customer.getCustomerByName", Customer.class)
                .setParameter("name", name)
                .getResultList();
        if (list.isEmpty()) return null;
        else {
            Customer customer = list.get(0);
//            em.refresh(customer);
            return customer;
        }
    }

    public Set<Customer> getCustomersByProductTitle(String title) {
        List<OrderProduct> orders = getOrderProductsByProductTitle(title);
        if (orders == null) return null;
        else return orders.stream()
                .map(OrderProduct::getCustomer)
                .collect(Collectors.toSet());
    }

    public List<OrderProduct> getOrderProductsByProductTitle(String title) {
        Product product = getProductByTitle(title);
        if (product == null) return null;
        else return product.getOrderProducts();
    }

    public Product getProductByTitle(String title) {
        List<Product> list = em.createNamedQuery("Product.getProductByTitle", Product.class)
                .setParameter("title", title)
                .getResultList();
        if (list.isEmpty()) return null;
        else {
            Product product = list.get(0);
//            em.refresh(product);
            return product;
        }
    }

    public void deleteProductByTitle(String title) {
        Product product = getProductByTitle(title);
        if (product != null) {
            em.clear();
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
        }
    }

    public void deleteCustomerByName(String name) {
        Customer customer = getCustomerByName(name);
        if (customer != null) {
            em.clear();
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
        }
    }
}
