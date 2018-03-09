package me.geekymind.moviedroid.data;

import io.reactivex.Single;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.data.entity.MoviedbResponse;
import me.geekymind.moviedroid.data.entity.SortOrder;
import me.geekymind.moviedroid.data.remote.MovieRemote;
import me.geekymind.moviedroid.data.remote.RemoteDataFactory;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class MoviesRepo implements MoviesRepository {

  private final MovieRemote movieRemote;

  public MoviesRepo() {
    movieRemote = RemoteDataFactory.newMovieRemote();
  }

  @Override
  public Single<List<Movie>> getMovies() {
    return movieRemote.getMovies(SortOrder.POPULAR)
        .map(MoviedbResponse::getMovies);
  }
}
