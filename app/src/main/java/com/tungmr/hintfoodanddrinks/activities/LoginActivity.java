package com.tungmr.hintfoodanddrinks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.DatabaseHelper;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private EditText passwordEdt, emailEdt;
    private Button signIn;
    private TextView signUp;

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
                    boolean check = loginDBHelper.checkUser(email, password);
                    if (check) {
                        Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.incorrect_info_login, Toast.LENGTH_LONG).show();

                    }

                    loginDBHelper.close();
                }

            }
        });

    }
}
