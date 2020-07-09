package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class DaftarAkun implements Parcelable {

    private int account_id;
    private String chart_of_account_type_name;
    private String chart_of_account_group_code;
    private String account;
    private String account_code;
    private String is_group;
    private String is_active;

    public DaftarAkun(int account_id, String chart_of_account_type_name, String chart_of_account_group_code,
                      String account, String account_code, String is_group, String is_active){
        this.account_id = account_id;
        this.chart_of_account_type_name = chart_of_account_type_name;
        this.chart_of_account_group_code = chart_of_account_group_code;
        this.account = account;
        this.account_code = account_code;
        this.is_group = is_group;
        this.is_active = is_active;
    }

    protected DaftarAkun(Parcel in) {
        account_id = in.readInt();
        chart_of_account_type_name = in.readString();
        chart_of_account_group_code = in.readString();
        account = in.readString();
        account_code = in.readString();
        is_group = in.readString();
        is_active = in.readString();
    }

    public DaftarAkun(JSONObject jsonObject){
        try {
            this.account_id = jsonObject.getInt("account_id");
            this.chart_of_account_type_name = jsonObject.getString("chart_of_account_type_name");
            this.chart_of_account_group_code = jsonObject.getString("chart_of_account_group_code");
            this.account = jsonObject.getString("account");
            this.account_code = jsonObject.getString("account_code");
            this.is_group = jsonObject.getString("is_group");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<DaftarAkun> CREATOR = new Creator<DaftarAkun>() {
        @Override
        public DaftarAkun createFromParcel(Parcel in) {
            return new DaftarAkun(in);
        }

        @Override
        public DaftarAkun[] newArray(int size) {
            return new DaftarAkun[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(account_id);
        dest.writeString(chart_of_account_type_name);
        dest.writeString(chart_of_account_group_code);
        dest.writeString(account);
        dest.writeString(account_code);
        dest.writeString(is_group);
        dest.writeString(is_active);
    }
}
