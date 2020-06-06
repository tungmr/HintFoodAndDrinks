package com.tungmr.hintfoodanddrinks.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.tungmr.hintfoodanddrinks.R;

public class DialogInfo extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText edHeight, edWeight;
    private Spinner spinnerGender;
    private DialogInfoListener dialogInfoListener;
    private String[] genders = {"Male", "Female"};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_info, null);
        edHeight = view.findViewById(R.id.editTextHeight);
        edWeight = view.findViewById(R.id.editTextWeight);
        builder.setView(view).setTitle("Tell us your information")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer weight = null;
                        try {
                            weight = Integer.valueOf(edWeight.getText().toString());
                        } catch (NumberFormatException e) {
                            weight = 0;
                        }

                        Integer height = null;
                        try {
                            height = Integer.valueOf(edHeight.getText().toString());
                        } catch (NumberFormatException e) {
                            height = 0;
                        }

                        String gender = spinnerGender.getSelectedItem().toString();
                        dialogInfoListener.applyInfo(weight, height, gender);

                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });


        spinnerGender = view.findViewById(R.id.spinnerGenderLog);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genders);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(this);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogInfoListener = (DialogInfoListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface DialogInfoListener {
        void applyInfo(Integer weight, Integer height, String gender);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
