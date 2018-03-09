package me.geekymind.moviedroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import me.geekymind.moviedroid.data.entity.Filter;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class PreferenceHelper implements MoviesLocal {

  private static final String FILE_NAME = "MovieDroid";

  private final SharedPreferences preferences;

  public PreferenceHelper(Context context) {
    preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
  }

  @Override
  public void saveFilter(String filter) {
    preferences.edit().putString("FILTER_KEY", filter).apply();
  }

  @Override
  public String getFilter() {
    return preferences.getString(FILE_NAME, Filter.POPULAR);
  }
}
