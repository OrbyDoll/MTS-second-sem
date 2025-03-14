package org.example.first_hometask.response.user;

import lombok.Data;
import org.example.first_hometask.model.User;

@Data
public class UserGetAllResponse {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer age;

    public UserGetAllResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.age = user.getAge();
    }
}
