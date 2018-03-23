package me.geekymind.moviedroid.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import me.geekymind.moviedroid.ui.movie.MovieViewModel;

/**
 * Created by Mohamed Ibrahim on 3/23/18.
 */
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

  private Long mParam;

  public MovieViewModelFactory(Long param) {
    mParam = param;
  }

  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    return (T) new MovieViewModel(mParam);
  }
}
