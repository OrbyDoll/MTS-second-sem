package org.example.first_hometask.withcontainer;

import org.example.first_hometask.Application;
import org.example.first_hometask.security.SecurityConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={Application.class, SecurityConfig.class})
@ActiveProfiles("test-with-container")
@Testcontainers
public class ApplicationWithContainerTest {
  private static final Logger log = LoggerFactory.getLogger(ApplicationWithContainerTest.class);

  @Container
  public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("whatever")
      .withInitScript("init.sql");

  @BeforeAll
  static void setUp() {
    log.info("PostgreSQL контейнер хост: {}", postgres.getHost() + ":" + postgres.getFirstMappedPort());
    log.info("PostgreSQL URL соединения: {}", postgres.getJdbcUrl());
  }

  @Test
  public void testDatabaseConnection() {
    assertTrue(postgres.isRunning());
  }
}
