package ua.mem4ik.eshop.src.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.mem4ik.eshop.src.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // страница входа и регистрация доступны всем
                        .requestMatchers("/auth/**").permitAll()

                        // просмотр товаров доступен всем (и гостям тоже)
                        .requestMatchers("/products", "/products/**" , "/").permitAll()

                        // создание/редактирование товаров — только SELLER и ADMIN
                        .requestMatchers("/products/add", "/products/edit/**", "/products/delete/**")
                        .hasAnyRole("SELLER", "ADMIN")

                        // корзина (позже) — для CUSTOMER и GUEST
                        //.requestMatchers("/cart/**").hasAnyRole("CUSTOMER", "GUEST")

                        // всё остальное по умолчанию требует авторизации
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                UserService userService,
                                PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}

