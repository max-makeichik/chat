package com.example.chat.api;

public class MessageEnvelope {

  private String text;

  public MessageEnvelope() {
  }

  public MessageEnvelope(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        ", text='" + text + '\'' +
        '}';
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}