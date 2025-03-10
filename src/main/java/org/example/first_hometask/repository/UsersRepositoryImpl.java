package org.example.first_hometask.repository;

import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
  private final Map<UserId, User> users = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private WebClient webClient;

  @Override
  public List<User> findAll() {
    String randomUrl = "https://ya.ru/search/?text=rickroll";
    String response = restTemplate.getForObject(randomUrl, String.class);
    System.out.println("Ответ от RestTemplate: " + response);
    return new ArrayList<>(users.values());
  }

  @Override
  public Optional<User> findById(UserId userId) {
    String randomUrl = "https://ya.ru/search/?text=rickroll";
    String response = webClient.get()
        .uri(randomUrl)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    System.out.println("Ответ от WebClient: " + response);
    return Optional.ofNullable(users.get(userId));
  }

  @Override
  public UserId create(User user) {
    UserId id = new UserId(idCounter.getAndIncrement());
    user.setId(id);
    user.setBooks(new ArrayList<>());
    users.put(user.getId(), user);
    return id;
  }

  @Override
  public void deleteById(UserId userId) {
    users.remove(userId);
  }
}

