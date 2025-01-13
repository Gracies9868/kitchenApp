package com.example.kitchenapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kitchenapp.R;
import java.util.Map;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.ViewHolder> {
    private Map<String, String> nutritionMap;

    public void setNutritionMap(Map<String, String> nutritionMap) {
        this.nutritionMap = nutritionMap;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nutrition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = (String) nutritionMap.keySet().toArray()[position];
        String value = nutritionMap.get(key);
        holder.label.setText(key);
        holder.value.setText(value);
    }

    @Override
    public int getItemCount() {
        return nutritionMap != null ? nutritionMap.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView value;

        ViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.nutritionLabel);
            value = itemView.findViewById(R.id.nutritionValue);
        }
    }
} 