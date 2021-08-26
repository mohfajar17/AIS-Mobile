package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RiwayatDetail implements Parcelable {
    private String history_date;
    private String employee_number;
    private String fullname;
    private String employee_grade_name;
    private String marital_status_name;
    private String company_workbase_name;
    private String notes;

    protected RiwayatDetail (Parcel in) {
        history_date = in.readString();
        employee_number = in.readString();
        fullname = in.readString();
        employee_grade_name = in.readString();
        marital_status_name = in.readString();
        company_workbase_name = in.readString();
        notes = in.readString();
    }

    public RiwayatDetail (JSONObject jsonObject){
        try {
            this.history_date = jsonObject.getString("history_date");
            this.employee_number = jsonObject.getString("employee_number");
            this.fullname = jsonObject.getString("fullname");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.marital_status_name = jsonObject.getString("marital_status_name");
            this.company_workbase_name = jsonObject.getString("company_workbase_name");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<RiwayatDetail> CREATOR = new Creator<RiwayatDetail>() {
        @Override
        public RiwayatDetail createFromParcel(Parcel in) {
            return new RiwayatDetail(in);
        }

        @Override
        public RiwayatDetail[] newArray(int size) {
            return new RiwayatDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(history_date);
        dest.writeString(employee_number);
        dest.writeString(fullname);
        dest.writeString(employee_grade_name);
        dest.writeString(marital_status_name);
        dest.writeString(company_workbase_name);
        dest.writeString(notes);
    }

    public String getHistory_date() {
        return history_date;
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getMarital_status_name() {
        return marital_status_name;
    }

    public String getCompany_workbase_name() {
        return company_workbase_name;
    }

    public String getNotes() {
        return notes;
    }
}
