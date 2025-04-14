package org.example.first_hometask.service;

import jakarta.validation.ConstraintViolationException;
import org.example.first_hometask.Application;
import org.example.first_hometask.model.OutboxRecord;
import org.example.first_hometask.repository.OutboxRecordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"spring.scheduling.enabled=false"}, classes = {Application.class})
@Testcontainers
public class OutboxSchedulerTest {
  @ServiceConnection
  public static final KafkaContainer KAFKA =
      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @Container
  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:17")
          .withInitScript("init.sql")
          .withDatabaseName("test database")
          .withUsername("My user");

  static {
    postgresContainer.start();
  }

  @Autowired
  private OutboxScheduler outboxScheduler;
  @Autowired
  private OutboxRecordsRepository outboxRepository;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @BeforeEach
  void cleanUp() {
    outboxRepository.deleteAll();
    outboxScheduler.processOutbox();
  }

  @Test
  @DisplayName("После процессинга не должно остаться записей")
  void test1() {
    OutboxRecord record1 = new OutboxRecord("testing 1");
    OutboxRecord record2 = new OutboxRecord("testing 2");
    outboxRepository.save(record1);
    outboxRepository.save(record2);

    assertThat(outboxRepository.findAll()).hasSize(2);
    outboxScheduler.processOutbox();
    assertThat(outboxRepository.findAll()).hasSize(0);
  }

  @Test
  @DisplayName("Ошибка при сохранении невалидной записи")
  void test2() {
    assertThrows(ConstraintViolationException.class,
        () -> outboxRepository.save(new OutboxRecord(null)));
  }
}
