package com.example.chat.api.response;

import com.example.chat.model.ChatMessage;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatMessagesResponse extends ApiResponse {

  public ChatMessagesResponse(List<ChatMessage> chatMessages) {
    this.chatMessages = chatMessages;
  }

  @SerializedName("messages")
  private List<ChatMessage> chatMessages;

  @Override
  public String toString() {
    return "ChatMessagesResponse{" +
        "chatMessages=" + chatMessages +
        '}';
  }

  public List<ChatMessage> getChatMessages() {
    return chatMessages;
  }
}
