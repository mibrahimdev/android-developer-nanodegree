package me.geekymind.moviedroid.data;

import com.google.gson.GsonBuilder;
import java.io.IOException;
import me.geekymind.moviedroid.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public class RemoteDataFactory {

  private RemoteDataFactory() {
  }

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
        new OkHttpClient.Builder().addInterceptor(new AuthorizationInterceptor())
            .addInterceptor(loggingInterceptor());
    return clientBuilder.build();
  }

  private static HttpLoggingInterceptor loggingInterceptor() {
    return new HttpLoggingInterceptor().setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
  }

  private static class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request original = chain.request();
      HttpUrl originalHttpUrl = original.url();
      HttpUrl url =
          originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build();

      Request.Builder requestBuilder = original.newBuilder().url(url);
      Request request = requestBuilder.build();
      return chain.proceed(request);
    }
  }
}
