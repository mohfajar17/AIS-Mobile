package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeGradeDetail implements Parcelable {
    private String department_name;
    private String fullname;
    private String employment_date;
    private String termination_date;
    private String address;
    private String religion_name;
    private String is_active;

    protected EmployeeGradeDetail (Parcel in) {
        department_name = in.readString();
        fullname = in.readString();
        employment_date = in.readString();
        termination_date = in.readString();
        address = in.readString();
        religion_name = in.readString();
        is_active = in.readString();
    }

    public EmployeeGradeDetail (JSONObject jsonObject){
        try {
            this.department_name = jsonObject.getString("department_name");
            this.fullname = jsonObject.getString("fullname");
            this.employment_date = jsonObject.getString("employment_date");
            this.termination_date = jsonObject.getString("termination_date");
            this.address = jsonObject.getString("address");
            this.religion_name = jsonObject.getString("religion_name");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeGradeDetail> CREATOR = new Creator<EmployeeGradeDetail>() {
        @Override
        public EmployeeGradeDetail createFromParcel(Parcel in) {
            return new EmployeeGradeDetail(in);
        }

        @Override
        public EmployeeGradeDetail[] newArray(int size) {
            return new EmployeeGradeDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(department_name);
        dest.writeString(fullname);
        dest.writeString(employment_date);
        dest.writeString(termination_date);
        dest.writeString(address);
        dest.writeString(religion_name);
        dest.writeString(is_active);
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmployment_date() {
        return employment_date;
    }

    public String getTermination_date() {
        return termination_date;
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
