package com.tungmr.hintfoodanddrinks.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.adapter.RecyclerViewAdapter;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.db.CategoryDBHelper;
import com.tungmr.hintfoodanddrinks.db.MealDBHelper;
import com.tungmr.hintfoodanddrinks.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {


    private List<Bitmap> imagesCircle;
    private List<String> mealTitles, categoriesName, categoryIntroductionTexts;
    private List<Long> mealIds;
    private TextView hintFor, tvLunch, tvDinner, tvBreakfast, tvSubLunch, tvSubDinner, tvSubBreakfast;

    private ImageView imgLunch, imgDinner, imgBreakfast;


    private RecyclerView recyclerView;

    private LinearLayout lnBreakfast, lnLunch, lnDinner;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.mView = view;
        setControl();
        setValue();
        setValueForDailyMeal();
        setEvent();

        return view;
    }

    private void setControl() {
        recyclerView = mView.findViewById(R.id.recyclerView);
        hintFor = mView.findViewById(R.id.hintOf);
        tvLunch = mView.findViewById(R.id.textViewLunch);
        tvSubLunch = mView.findViewById(R.id.subLunch);
        imgLunch = mView.findViewById(R.id.imageLunch);

        tvDinner = mView.findViewById(R.id.textViewDinner);
        tvSubDinner = mView.findViewById(R.id.subDinner);
        imgDinner = mView.findViewById(R.id.imageDinner);

        tvBreakfast = mView.findViewById(R.id.textViewBreakfast);
        tvSubBreakfast = mView.findViewById(R.id.subBreakfast);
        imgBreakfast = mView.findViewById(R.id.imageBreakfast);

        lnBreakfast = mView.findViewById(R.id.linearLayoutBF);
        lnLunch = mView.findViewById(R.id.linearLayoutL);
        lnDinner = mView.findViewById(R.id.linearLayoutD);
    }

    private void setEvent() {

        if (imagesCircle.size() > 0 && mealTitles.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), imagesCircle, mealTitles, mealIds);
            recyclerView.setAdapter(recyclerViewAdapter);

        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        lnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(getString(R.string.categoryPicked), CoreConstants.BREAKFAST);
                editor.commit();
                Intent intent = new Intent(getActivity(), AdminViewMeal.class);
                startActivity(intent);
            }
        });
        lnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(getString(R.string.categoryPicked), CoreConstants.LUNCH);
                editor.commit();
                Intent intent = new Intent(getActivity(), AdminViewMeal.class);
                startActivity(intent);
            }
        });
        lnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(getString(R.string.categoryPicked), CoreConstants.DINNER);
                editor.commit();
                Intent intent = new Intent(getActivity(), AdminViewMeal.class);
                startActivity(intent);
            }
        });

    }

    private void setValue() {
        categoriesName = new ArrayList<>();
        categoryIntroductionTexts = new ArrayList<>();
        imagesCircle = new ArrayList<>();
        mealTitles = new ArrayList<>();
        mealIds = new ArrayList<>();
        hintFor.setText("Meal for this " + TimeUtils.getTime());
        CategoryDBHelper categoryDBHelper = CategoryDBHelper.getInstance(getActivity());
        categoryDBHelper.open();
        categoryDBHelper.getCategories(categoriesName, categoryIntroductionTexts);
        categoryDBHelper.close();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String gender = preferences.getString(getString(R.string.genderKey), null);
        Double BMI = Double.valueOf(preferences.getString(getString(R.string.bmiKey), null));
        if (gender != null && BMI != null && BMI > 0d) {
            MealDBHelper mealDBHelper = MealDBHelper.getInstance(getActivity());
            mealDBHelper.open();
            mealDBHelper.getImageBitmapAndMealName(imagesCircle, mealTitles, mealIds, 1, BMI, gender, getTime(TimeUtils.getTime()));
            mealDBHelper.close();
        } else {
            MealDBHelper mealDBHelper = MealDBHelper.getInstance(getActivity());
            mealDBHelper.open();
            mealDBHelper.getImageBitmapAndMealNameRand(imagesCircle, mealTitles, mealIds, 1, getTime(TimeUtils.getTime()));
            mealDBHelper.close();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Objects.requireNonNull(getActivity()).finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setValueForDailyMeal() {
        categoriesName = new ArrayList<>();
        categoryIntroductionTexts = new ArrayList<>();
        CategoryDBHelper categoryDBHelper = CategoryDBHelper.getInstance(getActivity());
        categoryDBHelper.open();
        categoryDBHelper.getCategories(categoriesName, categoryIntroductionTexts);
        categoryDBHelper.close();

        tvBreakfast.setText(categoriesName.get(0));
        tvLunch.setText(categoriesName.get(0));
        tvDinner.setText(categoriesName.get(0));

        tvSubBreakfast.setText(categoryIntroductionTexts.get(0));
        tvSubLunch.setText(categoryIntroductionTexts.get(0));
        tvSubDinner.setText(categoryIntroductionTexts.get(0));

        imgBreakfast.setImageResource(R.drawable.food);
        imgLunch.setImageResource(R.drawable.food2);
        imgDinner.setImageResource(R.drawable.food3);

    }

    private String getTime(String time){
        if (time.equals(TimeUtils.MORNING))
            return "Breakfast";
        else  if (time.equals(TimeUtils.NOON))
            return "Lunch";
        return "Dinner";
    }
}
