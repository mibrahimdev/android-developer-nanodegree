package me.geekymind.moviedroid.ui.movie;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.moviedroid.R;
import me.geekymind.moviedroid.data.entity.Review;
import me.geekymind.moviedroid.databinding.ItemReviewBinding;
import me.geekymind.moviedroid.databinding.ItemTrailerBinding;

/**
 * Created by Mohamed Ibrahim on 3/23/18.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

  private final List<Review> reviews = new ArrayList<>();

  public void setData(List<Review> reviews) {
    this.reviews.clear();
    this.reviews.addAll(reviews);
    notifyDataSetChanged();
  }

  @Override
  public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ItemReviewBinding itemBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.item_review, parent, false);
    return new ReviewViewHolder(itemBinding);
  }

  @Override
  public void onBindViewHolder(ReviewViewHolder holder, int position) {
    Review review = reviews.get(position);
    holder.bindData(review);
  }

  @Override
  public int getItemCount() {
    return reviews.size();
  }

  class ReviewViewHolder extends RecyclerView.ViewHolder {

    private final ItemReviewBinding binding;

    ReviewViewHolder(ItemReviewBinding itemView) {
      super(itemView.getRoot());
      binding = itemView;
    }

    void bindData(Review review) {
      binding.author.setText(review.getAuthor());
      binding.review.setText(review.getContent());
    }
  }
}
