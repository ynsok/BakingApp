package com.example.android.baking.RecylerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.ModelData.StepModelData;
import com.example.android.baking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krzys on 04.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class Adapter_DetailActivity extends RecyclerView.Adapter<Adapter_DetailActivity.ViewHolder> {
    private final Context context;

    private List<StepModelData> modelData;
    private final StartFragmentWithMovie mStartFragment;

    public interface StartFragmentWithMovie {
        void startFragmentsWithMovie(int position);
    }

    @Override
    public Adapter_DetailActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_steps_description, parent, false);
        return new ViewHolder(view);
    }

    public Adapter_DetailActivity(Context context, StartFragmentWithMovie mStartFragmentFromCon) {
        this.context = context;
        mStartFragment = mStartFragmentFromCon;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Adapter_DetailActivity.ViewHolder holder, int position) {


        String getShortDescription = modelData.get(position).getShortDescription();
        int id = modelData.get(position).getId();
        holder.getIdFromData(id);
        if (getShortDescription == null || getShortDescription.isEmpty()) {
            holder.textView_StepsDesctiption.setText("The step is to simply");
        } else {
            holder.textView_StepsDesctiption.setText(getShortDescription);
        }

    }

    @Override
    public int getItemCount() {

        return modelData.size();
    }

    public void getStepModelData(List<StepModelData> mData) {
        if (mData == modelData) return;
        if (mData != null) {
            modelData = mData;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int getId;
        @BindView(R.id.TextView_StepsDescription)
        TextView textView_StepsDesctiption;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            textView_StepsDesctiption.setOnClickListener(this);

        }

        void getIdFromData(int id) {
            getId = id;
        }


        @Override
        public void onClick(View view) {
            mStartFragment.startFragmentsWithMovie(getId);
        }
    }
}
