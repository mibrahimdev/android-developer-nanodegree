package me.geekymind.moviedroid.ui.movie;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import me.geekymind.moviedroid.data.MoviesRepository;
import me.geekymind.moviedroid.data.entity.Movie;
import me.geekymind.moviedroid.di.AppDependencies;
import me.geekymind.moviedroid.util.RxEventBus;
import me.geekymind.moviedroid.util.RxUtil;

/**
 * Created by Mohamed Ibrahim on 3/10/18.
 */
public class MovieViewModel extends ViewModel {

  private final MoviesRepository moviesRepository;
  private final Movie movie;
  private PublishSubject<Boolean> isLoading = PublishSubject.create();

  public MovieViewModel(Movie movie) {
    this.movie = movie;
    moviesRepository = AppDependencies.getMoviesRepo();
  }

  public Single<MovieDataHolder> getMovieData() {
    String movieId = String.valueOf(movie.getId());
    return Single.zip(moviesRepository.getTrailers(movieId), moviesRepository.getReviews(movieId),
        MovieDataHolder::new)
        .compose(loadingTransformer())
        .compose(RxUtil.applyLogging())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Boolean> loading() {
    return isLoading.observeOn(AndroidSchedulers.mainThread());
  }

  private <T> SingleTransformer<T, T> loadingTransformer() {
    return upstream -> upstream.doOnSubscribe(disposable -> isLoading.onNext(true))
        .doAfterTerminate(() -> isLoading.onNext(false));
  }

  public Single<Boolean> handleFavoriteAction() {
    return Observable.just(movie.isFavorite())
        .flatMapSingle(isFavorite -> {
          if (isFavorite) {
            return moviesRepository.deleteMovie(movie).toSingleDefault(false);
          } else {
            return moviesRepository.saveMovie(movie).toSingleDefault(true);
          }
        })
        .singleOrError()
        .doAfterSuccess(movie::setFavorite)
        .doFinally(() -> RxEventBus.getInstance().post(movie));
  }

  public boolean isFavorite() {
    return movie.isFavorite();
  }
}
