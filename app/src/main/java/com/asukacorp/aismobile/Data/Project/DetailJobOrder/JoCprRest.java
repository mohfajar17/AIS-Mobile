package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoCprRest implements Parcelable {

    private int responsbility_advance_detail;
    private String usage_date;
    private String item_name;
    private String quantity;
    private String unit_abbr;
    private String unit_price;
    private String discount;

    public JoCprRest(int responsbility_advance_detail, String usage_date, String item_name, String quantity,
                     String unit_abbr, String unit_price, String discount){
        this.responsbility_advance_detail = responsbility_advance_detail;
        this.usage_date = usage_date;
        this.item_name = item_name;
        this.quantity = quantity;
        this.unit_abbr = unit_abbr;
        this.unit_price = unit_price;
        this.discount = discount;
    }

    protected JoCprRest(Parcel in) {
        responsbility_advance_detail = in.readInt();
        usage_date = in.readString();
        item_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price = in.readString();
        discount = in.readString();
    }

    public JoCprRest(JSONObject jsonObject){
        try {
            this.responsbility_advance_detail = jsonObject.getInt("responsbility_advance_detail");
            this.usage_date = jsonObject.getString("usage_date");
            this.item_name = jsonObject.getString("item_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price = jsonObject.getString("unit_price");
            this.discount = jsonObject.getString("discount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoCprRest> CREATOR = new Creator<JoCprRest>() {
        @Override
        public JoCprRest createFromParcel(Parcel in) {
            return new JoCprRest(in);
        }

        @Override
        public JoCprRest[] newArray(int size) {
            return new JoCprRest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(responsbility_advance_detail);
        dest.writeString(usage_date);
        dest.writeString(item_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price);
        dest.writeString(discount);
    }

    public int getResponsbility_advance_detail() {
        return responsbility_advance_detail;
    }

    public String getUsage_date() {
        return usage_date;
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

    public String getUnit_price() {
        return unit_price;
    }

    public String getDiscount() {
        return discount;
    }
}
