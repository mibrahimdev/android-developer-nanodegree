package me.geekymind.moviedroid.data.provider;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.moviedroid.data.entity.Movie;
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

  public Completable favoriteMovie(Movie movie) {
    return Completable.create(emitter -> {
      Uri mUri;
      ContentValues values = new ContentValues();
      values.put(Movie.COLUMN_ID, movie.getId());
      values.put(Movie.COLUMN_TITLE, movie.getTitle());
      values.put(Movie.COLUMN_OVERVIEW, movie.getOverview());
      values.put(Movie.COLUMN_RELEASE_DATE, movie.getReleaseDate());
      values.put(Movie.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
      values.put(Movie.COLUMN_POSTER_PATH, movie.getPosterPath());

      try {
        mUri = contentResolver.insert(FavoritesContentProvider.URI_MOVIE, values);
        long id = ContentUris.parseId(mUri);
        Timber.i("Success Insertion wit ID: %s", id);
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(new RuntimeException("Insertio"));
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
        contentResolver.query(FavoritesContentProvider.URI_MOVIE, null, null, null, null);
    int idColumn = cursor.getColumnIndex(Movie.COLUMN_ID);
    int titleColumn = cursor.getColumnIndex(Movie.COLUMN_TITLE);
    int overViewColumn = cursor.getColumnIndex(Movie.COLUMN_OVERVIEW);
    int posterColumn = cursor.getColumnIndex(Movie.COLUMN_POSTER_PATH);
    int releaseDateColumn = cursor.getColumnIndex(Movie.COLUMN_RELEASE_DATE);
    int voteAverageColumn = cursor.getColumnIndex(Movie.COLUMN_VOTE_AVERAGE);

    while (cursor.moveToNext()) {
      Movie movie = new Movie();
      Long favoriteId = cursor.getLong(idColumn);
      movie.setId(favoriteId);
      String title = cursor.getString(titleColumn);
      movie.setTitle(title);
      String overview = cursor.getString(overViewColumn);
      movie.setOverview(overview);
      String poster = cursor.getString(posterColumn);
      movie.setPosterPath(poster);
      String releaseDate = cursor.getString(releaseDateColumn);
      movie.setReleaseDate(releaseDate);
      String voteAverage = cursor.getString(voteAverageColumn);
      movie.setVoteAverage(Double.valueOf(voteAverage));
      movies.add(movie);
    }

    return movies;
  }

  public Single<Boolean> isFavorite(Long id) {
    return Single.fromCallable(() -> isFavoriteMovie(id));
  }

  private boolean isFavoriteMovie(Long id) {
    String[] mProjection = { Movie.COLUMN_ID };
    Cursor cursor =
        contentResolver.query(FavoritesContentProvider.URI_MOVIE, mProjection, null, null, null);
    int nameColumn = cursor.getColumnIndex(Movie.COLUMN_ID);
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
