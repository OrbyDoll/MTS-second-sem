package org.example.first_hometask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.first_hometask.model.User;
import org.example.first_hometask.request.user.UserCreateRequest;
import org.example.first_hometask.request.user.UserPatchRequest;
import org.example.first_hometask.request.user.UserPutRequest;
import org.example.first_hometask.response.user.UserGetAllResponse;
import org.example.first_hometask.response.user.UserGetResponse;
import org.example.first_hometask.response.user.UserUpdateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
public interface UsersController {

  @Operation(summary = "Получить всех пользователей")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Все пользователи успешно получены",
        content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = User.class))
        )
      })
  })
  @GetMapping("/")
  ResponseEntity<List<UserGetAllResponse>> getAllUsers();

  @Operation(summary = "Получить пользователя по ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Пользователь успешно получен",
        content = {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = User.class)
        )
      }),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Пользователь не найден",
        content = {@Content})
  })
  @GetMapping("/{id}")
  ResponseEntity<UserGetResponse> getUserById(@PathVariable("id") @Valid Long id);

  @Operation(summary = "Создание пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
        content = {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Long.class)
      )}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
        content = {@Content}),
  })
  @PostMapping("/")
  ResponseEntity<Long> createUser(@Valid @RequestBody UserCreateRequest user);

  @Operation(summary = "Полное обновление данных пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
          content = {@Content(
              mediaType = "application/json",
              schema = @Schema(implementation = User.class)
          )
      }),
      @ApiResponse(responseCode = "404", description = "Пользователь не найден",
        content = {@Content}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
        content = {@Content})
  })
  @PutMapping("/{id}")
  ResponseEntity<UserUpdateResponse> updateUser(@PathVariable("id") @Valid Long id, @Valid @RequestBody UserPutRequest user);

  @Operation(summary = "Частичное обновление данных пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
          content = {@Content(
              mediaType = "application/json",
              schema = @Schema(implementation = User.class)
          )
          }),
      @ApiResponse(responseCode = "404", description = "Пользователь не найден",
          content = {@Content}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content})
  })
  @PatchMapping("/{id}")
  ResponseEntity<UserUpdateResponse> patchUser(@PathVariable("id") @Valid Long id, @Valid @RequestBody UserPatchRequest user);

  @Operation(summary = "Удаление пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Пользователь успешно удален",
          content = {@Content}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content})
  })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteUser(@PathVariable("id") @Valid Long id);
}
