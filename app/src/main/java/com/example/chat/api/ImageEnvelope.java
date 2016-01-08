package com.example.chat.api;

import com.google.gson.annotations.SerializedName;

public class ImageEnvelope {

  @SerializedName("image_url")
  private String imageUrl;

  public ImageEnvelope(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "ImageEnvelope{" +
        "imageUrl=" + imageUrl;
  }


}