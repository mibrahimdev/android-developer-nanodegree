package me.geekymind.moviedroid.data;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.data.entity.MoviedbResponse;
import me.geekymind.moviedroid.data.entity.Review;
import me.geekymind.moviedroid.data.entity.ReviewResponse;
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

  private static MoviesRepo instance;

  private MoviesRepo() {
    movieRemote = AppDependencies.getMovieRemote();
    moviesLocal = AppDependencies.getPrefHelperInstance();
  }

  public static MoviesRepo getInstance() {
    if (instance == null) {
      instance = new MoviesRepo();
    }
    return instance;
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

  @Override
  public Single<List<Review>> getReviews(String movieId) {
    return movieRemote.getReviews(movieId)
        .map(ReviewResponse::getReviews)
        .subscribeOn(Schedulers.io());
  }
}
