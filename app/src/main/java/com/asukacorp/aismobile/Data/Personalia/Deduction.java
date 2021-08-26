package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Deduction implements Parcelable {

    private int deduction_id;
    private String deduction_name;
    private String report_code;
    private String description;
    private String value;
    private String adjustment;
    private String is_active;

    public Deduction (int deduction_id, String deduction_name, String report_code, String description,
                      String value){
        this.deduction_id = deduction_id;
        this.deduction_name = deduction_name;
        this.report_code = report_code;
        this.description = description;
        this.value = value;
        this.adjustment = adjustment;
    }
    protected Deduction (Parcel in) {
        deduction_id = in.readInt();
        deduction_name = in.readString();
        report_code = in.readString();
        description = in.readString();
        value = in.readString();
        adjustment = in.readString();
        is_active = in.readString();
    }

    public Deduction (JSONObject jsonObject){
        try {
            this.deduction_id = jsonObject.getInt("deduction_id");
            this.deduction_name = jsonObject.getString("deduction_name");
            this.report_code = jsonObject.getString("report_code");
            this.description = jsonObject.getString("description");
            this.value = jsonObject.getString("value");
            this.adjustment = jsonObject.getString("adjustment");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Deduction> CREATOR = new Creator<Deduction>() {
        @Override
        public Deduction createFromParcel(Parcel in) {
            return new Deduction(in);
        }

        @Override
        public Deduction[] newArray(int size) {
            return new Deduction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deduction_id);
        dest.writeString(deduction_name);
        dest.writeString(report_code);
        dest.writeString(description);
        dest.writeString(value);
        dest.writeString(adjustment);
        dest.writeString(is_active);
    }

    public int getDeduction_id() {
        return deduction_id;
    }

    public String getDeduction_name() {
        return deduction_name;
    }

    public String getReport_code() {
        return report_code;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public String getAdjustment() {
        return adjustment;
    }

    public String getIs_active() {
        return is_active;
    }
}
