package com.example.chat.model;

import java.util.Date;

/**
 * Created by max on 02-Oct-15.
 */
public class ChatMessage {

  public static final int TYPE_MY_MESSAGE = 0;
  public static final int TYPE_NOT_MY_MESSAGE = 1;
  public static final int TYPE_MY_IMAGE = 2;
  public static final int TYPE_NOT_MY_IMAGE = 3;

  private int id;
  private String name;
  private String text;
  private String imageUrl;
  private Date updatedAt;

  public ChatMessage() {
  }

  public ChatMessage(String name, String text, String imageUrl, Date updatedAt) {
    this.name = name;
    this.text = text;
    this.imageUrl = imageUrl;
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        "id=" + id +
        ", text='" + text + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        ", updatedAt=" + updatedAt +
        '}';
  }

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return name;
  }
}