package id.my.hendisantika.contactmanagement.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;

    @NotBlank(message = "Name field is required !")
    @Size(min = 2, max = 20, message = "min 2 and max 20 characters are allowed")
    private String username;

    private String password;
    @Column(unique = true)
    private String email;
    private String image;

    @Column(length = 500)
    private String about;
    private String role;
    private boolean enabled;

    //	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="user")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();
}
