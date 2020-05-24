package com.tungmr.hintfoodanddrinks.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.CategoryDBHelper;
import com.tungmr.hintfoodanddrinks.db.MealDBHelper;
import com.tungmr.hintfoodanddrinks.model.Category;
import com.tungmr.hintfoodanddrinks.model.Meal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddMealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button chooseImage, add, back;
    private ImageView imageView;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private byte[] imageAdd;

    private EditText edMealName, edMealDes;
    private Spinner spinnerCategory;
    private Switch status;

    private List<String> categoriesName;

    private String categoryChoose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        setControl();
        setValue();
        setEvent();

    }

    private void setControl() {
        edMealName = findViewById(R.id.editTextMealName);
        edMealDes = findViewById(R.id.editTextMealDes);
        chooseImage = findViewById(R.id.buttonChooseAnImage);
        imageView = findViewById(R.id.imageViewAfterChooseImage);
        add = findViewById(R.id.buttonAddNewMeal);
        back = findViewById(R.id.buttonAddMealBack);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        status = findViewById(R.id.switchStatus);
    }

    private void setValue() {
        categoriesName = new ArrayList<>();
        CategoryDBHelper categoryDBHelper = CategoryDBHelper.getInstance(getApplicationContext());
        categoryDBHelper.open();
        categoryDBHelper.findAllCategoryName(categoriesName);
        categoryDBHelper.close();
    }

    private void setEvent() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesName);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = edMealName.getText().toString();
                String mealDes = edMealDes.getText().toString();
                boolean statusMeal = status.isChecked();

                if (!mealName.isEmpty() && !mealDes.isEmpty() && !categoryChoose.isEmpty() && imageAdd != null && imageAdd.length > 0) {
                    MealDBHelper mealDBHelper = MealDBHelper.getInstance(getApplicationContext());
                    mealDBHelper.open();
                    Integer mealStatus = statusMeal ? 1 : 0;
                    boolean check = mealDBHelper.saveMeal(new Meal(mealName, mealDes, mealStatus, categoryChoose, imageAdd));
                    if (check) {
                        Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_LONG).show();
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
            imageAdd = stream.toByteArray();
            // ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryChoose = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
