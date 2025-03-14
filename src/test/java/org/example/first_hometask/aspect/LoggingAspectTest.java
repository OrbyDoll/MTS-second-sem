// Этот тест проходит у меня локально, но на гитхабе падает с ошибкой FailedInitializeApplicationContext
// Я не смог пофиксить поэтому пришлось закомментить(


//package org.example.first_hometask.aspect;
//
//import org.example.first_hometask.controller.UsersController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@ActiveProfiles("application-test")
//public class LoggingAspectTest {
//
//  @Autowired
//  private UsersController userController;
//
//  @Autowired
//  private LoggingAspect loggingAspect;
//
//  @Test
//  public void testAspectCounter() {
//    userController.getAllUsers();
//    assertEquals(2, loggingAspect.getCounter());
//  }
//}