package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetLeave implements Parcelable {

    private String date_leave;
    private String proposed_date;
    private String date_extended;
    private String status;
    private String leave_category_name;
    private String is_approved;

    protected EmployeeDetLeave (Parcel in) {
        date_leave = in.readString();
        proposed_date = in.readString();
        date_extended = in.readString();
        status = in.readString();
        leave_category_name = in.readString();
        is_approved = in.readString();
    }

    public EmployeeDetLeave (JSONObject jsonObject){
        try {
            this.date_leave = jsonObject.getString("date_leave");
            this.proposed_date = jsonObject.getString("proposed_date");
            this.date_extended = jsonObject.getString("date_extended");
            this.status = jsonObject.getString("status");
            this.leave_category_name = jsonObject.getString("leave_category_name");
            this.is_approved = jsonObject.getString("is_approved");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetLeave> CREATOR = new Creator<EmployeeDetLeave>() {
        @Override
        public EmployeeDetLeave createFromParcel(Parcel in) {
            return new EmployeeDetLeave(in);
        }

        @Override
        public EmployeeDetLeave[] newArray(int size) {
            return new EmployeeDetLeave[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_leave);
        dest.writeString(proposed_date);
        dest.writeString(date_extended);
        dest.writeString(status);
        dest.writeString(leave_category_name);
        dest.writeString(is_approved);
    }

    public String getDate_leave() {
        return date_leave;
    }

    public String getProposed_date() {
        return proposed_date;
    }

    public String getDate_extended() {
        return date_extended;
    }

    public String getStatus() {
        return status;
    }

    public String getLeave_category_name() {
        return leave_category_name;
    }

    public String getIs_approved() {
        return is_approved;
    }
}
