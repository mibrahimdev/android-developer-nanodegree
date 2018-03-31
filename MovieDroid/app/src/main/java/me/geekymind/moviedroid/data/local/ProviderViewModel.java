package me.geekymind.moviedroid.data.local;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.data.local.provider.MovieContract;
import timber.log.Timber;

/**
 * Created by Mohamed Ibrahim on 3/30/18.
 */
public class ProviderViewModel extends AndroidViewModel {

  private final ContentResolver contentResolver;

  public ProviderViewModel(@NonNull Application application) {
    super(application);
    contentResolver = application.getContentResolver();
  }

  public Completable saveMovie(Movie movie) {
    return Completable.create(emitter -> {
      if (isFavoriteMovie(movie.getId())) {
        emitter.onError(new RuntimeException("Duplicate insertion"));
      }
      ContentValues values = new ContentValues();
      values.put(MovieContract.MovieEntry.COLUMN_ID_MOVIE, movie.getId());
      values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
      values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
      values.put(MovieContract.MovieEntry.COLUMN_DATE, movie.getReleaseDate());
      values.put(MovieContract.MovieEntry.COLUMN_VOTE, movie.getVoteAverage());
      values.put(MovieContract.MovieEntry.COLUMN_POSTER, movie.getPosterPath());

      try {
        Uri mUri = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values);
        long id = ContentUris.parseId(mUri);
        Timber.i("Success Insertion wit ID: %s", id);
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(new RuntimeException("Failed Insertion of item " + movie.getTitle()));
        Timber.e(e);
      }
    });
  }

  public Single<List<Movie>> getAllFavorites() {
    return Single.fromCallable(this::getAllFavoriteMovies);
  }

  //TODO: search for a better way to write this boilerblate
  private List<Movie> getAllFavoriteMovies() {
    List<Movie> movies = new ArrayList<>();
    Cursor cursor =
        contentResolver.query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
    int idColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID_MOVIE);
    int titleColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
    int overViewColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
    int posterColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);
    int releaseDateColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE);
    int voteAverageColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE);

    while (cursor.moveToNext()) {
      Long favoriteId = cursor.getLong(idColumn);
      String title = cursor.getString(titleColumn);
      String overview = cursor.getString(overViewColumn);
      String poster = cursor.getString(posterColumn);
      String releaseDate = cursor.getString(releaseDateColumn);
      String voteAverage = cursor.getString(voteAverageColumn);
      Movie movie =
          new Movie(favoriteId, overview, poster, releaseDate, title, Double.valueOf(voteAverage));
      movies.add(movie);
    }

    return movies;
  }

  public Single<Boolean> isFavorite(Long id) {
    return Single.fromCallable(() -> isFavoriteMovie(id));
  }

  public Completable deleteFavorite(Movie movie) {
    return Completable.create(emitter -> {
      String mSelectionClause = MovieContract.MovieEntry.COLUMN_ID_MOVIE + " =?";
      String[] mSelectionArgs = { String.valueOf(movie.getId()) };
      int delete = contentResolver.delete(MovieContract.MovieEntry.CONTENT_URI, mSelectionClause,
          mSelectionArgs);
      if (delete > 0) {
        emitter.onComplete();
      } else {
        emitter.onError(new RuntimeException("Couldn't delete movie"));
      }
    });
  }

  private boolean isFavoriteMovie(Long id) {
    String[] mProjection = { MovieContract.MovieEntry.COLUMN_ID_MOVIE };
    Cursor cursor =
        contentResolver.query(MovieContract.MovieEntry.CONTENT_URI, mProjection, null, null, null);
    int nameColumn = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID_MOVIE);
    while (cursor.moveToNext()) {
      Long favoriteId = cursor.getLong(nameColumn);
      if (favoriteId.equals(id)) {
        cursor.close();
        return true;
      }
    }
    cursor.close();
    return false;
  }
}
