package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SpklDetail implements Parcelable {

    private int otwo_detail_id;
    private String fullname;
    private String job_grade_name;
    private String overtime_date;
    private String description;
    private String start_time;
    private String finish_time;
    private String approval_status1;
    private String approval_status2;

    public SpklDetail(int otwo_detail_id, String fullname, String job_grade_name, String overtime_date,
                String description, String start_time, String finish_time, String approval_status1,
                String approval_status2){
        this.otwo_detail_id = otwo_detail_id;
        this.fullname = fullname;
        this.job_grade_name = job_grade_name;
        this.overtime_date = overtime_date;
        this.description = description;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.approval_status1 = approval_status1;
        this.approval_status2 = approval_status2;
    }

    protected SpklDetail(Parcel in) {
        otwo_detail_id = in.readInt();
        fullname = in.readString();
        job_grade_name = in.readString();
        overtime_date = in.readString();
        description = in.readString();
        start_time = in.readString();
        finish_time = in.readString();
        approval_status1 = in.readString();
        approval_status2 = in.readString();
    }

    public SpklDetail(JSONObject jsonObject){
        try {
            this.otwo_detail_id = jsonObject.getInt("otwo_detail_id");
            this.fullname = jsonObject.getString("fullname");
            this.job_grade_name = jsonObject.getString("job_grade_name");
            this.overtime_date = jsonObject.getString("overtime_date");
            this.description = jsonObject.getString("description");
            this.start_time = jsonObject.getString("start_time");
            this.finish_time = jsonObject.getString("finish_time");
            this.approval_status1 = jsonObject.getString("approval_status1");
            this.approval_status2 = jsonObject.getString("approval_status2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SpklDetail> CREATOR = new Creator<SpklDetail>() {
        @Override
        public SpklDetail createFromParcel(Parcel in) {
            return new SpklDetail(in);
        }

        @Override
        public SpklDetail[] newArray(int size) {
            return new SpklDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(otwo_detail_id);
        dest.writeString(fullname);
        dest.writeString(job_grade_name);
        dest.writeString(overtime_date);
        dest.writeString(description);
        dest.writeString(start_time);
        dest.writeString(finish_time);
        dest.writeString(approval_status1);
        dest.writeString(approval_status2);
    }

    public int getOtwo_detail_id() {
        return otwo_detail_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getJob_grade_name() {
        return job_grade_name;
    }

    public String getOvertime_date() {
        return overtime_date;
    }

    public String getDescription() {
        return description;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getApproval_status1() {
        return approval_status1;
    }

    public String getApproval_status2() {
        return approval_status2;
    }
}
