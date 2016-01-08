package com.example.chat.rx;

import android.util.Log;

import com.example.chat.api.response.SessionResponse;

import rx.functions.Func1;

public class MapValidResponseToSession implements Func1<SessionResponse, String> {
  private static final String TAG = "Map ToSession";

  @Override
    public String call(SessionResponse response) {
    Log.d(TAG, "response " + response);
      Log.d(TAG, "(Session) response.response " + (String) response.response);
      return (String) response.response;
    }
}