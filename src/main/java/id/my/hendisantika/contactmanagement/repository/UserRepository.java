package id.my.hendisantika.contactmanagement.repository;

import id.my.hendisantika.contactmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.10
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    User getUserByUserName(@Param("email") String email);
}
