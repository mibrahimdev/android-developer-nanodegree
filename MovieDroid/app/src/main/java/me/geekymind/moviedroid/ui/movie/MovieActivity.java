package me.geekymind.moviedroid.ui.movie;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import me.geekymind.moviedroid.R;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.databinding.ActivityMovieBinding;

/**
 * Created by Mohamed Ibrahim on 3/10/18.
 */
public class MovieActivity extends AppCompatActivity {

  private static final String MOVIE_EXTRA_KEY = "movie_";
  private ActivityMovieBinding activityMovieBinding;

  public static Intent startMovieActivityIntent(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieActivity.class);
    intent.putExtra(MOVIE_EXTRA_KEY, movie);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
    setupToolbar();

    Movie movie = getIntent().getParcelableExtra(MOVIE_EXTRA_KEY);

    Picasso.get().load(movie.getPosterPath()).into(new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        BitmapDrawable imageDrawable = new BitmapDrawable(getResources(), bitmap);
        activityMovieBinding.moviePosterBackground.setBackground(imageDrawable);
      }

      @Override
      public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        activityMovieBinding.moviePosterBackground.setBackgroundColor(
            getResources().getColor(R.color.blue_grey_700));
      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {
        activityMovieBinding.moviePosterBackground.setBackgroundColor(
            getResources().getColor(R.color.blue_grey_700));
      }
    });

    Picasso.get()
        .load(movie.getPosterPath())
        .placeholder(R.drawable.ic_local_movies_white_24dp)
        .error(R.drawable.ic_error_outline_white_24dp)
        .into(activityMovieBinding.moviePoster);
    activityMovieBinding.movieTitle.setText(movie.getTitle());
    activityMovieBinding.movieDescription.setText(movie.getOverview());
    //TODO: it would be nice if we make it with stars icons, represent the rate
    activityMovieBinding.movieRate.setText(String.format("%s/10", movie.getVoteAverage()));
    activityMovieBinding.movieReleaseDate.setText(movie.getReleaseDate());
  }

  private void setupToolbar() {
    setSupportActionBar((Toolbar) activityMovieBinding.toolbarMovie);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_movie_activity, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int i = item.getItemId();
    switch (i) {
      case android.R.id.home:
        onBackPressed();
        break;
      case R.id.action_share_movie:
        break;
      case R.id.action_favorite:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
