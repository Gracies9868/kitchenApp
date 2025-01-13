package com.example.kitchenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.content.Intent;

import com.example.kitchenapp.adapter.RecipeAdapter;
import com.example.kitchenapp.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes = new ArrayList<>();
    private EditText ingredientInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 初始化Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 隐藏默认标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        
        // 初始化RecyclerView
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(recipes, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        });
        recipeRecyclerView.setAdapter(recipeAdapter);
        
        // 初始化食材输入
        ingredientInput = findViewById(R.id.ingredientInput);
        
        // 设置搜索框点击事件
        ingredientInput.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
        
        // 禁用搜索框的编辑功能，只响应点击
        ingredientInput.setFocusable(false);
        ingredientInput.setFocusableInTouchMode(false);
        
        // 添加测试数据
        loadTestData();
    }

    private void loadTestData() {
        // 宫保鸡丁
        recipes.add(new Recipe(
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
        recipes.add(new Recipe(
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
        recipes.add(new Recipe(
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
        recipes.add(new Recipe(
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
        recipes.add(new Recipe(
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
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}