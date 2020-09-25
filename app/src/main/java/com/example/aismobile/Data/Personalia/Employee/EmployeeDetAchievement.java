package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetAchievement implements Parcelable {
    private String event;
    private String achievement;
    private String achievement_date;
    private String notes;

    protected EmployeeDetAchievement (Parcel in) {
        event = in.readString();
        achievement = in.readString();
        achievement_date = in.readString();
        notes = in.readString();
    }

    public EmployeeDetAchievement (JSONObject jsonObject){
        try {
            this.event = jsonObject.getString("event");
            this.achievement = jsonObject.getString("achievement");
            this.achievement_date = jsonObject.getString("achievement_date");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetAchievement> CREATOR = new Creator<EmployeeDetAchievement>() {
        @Override
        public EmployeeDetAchievement createFromParcel(Parcel in) {
            return new EmployeeDetAchievement(in);
        }

        @Override
        public EmployeeDetAchievement[] newArray(int size) {
            return new EmployeeDetAchievement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event);
        dest.writeString(achievement);
        dest.writeString(achievement_date);
        dest.writeString(notes);
    }

    public String getEvent() {
        return event;
    }

    public String getAchievement() {
        return achievement;
    }

    public String getAchievement_date() {
        return achievement_date;
    }

    public String getNotes() {
        return notes;
    }
}
