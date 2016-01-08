package com.example.chat.api.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Response {

    @Nullable
    private Object mAnswer;

    private RequestResult mRequestResult;

    public Response() {
        mRequestResult = RequestResult.ERROR;
    }

    @NonNull
    public RequestResult getRequestResult() {
        return mRequestResult;
    }

    public Response setRequestResult(RequestResult requestResult) {
        mRequestResult = requestResult;
        return this;
    }

    @android.support.annotation.Nullable
    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mAnswer;
    }

    public Response setAnswer(@android.support.annotation.Nullable Object answer) {
        mAnswer = answer;
        return this;
    }

    public void save(android.content.Context context) {
    }
}
