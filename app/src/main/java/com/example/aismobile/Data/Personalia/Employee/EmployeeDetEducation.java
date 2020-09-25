package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetEducation implements Parcelable {
    private String education_name;
    private String school_name;
    private String major;
    private String education_end;

    protected EmployeeDetEducation (Parcel in) {
        education_name = in.readString();
        school_name = in.readString();
        major = in.readString();
        education_end = in.readString();
    }

    public EmployeeDetEducation (JSONObject jsonObject){
        try {
            this.education_name = jsonObject.getString("education_name");
            this.school_name = jsonObject.getString("school_name");
            this.major = jsonObject.getString("major");
            this.education_end = jsonObject.getString("education_end");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetEducation> CREATOR = new Creator<EmployeeDetEducation>() {
        @Override
        public EmployeeDetEducation createFromParcel(Parcel in) {
            return new EmployeeDetEducation(in);
        }

        @Override
        public EmployeeDetEducation[] newArray(int size) {
            return new EmployeeDetEducation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(education_name);
        dest.writeString(school_name);
        dest.writeString(major);
        dest.writeString(education_end);
    }

    public String getEducation_name() {
        return education_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public String getMajor() {
        return major;
    }

    public String getEducation_end() {
        return education_end;
    }
}
