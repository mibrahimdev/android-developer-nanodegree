package me.geekymind.moviedroid.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movie {

  @SerializedName("adult")
  private Boolean Adult;
  @SerializedName("backdrop_path")
  private String BackdropPath;
  @SerializedName("genre_ids")
  private List<Long> GenreIds;
  @SerializedName("id")
  private Long Id;
  @SerializedName("original_language")
  private String OriginalLanguage;
  @SerializedName("original_title")
  private String OriginalTitle;
  @SerializedName("overview")
  private String Overview;
  @SerializedName("popularity")
  private Double Popularity;
  @SerializedName("poster_path")
  private String PosterPath;
  @SerializedName("release_date")
  private String ReleaseDate;
  @SerializedName("title")
  private String Title;
  @SerializedName("video")
  private Boolean Video;
  @SerializedName("vote_average")
  private Long VoteAverage;
  @SerializedName("vote_count")
  private Long VoteCount;

  public Boolean getAdult() {
    return Adult;
  }

  public void setAdult(Boolean adult) {
    Adult = adult;
  }

  public String getBackdropPath() {
    return BackdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    BackdropPath = backdropPath;
  }

  public List<Long> getGenreIds() {
    return GenreIds;
  }

  public void setGenreIds(List<Long> genreIds) {
    GenreIds = genreIds;
  }

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public String getOriginalLanguage() {
    return OriginalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    OriginalLanguage = originalLanguage;
  }

  public String getOriginalTitle() {
    return OriginalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    OriginalTitle = originalTitle;
  }

  public String getOverview() {
    return Overview;
  }

  public void setOverview(String overview) {
    Overview = overview;
  }

  public Double getPopularity() {
    return Popularity;
  }

  public void setPopularity(Double popularity) {
    Popularity = popularity;
  }

  public String getPosterPath() {
    return PosterPath;
  }

  public void setPosterPath(String posterPath) {
    PosterPath = posterPath;
  }

  public String getReleaseDate() {
    return ReleaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    ReleaseDate = releaseDate;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public Boolean getVideo() {
    return Video;
  }

  public void setVideo(Boolean video) {
    Video = video;
  }

  public Long getVoteAverage() {
    return VoteAverage;
  }

  public void setVoteAverage(Long voteAverage) {
    VoteAverage = voteAverage;
  }

  public Long getVoteCount() {
    return VoteCount;
  }

  public void setVoteCount(Long voteCount) {
    VoteCount = voteCount;
  }
}
