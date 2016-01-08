package com.example.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.chat.ChatActivity;

public class Utils {

  public static boolean isNetworkConnected(Context context) {
    try {
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo info = cm.getActiveNetworkInfo();

      SharedPreferences wifi = PreferenceManager.getDefaultSharedPreferences(context);
      if (info != null) {
        if (!wifi.getBoolean("wifipref", false))
          return info.isConnectedOrConnecting();
        else
          return (info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnectedOrConnecting());
      } else
        return false;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static int spToPixels(Context context, float sp) {
    float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (sp * scaledDensity);
  }

  public static int dpToPixels(Context context, int dp) {
    Resources r = context.getResources();
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.getDisplayMetrics()
    );
  }

  public static int getMargin(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    return (int) (width * 0.4);
  }

  public static Bitmap clipit(Bitmap bitmapimg,int direct) {

    //1 = direction right
    //0 = direction left

    Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
        bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
        bitmapimg.getHeight());

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);

    if(direct == 0) {
      canvas.drawRect(0, 0, bitmapimg.getWidth()-15, bitmapimg.getHeight(), paint);
      Path path = new Path();
      path.moveTo(bitmapimg.getWidth()-15, 10);
      path.lineTo(bitmapimg.getWidth(), 20);
      path.lineTo(bitmapimg.getWidth()-15, 30);
      path.lineTo(bitmapimg.getWidth()-15, 10);
      canvas.drawPath(path,paint);
    }
    if(direct == 1) {
      canvas.drawRect(15, 0, bitmapimg.getWidth(), bitmapimg.getHeight(), paint);
      Path path = new Path();
      path.moveTo(15, 10);
      path.lineTo(0, 20);
      path.lineTo(15, 30);
      path.lineTo(15, 10);
      canvas.drawPath(path,paint);
    }

    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmapimg, rect, rect, paint);
    return output;
  }

  public static String getSession(Context context) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(ChatActivity.KEY_SESSION, null);
  }

  public static void saveSession(Context context, String session) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(ChatActivity.KEY_SESSION, session).apply();
  }

  public static void showApiError(Context context, String errorMessage) {
    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
  }
}