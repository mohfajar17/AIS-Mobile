package com.asukacorp.aismobile.Data.Marketing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoPicture implements Parcelable {

    private int job_order_picture_id;
    private String job_order_number;
    private String job_order_description;
    private String description;
    private String file_name;

    protected JoPicture(Parcel in) {
        job_order_picture_id = in.readInt();
        job_order_number = in.readString();
        job_order_description = in.readString();
        description = in.readString();
        file_name = in.readString();
    }

    public JoPicture(JSONObject jsonObject){
        try {
            this.job_order_picture_id = jsonObject.getInt("job_order_picture_id");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.description = jsonObject.getString("description");
            this.file_name = jsonObject.getString("file_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoPicture> CREATOR = new Creator<JoPicture>() {
        @Override
        public JoPicture createFromParcel(Parcel in) {
            return new JoPicture(in);
        }

        @Override
        public JoPicture[] newArray(int size) {
            return new JoPicture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(job_order_picture_id);
        parcel.writeString(job_order_number);
        parcel.writeString(job_order_description);
        parcel.writeString(description);
        parcel.writeString(file_name);
    }

    public int getJob_order_picture_id() {
        return job_order_picture_id;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getDescription() {
        return description;
    }

    public String getFile_name() {
        return file_name;
    }
}
