package com.example.chat.api.response;

import com.example.chat.model.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse extends ApiResponse {

  public UserResponse(User user) {
    this.user = user;
  }

  @SerializedName("User")
  private User user;

  public User getUser() {
    return user;
  }

  @Override
  public String toString() {
    return "UserResponse{" +
        "user='" + user + '\'' +
        '}';
  }
}
