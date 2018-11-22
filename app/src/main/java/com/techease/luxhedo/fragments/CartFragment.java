package com.techease.luxhedo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.luxhedo.R;
import com.techease.luxhedo.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    EditText etQTY;
    TextView tvItemName,tvPrice,tvsizeInfo,tvadditionalInfo;
    Spinner spinner;
    Button btnAddToCart;
    String id,name,price,size,qty,additional;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        name = GeneralUtils.getSubCategory(getActivity());

        etQTY = view.findViewById(R.id.etQty);
        tvItemName = view.findViewById(R.id.tvItemName);
        tvPrice = view.findViewById(R.id.tvItemPrice);
        tvsizeInfo = view.findViewById(R.id.tvSizeInfo);
        tvadditionalInfo = view.findViewById(R.id.tvAdditionalInfo);
        spinner = view.findViewById(R.id.spinnerSize);
        btnAddToCart = view.findViewById(R.id.btnAddToCart);


        List<String> categories = new ArrayList<String>();
        categories.add("XS");
        categories.add("S");
        categories.add("M");
        categories.add("L");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
