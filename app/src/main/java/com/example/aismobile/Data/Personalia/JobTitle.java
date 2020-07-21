package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JobTitle implements Parcelable {

    private int jobtitle_id;
    private String jobtitle_code;
    private String jobtitle_name;
    private String jobtitle_description;
    private String is_active;

    public JobTitle (int jobtitle_id, String jobtitle_code, String jobtitle_name, String jobtitle_description,
                       String is_active){
        this.jobtitle_id = jobtitle_id;
        this.jobtitle_code = jobtitle_code;
        this.jobtitle_name = jobtitle_name;
        this.jobtitle_description = jobtitle_description;
        this.is_active = is_active;
    }
    protected JobTitle (Parcel in) {
        jobtitle_id = in.readInt();
        jobtitle_code = in.readString();
        jobtitle_name = in.readString();
        jobtitle_description = in.readString();
        is_active = in.readString();
    }

    public JobTitle (JSONObject jsonObject){
        try {
            this.jobtitle_id = jsonObject.getInt("jobtitle_id");
            this.jobtitle_code = jsonObject.getString("jobtitle_code");
            this.jobtitle_name = jsonObject.getString("jobtitle_name");
            this.jobtitle_description = jsonObject.getString("jobtitle_description");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JobTitle> CREATOR = new Creator<JobTitle>() {
        @Override
        public JobTitle createFromParcel(Parcel in) {
            return new JobTitle(in);
        }

        @Override
        public JobTitle[] newArray(int size) {
            return new JobTitle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(jobtitle_id);
        dest.writeString(jobtitle_code);
        dest.writeString(jobtitle_name);
        dest.writeString(jobtitle_description);
        dest.writeString(is_active);
    }

    public int getJobtitle_id() {
        return jobtitle_id;
    }

    public String getJobtitle_code() {
        return jobtitle_code;
    }

    public String getJobtitle_name() {
        return jobtitle_name;
    }

    public String getJobtitle_description() {
        return jobtitle_description;
    }

    public String getIs_active() {
        return is_active;
    }
}
