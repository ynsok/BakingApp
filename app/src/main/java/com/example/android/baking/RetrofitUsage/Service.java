package com.example.android.baking.RetrofitUsage;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Krzys on 01.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public interface Service {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<JsonArray> getBakingList();
}
