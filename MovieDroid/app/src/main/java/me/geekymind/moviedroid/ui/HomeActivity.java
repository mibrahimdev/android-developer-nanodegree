package me.geekymind.moviedroid.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import me.geekymind.moviedroid.data.MovieRemote;
import me.geekymind.moviedroid.data.RemoteDataFactory;
import me.geekymind.moviedroid.data.entity.MoviedbResponse;
import me.geekymind.moviedroid.data.entity.SortOrder;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public class HomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    MovieRemote movieRemote = RemoteDataFactory.newMovieRemote();

    movieRemote.getMovies(SortOrder.POPULAR)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(moviedbResponse -> {

        }, throwable -> {

        });
  }
}
