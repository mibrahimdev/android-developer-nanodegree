package me.geekymind.moviedroid.data;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.data.entity.MoviedbResponse;
import me.geekymind.moviedroid.data.entity.Trailer;
import me.geekymind.moviedroid.data.entity.TrailersResponse;
import me.geekymind.moviedroid.data.local.MoviesLocal;
import me.geekymind.moviedroid.data.remote.MovieRemote;
import me.geekymind.moviedroid.di.AppDependencies;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class MoviesRepo implements MoviesRepository {

  private final MovieRemote movieRemote;
  private final MoviesLocal moviesLocal;

  public MoviesRepo() {
    movieRemote = AppDependencies.getMovieRemote();
    moviesLocal = AppDependencies.getPrefHelperInstance();
  }

  @Override
  public Single<List<Movie>> getMovies() {
    return getMovies(moviesLocal.getFilter());
  }

  @Override
  public Single<List<Movie>> getMovies(String filterType) {
    return movieRemote.getMovies(filterType)
        .doAfterSuccess(moviedbResponse -> moviesLocal.saveFilter(filterType))
        .map(MoviedbResponse::getMovies)
        .subscribeOn(Schedulers.io());
  }

  @Override
  public Single<List<Trailer>> getTrailers(String movieId) {
    return movieRemote.getTrailers(movieId)
        .map(TrailersResponse::getTrailers)
        .subscribeOn(Schedulers.io());
  }
}
