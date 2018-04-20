package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Krzys on 18.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class MyWidgetRemoteViewsSerive extends RemoteViewsService {
    private final static String TAG = "Widget";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewFactory(this.getApplicationContext());
    }

    private class MyWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
        private final Context context;
        private ArrayList<SendDataToWidget> myData;

        public MyWidgetRemoteViewFactory(Context applicationContext) {
            context = applicationContext;
            myData = new ArrayList<>();
        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String json = preferences.getString(DetailActivity.SHARED_PREF_KEY, "");

            if (!json.equals("")) {
                Gson gson = new Gson();
                myData = gson.fromJson(json, new TypeToken<ArrayList<SendDataToWidget>>() {
                }.getType());
            }

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (myData != null) {
                return myData.size();
            } else return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            rv.setTextViewText(R.id.widget_measure, myData.get(i).mMeasure);

            rv.setTextViewText(R.id.widget_ing, myData.get(i).mIngredients);

            rv.setTextViewText(R.id.widget_quantity, myData.get(i).mQuantity);


            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
