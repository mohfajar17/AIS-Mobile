package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Lead implements Parcelable {

    private int lead_id;
    private String lead_name;
    private String lead_phone;
    private String lead_email;
    private String person;
    private String position;
    private String personal_phone;
    private String status;

    public Lead(int lead_id, String lead_name, String lead_phone, String lead_email, String person, String position,
                String personal_phone, String status){
        this.lead_id = lead_id;
        this.lead_name = lead_name;
        this.lead_phone = lead_phone;
        this.lead_email = lead_email;
        this.person = person;
        this.position = position;
        this.personal_phone = personal_phone;
        this.status = status;
    }

    protected Lead(Parcel in) {
        lead_id = in.readInt();
        lead_name = in.readString();
        lead_phone = in.readString();
        lead_email = in.readString();
        person = in.readString();
        position = in.readString();
        personal_phone = in.readString();
        status = in.readString();
    }

    public Lead(JSONObject jsonObject){
        try {
            this.lead_id = jsonObject.getInt("lead_id");
            this.lead_name = jsonObject.getString("lead_name");
            this.lead_phone = jsonObject.getString("lead_phone");
            this.lead_email = jsonObject.getString("lead_email");
            this.person = jsonObject.getString("person");
            this.position = jsonObject.getString("position");
            this.personal_phone = jsonObject.getString("personal_phone");
            this.status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Lead> CREATOR = new Creator<Lead>() {
        @Override
        public Lead createFromParcel(Parcel in) {
            return new Lead(in);
        }

        @Override
        public Lead[] newArray(int size) {
            return new Lead[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lead_id);
        dest.writeString(lead_name);
        dest.writeString(lead_phone);
        dest.writeString(lead_email);
        dest.writeString(person);
        dest.writeString(position);
        dest.writeString(personal_phone);
        dest.writeString(status);
    }

    public int getLead_id() {
        return lead_id;
    }

    public String getLead_name() {
        return lead_name;
    }

    public String getLead_phone() {
        return lead_phone;
    }

    public String getLead_email() {
        return lead_email;
    }

    public String getPerson() {
        return person;
    }

    public String getPosition() {
        return position;
    }

    public String getPersonal_phone() {
        return personal_phone;
    }

    public String getStatus() {
        return status;
    }
}
