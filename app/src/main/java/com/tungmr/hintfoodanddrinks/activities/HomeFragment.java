package com.tungmr.hintfoodanddrinks.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import androidx.fragment.app.Fragment;

import com.tungmr.hintfoodanddrinks.R;
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
                if (position == 0) {
                    Toast.makeText(getActivity(), "Breakfast", Toast.LENGTH_LONG).show();
                }
                if (position == 1) {
                    Toast.makeText(getActivity(), "Lunch", Toast.LENGTH_LONG).show();
                }
                if (position == 2) {
                    Toast.makeText(getActivity(), "Dinner", Toast.LENGTH_LONG).show();
                }
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
}
