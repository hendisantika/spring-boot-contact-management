package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.repository.UserRepository;
import id.my.hendisantika.contactmanagement.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.16
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
public class ForgotController {

    private final EmailService emailService;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // for generating otp
    SecureRandom random = new SecureRandom();

    @GetMapping("/forgot")
    public String forgot() {
        return "forgot";
    }
}
