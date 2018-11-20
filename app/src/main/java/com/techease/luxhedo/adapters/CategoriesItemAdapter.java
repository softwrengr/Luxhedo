package com.techease.luxhedo.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.techease.luxhedo.R;
import com.techease.luxhedo.dataModels.categoriesDataModel.ChildData;
import com.techease.luxhedo.dataModels.categorySKU.CategoryItemsModel;
import com.techease.luxhedo.fragments.CategoryDetailFragment;
import com.techease.luxhedo.fragments.ItemDetailFragment;
import com.techease.luxhedo.utils.GeneralUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eapple on 15/11/2018.
 */

public class CategoriesItemAdapter extends BaseAdapter {
    ArrayList<CategoryItemsModel> clientsDetailsModels;
    Context context;
    private LayoutInflater layoutInflater;
    String clientId;
    android.support.v7.app.AlertDialog alertDialog;
    MyViewHolder viewHolder = null;

    public CategoriesItemAdapter(Context context, ArrayList<CategoryItemsModel> clientsDetailsModels) {
        this.clientsDetailsModels=clientsDetailsModels;
        this.context=context;
        if (context!=null)
        {
            this.layoutInflater=LayoutInflater.from(context);

        }
    }

    @Override
    public int getCount() {
        if (clientsDetailsModels!=null) return clientsDetailsModels.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(clientsDetailsModels != null && clientsDetailsModels.size() > position) return  clientsDetailsModels.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        final CategoryItemsModel model=clientsDetailsModels.get(position);
        if(clientsDetailsModels != null && clientsDetailsModels.size() > position) return  clientsDetailsModels.size();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CategoryItemsModel model = clientsDetailsModels.get(position);

        viewHolder = new MyViewHolder();
        convertView=layoutInflater.inflate(R.layout.category_item_layout,parent,false);
        viewHolder.tvName=(TextView)convertView.findViewById(R.id.category_name);
        viewHolder.tvPrice= convertView.findViewById(R.id.tv_price);
        viewHolder.imageView = convertView.findViewById(R.id.iv_category);
        viewHolder.layout = convertView.findViewById(R.id.layout);

        viewHolder.tvName.setText(model.getName());
        viewHolder.tvPrice.setText("€"+model.getPrice());
        Glide.with(context).load("http://www.luxhedo.com/pub/media/catalog/product/"+model.getImage1()).into(viewHolder.imageView);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(context,"sub_category",model.getName());
                GeneralUtils.putStringValueInEditor(context,"price",model.getPrice());
                GeneralUtils.putStringValueInEditor(context,"descp",model.getDescription());
                GeneralUtils.putStringValueInEditor(context,"image1",model.getImage1());
                GeneralUtils.putStringValueInEditor(context,"image2",model.getImage2());
                GeneralUtils.putStringValueInEditor(context,"image3",model.getImage3());
                GeneralUtils.putStringValueInEditor(context,"image4",model.getImage4());
                GeneralUtils.connectFragmentWithBackStack(context,new ItemDetailFragment());

            }
        });

        convertView.setTag(viewHolder);
        return convertView;
    }


    private class MyViewHolder  {
        RelativeLayout layout;
        TextView tvName,tvPrice;
        ImageView imageView;
    }
}
