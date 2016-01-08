package com.example.chat.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

public class ApiFactory {

  private static final String BASE_URL = "http://52.192.101.131";

  private static final int TIMEOUT = 60;
  private static final int WRITE_TIMEOUT = 120;
  private static final int CONNECT_TIMEOUT = 10;

  private static final OkHttpClient CLIENT = new OkHttpClient();

  static {
    CLIENT.setConnectTimeout(CONNECT_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS);
    CLIENT.setWriteTimeout(WRITE_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS);
    CLIENT.setReadTimeout(TIMEOUT, java.util.concurrent.TimeUnit.SECONDS);

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    CLIENT.interceptors().add(logging);  // <-- this is the important line!

  }

  @NonNull
  public static ChatService getChatService() {
    return getRetrofit().create(ChatService.class);
  }

  @NonNull
  private static retrofit.Retrofit getRetrofit() {
    return new retrofit.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(retrofit.GsonConverterFactory.create())
        .addCallAdapterFactory(retrofit.RxJavaCallAdapterFactory.create())
        .client(CLIENT)
        .build();
  }

}
