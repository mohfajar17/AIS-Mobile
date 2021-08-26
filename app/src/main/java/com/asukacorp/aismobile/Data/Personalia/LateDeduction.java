package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class LateDeduction implements Parcelable {

    private int late_deduction_id;
    private String amount;
    private String notes;
    private String report_name;

    public LateDeduction (int late_deduction_id, String amount, String notes, String report_name){
        this.late_deduction_id = late_deduction_id;
        this.amount = amount;
        this.notes = notes;
        this.report_name = report_name;
    }
    protected LateDeduction (Parcel in) {
        late_deduction_id = in.readInt();
        amount = in.readString();
        notes = in.readString();
        report_name = in.readString();
    }

    public LateDeduction (JSONObject jsonObject){
        try {
            this.late_deduction_id = jsonObject.getInt("late_deduction_id");
            this.amount = jsonObject.getString("amount");
            this.notes = jsonObject.getString("notes");
            this.report_name = jsonObject.getString("report_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<LateDeduction> CREATOR = new Creator<LateDeduction>() {
        @Override
        public LateDeduction createFromParcel(Parcel in) {
            return new LateDeduction(in);
        }

        @Override
        public LateDeduction[] newArray(int size) {
            return new LateDeduction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(late_deduction_id);
        dest.writeString(amount);
        dest.writeString(notes);
        dest.writeString(report_name);
    }

    public int getLate_deduction_id() {
        return late_deduction_id;
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
