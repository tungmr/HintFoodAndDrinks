package com.tungmr.hintfoodanddrinks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;
import com.tungmr.hintfoodanddrinks.model.User;
import com.tungmr.hintfoodanddrinks.security.SHAHashing;
import com.tungmr.hintfoodanddrinks.utils.BMIUtils;

import java.util.ArrayList;
import java.util.List;

public class ChangePassword extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextEmail, editTextUsername, editTextOldPassword, editTextNewPassword, editTextReNewPassword, edWeight, edHeight;
    private Button confirm;
    private Spinner spinnerGender;
    private List<String> genders;

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
        spinnerGender = findViewById(R.id.spinnerGenderChange);
        edWeight = findViewById(R.id.editTextChangeWeight);
        edHeight = findViewById(R.id.editTextChangeHeight);
    }

    private void setEvent() {
        genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Change information");
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString(getString(R.string.usernameKey), "Username");
        email = preferences.getString(getString(R.string.emailKey), "contact@tungmr.com");
        editTextEmail.setText(email);
        editTextUsername.setText(username);
        LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getApplicationContext());
        loginDBHelper.open();
        User user = loginDBHelper.findUserByEmail(email);
        loginDBHelper.close();
        edWeight.setText(String.valueOf(user.getWeight()));
        edHeight.setText(String.valueOf(user.getHeight()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, genders);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(this);
        spinnerGender.setSelection(genders.indexOf(user.getGender()));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String reNewPassword = editTextReNewPassword.getText().toString();
                Integer height = 0, weight = 0;
                try {
                    weight = Integer.valueOf(edWeight.getText().toString());
                } catch (NumberFormatException e) {
                    edWeight.requestFocus();
                    edWeight.setError("just a number");
                    return;
                }
                try {
                    height = Integer.valueOf(edHeight.getText().toString());
                } catch (NumberFormatException e) {
                    edHeight.requestFocus();
                    edHeight.setError("just a number");
                    return;
                }

                if (!username.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty() && !reNewPassword.isEmpty()) {

                    if (newPassword.equals(reNewPassword)) {
                        LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getApplicationContext());
                        loginDBHelper.open();
                        User user = loginDBHelper.findUserByEmail(email);
                        String oldPasswordHash = SHAHashing.getSHAHash(oldPassword);
                        if (oldPasswordHash.equals(user.getPassword())) {
                            user.setName(username);
                            user.setPassword(SHAHashing.getSHAHash(newPassword));
                            user.setWeight(weight);
                            user.setHeight(height);
                            user.setGender(spinnerGender.getSelectedItem().toString());
                            boolean check = loginDBHelper.editUser(user);
                            if (check) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(getString(R.string.usernameKey), user.getName());
                                editor.putString(getString(R.string.bmiKey), String.valueOf(BMIUtils.calculateBMI(height,weight)));

                                View inflatedView = getLayoutInflater().inflate(R.layout.activity_main, null);
                                NavigationView navigationView = inflatedView.findViewById(R.id.nav_view);
                                View header = navigationView.getHeaderView(0);
                                TextView tvUsername = header.findViewById(R.id.navNameInfo);
                                tvUsername.setText(user.getName());

                                editor.commit();

                                Toast.makeText(getApplicationContext(), "Information was changed", Toast.LENGTH_LONG).show();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
