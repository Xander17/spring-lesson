package ru.geekbrains.shopspringboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Fill the name field")
    private String name;

    @Column
    @NotBlank(message = "Fill the password field")
    private String password;

    @Transient
    private String confirmPassword;

    @Column
    @NotBlank(message = "Fill the email field")
    @Email(message = "Email must be valid (ex. mail@domain.com)")
    private String email;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
