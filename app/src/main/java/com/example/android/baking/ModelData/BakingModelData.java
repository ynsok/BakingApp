package com.example.android.baking.ModelData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Krzys on 01.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class BakingModelData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<IngredientsModelData> ingredients;
    @SerializedName("steps")
    @Expose
    private List<StepModelData> steps;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsModelData> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsModelData> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepModelData> getSteps() {
        return steps;
    }

    public void setSteps(List<StepModelData> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

