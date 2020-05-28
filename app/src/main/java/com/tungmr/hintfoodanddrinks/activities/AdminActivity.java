package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    private Button addMeal, signOut, viewMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        checkLoginAdmin();
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

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Notification");
                builder.setMessage(getString(R.string.ask_logout));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = mySPrefs.edit();
                        editor.remove(getString(R.string.emailKey));
                        editor.remove(getString(R.string.usernameKey));
                        editor.remove(getString(R.string.role));
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }
    private void checkLoginAdmin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String role = preferences.getString(getString(R.string.role), null);
        if (role!=null && role.equals(CoreConstants.ROLE_USER)){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }
}
