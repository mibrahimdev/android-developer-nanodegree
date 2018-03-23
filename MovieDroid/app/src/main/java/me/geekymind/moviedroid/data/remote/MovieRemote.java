package me.geekymind.moviedroid.data.remote;

import io.reactivex.Single;
import me.geekymind.moviedroid.data.entity.MoviedbResponse;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.data.entity.ReviewResponse;
import me.geekymind.moviedroid.data.entity.TrailersResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public interface MovieRemote {

  @GET("movie/{sort_order}")
  Single<MoviedbResponse> getMovies(@Path("sort_order") @Filter String sortOrder);

  @GET("movie/{movie_id}/videos")
  Single<TrailersResponse> getTrailers(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/reviews ")
  Single<ReviewResponse> getReviews(@Path("movie_id") String movieId);
}
