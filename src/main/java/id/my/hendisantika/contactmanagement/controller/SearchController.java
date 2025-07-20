package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.entity.Contact;
import id.my.hendisantika.contactmanagement.entity.User;
import id.my.hendisantika.contactmanagement.repository.ContactRepository;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.26
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final UserRepository userRepository;

    private final ContactRepository contactRepository;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> search(@PathVariable("keyword") String keyword, Principal p) {
        User user = userRepository.getUserByUserName(p.getName());
        List<Contact> contacts = contactRepository.findByUidAndKeyword(user.getUid(), keyword);
        return ResponseEntity.ok(contacts);
    }
}
