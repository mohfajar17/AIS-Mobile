package com.asukacorp.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MaterialReturnDetail implements Parcelable {
    private String item_name;
    private String item_specification;
    private String warehouse_name;
    private String quantity;
    private String unit_abbr;

    protected MaterialReturnDetail(Parcel in) {
        item_name = in.readString();
        item_specification = in.readString();
        warehouse_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
    }

    public MaterialReturnDetail(JSONObject jsonObject){
        try {
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.warehouse_name = jsonObject.getString("warehouse_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MaterialReturnDetail> CREATOR = new Creator<MaterialReturnDetail>() {
        @Override
        public MaterialReturnDetail createFromParcel(Parcel in) {
            return new MaterialReturnDetail(in);
        }

        @Override
        public MaterialReturnDetail[] newArray(int size) {
            return new MaterialReturnDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(warehouse_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }
}
