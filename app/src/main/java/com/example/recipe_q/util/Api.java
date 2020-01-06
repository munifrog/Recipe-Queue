package com.example.recipe_q.util;

import com.example.recipe_q.BuildConfig;
import com.example.recipe_q.model.DirectionGroup;
import com.example.recipe_q.model.Ingredient;
import com.example.recipe_q.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.example.recipe_q.model.DirectionConverter.convertToDirectionGroupList;
import static com.example.recipe_q.model.IngredientConverter.convertToIngredientList;

public class Api {
    private static final String API_KEY = "apiKey=" + BuildConfig.API_KEY_SPOONACULAR;
    private static final String API_PROTOCOL = "https";
    private static final String API_HOST = "api.spoonacular.com";

    public static final String ENDPOINT_FREE_BASE = "https://spoonacular.com/";
    private static final String ENDPOINT_IMAGE_SIZE_INFORMATION = "556x370";
    private static final String ENDPOINT_IMAGE_SIZE_DESIRED = "312x231";
    public static final String ENDPOINT_IMAGE_INGREDIENT = "https://spoonacular.com/cdn/ingredients_100x100/%1$s";
    private static final String ENDPOINT_IMAGE_RECIPE = "https://spoonacular.com/recipeImages/%1$d-" + ENDPOINT_IMAGE_SIZE_DESIRED + "%2$s";

    public static final String JSON_TAG_DIRECTION_GROUP_NAME = "name";
    public static final String JSON_TAG_DIRECTION_GROUP_STEPS = "steps";
    public static final String JSON_TAG_DIRECTION_SINGLE_STEP = "step";
    private static final String JSON_TAG_IDENTIFIER = "id";
    private static final String JSON_TAG_IMAGE_MAIN = "image";
    public static final String JSON_TAG_INGREDIENT_AMOUNT = "amount";
    public static final String JSON_TAG_INGREDIENT_IMAGE = "image";
    private static final String JSON_TAG_INGREDIENTS_MISSED = "missedIngredients";
    private static final String JSON_TAG_INGREDIENTS_EXTENDED = "extendedIngredients";
    public static final String JSON_TAG_INGREDIENT_NAME = "name";
    public static final String JSON_TAG_INGREDIENT_UNIT = "unit";
    private static final String JSON_TAG_INSTRUCTIONS_ANALYZED = "analyzedInstructions";
    private static final String JSON_TAG_JOKE_TEXT = "text";
    private static final String JSON_TAG_READY_MINUTES = "readyInMinutes";
    private static final String JSON_TAG_RECIPES_LIST = "recipes";
    private static final String JSON_TAG_RESULTS_LIST = "results";
    private static final String JSON_TAG_SERVINGS = "servings";
    private static final String JSON_TAG_SOURCE_URL_ORIGINAL = "sourceUrl";
    private static final String JSON_TAG_SOURCE_URL_SPOONACULAR = "spoonacularSourceUrl";
    private static final String JSON_TAG_TITLE = "title";

    public static final String QUERY_COMPLEX_COUNT_NUMBER = "number";
    public static final String QUERY_COMPLEX_CUISINE_INCLUDE = "cuisine";
    public static final String QUERY_COMPLEX_CUISINE_EXCLUDE = "excludeCuisine";
    public static final String QUERY_COMPLEX_DIET = "diet";
    public static final String QUERY_COMPLEX_INGREDIENTS_EXCLUDE = "excludeIngredients";
    public static final String QUERY_COMPLEX_INGREDIENTS_FILL = "fillIngredients";
    public static final String QUERY_COMPLEX_INGREDIENTS_INCLUDE = "includeIngredients";
    public static final String QUERY_COMPLEX_INTOLERANCE = "intolerances";
    public static final String QUERY_COMPLEX_MEAL_TYPE = "type";
    public static final String QUERY_COMPLEX_MEASURE_ALCOHOL_MIN = "minAlcohol";
    public static final String QUERY_COMPLEX_MEASURE_ALCOHOL_MAX = "maxAlcohol";
    public static final String QUERY_COMPLEX_MEASURE_CAFFEINE_MIN = "minCaffeine";
    public static final String QUERY_COMPLEX_MEASURE_CAFFEINE_MAX = "maxCaffeine";
    public static final String QUERY_COMPLEX_MEASURE_CALCIUM_MIN = "minCalcium";
    public static final String QUERY_COMPLEX_MEASURE_CALCIUM_MAX = "maxCalcium";
    public static final String QUERY_COMPLEX_MEASURE_CALORY_MIN = "minCalories";
    public static final String QUERY_COMPLEX_MEASURE_CALORY_MAX = "maxCalories";
    public static final String QUERY_COMPLEX_MEASURE_CARBOHYDRATE_MIN = "minCarbs";
    public static final String QUERY_COMPLEX_MEASURE_CARBOHYDRATE_MAX = "maxCarbs";
    public static final String QUERY_COMPLEX_MEASURE_CHOLESTEROL_MIN = "minCholesterol";
    public static final String QUERY_COMPLEX_MEASURE_CHOLESTEROL_MAX = "maxCholesterol";
    public static final String QUERY_COMPLEX_MEASURE_CHOLINE_MIN = "minCholine";
    public static final String QUERY_COMPLEX_MEASURE_CHOLINE_MAX = "maxCholine";
    public static final String QUERY_COMPLEX_MEASURE_COPPER_MIN = "minCopper";
    public static final String QUERY_COMPLEX_MEASURE_COPPER_MAX = "maxCopper";
    public static final String QUERY_COMPLEX_MEASURE_FAT_MIN = "minFat";
    public static final String QUERY_COMPLEX_MEASURE_FAT_MAX = "maxFat";
    public static final String QUERY_COMPLEX_MEASURE_FAT_SATURATED_MIN = "minSaturatedFat";
    public static final String QUERY_COMPLEX_MEASURE_FAT_SATURATED_MAX = "maxSaturatedFat";
    public static final String QUERY_COMPLEX_MEASURE_FIBER_MIN = "minFiber";
    public static final String QUERY_COMPLEX_MEASURE_FIBER_MAX = "maxFiber";
    public static final String QUERY_COMPLEX_MEASURE_FLUORIDE_MIN = "minFluoride";
    public static final String QUERY_COMPLEX_MEASURE_FLUORIDE_MAX = "maxFluoride";
    public static final String QUERY_COMPLEX_MEASURE_FOLATE_MIN = "minFolate";
    public static final String QUERY_COMPLEX_MEASURE_FOLATE_MAX = "maxFolate";
    public static final String QUERY_COMPLEX_MEASURE_FOLIC_ACID_MIN = "minFolicAcid";
    public static final String QUERY_COMPLEX_MEASURE_FOLIC_ACID_MAX = "maxFolicAcid";
    public static final String QUERY_COMPLEX_MEASURE_IODINE_MIN = "minIodine";
    public static final String QUERY_COMPLEX_MEASURE_IODINE_MAX = "maxIodine";
    public static final String QUERY_COMPLEX_MEASURE_IRON_MIN = "minIron";
    public static final String QUERY_COMPLEX_MEASURE_IRON_MAX = "maxIron";
    public static final String QUERY_COMPLEX_MEASURE_MAGNESIUM_MIN = "minMagnesium";
    public static final String QUERY_COMPLEX_MEASURE_MAGNESIUM_MAX = "maxMagnesium";
    public static final String QUERY_COMPLEX_MEASURE_MANGANESE_MIN = "minManganese";
    public static final String QUERY_COMPLEX_MEASURE_MANGANESE_MAX = "maxManganese";
    public static final String QUERY_COMPLEX_MEASURE_PHOSPHORUS_MIN = "minPhosphorus";
    public static final String QUERY_COMPLEX_MEASURE_PHOSPHORUS_MAX = "maxPhosphorus";
    public static final String QUERY_COMPLEX_MEASURE_POTASSIUM_MIN = "minPotassium";
    public static final String QUERY_COMPLEX_MEASURE_POTASSIUM_MAX = "maxPotassium";
    public static final String QUERY_COMPLEX_MEASURE_PROTEIN_MIN = "minProtein";
    public static final String QUERY_COMPLEX_MEASURE_PROTEIN_MAX = "maxProtein";
    public static final String QUERY_COMPLEX_MEASURE_SELENIUM_MIN = "minSelenium";
    public static final String QUERY_COMPLEX_MEASURE_SELENIUM_MAX = "maxSelenium";
    public static final String QUERY_COMPLEX_MEASURE_SODIUM_MIN = "minSodium";
    public static final String QUERY_COMPLEX_MEASURE_SODIUM_MAX = "maxSodium";
    public static final String QUERY_COMPLEX_MEASURE_SUGAR_MIN = "minSugar";
    public static final String QUERY_COMPLEX_MEASURE_SUGAR_MAX = "maxSugar";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_A_MIN = "minVitaminA";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_A_MAX = "maxVitaminA";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B01_MIN = "minVitaminB1";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B01_MAX = "maxVitaminB1";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B02_MIN = "minVitaminB2";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B02_MAX = "maxVitaminB2";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B03_MIN = "minVitaminB3";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B03_MAX = "maxVitaminB3";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B05_MIN = "minVitaminB5";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B05_MAX = "maxVitaminB5";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B06_MIN = "minVitaminB6";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B06_MAX = "maxVitaminB6";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B12_MIN = "minVitaminB12";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_B12_MAX = "maxVitaminB12";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_C_MIN = "minVitaminC";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_C_MAX = "maxVitaminC";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_D_MIN = "minVitaminD";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_D_MAX = "maxVitaminD";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_E_MIN = "minVitaminE";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_E_MAX = "maxVitaminE";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_K_MIN = "minVitaminK";
    public static final String QUERY_COMPLEX_MEASURE_VITAMIN_K_MAX = "maxVitaminK";
    public static final String QUERY_COMPLEX_MEASURE_ZINC_MIN = "minZinc";
    public static final String QUERY_COMPLEX_MEASURE_ZINC_MAX = "maxZinc";
    public static final String QUERY_COMPLEX_PANTRY_IGNORE = "ignorePantry";
    public static final String QUERY_COMPLEX_RECIPE_ADD_INFO = "addRecipeInformation";
    public static final String QUERY_COMPLEX_REQUIRE_INSTRUCTIONS = "instructionsRequired";

    private static final String HEADER_QUOTA_USED_TODAY = "X-API-Quota-Used";

    private Spoonacular mSpoonacular;
    private BaseListener mListener;
    private float mQuota;

    public interface BaseListener {
        void onInternetFailure(Throwable throwable);
    }

    public interface JokeListener extends BaseListener {
        void onJokeRetrieved(String joke);
    }

    public interface RecipeListener extends BaseListener {
        void onRecipesReturned(List<Recipe> recipes);
    }

    public interface RecipeInfoListener extends BaseListener {
        void onInformationReturned(Recipe recipe);
    }

    public Api(BaseListener listener) {
        mListener = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_PROTOCOL + "://" + API_HOST)
                // Default to ResponseBody for converter
                .build();

        mSpoonacular = retrofit.create(Spoonacular.class);
        mQuota = 0;
    }

    public float getQuotaUsed() { return mQuota; }
    private void updateQuota(Headers headers) {
        String quotaUsed = headers.get(HEADER_QUOTA_USED_TODAY);
        mQuota = quotaUsed == null ? 0 : Float.parseFloat(quotaUsed);
    }

    private void onInternetFailure(Call<ResponseBody> call, Throwable throwable) {
        mListener.onInternetFailure(throwable);
    }

    public void getRandomJoke() {
        if (!(mListener instanceof JokeListener)) {
            throw new ClassCastException(mListener.getClass().getSimpleName() +
                    " does not implement " + JokeListener.class.getName()
            );
        }

        mSpoonacular.getRandomJoke().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateQuota(response.headers());
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String json = responseBody.string();
                        JSONObject jsonJoke = new JSONObject(json);
                        String joke = jsonJoke.getString(JSON_TAG_JOKE_TEXT);
                        ((JokeListener) mListener).onJokeRetrieved(joke);
                    } catch (JSONException e) {
                        // TODO: Handle appropriately
                    } catch (IOException e) {
                        // TODO: Handle appropriately
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onInternetFailure(call, t);
            }
        });
    }

    public void getRecipesSimilarTo(long id, int number) { getRecipesSimilarTo(Long.toString(id), number); }
    public void getRecipesSimilarTo(String id, int number) {
        mSpoonacular.getRecipesSimilarTo(id, number).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateQuota(response.headers());
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        List<Recipe> recipes = parseSimilar(jsonString);
                        ((RecipeListener) mListener).onRecipesReturned(recipes);
                    } catch (IOException e) {
                        // TODO: Handle appropriately
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onInternetFailure(call, t);
            }
        });
    }

    private Recipe getRecipeInformation(String json) {
        try {
            return getRecipeInformation(new JSONObject(json));
        } catch (JSONException e) {
            // TODO: Handle appropriately
        }
        return null;
    }
    private Recipe getRecipeInformation(JSONObject recipe) {
        Recipe newRecipe = null;
        try {
            long currentId = recipe.getLong(JSON_TAG_IDENTIFIER);
            String currentTitle = recipe.getString(JSON_TAG_TITLE);
            int currentReadyMin = recipe.getInt(JSON_TAG_READY_MINUTES);
            int currentServings = recipe.getInt(JSON_TAG_SERVINGS);
            String currentImage = recipe.getString(JSON_TAG_IMAGE_MAIN);
            currentImage = currentImage.replace(ENDPOINT_IMAGE_SIZE_INFORMATION, ENDPOINT_IMAGE_SIZE_DESIRED);
            String currentSourceUrl = recipe.getString(JSON_TAG_SOURCE_URL_ORIGINAL);
            String currentSourceUrlSpoonacular = recipe.getString(JSON_TAG_SOURCE_URL_SPOONACULAR);
            JSONArray currentIngredients;
            List<Ingredient> ingredients;
            try {
                currentIngredients = recipe.getJSONArray(JSON_TAG_INGREDIENTS_EXTENDED);
                ingredients = convertToIngredientList(currentIngredients);
            } catch (JSONException e) {
                try {
                    currentIngredients = recipe.getJSONArray(JSON_TAG_INGREDIENTS_MISSED);
                    ingredients = convertToIngredientList(currentIngredients);
                } catch (JSONException ex) {
                    ingredients = new ArrayList<>();
                }
            }
            JSONArray currentJsonDirections = recipe.getJSONArray(JSON_TAG_INSTRUCTIONS_ANALYZED);
            List<DirectionGroup> directions = convertToDirectionGroupList(currentJsonDirections);
            newRecipe = new Recipe(
                    currentId,
                    currentSourceUrl,
                    currentSourceUrlSpoonacular,
                    currentImage,
                    currentReadyMin,
                    currentServings,
                    currentTitle,
                    ingredients,
                    directions
            );
        } catch (JSONException e) {
            // TODO: Handle appropriately
        }
        return newRecipe;
    }

    public void getRecipesRandomPopular(int howMany) {
        mSpoonacular.getRecipesRandomPopular(howMany).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateQuota(response.headers());
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        JSONObject jsonEntire = new JSONObject(jsonString);
                        JSONArray recipeArray = jsonEntire.getJSONArray(JSON_TAG_RECIPES_LIST);
                        if (recipeArray != null) {
                            int numRecipes = recipeArray.length();
                            JSONObject currentObject;
                            for (int i = 0; i < numRecipes; i++) {
                                currentObject = recipeArray.getJSONObject(i);
                                getRecipeInformation(currentObject);
                            }
                        }
                    } catch (JSONException e) {
                        // TODO: Handle appropriately
                    } catch (IOException e) {
                        // TODO: Handle appropriately
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onInternetFailure(call, t);
            }
        });
    }

    public void getRecipeSpecific(long id) { getRecipeSpecific(Long.toString(id)); }
    public void getRecipeSpecific(String id) {
        if (!(mListener instanceof RecipeInfoListener)) {
            throw new ClassCastException(mListener.getClass().getSimpleName() +
                    " does not implement " + RecipeInfoListener.class.getName()
            );
        }

        mSpoonacular.getRecipeSpecific(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateQuota(response.headers());
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        Recipe recipe = getRecipeInformation(jsonString);
                        ((RecipeInfoListener) mListener).onInformationReturned(recipe);
                    } catch (IOException e) {
                        // TODO: Handle appropriately
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onInternetFailure(call, t);
            }
        });
    }

    public void getRecipeSpecificList(long [] idList) {
        if (idList != null && idList.length > 0) {
            String list = Long.toString(idList[0]);
            for (int i = 1; i < idList.length; i++) {
                list += "," + idList[i];
            }
            getRecipeSpecificList(list);
        }
    }
    public void getRecipeSpecificList(String commaSeparatedList) {
        mSpoonacular.getRecipeSpecificList(commaSeparatedList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateQuota(response.headers());
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        JSONArray recipeArray = new JSONArray(jsonString);
                        int numRecipes = recipeArray.length();
                        JSONObject currentObject;
                        for (int i = 0; i < numRecipes; i++) {
                            currentObject = recipeArray.getJSONObject(i);
                            getRecipeInformation(currentObject);
                        }
                    } catch (JSONException e) {
                        // TODO: Handle appropriately
                    } catch (IOException e) {
                        // TODO: Handle appropriately
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onInternetFailure(call, t);
            }
        });
    }

    public void getRecipesComplexSearch(Map<String, String> searchTerms) {
        if (!(mListener instanceof RecipeListener)) {
            throw new ClassCastException(mListener.getClass().getSimpleName() +
                    " does not implement " + RecipeListener.class.getName()
            );
        }

        mSpoonacular.getRecipesComplexSearch(searchTerms).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateQuota(response.headers());
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        List<Recipe> recipes = parseComplex(responseBody.string());
                        ((RecipeListener) mListener).onRecipesReturned(recipes);
                    } catch (IOException e) {
                        // TODO: Handle appropriately
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onInternetFailure(call, t);
            }
        });
    }

    private List<Recipe> parseComplex(String json) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONObject searchResults = new JSONObject(json);
            JSONArray foundRecipes = searchResults.getJSONArray(JSON_TAG_RESULTS_LIST);
            if (foundRecipes != null) {
                int recipeCount = foundRecipes.length();
                JSONObject currentRecipe;
                Recipe oneRecipe;
                for (int i =0; i < recipeCount; i++) {
                    currentRecipe = foundRecipes.getJSONObject(i);
                    oneRecipe = getRecipeInformation(currentRecipe);
                    recipes.add(oneRecipe);
                }
            }
        } catch (JSONException e) {
            // TODO: Handle appropriately
        }
        return recipes;
    }

    private List<Recipe> parseSimilar(String json) {
        {
            List<Recipe> recipes = new ArrayList<>();
            try {
                JSONArray foundRecipes = new JSONArray(json);
                int recipeCount = foundRecipes.length();
                JSONObject currentRecipe;
                long currentId;
                String currentImageUrl;
                String currentImageName;
                String currentImageExtension;
                int currentReadyInMinutes;
                int currentServings;
                String currentTitle;
                Recipe oneRecipe;
                for (int i = 0; i < recipeCount; i++) {
                    currentRecipe = foundRecipes.getJSONObject(i);
                    currentId = currentRecipe.getLong(JSON_TAG_IDENTIFIER);
                    currentImageName = currentRecipe.getString(JSON_TAG_IMAGE_MAIN);
                    currentImageExtension = currentImageName.substring(currentImageName.lastIndexOf("."));
                    currentImageUrl = String.format(Locale.getDefault(), ENDPOINT_IMAGE_RECIPE, currentId, currentImageExtension);
                    currentReadyInMinutes = currentRecipe.getInt(JSON_TAG_READY_MINUTES);
                    currentServings = currentRecipe.getInt(JSON_TAG_SERVINGS);
                    currentTitle = currentRecipe.getString(JSON_TAG_TITLE);
                    oneRecipe = new Recipe(
                            currentId,
                            null,
                            null,
                            currentImageUrl,
                            currentReadyInMinutes,
                            currentServings,
                            currentTitle,
                            new ArrayList<Ingredient>(),
                            new ArrayList<DirectionGroup>()
                    );
                    recipes.add(oneRecipe);
                }
            } catch (JSONException e) {
                // TODO: Handle appropriately
            }
            return recipes;
        }
    }

    private interface Spoonacular {
        @GET("food/jokes/random/?" + API_KEY)
        Call<ResponseBody> getRandomJoke();

        @GET("recipes/{id}/similar/?" + API_KEY)
        Call<ResponseBody> getRecipesSimilarTo(@Path("id") String recipeId, @Query("number") int howMany);

        @GET("recipes/random/?" + API_KEY)
        Call<ResponseBody> getRecipesRandomPopular(@Query("number") int howMany);

        @GET("recipes/{id}/information/?" + API_KEY)
        Call<ResponseBody> getRecipeSpecific(@Path("id") String recipeId);

        @GET("recipes/informationBulk/?" + API_KEY)
        Call<ResponseBody> getRecipeSpecificList(@Query("ids") String idList);

        @GET("recipes/complexSearch/?" + API_KEY)
        Call<ResponseBody> getRecipesComplexSearch(@QueryMap Map<String, String> queries);
    }
}
