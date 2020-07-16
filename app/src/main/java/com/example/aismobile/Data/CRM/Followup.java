package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Followup implements Parcelable {

    private int lead_followup_id;
    private String lead_id;
    private String company_name;
    private String notes;
    private String followup_date;
    private String followup_by;
    private String created_by;

    public Followup(int lead_followup_id, String lead_id, String company_name, String notes, String followup_date,
                    String followup_by, String created_by){
        this.lead_followup_id = lead_followup_id;
        this.lead_id = lead_id;
        this.company_name = company_name;
        this.notes = notes;
        this.followup_date = followup_date;
        this.followup_by = followup_by;
        this.created_by = created_by;
    }

    protected Followup(Parcel in) {
        lead_followup_id = in.readInt();
        lead_id = in.readString();
        company_name = in.readString();
        notes = in.readString();
        followup_date = in.readString();
        followup_by = in.readString();
        created_by = in.readString();
    }

    public Followup(JSONObject jsonObject){
        try {
            this.lead_followup_id = jsonObject.getInt("lead_followup_id");
            this.lead_id = jsonObject.getString("lead_id");
            this.company_name = jsonObject.getString("company_name");
            this.notes = jsonObject.getString("notes");
            this.followup_date = jsonObject.getString("followup_date");
            this.followup_by = jsonObject.getString("followup_by");
            this.created_by = jsonObject.getString("created_by");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Followup> CREATOR = new Creator<Followup>() {
        @Override
        public Followup createFromParcel(Parcel in) {
            return new Followup(in);
        }

        @Override
        public Followup[] newArray(int size) {
            return new Followup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lead_followup_id);
        dest.writeString(lead_id);
        dest.writeString(company_name);
        dest.writeString(notes);
        dest.writeString(followup_date);
        dest.writeString(followup_by);
        dest.writeString(created_by);
    }

    public int getLead_followup_id() {
        return lead_followup_id;
    }

    public String getLead_id() {
        return lead_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getNotes() {
        return notes;
    }

    public String getFollowup_date() {
        return followup_date;
    }

    public String getFollowup_by() {
        return followup_by;
    }

    public String getCreated_by() {
        return created_by;
    }
}
