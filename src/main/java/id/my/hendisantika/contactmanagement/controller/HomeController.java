package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.dto.MessageDTO;
import id.my.hendisantika.contactmanagement.entity.User;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@Slf4j
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

    @PostMapping(value = "/do_signup")
    public String signupUser(@Valid @ModelAttribute("user") User user, BindingResult r, Model model, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, HttpSession session) {
        try {
            if (!agreement) {
                log.info("You have not agreed the Terms and Conditions");
                throw new Exception("You have not agreed the Terms and Conditions");
            }

            if (r.hasErrors()) {
                model.addAttribute("user", user);
                log.info("Error {}", r);
                return "signup";
            }


            if (userRepository.getUserByUserName(user.getEmail()) != null) {
                log.info("Same Email Id is already registered. Try using different Email Id !");
                throw new Exception("Same Email Id is already registered. Try using different Email Id !");
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImage("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));


            log.info("Agreement {}", agreement);
            log.info("Userr {}", user);

            User result = this.userRepository.save(user);

            // it means it will return the data on the same page
            model.addAttribute("user", new User());

            session.setAttribute("message", new MessageDTO("Successfully registered !!", "alert-success"));
            return "signup";


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new MessageDTO("Something went wrong !!" + e.getMessage(), "alert-danger"));
            return "signup";
        }


    }
}
