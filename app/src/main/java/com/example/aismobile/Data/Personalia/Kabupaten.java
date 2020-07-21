package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Kabupaten implements Parcelable {

    private int kab_id;
    private String prov_name;
    private String kab_name;

    public Kabupaten (int kab_id, String prov_name, String kab_name){
        this.kab_id = kab_id;
        this.prov_name = prov_name;
        this.kab_name = kab_name;
    }
    protected Kabupaten (Parcel in) {
        kab_id = in.readInt();
        prov_name = in.readString();
        kab_name = in.readString();
    }

    public Kabupaten (JSONObject jsonObject){
        try {
            this.kab_id = jsonObject.getInt("kab_id");
            this.prov_name = jsonObject.getString("prov_name");
            this.kab_name = jsonObject.getString("kab_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Kabupaten> CREATOR = new Creator<Kabupaten>() {
        @Override
        public Kabupaten createFromParcel(Parcel in) {
            return new Kabupaten(in);
        }

        @Override
        public Kabupaten[] newArray(int size) {
            return new Kabupaten[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(kab_id);
        dest.writeString(prov_name);
        dest.writeString(kab_name);
    }

    public int getKab_id() {
        return kab_id;
    }

    public String getProv_name() {
        return prov_name;
    }

    public String getKab_name() {
        return kab_name;
    }
}
