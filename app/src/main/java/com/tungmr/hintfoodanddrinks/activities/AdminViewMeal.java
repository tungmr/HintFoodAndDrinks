package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
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

    private Integer iDItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_meal);
        setControl();
        setValue();
        setEvent();
    }

    private void setControl() {
        gridView = findViewById(R.id.girdViewMeals);
    }

    private void setValue() {
        MealDBHelper mealDBHelper = MealDBHelper.getInstance(getApplicationContext());
        mealDBHelper.open();
        meals = mealDBHelper.getAllMeal();
        mealDBHelper.close();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long pickedId = meals.get(position).getMealId();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.pickedId), String.valueOf(pickedId));
                editor.commit();
                Intent intent = new Intent(AdminViewMeal.this, ViewAMeal.class);
                startActivity(intent);

            }
        });
    }

    private void setEvent() {
        myMealAdapter = new MyMealAdapter(getApplicationContext(), R.layout.meal_item, meals);
        gridView.setAdapter(myMealAdapter);
        myMealAdapter.notifyDataSetChanged();
    }
}
