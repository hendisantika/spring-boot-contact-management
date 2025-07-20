package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.dto.MessageDTO;
import id.my.hendisantika.contactmanagement.entity.Email;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import id.my.hendisantika.contactmanagement.service.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@Slf4j
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

    @PostMapping("/enterotp")
    public String sendOtp(@RequestParam("mail") String mail, HttpSession session) {
        if (userRepository.getUserByUserName(mail) != null) {
            //generating otp of 4 digit
            int otp = random.nextInt((9999 - 100) + 1) + 10;
//		String message = "Your OTP is: "+ otp;
            String message = "<div style='border:1px solid #e2e2e2; padding:20px'>"
                    + "<h2>"
                    + "Your OTP is: "
                    + "<b>" + otp
                    + "</b>"
                    + "</h2>"
                    + "</div>";
            log.info("==========================================================================================================================================");
            log.info("OTP: {}", otp);
            log.info("==========================================================================================================================================");


            //write code for sending otp to email...

            Email email = new Email();
            email.setTo(mail);
            email.setMessage(message);
            email.setSubject("Please Verify OTP");
            emailService.sendEmail(email);

            // sending otp and email for verifying
            session.setAttribute("myotp", otp);
            session.setAttribute("mail", mail);
            return "enterotp";
        } else {
            session.setAttribute("message", new MessageDTO("Not a registered Email Id, Please enter correct EmailId !!", "danger"));
            return "forgot";
        }
    }

}
