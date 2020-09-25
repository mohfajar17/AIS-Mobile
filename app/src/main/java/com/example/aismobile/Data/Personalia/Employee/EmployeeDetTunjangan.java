package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetTunjangan implements Parcelable {
    private String employee_grade_allowance_name;
    private String value;
    private String count_as_religious_holiday_allowance;
    private String is_active;

    protected EmployeeDetTunjangan (Parcel in) {
        employee_grade_allowance_name = in.readString();
        value = in.readString();
        count_as_religious_holiday_allowance = in.readString();
        is_active = in.readString();
    }

    public EmployeeDetTunjangan (JSONObject jsonObject){
        try {
            this.employee_grade_allowance_name = jsonObject.getString("employee_grade_allowance_name");
            this.value = jsonObject.getString("value");
            this.count_as_religious_holiday_allowance = jsonObject.getString("count_as_religious_holiday_allowance");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetTunjangan> CREATOR = new Creator<EmployeeDetTunjangan>() {
        @Override
        public EmployeeDetTunjangan createFromParcel(Parcel in) {
            return new EmployeeDetTunjangan(in);
        }

        @Override
        public EmployeeDetTunjangan[] newArray(int size) {
            return new EmployeeDetTunjangan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employee_grade_allowance_name);
        dest.writeString(value);
        dest.writeString(count_as_religious_holiday_allowance);
        dest.writeString(is_active);
    }

    public String getEmployee_grade_allowance_name() {
        return employee_grade_allowance_name;
    }

    public String getValue() {
        return value;
    }

    public String getCount_as_religious_holiday_allowance() {
        return count_as_religious_holiday_allowance;
    }

    public String getIs_active() {
        return is_active;
    }
}
