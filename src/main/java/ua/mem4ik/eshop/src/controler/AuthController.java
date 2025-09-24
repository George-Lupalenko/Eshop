package ua.mem4ik.eshop.src.controler;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.mem4ik.eshop.src.domain.User;
import ua.mem4ik.eshop.src.service.UserService;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
public class AuthController {
        @Autowired
        UserService userService;
        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @GetMapping("/registration")
        public String registration(Model model) {
            model.addAttribute("user", new User()); // иначе будет ошибка в шаблоне
            return "registration";
        }

        @PostMapping("/registration")
        public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
            if (user.getPassword() != null && !user.getPassword().equals(user.getPasswordConfirm())) {
                model.addAttribute("PasswordError", "Passwords do not match");
            }
            if (user.getPassword() != null && user.getPassword().isEmpty()) {
                model.addAttribute("PasswordError", "You should enter password");
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

            return "redirect:/auth/login"; // поправил редирект
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
