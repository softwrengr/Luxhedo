package com.techease.luxhedo.networking;

import com.techease.luxhedo.dataModels.categoriesDataModel.CategoryModel;
import com.techease.luxhedo.dataModels.categorySKU.SkuModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by eapple on 29/08/2018.
 */

public interface ApiInterface {

    @GET("categories")
    Call<CategoryModel> categories(@Header("Authorization") String token);

    @GET("categories/{request_id}/products")
    Call<SkuModel> sku_model(@Path("request_id") int skuID,
                             @Header("Authorization") String token);


}
