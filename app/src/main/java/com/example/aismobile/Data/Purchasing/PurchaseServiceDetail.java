package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseServiceDetail implements Parcelable {

    private String work_order_detail_id;
    private String item_name;
    private String quantity;
    private String unit_abbr;
    private String unit_price_buy;
    private String max_budget;
    private String discount;
    private String ps_app1;

    protected PurchaseServiceDetail(Parcel in) {
        work_order_detail_id = in.readString();
        item_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price_buy = in.readString();
        max_budget = in.readString();
        discount = in.readString();
        ps_app1 = in.readString();
    }

    public PurchaseServiceDetail(JSONObject jsonObject){
        try {
            this.work_order_detail_id = jsonObject.getString("work_order_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price_buy = jsonObject.getString("unit_price");
            this.max_budget = jsonObject.getString("max_budget");
            this.discount = jsonObject.getString("discount");
            this.ps_app1 = jsonObject.getString("ps_app1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PurchaseServiceDetail> CREATOR = new Creator<PurchaseServiceDetail>() {
        @Override
        public PurchaseServiceDetail createFromParcel(Parcel in) {
            return new PurchaseServiceDetail(in);
        }

        @Override
        public PurchaseServiceDetail[] newArray(int size) {
            return new PurchaseServiceDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(work_order_detail_id);
        dest.writeString(item_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price_buy);
        dest.writeString(max_budget);
        dest.writeString(discount);
        dest.writeString(ps_app1);
    }

    public String getWork_order_detail_id() {
        return work_order_detail_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getUnit_price_buy() {
        return unit_price_buy;
    }

    public String getMax_budget() {
        return max_budget;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPs_app1() {
        return ps_app1;
    }
}
