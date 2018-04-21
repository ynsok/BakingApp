package com.example.android.baking.RecylerAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.baking.ModelData.BakingModelData;
import com.example.android.baking.R;
import com.example.android.baking.StartNewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krzys on 02.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class Adapter_MainActivity extends RecyclerView.Adapter<Adapter_MainActivity.BakingViewHolder> {
    private final Context context;
    private List<BakingModelData> mBakingAdapterData;
    private final StartNewActivity mStartNewAct;

    public Adapter_MainActivity(Context context, StartNewActivity startNewActivity) {
        this.context = context;
        mBakingAdapterData = new ArrayList<>();
        mStartNewAct = startNewActivity;
    }

    @Override
    public BakingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_baking, parent, false);

        return new BakingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BakingViewHolder holder, int position) {
        if (mBakingAdapterData == null || mBakingAdapterData.size() == 0) return;

        String nameOfBaking = mBakingAdapterData.get(position).getName();
        holder.mTextViewCard.setText(nameOfBaking);
        String photoURI = mBakingAdapterData.get(position).getImage();
        if (photoURI.isEmpty()) {
            Picasso
                    .with(context)
                    .load(R.drawable.pobrane)
                    .placeholder(R.drawable.pobrane)
                    .error(R.drawable.pobrane)
                    .into(holder.mImageViewCard);

        } else {
            Picasso
                    .with(context)
                    .load(photoURI)
                    .into(holder.mImageViewCard);
        }


    }

    @Override
    public int getItemCount() {
        return mBakingAdapterData.size();
    }

    public void getBakingData(List<BakingModelData> data) {
        if (data == mBakingAdapterData) return;
        mBakingAdapterData = data;
        if (data != null) {
            notifyDataSetChanged();
        }

    }

    public class BakingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageView_cardView)
        ImageView mImageViewCard;
        @BindView(R.id.textView_cardView)
        TextView mTextViewCard;
        @BindView(R.id.cardView_recycler)
        CardView mCardView;

        public BakingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCardView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mStartNewAct.startNewActivity(getAdapterPosition(), (String) mTextViewCard.getText());

        }
    }
}
