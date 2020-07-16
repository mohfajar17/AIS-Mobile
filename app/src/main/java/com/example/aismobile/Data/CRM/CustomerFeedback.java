package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerFeedback implements Parcelable {

    private int feedback_id;
    private String feedback_number;
    private String feedback_date;
    private String feedback_subject;
    private String company_name;
    private String contact_id;
    private String contact_personal;
    private String marketing_aspect_id;
    private String feedback_category_id;
    private String feedback_status;

    public CustomerFeedback(int feedback_id, String feedback_number, String feedback_date, String feedback_subject,
                            String company_name, String contact_id, String contact_personal, String marketing_aspect_id,
                            String feedback_category_id, String feedback_status){
        this.feedback_id = feedback_id;
        this.feedback_number = feedback_number;
        this.feedback_date = feedback_date;
        this.feedback_subject = feedback_subject;
        this.company_name = company_name;
        this.contact_id = contact_id;
        this.contact_personal = contact_personal;
        this.marketing_aspect_id = marketing_aspect_id;
        this.feedback_category_id = feedback_category_id;
        this.feedback_status = feedback_status;
    }

    protected CustomerFeedback(Parcel in) {
        feedback_id = in.readInt();
        feedback_number = in.readString();
        feedback_date = in.readString();
        feedback_subject = in.readString();
        company_name = in.readString();
        contact_id = in.readString();
        contact_personal = in.readString();
        marketing_aspect_id = in.readString();
        feedback_category_id = in.readString();
        feedback_status = in.readString();
    }

    public CustomerFeedback(JSONObject jsonObject){
        try {
            this.feedback_id = jsonObject.getInt("feedback_id");
            this.feedback_number = jsonObject.getString("feedback_number");
            this.feedback_date = jsonObject.getString("feedback_date");
            this.feedback_subject = jsonObject.getString("feedback_subject");
            this.company_name = jsonObject.getString("company_name");
            this.contact_id = jsonObject.getString("contact_id");
            this.contact_personal = jsonObject.getString("contact_personal");
            this.marketing_aspect_id = jsonObject.getString("marketing_aspect_id");
            this.feedback_category_id = jsonObject.getString("feedback_category_id");
            this.feedback_status = jsonObject.getString("feedback_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CustomerFeedback> CREATOR = new Creator<CustomerFeedback>() {
        @Override
        public CustomerFeedback createFromParcel(Parcel in) {
            return new CustomerFeedback(in);
        }

        @Override
        public CustomerFeedback[] newArray(int size) {
            return new CustomerFeedback[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(feedback_id);
        dest.writeString(feedback_number);
        dest.writeString(feedback_date);
        dest.writeString(feedback_subject);
        dest.writeString(company_name);
        dest.writeString(contact_id);
        dest.writeString(contact_personal);
        dest.writeString(marketing_aspect_id);
        dest.writeString(feedback_category_id);
        dest.writeString(feedback_status);
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public String getFeedback_number() {
        return feedback_number;
    }

    public String getFeedback_date() {
        return feedback_date;
    }

    public String getFeedback_subject() {
        return feedback_subject;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getContact_id() {
        return contact_id;
    }

    public String getContact_personal() {
        return contact_personal;
    }

    public String getMarketing_aspect_id() {
        return marketing_aspect_id;
    }

    public String getFeedback_category_id() {
        return feedback_category_id;
    }

    public String getFeedback_status() {
        return feedback_status;
    }
}
