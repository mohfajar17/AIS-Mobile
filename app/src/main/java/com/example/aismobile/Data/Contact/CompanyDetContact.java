package com.example.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyDetContact implements Parcelable {
    private String contact_name;
    private String contact_catid;
    private String contact_jobtitle;
    private String contact_email;
    private String contact_phone1;

    protected CompanyDetContact(Parcel in) {
        contact_name = in.readString();
        contact_catid = in.readString();
        contact_jobtitle = in.readString();
        contact_email = in.readString();
        contact_phone1 = in.readString();
    }

    public CompanyDetContact(JSONObject jsonObject){
        try {
            this.contact_name = jsonObject.getString("contact_name");
            this.contact_catid = jsonObject.getString("contact_catid");
            this.contact_jobtitle = jsonObject.getString("contact_jobtitle");
            this.contact_email = jsonObject.getString("contact_email");
            this.contact_phone1 = jsonObject.getString("contact_phone1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CompanyDetContact> CREATOR = new Creator<CompanyDetContact>() {
        @Override
        public CompanyDetContact createFromParcel(Parcel in) {
            return new CompanyDetContact(in);
        }

        @Override
        public CompanyDetContact[] newArray(int size) {
            return new CompanyDetContact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact_name);
        dest.writeString(contact_catid);
        dest.writeString(contact_jobtitle);
        dest.writeString(contact_email);
        dest.writeString(contact_phone1);
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getContact_catid() {
        return contact_catid;
    }

    public String getContact_jobtitle() {
        return contact_jobtitle;
    }

    public String getContact_email() {
        return contact_email;
    }

    public String getContact_phone1() {
        return contact_phone1;
    }
}
