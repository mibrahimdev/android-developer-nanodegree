package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvOrigin;
    private TextView tvDescription;
    private TextView tvIngredients;
    private TextView tvAlsoKnown;
    private Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvAlsoKnown = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if (sandwich != null) {
            String placeOfOrigin = sandwich.getPlaceOfOrigin();
            boolean isValidPlaceString = placeOfOrigin != null && !placeOfOrigin.isEmpty();
            tvOrigin.setText(isValidPlaceString ? placeOfOrigin : getString(R.string.empty_data_message));
            tvDescription.setText(sandwich.getDescription());

            List<String> ingredients = sandwich.getIngredients();
            if (ingredients != null && ingredients.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < ingredients.size(); i++) {
                    stringBuilder.append(ingredients.get(i)).append("\n");
                }
                tvIngredients.setText(stringBuilder);
            } else {
                tvIngredients.setText(getString(R.string.empty_data_message));
            }

            List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
            if (alsoKnownAs != null && alsoKnownAs.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < alsoKnownAs.size(); i++) {
                    stringBuilder.append(alsoKnownAs.get(i)).append("\n");
                }
                tvAlsoKnown.setText(stringBuilder);
            } else {
                tvAlsoKnown.setText(getString(R.string.empty_data_message));
            }
        }
    }
}
