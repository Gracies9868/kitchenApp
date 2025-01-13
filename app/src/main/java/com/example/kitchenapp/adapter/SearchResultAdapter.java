package com.example.kitchenapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kitchenapp.R;
import com.example.kitchenapp.model.Recipe;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<Recipe> matchedRecipes;
    private final OnRecipeClickListener listener;

    public SearchResultAdapter(List<Recipe> matchedRecipes, OnRecipeClickListener listener) {
        this.matchedRecipes = matchedRecipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(matchedRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return matchedRecipes.size();
    }

    public void updateData(List<Recipe> newMatchedRecipes) {
        this.matchedRecipes = newMatchedRecipes;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView recipeImage;
        private final TextView recipeName;
        private final TextView cookingTime;
        private final TextView matchRate;

        ViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeName = itemView.findViewById(R.id.recipeName);
            cookingTime = itemView.findViewById(R.id.cookingTime);
            matchRate = itemView.findViewById(R.id.matchRate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onRecipeClick(matchedRecipes.get(position));
                }
            });
        }

        void bind(Recipe recipe) {
            recipeName.setText(recipe.getName());
            cookingTime.setText(String.format("%d分钟", recipe.getCookingTimeMinutes()));

            Glide.with(itemView.getContext())
                    .load(recipe.getImageUrl())
                    .placeholder(R.drawable.placeholder_recipe)
                    .error(R.drawable.error_recipe)
                    .centerCrop()
                    .into(recipeImage);
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }
} 