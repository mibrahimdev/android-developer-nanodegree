package me.geekymind.moviedroid.util;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Mohamed Ibrahim on 3/9/18.
 */
public class RxUtil {

  public static <T> SingleTransformer<T, T> applyLogging() {
    return upstream -> upstream.doOnError(Timber::e);
  }
}
