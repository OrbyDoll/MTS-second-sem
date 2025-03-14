package org.example.first_hometask.e2e;

import org.example.first_hometask.Application;
import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.request.book.BookPatchRequest;
import org.example.first_hometask.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties =
    {"spring.flyway.enabled=false"})
@ContextConfiguration(classes = {Application.class, SecurityConfig.class})
@Testcontainers
@ActiveProfiles("test")
public class EndToEndTest {
  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:17")
          .withInitScript("init.sql")
          .withDatabaseName("test database")
          .withUsername("My user");

  static {
    postgresContainer.start();
  }

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  @DisplayName("Тест всей логики приложения")
  public void E2ETest() {
    User user1 = new User("Anton", "Khazin", 18);
    User user2 = new User("Vadim", "Sosnin", 19);
    ResponseEntity<Long> createUserResponse1 =
        restTemplate.postForEntity("http://localhost:" + port + "/api/users/", user1, Long.class);
    ResponseEntity<Long> createUserResponse2 =
        restTemplate.postForEntity("http://localhost:" + port + "/api/users/", user2, Long.class);
    assertEquals(HttpStatus.CREATED, createUserResponse1.getStatusCode());
    assertEquals(HttpStatus.CREATED, createUserResponse2.getStatusCode());
    assertEquals(1L, createUserResponse1.getBody());
    assertEquals(2L, createUserResponse2.getBody());

    ResponseEntity<User> getUserResponse =
        restTemplate.getForEntity("http://localhost:" + port + "/api/users/1", User.class);
    assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());
    assertEquals(user1.getFirstName(), getUserResponse.getBody().getFirstName());
    assertEquals(user1.getSecondName(), getUserResponse.getBody().getSecondName());
    assertEquals(user1.getAge(), getUserResponse.getBody().getAge());

    UserBook book = new UserBook("Duna. Part 2", 1L);
    ResponseEntity<Long> createBookResponse =
        restTemplate.postForEntity("http://localhost:" + port + "/api/books/", book, Long.class);
    assertEquals(HttpStatus.CREATED, createBookResponse.getStatusCode());
    assertEquals(1L, createBookResponse.getBody());

    ResponseEntity<User> getUser1DataResponse =
        restTemplate.getForEntity("http://localhost:" + port + "/api/users/1", User.class);
    assertEquals(HttpStatus.OK, getUser1DataResponse.getStatusCode());
    UserBook receivedBook1 = getUser1DataResponse.getBody().getBooks().get(0);
    assertEquals(book.getTitle(), receivedBook1.getTitle());

    BookPatchRequest newBook = new BookPatchRequest(null, null, 2L);
    UserBook updatedBook = new UserBook("Duna. Part 2", 2L);
    UserBook patchBookResponse =
        restTemplate.patchForObject("http://localhost:" + port + "/api/books/1", newBook,
            UserBook.class);
    assertEquals(patchBookResponse.getTitle(), updatedBook.getTitle());
    ResponseEntity<User> getUser2DataResponse =
        restTemplate.getForEntity("http://localhost:" + port + "/api/users/2", User.class);
    assertEquals(HttpStatus.OK, getUser2DataResponse.getStatusCode());
    UserBook receivedBook2 = getUser2DataResponse.getBody().getBooks().get(0);
    assertEquals(updatedBook.getTitle(), receivedBook2.getTitle());

    restTemplate.delete("http://localhost:" + port + "/api/books/1");
    getUser2DataResponse =
        restTemplate.getForEntity("http://localhost:" + port + "/api/users/2", User.class);
    assertEquals(new ArrayList<>(), getUser2DataResponse.getBody().getBooks());
  }
}
