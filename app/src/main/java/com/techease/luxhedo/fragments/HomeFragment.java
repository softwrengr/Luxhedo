package com.techease.luxhedo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techease.luxhedo.R;
import com.techease.luxhedo.adapters.CategoriesAdapter;
import com.techease.luxhedo.dataModels.categoriesDataModel.CategoryModel;
import com.techease.luxhedo.dataModels.categoriesDataModel.ChildData;
import com.techease.luxhedo.networking.ApiClient;
import com.techease.luxhedo.networking.ApiInterface;
import com.techease.luxhedo.utils.AlertUtils;
import com.techease.luxhedo.utils.Configuration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {
    View view;
    AlertDialog alertDialog;
    RecyclerView rvCategories;
    CategoriesAdapter categoriesAdapter;
    List<ChildData> categoryModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rvCategories = view.findViewById(R.id.rv_categories);
        initUI();
        return view;
    }

    private void initUI(){
        RecyclerView.LayoutManager mLayoutManagerReviews = new LinearLayoutManager(getActivity());
        rvCategories.setLayoutManager(mLayoutManagerReviews);
        categoryModelList = new ArrayList<>();
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        apiCall();
    }

    private void apiCall() {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CategoryModel> categoriesResponseModelCall = services.categories("Bearer "+Configuration.TOKEN);
        categoriesResponseModelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, retrofit2.Response<CategoryModel> response) {
               Log.d("response",response.body().getName());
               alertDialog.dismiss();
                categoryModelList.addAll(response.body().getChildrenData());

                response.body().getId();

                categoriesAdapter = new CategoriesAdapter(getActivity(), categoryModelList);
                rvCategories.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                alertDialog.dismiss();
                initUI();
            }
        });
    }

}
