package com.example.kitchenapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kitchenapp.adapter.StepAdapter;
import com.example.kitchenapp.adapter.IngredientAdapter;
import com.example.kitchenapp.adapter.NutritionAdapter;
import com.example.kitchenapp.model.Recipe;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class RecipeDetailActivity extends AppCompatActivity {
    
    private ImageView recipeImage;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;
    private RecyclerView nutritionRecyclerView;
    private IngredientAdapter ingredientAdapter;
    private StepAdapter stepAdapter;
    private NutritionAdapter nutritionAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        
        // 初始化视图
        initViews();
        
        // 获取传递的食谱数据
        Recipe recipe = getIntent().getParcelableExtra("recipe");
        if (recipe != null) {
            displayRecipeDetails(recipe);
        }
    }
    
    private void initViews() {
        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // 初始化视图
        recipeImage = findViewById(R.id.recipeImage);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        nutritionRecyclerView = findViewById(R.id.nutritionRecyclerView);
        
        // 设置RecyclerView
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nutritionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        ingredientAdapter = new IngredientAdapter();
        stepAdapter = new StepAdapter();
        nutritionAdapter = new NutritionAdapter();
        
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        stepsRecyclerView.setAdapter(stepAdapter);
        nutritionRecyclerView.setAdapter(nutritionAdapter);
    }
    
    private void displayRecipeDetails(Recipe recipe) {
        // 设置标题
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(recipe.getName());
        
        // 加载图片
        Glide.with(this)
            .load(recipe.getImageUrl())
            .placeholder(R.drawable.placeholder_recipe)
            .error(R.drawable.error_recipe)
            .centerCrop()
            .into(recipeImage);
        
        // 更新食材列表
        ingredientAdapter.updateIngredients(recipe.getIngredients());
        
        // 更新步骤列表
        stepAdapter.updateSteps(recipe.getSteps());
        
        if (recipe.getNutrition() != null) {
            nutritionAdapter.setNutritionMap(recipe.getNutrition());
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 