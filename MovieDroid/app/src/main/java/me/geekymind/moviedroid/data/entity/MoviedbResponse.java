package me.geekymind.moviedroid.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MoviedbResponse {

  @SerializedName("page")
  private Long Page;
  @SerializedName("results")
  private List<Movie> movies;
  @SerializedName("total_pages")
  private Long TotalPages;
  @SerializedName("total_results")
  private Long TotalResults;

  public Long getPage() {
    return Page;
  }

  public void setPage(Long page) {
    Page = page;
  }

  public List<Movie> getMovies() {
    return movies;
  }

  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }

  public Long getTotalPages() {
    return TotalPages;
  }

  public void setTotalPages(Long totalPages) {
    TotalPages = totalPages;
  }

  public Long getTotalResults() {
    return TotalResults;
  }

  public void setTotalResults(Long totalResults) {
    TotalResults = totalResults;
  }
}
