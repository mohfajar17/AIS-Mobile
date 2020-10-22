package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CashOnDeliveryDetail implements Parcelable {

    private String cash_on_delivery_detail_id;
    private String item_name;
    private String quantity;
    private String unit_abbr;
    private String unit_price_buy;
    private String max_budget;
    private String discount;
    private String cod_app1;

    protected CashOnDeliveryDetail(Parcel in) {
        cash_on_delivery_detail_id = in.readString();
        item_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price_buy = in.readString();
        max_budget = in.readString();
        discount = in.readString();
        cod_app1 = in.readString();
    }

    public CashOnDeliveryDetail(JSONObject jsonObject){
        try {
            this.cash_on_delivery_detail_id = jsonObject.getString("cash_on_delivery_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price_buy = jsonObject.getString("unit_price");
            this.max_budget = jsonObject.getString("max_budget");
            this.discount = jsonObject.getString("discount");
            this.cod_app1 = jsonObject.getString("cod_app1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CashOnDeliveryDetail> CREATOR = new Creator<CashOnDeliveryDetail>() {
        @Override
        public CashOnDeliveryDetail createFromParcel(Parcel in) {
            return new CashOnDeliveryDetail(in);
        }

        @Override
        public CashOnDeliveryDetail[] newArray(int size) {
            return new CashOnDeliveryDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cash_on_delivery_detail_id);
        dest.writeString(item_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price_buy);
        dest.writeString(max_budget);
        dest.writeString(discount);
        dest.writeString(cod_app1);
    }

    public String getCash_on_delivery_detail_id() {
        return cash_on_delivery_detail_id;
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

    public String getCod_app1() {
        return cod_app1;
    }
}
