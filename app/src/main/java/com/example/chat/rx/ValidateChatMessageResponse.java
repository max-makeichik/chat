package com.example.chat.rx;

import com.example.chat.api.ApiException;
import com.example.chat.api.response.ChatMessageResponse;

import rx.Observable;
import rx.functions.Func1;

public class ValidateChatMessageResponse implements Func1<ChatMessageResponse, Observable<ChatMessageResponse>> {
  @Override
  public Observable<ChatMessageResponse> call(ChatMessageResponse response) {
    if (response.error != null)
      return Observable.error(new ApiException(response.error.errorMessage));
    return Observable.just(response);
  }
}