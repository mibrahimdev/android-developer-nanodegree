package me.geekymind.moviedroid.ui.home;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
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
import me.geekymind.moviedroid.databinding.ItemGridMovieBinding;
import me.geekymind.moviedroid.ui.movie.MovieActivity;

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

  public void updateItem(Movie movie) {
    for (int i = 0; i < movies.size(); i++) {
      Movie item = movies.get(i);
      if (movie.getId().equals(item.getId())) {
        //TODO: there is a bug when use notifyItemChanged with image transition
        item.setFavorite(movie.isFavorite());
        notifyItemChanged(i);
        break;
      }
    }
  }

  public void removeItem(Movie movie) {
    for (int i = 0; i < movies.size(); i++) {
      Movie item = movies.get(i);
      if (movie.getId().equals(item.getId())) {
        //TODO: there is a bug when use notifyItemChanged with image transition
        movies.remove(i);
        notifyItemRemoved(i);
        break;
      }
    }
  }

  public void addItem(Movie movie) {
    movies.add(movie);
    notifyItemInserted(movies.size() - 1);
  }

  @Override
  public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ItemGridMovieBinding itemBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.item_grid_movie, parent, false);
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
    private final ItemGridMovieBinding binding;

    MoviesViewHolder(ItemGridMovieBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(Movie movie) {
      Context context = binding.movieTitle.getContext();
      Resources resources = context.getResources();
      int defaultColor = resources.getColor(R.color.blue_grey_900);
      binding.movieTitle.setText(movie.getTitle());
      binding.releaseYear.setText(movie.getReleaseDate());
      binding.isFavorite.setVisibility(movie.isFavorite() ? View.VISIBLE : View.GONE);
      binding.posterCard.setOnClickListener(v -> {
        View sharedView = binding.moviePoster;
        String transitionName = resources.getString(R.string.poster);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
          ActivityOptions transitionActivityOptions =
              ActivityOptions.makeSceneTransitionAnimation((HomeActivity) context, sharedView,
                  transitionName);
          context.startActivity(MovieActivity.startMovieActivityIntent(context, movie),
              transitionActivityOptions.toBundle());
        } else {
          context.startActivity(MovieActivity.startMovieActivityIntent(context, movie));
        }
      });

      Picasso.get().load(movie.getCorrectPosterPath()).into(new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
          Palette.from(bitmap).generate(asyncListener -> {
            int darkMutedColor = asyncListener.getDarkMutedColor(defaultColor);
            binding.textsLayout.setBackgroundColor(darkMutedColor);
            binding.posterCard.setCardBackgroundColor(darkMutedColor);
            binding.moviePoster.setImageDrawable(new BitmapDrawable(resources, bitmap));
          });
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
          binding.moviePoster.setImageDrawable(
              resources.getDrawable(R.drawable.ic_error_outline_white_24dp));
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
          binding.moviePoster.setImageDrawable(
              resources.getDrawable(R.drawable.ic_local_movies_white_24dp));
        }
      });
    }
  }
}
