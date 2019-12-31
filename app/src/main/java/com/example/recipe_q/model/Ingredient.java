package com.example.recipe_q.model;

public class Ingredient {
    private String mIngredientName;
    private float mAmount;
    private String mUnit;
    private String mImage;

    public Ingredient(
            String ingredientName,
            float amount,
            String unit,
            String image
    ) {
        mIngredientName = ingredientName;
        mAmount = amount;
        mUnit = unit;
        mImage = image;
    }

    public String getIngredientName() { return mIngredientName; }
    public void setIngredientName(String ingredientName) { this.mIngredientName = ingredientName; }

    public float getAmount() { return mAmount; }
    public void setAmount(float amount) { this.mAmount = amount; }

    public String getUnit() { return mUnit; }
    public void setUnit(String unit) { this.mUnit = unit; }

    public String getImage() { return mImage; }
    public void setImage(String image) { this.mImage = image; }
}
