package me.geekymind.moviedroid.ui.home;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.IntDef;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import me.geekymind.moviedroid.data.MoviesRepo;
import me.geekymind.moviedroid.data.MoviesRepository;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.di.AppDependencies;
import me.geekymind.moviedroid.util.RxEventBus;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class HomeViewModel extends ViewModel {

  private final MoviesRepository moviesRepository;
  private PublishSubject<Boolean> isLoading = PublishSubject.create();
  private List<Movie> movies = Collections.emptyList();
  private @Filter
  String filter;

  public HomeViewModel() {
    moviesRepository = AppDependencies.getMoviesRepo();
  }

  public Single<List<Movie>> getMovies() {
    return movies.isEmpty() ? moviesRepository.getMovies()
        .doOnSuccess(state -> movies = state)
        .compose(loadingTransformer())
        .observeOn(AndroidSchedulers.mainThread()) : Single.just(movies);
  }

  public Single<List<Movie>> getMovies(String filter) {
    return movies.isEmpty() || !Objects.equals(filter, this.filter) ?
        moviesRepository.getMovies(filter)
        .doOnSuccess(state -> movies = state)
        .doOnSuccess(state -> this.filter = filter)
        .compose(loadingTransformer())
        .observeOn(AndroidSchedulers.mainThread()) : Single.just(movies);
  }

  public Observable<Boolean> loading() {
    return isLoading.observeOn(AndroidSchedulers.mainThread());
  }

  private <T> SingleTransformer<T, T> loadingTransformer() {
    return upstream -> upstream.doOnSubscribe(disposable -> isLoading.onNext(true))
        .doAfterTerminate(() -> isLoading.onNext(false));
  }

  public Observable<AdapterAction> onUpdatedMovie() {
    return RxEventBus.getInstance().filteredObservable(Movie.class).flatMap(movie -> {
      if (moviesRepository.getFilter().equals(Filter.FAVORITES)) {
        return Observable.just(new AdapterAction(movie,
            movie.isFavorite() ? AdapterAction.Action.ADDED : AdapterAction.Action.REMOVE));
      } else {
        return Observable.just(new AdapterAction(movie, AdapterAction.Action.UPDATE));
      }
    }).observeOn(AndroidSchedulers.mainThread());
  }

  @Override

  protected void onCleared() {
    super.onCleared();
  }
}
