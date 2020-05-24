package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tungmr.hintfoodanddrinks.R;

public class AdminActivity extends AppCompatActivity {

    private Button addMeal, signOut, viewMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setControl();
        setEvent();
    }

    private void setControl() {
        addMeal = findViewById(R.id.buttonAdminAddMeal);
        signOut = findViewById(R.id.buttonAdminSignOut);
        viewMeal = findViewById(R.id.buttonViewMeal);
    }

    private void setEvent() {

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddMealActivity.class);
                startActivity(intent);
            }
        });

        viewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminViewMeal.class);
                startActivity(intent);
            }
        });
    }
}
