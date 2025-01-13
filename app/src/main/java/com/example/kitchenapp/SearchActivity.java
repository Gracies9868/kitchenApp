package com.example.kitchenapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;

import com.example.kitchenapp.adapter.SearchResultAdapter;
import com.example.kitchenapp.model.Recipe;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    private EditText searchInput;
    private RecyclerView recipeResultsView;
    private ChipGroup timeFilterGroup;
    private ChipGroup tasteFilterGroup;
    private ChipGroup healthFilterGroup;
    private SearchResultAdapter searchResultAdapter;
    private List<Recipe> allRecipes; // 存储所有食谱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setupFilters();

        // 初始化RecyclerView
        recipeResultsView.setLayoutManager(new LinearLayoutManager(this));
        searchResultAdapter = new SearchResultAdapter(new ArrayList<>(), recipe -> {
            // 跳转到食谱详情页
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        });
        recipeResultsView.setAdapter(searchResultAdapter);

        // 加载测试数据
        loadTestRecipes();

        // 默认显示所有菜品
        performSearch("");
    }

    private void initViews() {
        searchInput = findViewById(R.id.searchInput);
        recipeResultsView = findViewById(R.id.recipeResultsView);
        timeFilterGroup = findViewById(R.id.timeFilterGroup);
        tasteFilterGroup = findViewById(R.id.tasteFilterGroup);
        healthFilterGroup = findViewById(R.id.healthFilterGroup);

        // 设置搜索按钮点击事件
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchInput.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void setupFilters() {
        // 时间筛选
        String[] timeFilters = {"15分钟以内", "15-30分钟", "30分钟以上"};
        for (String filter : timeFilters) {
            addChip(timeFilterGroup, filter);
        }

        // 口味筛选
        String[] tasteFilters = {"甜", "酸甜", "香辣", "酱香", "清淡"};
        for (String filter : tasteFilters) {
            addChip(tasteFilterGroup, filter);
        }

        // 健康筛选
        String[] healthFilters = {"低糖低脂", "少油少盐", "高蛋白", "无过敏原"};
        for (String filter : healthFilters) {
            addChip(healthFilterGroup, filter);
        }

        // 在setupFilters()方法中，设置时间筛选ChipGroup为单选模式
        timeFilterGroup.setSingleSelection(true);
    }

    private void addChip(ChipGroup group, String text) {
        Chip chip = new Chip(this);
        chip.setText(text);
        chip.setCheckable(true);
        chip.setClickable(true);
        chip.setCheckedIconVisible(true);

        // 设置样式
        chip.setChipBackgroundColorResource(R.color.chip_background_color);
        chip.setChipStrokeColorResource(R.color.chip_stroke_color);
        chip.setChipStrokeWidth(getResources().getDimension(R.dimen.chip_stroke_width));
        chip.setTextColor(getResources().getColorStateList(R.color.chip_text_color));

        // 设置内边距
        int padding = (int) getResources().getDimension(R.dimen.chip_padding);
        chip.setChipStartPadding(padding);
        chip.setChipEndPadding(padding);

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateResults();
        });

        group.addView(chip);
    }

    private void updateResults() {
        String query = searchInput.getText().toString();
        performSearch(query);
    }

    private List<String> getSelectedChips(ChipGroup group) {
        List<String> selected = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            Chip chip = (Chip) group.getChildAt(i);
            if (chip.isChecked()) {
                selected.add(chip.getText().toString());
            }
        }
        return selected;
    }

    private void performSearch(String query) {
        List<Recipe> searchResults = new ArrayList<>();
        
        // 获取搜索条件
        String ingredientQuery = searchInput.getText().toString();
        List<String> selectedTimeFilters = getSelectedChips(timeFilterGroup);
        List<String> selectedTasteFilters = getSelectedChips(tasteFilterGroup);
        List<String> selectedComponentFilters = getSelectedChips(healthFilterGroup);

        // 如果没有设置任何条件，显示全部
        if (ingredientQuery.isEmpty() && 
            selectedTimeFilters.isEmpty() &&
            selectedTasteFilters.isEmpty() &&
            selectedComponentFilters.isEmpty()) {
            searchResults.addAll(allRecipes);
        } else {
            // 匹配逻辑
            for (Recipe recipe : allRecipes) {
                if (matchesAllCriteria(recipe, ingredientQuery, 
                    selectedTimeFilters, selectedTasteFilters, selectedComponentFilters)) {
                    searchResults.add(recipe);
                }
            }
        }

        searchResultAdapter.updateData(searchResults);
    }

    private boolean matchesAllCriteria(Recipe recipe, String ingredientQuery, 
        List<String> timeFilters, List<String> tasteFilters, List<String> componentFilters) {
        
        // 匹配食材
        if (!ingredientQuery.isEmpty() && !matchesIngredients(recipe, ingredientQuery)) {
            return false;
        }
        
        // 匹配时间
        if (!timeFilters.isEmpty() && !matchesTimeFilter(recipe, timeFilters)) {
            return false;
        }
        
        // 匹配口味
        if (!tasteFilters.isEmpty() && !matchesTasteFilter(recipe, tasteFilters)) {
            return false;
        }
        
        // 匹配成分
        if (!componentFilters.isEmpty() && !matchesComponentFilter(recipe, componentFilters)) {
            return false;
        }
        
        return true;
    }

    private boolean matchesIngredients(Recipe recipe, String query) {
        // 使用正则表达式分割多种食材
        String[] ingredients = query.split("[、，, ]+");
        
        // 检查所有食材是否都在菜谱中
        for (String ingredient : ingredients) {
            boolean found = false;
            for (String recipeIngredient : recipe.getIngredients()) {
                if (recipeIngredient.toLowerCase().contains(ingredient.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesTimeFilter(Recipe recipe, List<String> timeFilters) {
        int cookingTime = recipe.getCookingTimeMinutes();
        for (String filter : timeFilters) {
            switch (filter) {
                case "15分钟以内":
                    if (cookingTime <= 15) return true;
                    break;
                case "15-30分钟":
                    if (cookingTime > 15 && cookingTime <= 30) return true;
                    break;
                case "30分钟以上":
                    if (cookingTime > 30) return true;
                    break;
            }
        }
        return false;
    }

    private boolean matchesTasteFilter(Recipe recipe, List<String> tasteFilters) {
        for (String taste : recipe.getTastes()) {
            if (tasteFilters.contains(taste)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesComponentFilter(Recipe recipe, List<String> componentFilters) {
        for (String component : recipe.getComponents()) {
            if (componentFilters.contains(component)) {
                return true;
            }
        }
        return false;
    }


    private void loadTestRecipes() {
        allRecipes = new ArrayList<>();

        // 宫保鸡丁
        allRecipes.add(new Recipe(
            "1",
            "宫保鸡丁",
            "https://bkimg.cdn.bcebos.com/pic/d043ad4bd11373f082029662ad565cfbfbedaa64ae8f?x-bce-process=image/format,f_auto/quality,Q_70/resize,m_lfit,limit_1,w_536",
            30,
            85,
            Arrays.asList("香辣", "酱香"),
            Arrays.asList("高蛋白"),
            Arrays.asList("鸡胸肉 300g", "花生米 50g", "胡萝卜 1根", "黄瓜 1根"),
            Arrays.asList(
                "鸡胸肉切丁，用料酒、生抽、淀粉腌制15分钟",
                "花生米炒熟备用",
                "胡萝卜、黄瓜切丁",
                "热锅下油，爆香姜蒜",
                "加入鸡丁翻炒至变色",
                "加入胡萝卜、黄瓜丁翻炒",
                "加入干辣椒、花生米",
                "加入调味料翻炒均匀",
                "最后撒上葱花即可"
            ),
            new HashMap<String, String>() {{
                put("卡路里", "350kcal");
                put("蛋白质", "25g");
                put("脂肪", "15g");
            }}
        ));

        // 麻婆豆腐
        allRecipes.add(new Recipe(
            "2",
            "麻婆豆腐",
            "https://bkimg.cdn.bcebos.com/pic/dbb44aed2e738bd4b31c837855d390d6277f9e2f537b?x-bce-process=image/format,f_auto/quality,Q_70/resize,m_lfit,limit_1,w_536",
            25,
            78,
            Arrays.asList("麻辣", "酱香"),
            Arrays.asList("多辣"),
            Arrays.asList("豆腐 400g", "猪肉末 200g", "郫县豆瓣酱 2勺"),
            Arrays.asList(
                "豆腐切块，用开水焯烫",
                "热锅下油，爆香蒜末",
                "加入猪肉末翻炒至变色",
                "加入豆瓣酱炒出香味",
                "加入适量清水",
                "放入豆腐块",
                "加入调味料",
                "勾芡",
                "撒上花椒粉和葱花即可"
            ),
            new HashMap<String, String>() {{
                put("卡路里", "280kcal");
                put("蛋白质", "18g");
                put("脂肪", "12g");
            }}
        ));

        // 番茄炒蛋
        allRecipes.add(new Recipe(
            "3",
            "番茄炒蛋",
            "https://bkimg.cdn.bcebos.com/pic/b8389b504fc2d56285358397e04887ef76c6a7efc60a?x-bce-process=image/format,f_auto/quality,Q_70/resize,m_lfit,limit_1,w_536",
            15,
            92,
            Arrays.asList("酸甜", "清淡"),
            Arrays.asList("低糖低脂", "少油少盐", "无过敏原"),
            Arrays.asList("番茄 2个", "鸡蛋 3个"),
            Arrays.asList(
                "番茄切块，鸡蛋打散",
                "热锅下油，先炒鸡蛋至凝固",
                "盛出鸡蛋，炒番茄至出汁",
                "加入鸡蛋翻炒均匀",
                "调味后即可出锅"
            ),
            new HashMap<String, String>() {{
                put("卡路里", "200kcal");
                put("蛋白质", "12g");
                put("脂肪", "8g");
            }}
        ));

        // 肉末茄子
        allRecipes.add(new Recipe(
            "4",
            "肉末茄子",
            "https://bkimg.cdn.bcebos.com/pic/267f9e2f070828381f309e5831c1be014c086e065b7c?x-bce-process=image/format,f_auto/quality,Q_70/resize,m_lfit,limit_1,w_536",
            25,
            88,
            Arrays.asList("酱香", "咸鲜"),
            Arrays.asList("多油多盐"),
            Arrays.asList("茄子 2根", "猪肉末 200g", "蒜 3瓣", "生抽 适量"),
            Arrays.asList(
                "茄子切条，撒盐腌制10分钟",
                "热锅下油，爆香蒜末",
                "加入猪肉末翻炒至变色",
                "加入茄子翻炒",
                "加入调味料",
                "焖煮5分钟",
                "收汁后即可出锅"
            ),
            new HashMap<String, String>() {{
                put("卡路里", "320kcal");
                put("蛋白质", "18g");
                put("脂肪", "20g");
            }}
        ));

        // 蛋花汤
        allRecipes.add(new Recipe(
            "5",
            "蛋花汤",
            "https://bkimg.cdn.bcebos.com/pic/4ec2d5628535e5dde7113962b08cb0efce1b9d16c471?x-bce-process=image/format,f_auto/watermark,image_d2F0ZXIvYmFpa2UyNzI,g_7,xp_5,yp_5,P_20/resize,m_lfit,limit_1,h_1080",
            10,
            95,
            Arrays.asList("清淡"),
            Arrays.asList("低糖低脂", "少油少盐", "无过敏原"),
            Arrays.asList("鸡蛋 2个", "水 500ml", "葱花 适量"),
            Arrays.asList(
                "水烧开",
                "鸡蛋打散",
                "缓慢倒入蛋液，同时用筷子搅拌",
                "加入盐和葱花",
                "煮沸后即可出锅"
            ),
            new HashMap<String, String>() {{
                put("卡路里", "80kcal");
                put("蛋白质", "6g");
                put("脂肪", "5g");
            }}
        ));
    }
} 