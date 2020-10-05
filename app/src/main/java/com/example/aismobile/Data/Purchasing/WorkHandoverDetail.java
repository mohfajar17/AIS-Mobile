package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkHandoverDetail implements Parcelable {

    private String item_name;
    private String quantity_received;
    private String unit_abbr;
    private String notes;
    private String work_order_description;
    private String wo_notes;

    protected WorkHandoverDetail(Parcel in) {
        item_name = in.readString();
        quantity_received = in.readString();
        unit_abbr = in.readString();
        notes = in.readString();
        work_order_description = in.readString();
        wo_notes = in.readString();
    }

    public WorkHandoverDetail(JSONObject jsonObject){
        try {
            this.item_name = jsonObject.getString("item_name");
            this.quantity_received = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.notes = jsonObject.getString("notes");
            this.work_order_description = jsonObject.getString("work_order_description");
            this.wo_notes = jsonObject.getString("wo_notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkHandoverDetail> CREATOR = new Creator<WorkHandoverDetail>() {
        @Override
        public WorkHandoverDetail createFromParcel(Parcel in) {
            return new WorkHandoverDetail(in);
        }

        @Override
        public WorkHandoverDetail[] newArray(int size) {
            return new WorkHandoverDetail[size];
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
        dest.writeString(work_order_description);
        dest.writeString(wo_notes);
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

    public String getWork_order_description() {
        return work_order_description;
    }

    public String getWo_notes() {
        return wo_notes;
    }
}
