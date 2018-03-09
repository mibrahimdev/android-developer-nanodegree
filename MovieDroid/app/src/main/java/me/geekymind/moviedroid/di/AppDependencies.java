package me.geekymind.moviedroid.di;

import android.app.Application;
import me.geekymind.moviedroid.data.local.PreferenceHelper;
import me.geekymind.moviedroid.data.remote.MovieRemote;
import me.geekymind.moviedroid.data.remote.RemoteDataFactory;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class AppDependencies {

  private AppDependencies() {
  }

  private static PreferenceHelper prefHelperInstance;
  private static MovieRemote movieRemote;

  public static void createGraph(Application application) {
    if (prefHelperInstance == null) {
      prefHelperInstance = new PreferenceHelper(application);
    }
  }

  public static PreferenceHelper getPrefHelperInstance() {
    return prefHelperInstance;
  }

  public static MovieRemote getMovieRemote() {
    return RemoteDataFactory.newMovieRemote();
  }
}
