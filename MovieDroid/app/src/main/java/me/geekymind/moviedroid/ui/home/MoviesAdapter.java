package me.geekymind.moviedroid.ui.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.moviedroid.R;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.databinding.GridItemMovieBinding;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

  private final List<Movie> movies = new ArrayList<>();

  public void setData(List<Movie> movies) {
    this.movies.clear();
    this.movies.addAll(movies);
    notifyDataSetChanged();
  }

  @Override
  public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    GridItemMovieBinding itemBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.grid_item_movie, parent, false);
    return new MoviesViewHolder(itemBinding);
  }

  @Override
  public void onBindViewHolder(MoviesViewHolder holder, int position) {
    Movie movie = movies.get(position);
    holder.bind(movie);
  }

  @Override
  public int getItemCount() {
    return movies.size();
  }

  class MoviesViewHolder extends RecyclerView.ViewHolder {
    private final GridItemMovieBinding binding;

    MoviesViewHolder(GridItemMovieBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(Movie movie) {
      binding.movieTitle.setText(movie.getTitle());
      binding.releaseYear.setText(movie.getReleaseDate());
      Picasso.get().setLoggingEnabled(true);
      Picasso.get().load(movie.getPosterPath()).into(binding.moviePoster);

    }
  }
}
