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
            alsoKnownAs.add(alsoKnownAsJsonArray.optString(i));
        }

        String name = nameObject.optString("mainName");

        String placeOfOrigin = jsonObject.optString("placeOfOrigin");
        String description = jsonObject.optString("description");
        String image = jsonObject.optString("image");


        JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredients.add(ingredientsJsonArray.optString(i));
        }

        return new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
