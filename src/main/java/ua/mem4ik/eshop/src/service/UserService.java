package ua.mem4ik.eshop.src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.mem4ik.eshop.src.domain.User;
import ua.mem4ik.eshop.src.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        if(user.getUsername() != null && !user.getUsername().equals(userRepository.findByUsername(user.getUsername()).getUsername())) {
            userRepository.save(user);
        }
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
        return null;
    }
}
