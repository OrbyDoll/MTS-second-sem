package org.example.first_hometask.Controller.Implementation;

import lombok.RequiredArgsConstructor;
import org.example.first_hometask.Controller.Interface.UserController;
import org.example.first_hometask.Model.User;
import org.example.first_hometask.Model.UserId;
import org.example.first_hometask.Request.User.UserCreateRequest;
import org.example.first_hometask.Request.User.UserPatchRequest;
import org.example.first_hometask.Request.User.UserPutRequest;
import org.example.first_hometask.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {
  private final UserService userService;

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
    return ResponseEntity.ok(userService.createUser(castedUser));
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