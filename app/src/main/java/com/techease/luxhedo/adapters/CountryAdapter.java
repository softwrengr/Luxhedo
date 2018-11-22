package com.techease.luxhedo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.techease.luxhedo.R;
import com.techease.luxhedo.dataModels.CountryModel;
import com.techease.luxhedo.fragments.PurchaseFormFragment;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    Activity activity;
    List<CountryModel> countryModelList;
    public CountryAdapter(Activity activity, List<CountryModel> countryModelList) {
        this.activity=activity;
        this.countryModelList=countryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_countrylist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
    final CountryModel model=countryModelList.get(position);

    holder.tvName.setText(model.getName());
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String name=model.getName();
            PurchaseFormFragment.tvCountry.setText(name);
            holder.editor.putString("countryname",name).commit();

        }
    });


    }

    @Override
    public int getItemCount() {
        return countryModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            tvName=itemView.findViewById(R.id.tvCountryName);
            linearLayout=itemView.findViewById(R.id.ll);
        }
    }
}
