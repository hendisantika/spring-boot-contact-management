package id.my.hendisantika.contactmanagement.repository;

import id.my.hendisantika.contactmanagement.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-contact-management
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/07/25
 * Time: 15.08
 * To change this template use File | Settings | File Templates.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {
//pagination

//	@Query("from Contact as c where c.userr.uid = :uid")
//	public List<Contact> getContactsByUid(@Param("uid")int uid);

    @Query("from Contact as c where c.userr.uid = :uid")
    Page<Contact> getContactsByUid(@Param("uid") int uid, Pageable pageable);
    // Pageable object contains
    // two values
    //currentPage - page
    //contact per page - 3


    @Query("from Contact as c where c.userr.uid = :uid and c.cname like %:keyword%")
    List<Contact> findByUidAndKeyword(@Param("uid") int uid, @Param("keyword") String keyword);
}
