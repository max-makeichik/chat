package com.example.chat.rx;

import com.example.chat.api.ApiException;
import com.example.chat.api.response.UserResponse;

import rx.Observable;

public class ValidateUserResponse implements rx.functions.Func1<UserResponse, Observable<UserResponse>> {
  @Override
  public Observable<UserResponse> call(UserResponse response) {
    if (response.error != null)
      return Observable.error(new ApiException(response.error.errorMessage));
    return Observable.just(response);
  }
}