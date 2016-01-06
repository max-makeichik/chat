package com.example.chat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chat.ChatActivity;
import com.example.chat.R;
import com.example.chat.adapters.ChatAdapter;
import com.example.chat.model.ChatMessage;
import com.example.chat.utils.Utils;
import com.example.chat.view.RecView;
import com.mikepenz.materialdrawer.util.KeyboardUtil;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class ChatFragment extends Fragment {

  private static final String CHAT_LIST_KEY = "CHAT_LIST", MESSAGE_TEXT_KEY = "MESSAGE_TEXT";

  private RecView recyclerView;
  private ChatAdapter chatAdapter;
  private LinearLayoutManager layoutManager;

  private String message;

  private ArrayList<ChatMessage> chatList = new ArrayList<>();

  @Bind(R.id.messageEditText)
  EditText messageEditText;

  @Bind(R.id.send_button)
  ImageButton sendButton;

  private static final String TAG = "ChatFragment";

  public ChatFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    setHasOptionsMenu(true);
    setRetainInstance(true);
    if (savedInstanceState != null) {
      //chatList = savedInstanceState.getParcelableArrayList(CHAT_LIST_KEY);
      message = savedInstanceState.getString(MESSAGE_TEXT_KEY);
    }

    DataFactory df = new DataFactory();
    ChatMessage chatMessage1 = new ChatMessage("Max", null, "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage2 = new ChatMessage(null, df.getRandomText(15, 50), "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage3 = new ChatMessage(null, df.getRandomText(15, 50), "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage4 = new ChatMessage("Max", null, "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage5 = new ChatMessage(null, df.getRandomText(15, 50), "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage6 = new ChatMessage("Max", df.getRandomText(15, 50), "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage7 = new ChatMessage(null, null, "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());
    ChatMessage chatMessage8 = new ChatMessage("Max", df.getRandomText(15, 50), "http://weknowyourdreams.com/images/girl/girl-08.jpg", df.getBirthDate());

    chatList.add(chatMessage1);
    chatList.add(chatMessage2);
    chatList.add(chatMessage3);
    chatList.add(chatMessage4);
    chatList.add(chatMessage5);
    chatList.add(chatMessage6);
    chatList.add(chatMessage7);
    chatList.add(chatMessage8);

    chatAdapter = new ChatAdapter(chatList, getActivity(),
        new ChatAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(ChatMessage chatMessage) {  //  check icon clicked
            Log.d(TAG, "position");
            //((ChatActivity) getActivity()).showProfileDialog(chatMessage.getUser());
          }
        }
    );
  }

  View view;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");

    view = inflater.inflate(com.example.chat.R.layout.fragment_chat, container, false);
    butterknife.ButterKnife.bind(this, view);

    KeyboardUtil keyboardUtil = new KeyboardUtil(getActivity(), view.findViewById(R.id.fragment_chat));
    keyboardUtil.enable();

    recyclerView = (RecView) view.findViewById(R.id.recycler_view);
    layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setReverseLayout(true);
    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(itemAnimator);
    recyclerView.setAdapter(chatAdapter);
    messageEditText = (EditText) view.findViewById(R.id.messageEditText);

    if (message != null)
      messageEditText.setText(message);
    messageEditText.requestFocus();

    return view;
  }

  @OnClick(R.id.send_button)
  void sendChatMessage() {
    if (!Utils.isNetworkConnected(getActivity())) {
      Toast.makeText(getActivity(), getString(com.example.chat.R.string.error_no_connection), Toast.LENGTH_SHORT).show();
      return;
    }
    if ("".equals(messageEditText.getText().toString().trim()))
      return;
    sendButton.setClickable(false);
    String message = messageEditText.getText().toString();
    ((ChatActivity) getActivity()).sendChatMessage(message);
  }

  public void clearEditText() {
    messageEditText.setText("");
  }

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d(TAG, "onSaveInstanceState");
    //outState.putParcelableArrayList(CHAT_LIST_KEY, chatList);
    outState.putString(MESSAGE_TEXT_KEY, messageEditText.getText().toString());
  }

  public void onDestroyView() {
    super.onDestroyView();
    Log.d(TAG, "onDestroyView");
    view = null;
    recyclerView = null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy");
  }

}