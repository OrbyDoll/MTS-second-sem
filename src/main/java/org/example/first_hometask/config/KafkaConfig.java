package org.example.first_hometask.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${topic-to-send-message}")
  private String topicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put("bootstrap.servers", bootstrapServers);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic auditTopic() {
    return new NewTopic(topicName, 1, (short) 1);
  }
}