/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.geekymind.moviedroid.data.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.data.local.FavoritesDb;
import me.geekymind.moviedroid.data.local.MovieDao;

public class FavoritesContentProvider extends ContentProvider {

  /** The authority of this content provider. */
  public static final String AUTHORITY = "me.geekymind.moviedroid.data.provider";

  /** The URI for the Movie table. */
  public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + Movie.TABLE_NAME);

  /** The match code for some items in the Movie table. */
  private static final int CODE_MOVIE_DIR = 1;

  /** The match code for an item in the Movie table. */
  private static final int CODE_MOVIE_ITEM = 2;

  /** The URI matcher. */
  private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME, CODE_MOVIE_DIR);
    MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
  }

  @Override
  public boolean onCreate() {
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    final int code = MATCHER.match(uri);
    if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
      final Context context = getContext();
      if (context == null) {
        return null;
      }
      MovieDao movie = FavoritesDb.getInstance(context).movieDao();
      final Cursor cursor;
      if (code == CODE_MOVIE_DIR) {
        cursor = movie.selectAll();
      } else {
        cursor = movie.selectById(ContentUris.parseId(uri));
      }
      cursor.setNotificationUri(context.getContentResolver(), uri);
      return cursor;
    } else {
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    switch (MATCHER.match(uri)) {
      case CODE_MOVIE_DIR:
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + Movie.TABLE_NAME;
      case CODE_MOVIE_ITEM:
        return "vnd.android.cursor.item/" + AUTHORITY + "." + Movie.TABLE_NAME;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    switch (MATCHER.match(uri)) {
      case CODE_MOVIE_DIR:
        final Context context = getContext();
        if (context == null) {
          return null;
        }
        final long id =
            FavoritesDb.getInstance(context).movieDao().insert(Movie.fromContentValues(values));
        context.getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
      case CODE_MOVIE_ITEM:
        throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    switch (MATCHER.match(uri)) {
      case CODE_MOVIE_DIR:
        throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
      case CODE_MOVIE_ITEM:
        final Context context = getContext();
        if (context == null) {
          return 0;
        }
        final int count =
            FavoritesDb.getInstance(context).movieDao().deleteById(ContentUris.parseId(uri));
        context.getContentResolver().notifyChange(uri, null);
        return count;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    switch (MATCHER.match(uri)) {
      case CODE_MOVIE_DIR:
        throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
      case CODE_MOVIE_ITEM:
        final Context context = getContext();
        if (context == null) {
          return 0;
        }
        final Movie movie = Movie.fromContentValues(values);
        movie.setId(ContentUris.parseId(uri));
        final int count = FavoritesDb.getInstance(context).movieDao().update(movie);
        context.getContentResolver().notifyChange(uri, null);
        return count;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
  }

  @NonNull
  @Override
  public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations)
      throws OperationApplicationException {
    final Context context = getContext();
    if (context == null) {
      return new ContentProviderResult[0];
    }
    final FavoritesDb database = FavoritesDb.getInstance(context);
    database.beginTransaction();
    try {
      final ContentProviderResult[] result = super.applyBatch(operations);
      database.setTransactionSuccessful();
      return result;
    } finally {
      database.endTransaction();
    }
  }

  @Override
  public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
    switch (MATCHER.match(uri)) {
      case CODE_MOVIE_DIR:
        final Context context = getContext();
        if (context == null) {
          return 0;
        }
        final FavoritesDb database = FavoritesDb.getInstance(context);
        final Movie[] Movies = new Movie[valuesArray.length];
        for (int i = 0; i < valuesArray.length; i++) {
          Movies[i] = Movie.fromContentValues(valuesArray[i]);
        }
        return database.movieDao().insertAll(Movies).length;
      case CODE_MOVIE_ITEM:
        throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
  }
}
