package org.example.first_hometask.controller;

import org.example.first_hometask.Application;
import org.example.first_hometask.exception.UserNotFoundException;
import org.example.first_hometask.model.User;
import org.example.first_hometask.response.user.UserGetAllResponse;
import org.example.first_hometask.response.user.UserGetResponse;
import org.example.first_hometask.security.SecurityConfig;
import org.example.first_hometask.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
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
@WebMvcTest(UsersController.class)
@ContextConfiguration(classes={Application.class, SecurityConfig.class})
public class UserControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockitoBean
  private UsersService userService;

  @Test
  public void getAllUsersTest() throws Exception {
    List<User> users = List.of(new User("Anton", "Khazin", 18),
        new User( "Vadim", "Sosnin", 19));
    List<UserGetAllResponse> response = new ArrayList<>();
    for (User user : users) {
      response.add(new UserGetAllResponse(user));
    }
    when(userService.getAllUsers()).thenReturn(response);

    mvc.perform(get("/api/users/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value("Anton"))
        .andExpect(jsonPath("$[0].secondName").value("Khazin"))
        .andExpect(jsonPath("$[0].age").value(18))
        .andExpect(jsonPath("$[1].firstName").value("Vadim"))
        .andExpect(jsonPath("$[1].secondName").value("Sosnin"))
        .andExpect(jsonPath("$[1].age").value(19));
  }

  @Test
  @DisplayName("Тест на успешное получение пользователя по ID")
  public void getUserByIdTest1() throws Exception {
    User user = new User("Anton", "Khazin", 18);
    UserGetResponse response = new UserGetResponse(user);
    when(userService.getUserById(1L)).thenReturn(response);
    mvc.perform(get("/api/users/1"))
        .andExpect(status().isOk())
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
    when(userService.getUserById(10L))
        .thenThrow(new UserNotFoundException(10L));
    mvc.perform(get("/api/users/10")).andExpect(status().isNotFound());
  }
}
