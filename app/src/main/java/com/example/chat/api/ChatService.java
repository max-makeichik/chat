package com.example.chat.api;

import android.media.Image;
import android.support.annotation.Nullable;

import com.example.chat.api.response.ChatMessageResponse;
import com.example.chat.api.response.ChatMessagesResponse;
import com.example.chat.api.response.SessionResponse;
import com.example.chat.api.response.UserResponse;
import com.example.chat.model.Session;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface ChatService {

  @POST("/signup")
  Observable<SessionResponse> signup();

  @POST("/session")
  Observable<UserResponse> updateSession(@Body Session session);

  @GET("/messages")
  Observable<ChatMessagesResponse> getMessages(@Query("session") String session, @Nullable @QueryMap Map<String, Object> options);

  @POST("/messages/message")
  Observable<ChatMessageResponse> postMessage(@Body ChatMessageEnvelope chatMessageEnvelope);

  @POST("/image")
  Observable<String> postImage(@Body Image image);

}
