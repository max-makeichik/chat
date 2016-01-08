package com.example.chat.api;

public class ChatMessageEnvelope {

  private String session;
  private MessageEnvelope message;

  public ChatMessageEnvelope(String session, MessageEnvelope messageEnvelope) {
    this.session = session;
    this.message = messageEnvelope;
  }

}