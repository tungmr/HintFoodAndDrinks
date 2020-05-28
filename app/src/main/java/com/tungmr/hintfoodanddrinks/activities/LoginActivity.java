package com.tungmr.hintfoodanddrinks.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.db.DatabaseHelper;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;
import com.tungmr.hintfoodanddrinks.model.User;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private EditText passwordEdt, emailEdt;
    private Button signIn;
    private TextView signUp;
    String nameUser, emailUser, pHashUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setControl();
        setEvent();
    }

    private void setControl() {
        passwordEdt = findViewById(R.id.editTextPassword);
        emailEdt = findViewById(R.id.editTextEmail);
        signIn = findViewById(R.id.buttonSignIn);
        signUp = findViewById(R.id.textViewSignUp);
    }

    private void setEvent() {

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.please_fill_all, Toast.LENGTH_LONG).show();
                } else {
                    LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getApplicationContext());
                    loginDBHelper.open();
                    User checkUser = loginDBHelper.checkUser(email, password);
                    if (checkUser.getEmail() != null) {

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(getString(R.string.usernameKey), checkUser.getName());
                        editor.putString(getString(R.string.emailKey), checkUser.getEmail());
                        editor.putString(getString(R.string.role), checkUser.getRole());
                        editor.commit();
                        Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_LONG).show();

                        if (checkUser.getRole().equals(CoreConstants.ROLE_USER)) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.incorrect_info_login, Toast.LENGTH_LONG).show();

                    }

                    loginDBHelper.close();
                }

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return true;
    }
}
