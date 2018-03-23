package me.geekymind.moviedroid.data;

import io.reactivex.Single;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.data.entity.Trailer;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public interface MoviesRepository {

  Single<List<Movie>> getMovies();

  Single<List<Movie>> getMovies(@Filter String filterType);

  Single<List<Trailer>> getTrailers(String movieId);
}
