package com.example.chat;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.chat.api.ApiFactory;
import com.example.chat.api.ChatMessageEnvelope;
import com.example.chat.api.ChatService;
import com.example.chat.api.MessageEnvelope;
import com.example.chat.api.response.ChatMessageResponse;
import com.example.chat.api.response.ChatMessagesResponse;
import com.example.chat.api.response.SessionResponse;
import com.example.chat.api.response.UserResponse;
import com.example.chat.fragments.ChatFragment;
import com.example.chat.model.ChatMessage;
import com.example.chat.model.Session;
import com.example.chat.model.User;
import com.example.chat.rx.ValidateChatMessageResponse;
import com.example.chat.rx.ValidateChatMessagesResponse;
import com.example.chat.rx.ValidateServiceResponse;
import com.example.chat.rx.ValidateUserResponse;
import com.example.chat.utils.Utils;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.DrawerBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatActivity extends AppCompatActivity {

  private static final String TAG_CHAT_FRAGMENT = "CHAT_FRAGMENT";
  public static final String KEY_SESSION = "session";
  private static final String KEY_USER = "user";
  private String session;
  private User user;
  private Context context = this;
  private Gson gson;
  private ChatService service;

  @Bind(R.id.app_bar)
  Toolbar toolbar;
  @Bind(R.id.toolbar_title)
  TextView toolbarTitle;

  private static final String TAG = "CHAT_ACTIVITY";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_chat);
    ButterKnife.bind(this);
    gson = new Gson();
    service = ApiFactory.getChatService();

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    new DrawerBuilder().withActivity(this).withHeaderPadding(false)
        .withToolbar(toolbar).build(); // navigation drawer

    if (savedInstanceState != null) {
      session = savedInstanceState.getString(KEY_SESSION);
      user = savedInstanceState.getParcelable(KEY_USER);
      return;
    }
    replaceFragment(new ChatFragment());
    if (Utils.isNetworkConnected(this)) {
      session = Utils.getSession(context);
      if (session == null)
        signUp();
      else
        updateSession();
    } else {
      Toast.makeText(this, getString(R.string.error_no_connection), Toast.LENGTH_SHORT).show();
    }

  }

  private void signUp() {
    ChatService service = ApiFactory.getChatService();

    AppObservable.bindActivity(this, service.signup())
        .flatMap(new ValidateServiceResponse())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(Throwable::printStackTrace)
        .subscribe(new Subscriber<SessionResponse>() {
          @Override
          public final void onCompleted() {
          }

          @Override
          public final void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
          }

          @Override
          public final void onNext(SessionResponse sessionResponse) {
            Log.d(TAG, "signedUp: sessionResponse " + sessionResponse.getSession());
            Utils.saveSession(context, sessionResponse.getSession());
            session = sessionResponse.getSession();
            updateSession();
          }
        });
  }

  private void updateSession() {
    AppObservable.bindActivity(this, service.updateSession(new Session(session)))
        .flatMap(new ValidateUserResponse())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(Throwable::printStackTrace)
        .subscribe(new Subscriber<UserResponse>() {
          @Override
          public final void onCompleted() {
          }

          @Override
          public final void onError(Throwable e) {
            Log.e("update session", e.getMessage());
          }

          @Override
          public final void onNext(UserResponse userResponse) {
            Log.d("update session", "userResponse " + userResponse);
            setUser(userResponse.getUser());
            getMessages();
            if(getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT) != null)
              ((ChatFragment) getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT)).onUserIdReceived(userResponse.getUser().getId());
          }
        });
  }

  private void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void replaceFragment(Fragment fragment) {
    getFragmentManager().beginTransaction()
        .replace(R.id.container, fragment, TAG_CHAT_FRAGMENT)
        .commit();
  }

  public void getMessages() {
    if (session == null || user == null)
      return;
    AppObservable.bindActivity(this, service.getMessages(session, null))
        .flatMap(new ValidateChatMessagesResponse())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(Throwable::printStackTrace)
        .subscribe(new Subscriber<ChatMessagesResponse>() {
          @Override
          public final void onCompleted() {
          }

          @Override
          public final void onError(Throwable e) {
            Log.e("message result", e.getMessage());
          }

          @Override
          public final void onNext(ChatMessagesResponse response) {
            Log.d("messages result", "" + response.getChatMessages());
            ((ChatFragment) getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT)).displayChatMessages(response.getChatMessages());

          }
        });
    if (getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT) != null)
      ((ChatFragment) getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT)).clearEditText();
  }

  public void sendChatMessage(String message) {
    if (session == null || user == null)
      return;
    AppObservable.bindActivity(this, service.postMessage(new ChatMessageEnvelope(session, new MessageEnvelope(message))))
        .flatMap(new ValidateChatMessageResponse())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(Throwable::printStackTrace)
        .subscribe(new Subscriber<ChatMessageResponse>() {
          @Override
          public final void onCompleted() {
          }

          @Override
          public final void onError(Throwable e) {
            Log.e("message result", e.getMessage());
          }

          @Override
          public final void onNext(ChatMessageResponse response) {
            Log.d("message result", "" + response.getMessage());
            ((ChatFragment) getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT)).displayChatMessage(new ChatMessage(user, response.getMessage()));

          }
        });
    if (getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT) != null)
      ((ChatFragment) getFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT)).clearEditText();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(KEY_SESSION, session);
    outState.putParcelable(KEY_USER, user);
  }
}