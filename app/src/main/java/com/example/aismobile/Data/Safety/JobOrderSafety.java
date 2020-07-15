package com.example.aismobile.Data.Safety;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JobOrderSafety implements Parcelable {

    private int job_order_safety_id;
    private String job_order_id;
    private String description;
    private String category_id;
    private String job_safety_file_name;

    public JobOrderSafety (int job_order_safety_id, String job_order_id, String description,
                        String category_id, String job_safety_file_name){
        this.job_order_safety_id = job_order_safety_id;
        this.job_order_id = job_order_id;
        this.description = description;
        this.category_id = category_id;
        this.job_safety_file_name = job_safety_file_name;
    }

    protected JobOrderSafety(Parcel in) {
        job_order_safety_id = in.readInt();
        job_order_id = in.readString();
        description = in.readString();
        category_id = in.readString();
        job_safety_file_name = in.readString();
    }

    public JobOrderSafety(JSONObject jsonObject){
        try {
            this.job_order_safety_id = jsonObject.getInt("job_order_safety_id");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.description = jsonObject.getString("description");
            this.category_id = jsonObject.getString("category_id");
            this.job_safety_file_name = jsonObject.getString("job_safety_file_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JobOrderSafety> CREATOR = new Creator<JobOrderSafety>() {
        @Override
        public JobOrderSafety createFromParcel(Parcel in) {
            return new JobOrderSafety(in);
        }

        @Override
        public JobOrderSafety[] newArray(int size) {
            return new JobOrderSafety[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(job_order_safety_id);
        dest.writeString(job_order_id);
        dest.writeString(description);
        dest.writeString(category_id);
        dest.writeString(job_safety_file_name);
    }

    public int getJob_order_safety_id() {
        return job_order_safety_id;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getJob_safety_file_name() {
        return job_safety_file_name;
    }
}
