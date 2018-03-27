package me.geekymind.moviedroid.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Mohamed Ibrahim on 3/27/18.
 */
public class AndroidUtil {

  public static void watchYoutubeVideo(String key, Context context) {
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
      context.startActivity(intent);
    } catch (ActivityNotFoundException ex) {
      Intent intent =
          new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
      context.startActivity(intent);
    }
  }
}
