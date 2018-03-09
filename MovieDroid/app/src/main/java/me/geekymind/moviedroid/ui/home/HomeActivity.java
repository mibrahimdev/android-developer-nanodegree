package me.geekymind.moviedroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import me.geekymind.moviedroid.R;
import me.geekymind.moviedroid.data.entity.Filter;
import me.geekymind.moviedroid.databinding.ActivityHomeBinding;

/**
 * Created by Mohamed Ibrahim on 3/8/18.
 */
public class HomeActivity extends AppCompatActivity {

  private HomeViewModel homeViewModel;
  private ActivityHomeBinding viewDataBinding;
  private MoviesAdapter moviesAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    setSupportActionBar(findViewById(R.id.toolbar));
    setupRecycler();
    setupLoading();
    loadMovies();
  }

  private void setupLoading() {
    homeViewModel.loading()
        .subscribe(aBoolean -> viewDataBinding.swipeToRefresh.setRefreshing(aBoolean));
    viewDataBinding.swipeToRefresh.setOnRefreshListener(this::loadMovies);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    menu.clear();
    getMenuInflater().inflate(R.menu.menu_home, menu);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int i = item.getItemId();
    if (i == R.id.most_popular) {
      loadMovies(Filter.POPULAR);
    } else if (i == R.id.top_rated) {
      loadMovies(Filter.TOP_RATED);
    }
    return super.onOptionsItemSelected(item);
  }

  //TODO: Duplicate code need more enhancements
  private void loadMovies(String filter) {
    homeViewModel.getMovies(filter).subscribe(movies -> {
      moviesAdapter.setData(movies);
    }, throwable -> {
      Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    });
  }

  private void loadMovies() {
    homeViewModel.getMovies().subscribe(movies -> {
      moviesAdapter.setData(movies);
    }, throwable -> {
      Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    });
  }

  private void setupRecycler() {
    moviesAdapter = new MoviesAdapter();
    RecyclerView recyclerView = viewDataBinding.recyclerView;
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(moviesAdapter);
  }
}
