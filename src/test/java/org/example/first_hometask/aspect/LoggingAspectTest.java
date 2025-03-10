package org.example.first_hometask.aspect;

import org.example.first_hometask.controller.UsersController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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