package me.geekymind.moviedroid.ui.home;

import android.arch.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import me.geekymind.moviedroid.data.MoviesRepo;
import me.geekymind.moviedroid.data.MoviesRepository;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class HomeViewModel extends ViewModel {

  private final MoviesRepository moviesRepository;
  private Disposable getMoviesDisposable;

  public HomeViewModel() {
    moviesRepository = new MoviesRepo();
  }

  public Disposable getMovies(Consumer<List<Movie>> resultConsumer,
      Consumer<Throwable> errorConsumer) {
    getMoviesDisposable = moviesRepository.getMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(resultConsumer, errorConsumer);
    return getMoviesDisposable;
  }

  @Override
  protected void onCleared() {
    if (getMoviesDisposable != null) {
      getMoviesDisposable.dispose();
    }
    super.onCleared();
  }
}
