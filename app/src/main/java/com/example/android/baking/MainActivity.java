package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android.baking.ModelData.BakingModelData;
import com.example.android.baking.RecylerAdapters.Adapter_MainActivity;
import com.example.android.baking.RetrofitUsage.RetrofitClass;
import com.example.android.baking.RetrofitUsage.Service;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity implements StartNewActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String POSITION = "position";
    public static final String NAME = "NAME";
    private List<BakingModelData> mBakingData;
    private Adapter_MainActivity mAdapterMainActivity;
    private DataKeeper mDataKeeper;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recyclerView_MainActivity)
    RecyclerView mRecyclerViewMainActivity;
    @BindView(R.id.activity_layout)
    LinearLayout mLinearLayout;
    private LinearLayoutManager mLinear;
    private final static String ADAPTER_POSITION = "adapter position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDataKeeper = new DataKeeper();
        implementViews();
        final int postion;
        if (savedInstanceState != null) {
            postion = savedInstanceState.getInt(ADAPTER_POSITION);
        } else {
            postion = -1;
        }

        if (checkNet()) {
            getBakingDataFromNetw(postion);
        } else {
            Snackbar snackbar = Snackbar.make(mLinearLayout, R.string.checkConnection, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.Retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getBakingDataFromNetw(postion);

                        }
                    });
            snackbar.show();
        }


    }


    private void getBakingDataFromNetw(final int position) {

        Service service = RetrofitClass.getServiceCall();

        Call<JsonArray> getBakingData = service.getBakingList();
        getBakingData.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {


                @SuppressWarnings("ConstantConditions") String jsonResponse = response.body().toString();

                Type mBakingType = new TypeToken<List<BakingModelData>>() {
                }.getType();
                mBakingData = getIngredientsFromJson(mBakingType, jsonResponse);
                mAdapterMainActivity.getBakingData(mBakingData);
                mDataKeeper.takeData(mBakingData);
                if (position != -1) {
                    mLinear.scrollToPosition(position);
                }


            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {

                Snackbar snackbar = Snackbar.make(mLinearLayout, R.string.checkConnection, Snackbar.LENGTH_LONG)
                        .setAction(R.string.Retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getBakingDataFromNetw(position);

                            }
                        });
                snackbar.show();
            }

        });


    }

    private boolean checkNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    private static <T> List<T> getIngredientsFromJson(Type type, String Json) {
        if (!isValid(Json)) {
            return null;
        }
        return new Gson().fromJson(Json, type);
    }

    private static boolean isValid(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException j) {
            return false;
        }
    }

    private void implementViews() {
        mLinear = new LinearLayoutManager(this);
        mRecyclerViewMainActivity.setLayoutManager(mLinear);
        mRecyclerViewMainActivity.setHasFixedSize(true);
        mAdapterMainActivity = new Adapter_MainActivity(this, this);
        mRecyclerViewMainActivity.setAdapter(mAdapterMainActivity);
    }

    @Override
    public void startNewActivity(int position, String name) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(POSITION, position);
        intent.putExtra(NAME, name);
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ADAPTER_POSITION, mLinear.findLastCompletelyVisibleItemPosition());

    }
}
