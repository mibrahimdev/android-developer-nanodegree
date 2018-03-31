package me.geekymind.moviedroid.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.ui.movie.MovieViewModel;

/**
 * Created by Mohamed Ibrahim on 3/23/18.
 */
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

  private Movie mParam;

  public MovieViewModelFactory(Movie param) {
    mParam = param;
  }

  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    return (T) new MovieViewModel(mParam);
  }
}
