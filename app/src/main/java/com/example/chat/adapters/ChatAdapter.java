package com.example.chat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chat.R;
import com.example.chat.model.ChatMessage;
import com.example.chat.utils.Utils;
import com.example.chat.view.BubbleDrawable;
import com.example.chat.view.BubbleTransformation;
import com.example.chat.view.GenericViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<GenericViewHolder> {

  private List<ChatMessage> chatList;
  private OnItemClickListener onItemClickListener;
  private Context context;
  private int margin;
  private int myId;

  private static final String TAG = "ChatAdapter";

  public interface OnItemClickListener {
    void onItemClick(ChatMessage chatMessage);
  }

  public ChatAdapter(List<ChatMessage> chatList, Context context, int myId, OnItemClickListener onItemClickListener) {
    this.chatList = chatList;
    this.context = context;
    this.myId = myId;
    this.onItemClickListener = onItemClickListener;
    Log.d(TAG, "myId " + myId);
    margin = Utils.getMargin(context);
  }

  @Override
  public int getItemViewType(int position) {
    boolean my = chatList.get(position).getUser().getId() == myId;
    //Log.d(TAG, "position " + position + chatList.get(position).getMessage().getText() + ", my " + my);
    if (chatList.get(position).getMessage().getImageUrl() != null)
      return my ? ChatMessage.TYPE_MY_IMAGE : ChatMessage.TYPE_NOT_MY_IMAGE;
    return my ? ChatMessage.TYPE_MY_MESSAGE : ChatMessage.TYPE_NOT_MY_MESSAGE;
  }

  @Override
  public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ChatMessage.TYPE_MY_MESSAGE:
        return new ViewHolderMessageMy(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message_my, parent, false));
      case ChatMessage.TYPE_NOT_MY_MESSAGE:
        return new ViewHolderMessageNotMy(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message_not_my, parent, false));
      case ChatMessage.TYPE_MY_IMAGE:
        return new ViewHolderImageMy(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_image_my, parent, false));
      case ChatMessage.TYPE_NOT_MY_IMAGE:
        return new ViewHolderImageNotMy(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_image_not_my, parent, false));
    }
    return null;
  }

  @Override
  public void onBindViewHolder(final GenericViewHolder holder, final int position) {
    final ChatMessage chatMessage = chatList.get(position);
    holder.setChatMessage(chatMessage);
  }

  @Override
  public int getItemCount() {
    return chatList.size();
  }

  private void setBubbleDrawable(FrameLayout bubbleLayout, TextView date, boolean my) {
    BubbleDrawable bubbleDrawable = new BubbleDrawable(my);
    bubbleDrawable.setFillColor(my ? context.getResources().getColor(R.color.bubble_my) : context.getResources().getColor(R.color.bubble_not_my));
    bubbleDrawable.setStrokeColor(context.getResources().getColor(R.color.bubble_stroke));
    bubbleDrawable.setStrokeWidth(context.getResources().getDimension(R.dimen.bubble_stroke_width));

    //int topPadding = image ? Utils.dpToPixels(context, context.getResources().getInteger(R.integer.chat_item_top_padding)) : 0;
    int sideMargin = Utils.dpToPixels(context, context.getResources().getInteger(R.integer.chat_item_side_margin));
    bubbleDrawable.setPadding(0, 0, 0, 0);

    bubbleLayout.setBackgroundDrawable(bubbleDrawable);
    //int margin = Utils.dpToPixels(context, context.getResources().getInteger(R.integer.chat_item_margin));

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    );
    params.setMargins(my ? margin : sideMargin, 0, my ? sideMargin : margin, 0);
    params.gravity = my ? Gravity.RIGHT : Gravity.LEFT;

    //bubbleLayout.setLayoutParams(params);

    if (!my) {
      bubbleLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) date.getLayoutParams();
          p.leftMargin = bubbleLayout.getWidth() - date.getWidth();
          date.setLayoutParams(p);
        }
      });
    }
  }

  private void setBubbleDrawable(ImageView imageView, TextView date, boolean my, boolean image) {
    BubbleDrawable bubbleDrawable = new BubbleDrawable(my);
    bubbleDrawable.setFillColor(my ? context.getResources().getColor(R.color.bubble_my) : context.getResources().getColor(R.color.bubble_not_my));
    bubbleDrawable.setStrokeColor(context.getResources().getColor(R.color.bubble_stroke));
    bubbleDrawable.setStrokeWidth(context.getResources().getDimension(R.dimen.bubble_stroke_width));

    //int topPadding = image ? Utils.dpToPixels(context, context.getResources().getInteger(R.integer.chat_item_top_padding)) : 0;
    int sideMargin = Utils.dpToPixels(context, context.getResources().getInteger(R.integer.chat_item_side_margin));
    bubbleDrawable.setPadding(0, 0, 0, 0);

    //imageView.setImageDrawable(bubbleDrawable);
    //int margin = Utils.dpToPixels(context, context.getResources().getInteger(R.integer.chat_item_margin));

    if (!my) {
      imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) date.getLayoutParams();
          p.leftMargin = imageView.getWidth() - date.getWidth();
          date.setLayoutParams(p);
        }
      });
    }
  }

  protected class ViewHolderMessageMy extends GenericViewHolder {
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.bubble_layout)
    FrameLayout bubble_layout;
    @Bind(R.id.text)
    TextView text;

    public ViewHolderMessageMy(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override
    public void setChatMessage(ChatMessage chatMessage) {
      setBubbleDrawable(bubble_layout, date, true);
      text.setText(chatMessage.getMessage().getText());
      date.setText(chatMessage.getMessage().getUpdatedAt());
    }
  }

  protected class ViewHolderMessageNotMy extends GenericViewHolder {
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.bubble_layout)
    FrameLayout bubble_layout;
    @Bind(R.id.text)
    TextView text;

    public ViewHolderMessageNotMy(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      setBubbleDrawable(bubble_layout, date, false);
    }

    @Override
    public void setChatMessage(ChatMessage chatMessage) {
      username.setText(chatMessage.getUser().getName());
      text.setText(chatMessage.getMessage().getText());
      date.setText(chatMessage.getMessage().getUpdatedAt());
    }
  }

  protected class ViewHolderImageMy extends GenericViewHolder {
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.image)
    ImageView image;

    public ViewHolderImageMy(View itemView) {
      super(itemView);
      Log.d(TAG, "setImageDrawable");
      ButterKnife.bind(this, itemView);
      setBubbleDrawable(image, date, true, true);
    }

    @Override
    public void setChatMessage(ChatMessage chatMessage) {
      date.setText(chatMessage.getMessage().getUpdatedAt());
      Picasso.with(context).load(chatMessage.getMessage().getImageUrl()).transform(new BubbleTransformation(20, false)).into(image);
    }
  }

  protected class ViewHolderImageNotMy extends GenericViewHolder {
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.image)
    ImageView image;

    public ViewHolderImageNotMy(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      setBubbleDrawable(image, date, false, true);
    }

    @Override
    public void setChatMessage(ChatMessage chatMessage) {
      username.setText(chatMessage.getUser().getName());
      //Picasso.with(context).load(chatMessage.getImageUrl()).into(image);
      date.setText(chatMessage.getMessage().getUpdatedAt());
      Picasso.with(context).load(chatMessage.getMessage().getImageUrl()).transform(new BubbleTransformation(20, true)).into(image);
    }

  }
}