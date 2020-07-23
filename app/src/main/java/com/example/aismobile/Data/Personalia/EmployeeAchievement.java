package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeAchievement implements Parcelable {


    private int employee_achievement_id;
    private String fullname;
    private String event;
    private String achievement;
    private String achievement_date;
    private String notes;

    public EmployeeAchievement (int employee_achievement_id, String fullname, String event, String achievement,
                                String achievement_date, String notes){
        this.employee_achievement_id = employee_achievement_id;
        this.fullname = fullname;
        this.event = event;
        this.achievement = achievement;
        this.achievement_date = achievement_date;
        this.notes = notes;
    }
    protected EmployeeAchievement (Parcel in) {
        employee_achievement_id = in.readInt();
        fullname = in.readString();
        event = in.readString();
        achievement = in.readString();
        achievement_date = in.readString();
        notes = in.readString();
    }

    public EmployeeAchievement (JSONObject jsonObject){
        try {
            this.employee_achievement_id = jsonObject.getInt("employee_achievement_id");
            this.fullname = jsonObject.getString("fullname");
            this.event = jsonObject.getString("event");
            this.achievement = jsonObject.getString("achievement");
            this.achievement_date = jsonObject.getString("achievement_date");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeAchievement> CREATOR = new Creator<EmployeeAchievement>() {
        @Override
        public EmployeeAchievement createFromParcel(Parcel in) {
            return new EmployeeAchievement(in);
        }

        @Override
        public EmployeeAchievement[] newArray(int size) {
            return new EmployeeAchievement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_achievement_id);
        dest.writeString(fullname);
        dest.writeString(event);
        dest.writeString(achievement);
        dest.writeString(achievement_date);
        dest.writeString(notes);
    }

    public int getEmployee_achievement_id() {
        return employee_achievement_id;
    }

    public String getFullname() {
        return fullname;
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
