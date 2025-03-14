package org.example.first_hometask.repository;

import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

import jakarta.validation.ConstraintViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=false"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Testcontainers
@ActiveProfiles("application-test-with-containers")
public class UserBooksRepositoryTest {
  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:17")
          .withInitScript("init.sql")
          .withDatabaseName("test database")
          .withUsername("My user");

  static {
    postgresContainer.start();
  }

  @Autowired
  private UserBooksRepository userBooksRepository;

  @Autowired
  private UsersRepository usersRepository;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  @DisplayName("Тест на сохранение и поиск книги по ID")
  public void testSaveAndFindById() {
    User testUser = new User("test", "user", 18);
    usersRepository.save(testUser);
    UserBook userBook = new UserBook("Одиннадцать стульев", 1L);
    UserBook savedBook = userBooksRepository.save(userBook);
    Optional<UserBook> foundBook = userBooksRepository.findById(savedBook.getId());
    assertTrue(foundBook.isPresent());
    assertEquals(savedBook.getId(), foundBook.get().getId());
    assertEquals("Одиннадцать стульев", foundBook.get().getTitle());
    assertEquals(1L, foundBook.get().getUserId());
  }

  @Test
  @DisplayName("Тест на поиск несуществующей книги")
  public void testFindByIdAndNotFound() {
    Optional<UserBook> foundBook = userBooksRepository.findById(999L);
    assertFalse(foundBook.isPresent());
  }

  @Test
  @DisplayName("Тест на удаление книги")
  public void testDeleteBook() {
    User testUser = new User("test", "user", 18);
    usersRepository.save(testUser);
    UserBook userBook = new UserBook("Одиннадцать стульев", 1L);
    UserBook savedBook = userBooksRepository.save(userBook);
    userBooksRepository.delete(savedBook);
    Optional<UserBook> foundBook = userBooksRepository.findById(savedBook.getId());
    assertFalse(foundBook.isPresent());
  }

  @Test
  @DisplayName("Тест на получение всех книг")
  public void testFindAll() {
    User testUser1 = new User("test1", "user", 18);
    User testUser2 = new User("test2", "user", 18);
    usersRepository.save(testUser1);
    usersRepository.save(testUser2);
    UserBook book1 = new UserBook("Одиннадцать стульев", 1L);
    UserBook book2 = new UserBook("Двенадцать стульев", 2L);
    userBooksRepository.save(book1);
    userBooksRepository.save(book2);
    List<UserBook> books = userBooksRepository.findAll();
    assertEquals(2, books.size());
    assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Одиннадцать стульев")));
    assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Двенадцать стульев")));
  }

  @Test
  @DisplayName("Тест на обновление книги")
  public void testUpdateBook() {
    User testUser1 = new User("test1", "user", 18);
    User testUser2 = new User("test2", "user", 18);
    usersRepository.save(testUser1);
    usersRepository.save(testUser2);
    UserBook userBook = new UserBook("Одиннадцать стульев", 1L);
    UserBook savedBook = userBooksRepository.save(userBook);
    savedBook.setTitle("Двенадцать стульев");
    savedBook.setUserId(2L);
    UserBook updatedBook = userBooksRepository.save(savedBook);
    Optional<UserBook> foundBook = userBooksRepository.findById(updatedBook.getId());
    assertTrue(foundBook.isPresent());
    assertEquals("Двенадцать стульев", foundBook.get().getTitle());
    assertEquals(2L, foundBook.get().getUserId());
  }

  @Test
  @DisplayName("Тест на валидацию данных книги")
  public void testValidation() {
    User testUser = new User("test", "user", 18);
    usersRepository.save(testUser);
    UserBook userBook = new UserBook("", 1L);
    assertThrows(ConstraintViolationException.class, () -> {
      userBooksRepository.save(userBook);
    });
  }
}