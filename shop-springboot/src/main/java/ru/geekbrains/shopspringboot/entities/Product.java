package ru.geekbrains.shopspringboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Fill the title field")
    private String title;

    @Column(length = 2048)
    @NotBlank(message = "Fill the description field")
    private String description;

    @Column
    @NotNull(message = "Fill the price field")
    @Positive(message = "Price should be a positive")
    private BigDecimal price;
}
