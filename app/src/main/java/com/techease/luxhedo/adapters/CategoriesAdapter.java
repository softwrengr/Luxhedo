package com.techease.luxhedo.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.luxhedo.R;
import com.techease.luxhedo.dataModels.categoriesDataModel.ChildData;
import com.techease.luxhedo.fragments.CategoryDetailFragment;
import com.techease.luxhedo.utils.GeneralUtils;

import java.util.List;

/**
 * Created by eapple on 14/11/2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private Context context;
    private List<ChildData> userList;


    public CategoriesAdapter(Activity context, List<ChildData> userList) {
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_categories_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ChildData model = userList.get(position);
        holder.tvName.setText(model.getName());

        holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(context,"category",model.getName());
                String childID = String.valueOf(model.getId());
                Bundle bundle = new Bundle();
                bundle.putString("id", childID);
                android.support.v4.app.Fragment fragment = new CategoryDetailFragment();
                fragment.setArguments(bundle);
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivBag;
        RelativeLayout categoryLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            categoryLayout = itemView.findViewById(R.id.category_layout);

        }
    }
}

