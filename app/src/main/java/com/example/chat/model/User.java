package com.example.chat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Max on 13.09.2015.
 */
public class User implements Parcelable {

  private int id;
  @SerializedName("nickname")
  private String name;
  @SerializedName("avatar_image")
  private String avatarUrl;

  public User(int id, String name, String avatarUrl) {
    this.id = id;
    this.name = name;
    this.avatarUrl = avatarUrl;
  }

  protected User(Parcel in) {
    id = in.readInt();
    name = in.readString();
    avatarUrl = in.readString();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel in) {
      return new User(in);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        '}';
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeString(name);
    dest.writeString(avatarUrl);
  }
}