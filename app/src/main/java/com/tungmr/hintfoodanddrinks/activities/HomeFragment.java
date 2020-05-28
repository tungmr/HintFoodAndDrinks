package com.tungmr.hintfoodanddrinks.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.db.CategoryDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private ListView listView;

    private List<String> categoriesName, categoryIntroductionTexts;

    private int[] images = {R.drawable.food, R.drawable.food2, R.drawable.food3};


    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.mView = view;
        setControl();
        setValue();
        setEvent();

        return view;
    }

    private void setControl() {
        listView = mView.findViewById(R.id.listViewHomePage);
    }

    private void setEvent() {




        MyCategoryAdapter myCategoryAdapter = new MyCategoryAdapter(getActivity(), categoriesName, categoryIntroductionTexts, images);
        listView.setAdapter(myCategoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Breakfast
                 * */
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                if (position == 0) {
                    editor.putString(getString(R.string.categoryPicked), CoreConstants.BREAKFAST);
                }
                /**
                 * Lunch
                 * */
                if (position == 1) {
                    editor.putString(getString(R.string.categoryPicked), CoreConstants.LUNCH);

                }
                /**
                 * Dinner
                 * */
                if (position == 2) {
                    editor.putString(getString(R.string.categoryPicked), CoreConstants.DINNER);

                }
                editor.commit();
                Intent intent = new Intent(getActivity(), AdminViewMeal.class);
                startActivity(intent);

            }
        });
    }

    private void setValue() {
        categoriesName = new ArrayList<>();
        categoryIntroductionTexts = new ArrayList<>();
        CategoryDBHelper categoryDBHelper = CategoryDBHelper.getInstance(getActivity());
        categoryDBHelper.open();
        categoryDBHelper.getCategories(categoriesName, categoryIntroductionTexts);
        categoryDBHelper.close();
    }

    class MyCategoryAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> names;
        List<String> introductions;
        int[] images;

        MyCategoryAdapter(Context context, List<String> names, List<String> introductions, int[] images) {
            super(context, R.layout.list_view_home_item, R.id.listViewHomeItemName, names);
            this.context = context;
            this.images = images;
            this.names = names;
            this.introductions = introductions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.list_view_home_item, parent, false);
            ImageView imageView = row.findViewById(R.id.listViewHomeItemImage);
            TextView name = row.findViewById(R.id.listViewHomeItemName);
            TextView intro = row.findViewById(R.id.listViewHomeItemIntro);


            imageView.setImageResource(images[position]);
            name.setText(names.get(position));
            intro.setText(introductions.get(position));

            return row;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Objects.requireNonNull(getActivity()).finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
