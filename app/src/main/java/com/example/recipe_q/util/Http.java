package com.example.recipe_q.util;

import com.example.recipe_q.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Http {
    private static final String API_KEY = "apiKey=" + BuildConfig.API_KEY_SPOONACULAR;
    private static final String API_PROTOCOL = "https";
    private static final String API_HOST = "api.spoonacular.com";

    private static final String JSON_TAG_JOKE_TEXT = "text";
    private static final String JSON_TAG_IDENTIFIER = "id";
    private static final String JSON_TAG_TITLE = "title";
    private static final String JSON_TAG_READY_MINUTES = "readyInMinutes";
    private static final String JSON_TAG_SERVINGS = "servings";
    private static final String JSON_TAG_IMAGE_MAIN = "image";
    private static final String JSON_TAG_IMAGE_LIST = "imageUrls";

    private Spoonacular mSpoonacular;
    private Listener mListener;

    public interface Listener {
        void onInternetFailure(Throwable throwable);
        void onJokeRetrieved(String joke);
    }

    public Http(Listener listener) {
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

    private interface Spoonacular {
        @GET("food/jokes/random/?" + API_KEY)
        Call<ResponseBody> getRandomJoke();

        @GET("recipes/{id}/similar/?" + API_KEY)
        Call<ResponseBody> getRecipesSimilarTo(@Path("id") String recipeId);
    }
}
