package ru.geekbrains.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Customer.getCustomerByName", query = "select c from Customer c where c.name=:name"),
        @NamedQuery(name = "Customer.getCustomerByProductTitle", query = "select o.customer from Product p join OrderProduct o on p.id=o.product.id where p.title=:title"),
        @NamedQuery(name = "Customer.getOrderProductsByCustomerName", query = "select o from Customer c join OrderProduct o on c.id=o.customer.id where c.name=:name")
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
