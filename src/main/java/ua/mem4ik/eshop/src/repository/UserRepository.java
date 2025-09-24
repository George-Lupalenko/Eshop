package ua.mem4ik.eshop.src.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mem4ik.eshop.src.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(@Email(message = "Email is not correct") @NotBlank(message = "Email can't be empty") String email);

    User findByActivationCode(String code);
}
