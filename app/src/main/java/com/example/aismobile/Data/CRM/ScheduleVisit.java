package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleVisit implements Parcelable {

    private int schedule_visits_id;
    private String visits_number;
    private String visits_date;
    private String lead_id;
    private String company_id;
    private String done;

    public ScheduleVisit(int schedule_visits_id, String visits_number, String visits_date, String lead_id,
                         String company_id, String done){
        this.schedule_visits_id = schedule_visits_id;
        this.visits_number = visits_number;
        this.visits_date = visits_date;
        this.lead_id = lead_id;
        this.company_id = company_id;
        this.done = done;
    }

    protected ScheduleVisit(Parcel in) {
        schedule_visits_id = in.readInt();
        visits_number = in.readString();
        visits_date = in.readString();
        lead_id = in.readString();
        company_id = in.readString();
        done = in.readString();
    }

    public ScheduleVisit(JSONObject jsonObject){
        try {
            this.schedule_visits_id = jsonObject.getInt("schedule_visits_id");
            this.visits_number = jsonObject.getString("visits_number");
            this.visits_date = jsonObject.getString("visits_date");
            this.lead_id = jsonObject.getString("lead_id");
            this.company_id = jsonObject.getString("company_id");
            this.done = jsonObject.getString("done");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ScheduleVisit> CREATOR = new Creator<ScheduleVisit>() {
        @Override
        public ScheduleVisit createFromParcel(Parcel in) {
            return new ScheduleVisit(in);
        }

        @Override
        public ScheduleVisit[] newArray(int size) {
            return new ScheduleVisit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(schedule_visits_id);
        dest.writeString(visits_number);
        dest.writeString(visits_date);
        dest.writeString(lead_id);
        dest.writeString(company_id);
        dest.writeString(done);
    }

    public int getSchedule_visits_id() {
        return schedule_visits_id;
    }

    public String getVisits_number() {
        return visits_number;
    }

    public String getVisits_date() {
        return visits_date;
    }

    public String getLead_id() {
        return lead_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getDone() {
        return done;
    }
}
