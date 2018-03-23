package me.geekymind.moviedroid.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TrailersResponse {

  @SerializedName("id")
  private Long Id;
  @SerializedName("results")
  private List<Trailer> trailers;

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public List<Trailer> getTrailers() {
    return trailers;
  }

  public void setTrailers(List<Trailer> trailers) {
    this.trailers = trailers;
  }
}
