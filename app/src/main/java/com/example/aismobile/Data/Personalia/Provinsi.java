package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Provinsi implements Parcelable {

    private int prov_id;
    private String prov_name;

    public Provinsi (int prov_id, String prov_name){
        this.prov_id = prov_id;
        this.prov_name = prov_name;
    }

    protected Provinsi (Parcel in) {
        prov_id = in.readInt();
        prov_name = in.readString();
    }

    public Provinsi (JSONObject jsonObject){
        try {
            this.prov_id = jsonObject.getInt("prov_id");
            this.prov_name = jsonObject.getString("prov_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Provinsi> CREATOR = new Creator<Provinsi>() {
        @Override
        public Provinsi createFromParcel(Parcel in) {
            return new Provinsi(in);
        }

        @Override
        public Provinsi[] newArray(int size) {
            return new Provinsi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(prov_id);
        dest.writeString(prov_name);
    }

    public int getProv_id() {
        return prov_id;
    }

    public String getProv_name() {
        return prov_name;
    }
}
