package org.example.first_hometask.controller;

import lombok.RequiredArgsConstructor;
import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserId;
import org.example.first_hometask.request.User.UserCreateRequest;
import org.example.first_hometask.request.User.UserPatchRequest;
import org.example.first_hometask.request.User.UserPutRequest;
import org.example.first_hometask.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {
  private final UsersService userService;

  @Override
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @Override
  public ResponseEntity<User> getUserById(UserId id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @Override
  public ResponseEntity<UserId> createUser(UserCreateRequest user) {
    User castedUser = new User(user.getId(), user.getFirstName(), user.getSecondName(), user.getAge(), user.getBooks());
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(castedUser));
  }

  @Override
  public ResponseEntity<User> updateUser(UserId id, UserPutRequest user) {
    User castedUser = new User(user.getId(), user.getFirstName(), user.getSecondName(), user.getAge(), user.getBooks());
    return ResponseEntity.ok(userService.updateUser(id, castedUser));
  }

  @Override
  public ResponseEntity<User> patchUser(UserId id, UserPatchRequest user) {
    User castedUser = new User(user.getId(), user.getFirstName(), user.getSecondName(), user.getAge(), user.getBooks());
    return ResponseEntity.ok(userService.patchUser(id, castedUser));
  }

  @Override
  public ResponseEntity<Void> deleteUser(UserId id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}