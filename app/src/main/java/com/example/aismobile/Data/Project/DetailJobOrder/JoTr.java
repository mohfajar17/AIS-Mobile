package com.example.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoTr implements Parcelable {

    private int assetRental;
    private String qty;
    private String price;
    private String assetName;
    private String assetDesc;
    private String unit;

    public JoTr(int assetRental, String qty, String price, String assetName, String assetDesc,
                  String unit){
        this.assetRental = assetRental;
        this.qty = qty;
        this.price = price;
        this.assetName = assetName;
        this.assetDesc = assetDesc;
        this.unit = unit;
    }

    protected JoTr(Parcel in) {
        assetRental = in.readInt();
        qty = in.readString();
        price = in.readString();
        assetName = in.readString();
        assetDesc = in.readString();
        unit = in.readString();
    }

    public JoTr(JSONObject jsonObject){
        try {
            this.assetRental = jsonObject.getInt("assetRental");
            this.qty = jsonObject.getString("Qty");
            this.price = jsonObject.getString("price");
            this.assetName = jsonObject.getString("assetName");
            this.assetDesc = jsonObject.getString("assetDesc");
            this.unit = jsonObject.getString("unit");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoTr> CREATOR = new Creator<JoTr>() {
        @Override
        public JoTr createFromParcel(Parcel in) {
            return new JoTr(in);
        }

        @Override
        public JoTr[] newArray(int size) {
            return new JoTr[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(assetRental);
        dest.writeString(qty);
        dest.writeString(price);
        dest.writeString(assetName);
        dest.writeString(assetDesc);
        dest.writeString(unit);
    }

    public int getAssetRental() {
        return assetRental;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public String getUnit() {
        return unit;
    }
}
