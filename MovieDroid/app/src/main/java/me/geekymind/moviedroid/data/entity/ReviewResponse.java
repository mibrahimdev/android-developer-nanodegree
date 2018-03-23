package me.geekymind.moviedroid.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReviewResponse {

  @SerializedName("id")
  private Long Id;
  @SerializedName("page")
  private Long Page;
  @SerializedName("results")
  private List<Review> reviews;
  @SerializedName("total_pages")
  private Long TotalPages;
  @SerializedName("total_results")
  private Long TotalResults;

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public Long getPage() {
    return Page;
  }

  public void setPage(Long page) {
    Page = page;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
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
