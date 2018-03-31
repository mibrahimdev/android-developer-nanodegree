package me.geekymind.moviedroid.data.local.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MoviesProvider extends ContentProvider {
  private static final String LOG_TAG = MoviesProvider.class.getSimpleName();
  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private MovieDBHelper mOpenHelper;

  // Codes for the UriMatcher
  private static final int MOVIE = 100;
  private static final int MOVIE_WITH_ID = 200;

  private static UriMatcher buildUriMatcher() {

    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = MovieContract.CONTENT_AUTHORITY;

    matcher.addURI(authority, MovieContract.MovieEntry.TABLE_FMovies, MOVIE);
    matcher.addURI(authority, MovieContract.MovieEntry.TABLE_FMovies + "/#", MOVIE_WITH_ID);
    return matcher;
  }

  @Override
  public boolean onCreate() {
    mOpenHelper = new MovieDBHelper(getContext());
    return true;
  }

  @Override
  public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);

    switch (match) {
      case MOVIE: {
        return MovieContract.MovieEntry.CONTENT_DIR_TYPE;
      }
      case MOVIE_WITH_ID: {
        return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
      }
      default: {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      // All movies selected
      case MOVIE: {
        retCursor = mOpenHelper.getReadableDatabase()
            .query(MovieContract.MovieEntry.TABLE_FMovies, projection, selection, selectionArgs,
                null, null, sortOrder);
        return retCursor;
      }
      // Individual Movie based on Id selected
      case MOVIE_WITH_ID: {
        retCursor = mOpenHelper.getReadableDatabase()
            .query(MovieContract.MovieEntry.TABLE_FMovies, projection,
                MovieContract.MovieEntry._ID + " = ?",
                new String[] { String.valueOf(ContentUris.parseId(uri)) }, null, null, sortOrder);
        return retCursor;
      }
      default: {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    Uri returnUri;
    switch (sUriMatcher.match(uri)) {
      case MOVIE: {
        long _id = db.insert(MovieContract.MovieEntry.TABLE_FMovies, null, values);
        // insert unless it is already contained in the database
        if (_id > 0) {
          returnUri = MovieContract.MovieEntry.buildMoviesUri(_id);
        } else {
          throw new android.database.SQLException("Failed to insert row into: " + uri);
        }
        break;
      }

      default: {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    int numDeleted;
    switch (match) {
      case MOVIE:
        numDeleted = db.delete(MovieContract.MovieEntry.TABLE_FMovies, selection, selectionArgs);
        // reset _ID
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
            + MovieContract.MovieEntry.TABLE_FMovies
            + "'");
        break;
      case MOVIE_WITH_ID:
        numDeleted =
            db.delete(MovieContract.MovieEntry.TABLE_FMovies, MovieContract.MovieEntry._ID + " = ?",
                new String[] { String.valueOf(ContentUris.parseId(uri)) });
        // reset _ID
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
            + MovieContract.MovieEntry.TABLE_FMovies
            + "'");

        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    return numDeleted;
  }

  @Override
  public int bulkInsert(Uri uri, ContentValues[] values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case MOVIE:
        // allows for multiple transactions
        db.beginTransaction();

        // keep track of successful inserts
        int numInserted = 0;
        try {
          for (ContentValues value : values) {
            if (value == null) {
              throw new IllegalArgumentException("Cannot have null content values");
            }
            long _id = -1;
            try {
              _id = db.insertOrThrow(MovieContract.MovieEntry.TABLE_FMovies, null, value);
            } catch (SQLiteConstraintException e) {
              Log.w(LOG_TAG, "Attempting to insert "
                  + value.getAsString(MovieContract.MovieEntry.COLUMN_TITLE)
                  + " but value is already in database.");
            }
            if (_id != -1) {
              numInserted++;
            }
          }
          if (numInserted > 0) {
            db.setTransactionSuccessful();
          }
        } finally {
          db.endTransaction();
        }
        if (numInserted > 0) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return numInserted;
      default:
        return super.bulkInsert(uri, values);
    }
  }

  @Override
  public int update(Uri uri, ContentValues contentValues, String selection,
      String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    int numUpdated = 0;

    if (contentValues == null) {
      throw new IllegalArgumentException("Cannot have null content values");
    }

    switch (sUriMatcher.match(uri)) {
      case MOVIE: {
        numUpdated = db.update(MovieContract.MovieEntry.TABLE_FMovies, contentValues, selection,
            selectionArgs);
        break;
      }
      case MOVIE_WITH_ID: {
        numUpdated = db.update(MovieContract.MovieEntry.TABLE_FMovies, contentValues,
            MovieContract.MovieEntry._ID + " = ?",
            new String[] { String.valueOf(ContentUris.parseId(uri)) });
        break;
      }
      default: {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }

    if (numUpdated > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return numUpdated;
  }
}
