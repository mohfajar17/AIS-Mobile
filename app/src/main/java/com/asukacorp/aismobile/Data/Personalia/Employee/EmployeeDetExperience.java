package com.asukacorp.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetExperience implements Parcelable {
    private String start_date;
    private String end_date;
    private String experience_description;
    private String experience_position;

    protected EmployeeDetExperience (Parcel in) {
        start_date = in.readString();
        end_date = in.readString();
        experience_description = in.readString();
        experience_position = in.readString();
    }

    public EmployeeDetExperience (JSONObject jsonObject){
        try {
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
            this.experience_description = jsonObject.getString("experience_description");
            this.experience_position = jsonObject.getString("experience_position");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetExperience> CREATOR = new Creator<EmployeeDetExperience>() {
        @Override
        public EmployeeDetExperience createFromParcel(Parcel in) {
            return new EmployeeDetExperience(in);
        }

        @Override
        public EmployeeDetExperience[] newArray(int size) {
            return new EmployeeDetExperience[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(experience_description);
        dest.writeString(experience_position);
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getExperience_description() {
        return experience_description;
    }

    public String getExperience_position() {
        return experience_position;
    }
}
