package ru.geekbrains.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Product.getProductByTitle", query = "select p from Product p where p.title=:title"),
        @NamedQuery(name = "Product.getProductByCustomerName", query = "select o.product from Customer c join OrderProduct o on c.id=o.product.id where c.name=:name"),
        @NamedQuery(name = "Product.getOrderProductsByProductTitle", query = "select o from Product p join OrderProduct o on p.id=o.product.id where p.title=:title")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;
}