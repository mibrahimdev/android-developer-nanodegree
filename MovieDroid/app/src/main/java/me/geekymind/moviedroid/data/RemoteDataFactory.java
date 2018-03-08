package me.geekymind.moviedroid.data;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import me.geekymind.moviedroid.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public class RemoteDataFactory {

  private static final String BASE_URL = "http://api.themoviedb.org/3/";

  public static MovieRemote newMovieRemote() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    return retrofit.create(MovieRemote.class);
  }

  private static OkHttpClient getOkHttpClient() {
    final OkHttpClient.Builder clientBuilder =
        new OkHttpClient.Builder().addInterceptor(loggingInterceptor());
    return clientBuilder.build();
  }

  private static HttpLoggingInterceptor loggingInterceptor() {
    return new HttpLoggingInterceptor().setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
  }
}
