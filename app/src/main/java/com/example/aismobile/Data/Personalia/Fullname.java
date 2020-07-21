package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Fullname implements Parcelable {

    private int id;
    private String fullname;

    public Fullname (int id, String fullname){
        this.id = id;
        this.fullname = fullname;
    }
    protected Fullname (Parcel in) {
        id = in.readInt();
        fullname = in.readString();
    }

    public Fullname (JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.fullname = jsonObject.getString("fullname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Fullname> CREATOR = new Creator<Fullname>() {
        @Override
        public Fullname createFromParcel(Parcel in) {
            return new Fullname(in);
        }

        @Override
        public Fullname[] newArray(int size) {
            return new Fullname[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fullname);
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }
}
