package com.example.android.baking;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Krzys on 18.04.2018.
 */

@SuppressWarnings({"ALL", "DefaultFileTemplate"})
public class SendDataToWidget implements Parcelable {



    private String nameOfCake;
    String mIngredients;
    String mMeasure;
    String mQuantity;
    public SendDataToWidget(String NameOfCake,String Ingredients,String Measure,String Quantity) {
        nameOfCake = NameOfCake;
        mIngredients = Ingredients;
        mMeasure = Measure;
        mQuantity = Quantity;
    }

    public static final Creator<SendDataToWidget> CREATOR = new Creator<SendDataToWidget>() {
        @Override
        public SendDataToWidget createFromParcel(Parcel in) {
            return new SendDataToWidget(in);
        }

        @Override
        public SendDataToWidget[] newArray(int size) {
            return new SendDataToWidget[size];
        }
    };

    private SendDataToWidget(Parcel in) {
        this.mQuantity = in.readString();
        this.mMeasure = in.readString();
        this.mIngredients = in.readString();
        this.nameOfCake = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.mIngredients);
        parcel.writeString(this.mMeasure);
        parcel.writeString(this.mQuantity);
        parcel.writeString(this.nameOfCake);
    }

    public String getNameOfCake() {
        return nameOfCake;
    }

    public void setNameOfCake(String nameOfCake) {
        this.nameOfCake = nameOfCake;
    }

    public String getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(String mIngredients) {
        this.mIngredients = mIngredients;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }
}
