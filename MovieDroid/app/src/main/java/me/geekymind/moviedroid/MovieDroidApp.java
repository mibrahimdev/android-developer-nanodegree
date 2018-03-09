package me.geekymind.moviedroid;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import me.geekymind.moviedroid.di.AppDependencies;
import timber.log.Timber;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public class MovieDroidApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());

    AppDependencies.createGraph(this);
  }
}
