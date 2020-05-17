package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;
import com.tungmr.hintfoodanddrinks.model.User;
import com.tungmr.hintfoodanddrinks.security.SHAHashing;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, name, password, rePassword;
    private Button signUp;
    private TextView returnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setControl();
        setEvent();
    }

    private void setControl() {
        email = findViewById(R.id.editTextEmailSU);
        name = findViewById(R.id.editTextNameSU);
        password = findViewById(R.id.editTextPasswordSU);
        rePassword = findViewById(R.id.editTextConfirmPassword);
        signUp = findViewById(R.id.buttonSignUp);
        returnSignIn = findViewById(R.id.textViewReturnSignIn);
    }

    private void setEvent() {
        returnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String nameText = name.getText().toString();
                String passwordText = password.getText().toString();
                String rePasswordText = rePassword.getText().toString();

                if (!emailText.isEmpty() && !nameText.isEmpty() && !passwordText.isEmpty() && !rePasswordText.isEmpty()) {
                    LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getApplicationContext());
                    loginDBHelper.open();
                    boolean checkEmailExisted = loginDBHelper.checkEmailExisted(emailText);
                    if (checkEmailExisted) {
                        Toast.makeText(getApplicationContext(), R.string.email_existed, Toast.LENGTH_LONG).show();
                    } else {
                        if (passwordText.equals(rePasswordText)) {
                            User user = new User(emailText, nameText, passwordText);
                            boolean check = loginDBHelper.insertUser(user);
                            if (check) {
                                Toast.makeText(getApplicationContext(), R.string.sign_up_success, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.occurred, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.password_not_match, Toast.LENGTH_LONG).show();
                        }
                    }
                    loginDBHelper.close();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.please_fill_all, Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
