package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RemainLeave implements Parcelable {

    private int remain_leave_id;
    private String amount;
    private String notes;
    private String report_name;

    public RemainLeave (int remain_leave_id, String amount, String notes, String report_name){
        this.remain_leave_id = remain_leave_id;
        this.amount = amount;
        this.notes = notes;
        this.report_name = report_name;
    }
    protected RemainLeave (Parcel in) {
        remain_leave_id = in.readInt();
        amount = in.readString();
        notes = in.readString();
        report_name = in.readString();
    }

    public RemainLeave (JSONObject jsonObject){
        try {
            this.remain_leave_id = jsonObject.getInt("remain_leave_id");
            this.amount = jsonObject.getString("amount");
            this.notes = jsonObject.getString("notes");
            this.report_name = jsonObject.getString("report_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<RemainLeave> CREATOR = new Creator<RemainLeave>() {
        @Override
        public RemainLeave createFromParcel(Parcel in) {
            return new RemainLeave(in);
        }

        @Override
        public RemainLeave[] newArray(int size) {
            return new RemainLeave[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(remain_leave_id);
        dest.writeString(amount);
        dest.writeString(notes);
        dest.writeString(report_name);
    }

    public int getRemain_leave_id() {
        return remain_leave_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }

    public String getReport_name() {
        return report_name;
    }
}
