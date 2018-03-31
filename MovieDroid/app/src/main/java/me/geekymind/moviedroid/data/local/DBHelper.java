package me.geekymind.moviedroid.data.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class DBHelper implements MoviesLocal {

  private static final String FILE_NAME = "MovieDroid";
  private static final String FILTER_KEY = "FILTER_KEY";
  private final ProviderViewModel providerViewModel;

  private final SharedPreferences preferences;

  public DBHelper(Application application) {
    preferences = application.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    providerViewModel = new ProviderViewModel(application);
  }

  @Override
  public void saveFilter(String filter) {
    preferences.edit().putString(FILTER_KEY, filter).apply();
  }

  @Override
  public String getFilter() {
    return preferences.getString(FILTER_KEY, Filter.POPULAR);
  }

  @Override
  public Observable<Boolean> isFavorite(Movie movie) {
    return providerViewModel.isFavorite(movie.getId()).toObservable();
  }

  @Override
  public Completable deleteMovie(Movie movie) {
    return providerViewModel.deleteFavorite(movie);
  }

  @Override
  public Observable<List<Movie>> getAllFavorites() {
    return providerViewModel.getAllFavorites().toObservable();
  }

  @Override
  public Completable saveMovie(Movie movie) {
    return providerViewModel.saveMovie(movie);
  }
}
