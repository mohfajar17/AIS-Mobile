package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Kuesioner implements Parcelable {

    private int survey_id;
    private String survey_number;
    private String company_id;
    private String contact_name;
    private String survey_date;

    public Kuesioner(int survey_id, String survey_number, String company_id, String contact_name, String survey_date){
        this.survey_id = survey_id;
        this.survey_number = survey_number;
        this.company_id = company_id;
        this.contact_name = contact_name;
        this.survey_date = survey_date;
    }

    protected Kuesioner(Parcel in) {
        survey_id = in.readInt();
        survey_number = in.readString();
        company_id = in.readString();
        contact_name = in.readString();
        survey_date = in.readString();
    }

    public Kuesioner(JSONObject jsonObject){
        try {
            this.survey_id = jsonObject.getInt("survey_id");
            this.survey_number = jsonObject.getString("survey_number");
            this.company_id = jsonObject.getString("company_id");
            this.contact_name = jsonObject.getString("contact_name");
            this.survey_date = jsonObject.getString("survey_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Kuesioner> CREATOR = new Creator<Kuesioner>() {
        @Override
        public Kuesioner createFromParcel(Parcel in) {
            return new Kuesioner(in);
        }

        @Override
        public Kuesioner[] newArray(int size) {
            return new Kuesioner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(survey_id);
        dest.writeString(survey_number);
        dest.writeString(company_id);
        dest.writeString(contact_name);
        dest.writeString(survey_date);
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public String getSurvey_number() {
        return survey_number;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getSurvey_date() {
        return survey_date;
    }
}
