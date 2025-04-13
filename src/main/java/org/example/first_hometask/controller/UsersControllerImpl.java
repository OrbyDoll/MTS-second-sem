package org.example.first_hometask.controller;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.example.first_hometask.model.User;
import org.example.first_hometask.request.user.UserCreateRequest;
import org.example.first_hometask.request.user.UserPatchRequest;
import org.example.first_hometask.request.user.UserPutRequest;
import org.example.first_hometask.response.user.UserGetAllResponse;
import org.example.first_hometask.response.user.UserGetResponse;
import org.example.first_hometask.response.user.UserUpdateResponse;
import org.example.first_hometask.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {
  private final UsersService userService;
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("rateController");

  @Override
  public ResponseEntity<List<UserGetAllResponse>> getAllUsers() {
    return rateLimiter.executeSupplier(() -> {
      List<UserGetAllResponse> users = userService.getAllUsers();
      List<Long> usersIds = users.stream().map(UserGetAllResponse::getId).toList();
      return ResponseEntity.ok()
          .header("userId", usersIds.toString())
          .body(users);
    });
  }

  @Override
  public ResponseEntity<UserGetResponse> getUserById(Long id) {
    return rateLimiter.executeSupplier(() -> {
      UserGetResponse user = userService.getUserById(id);
      return ResponseEntity.ok()
          .header("userId", String.valueOf(user.getId()))
          .body(user);
    });
  }

  @Override
  public ResponseEntity<Long> createUser(UserCreateRequest user) {
    return rateLimiter.executeSupplier(() -> {
      User castedUser = new User(user.getFirstName(), user.getSecondName(), user.getAge());
      Long userId = userService.createUser(castedUser);
      return ResponseEntity.status(HttpStatus.CREATED)
          .header("userId", String.valueOf(userId))
          .body(userId);
    });
  }

  @Override
  public ResponseEntity<UserUpdateResponse> updateUser(Long id, UserPutRequest user) {
    return rateLimiter.executeSupplier(() -> {
      User castedUser = new User(user.getFirstName(), user.getSecondName(), user.getAge());
      UserUpdateResponse response = userService.updateUser(id, castedUser);
      return ResponseEntity.ok()
          .header("userId", String.valueOf(id))
          .body(response);
    });
  }

  @Override
  public ResponseEntity<UserUpdateResponse> patchUser(Long id, UserPatchRequest user) {
    return rateLimiter.executeSupplier(() -> {
      User castedUser = new User(user.getFirstName(), user.getSecondName(), user.getAge());
      UserUpdateResponse response = userService.patchUser(id, castedUser);
      return ResponseEntity.ok()
          .header("userId", String.valueOf(id))
          .body(response);
    });
  }

  @Override
  public ResponseEntity<Void> deleteUser(Long id) {
    return rateLimiter.executeSupplier(() -> {
      userService.deleteUser(id);
      return ResponseEntity.noContent()
          .header("userId", String.valueOf(id))
          .build();
    });
  }
}