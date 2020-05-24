package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.adapter.MyMealAdapter;
import com.tungmr.hintfoodanddrinks.db.MealDBHelper;
import com.tungmr.hintfoodanddrinks.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class AdminViewMeal extends AppCompatActivity {

    private GridView gridView;
    private List<Meal> meals;
    MyMealAdapter myMealAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_meal);
        setControl();
        setValue();
        setEvent();
    }

    private void setControl(){
        gridView = findViewById(R.id.girdViewMeals);
    }

    private void setValue(){
        MealDBHelper mealDBHelper = MealDBHelper.getInstance(getApplicationContext());
        mealDBHelper.open();
        meals = new ArrayList<>();
        meals = mealDBHelper.getAllMeal(1);
        mealDBHelper.close();
    }

    private void setEvent(){
        myMealAdapter = new MyMealAdapter(getApplicationContext(), R.layout.meal_item, meals);
        gridView.setAdapter(myMealAdapter);
        myMealAdapter.notifyDataSetChanged();
    }
}
