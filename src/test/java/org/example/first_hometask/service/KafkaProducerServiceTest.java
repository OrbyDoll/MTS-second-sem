package org.example.first_hometask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.first_hometask.Application;
import org.example.first_hometask.model.Action;
import org.example.first_hometask.model.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.KafkaException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
    classes = {Application.class, KafkaProducerService.class},
    properties = {"topic-to-send-message=audit-topic", "spring.flyway.enabled=false"}
)
@Testcontainers
class KafkaProducerServiceTest {

  @Container
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
  private KafkaProducerService kafkaProducerService;
  @Autowired
  private ObjectMapper objectMapper;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  @DisplayName("Тест на удачную посылку сообщения")
  void test1() {
    Message testDtoMessage = new Message();
    assertDoesNotThrow(() -> kafkaProducerService.sendMessage(testDtoMessage));
    outboxScheduler.processOutbox();
    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "audit-group");
    consumer.subscribe(List.of("audit-topic"));

    ConsumerRecords<String, String> records = consumer.poll();
    assertEquals(1, records.count());
    records.iterator().forEachRemaining(
        record -> {
          Message message = null;
          try {
            message = objectMapper.readValue(record.value(), Message.class);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
          assertEquals(testDtoMessage, message);
        }
    );
  }

  @Test
  @DisplayName("Тест на посылку слишком большого сообщения")
  void test2() {
    String largeText = new String(new byte[1_000_001]);
    kafkaProducerService.sendMessage(new Message(1L, Instant.now(), Action.INSERT, largeText));
    assertThrows(KafkaException.class, () -> {
      outboxScheduler.processOutbox();
    });
  }

  static class KafkaTestConsumer {
    private final KafkaConsumer<String, String> consumer;

    public KafkaTestConsumer(String bootstrapServers, String groupId) {
      Properties props = new Properties();

      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

      this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribe(List<String> topics) {
      consumer.subscribe(topics);
    }

    public ConsumerRecords<String, String> poll() {
      return consumer.poll(Duration.ofSeconds(5));
    }
  }
}
