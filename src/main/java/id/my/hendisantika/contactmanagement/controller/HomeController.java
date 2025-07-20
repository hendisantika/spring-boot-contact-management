package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.entity.User;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.21
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("title", "Signin - Smart Contact Manager");
        return "signin";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }
}
