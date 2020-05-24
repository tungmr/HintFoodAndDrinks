package com.tungmr.hintfoodanddrinks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MyMealAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Meal> meals;

    public MyMealAdapter(Context context, int layout, List<Meal> meals) {
        this.context = context;
        this.layout = layout;
        this.meals = meals;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public Object getItem(int position) {
        return meals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView mealName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(layout, null);
            viewHolder.mealName = row.findViewById(R.id.textMealNameGirdView);
            viewHolder.imageView = row.findViewById(R.id.imageMealGirdView);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        Meal meal = meals.get(position);
        viewHolder.mealName.setText(meal.getName());
        viewHolder.imageView.setImageBitmap(meal.getImage());


        return row;
    }
}
