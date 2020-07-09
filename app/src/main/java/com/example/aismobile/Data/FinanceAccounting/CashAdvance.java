package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CashAdvance implements Parcelable {

    private int advanced_id;
    private String advanced_number;
    private String advanced_date;
    private String advanced_for;
    private String received_by;
    private String amount;
    private String status;

    public CashAdvance(int advanced_id, String advanced_number, String advanced_date, String advanced_for,
                   String received_by, String amount, String status){
        this.advanced_id = advanced_id;
        this.advanced_number = advanced_number;
        this.advanced_date = advanced_date;
        this.advanced_for = advanced_for;
        this.received_by = received_by;
        this.amount = amount;
        this.status = status;
    }
    protected CashAdvance(Parcel in) {
        advanced_id = in.readInt();
        advanced_number = in.readString();
        advanced_date = in.readString();
        advanced_for = in.readString();
        received_by = in.readString();
        amount = in.readString();
        status = in.readString();
    }

    public CashAdvance(JSONObject jsonObject){
        try {
            this.advanced_id = jsonObject.getInt("advanced_id");
            this.advanced_number = jsonObject.getString("advanced_number");
            this.advanced_date = jsonObject.getString("advanced_date");
            this.advanced_for = jsonObject.getString("advanced_for");
            this.received_by = jsonObject.getString("received_by");
            this.amount = jsonObject.getString("amount");
            this.status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CashAdvance> CREATOR = new Creator<CashAdvance>() {
        @Override
        public CashAdvance createFromParcel(Parcel in) {
            return new CashAdvance(in);
        }

        @Override
        public CashAdvance[] newArray(int size) {
            return new CashAdvance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(advanced_id);
        dest.writeString(advanced_number);
        dest.writeString(advanced_date);
        dest.writeString(advanced_for);
        dest.writeString(received_by);
        dest.writeString(amount);
        dest.writeString(status);
    }

    public int getAdvanced_id() {
        return advanced_id;
    }

    public String getAdvanced_number() {
        return advanced_number;
    }

    public String getAdvanced_date() {
        return advanced_date;
    }

    public String getAdvanced_for() {
        return advanced_for;
    }

    public String getReceived_by() {
        return received_by;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
