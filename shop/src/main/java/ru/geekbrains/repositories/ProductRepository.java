package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Product;

@Repository
public interface ProductRepository extends JpaRepositoryImplementation<Product, Long> {

}
