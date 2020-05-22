package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;
import com.tungmr.hintfoodanddrinks.model.User;
import com.tungmr.hintfoodanddrinks.security.SHAHashing;

public class ChangePassword extends AppCompatActivity {

    private EditText editTextEmail, editTextUsername, editTextOldPassword, editTextNewPassword, editTextReNewPassword;
    private Button confirm, cancel;

    private String email, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setControl();
        setEvent();
    }

    private void setControl() {
        editTextEmail = findViewById(R.id.editTextChangeEmail);
        editTextEmail.setEnabled(false);
        editTextUsername = findViewById(R.id.editTextChangeUsername);
        editTextOldPassword = findViewById(R.id.editTextChangeOldPassword);
        editTextNewPassword = findViewById(R.id.editTextChangeNewPassword);
        editTextReNewPassword = findViewById(R.id.editTextChangeNewRePassword);
        confirm = findViewById(R.id.buttonConfirmChangePassword);
        cancel = findViewById(R.id.buttonCancelChangePassword);
    }

    private void setEvent() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString(getString(R.string.usernameKey), "Username");
        email = preferences.getString(getString(R.string.emailKey), "contact@tungmr.com");
        editTextEmail.setText(email);
        editTextUsername.setText(username);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String reNewPassword = editTextReNewPassword.getText().toString();

                if (!username.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty() && !reNewPassword.isEmpty()) {

                    if (newPassword.equals(reNewPassword)) {
                        LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getApplicationContext());
                        loginDBHelper.open();
                        User user = loginDBHelper.findUserByEmail(email);
                        String oldPasswordHash = SHAHashing.getSHAHash(oldPassword);
                        if (oldPasswordHash.equals(user.getPassword())) {
                            user.setName(username);
                            user.setPassword(SHAHashing.getSHAHash(newPassword));
                            boolean check = loginDBHelper.editUser(user);
                            if (check) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(getString(R.string.usernameKey), user.getName());

                                View inflatedView = getLayoutInflater().inflate(R.layout.activity_main, null);
                                NavigationView navigationView = inflatedView.findViewById(R.id.nav_view);
                                View header = navigationView.getHeaderView(0);
                                TextView tvUsername = header.findViewById(R.id.navNameInfo);
                                tvUsername.setText(user.getName());

                                editor.commit();

                                Toast.makeText(getApplicationContext(), R.string.change_password_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.occurred, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.password_invalid, Toast.LENGTH_LONG).show();
                        }
                        loginDBHelper.close();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.password_not_match, Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(getApplicationContext(), R.string.please_fill_all, Toast.LENGTH_LONG).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
