package org.example.first_hometask.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@Schema(name = "User", description = "Модель пользователя")
public class User {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Уникальный идентификатор пользователя")
  private Long id;

  @Getter
  @Setter
  @Size(min = 1, max = 50)
  @Column(name = "first_name", length = 50, nullable = false)
  @Schema(description = "Имя пользователя", example = "Антон")
  private String firstName;

  @Getter
  @Setter
  @Size(min = 1, max = 50)
  @Column(name = "second_name", length = 50, nullable = false)
  @Schema(description = "Фамилия пользователя", example = "Хазин")
  private String secondName;

  @Getter
  @Setter
  @Min(value = 18)
  @Column(name = "age", nullable = false)
  @Schema(description = "Возраст пользователя")
  private Integer age;

  @Getter
  @Setter
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  @Schema(description = "Книги пользователя")
  private List<UserBook> books;

  public User(String firstName, String secondName, Integer age) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.age = age;
    this.books = new ArrayList<>();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
