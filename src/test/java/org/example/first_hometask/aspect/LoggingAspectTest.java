package org.example.first_hometask.aspect;

import org.example.first_hometask.Application;
import org.example.first_hometask.config.SecurityConfig;
import org.example.first_hometask.controller.UsersController;
import org.example.first_hometask.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {Application.class, SecurityConfig.class, KafkaProducerService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties =
    {"spring.flyway.enabled=false", "topic-to-send-message=audit-topic"})
@Testcontainers
@ActiveProfiles("application-test")
public class LoggingAspectTest {

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA =
      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:17")
          .withInitScript("init.sql")
          .withDatabaseName("test database")
          .withUsername("My user");

  static {
    postgresContainer.start();
  }

  @Autowired
  private UsersController userController;
  @Autowired
  private LoggingAspect loggingAspect;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  public void testAspectCounter() {
    userController.getAllUsers();
    assertEquals(2, loggingAspect.getCounter());
  }
}