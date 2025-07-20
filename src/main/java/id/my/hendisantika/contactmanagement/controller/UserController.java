package id.my.hendisantika.contactmanagement.controller;

import id.my.hendisantika.contactmanagement.dto.MessageDTO;
import id.my.hendisantika.contactmanagement.entity.Contact;
import id.my.hendisantika.contactmanagement.entity.User;
import id.my.hendisantika.contactmanagement.repository.ContactRepository;
import id.my.hendisantika.contactmanagement.repository.OrderRepository;
import id.my.hendisantika.contactmanagement.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

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
@Slf4j
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

    @GetMapping("/")
    public String dashboard(Model m, Principal p) {
        m.addAttribute("title", "Dashboard");
        return "user/dashboard";
    }

    @GetMapping("/addcontact")
    public String addContact(Model m, Principal p) {
        m.addAttribute("title", "Add Contact");
        m.addAttribute("contact", new Contact());

        return "user/addcontact";
    }

    @PostMapping("/processcontact")
    public String processcontact(@ModelAttribute("contact") Contact contact,
                                 BindingResult br, Principal p,
                                 HttpSession session,
                                 @RequestParam("cimage") MultipartFile file) // after adding BindingResult we will get the String from cimage field
    {                                                   // always put BindingResult next to @ModelAttribute
        try {
            String userName = p.getName();

            //processing and uploading file
            if (file.isEmpty()) {
                //if the file is empty then try our message
                log.info("File is empty");
                contact.setImage("contact.png");

            } else {
                // upload the file to folder and save the name to contact table
                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                log.info("Image is uploaded successfully");
            }


            User user = userRepository.getUserByUserName(userName);
            contact.setUser(user);
            user.getContacts().add(contact);
            userRepository.save(user);

            log.info("Contact Added Successfully...");

            session.setAttribute("message", new MessageDTO("Your contact is added successfully !!", "success"));

        } catch (Exception e) {
            log.info("ERROR: {}", e.getMessage());
            e.printStackTrace();

            session.setAttribute("message", new MessageDTO("Something went wrong !!", "danger"));

        }

        return "user/addcontact";
    }

    // show contacts handler
    // per page = 3[n]
    // current page = 0[page]
    @GetMapping("/viewcontacts/{page}")
    public String viewContacts(@PathVariable("page") Integer page, Model m, Principal p, HttpSession session) {// here we have use page variable for pagination
        String userName = p.getName();

        User user = userRepository.getUserByUserName(userName);

        Pageable pageable = PageRequest.of(page, 3); // current page and contacts per page-3

        Page<Contact> contacts = contactRepository.getContactsByUid(user.getUid(), pageable);

        m.addAttribute("contacts", contacts);

        m.addAttribute("currentPage", page);

        m.addAttribute("totalPages", contacts.getTotalPages());

        m.addAttribute("title", "View User Contacts");
        return "user/viewcontacts";
    }

    // showing particular contact details
    @RequestMapping("/{cid}/contactdetails")
    public String contact(@PathVariable("cid") Integer cid, Model m, Principal p) {
        log.info("cid: {}", cid);

        Optional<Contact> contOpt = contactRepository.findById(cid);
        Contact contact = contOpt.get();
        //
        String userName = p.getName();
        User user = userRepository.getUserByUserName(userName);

        if (user.getUid() == contact.getUser().getUid()) {
            m.addAttribute("title", contact.getName());
            m.addAttribute("contact", contact);
        }
        return "user/contactdetails";
    }

    // delete contact handler
    @GetMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cid, Model m, Principal p, HttpSession session) throws IOException {
        Optional<Contact> contOpt = contactRepository.findById(cid);
        Contact contact = contOpt.get();

        String userName = p.getName();
        User user = userRepository.getUserByUserName(userName);

        if (user.getUid() == contact.getUser().getUid()) {
            // before deleting contact, delete photo of contact
            //contact.getImage
            //----------
            if (!contact.getImage().equals("contact.png")) {
                File imagefile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(imagefile.getAbsolutePath() + File.separator + contact.getImage());

                Files.delete(path);
            }
            //----------
//		contact.setUserr(null);   //--|   // due to cascade all we are unable to delete thats why we have to null that column before deleting. and after then only it will be able to delete.
//		contRepo.delete(contact); //--|-- use this code when using without orphanRemoval

            user.getContacts().remove(contact);   //--|
            userRepository.save(user);                    //--|-- use this code when using orphanRemoval


            session.setAttribute("message", new MessageDTO("Contact deleted successfully...", "success"));
        }

        return "redirect:/user/viewcontacts/0";
    }

}
