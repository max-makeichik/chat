package com.example.chat.api.response;

public class SessionResponse extends ApiResponse {

  public SessionResponse(String session) {
    this.session = session;
  }

  public String session;

  public String getSession() {
    return session;
  }

  @Override
  public String toString() {
    return "SessionResponse{" +
        "session='" + session + '\'' +
        '}';
  }
}
