package me.geekymind.moviedroid.ui.home;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import me.geekymind.moviedroid.data.MoviesRepo;
import me.geekymind.moviedroid.data.MoviesRepository;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class HomeViewModel extends ViewModel {

  private final MoviesRepository moviesRepository;
  private @Filter
  String filter;

  public HomeViewModel() {
    moviesRepository = new MoviesRepo();
  }

  public Single<List<Movie>> getMovies() {
    return moviesRepository.getMovies().observeOn(AndroidSchedulers.mainThread());
  }

  public Single<List<Movie>> getMovies(String filter) {
    return moviesRepository.getMovies(filter).observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
