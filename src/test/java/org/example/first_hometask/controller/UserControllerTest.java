package org.example.first_hometask.controller;

import org.example.first_hometask.exception.UserNotFoundException;
import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserId;
import org.example.first_hometask.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserControllerInterface.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockitoBean
  private UserService userService;

  @Test
  public void getAllUsersTest() throws Exception {
    List<User> users = List.of(new User(new UserId(1L), "Anton", "Khazin", 18, new ArrayList<>()),
        new User(new UserId(2L), "Vadim", "Sosnin", 19, new ArrayList<>()));
    when(userService.getAllUsers()).thenReturn(users);

    mvc.perform(get("/api/users/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id.userId").value(1))
        .andExpect(jsonPath("$[0].firstName").value("Anton"))
        .andExpect(jsonPath("$[0].secondName").value("Khazin"))
        .andExpect(jsonPath("$[0].age").value(18))
        .andExpect(jsonPath("$[1].id.userId").value(2))
        .andExpect(jsonPath("$[1].firstName").value("Vadim"))
        .andExpect(jsonPath("$[1].secondName").value("Sosnin"))
        .andExpect(jsonPath("$[1].age").value(19));
  }

  @Test
  @DisplayName("Тест на успешное получение пользователя по ID")
  public void getUserByIdTest1() throws Exception {
    User user = new User(new UserId(1L), "Anton", "Khazin", 18, new ArrayList<>());
    when(userService.getUserById(new UserId(1L))).thenReturn(user);
    mvc.perform(get("/api/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id.userId").value(1))
        .andExpect(jsonPath("$.firstName").value("Anton"))
        .andExpect(jsonPath("$.secondName").value("Khazin"))
        .andExpect(jsonPath("$.age").value(18));
  }

  @Test
  @DisplayName("Тест на неудачное получение пользователя по ID : Bad Request")
  public void getUserByIdTest2() throws Exception {
    mvc.perform(get("/api/users/a")).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Тест на неудачное получение пользователя по ID : User Not Found")
  public void getUserByIdTest3() throws Exception {
    when(userService.getUserById(new UserId(10L)))
        .thenThrow(new UserNotFoundException(new UserId(10L)));
    mvc.perform(get("/api/users/10")).andExpect(status().isNotFound());
  }
}
