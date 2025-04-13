package org.example.first_hometask.repository;

import jakarta.validation.ConstraintViolationException;
import org.example.first_hometask.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {"spring.flyway.enabled=false"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Testcontainers
@ActiveProfiles("application-test-with-containers")
public class UserRepositoryTest {
  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:17")
          .withInitScript("init.sql")
          .withDatabaseName("test database")
          .withUsername("My user");

  static {
    postgresContainer.start();
  }

  @Autowired
  private UsersRepository usersRepository;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  @DisplayName("Тест на сохранение и поиск пользователя по ID")
  public void testSaveAndFindById() {
    User user = new User("test", "user", 25);
    User savedUser = usersRepository.save(user);
    Optional<User> foundUser = usersRepository.findById(savedUser.getId());
    assertTrue(foundUser.isPresent());
    assertEquals(savedUser.getId(), foundUser.get().getId());
    assertEquals("test", foundUser.get().getFirstName());
    assertEquals(25, foundUser.get().getAge());
  }

  @Test
  @DisplayName("Тест на поиск несуществующего пользователя")
  public void testFindByIdAndNotFound() {
    Optional<User> foundUser = usersRepository.findById(999L);
    assertFalse(foundUser.isPresent());
  }

  @Test
  @DisplayName("Тест на удаление пользователя")
  public void testDeleteUser() {
    User user = new User("test", "user", 25);
    User savedUser = usersRepository.save(user);
    usersRepository.delete(savedUser);
    Optional<User> foundUser = usersRepository.findById(savedUser.getId());
    assertFalse(foundUser.isPresent());
  }

  @Test
  @DisplayName("Тест на получение всех пользователей")
  public void testFindAll() {
    User user1 = new User("user", "1", 20);
    User user2 = new User("user", "2", 30);
    usersRepository.save(user1);
    usersRepository.save(user2);
    List<User> users = usersRepository.findAll();
    assertEquals(2, users.size());
    assertEquals("1", users.get(0).getSecondName());
    assertEquals("2", users.get(1).getSecondName());
  }

  @Test
  @DisplayName("Тест на обновление пользователя")
  public void testUpdateUser() {
    User user = new User("test", "user", 25);
    User savedUser = usersRepository.save(user);
    savedUser.setFirstName("updated");
    savedUser.setAge(30);
    User updatedUser = usersRepository.save(savedUser);
    Optional<User> foundUser = usersRepository.findById(updatedUser.getId());
    assertTrue(foundUser.isPresent());
    assertEquals("updated", foundUser.get().getFirstName());
    assertEquals(30, foundUser.get().getAge());
  }

  @Test
  @DisplayName("Тест на валидацию данных пользователя")
  public void testValidation() {
    User user = new User("invalid", "user", 17);
    assertThrows(ConstraintViolationException.class, () -> {
      usersRepository.save(user);
    });
  }
}
