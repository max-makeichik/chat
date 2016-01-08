package com.example.chat.model;

public class Session {

  private String session;

  public Session(String session) {
    this.session = session;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        "session=" + session;
  }

  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
  }
}