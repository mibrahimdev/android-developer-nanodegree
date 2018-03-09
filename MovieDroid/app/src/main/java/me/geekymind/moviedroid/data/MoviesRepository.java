package me.geekymind.moviedroid.data;

import io.reactivex.Single;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public interface MoviesRepository {

  Single<List<Movie>> getMovies();
}
