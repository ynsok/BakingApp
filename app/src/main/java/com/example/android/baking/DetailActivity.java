package com.example.android.baking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.baking.Fragments.DetailFragment;
import com.example.android.baking.Fragments.IngredientsFragment;
import com.example.android.baking.Fragments.StepFragment;
import com.example.android.baking.ModelData.IngredientsModelData;
import com.example.android.baking.ModelData.StepModelData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailFragment.WhenIngredientsViewIsClicked, DetailFragment.StartStepFragment, StepFragment.SkipToNextPosition,StepFragment.ClickPreviousButton{
    private static final int DEFAULD_VALUE = 0;
    public final static String SHARED_PREF_KEY = "KEY_FOR_PREFERENCES";
    public final static String NAME = "It's name";
    private FragmentManager mFragManager;
    private DetailFragment mDetailFrag;
    private IngredientsFragment mIngredFrag;
    private StepFragment mStepFragment;
    private List<StepModelData> mStepModelData;
    private List<IngredientsModelData> mIngredientsModelData;
    private final String DETAIL_TAG = "IngredientsFragmetnTag";
    private String getName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
        getSupportActionBar().setTitle(getName);
        mFragManager = getSupportFragmentManager();
        mDetailFrag = new DetailFragment();
        mIngredFrag = new IngredientsFragment();
        sendDataToWidget();


        if (savedInstanceState == null) {
            catchTheData();


        }

    }

    private void catchTheData() {


        mDetailFrag.getDataFromActivity(mStepModelData);
        mIngredFrag.takeIngredient(mIngredientsModelData);


        if (getResources().getConfiguration().screenWidthDp >= 600) {
            mFragManager.beginTransaction().replace(R.id.layout_600dp, mIngredFrag).commit();
            mFragManager.beginTransaction().replace(R.id.layout_container, mDetailFrag, DETAIL_TAG).commit();

        }else {
            mFragManager.beginTransaction().replace(R.id.layout_container, mDetailFrag, DETAIL_TAG).commit();
        }

    }

    private void getData() {
        getName = getIntent().getStringExtra(MainActivity.NAME);
        int getPosition = getIntent().getIntExtra(MainActivity.POSITION, DEFAULD_VALUE);
        //noinspection unchecked
        mStepModelData = (List<StepModelData>) DataKeeper.stepsServers().get(getPosition);
        //noinspection unchecked
        mIngredientsModelData = (List<IngredientsModelData>) DataKeeper.ingrediendsServers().get(getPosition);


    }

    @Override
    public void onClickView() {
        mIngredFrag.takeIngredient(mIngredientsModelData);

        if (getResources().getConfiguration().screenWidthDp >= 600) {
            mFragManager.beginTransaction().replace(R.id.layout_600dp, mIngredFrag).addToBackStack(null).commit();

        } else {
            mFragManager.beginTransaction().replace(R.id.layout_container, mIngredFrag).addToBackStack(null)
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();




        if (getResources().getConfiguration().screenWidthDp >= 600) {
            finish();
        }


    }


    @Override
    public void startStepFragment(int id) {
        createThePortionOfData(id);
        if (getResources().getConfiguration().screenWidthDp >= 600) {
            mFragManager.beginTransaction().replace(R.id.layout_600dp, mStepFragment)
                    .commit();
        } else {
            mFragManager.beginTransaction().replace(R.id.layout_container, mStepFragment).addToBackStack(null).commit();
        }


    }


    private void createThePortionOfData(int id) {
        String getDesription = mStepModelData.get(id).getDescription();
        String getURL;

            mStepFragment =new StepFragment();
        if (!mStepModelData.get(id).getVideoURL().isEmpty()) {
            getURL = mStepModelData.get(id).getVideoURL();
        } else if (!mStepModelData.get(id).getThumbnailURL().isEmpty()) {
            getURL = mStepModelData.get(id).getThumbnailURL();
        } else {
            getURL = null;
        }
        boolean check = false;


        if (mStepModelData.size() - 1 > id) {
            check = true;
        }

        mStepFragment.getStepData(getDesription, getURL, check, id);



    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        if (mFragManager.findFragmentByTag(DETAIL_TAG).isVisible()) {
            String SAVE_BUNDLE = "it's FRagments Tag";
            outState.putString(SAVE_BUNDLE, DETAIL_TAG);
        }


    }


    @Override
    public void startNextStepItem(int position) {



        createThePortionOfData(position);
        if (getResources().getConfiguration().screenWidthDp >= 600) {
            mFragManager.beginTransaction().replace(R.id.layout_600dp, mStepFragment).commit();

        } else {
            mFragManager.beginTransaction().replace(R.id.layout_container, mStepFragment).addToBackStack(null).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getItem = item.getItemId();
        if (getItem == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendDataToWidget() {
        ArrayList<SendDataToWidget> mSendWidget = new ArrayList<>();
        for (int x = 0; x < mIngredientsModelData.size(); x++) {
            String mIngre = mIngredientsModelData.get(x).getIngredient();
            String mQuant = mIngredientsModelData.get(x).getMeasure();
            String mMeas = String.valueOf(mIngredientsModelData.get(x).getQuantity());

            mSendWidget.add(new SendDataToWidget(getName, mIngre, mMeas, mQuant));


        }
        Gson gson = new Gson();
        String json = gson.toJson(mSendWidget);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SHARED_PREF_KEY, json).apply();

        Intent intent = new Intent(this, BakingWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE\"");
        intent.putExtra(NAME, getName);
        sendBroadcast(intent);


    }


    @Override
    public void clickPreviousButton() {
        onBackPressed();
    }
}

