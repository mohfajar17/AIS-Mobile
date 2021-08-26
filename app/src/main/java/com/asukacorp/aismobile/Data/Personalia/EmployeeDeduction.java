package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDeduction implements Parcelable {

    private int employee_deduction_id;
    private String fullname;
    private String deduction_name;
    private String date_begin;
    private String date_end;
    private String tax;
    private String is_active;

    public EmployeeDeduction (int employee_deduction_id, String fullname, String deduction_name, String date_begin,
                      String date_end, String tax, String is_active){
        this.employee_deduction_id = employee_deduction_id;
        this.fullname = fullname;
        this.deduction_name = deduction_name;
        this.date_begin = date_begin;
        this.date_end = date_end;
        this.tax = tax;
        this.is_active = is_active;
    }
    protected EmployeeDeduction (Parcel in) {
        employee_deduction_id = in.readInt();
        fullname = in.readString();
        deduction_name = in.readString();
        date_begin = in.readString();
        date_end = in.readString();
        tax = in.readString();
        is_active = in.readString();
    }

    public EmployeeDeduction (JSONObject jsonObject){
        try {
            this.employee_deduction_id = jsonObject.getInt("employee_deduction_id");
            this.fullname = jsonObject.getString("fullname");
            this.deduction_name = jsonObject.getString("deduction_name");
            this.date_begin = jsonObject.getString("date_begin");
            this.date_end = jsonObject.getString("date_end");
            this.tax = jsonObject.getString("tax");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDeduction> CREATOR = new Creator<EmployeeDeduction>() {
        @Override
        public EmployeeDeduction createFromParcel(Parcel in) {
            return new EmployeeDeduction(in);
        }

        @Override
        public EmployeeDeduction[] newArray(int size) {
            return new EmployeeDeduction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_deduction_id);
        dest.writeString(fullname);
        dest.writeString(deduction_name);
        dest.writeString(date_begin);
        dest.writeString(date_end);
        dest.writeString(tax);
        dest.writeString(is_active);
    }

    public int getEmployee_deduction_id() {
        return employee_deduction_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getDeduction_name() {
        return deduction_name;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public String getTax() {
        return tax;
    }

    public String getIs_active() {
        return is_active;
    }
}
