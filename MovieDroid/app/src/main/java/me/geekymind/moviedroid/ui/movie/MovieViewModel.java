package me.geekymind.moviedroid.ui.movie;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import me.geekymind.moviedroid.data.MoviesRepository;
import me.geekymind.moviedroid.data.entity.Review;
import me.geekymind.moviedroid.data.entity.Trailer;
import me.geekymind.moviedroid.di.AppDependencies;
import me.geekymind.moviedroid.util.RxUtil;

/**
 * Created by Mohamed Ibrahim on 3/10/18.
 */
public class MovieViewModel extends ViewModel {

  private final MoviesRepository moviesRepository;
  private final String movieId;
  private PublishSubject<Boolean> isLoading = PublishSubject.create();

  public MovieViewModel(Long movieId) {
    this.movieId = String.valueOf(movieId);
    moviesRepository = AppDependencies.getMoviesRepo();
  }

  public Single<MovieDataHolder> getMovieData() {
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
}
