package com.techease.luxhedo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.luxhedo.R;
import com.techease.luxhedo.utils.GeneralUtils;

public class ItemDetailFragment extends Fragment {
    String image1,image2,image3,image4,description,price,subItemName;
    ImageView ivItem,ivNext,ivPrevious;
    TextView tvItemDescp,tvItemPrice,tvItemName;
    int count = 0,count2 = 3;
    Button btnPurchase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        subItemName = GeneralUtils.getSubCategory(getActivity());

        btnPurchase = view.findViewById(R.id.btnPurchase);
        ivItem = view.findViewById(R.id.item_image);
        tvItemDescp = view.findViewById(R.id.item_descp);
        tvItemPrice = view.findViewById(R.id.item_price);
        tvItemName = view.findViewById(R.id.sub_item_name);
        ivNext = view.findViewById(R.id.iv_next);
        ivPrevious = view.findViewById(R.id.iv_Previous);

        image1 = GeneralUtils.getImage1(getActivity());
        image2 = GeneralUtils.getImage2(getActivity());
        image3 = GeneralUtils.getImage3(getActivity());
        image4 = GeneralUtils.getImage4(getActivity());

        description = GeneralUtils.getDescp(getActivity());
        price = GeneralUtils.getPrice(getActivity());

        initUI();


        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view;

    }

    private void initUI(){
        Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image1).into(ivItem);
        tvItemDescp.setText(description);
        tvItemPrice.setText("Price €"+price);
        tvItemName.setText(subItemName);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image2).into(ivItem);
                     count++;
                }
                else if(count==1){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image3).into(ivItem);
                    count++;
                }
                else if(count==2){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image4).into(ivItem);
                    count++;
                }
                else if(count==3){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image1).into(ivItem);
                    count=0;
                }
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("count",String.valueOf(count2));
                if(count2==3){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image4).into(ivItem);
                    count2--;
                }
                else  if(count2==2){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image3).into(ivItem);
                    count2--;
                }
                if(count2==1){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image2).into(ivItem);
                    count2--;
                }
                else if(count2==0){
                    Glide.with(getActivity()).load("http://www.luxhedo.com/pub/media/catalog/product/"+image1).into(ivItem);
                    count2=3;
                }
            }
        });
    }

}
