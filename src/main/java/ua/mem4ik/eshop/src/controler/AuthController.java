package ua.mem4ik.eshop.src.controler;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.mem4ik.eshop.src.domain.User;
import ua.mem4ik.eshop.src.service.GuestService;
import ua.mem4ik.eshop.src.service.UserService;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
public class AuthController {
        @Autowired
        UserService userService;
        @Autowired
        GuestService guestService;
        @GetMapping("/login")
        public String login() {
            return "login";
        }
        @GetMapping("/registration")
        public String registration(Model model) {
            model.addAttribute("user", new User());
            return "registration";
        }

        @GetMapping("/guest")
        public String guestLogin(HttpServletResponse response) {
            String guestToken = guestService.generateGuestToken();

            ResponseCookie cookie = ResponseCookie.from("GUEST_TOKEN", guestToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());

            return "redirect:/";
        }
        @PostMapping("/registration")
        public String addUser(@Valid User user,
                              BindingResult bindingResult,
                              Model model) {

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                bindingResult.rejectValue("password", "error.user", "You should enter password");
            }

            if (user.getPassword() != null &&
                    !user.getPassword().equals(user.getPasswordConfirm())) {
                bindingResult.rejectValue("passwordConfirm", "error.user", "Passwords do not match");
            }

            if (bindingResult.hasErrors()) {
                Map<String, String> errors = getErrors(bindingResult);
                model.mergeAttributes(errors);
                return "registration";
            }

            if (!userService.saveUser(user)) {
                model.addAttribute("usernameError", "User exist!");
                return "registration";
            }

            return "redirect:/auth/login";
        }

        @GetMapping("/activate/{code}")
        public String activate(Model model, @PathVariable String code) {
            boolean isActivated = userService.activateUser(code);

            if (isActivated) {
                model.addAttribute("message", "User successfully activated");
            } else {
                model.addAttribute("message", "Activation code is not found!");
            }

            return "login";
        }
        private Map<String,String> getErrors(BindingResult bindingResult) {
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(fieldError -> fieldError.getField() + "Error", FieldError::getDefaultMessage);
            return bindingResult.getFieldErrors().stream().collect(collector);
        }
}
