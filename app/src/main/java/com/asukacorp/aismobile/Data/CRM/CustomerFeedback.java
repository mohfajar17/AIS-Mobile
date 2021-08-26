package com.asukacorp.aismobile.Data.CRM;

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
    private String department_name;
    private String media_name;
    private String closed_user_id;
    private String closed_time;
    private String priority;
    private String feedback_description;
    private String user_displayname;
    private String feedback_entry_time;
    private String analyze_description;
    private String answer_description;
    private String preventive_description;
    private String archive1;
    private String archive2;
    private String archive3;

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
        department_name = in.readString();
        media_name = in.readString();
        closed_user_id = in.readString();
        closed_time = in.readString();
        priority = in.readString();
        feedback_description = in.readString();
        user_displayname = in.readString();
        feedback_entry_time = in.readString();
        analyze_description = in.readString();
        answer_description = in.readString();
        preventive_description = in.readString();
        archive1 = in.readString();
        archive2 = in.readString();
        archive3 = in.readString();
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
            this.department_name = jsonObject.getString("department_name");
            this.media_name = jsonObject.getString("media_name");
            this.closed_user_id = jsonObject.getString("closed_user_id");
            this.closed_time = jsonObject.getString("closed_time");
            this.priority = jsonObject.getString("priority");
            this.feedback_description = jsonObject.getString("feedback_description");
            this.user_displayname = jsonObject.getString("user_displayname");
            this.feedback_entry_time = jsonObject.getString("feedback_entry_time");
            this.analyze_description = jsonObject.getString("analyze_description");
            this.answer_description = jsonObject.getString("answer_description");
            this.preventive_description = jsonObject.getString("preventive_description");
            this.archive1 = jsonObject.getString("archive1");
            this.archive2 = jsonObject.getString("archive2");
            this.archive3 = jsonObject.getString("archive3");
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
        dest.writeString(department_name);
        dest.writeString(media_name);
        dest.writeString(closed_user_id);
        dest.writeString(closed_time);
        dest.writeString(priority);
        dest.writeString(feedback_description);
        dest.writeString(user_displayname);
        dest.writeString(feedback_entry_time);
        dest.writeString(analyze_description);
        dest.writeString(answer_description);
        dest.writeString(preventive_description);
        dest.writeString(archive1);
        dest.writeString(archive2);
        dest.writeString(archive3);
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

    public String getDepartment_name() {
        return department_name;
    }

    public String getMedia_name() {
        return media_name;
    }

    public String getClosed_user_id() {
        return closed_user_id;
    }

    public String getClosed_time() {
        return closed_time;
    }

    public String getPriority() {
        return priority;
    }

    public String getFeedback_description() {
        return feedback_description;
    }

    public String getUser_displayname() {
        return user_displayname;
    }

    public String getFeedback_entry_time() {
        return feedback_entry_time;
    }

    public String getAnalyze_description() {
        return analyze_description;
    }

    public String getAnswer_description() {
        return answer_description;
    }

    public String getPreventive_description() {
        return preventive_description;
    }

    public String getArchive1() {
        return archive1;
    }

    public String getArchive2() {
        return archive2;
    }

    public String getArchive3() {
        return archive3;
    }
}
