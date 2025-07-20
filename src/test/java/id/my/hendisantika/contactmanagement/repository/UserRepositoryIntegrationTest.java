package id.my.hendisantika.contactmanagement.repository;

import id.my.hendisantika.contactmanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for UserRepository using MySQL TestContainer.
 * This test ensures that the UserRepository works correctly with a MySQL database.
 */
@SpringBootTest
@Testcontainers
public class UserRepositoryIntegrationTest {

    // Define MySQL container with the same version as in the application
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");
    @Autowired
    private UserRepository userRepository;

    // Configure Spring Boot to use the TestContainer
    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void testSaveAndFindUser() {
        // Create a test user
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("USER");
        user.setEnabled(true);
        user.setAbout("Test user for integration testing");

        // Save the user
        User savedUser = userRepository.save(user);

        // Verify the user was saved with an ID
        assertNotNull(savedUser.getUid());

        // Find the user by ID
        Optional<User> foundUser = userRepository.findById(savedUser.getUid());
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());

        // Test the custom query method
        User userByEmail = userRepository.getUserByUserName("test@example.com");
        assertNotNull(userByEmail);
        assertEquals(savedUser.getUid(), userByEmail.getUid());
    }
}