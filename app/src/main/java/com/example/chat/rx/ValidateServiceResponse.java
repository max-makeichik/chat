package com.example.chat.rx;

import com.example.chat.api.ApiException;
import com.example.chat.api.response.SessionResponse;

import rx.Observable;

public class ValidateServiceResponse implements rx.functions.Func1<SessionResponse, Observable<SessionResponse>> {
  @Override
  public Observable<SessionResponse> call(SessionResponse response) {
    if (response.error != null)
      return Observable.error(new ApiException(response.error.errorMessage));
    return Observable.just(response);
  }
}