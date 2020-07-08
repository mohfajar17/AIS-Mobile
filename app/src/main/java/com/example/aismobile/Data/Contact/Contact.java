package com.example.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact implements Parcelable {

    private int contact_id;
    private String contact_name;
    private String company_name;
    private String contact_jobtitle;
    private String contact_email;
    private String contact_phone1;

    public Contact(int contact_id, String contact_name, String company_name, String contact_jobtitle,
                   String contact_email, String contact_phone1){
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.company_name = company_name;
        this.contact_jobtitle = contact_jobtitle;
        this.contact_email = contact_email;
        this.contact_phone1 = contact_phone1;
    }

    protected Contact(Parcel in) {
        contact_id = in.readInt();
        contact_name = in.readString();
        company_name = in.readString();
        contact_jobtitle = in.readString();
        contact_email = in.readString();
        contact_phone1 = in.readString();
    }

    public Contact(JSONObject jsonObject){
        try {
            this.contact_id = jsonObject.getInt("contact_id");
            this.contact_name = jsonObject.getString("contact_name");
            this.company_name = jsonObject.getString("company_name");
            this.contact_jobtitle = jsonObject.getString("contact_jobtitle");
            this.contact_email = jsonObject.getString("contact_email");
            this.contact_phone1 = jsonObject.getString("contact_phone1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contact_id);
        dest.writeString(contact_name);
        dest.writeString(company_name);
        dest.writeString(contact_jobtitle);
        dest.writeString(contact_email);
        dest.writeString(contact_phone1);
    }

    public int getContact_id() {
        return contact_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getCompany_name() {
        return company_name;
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
