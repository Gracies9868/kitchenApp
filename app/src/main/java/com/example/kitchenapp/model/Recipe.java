package com.example.kitchenapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Map;

public class Recipe implements Parcelable {
    private String id;
    private String name;
    private String imageUrl;
    private int cookingTimeMinutes;
    private int healthScore;
    private List<String> tastes;
    private List<String> components;
    private List<String> ingredients;
    private List<String> steps;
    private Map<String, String> nutrition;
    

    // 简单视图构造函数
    public Recipe(String id, String name, String imageUrl,
                  int cookingTimeMinutes, int healthScore) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.cookingTimeMinutes = cookingTimeMinutes;
        this.healthScore = healthScore;
    }

    // 详细视图构造函数
    public Recipe(String id, String name, String imageUrl,
                  int cookingTimeMinutes, int healthScore,
                  List<String> tastes, List<String> components,
                  List<String> ingredients, List<String> steps,
                  Map<String, String> nutrition) {
        this(id, name, imageUrl, cookingTimeMinutes, healthScore);
        this.tastes = tastes;
        this.components = components;
        this.ingredients = ingredients;
        this.steps = steps;
        this.nutrition = nutrition;
    }


    // Parcelable implementation
    protected Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        cookingTimeMinutes = in.readInt();
        healthScore = in.readInt();
        ingredients = in.createStringArrayList();
        steps = in.createStringArrayList();
        nutrition = in.readHashMap(String.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeInt(cookingTimeMinutes);
        dest.writeInt(healthScore);
        dest.writeStringList(ingredients);
        dest.writeStringList(steps);
        dest.writeMap(nutrition);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getCookingTimeMinutes() { return cookingTimeMinutes; }
    public void setCookingTimeMinutes(int cookingTimeMinutes) { 
        this.cookingTimeMinutes = cookingTimeMinutes; 
    }

    public int getHealthScore() { return healthScore; }
    public void setHealthScore(int healthScore) {
        this.healthScore = healthScore;
    }

    public List<String> getTastes() {  return tastes; }
    public void setTastes(List<String> tastes) {
        this.tastes = tastes;
    }

    public List<String> getComponents() {  return components; }
    public void setComponents(List<String> components) {
        this.components = components;
    }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public Map<String, String> getNutrition() { return nutrition; }
    public void setNutrition(Map<String, String> nutrition) { this.nutrition = nutrition; }

}