package ru.geekbrains.shopspringboot.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import ru.geekbrains.shopspringboot.entities.Product;

@Repository
public interface ProductRepository extends JpaRepositoryImplementation<Product, Long> {

}
