package me.geekymind.moviedroid.ui.home;

import android.support.annotation.IntDef;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/31/18.
 */
public class AdapterAction {

  private final Movie movie;
  private final int action;

  public AdapterAction(Movie movie, @Action int action) {
    this.movie = movie;
    this.action = action;
  }

  @IntDef({ Action.REMOVE, Action.UPDATE, Action.ADDED })
  public @interface Action {
    int UPDATE = 0;
    int REMOVE = 1;
    int ADDED = 2;
  }

  public Movie getMovie() {
    return movie;
  }

  public int getAction() {
    return action;
  }
}
