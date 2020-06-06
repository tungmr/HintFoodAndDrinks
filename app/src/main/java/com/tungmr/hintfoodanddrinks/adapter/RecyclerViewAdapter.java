package com.tungmr.hintfoodanddrinks.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tungmr.hintfoodanddrinks.R;
import com.tungmr.hintfoodanddrinks.activities.DetailMeal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private List<Bitmap> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<Long> mealIds = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, List<Bitmap> images, List<String> titles, List<Long> mealIds) {
        this.images = images;
        this.titles = titles;
        this.context = context;
        this.mealIds = mealIds;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageCircle);
            name = itemView.findViewById(R.id.titleCircleView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_view_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context)
                .asBitmap().load(images.get(position)).into(holder.image);
        holder.name.setText(titles.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("pickedId", mealIds.get(position).toString());
                editor.commit();
                Intent intent = new Intent(context, DetailMeal.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }


}
