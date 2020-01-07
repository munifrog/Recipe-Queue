package com.example.recipe_q.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.List;

import static com.example.recipe_q.model.DirectionConverter.convertToDirectionGroupList;
import static com.example.recipe_q.model.IngredientConverter.convertToIngredientList;

@Entity(tableName = "recipes")
public class Recipe implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id_api")
    private long mIdSpoonacular;

    @ColumnInfo(name = "image_url")
    private String mImage;

    @ColumnInfo(name = "source_orig")
    private String mSourceUrl;

    @ColumnInfo(name = "source_api")
    private String mSourceUrlSpoonacular;

    @ColumnInfo(name = "ready_in")
    private int mReadyInMinutes;

    @ColumnInfo(name = "servings")
    private float mServings;

    @ColumnInfo(name = "name")
    private String mRecipeTitle;

    @ColumnInfo(name = "ingredients")
    private List<Ingredient> mIngredients;

    @ColumnInfo(name = "directions")
    private List<DirectionGroup> mDirections;

    @ColumnInfo(name = "timestamp")
    private long mRetrievalTime;

    @Ignore
    public Recipe(
            long id,
            String sourceUrl,
            String spoonacularSourceUrl,
            String image,
            int readyInMinutes,
            float servings,
            String title,
            List<Ingredient> ingredients,
            List<DirectionGroup> directions
    ) {
        this(
                id,
                sourceUrl,
                spoonacularSourceUrl,
                image,
                readyInMinutes,
                servings,
                title,
                ingredients,
                directions,
                Calendar.getInstance().getTimeInMillis()
        );
    }

    public Recipe(
            long idSpoonacular,
            String sourceUrl,
            String sourceUrlSpoonacular,
            String image,
            int readyInMinutes,
            float servings,
            String recipeTitle,
            List<Ingredient> ingredients,
            List<DirectionGroup> directions,
            long retrievalTime
    ) {
        mIdSpoonacular = idSpoonacular;
        mSourceUrl = sourceUrl;
        mSourceUrlSpoonacular = sourceUrlSpoonacular;
        mImage = image;
        mReadyInMinutes = readyInMinutes;
        mServings = servings;
        mRecipeTitle = recipeTitle;
        mIngredients = ingredients;
        mDirections = directions;
        mRetrievalTime = retrievalTime;
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

    public List<Ingredient> getIngredients() { return mIngredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.mIngredients = ingredients; }

    public List<DirectionGroup> getDirections() { return mDirections; }
    public void setDirections(List<DirectionGroup> directions) { this.mDirections = directions; }

    public long getRetrievalTime() { return mRetrievalTime; }
    public void setRetrievalTime(long timestamp) { this.mRetrievalTime = timestamp; }

    public FavoriteRecipe asFavoriteRecipe() {
        return new FavoriteRecipe(
                mIdSpoonacular,
                mRecipeTitle,
                mImage
        );
    }

    @Ignore
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe [] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mIdSpoonacular);
        dest.writeString(mImage);
        dest.writeString(mSourceUrl);
        dest.writeString(mSourceUrlSpoonacular);
        dest.writeInt(mReadyInMinutes);
        dest.writeFloat(mServings);
        dest.writeString(mRecipeTitle);
        dest.writeString(IngredientConverter.convertToString(mIngredients));
        dest.writeString(DirectionConverter.convertToString(mDirections));
    }

    @Ignore
    private Recipe(Parcel parcel) {
        setIdSpoonacular(parcel.readLong());
        setImage(parcel.readString());
        setSourceUrl(parcel.readString());
        setSourceUrlSpoonacular(parcel.readString());
        setReadyInMinutes(parcel.readInt());
        setServings(parcel.readFloat());
        setRecipeTitle(parcel.readString());
        setIngredients(convertToIngredientList(parcel.readString()));
        setDirections(convertToDirectionGroupList(parcel.readString()));
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
}
