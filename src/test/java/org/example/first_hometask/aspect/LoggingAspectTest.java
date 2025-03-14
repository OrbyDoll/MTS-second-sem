package org.example.first_hometask.aspect;

import org.example.first_hometask.Application;
import org.example.first_hometask.controller.UsersController;
import org.example.first_hometask.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {Application.class, SecurityConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("application-test")
public class LoggingAspectTest {

  @Autowired
  private UsersController userController;

  @Autowired
  private LoggingAspect loggingAspect;

  @Test
  public void testAspectCounter() {
    userController.getAllUsers();
    assertEquals(2, loggingAspect.getCounter());
  }
}