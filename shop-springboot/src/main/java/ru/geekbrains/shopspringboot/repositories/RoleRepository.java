package ru.geekbrains.shopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.shopspringboot.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
