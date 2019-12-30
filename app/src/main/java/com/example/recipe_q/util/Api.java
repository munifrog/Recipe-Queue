package com.example.recipe_q.util;

import com.example.recipe_q.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class Api {
    private static final String API_KEY = "apiKey=" + BuildConfig.API_KEY_SPOONACULAR;
    private static final String API_PROTOCOL = "https";
    private static final String API_HOST = "api.spoonacular.com";

    private static final String JSON_TAG_IDENTIFIER = "id";
    private static final String JSON_TAG_IMAGE_MAIN = "image";
    private static final String JSON_TAG_IMAGE_LIST = "imageUrls";
    private static final String JSON_TAG_INSTRUCTIONS_ANALYZED = "analyzedInstructions";
    private static final String JSON_TAG_JOKE_TEXT = "text";
    private static final String JSON_TAG_READY_MINUTES = "readyInMinutes";
    private static final String JSON_TAG_RECIPES_LIST = "recipes";
    private static final String JSON_TAG_RESULTS_LIST = "results";
    private static final String JSON_TAG_SERVINGS = "servings";
    private static final String JSON_TAG_STEP = "step";
    private static final String JSON_TAG_STEPS_LIST = "steps";
    private static final String JSON_TAG_TITLE = "title";

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

    private Spoonacular mSpoonacular;
    private Listener mListener;

    public interface Listener {
        void onInternetFailure(Throwable throwable);
        void onJokeRetrieved(String joke);
    }

    public Api(Listener listener) {
        mListener = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_PROTOCOL + "://" + API_HOST)
                // Default to ResponseBody for converter
                .build();

        mSpoonacular = retrofit.create(Spoonacular.class);
    }

    private void onInternetFailure(Call<ResponseBody> call, Throwable throwable) {
        mListener.onInternetFailure(throwable);
    }

    public void getRandomJoke() {
        mSpoonacular.getRandomJoke().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String json = responseBody.string();
                        JSONObject jsonJoke = new JSONObject(json);
                        String joke = jsonJoke.getString(JSON_TAG_JOKE_TEXT);
                        mListener.onJokeRetrieved(joke);
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

    public void getRecipesSimilarTo(long id) { getRecipesSimilarTo(Long.toString(id)); }
    public void getRecipesSimilarTo(String id) {
        mSpoonacular.getRecipesSimilarTo(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        JSONArray jsonEntire = new JSONArray(jsonString);

                        JSONObject currentObject;
                        long currentId;
                        String currentTitle;
                        int currentReadyMin;
                        int currentServings;
                        String currentImage;
                        JSONArray currentImageUrls;
                        int currentImageListLength;
                        String [] currentImageList;
                        int arraySize = jsonEntire.length();
                        for (int i = 0; i < arraySize; i++) {
                            currentObject = jsonEntire.getJSONObject(i);
                            if (currentObject != null) {
                                currentId = currentObject.getLong(JSON_TAG_IDENTIFIER);
                                currentTitle = currentObject.getString(JSON_TAG_TITLE);
                                currentReadyMin = currentObject.getInt(JSON_TAG_READY_MINUTES);
                                currentServings = currentObject.getInt(JSON_TAG_SERVINGS);
                                currentImage = currentObject.getString(JSON_TAG_IMAGE_MAIN);
                                currentImageUrls = currentObject.getJSONArray(JSON_TAG_IMAGE_LIST);

                                if (currentImageUrls != null) {
                                    currentImageListLength = currentImageUrls.length();
                                    currentImageList = new String[currentImageListLength];
                                    for (int j = 0; j < currentImageListLength; j++) {
                                        currentImageList[j] = currentImageUrls.getString(j);
                                    }
                                }
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

    private void getRecipeInformation(String json) {
        try {
            getRecipeInformation(new JSONObject(json));
        } catch (JSONException e) {

        }
    }
    private void getRecipeInformation(JSONObject recipe) {
        try {
            long currentId = recipe.getLong(JSON_TAG_IDENTIFIER);
            String currentTitle = recipe.getString(JSON_TAG_TITLE);
            int currentReadyMin = recipe.getInt(JSON_TAG_READY_MINUTES);
            int currentServings = recipe.getInt(JSON_TAG_SERVINGS);
            String currentImage = recipe.getString(JSON_TAG_IMAGE_MAIN);

            JSONArray currentJsonDirections;
            int numMainDirections;
            int numSubDirections;
            String [][] currentDirections = null;
            JSONObject currentMainDirection;
            JSONArray currentSubDirections;

            currentJsonDirections = recipe.getJSONArray(JSON_TAG_INSTRUCTIONS_ANALYZED);
            if (currentJsonDirections != null) {
                numMainDirections = currentJsonDirections.length();
                currentDirections = new String [numMainDirections][];

                for (int j = 0; j < numMainDirections; j++) {
                    currentMainDirection = currentJsonDirections.getJSONObject(j);
                    currentSubDirections = currentMainDirection.getJSONArray(JSON_TAG_STEPS_LIST);
                    if (currentSubDirections != null) {
                        numSubDirections = currentSubDirections.length();
                        currentDirections[j] = new String[numSubDirections];
                        for (int k = 0; k < numSubDirections; k++) {
                            JSONObject obj = currentSubDirections.getJSONObject(k);
                            currentDirections[j][k] = obj.getString(JSON_TAG_STEP);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            // TODO: Handle appropriately
        }
    }

    public void getRecipesRandomPopular(int howMany) {
        mSpoonacular.getRecipesRandomPopular(howMany).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        JSONObject jsonEntire = new JSONObject(jsonString);
                        if (jsonEntire != null) {
                            JSONArray recipeArray = jsonEntire.getJSONArray(JSON_TAG_RECIPES_LIST);
                            if (recipeArray != null) {
                                int numRecipes = recipeArray.length();
                                JSONObject currentObject;
                                for (int i = 0; i < numRecipes; i++) {
                                    currentObject = recipeArray.getJSONObject(i);
                                    getRecipeInformation(currentObject);
                                }
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

    public void getRecipesSpecific(long id) { getRecipesSpecific(Long.toString(id)); }
    public void getRecipesSpecific(String id) {
        mSpoonacular.getRecipesSpecific(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        getRecipeInformation(jsonString);
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

    public void getRecipesSpecificList(long [] idList) {
        if (idList != null && idList.length > 0) {
            String list = Long.toString(idList[0]);
            for (int i = 1; i < idList.length; i++) {
                list += "," + idList[i];
            }
            getRecipesSpecificList(list);
        }
    }
    public void getRecipesSpecificList(String commaSeparatedList) {
        mSpoonacular.getRecipesSpecificList(commaSeparatedList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
        mSpoonacular.getRecipesComplexSearch(searchTerms).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        String jsonString = responseBody.string();
                        JSONObject searchResults  =new JSONObject(jsonString);
                        JSONArray foundRecipes = searchResults.getJSONArray(JSON_TAG_RESULTS_LIST);
                        if (foundRecipes != null) {
                            int recipeCount = foundRecipes.length();
                            JSONObject currentRecipe;
                            long currentId;
                            String currentTitle;
                            for (int i =0; i < recipeCount; i++) {
                                currentRecipe = foundRecipes.getJSONObject(i);
                                currentId = currentRecipe.getLong(JSON_TAG_IDENTIFIER);
                                currentTitle = currentRecipe.getString(JSON_TAG_TITLE);
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

    private interface Spoonacular {
        @GET("food/jokes/random/?" + API_KEY)
        Call<ResponseBody> getRandomJoke();

        @GET("recipes/{id}/similar/?" + API_KEY)
        Call<ResponseBody> getRecipesSimilarTo(@Path("id") String recipeId);

        @GET("recipes/random/?" + API_KEY)
        Call<ResponseBody> getRecipesRandomPopular(@Query("number") int howMany);

        @GET("recipes/{id}/information/?" + API_KEY)
        Call<ResponseBody> getRecipesSpecific(@Path("id") String recipeId);

        @GET("recipes/informationBulk/?" + API_KEY)
        Call<ResponseBody> getRecipesSpecificList(@Query("ids") String idList);

        @GET("recipes/complexSearch/?" + API_KEY)
        Call<ResponseBody> getRecipesComplexSearch(@QueryMap Map<String, String> queries);
    }
}
