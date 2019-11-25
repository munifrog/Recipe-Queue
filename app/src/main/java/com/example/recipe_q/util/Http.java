package com.example.recipe_q.util;

import com.example.recipe_q.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class Http {
    private static final String API_KEY = "apiKey=" + BuildConfig.API_KEY_SPOONACULAR;
    private static final String API_PROTOCOL = "https";
    private static final String API_HOST = "api.spoonacular.com";

    private static final String JSON_TAG_JOKE_TEXT = "text";

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
                mListener.onInternetFailure(t);
            }
        });
    }

    private interface Spoonacular {
        @GET("food/jokes/random/?" + API_KEY)
        Call<ResponseBody> getRandomJoke();
    }
}
