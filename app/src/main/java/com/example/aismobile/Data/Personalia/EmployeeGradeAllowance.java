package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeGradeAllowance implements Parcelable {

    private int employee_grade_allowance_id;
    private String employee_grade_allowance_name;
    private String employee_grade_name;
    private String allowance_id;
    private String is_active;

    public EmployeeGradeAllowance (int employee_grade_allowance_id, String employee_grade_allowance_name,
                                   String employee_grade_name, String allowance_id, String is_active){
        this.employee_grade_allowance_id = employee_grade_allowance_id;
        this.employee_grade_allowance_name = employee_grade_allowance_name;
        this.employee_grade_name = employee_grade_name;
        this.allowance_id = allowance_id;
        this.is_active = is_active;
    }
    protected EmployeeGradeAllowance (Parcel in) {
        employee_grade_allowance_id = in.readInt();
        employee_grade_allowance_name = in.readString();
        employee_grade_name = in.readString();
        allowance_id = in.readString();
        is_active = in.readString();
    }

    public EmployeeGradeAllowance (JSONObject jsonObject){
        try {
            this.employee_grade_allowance_id = jsonObject.getInt("employee_grade_allowance_id");
            this.employee_grade_allowance_name = jsonObject.getString("employee_grade_allowance_name");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.allowance_id = jsonObject.getString("allowance_id");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeGradeAllowance> CREATOR = new Creator<EmployeeGradeAllowance>() {
        @Override
        public EmployeeGradeAllowance createFromParcel(Parcel in) {
            return new EmployeeGradeAllowance(in);
        }

        @Override
        public EmployeeGradeAllowance[] newArray(int size) {
            return new EmployeeGradeAllowance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_grade_allowance_id);
        dest.writeString(employee_grade_allowance_name);
        dest.writeString(employee_grade_name);
        dest.writeString(allowance_id);
        dest.writeString(is_active);
    }
}
