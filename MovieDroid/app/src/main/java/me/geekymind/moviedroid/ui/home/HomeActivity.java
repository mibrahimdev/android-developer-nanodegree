package me.geekymind.moviedroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import me.geekymind.moviedroid.R;
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
    setupRecycler();
    setupObservables();
  }

  private void setupRecycler() {
    moviesAdapter = new MoviesAdapter();
    RecyclerView recyclerView = viewDataBinding.recyclerView;
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(moviesAdapter);
  }

  private void setupObservables() {
    homeViewModel.getMovies(movies -> {
      moviesAdapter.setData(movies);
    }, throwable -> {
      Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    });
  }
}
