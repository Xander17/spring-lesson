package ru.geekbrains.shopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.shopspringboot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
