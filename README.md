# Contact Management Application

### It is SpringBoot Project with Thymeleaf Frontend. Build in Spring Tool Suite.

## It is a Contact Management Application.

### Application Features:

- Users can manage their contacts
- Users can Add, Delete, Update their contacts.
- Also added forgot password option which uses integrated mail api to send the OTP to the users Email Id (Users who
  forgotted their password) for resetting the password.
- Added Payment method using Razorpay Payment Integration.

### Technology used in this Project:

- i) Thymeleaf,CSS : designing page layout.
- ii) Java : all the logic has been written in java.
- iii) MySQL: MySQL database has been used as database.
- iv) SpringSecurity: SpringSecurity has been used for authentication.
- v) Hibernate: Hibernate ORM is used.
- vi) Email API: Email API is also used for sending OTP to the users who forgotted their password.
- vii) TestContainers: TestContainers is used for integration testing with MySQL.

### Software And Tools Required:

- Java JDK 8+
- Eclipse EE or Spring Tool Suite
- MySQL

### Steps To Import And Run The Project in Eclipse EE

- In Eclipse or Spring Tool Suite
- Click on File
- Select Import
- Select Projects from Git(with smart import) -> Next
- Select Clone URI -> Next
- In URI paste this url: https://github.com/swapnilbamble1438/ContactManager.git
  -> Next
- Now in Local Destination

- proceed -> Next

           Now only select ContactManager/MySpring_Boot_aa17i_Contact_Manager
           -> Finish

- If everything goes right Project will get successfully imported
- Now wait for few seconds for getting things properly loaded

- Now open Project > src/main/resources > open application.properties file,
  inside this file look for

  spring.datasource.url=jdbc:mysql://localhost:3306/springbootnew?serverTimezone=UTC

  here "springbootnew" is the name of the database.

  so

  ## create database name "springbootnew" in MySQL.

  or

  (you can also create the database with different name in MySQL. but the created database
  name in MySQL should match the database name in url in application.properties file.
  so according to created database in MySQL set the database name in url in
  application.properties
  file.
   - also in
   - spring.mail.username= Put Your Gmail Id here
   - spring.mail.password= Put Your 16-digit App password for Gmail here

   - Also do this Changes:

              In UserrController in createorder handler at line no 403
              var client = new RazorpayClient("Enter here your razorpay key_id", "Enter here your razorpay key_secret");
                              And
              In src/main/resources/static/js/script.js at line not 140
              key: "Enter here your razorpay key_id", 


- Now save the changes.)
- And Try to Run the Project

### If you are using Spring Tool Suite

- Right Click On Project > Run As > Spring Boot App
- Now in Browser Type Url: localhost:8080
- Note: In Url put Port according to your application.properties file
- or if port is not mention in application.properties you can check with default port.
- Application will get Open

### If you are using Eclipse EE

- Open Project > open application.properties file >

Now do some changes, Change port number according to your Tomcat Server
and save the file.

- Right Click On Project > Run as > Spring Boot App
- Now in Browser Type Url: localhost:9002
- Note: In Url put Port according to your application.properties file.
- or if port is not mention in application.properties you can check with default port.
- Application will get Open.

### Some Screenshots of this Project:

![Home Page](img/a1.png)
==================================================================================================================================================================
![a2](img/a2.png)
==================================================================================================================================================================
![a3](img/a3.png)
==================================================================================================================================================================
![a4](img/a4.png)
==================================================================================================================================================================
![a5](img/a5.png)
==================================================================================================================================================================
![a6](img/a6.png)
==================================================================================================================================================================
![a7](img/a7.png)
==================================================================================================================================================================
![a8](img/a8.png)
==================================================================================================================================================================
![a9](img/a9.png)
==================================================================================================================================================================
![a10](img/a10.png)
==================================================================================================================================================================
![a11](img/a11.png)
==================================================================================================================================================================
![a12](img/a12.png)
==================================================================================================================================================================
![a13](img/a13.png)
==================================================================================================================================================================
![a14](img/a14.png)
==================================================================================================================================================================

### Screenshots of Payment using Razorpay Payment Integration

![a15](img/a15.png)
==================================================================================================================================================================
![a16](img/a16.png)
==================================================================================================================================================================
![a17](img/a17.png)
==================================================================================================================================================================
![a18](img/a18.png)
==================================================================================================================================================================
![a19](img/a19.png)
==================================================================================================================================================================

## Integration Testing with TestContainers

This project uses TestContainers for integration testing with MySQL. TestContainers is a Java library that provides
lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker
container.

### Test Setup

The integration tests use a MySQL 8.0 TestContainer that matches the version used in the application. The test container
is configured in the `UserRepositoryIntegrationTest` class:

```java

@SpringBootTest
@Testcontainers
public class UserRepositoryIntegrationTest {

    // Define MySQL container with the same version as in the application
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    // Configure Spring Boot to use the TestContainer
    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }
}
```

### Running Tests

To run the integration tests:

```bash
./mvnw test
```

The tests will automatically start the MySQL container, run the tests, and then stop and remove the container.


