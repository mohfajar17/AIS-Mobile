package com.asukacorp.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetTraining implements Parcelable {
    private String start_date;
    private String end_date;
    private String training_name;
    private String description;
    private String place;
    private String provider;
    private String duration_day;
    private String evaluation_date;

    protected EmployeeDetTraining (Parcel in) {
        start_date = in.readString();
        end_date = in.readString();
        training_name = in.readString();
        description = in.readString();
        place = in.readString();
        provider = in.readString();
        duration_day = in.readString();
        evaluation_date = in.readString();
    }

    public EmployeeDetTraining (JSONObject jsonObject){
        try {
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
            this.training_name = jsonObject.getString("training_name");
            this.description = jsonObject.getString("description");
            this.place = jsonObject.getString("place");
            this.provider = jsonObject.getString("provider");
            this.duration_day = jsonObject.getString("duration_day");
            this.evaluation_date = jsonObject.getString("evaluation_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetTraining> CREATOR = new Creator<EmployeeDetTraining>() {
        @Override
        public EmployeeDetTraining createFromParcel(Parcel in) {
            return new EmployeeDetTraining(in);
        }

        @Override
        public EmployeeDetTraining[] newArray(int size) {
            return new EmployeeDetTraining[size];
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
        dest.writeString(training_name);
        dest.writeString(description);
        dest.writeString(place);
        dest.writeString(provider);
        dest.writeString(duration_day);
        dest.writeString(evaluation_date);
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getTraining_name() {
        return training_name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public String getProvider() {
        return provider;
    }

    public String getDuration_day() {
        return duration_day;
    }

    public String getEvaluation_date() {
        return evaluation_date;
    }
}
