package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.role.RoleManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.adapter.MyMealAdapter;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.db.MealDBHelper;
import com.tungmr.hintfoodanddrinks.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class AdminViewMeal extends AppCompatActivity {

    private GridView gridView;
    private List<Meal> meals;
    MyMealAdapter myMealAdapter = null;

    private Integer iDItem = -1;

    private TextView textViewTitle;

    private String ROLE;


    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_meal);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ROLE = preferences.getString(getString(R.string.role), null);
        setControl();
        setValue();
        setEvent();
    }

    private void setControl() {
//        textViewTitle = findViewById(R.id.textViewTitleMeal);
        gridView = findViewById(R.id.girdViewMeals);
    }

    private void setValue() {
        MealDBHelper mealDBHelper = MealDBHelper.getInstance(getApplicationContext());
        mealDBHelper.open();
        if (ROLE.equals(CoreConstants.ROLE_ADMIN)) {
            meals = mealDBHelper.getAllMeal();
        } else if (ROLE.equals(CoreConstants.ROLE_USER)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            category = preferences.getString(getString(R.string.categoryPicked), null);
            //  textViewTitle.setText(category + " meal");
            meals = mealDBHelper.getAllMealByCategory(category, 1);
        }
        mealDBHelper.close();

    }

    private void setEvent() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (category != null)
                getSupportActionBar().setTitle(category + " meal");
            else getSupportActionBar().setTitle("List meals");
        }


        myMealAdapter = new MyMealAdapter(getApplicationContext(), R.layout.meal_item, meals);
        gridView.setAdapter(myMealAdapter);
        myMealAdapter.notifyDataSetChanged();

        if (ROLE.equals(CoreConstants.ROLE_ADMIN)) {
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
        } else if (ROLE.equals(CoreConstants.ROLE_USER)) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Long pickedId = meals.get(position).getMealId();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(getString(R.string.pickedId), String.valueOf(pickedId));
                    editor.commit();
                    Intent intent = new Intent(AdminViewMeal.this, DetailMeal.class);
                    startActivity(intent);

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
