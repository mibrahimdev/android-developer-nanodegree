package me.geekymind.moviedroid.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MovieDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 300;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_FMovies + " (" + MovieContract.MovieEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_ID_MOVIE + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POSTER +" TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_FMovies);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                MovieContract.MovieEntry.TABLE_FMovies + "'");
        // re-create database
        onCreate(sqLiteDatabase);
    }
}
