package id.my.hendisantika.contactmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "contacts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String nickname;
    private String work;
    private String email;
    private String phone;
    private String image;
    @Column(length = 100000)
    private String description;

    @ManyToOne
    @JsonIgnore // for search
    private User user;
}
