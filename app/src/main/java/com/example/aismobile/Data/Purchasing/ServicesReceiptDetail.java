package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ServicesReceiptDetail implements Parcelable {

    private String item_name;
    private String quantity;
    private String unit_abbr;
    private String notes;

    protected ServicesReceiptDetail(Parcel in) {
        item_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        notes = in.readString();
    }

    public ServicesReceiptDetail(JSONObject jsonObject){
        try {
            this.item_name = jsonObject.getString("item_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ServicesReceiptDetail> CREATOR = new Creator<ServicesReceiptDetail>() {
        @Override
        public ServicesReceiptDetail createFromParcel(Parcel in) {
            return new ServicesReceiptDetail(in);
        }

        @Override
        public ServicesReceiptDetail[] newArray(int size) {
            return new ServicesReceiptDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(notes);
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

    public String getNotes() {
        return notes;
    }
}
