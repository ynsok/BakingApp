package com.example.android.baking.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.baking.ModelData.IngredientsModelData;
import com.example.android.baking.R;
import com.example.android.baking.RecylerAdapters.Adapter_IngredientsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("ALL")
public class IngredientsFragment extends Fragment {
    private List<IngredientsModelData> mData;


    @BindView(R.id.recyclerView_Ingredients)
    RecyclerView mRecyclerView;
    private Adapter_IngredientsFragment mAdapter;
    private String LIST_INSTANCE = "LIST_INSTANCE";
    private final String TAG = "IngredientsFragment";

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);

        implementView();
        sendDataToAdapter();
        return view;
    }

    private void implementView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter_IngredientsFragment(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void sendDataToAdapter() {
        mAdapter.takeData(mData);
    }


    public void takeIngredient(List<IngredientsModelData> ingredients) {
        mData = ingredients;

    }


}



