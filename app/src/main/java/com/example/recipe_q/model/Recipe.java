package com.example.recipe_q.model;

public class Recipe {
    private long mIdSpoonacular;
    private String mImage;
    private String mSourceUrl;
    private String mSourceUrlSpoonacular;
    private int mReadyInMinutes;
    private float mServings;
    private String mRecipeTitle;

    public Recipe(
            long id,
            String sourceUrl,
            String spoonacularSourceUrl,
            String image,
            int readyInMinutes,
            float servings,
            String title
    ) {
        mIdSpoonacular = id;
        mSourceUrl = sourceUrl;
        mSourceUrlSpoonacular = spoonacularSourceUrl;
        mImage = image;
        mReadyInMinutes = readyInMinutes;
        mServings = servings;
        mRecipeTitle = title;
    }

    public long getIdSpoonacular() { return mIdSpoonacular; }
    public void setIdSpoonacular(long idSpoonacular) { this.mIdSpoonacular = idSpoonacular; }

    public String getImage() { return mImage; }
    public void setImage(String image) { this.mImage = image; }

    public String getSourceUrl() { return mSourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.mSourceUrl = sourceUrl; }

    public String getSourceUrlSpoonacular() { return mSourceUrlSpoonacular; }
    public void setSourceUrlSpoonacular(String sourceUrlSpoonacular) { this.mSourceUrlSpoonacular = sourceUrlSpoonacular; }

    public int getReadyInMinutes() { return mReadyInMinutes; }
    public void setReadyInMinutes(int readyInMinutes) { this.mReadyInMinutes = readyInMinutes; }

    public float getServings() { return mServings; }
    public void setServings(float servings) { this.mServings = servings; }

    public String getRecipeTitle() { return mRecipeTitle; }
    public void setRecipeTitle(String title) { this.mRecipeTitle = title; }
}
