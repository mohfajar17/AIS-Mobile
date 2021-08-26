package com.asukacorp.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class GoodRecivedNoteDetail implements Parcelable {

    private String item_name;
    private String quantity_received;
    private String unit_abbr;
    private String notes;
    private String item_specification;
    private String warehouse_name;
    private String job_order_number;

    protected GoodRecivedNoteDetail(Parcel in) {
        item_name = in.readString();
        quantity_received = in.readString();
        unit_abbr = in.readString();
        notes = in.readString();
        item_specification = in.readString();
        warehouse_name = in.readString();
        job_order_number = in.readString();
    }

    public GoodRecivedNoteDetail(JSONObject jsonObject){
        try {
            this.item_name = jsonObject.getString("item_name");
            this.quantity_received = jsonObject.getString("quantity_received");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.notes = jsonObject.getString("notes");
            this.item_specification = jsonObject.getString("item_specification");
            this.warehouse_name = jsonObject.getString("warehouse_name");
            this.job_order_number = jsonObject.getString("job_order_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<GoodRecivedNoteDetail> CREATOR = new Creator<GoodRecivedNoteDetail>() {
        @Override
        public GoodRecivedNoteDetail createFromParcel(Parcel in) {
            return new GoodRecivedNoteDetail(in);
        }

        @Override
        public GoodRecivedNoteDetail[] newArray(int size) {
            return new GoodRecivedNoteDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_name);
        dest.writeString(quantity_received);
        dest.writeString(unit_abbr);
        dest.writeString(notes);
        dest.writeString(item_specification);
        dest.writeString(warehouse_name);
        dest.writeString(job_order_number);
    }

    public String getItem_name() {
        return item_name;
    }

    public String getQuantity_received() {
        return quantity_received;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getNotes() {
        return notes;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public String getJob_order_number() {
        return job_order_number;
    }
}
