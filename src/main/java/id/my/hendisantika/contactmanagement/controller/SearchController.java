package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.repository.ContactRepository;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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

}
