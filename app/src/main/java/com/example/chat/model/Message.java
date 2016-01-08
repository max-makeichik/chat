package com.example.chat.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class Message {

  private int id;

  @Nullable
  @Expose
  private int user_id;
  private String text;
  private String imageUrl;

  @Nullable
  @SerializedName("created_at")
  private String createdAt;
  @SerializedName("updated_at")
  private String updatedAt;

  public Message() {
  }

  public Message(String text, String imageUrl, String updatedAt) {
    this.text = text;
    this.imageUrl = imageUrl;
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", text='" + text + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        ", updatedAt=" + updatedAt +
        '}';
  }

  public String getUpdatedAt() {
    return convertToFormat(updatedAt);
  }

  private String convertToFormat(String dateTime) {
    StringTokenizer tk = new StringTokenizer(dateTime);
    String date = tk.nextToken();
    String time = tk.nextToken();

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
    Date dt;
    try {
      dt = sdf.parse(time);
      System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
      return sdfs.format(dt);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public void setUpdatedAt(String updatedAt) {
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

}