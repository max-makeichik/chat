package com.example.chat.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chat.model.ChatMessage;

public abstract class GenericViewHolder extends RecyclerView.ViewHolder
{
  public GenericViewHolder(View itemView) {
    super(itemView);
  }

  public abstract  void setChatMessage(ChatMessage chatMessage);
}