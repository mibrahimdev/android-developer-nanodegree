package me.geekymind.moviedroid.ui.movie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.moviedroid.R;
import me.geekymind.moviedroid.data.entity.Trailer;
import me.geekymind.moviedroid.databinding.ItemTrailerBinding;
import me.geekymind.moviedroid.util.AndroidUtil;

/**
 * Created by Mohamed Ibrahim on 3/23/18.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

  private List<Trailer> trailers = new ArrayList<>();

  public void setData(List<Trailer> trailers) {
    this.trailers.clear();
    this.trailers.addAll(trailers);
    notifyDataSetChanged();
  }

  @Override
  public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ItemTrailerBinding itemBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.item_trailer, parent, false);
    return new TrailerViewHolder(itemBinding);
  }

  @Override
  public void onBindViewHolder(TrailerViewHolder holder, int position) {
    Trailer trailer = trailers.get(position);
    holder.bindData(trailer);
  }

  @Override
  public int getItemCount() {
    return trailers.size();
  }

  class TrailerViewHolder extends RecyclerView.ViewHolder {
    private final ItemTrailerBinding itemTrailerBinding;
    private final Context context;

    TrailerViewHolder(ItemTrailerBinding itemView) {
      super(itemView.getRoot());
      itemTrailerBinding = itemView;
      context = itemTrailerBinding.trailerCard.getContext();
    }

    void bindData(Trailer trailer) {
      itemTrailerBinding.trailerName.setText(trailer.getName());
      itemTrailerBinding.trailerCard.setOnClickListener(v -> {
        AndroidUtil.watchYoutubeVideo(trailer.getKey(), context);
      });
    }
  }
}
