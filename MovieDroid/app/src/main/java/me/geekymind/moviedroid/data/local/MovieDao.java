package me.geekymind.moviedroid.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/27/18.
 */
@Dao
public interface MovieDao {

  @Query("SELECT COUNT(*) FROM " + Movie.TABLE_NAME)
  int count();

  @Insert
  long insert(Movie movie);

  @Query("SELECT * FROM " + Movie.TABLE_NAME)
  Cursor selectAll();

  @Query("SELECT * FROM " + Movie.TABLE_NAME + " WHERE " + Movie.COLUMN_ID + " = :id")
  Cursor selectById(long id);

  @Query("DELETE FROM " + Movie.TABLE_NAME + " WHERE " + Movie.COLUMN_ID + " = :id")
  int deleteById(long id);

  @Update
  int update(Movie movie);

  @Insert
  long[] insertAll(Movie[] movies);
}
