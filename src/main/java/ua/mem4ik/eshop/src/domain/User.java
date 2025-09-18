package ua.mem4ik.eshop.src.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Enter username")
    private String username;
    @NotBlank(message = "Enter password")
    private String password;
    @Email(message = "Email is not correct")
    @NotBlank(message = "Email can't be empty")
    private String email;
    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Items> items;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name= "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;
}
