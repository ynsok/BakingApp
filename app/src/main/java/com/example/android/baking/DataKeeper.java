package com.example.android.baking;

import com.example.android.baking.ModelData.BakingModelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzys on 02.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
class DataKeeper {
    private static ArrayList stepData;
    private static ArrayList mIngredientsData;

    public DataKeeper() {


    }

    public static ArrayList ingrediendsServers() {
        return mIngredientsData;


    }

    public static ArrayList stepsServers() {
        return stepData;
    }

    public void takeData(List<BakingModelData> getData) {
        if (getData == null || getData.size() == 0) return;
        fetchIngredients(getData);
        fetchSteps(getData);
    }

    private void fetchIngredients(List<BakingModelData> bakingData) {
        mIngredientsData = new ArrayList<>();
        for (int x = 0; x < bakingData.size(); x++) {
            mIngredientsData.add(bakingData.get(x).getIngredients());

        }


    }

    private void fetchSteps(List<BakingModelData> bakingData) {
        stepData = new ArrayList<>();
        for (int x = 0; x < bakingData.size(); x++) {
            stepData.add(bakingData.get(x).getSteps());
        }

    }
}






