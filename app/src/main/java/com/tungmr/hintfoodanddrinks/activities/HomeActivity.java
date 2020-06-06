package com.tungmr.hintfoodanddrinks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;
import com.tungmr.hintfoodanddrinks.model.User;
import com.tungmr.hintfoodanddrinks.utils.BMIUtils;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInfo.DialogInfoListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private TextView tvUsername, tvEmail;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String statusUser = preferences.getString(getString(R.string.statusUser), null);
        navigationView = findViewById(R.id.nav_view);

        if (statusUser != null && statusUser.equals(CoreConstants.STATUS_NEW)) {
            openDialog();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
        setControl();
        setEvent();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    private void setControl() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolBar);
        View header = navigationView.getHeaderView(0);

        tvUsername = header.findViewById(R.id.navNameInfo);
        tvEmail = header.findViewById(R.id.navEmail);
    }

    private void setEvent() {


        tvUsername.setText(preferences.getString(getString(R.string.usernameKey), "Username"));
        tvEmail.setText(preferences.getString(getString(R.string.emailKey), "contact@tungmr.com"));

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Notification");
                builder.setMessage(getString(R.string.ask_logout));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = mySPrefs.edit();
                        editor.remove(getString(R.string.emailKey));
                        editor.remove(getString(R.string.usernameKey));
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

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return true;
    }

    public void openDialog() {
        DialogInfo dialogInfo = new DialogInfo();
        dialogInfo.setCancelable(false);
        dialogInfo.show(getSupportFragmentManager(), "Get info dialog");
    }

    @Override
    public void applyInfo(Integer weight, Integer height, String gender) {
        LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(this);
        String email = preferences.getString(getString(R.string.emailKey), null);
        loginDBHelper.open();
        User user = loginDBHelper.findUserByEmail(email);
        user.setHeight(height);
        user.setWeight(weight);
        user.setStatus(CoreConstants.STATUS_OLD);
        user.setGender(gender);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.heightKey), String.valueOf(height));
        editor.putString(getString(R.string.weightKey), String.valueOf(weight));
        editor.putString(getString(R.string.genderKey), gender);
        Double BMI = BMIUtils.calculateBMI(user.getHeight(), user.getWeight());
        if (!BMI.equals(0d))
            editor.putString(getString(R.string.bmiKey), String.valueOf(BMI));
        user.setBMI(BMI);

        loginDBHelper.editUser(user);
        editor.commit();
        loginDBHelper.close();
    }
}
