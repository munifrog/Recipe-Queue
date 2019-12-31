package com.example.recipe_q.model;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_AMOUNT;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_IMAGE;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_NAME;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_UNIT;

public class IngredientConverter {
    public static String convertToString(@NonNull ArrayList<Ingredient> ingredients) {
        JSONArray jsonArray = new JSONArray();
        Ingredient current;
        JSONObject jsonObject;
        int size = ingredients.size();
        for (int i = 0; i < size; i++) {
            current = ingredients.get(i);
            jsonObject = convertToJsonObject(current);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    private static JSONObject convertToJsonObject(Ingredient ingredient) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JSON_TAG_INGREDIENT_NAME, ingredient.getIngredientName());
            jsonObject.put(JSON_TAG_INGREDIENT_AMOUNT, ingredient.getAmount());
            jsonObject.put(JSON_TAG_INGREDIENT_UNIT, ingredient.getUnit());
            jsonObject.put(JSON_TAG_INGREDIENT_IMAGE, ingredient.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static ArrayList<Ingredient> convertToIngredientArrayList(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return convertToIngredientArrayList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static ArrayList<Ingredient> convertToIngredientArrayList(JSONArray jsonArray) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            JSONObject currentJsonObject;
            float currentAmount;
            String currentImage;
            String currentName;
            String currentUnit;
            Ingredient currentIngredient;
            int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                currentJsonObject = jsonArray.getJSONObject(i);
                currentAmount = (float) currentJsonObject.getDouble(JSON_TAG_INGREDIENT_AMOUNT);
                currentImage = currentJsonObject.getString(JSON_TAG_INGREDIENT_IMAGE);
                currentName = currentJsonObject.getString(JSON_TAG_INGREDIENT_NAME);
                currentUnit = currentJsonObject.getString(JSON_TAG_INGREDIENT_UNIT);
                currentIngredient = new Ingredient(
                        currentName,
                        currentAmount,
                        currentUnit,
                        currentImage
                );
                ingredients.add(currentIngredient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
}
