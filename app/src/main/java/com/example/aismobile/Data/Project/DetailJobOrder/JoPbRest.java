package com.example.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoPbRest implements Parcelable {

    private int cash_advance_detail_id;
    private String item_name;
    private String quantity;
    private String unit_abbr;
    private String unit_price;
    private String advance_app1;
    private String advance_app2;
    private String advance_app3;

    public JoPbRest(int cash_advance_detail_id, String item_name, String quantity, String unit_abbr, String unit_price){
        this.cash_advance_detail_id = cash_advance_detail_id;
        this.item_name = item_name;
        this.quantity = quantity;
        this.unit_abbr = unit_abbr;
        this.unit_price = unit_price;
    }

    protected JoPbRest(Parcel in) {
        cash_advance_detail_id = in.readInt();
        item_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price = in.readString();
        advance_app1 = in.readString();
        advance_app2 = in.readString();
        advance_app3 = in.readString();
    }

    public JoPbRest(JSONObject jsonObject){
        try {
            this.cash_advance_detail_id = jsonObject.getInt("cash_advance_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price = jsonObject.getString("unit_price");
            this.advance_app1 = jsonObject.getString("advance_app1");
            this.advance_app2 = jsonObject.getString("advance_app2");
            this.advance_app3 = jsonObject.getString("advance_app3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoPbRest> CREATOR = new Creator<JoPbRest>() {
        @Override
        public JoPbRest createFromParcel(Parcel in) {
            return new JoPbRest(in);
        }

        @Override
        public JoPbRest[] newArray(int size) {
            return new JoPbRest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cash_advance_detail_id);
        dest.writeString(item_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price);
        dest.writeString(advance_app1);
        dest.writeString(advance_app2);
        dest.writeString(advance_app3);
    }

    public String getAdvance_app1() {
        return advance_app1;
    }

    public String getAdvance_app2() {
        return advance_app2;
    }

    public String getAdvance_app3() {
        return advance_app3;
    }

    public int getCash_advance_detail_id() {
        return cash_advance_detail_id;
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
}
