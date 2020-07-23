package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeNotice implements Parcelable {

    private int employee_notice_id;
    private String fullname;
    private String notice_date;
    private String subject;
    private String expired_date;
    private String employee_grade_name;
    private String job_grade_name;

    public EmployeeNotice (int employee_notice_id, String fullname, String notice_date, String subject, String expired_date,
                           String employee_grade_name, String job_grade_name){
        this.employee_notice_id = employee_notice_id;
        this.fullname = fullname;
        this.notice_date = notice_date;
        this.subject = subject;
        this.expired_date = expired_date;
        this.employee_grade_name = employee_grade_name;
        this.job_grade_name = job_grade_name;
    }
    protected EmployeeNotice (Parcel in) {
        employee_notice_id = in.readInt();
        fullname = in.readString();
        notice_date = in.readString();
        subject = in.readString();
        expired_date = in.readString();
        employee_grade_name = in.readString();
        job_grade_name = in.readString();
    }

    public EmployeeNotice (JSONObject jsonObject){
        try {
            this.employee_notice_id = jsonObject.getInt("employee_notice_id");
            this.fullname = jsonObject.getString("fullname");
            this.notice_date = jsonObject.getString("notice_date");
            this.subject = jsonObject.getString("subject");
            this.expired_date = jsonObject.getString("expired_date");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.job_grade_name = jsonObject.getString("job_grade_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeNotice> CREATOR = new Creator<EmployeeNotice>() {
        @Override
        public EmployeeNotice createFromParcel(Parcel in) {
            return new EmployeeNotice(in);
        }

        @Override
        public EmployeeNotice[] newArray(int size) {
            return new EmployeeNotice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_notice_id);
        dest.writeString(fullname);
        dest.writeString(notice_date);
        dest.writeString(subject);
        dest.writeString(expired_date);
        dest.writeString(employee_grade_name);
        dest.writeString(job_grade_name);
    }

    public int getEmployee_notice_id() {
        return employee_notice_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public String getSubject() {
        return subject;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getJob_grade_name() {
        return job_grade_name;
    }
}
