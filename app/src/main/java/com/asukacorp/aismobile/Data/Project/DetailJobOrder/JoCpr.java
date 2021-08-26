package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoCpr implements Parcelable {

    private int responsbility_advance_id;
    private String responsbility_advance_number;
    private String cash_advance_number;

    public JoCpr(int responsbility_advance_id, String responsbility_advance_number, String cash_advance_number){
        this.responsbility_advance_id = responsbility_advance_id;
        this.responsbility_advance_number = responsbility_advance_number;
        this.cash_advance_number = cash_advance_number;
    }

    protected JoCpr(Parcel in) {
        responsbility_advance_id = in.readInt();
        responsbility_advance_number = in.readString();
        cash_advance_number = in.readString();
    }

    public JoCpr(JSONObject jsonObject){
        try {
            this.responsbility_advance_id = jsonObject.getInt("responsbility_advance_id");
            this.responsbility_advance_number = jsonObject.getString("responsbility_advance_number");
            this.cash_advance_number = jsonObject.getString("cash_advance_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoCpr> CREATOR = new Creator<JoCpr>() {
        @Override
        public JoCpr createFromParcel(Parcel in) {
            return new JoCpr(in);
        }

        @Override
        public JoCpr[] newArray(int size) {
            return new JoCpr[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(responsbility_advance_id);
        dest.writeString(responsbility_advance_number);
        dest.writeString(cash_advance_number);
    }

    public int getResponsbility_advance_id() {
        return responsbility_advance_id;
    }

    public String getResponsbility_advance_number() {
        return responsbility_advance_number;
    }

    public String getCash_advance_number() {
        return cash_advance_number;
    }
}
