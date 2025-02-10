package org.example.first_hometask.Objects;

public class UserCourse {
  private Long id;
  private String courseName;
  private User user;

  public Long getId() {
    return id;
  }

  public String getCourseName() {
    return courseName;
  }

  public Long getUserId() {
    return user.getId();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }
}
