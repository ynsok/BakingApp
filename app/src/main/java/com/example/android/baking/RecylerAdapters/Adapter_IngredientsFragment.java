package com.example.android.baking.RecylerAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.ModelData.IngredientsModelData;
import com.example.android.baking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krzys on 05.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class Adapter_IngredientsFragment extends RecyclerView.Adapter<Adapter_IngredientsFragment.ViewHolder> {
    private final Context context;
    private List<IngredientsModelData> ingredientsData;

    public Adapter_IngredientsFragment(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ingredients_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String measure = ingredientsData.get(position).getMeasure();
        String quantity = String.valueOf(ingredientsData.get(position).getQuantity());
        String ingredients = ingredientsData.get(position).getIngredient();

        if (measure == null || measure.isEmpty()) {
            holder.mMeasure.setText("-------");
        } else holder.mMeasure.setText(measure);

        if (quantity.isEmpty()) {
            holder.mQuantity.setText("-------");
        } else {
            holder.mQuantity.setText(quantity);
        }
        if(ingredients.isEmpty()){
            holder.mIngredients.setText("-----");
        }else {
            holder.mIngredients.setText(ingredients);
        }

    }

    @Override
    public int getItemCount() {
        return ingredientsData.size();
    }

    public void takeData(List<IngredientsModelData> data) {
        if (ingredientsData == data) return;
        if (data != null) {
            ingredientsData = data;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_ing) TextView mIngredients;
        @BindView(R.id.textView_measure) TextView mMeasure;
        @BindView(R.id.textView_quantity) TextView mQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
