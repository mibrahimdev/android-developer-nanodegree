package me.geekymind.moviedroid.ui.home;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import me.geekymind.moviedroid.data.MoviesRepo;
import me.geekymind.moviedroid.data.MoviesRepository;
import me.geekymind.moviedroid.data.entity.Movie;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class HomeViewModel extends ViewModel {

  private final MoviesRepository moviesRepository;
  private PublishSubject<Boolean> isLoading = PublishSubject.create();

  public HomeViewModel() {
    moviesRepository = new MoviesRepo();
  }

  public Single<List<Movie>> getMovies() {
    return moviesRepository.getMovies()
        .doOnSubscribe(disposable -> isLoading.onNext(true))
        .compose(loadingTransformer())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Single<List<Movie>> getMovies(String filter) {
    return moviesRepository.getMovies(filter)
        .compose(loadingTransformer())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Boolean> loading() {
    return isLoading.observeOn(AndroidSchedulers.mainThread());
  }

  private <T> SingleTransformer<T, T> loadingTransformer() {
    return upstream -> upstream.doOnSubscribe(disposable -> isLoading.onNext(true))
        .doAfterSuccess(movies -> isLoading.onNext(false));
  }

  @Override

  protected void onCleared() {
    super.onCleared();
  }
}
