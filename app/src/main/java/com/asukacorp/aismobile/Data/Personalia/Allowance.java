package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Allowance implements Parcelable {

    private int allowance_id;
    private String allowance_name;
    private String report_code;
    private String allowance_type_name;
    private String allowance_group_name;
    private String value;
    private String allowance_unit;
    private String description;
    private String currency_code;
    private String adjustment;
    private String is_thr;
    private String is_active;

    public Allowance (int allowance_id, String allowance_name, String report_code, String allowance_type_name,
                      String allowance_group_name, String value, String allowance_unit){
        this.allowance_id = allowance_id;
        this.allowance_name = allowance_name;
        this.report_code = report_code;
        this.allowance_type_name = allowance_type_name;
        this.allowance_group_name = allowance_group_name;
        this.value = value;
        this.allowance_unit = allowance_unit;
    }
    protected Allowance (Parcel in) {
        allowance_id = in.readInt();
        allowance_name = in.readString();
        report_code = in.readString();
        allowance_type_name = in.readString();
        allowance_group_name = in.readString();
        value = in.readString();
        allowance_unit = in.readString();
        description = in.readString();
        currency_code = in.readString();
        adjustment = in.readString();
        is_thr = in.readString();
        is_active = in.readString();
    }

    public Allowance (JSONObject jsonObject){
        try {
            this.allowance_id = jsonObject.getInt("allowance_id");
            this.allowance_name = jsonObject.getString("allowance_name");
            this.report_code = jsonObject.getString("report_code");
            this.allowance_type_name = jsonObject.getString("allowance_type_name");
            this.allowance_group_name = jsonObject.getString("allowance_group_name");
            this.value = jsonObject.getString("value");
            this.allowance_unit = jsonObject.getString("allowance_unit");
            this.description = jsonObject.getString("description");
            this.currency_code = jsonObject.getString("currency_code");
            this.adjustment = jsonObject.getString("adjustment");
            this.is_thr = jsonObject.getString("is_thr");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Allowance> CREATOR = new Creator<Allowance>() {
        @Override
        public Allowance createFromParcel(Parcel in) {
            return new Allowance(in);
        }

        @Override
        public Allowance[] newArray(int size) {
            return new Allowance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(allowance_id);
        dest.writeString(allowance_name);
        dest.writeString(report_code);
        dest.writeString(allowance_type_name);
        dest.writeString(allowance_group_name);
        dest.writeString(value);
        dest.writeString(allowance_unit);
        dest.writeString(description);
        dest.writeString(currency_code);
        dest.writeString(adjustment);
        dest.writeString(is_thr);
        dest.writeString(is_active);
    }

    public int getAllowance_id() {
        return allowance_id;
    }

    public String getAllowance_name() {
        return allowance_name;
    }

    public String getReport_code() {
        return report_code;
    }

    public String getAllowance_type_name() {
        return allowance_type_name;
    }

    public String getAllowance_group_name() {
        return allowance_group_name;
    }

    public String getValue() {
        return value;
    }

    public String getAllowance_unit() {
        return allowance_unit;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public String getAdjustment() {
        return adjustment;
    }

    public String getIs_thr() {
        return is_thr;
    }

    public String getIs_active() {
        return is_active;
    }
}
