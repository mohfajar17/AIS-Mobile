package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class StockDetail implements Parcelable {

    private int stock_adjustment_detail_id;
    private String item_name;
    private String item_specification;
    private String current_stock;
    private String actual_stock;
    private String adjustment_value;
    private String unit_price;
    private String notes;

    protected StockDetail(Parcel in) {
        stock_adjustment_detail_id = in.readInt();
        item_name = in.readString();
        item_specification = in.readString();
        current_stock = in.readString();
        actual_stock = in.readString();
        adjustment_value = in.readString();
        unit_price = in.readString();
        notes = in.readString();
    }

    public StockDetail(JSONObject jsonObject){
        try {
            this.stock_adjustment_detail_id = jsonObject.getInt("stock_adjustment_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.current_stock = jsonObject.getString("current_stock");
            this.actual_stock = jsonObject.getString("actual_stock");
            this.adjustment_value = jsonObject.getString("adjustment_value");
            this.unit_price = jsonObject.getString("unit_price");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<StockDetail> CREATOR = new Creator<StockDetail>() {
        @Override
        public StockDetail createFromParcel(Parcel in) {
            return new StockDetail(in);
        }

        @Override
        public StockDetail[] newArray(int size) {
            return new StockDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stock_adjustment_detail_id);
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(current_stock);
        dest.writeString(actual_stock);
        dest.writeString(adjustment_value);
        dest.writeString(unit_price);
        dest.writeString(notes);
    }

    public int getStock_adjustment_detail_id() {
        return stock_adjustment_detail_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getCurrent_stock() {
        return current_stock;
    }

    public String getActual_stock() {
        return actual_stock;
    }

    public String getAdjustment_value() {
        return adjustment_value;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public String getNotes() {
        return notes;
    }
}
