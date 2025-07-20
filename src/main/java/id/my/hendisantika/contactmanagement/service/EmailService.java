package id.my.hendisantika.contactmanagement.service;

import id.my.hendisantika.contactmanagement.entity.Email;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.06
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(Email email) {
        try {
            MimeMessage mmessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mhelper = new MimeMessageHelper(mmessage);

            mhelper.setFrom("ContactManager");
            mhelper.setTo(email.getTo());
            mhelper.setSubject(email.getSubject());
            mhelper.setText(email.getMessage());
            mmessage.setContent(email.getMessage(), "text/html");
            javaMailSender.send(mmessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
