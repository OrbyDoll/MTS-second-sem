package org.example.first_hometask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.first_hometask.model.Message;
import org.example.first_hometask.model.OutboxRecord;
import org.example.first_hometask.repository.OutboxRecordsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
  private final OutboxRecordsRepository outboxRecordsRepository;
  private final ObjectMapper objectMapper;
  private final String topic;
  private final OutboxScheduler outboxScheduler;

  public KafkaProducerService(OutboxRecordsRepository outboxRecordsRepository,
                              ObjectMapper objectMapper,
                              @Value("${topic-to-send-message}") String topic, OutboxScheduler outboxScheduler) {
    this.outboxRecordsRepository = outboxRecordsRepository;
    this.objectMapper = objectMapper;
    this.topic = topic;
    this.outboxScheduler = outboxScheduler;
  }

  @SneakyThrows
  public void sendMessage(Message message) {
    String transferData = objectMapper.writeValueAsString(message);
    outboxRecordsRepository.save(new OutboxRecord(transferData));
  }
}

