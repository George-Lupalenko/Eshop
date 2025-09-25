package ua.mem4ik.eshop.src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.mem4ik.eshop.src.domain.User;
import ua.mem4ik.eshop.src.domain.enums.Roles;
import ua.mem4ik.eshop.src.repository.UserRepository;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    public boolean saveUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        user.setRoles(new HashSet<>(Collections.singletonList(Roles.CUSTOMER)));

        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        if (StringUtils.hasText(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to E-Shop. Please, visit next link: http://localhost:8080/auth/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailService.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return user;
    }
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userRepository.save(user);

        return true;
    }
}
