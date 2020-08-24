package com.example.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoPb implements Parcelable {

    private int cash_advance_id;
    private String cash_advance_number;
    private String rest_from;
    private String rest_value;

    public JoPb(int cash_advance_id, String cash_advance_number, String rest_from, String rest_value){
        this.cash_advance_id = cash_advance_id;
        this.cash_advance_number = cash_advance_number;
        this.rest_from = rest_from;
        this.rest_value = rest_value;
    }

    protected JoPb(Parcel in) {
        cash_advance_id = in.readInt();
        cash_advance_number = in.readString();
        rest_from = in.readString();
        rest_value = in.readString();
    }

    public JoPb(JSONObject jsonObject){
        try {
            this.cash_advance_id = jsonObject.getInt("cash_advance_id");
            this.cash_advance_number = jsonObject.getString("cash_advance_number");
            this.rest_from = jsonObject.getString("rest_from");
            this.rest_value = jsonObject.getString("rest_value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoPb> CREATOR = new Creator<JoPb>() {
        @Override
        public JoPb createFromParcel(Parcel in) {
            return new JoPb(in);
        }

        @Override
        public JoPb[] newArray(int size) {
            return new JoPb[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cash_advance_id);
        dest.writeString(cash_advance_number);
        dest.writeString(rest_from);
        dest.writeString(rest_value);
    }

    public int getCash_advance_id() {
        return cash_advance_id;
    }

    public String getCash_advance_number() {
        return cash_advance_number;
    }

    public String getRest_from() {
        return rest_from;
    }

    public String getRest_value() {
        return rest_value;
    }
}
