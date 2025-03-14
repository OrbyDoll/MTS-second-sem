package org.example.first_hometask.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.first_hometask.model.UserBook;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@Schema(description = "Модель запроса на частичное обновление пользователя")
public class UserPatchRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Schema(description = "Уникальный идентификатор пользователя")
  private Long id;

  @Getter
  @Setter
  @Column(name = "first_name", length = 50, nullable = false)
  @Schema(description = "Имя пользователя", example = "Антон")
  private String firstName;

  @Getter
  @Setter
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
  @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
  @Schema(description = "Книги пользователя")
  private List<UserBook> books;

  public UserPatchRequest(String firstName, String secondName, Integer age) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.age = age;
  }
}
