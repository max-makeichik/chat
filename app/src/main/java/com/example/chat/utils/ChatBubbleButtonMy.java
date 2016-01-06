package com.example.chat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.chat.R;

import static com.example.chat.utils.CalloutPath.MARKER_BOTTOM;
import static com.example.chat.utils.CalloutPath.MARKER_LEFT;
import static com.example.chat.utils.CalloutPath.MARKER_NONE;
import static com.example.chat.utils.CalloutPath.MARKER_RIGHT;
import static com.example.chat.utils.CalloutPath.MARKER_TOP;
import static com.example.chat.utils.CalloutPath.factor;

public class ChatBubbleButtonMy extends Button {

  private boolean mAttachedToWindow;

  private CalloutPath mPath;
  private ShapeDrawable mFill;
  private ShapeDrawable mStroke;
  private float mCornerRadius;
  private int mCalloutMarker;

  @Override
  public boolean isAttachedToWindow() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return mAttachedToWindow;
    } else {
      return super.isAttachedToWindow();
    }
  }

  public ChatBubbleButtonMy(Context context) {
    super(context);
    init(null);
  }

  public ChatBubbleButtonMy(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public ChatBubbleButtonMy(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs);
  }

  private void init(AttributeSet attrs) {

    if (attrs == null) {
      return;
    }

    Context c = getContext();
    if (c == null) {
      return;
    }

    TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.ChatBubbleButtonNotMy);
    if (a == null) {
      return;
    }

    Resources r = getResources();
    if (r == null) {
      return;
    }

    try {
      mPath = new CalloutPath();
      mFill = new ShapeDrawable();
      mFill.getPaint().setStyle(Paint.Style.FILL);
      mFill.getPaint().setColor(a.getColor(R.styleable.ChatBubbleButtonNotMy_boxFillColor, r.getColor(R.color.bubble_my)));
      mStroke = new ShapeDrawable();
      mStroke.getPaint().setStyle(Paint.Style.STROKE);
      mStroke.getPaint().setColor(a.getColor(R.styleable.ChatBubbleButtonNotMy_boxStrokeColor, r.getColor(R.color.bubble_stroke)));
      mStroke.getPaint().setAntiAlias(true);
      mStroke.getPaint().setStrokeWidth(a.getDimension(R.styleable.ChatBubbleButtonNotMy_boxStrokeWidth, r.getDimension(R.dimen.bubble_stroke_width)));
      mCornerRadius = a.getDimension(R.styleable.ChatBubbleButtonNotMy_boxCornersRadius, r.getDimension(R.dimen.bubble_corner_radius));
      mCalloutMarker = a.getInt(R.styleable.ChatBubbleButtonNotMy_calloutMarker, MARKER_NONE);

      initBackground();
    } finally {
      a.recycle();
    }
  }

  @SuppressWarnings("deprecation")
  private void initBackground() {
    int pl = (int) (getPaddingLeft() + factor(mCalloutMarker, MARKER_LEFT) * mCornerRadius);
    int pt = (int) (getPaddingTop() + factor(mCalloutMarker, MARKER_TOP) * mCornerRadius);
    int pr = (int) (getPaddingRight() + factor(mCalloutMarker, MARKER_RIGHT) * mCornerRadius);
    int pb = (int) (getPaddingBottom() + factor(mCalloutMarker, MARKER_BOTTOM) * mCornerRadius);

    Drawable drawable = new LayerDrawable(new Drawable[]{mFill, mStroke});
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      setBackgroundDrawable(drawable);
    } else {
      setBackground(drawable);
    }

    setPadding(pl, pt, pr, pb);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    mAttachedToWindow = true;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mAttachedToWindow = false;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldW, int oldH) {
    mPath.build(mCalloutMarker, w, h, mStroke.getPaint().getStrokeWidth(), mCornerRadius);
    PathShape shape = new PathShape(mPath, w, h);
    mFill.setShape(shape);
    mStroke.setShape(shape);
  }

}