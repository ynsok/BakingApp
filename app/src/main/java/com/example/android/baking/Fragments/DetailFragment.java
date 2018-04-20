package com.example.android.baking.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.baking.ModelData.StepModelData;
import com.example.android.baking.R;
import com.example.android.baking.RecylerAdapters.Adapter_DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class DetailFragment extends Fragment implements Adapter_DetailActivity.StartFragmentWithMovie {
    @BindView(R.id.recylerView_DetailActivity)
    RecyclerView mRecyclerView_Detail;

    private static final int DEFAULD_VALUE = 0;
    private int getPosition;
    private List<StepModelData> mData;

    private WhenIngredientsViewIsClicked mViewClicked;
    private StartStepFragment mStartStepFragment;
    private final String TAG = "DetailFragment";
    private String DETAIL_KEY = "DETAIL_KEY";

    public interface StartStepFragment {
        void startStepFragment(int id);
    }

    public interface WhenIngredientsViewIsClicked {
        void onClickView();
    }

    public DetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mViewClicked = (WhenIngredientsViewIsClicked) context;
            mStartStepFragment = (StartStepFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement WhenIngredientsViewIsClicked");

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        initializeViews(view);


        return view;
    }

    private void initializeViews(View view) {
        ButterKnife.bind(this, view);
        mRecyclerView_Detail.setHasFixedSize(true);
        mRecyclerView_Detail.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter_DetailActivity mAdapterDetail = new Adapter_DetailActivity(getContext(), this);
        mRecyclerView_Detail.setAdapter(mAdapterDetail);

        mAdapterDetail.getStepModelData(mData);

    }

    public void getDataFromActivity(List<StepModelData> getData) {
        mData = getData;

    }

    @OnClick(R.id.TextView_DetailIngredients)
    public void onClickTextView() {

        mViewClicked.onClickView();


    }

    @Override
    public void startFragmentsWithMovie(int id) {
        mStartStepFragment.startStepFragment(id);
    }


}
