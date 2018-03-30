package me.geekymind.moviedroid.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/27/18.
 */
@Database(entities = { Movie.class }, version = 1)
public abstract class FavoritesDb extends RoomDatabase {

  private static final String MOVIE_DROID_FAVORITES = "MovieDroid_Favorites";

  public abstract MovieDao movieDao();

  private static FavoritesDb sInstance;

  /**
   * Gets the singleton instance of SampleDatabase.
   *
   * @param context The context.
   * @return The singleton instance of SampleDatabase.
   */
  public static synchronized FavoritesDb getInstance(Context context) {
    if (sInstance == null) {
      sInstance = Room.databaseBuilder(context.getApplicationContext(), FavoritesDb.class,
          MOVIE_DROID_FAVORITES).build();
    }
    return sInstance;
  }
}
