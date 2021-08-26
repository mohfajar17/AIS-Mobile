package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class DepartmentDetail implements Parcelable {
    private String employee_number;
    private String fullname;
    private String company_workbase_name;
    private String address;
    private String religion_name;
    private String is_active;

    protected DepartmentDetail (Parcel in) {
        employee_number = in.readString();
        fullname = in.readString();
        company_workbase_name = in.readString();
        address = in.readString();
        religion_name = in.readString();
        is_active = in.readString();
    }

    public DepartmentDetail (JSONObject jsonObject){
        try {
            this.employee_number = jsonObject.getString("employee_number");
            this.fullname = jsonObject.getString("fullname");
            this.company_workbase_name = jsonObject.getString("company_workbase_name");
            this.address = jsonObject.getString("address");
            this.religion_name = jsonObject.getString("religion_name");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<DepartmentDetail> CREATOR = new Creator<DepartmentDetail>() {
        @Override
        public DepartmentDetail createFromParcel(Parcel in) {
            return new DepartmentDetail(in);
        }

        @Override
        public DepartmentDetail[] newArray(int size) {
            return new DepartmentDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employee_number);
        dest.writeString(fullname);
        dest.writeString(company_workbase_name);
        dest.writeString(address);
        dest.writeString(religion_name);
        dest.writeString(is_active);
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCompany_workbase_name() {
        return company_workbase_name;
    }

    public String getAddress() {
        return address;
    }

    public String getReligion_name() {
        return religion_name;
    }

    public String getIs_active() {
        return is_active;
    }
}
