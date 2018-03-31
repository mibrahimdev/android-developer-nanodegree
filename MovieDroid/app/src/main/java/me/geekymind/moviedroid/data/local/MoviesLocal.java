package me.geekymind.moviedroid.data.local;

import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public interface MoviesLocal {

  void saveFilter(String filter);

  String getFilter();

  Observable<Boolean> isFavorite(Movie movie);

  Completable deleteMovie(Movie movie);

  Observable<List<Movie>> getAllFavorites();

  Completable saveMovie(Movie movie);
}
