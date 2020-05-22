package com.tungmr.hintfoodanddrinks.activities;

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
import androidx.fragment.app.Fragment;

import com.tungmr.hintfoodanddrinks.R;

public class ProfileFragment extends Fragment {

    private TextView username, email, changePassword, signOut;

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
    }

    private void setEvent() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username.setText(preferences.getString(getString(R.string.usernameKey), "Username"));
        email.setText(preferences.getString(getString(R.string.emailKey), "contact@tungmr.com"));

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
                SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = mySPrefs.edit();
                editor.remove(getString(R.string.emailKey));
                editor.remove(getString(R.string.usernameKey));
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }


}
