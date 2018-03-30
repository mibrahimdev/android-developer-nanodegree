package me.geekymind.moviedroid.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Movie.TABLE_NAME)
public class Movie implements Parcelable {

  private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
  public static final String TABLE_NAME = "favorites";
  public static final String COLUMN_ID = "_ID";
  public static final String COLUMN_TITLE = "_title";
  public static final String COLUMN_VOTE_AVERAGE = "vote_average";
  public static final String COLUMN_OVERVIEW = "_overview";
  public static final String COLUMN_RELEASE_DATE = "_release_date";
  public static final String COLUMN_POSTER_PATH = "_poster_path";

  @SerializedName("id")
  @ColumnInfo(name = COLUMN_ID)
  @PrimaryKey(autoGenerate = false)
  private Long id;

  @SerializedName("adult")
  private Boolean adult;

  @SerializedName("backdrop_path")
  private String backdropPath;

  @SerializedName("genre_ids")
  private List<Long> genreIds;

  @SerializedName("original_language")
  private String originalLanguage;

  @SerializedName("original_title")
  private String originaltitle;

  @ColumnInfo(name = COLUMN_OVERVIEW)
  @SerializedName("overview")
  private String overview;

  @SerializedName("popularity")
  private Double popularity;

  @ColumnInfo(name = COLUMN_POSTER_PATH)
  @SerializedName("poster_path")
  private String posterPath;

  @ColumnInfo(name = COLUMN_RELEASE_DATE)
  @SerializedName("release_date")
  private String releaseDate;

  @ColumnInfo(name = COLUMN_TITLE)
  @SerializedName("title")
  private String title;

  @SerializedName("video")
  private Boolean video;

  @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
  @SerializedName("vote_average")
  private double voteAverage;

  @SerializedName("vote_count")
  private double voteCount;

  private boolean isFavorite;


  public static Movie fromContentValues(ContentValues values) {
    final Movie movie = new Movie();
    if (values.containsKey(Movie.COLUMN_ID)) {
      movie.setId(values.getAsLong(COLUMN_ID));
      movie.setReleaseDate(values.getAsString(COLUMN_RELEASE_DATE));
      movie.setPosterPath(values.getAsString(COLUMN_POSTER_PATH));
      movie.setTitle(values.getAsString(COLUMN_TITLE));
      movie.setOverview(values.getAsString(COLUMN_OVERVIEW));
      movie.setVoteAverage(values.getAsDouble(COLUMN_VOTE_AVERAGE));
      return movie;
    }
    return null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getPosterPath() {
    return IMAGE_BASE_URL + posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.id);
    dest.writeValue(this.adult);
    dest.writeString(this.backdropPath);
    dest.writeList(this.genreIds);
    dest.writeString(this.originalLanguage);
    dest.writeString(this.originaltitle);
    dest.writeString(this.overview);
    dest.writeValue(this.popularity);
    dest.writeString(this.posterPath);
    dest.writeString(this.releaseDate);
    dest.writeString(this.title);
    dest.writeValue(this.video);
    dest.writeDouble(this.voteAverage);
    dest.writeDouble(this.voteCount);
    dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
  }

  public Movie() {
  }

  protected Movie(Parcel in) {
    this.id = (Long) in.readValue(Long.class.getClassLoader());
    this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.backdropPath = in.readString();
    this.genreIds = new ArrayList<Long>();
    in.readList(this.genreIds, Long.class.getClassLoader());
    this.originalLanguage = in.readString();
    this.originaltitle = in.readString();
    this.overview = in.readString();
    this.popularity = (Double) in.readValue(Double.class.getClassLoader());
    this.posterPath = in.readString();
    this.releaseDate = in.readString();
    this.title = in.readString();
    this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.voteAverage = in.readDouble();
    this.voteCount = in.readDouble();
    this.isFavorite = in.readByte() != 0;
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
