package com.example.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.chat.fragments.ChatFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class ChatActivity extends AppCompatActivity {

  private static final String TAG_CHAT_FRAGMENT = "CHAT_FRAGMENT";
  @Bind(R.id.app_bar)
  Toolbar toolbar;

  @Bind(R.id.toolbar_title)
  TextView toolbarTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_chat);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    new com.mikepenz.materialdrawer.DrawerBuilder().withActivity(this).withHeaderPadding(false)
        .withToolbar(toolbar).build(); // navigation drawer

    if (savedInstanceState == null)
      replaceFragment(new ChatFragment());

  }

  @Override
  public boolean onCreateOptionsMenu(android.view.Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void replaceFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, fragment, TAG_CHAT_FRAGMENT)
        .commit();
  }

  public void sendChatMessage(String message) {
    if (getSupportFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT) != null)
      ((ChatFragment) getSupportFragmentManager().findFragmentByTag(TAG_CHAT_FRAGMENT)).clearEditText();
  }
}
