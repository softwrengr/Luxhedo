package com.techease.luxhedo.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techease.luxhedo.R;
import com.techease.luxhedo.activities.FullscreenActivity;
import com.techease.luxhedo.adapters.CategoriesAdapter;
import com.techease.luxhedo.adapters.CategoriesItemAdapter;
import com.techease.luxhedo.dataModels.categoriesDataModel.CategoryModel;
import com.techease.luxhedo.dataModels.categorySKU.CategoryItemsModel;
import com.techease.luxhedo.dataModels.categorySKU.SkuModel;
import com.techease.luxhedo.networking.ApiClient;
import com.techease.luxhedo.networking.ApiInterface;
import com.techease.luxhedo.utils.AlertUtils;
import com.techease.luxhedo.utils.Configuration;
import com.techease.luxhedo.utils.GeneralUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class CategoryDetailFragment extends Fragment {
    AlertDialog alertDialog;
    View view;
    TextView tvTitle;
    int childID, lazyLoading = 50,sizeOfArray,totalLoaded;
    HashMap<String, String> hashMap;
    String skuID, strImage1, strImage2, strImage3, strImage4;
    CategoriesItemAdapter categoriesItemAdapter;
    ArrayList<CategoryItemsModel> categoryItemsModelList;
    RecyclerView rvCategoriesItems;
    GridView gvCategoriesItems;
    TextView tvLoadMore;
    ImageView ivBack;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_detail, container, false);

        tvTitle = view.findViewById(R.id.tvTitle);
        ivBack = view.findViewById(R.id.ivBack);
        tvTitle.setText(GeneralUtils.getCategory(getActivity()));
        tvLoadMore = view.findViewById(R.id.tvLoadMore);
        tvLoadMore.setVisibility(View.GONE);
        gvCategoriesItems = view.findViewById(R.id.gv_categories_items);
        childID = Integer.parseInt(getArguments().getString("id"));
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.BASE + "categories/" + childID + "/products/"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    sizeOfArray = jsonArray.length();

                    categoryItemsModelList = new ArrayList<>();
                    categoriesItemAdapter = new CategoriesItemAdapter(getActivity(), categoryItemsModelList);
                    gvCategoriesItems.setAdapter(categoriesItemAdapter);
                    apiCall(lazyLoading);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + Configuration.TOKEN);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);


        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lazyLoading += 6;
                tvLoadMore = view.findViewById(R.id.tvLoadMore);
                gvCategoriesItems = view.findViewById(R.id.gv_categories_items);
                childID = Integer.parseInt(getArguments().getString("id"));
                alertDialog = AlertUtils.createProgressDialog(getActivity());
                alertDialog.show();
                apiCall(lazyLoading);
            }
        });

        gvCategoriesItems.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                Log.d("itemTotal", String.valueOf(totalItemCount));
                Log.d("itemVisible", String.valueOf(visibleItemCount));
                totalLoaded = totalItemCount;
                if (totalItemCount != sizeOfArray)
                {
                    tvLoadMore.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvLoadMore.setVisibility(View.GONE);
                }
            }
        });



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), FullscreenActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }


    private void apiCall(final int lazyLoading) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.BASE + "categories/" + childID + "/products/"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (lazyLoading>50)
                    {
                        for (int i = totalLoaded; i < lazyLoading; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sku = jsonObject.getString("sku");
                            skuID = sku;
                            String categoryID = jsonObject.getString("category_id");


                            List<String> name = new ArrayList<String>();
                            name.add(sku);

                            for (int j = 0; j < name.size(); j++) {

                                apiCallForSingleCategory(name.get(0));
                                if (alertDialog == null)
                                    alertDialog = AlertUtils.createProgressDialog(getActivity());
                                alertDialog.show();

                            }
                            gvCategoriesItems.deferNotifyDataSetChanged();

                        }
                    }
                    else
                    for (int i = 0; i < lazyLoading; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sku = jsonObject.getString("sku");
                        skuID = sku;
                        String categoryID = jsonObject.getString("category_id");


                        List<String> name = new ArrayList<String>();
                        name.add(sku);

                        for (int j = 0; j < name.size(); j++) {

                            apiCallForSingleCategory(name.get(0));
                            if (alertDialog == null)
                                alertDialog = AlertUtils.createProgressDialog(getActivity());
                            alertDialog.show();

                        }
                        gvCategoriesItems.deferNotifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + Configuration.TOKEN);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }


    private void apiCallForSingleCategory(String sku) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.BASE + "products/" + sku
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String name = jsonObject.getString("name");
                    String price = jsonObject.getString("price");

                    JSONArray jsonArray = jsonObject.getJSONArray("media_gallery_entries");

                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                    JSONObject jsonObject3 = jsonArray.getJSONObject(2);
                    JSONObject jsonObject4 = jsonArray.getJSONObject(3);
                    strImage1 = jsonObject1.getString("file");
                    strImage2 = jsonObject2.getString("file");
                    strImage3 = jsonObject3.getString("file");
                    strImage4 = jsonObject4.getString("file");


                    JSONArray jsonArrayDescp = jsonObject.getJSONArray("custom_attributes");
                    JSONObject jsonObjectDescp = jsonArrayDescp.getJSONObject(0);
                    String description = jsonObjectDescp.getString("value");


                    CategoryItemsModel model = new CategoryItemsModel();
                    model.setName(name);
                    model.setPrice(price);
                    model.setImage1(strImage1);
                    model.setImage2(strImage2);
                    model.setImage3(strImage3);
                    model.setImage4(strImage4);
                    model.setDescription(description);
                    categoryItemsModelList.add(model);
                    categoriesItemAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + Configuration.TOKEN);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
