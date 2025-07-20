package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.entity.User;
import id.my.hendisantika.contactmanagement.repository.ContactRepository;
import id.my.hendisantika.contactmanagement.repository.OrderRepository;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.27
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final ContactRepository contactRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final OrderRepository orderRepository;

    @ModelAttribute
    public void commonData(Model m, Principal p) {
        String userName = p.getName(); // it will give the username(email) of person who is login
        User user = userRepository.getUserByUserName(userName);

        m.addAttribute("user", user);
    }
}
