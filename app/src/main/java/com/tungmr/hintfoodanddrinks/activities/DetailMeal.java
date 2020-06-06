package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.MealDBHelper;
import com.tungmr.hintfoodanddrinks.model.Meal;

public class DetailMeal extends AppCompatActivity {

    private TextView tvMealName, tvMealDes, tvMealCat, tvProtein, tvFat, tvMinerals, tvVitamins, tvCarbohydrate, tvCalories;
    private ImageView imageView;

    private Meal mealView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meal);
        setControl();
        setEvent();

    }

    private void setControl() {
        tvMealName = findViewById(R.id.textViewDetailMealName);
        tvMealDes = findViewById(R.id.textViewDetailMealDes);
        tvMealCat = findViewById(R.id.textViewDetailMealCategory);
        imageView = findViewById(R.id.imageViewDetailMealImage);
        tvProtein = findViewById(R.id.textViewDetailProtein);
        tvFat = findViewById(R.id.textViewDetailFat);
        tvMinerals = findViewById(R.id.textViewDetailMinerals);
        tvVitamins = findViewById(R.id.textViewDetailVitamins);
        tvCarbohydrate = findViewById(R.id.textViewDetailCarbohydrate);
        tvCalories = findViewById(R.id.textViewCaloriesView);

    }

    private void setEvent() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Details of a meal");

        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mealPickedId = preferences.getString(getString(R.string.pickedId), null);
        if (mealPickedId != null) {
            MealDBHelper mealDBHelper = MealDBHelper.getInstance(getApplicationContext());
            mealDBHelper.open();
            mealView = mealDBHelper.getMealById(Long.valueOf(mealPickedId));
            tvMealName.setText(mealView.getName());
            tvMealDes.setText(mealView.getDescription());
            tvMealCat.setText(mealView.getCategoryName());
            imageView.setImageBitmap(mealView.getImage());
            tvProtein.setText(String.valueOf(mealView.getProtein()));
            tvFat.setText(String.valueOf(mealView.getFat()));
            tvCarbohydrate.setText(String.valueOf(mealView.getCarbohydrate()));
            tvVitamins.setText(String.valueOf(mealView.getVitamins()));
            tvMinerals.setText(String.valueOf(mealView.getMinerals()));
            tvCalories.setText(String.format("%.2f", mealView.getTotalCalories()));
            mealDBHelper.close();
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
