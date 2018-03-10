package me.geekymind.moviedroid.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.moviedroid.data.remote.MovieRemote;
import me.geekymind.moviedroid.data.remote.RemoteDataFactory;

public class Movie implements Parcelable {

  private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

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
  private double VoteAverage;
  @SerializedName("vote_count")
  private double VoteCount;

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
    return IMAGE_BASE_URL + PosterPath;
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

  public double getVoteAverage() {
    return VoteAverage;
  }

  public void setVoteAverage(Long voteAverage) {
    VoteAverage = voteAverage;
  }

  public double getVoteCount() {
    return VoteCount;
  }

  public void setVoteCount(Long voteCount) {
    VoteCount = voteCount;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.Adult);
    dest.writeString(this.BackdropPath);
    dest.writeList(this.GenreIds);
    dest.writeValue(this.Id);
    dest.writeString(this.OriginalLanguage);
    dest.writeString(this.OriginalTitle);
    dest.writeString(this.Overview);
    dest.writeValue(this.Popularity);
    dest.writeString(this.PosterPath);
    dest.writeString(this.ReleaseDate);
    dest.writeString(this.Title);
    dest.writeValue(this.Video);
    dest.writeDouble(this.VoteAverage);
    dest.writeDouble(this.VoteCount);
  }

  public Movie() {
  }

  protected Movie(Parcel in) {
    this.Adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.BackdropPath = in.readString();
    this.GenreIds = new ArrayList<Long>();
    in.readList(this.GenreIds, Long.class.getClassLoader());
    this.Id = (Long) in.readValue(Long.class.getClassLoader());
    this.OriginalLanguage = in.readString();
    this.OriginalTitle = in.readString();
    this.Overview = in.readString();
    this.Popularity = (Double) in.readValue(Double.class.getClassLoader());
    this.PosterPath = in.readString();
    this.ReleaseDate = in.readString();
    this.Title = in.readString();
    this.Video = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.VoteAverage = in.readDouble();
    this.VoteCount = in.readDouble();
  }

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override
    public Movie createFromParcel(Parcel source) {
      return new Movie(source);
    }

    @Override
    public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };
}
