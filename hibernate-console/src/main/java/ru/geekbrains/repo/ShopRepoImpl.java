package ru.geekbrains.repo;

import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.OrderProduct;
import ru.geekbrains.entities.Product;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShopRepoImpl implements ShopRepo {

    private final EntityManager em;

    public ShopRepoImpl(EntityManager em) {
        this.em = em;
    }

    public Set<Customer> getCustomersByProductTitle(String title) {
        return new HashSet<>(em.createNamedQuery("Customer.getCustomerByProductTitle", Customer.class)
                .setParameter("title", title)
                .getResultList());
    }

    public List<OrderProduct> getOrderProductsByCustomerName(String name) {
        return em.createNamedQuery("Customer.getOrderProductsByCustomerName", OrderProduct.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Customer getCustomerByName(String name) {
        List<Customer> list = em.createNamedQuery("Customer.getCustomerByName", Customer.class)
                .setParameter("name", name)
                .getResultList();
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    public Set<Product> getProductsByCustomerName(String name) {
        return new HashSet<>(em.createNamedQuery("Product.getProductByCustomerName", Product.class)
                .setParameter("name", name).getResultList());
    }

    public List<OrderProduct> getOrderProductsByProductTitle(String title) {
        return em.createNamedQuery("Product.getOrderProductsByProductTitle", OrderProduct.class)
                .setParameter("title", title)
                .getResultList();
    }

    public Product getProductByTitle(String title) {
        List<Product> list = em.createNamedQuery("Product.getProductByTitle", Product.class)
                .setParameter("title", title)
                .getResultList();
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    public void deleteProductByTitle(String title) {
        Product product = getProductByTitle(title);
        if (product != null) {
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
        }
    }

    public void deleteCustomerByName(String name) {
        Customer customer = getCustomerByName(name);
        if (customer != null) {
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
        }
    }
}
