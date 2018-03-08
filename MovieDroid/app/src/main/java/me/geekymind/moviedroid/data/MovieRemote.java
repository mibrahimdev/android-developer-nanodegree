package me.geekymind.moviedroid.data;

import io.reactivex.Single;
import me.geekymind.moviedroid.data.entity.MoviedbResponse;
import me.geekymind.moviedroid.data.entity.SortOrder;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public interface MovieRemote {

  @GET("movie/{sort_order}")
  Single<MoviedbResponse> getMovies(@Path("sort_order") @SortOrder String sortOrder);
}
