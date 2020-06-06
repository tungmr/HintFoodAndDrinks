package com.tungmr.hintfoodanddrinks.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.db.LoginDBHelper;
import com.tungmr.hintfoodanddrinks.model.User;

import java.text.DecimalFormat;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView username, email, changePassword, signOut, tvGender, tvHeight, tvWeight, tvBMI;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.mView = view;
        setControl();
        setEvent();
        return view;

    }

    private void setControl() {
        username = mView.findViewById(R.id.textViewProfileName);
        email = mView.findViewById(R.id.textViewProfileEmail);
        changePassword = mView.findViewById(R.id.textViewProfileChangePassword);
        signOut = mView.findViewById(R.id.textViewProfileSignOut);
        tvGender = mView.findViewById(R.id.textViewGenderUserProfile);
        tvHeight = mView.findViewById(R.id.textViewHeightUserProfile);
        tvWeight = mView.findViewById(R.id.textViewWeightUserProfile);
        tvBMI = mView.findViewById(R.id.textViewBMIUserProfile);

    }

    private void setEvent() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String user = preferences.getString(getString(R.string.usernameKey), "Username");
        username.setText(user);
        email.setText(preferences.getString(getString(R.string.emailKey), "contact@tungmr.com"));
        LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getActivity());
        loginDBHelper.open();
        User userProfile = loginDBHelper.findUserByEmail(user);
        tvGender.setText(String.valueOf(userProfile.getGender()));
        tvHeight.setText(String.valueOf(userProfile.getHeight()));
        tvWeight.setText(String.valueOf(userProfile.getWeight()));
        tvBMI.setText(String.format("%.2f",userProfile.getBMI()));

        loginDBHelper.close();
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("Notification");
                builder.setMessage(getString(R.string.ask_logout));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = mySPrefs.edit();
                        editor.remove(getString(R.string.emailKey));
                        editor.remove(getString(R.string.usernameKey));
                        editor.remove(getString(R.string.role));
                        editor.apply();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
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


}
