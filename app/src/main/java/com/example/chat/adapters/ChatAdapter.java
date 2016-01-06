package com.example.chat.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chat.R;
import com.example.chat.model.ChatMessage;
import com.example.chat.utils.ChatBubbleButtonMy;
import com.example.chat.utils.ChatBubbleButtonNotMy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

  private List<ChatMessage> chatList;
  private OnItemClickListener mOnItemClickListener;

  private static final String TAG = "ChatAdapter";

  public interface OnItemClickListener {
    void onItemClick(ChatMessage chatMessage);
  }

  public ChatAdapter(List<ChatMessage> chatList, Context ctx, OnItemClickListener onItemClickListener) {
    this.chatList = chatList;
    mOnItemClickListener = onItemClickListener;
  }

  @Override
  public int getItemViewType(int position) {
    double d = Math.random();
    if (d > 0.75)
      return ChatMessage.TYPE_NOT_MY_IMAGE;
    if (d > 0.5)
      return ChatMessage.TYPE_NOT_MY_MESSAGE;
    if (d < 0.25)
      return ChatMessage.TYPE_MY_IMAGE;
    return ChatMessage.TYPE_MY_MESSAGE;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    int layout = -1;
    switch (viewType) {
      case ChatMessage.TYPE_MY_MESSAGE:
        layout = R.layout.item_chat_message_my;
        break;
      case ChatMessage.TYPE_NOT_MY_MESSAGE:
        layout = R.layout.item_chat_message_not_my;
        break;
      case ChatMessage.TYPE_MY_IMAGE:
        layout = R.layout.item_chat_image_my;
        break;
      case ChatMessage.TYPE_NOT_MY_IMAGE:
        layout = R.layout.item_chat_image_not_my;
        break;
    }
    View v = LayoutInflater
        .from(parent.getContext())
        .inflate(layout, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final ChatMessage chatMessage = chatList.get(position);
    holder.setMessage(chatMessage);
  }

  @Override
  public int getItemCount() {
    return chatList.size();
  }

  protected class ViewHolder extends RecyclerView.ViewHolder {
    @Nullable @Bind(R.id.image)
    ImageView imageView;

    @Nullable @Bind(R.id.username)
    TextView name;

    @Nullable @Bind(R.id.textMy)
    ChatBubbleButtonMy textMy;

    @Nullable @Bind(R.id.textNotMy)
    ChatBubbleButtonNotMy textNotMy;

    @Nullable @Bind(R.id.date)
    TextView date;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setMessage(ChatMessage chatMessage) {
      if (name != null && chatMessage.getName() != null)
        name.setText(chatMessage.getName());
      if (chatMessage.getText() != null) {
        if(textMy != null)
          textMy.setText(chatMessage.getText());
        if(textNotMy != null)
          textNotMy.setText(chatMessage.getText());
      }
      if (imageView != null && chatMessage.getImageUrl() != null)
        imageView.setBackgroundResource(R.drawable.placeholder);
    }
  }
}