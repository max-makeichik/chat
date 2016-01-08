package com.example.chat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ChatMessage implements Parcelable {

  public static final int TYPE_MY_MESSAGE = 0;
  public static final int TYPE_NOT_MY_MESSAGE = 1;
  public static final int TYPE_MY_IMAGE = 2;
  public static final int TYPE_NOT_MY_IMAGE = 3;

  @SerializedName("User")
  private User user;
  @SerializedName("Message")
  private Message message;

  public ChatMessage(User user, Message message) {
    this.user = user;
    this.message = message;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        "user=" + user +
        ", message=" + message +
        '}';
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  protected ChatMessage(Parcel in) {
    user = in.readParcelable(User.class.getClassLoader());
  }

  public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
    @Override
    public ChatMessage createFromParcel(Parcel in) {
      return new ChatMessage(in);
    }

    @Override
    public ChatMessage[] newArray(int size) {
      return new ChatMessage[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(user, flags);
  }
}