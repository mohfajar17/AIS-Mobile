package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PickupDetail implements Parcelable {

    private String item_name;
    private String item_specification;
    private String location;
    private String quantity_taked;
    private String picked_up;
    private String taken;
    private String unit_abbr;
    private String notes;

    protected PickupDetail(Parcel in) {
        item_name = in.readString();
        item_specification = in.readString();
        location = in.readString();
        quantity_taked = in.readString();
        picked_up = in.readString();
        taken = in.readString();
        unit_abbr = in.readString();
        notes = in.readString();
    }

    public PickupDetail(JSONObject jsonObject){
        try {
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.location = jsonObject.getString("location");
            this.quantity_taked = jsonObject.getString("quantity_taked");
            this.picked_up = jsonObject.getString("picked_up");
            this.taken = jsonObject.getString("taken");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PickupDetail> CREATOR = new Creator<PickupDetail>() {
        @Override
        public PickupDetail createFromParcel(Parcel in) {
            return new PickupDetail(in);
        }

        @Override
        public PickupDetail[] newArray(int size) {
            return new PickupDetail[size];
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
        dest.writeString(location);
        dest.writeString(quantity_taked);
        dest.writeString(picked_up);
        dest.writeString(taken);
        dest.writeString(unit_abbr);
        dest.writeString(notes);
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getLocation() {
        return location;
    }

    public String getQuantity_taked() {
        return quantity_taked;
    }

    public String getPicked_up() {
        return picked_up;
    }

    public String getTaken() {
        return taken;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getNotes() {
        return notes;
    }
}
