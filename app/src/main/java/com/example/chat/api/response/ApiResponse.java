package com.example.chat.api.response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
  public T response;
  public ApiError error;

  public static class ApiError {

    @SerializedName("message")
    public String errorMessage;

  }
}
