package com.example.recipe_q.model;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.recipe_q.util.Api.ENDPOINT_FREE_BASE;
import static com.example.recipe_q.util.Api.ENDPOINT_IMAGE_INGREDIENT;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_AMOUNT;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_IMAGE;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_NAME;
import static com.example.recipe_q.util.Api.JSON_TAG_INGREDIENT_UNIT;

public class IngredientConverter {
    @TypeConverter
    public static String convertToString(@NonNull List<Ingredient> ingredients) {
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

    @TypeConverter
    public static List<Ingredient> convertToIngredientList(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return convertToIngredientList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Ingredient> convertToIngredientList(JSONArray jsonArray) {
        List<Ingredient> ingredients = new ArrayList<>();
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
                if (!currentImage.startsWith(ENDPOINT_FREE_BASE)) {
                    currentImage = String.format(Locale.getDefault(), ENDPOINT_IMAGE_INGREDIENT, currentImage);
                }
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
