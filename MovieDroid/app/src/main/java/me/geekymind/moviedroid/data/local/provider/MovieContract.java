package me.geekymind.moviedroid.data.local.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
  public static final String CONTENT_AUTHORITY = "me.geekymind.moviedroid.data.local.provider";

  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

  public static final class MovieEntry implements BaseColumns {
    // table name
    public static final String TABLE_FMovies = "favorites";
    // columns
    public static final String _ID = "_id";
    public static final String COLUMN_POSTER = "poster_path";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_ID_MOVIE = "movie_ID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VOTE = "vote_average";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(TABLE_FMovies).build();
    public static final String CONTENT_DIR_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FMovies;

    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FMovies;

    public static Uri buildMoviesUri(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }
}
