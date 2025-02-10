package org.example.first_hometask.Objects;

public class UserUniversity {
  private Long id;
  private String universityName;
  private User user;

  public Long getId() {
    return id;
  }

  public String getUniversityName() {
    return universityName;
  }

  public Long getUserId() {
    return user.getId();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUniversityName(String universityName) {
    this.universityName = universityName;
  }
}
