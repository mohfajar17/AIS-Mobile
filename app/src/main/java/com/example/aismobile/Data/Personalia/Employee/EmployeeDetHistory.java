package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetHistory implements Parcelable {
    private String history_date;
    private String status_history;
    private String employee_grade_name;
    private String notes;

    protected EmployeeDetHistory (Parcel in) {
        history_date = in.readString();
        status_history = in.readString();
        employee_grade_name = in.readString();
        notes = in.readString();
    }

    public EmployeeDetHistory (JSONObject jsonObject){
        try {
            this.history_date = jsonObject.getString("history_date");
            this.status_history = jsonObject.getString("status_history");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetHistory> CREATOR = new Creator<EmployeeDetHistory>() {
        @Override
        public EmployeeDetHistory createFromParcel(Parcel in) {
            return new EmployeeDetHistory(in);
        }

        @Override
        public EmployeeDetHistory[] newArray(int size) {
            return new EmployeeDetHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(history_date);
        dest.writeString(status_history);
        dest.writeString(employee_grade_name);
        dest.writeString(notes);
    }

    public String getHistory_date() {
        return history_date;
    }

    public String getStatus_history() {
        return status_history;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getNotes() {
        return notes;
    }
}
