package me.geekymind.moviedroid.ui.movie;

import java.util.Collections;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Review;
import me.geekymind.moviedroid.data.entity.Trailer;

/**
 * Created by Mohamed Ibrahim on 3/23/18.
 */
public class MovieDataHolder {

  private final List<Trailer> trailers;
  private final List<Review> reviews;

  public MovieDataHolder(List<Trailer> trailers, List<Review> reviews) {
    this.trailers = trailers;
    this.reviews = reviews;
  }

  public List<Trailer> getTrailers() {
    return trailers;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public static MovieDataHolder empty() {
    return new MovieDataHolder(Collections.emptyList(), Collections.emptyList());
  }
}
