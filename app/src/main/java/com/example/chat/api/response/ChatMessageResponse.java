package com.example.chat.api.response;

import com.example.chat.model.Message;
import com.google.gson.annotations.SerializedName;

public class ChatMessageResponse extends ApiResponse {

  public ChatMessageResponse(Message message) {
    this.message = message;
  }

  @SerializedName("Message")
  private Message message;

  @Override
  public String toString() {
    return "ChatMessageResponse{" +
        "message=" + message +
        '}';
  }

  public Message getMessage() {
    return message;
  }
}
