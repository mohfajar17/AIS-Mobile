package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MaterialRequestPickup implements Parcelable {
    private String pickup_number;
    private String pickup_by;
    private String taken_date;
    private String recognized;

    protected MaterialRequestPickup(Parcel in) {
        pickup_number = in.readString();
        pickup_by = in.readString();
        taken_date = in.readString();
        recognized = in.readString();
    }

    public MaterialRequestPickup(JSONObject jsonObject){
        try {
            this.pickup_number = jsonObject.getString("pickup_number");
            this.pickup_by = jsonObject.getString("pickup_by");
            this.taken_date = jsonObject.getString("taken_date");
            this.recognized = jsonObject.getString("recognized");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MaterialRequestPickup> CREATOR = new Creator<MaterialRequestPickup>() {
        @Override
        public MaterialRequestPickup createFromParcel(Parcel in) {
            return new MaterialRequestPickup(in);
        }

        @Override
        public MaterialRequestPickup[] newArray(int size) {
            return new MaterialRequestPickup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pickup_number);
        dest.writeString(pickup_by);
        dest.writeString(taken_date);
        dest.writeString(recognized);
    }

    public String getPickup_number() {
        return pickup_number;
    }

    public String getPickup_by() {
        return pickup_by;
    }

    public String getTaken_date() {
        return taken_date;
    }

    public String getRecognized() {
        return recognized;
    }
}
