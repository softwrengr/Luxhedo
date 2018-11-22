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
import android.widget.EditText;
import android.widget.TextView;

import com.mikelau.countrypickerx.Country;
import com.mikelau.countrypickerx.CountryPickerCallbacks;
import com.mikelau.countrypickerx.CountryPickerDialog;
import com.techease.luxhedo.R;
import com.techease.luxhedo.adapters.CountryAdapter;
import com.techease.luxhedo.dataModels.CountryModel;

import java.util.ArrayList;
import java.util.List;


public class PurchaseFormFragment extends Fragment {

    EditText etEmail,etFname,etLname,etStreet,etcity,etzip,etstate,etphone,etcompany;
    List<CountryModel> countryModelList;
    CountryAdapter countryAdapter;
    public  static TextView tvCountry;
    String strCountry,email,fname,lname,street,city,zip,state,phone,comp,id;
    Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_form, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etFname = view.findViewById(R.id.etFname);
        etLname = view.findViewById(R.id.etLname);
        etstate = view.findViewById(R.id.etState);
        etStreet = view.findViewById(R.id.etStreet);
        etcity = view.findViewById(R.id.etCity);
        etzip = view.findViewById(R.id.etZip);
        etphone = view.findViewById(R.id.etPhone);
        etcompany = view.findViewById(R.id.etComp);
        tvCountry = view.findViewById(R.id.tv_country);
        btnNext = view.findViewById(R.id.btnNext);

        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCountriesList();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                fname = etFname.getText().toString();

            }
        });


        return view;
    }


    private void getCountriesList() {
        CountryPickerDialog countryPicker;

        /* Name of your Custom JSON list */
        int resourceId = getResources().getIdentifier("country_avail", "raw", getActivity().getPackageName());

        countryPicker = new CountryPickerDialog(getActivity(), new CountryPickerCallbacks() {
            @Override
            public void onCountrySelected(Country country, int flagResId) {
                Log.d("zma country", country.getCountryName(getActivity()) + country.getIsoCode());
                strCountry = country.getCountryName(getActivity());
                tvCountry.setText(strCountry);

                tvCountry.setTextColor(getResources().getColor(R.color.black));
                /* Get Country Name: country.getCountryName(context); */
                /* Call countryPicker.dismiss(); to prevent memory leaks */
            }

          /* Set to false if you want to disable Dial Code in the results and true if you want to show it
             Set to zero if you don't have a custom JSON list of countries in your raw file otherwise use
             resourceId for your customly available countries */
        }, false, 0);
        countryPicker.show();
    }


}
