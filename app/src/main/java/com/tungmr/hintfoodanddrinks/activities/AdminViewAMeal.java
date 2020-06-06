package com.tungmr.hintfoodanddrinks.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.CategoryDBHelper;
import com.tungmr.hintfoodanddrinks.db.MealDBHelper;
import com.tungmr.hintfoodanddrinks.model.Meal;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminViewAMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Button chooseImage, save;
    private ImageView imageView;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private byte[] imageSave;

    private EditText edMealName, edMealDes, edProtein, edMinerals, edFat, edVitamins, edCarbohydrate;
    private Spinner spinnerCategory;
    private Switch status;
    private TextView tvCalories;

    private List<String> categoriesName;

    private String categoryChoose;
    private MealDBHelper mealDBHelper;

    private Meal mealEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_a_meal);
        setControl();
        setValueForSpinner();
        setValueForMeal();
        setEvent();

    }


    private void setControl() {
        mealDBHelper = MealDBHelper.getInstance(getApplicationContext());
        mealDBHelper.open();
        edMealName = findViewById(R.id.editTextMealNameDetail);
        edMealDes = findViewById(R.id.editTextMealDesDetail);
        chooseImage = findViewById(R.id.buttonChooseAnImageDetail);
        imageView = findViewById(R.id.imageViewAfterChooseImageDetail);
        save = findViewById(R.id.buttonSaveMealDetail);
        spinnerCategory = findViewById(R.id.spinnerCategoryDetail);
        status = findViewById(R.id.switchStatusDetail);
        edProtein = findViewById(R.id.editTextProtein);
        edFat = findViewById(R.id.editTextFat);
        edMinerals = findViewById(R.id.editTextMinerals);
        edVitamins = findViewById(R.id.editTextVitamins);
        edCarbohydrate = findViewById(R.id.editTextCarbohydrate);
        tvCalories = findViewById(R.id.textViewCaloriesEdit);
    }

    private void setValueForSpinner() {
        categoriesName = new ArrayList<>();
        CategoryDBHelper categoryDBHelper = CategoryDBHelper.getInstance(getApplicationContext());
        categoryDBHelper.open();
        categoryDBHelper.findAllCategoryName(categoriesName);
        categoryDBHelper.close();
    }

    private void setValueForMeal() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesName);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Long pickedId = Long.valueOf(preferences.getString(getString(R.string.pickedId), null));
        mealEdit = mealDBHelper.getMealById(pickedId);
        imageSave = mealEdit.getImageArray();
        edMealName.setText(mealEdit.getName());
        edMealDes.setText(mealEdit.getDescription());
        edProtein.setText(String.valueOf(mealEdit.getProtein()));
        edFat.setText(String.valueOf(mealEdit.getFat()));
        edMinerals.setText(String.valueOf(mealEdit.getMinerals()));
        edVitamins.setText(String.valueOf(mealEdit.getVitamins()));
        edCarbohydrate.setText(String.valueOf(mealEdit.getCarbohydrate()));
        tvCalories.setText(String.format("%.2f", mealEdit.getTotalCalories()));
        int index = categoriesName.indexOf(mealEdit.getCategoryName());
        spinnerCategory.setSelection(index);
        if (mealEdit.getStatus().equals(1)) {
            status.setChecked(true);
        } else {
            status.setChecked(false);
        }
        imageView.setImageBitmap(mealEdit.getImage());

    }

    private void setEvent() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = edMealName.getText().toString();
                String mealDes = edMealDes.getText().toString();
                boolean statusMeal = status.isChecked();
                Double protein=null, fat = null, minerals=null, carbohydrate=null;
                try {
                     protein = Double.valueOf(edProtein.getText().toString());
                } catch (NumberFormatException e) {
                    edProtein.requestFocus();
                    edProtein.setError("Just a number");
                    return;
                }
                try {
                    fat = Double.valueOf(edFat.getText().toString());
                } catch (NumberFormatException e) {
                    edFat.requestFocus();
                    edFat.setError("Just a number");
                    return;
                }
                try {
                    minerals = Double.valueOf(edMinerals.getText().toString());
                } catch (NumberFormatException e) {
                    edMinerals.requestFocus();
                    edMinerals.setError("Just a number");
                    return;
                }
                try {
                    carbohydrate = Double.valueOf(edCarbohydrate.getText().toString());
                } catch (NumberFormatException e) {
                    edCarbohydrate.requestFocus();
                    edCarbohydrate.setError("Just a number");
                    return;
                }


                if (!mealName.isEmpty() && !mealDes.isEmpty() && !categoryChoose.isEmpty() && imageSave != null && imageSave.length > 0) {
                    Integer mealStatus = statusMeal ? 1 : 0;
                    Meal mealSave = new Meal(mealEdit.getMealId(), mealName, mealDes, mealStatus, categoryChoose, imageSave);
                    mealSave.setProtein(protein);
                    mealSave.setFat(fat);
                    mealSave.setMinerals(minerals);
                    mealSave.setCarbohydrate(carbohydrate);

                    boolean check = mealDBHelper.editMeal(mealSave);
                    if (check) {
                        Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AdminViewAMeal.this, AdminViewMeal.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.occurred, Toast.LENGTH_LONG).show();
                    }
                    mealDBHelper.close();

                } else {
                    Toast.makeText(getApplicationContext(), R.string.please_fill_all, Toast.LENGTH_LONG).show();

                }

            }

        });



    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageView.setImageURI(data.getData());


            BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageSave = stream.toByteArray();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryChoose = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
