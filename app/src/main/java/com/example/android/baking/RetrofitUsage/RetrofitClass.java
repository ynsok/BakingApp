package com.example.android.baking.RetrofitUsage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Krzys on 01.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class RetrofitClass {

        private static Service service;
        private static final String BaseURL = "https://d17h27t6h515a5.cloudfront.net/";


        public static Service getServiceCall()
        {
            if(service == null){
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BaseURL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();


                service = retrofit.create(Service.class);

            }

            return service;
        }
}
