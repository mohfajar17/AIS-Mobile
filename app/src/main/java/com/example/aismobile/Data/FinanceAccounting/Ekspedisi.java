package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Ekspedisi implements Parcelable {
    private String ekspedisi_code;
    private String ekspedisi_name;

    protected Ekspedisi(Parcel in) {
        ekspedisi_code = in.readString();
        ekspedisi_name = in.readString();
    }

    public Ekspedisi(JSONObject jsonObject){
        try {
            this.ekspedisi_code = jsonObject.getString("ekspedisi_code");
            this.ekspedisi_name = jsonObject.getString("ekspedisi_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Ekspedisi> CREATOR = new Creator<Ekspedisi>() {
        @Override
        public Ekspedisi createFromParcel(Parcel in) {
            return new Ekspedisi(in);
        }

        @Override
        public Ekspedisi[] newArray(int size) {
            return new Ekspedisi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ekspedisi_code);
        dest.writeString(ekspedisi_name);
    }

    public String getEkspedisi_code() {
        return ekspedisi_code;
    }

    public String getEkspedisi_name() {
        return ekspedisi_name;
    }
}
