package com.example.chat.fragments;

import android.app.Fragment;
import android.os.Bundle;
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
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.utils.Utils;
import com.example.chat.view.RecView;
import com.mikepenz.materialdrawer.util.KeyboardUtil;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;
import java.util.List;

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
      chatList = savedInstanceState.getParcelableArrayList(CHAT_LIST_KEY);
      message = savedInstanceState.getString(MESSAGE_TEXT_KEY);
    }

  }

  public void onUserIdReceived(int id){
    putFakeData();
    chatAdapter = new ChatAdapter(chatList, getActivity(), id,
        new ChatAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(ChatMessage chatMessage) {  //  check icon clicked
            Log.d(TAG, "position");
            //((ChatActivity) getActivity()).showProfileDialog(chatMessage.getUser());
          }
        }
    );
    recyclerView.setAdapter(chatAdapter);
  }

  private void putFakeData() {
    DataFactory df = new DataFactory();
    ChatMessage chatMessage1 = new ChatMessage(new User(49, null, null), new Message("aaaaaa", null, "2016-01-08 18:44:12"));

    chatList.add(chatMessage1);
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
    if(chatAdapter != null)
      recyclerView.setAdapter(chatAdapter);

    messageEditText = (EditText) view.findViewById(R.id.messageEditText);

    if (message != null)
      messageEditText.setText(message);
    messageEditText.requestFocus();

    return view;
  }

  public void displayChatMessage(ChatMessage chatMessage) {
    chatList.add(0, chatMessage);
    chatAdapter.notifyItemInserted(0);
    scrollToNewMessageOrShowBar();
  }

  public void displayChatMessages(List<ChatMessage> chatMessages) {
    //Log.d(TAG, "chatItems messages " + messages);

    if (chatMessages.size() == 0) {
      if(chatList.size() == 0)
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
      return;
    }

    chatList.clear();
    chatAdapter.notifyDataSetChanged();
    for (ChatMessage chatMessage : chatMessages) {
      //if(chatMessages.indexOf(chatMessage) == 0) {
        chatList.add(chatMessage);
        chatAdapter.notifyItemInserted(chatMessages.indexOf(chatMessage));
        scrollToNewMessageOrShowBar();
      //}
    }
  }

  private void scrollToNewMessageOrShowBar() {
    if (layoutManager != null && layoutManager.findFirstVisibleItemPosition() == 0)
      layoutManager.scrollToPosition(0);
    else if (recyclerView != null) {
      recyclerView.scrollBy(0, 1);
      recyclerView.scrollBy(0, -1);
    }
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
    outState.putParcelableArrayList(CHAT_LIST_KEY, chatList);
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