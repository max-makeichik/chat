package com.example.chat.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BubbleDrawable extends Drawable {

  private Paint mPaint;

  private float mBoxWidth;
  private float mBoxHeight;
  private Rect mBoxPadding = new Rect();

  private float mPointerWidth;
  private float mPointerHeight;
  private boolean my;
  private float strokeWidth;
  private int strokeColor;
  private int fillColor;
  private static final String TAG = "BubbleDrawable";

  public BubbleDrawable(boolean my) {
    this.my = my;
    initBubble();
  }

  private void initBubble() {
    setPointerSizes(25);
  }

  @Override
  public void draw(Canvas canvas) {
    Log.d(TAG, "draw");
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    fillBubble(canvas);
    drawBubbleStroke(canvas);
    //drawTriangle(canvas, my);
  }

  private void drawBubbleStroke(Canvas canvas) {
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(strokeWidth);
    //mPaint.setColor(strokeColor);
    mPaint.setColor(Color.RED);
    drawPath(canvas);
  }

  private void fillBubble(Canvas canvas) {
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setColor(fillColor);
    drawPath(canvas);
  }

  private void drawPath(Canvas canvas) {
    Rect bounds = getBounds();
    Path path = new Path();

    //right arrow
    path.moveTo(mBoxWidth, mBoxHeight / 2 - mPointerWidth / 2);
    path.lineTo(mBoxWidth + mPointerHeight, mBoxHeight / 2);
    path.lineTo(mBoxWidth, mBoxHeight / 2 + mPointerWidth / 2);

    //right vertical lower line.
    path.lineTo(mBoxWidth, mBoxHeight - mPointerWidth / 2);

    //bottom right arc.
    path.arcTo(new RectF(mBoxWidth - mPointerWidth, bounds.bottom - mPointerWidth, mBoxWidth, bounds.bottom), 0, 90);

    //bottom horizontal line.
    path.lineTo(mPointerWidth / 2, mBoxHeight);

    //bottom left arc.
    path.arcTo(new RectF(0, mBoxHeight - mPointerWidth, mPointerWidth, mBoxHeight), 90, 90);

    //left vertical line.
    path.lineTo(0, mPointerWidth);

    //top left arc.
    path.arcTo(new RectF(0, 0, mPointerWidth, mPointerWidth), 180, 90);

    //top horizontal line
    path.lineTo(mBoxWidth - mPointerWidth / 2, 0);

    //top right arc
    path.arcTo(new RectF(mBoxWidth - mPointerWidth, 0, mBoxWidth, mPointerWidth), 270, 90);

    //right vertical upper line.
    path.lineTo(mBoxWidth, mPointerWidth / 2);

    path.close();
    path.setFillType(Path.FillType.EVEN_ODD);
    if(!my)
      rotatePath(path);
    canvas.drawPath(path, mPaint);
  }

  private void rotatePath(Path path) {
    Matrix mMatrix = new Matrix();
    RectF bounds = new RectF();
    path.computeBounds(bounds, true);
    mMatrix.postRotate(180, bounds.centerX(), bounds.centerY());
    path.transform(mMatrix);
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSPARENT;
  }

  @Override
  public void setAlpha(int alpha) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setColorFilter(ColorFilter cf) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean getPadding(Rect padding) {
    //Log.d(TAG, "getPadding " + padding.bottom + ", " + padding.top);
    padding.set(mBoxPadding);

    return true;
  }

  @Override
  protected void onBoundsChange(Rect bounds) {
    mBoxWidth = bounds.width() - mPointerHeight;
    mBoxHeight = getBounds().height();
    super.onBoundsChange(bounds);
  }

  public void setPadding(int left, int top, int right, int bottom) {
    //Log.d(TAG, "setPadding");
    mBoxPadding.left = left;
    mBoxPadding.top = top;
    mBoxPadding.right = right;
    mBoxPadding.bottom = bottom;
  }

  public void setPointerSizes(int pointerWidth) {
    mPointerWidth = pointerWidth;
    mPointerHeight = (float) Math.sqrt((double) (pointerWidth * pointerWidth / 2)); //  base of equilateral triangle
    //Log.d(TAG, "mPointerHeight " + mPointerHeight);
  }


  public void setPointerSizes(int pointerWidth, int pointerHeight) {
    mPointerWidth = pointerWidth;
    mPointerHeight = pointerHeight;
  }

  public void setFillColor(int fillColor) {
    //Log.d(TAG, "setFillColor");
    this.fillColor = fillColor;
  }

  public void setStrokeColor(int strokeColor) {
    //Log.d(TAG, "setStrokeColor");
    this.strokeColor = strokeColor;
  }

  public void setStrokeWidth(float strokeWidth) {
    this.strokeWidth = strokeWidth;
  }
}