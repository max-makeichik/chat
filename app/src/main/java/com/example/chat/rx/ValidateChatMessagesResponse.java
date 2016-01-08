package com.example.chat.rx;

import com.example.chat.api.ApiException;
import com.example.chat.api.response.ChatMessagesResponse;

import rx.Observable;
import rx.functions.Func1;

public class ValidateChatMessagesResponse implements Func1<ChatMessagesResponse, Observable<ChatMessagesResponse>> {
  @Override
  public Observable<ChatMessagesResponse> call(ChatMessagesResponse response) {
    if (response.error != null)
      return Observable.error(new ApiException(response.error.errorMessage));
    return Observable.just(response);
  }
}