package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject nameObject = jsonObject.getJSONObject("name");

        JSONArray alsoKnownAsJsonArray = nameObject.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
        }

        String name = nameObject.getString("mainName");

        String placeOfOrigin = jsonObject.getString("placeOfOrigin");
        String description = jsonObject.getString("description");
        String image = jsonObject.getString("image");


        JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredients.add(ingredientsJsonArray.getString(i));
        }

        return new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
