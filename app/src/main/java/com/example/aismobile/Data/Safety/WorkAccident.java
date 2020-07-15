package com.example.aismobile.Data.Safety;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkAccident implements Parcelable {

    private int work_accident_id;
    private String employee_id;
    private String employee_name;
    private String day_accident;
    private String date_accident;
    private String time_accident;
    private String job_grade_id;
    private String company_workbase_id;
    private String accident_type;

    public WorkAccident (int work_accident_id, String employee_id, String employee_name, String day_accident,
                         String date_accident, String time_accident, String job_grade_id, String company_workbase_id,
                         String accident_type){
        this.work_accident_id = work_accident_id;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.day_accident = day_accident;
        this.date_accident = date_accident;
        this.time_accident = time_accident;
        this.job_grade_id = job_grade_id;
        this.company_workbase_id = company_workbase_id;
        this.accident_type = accident_type;
    }

    protected WorkAccident(Parcel in) {
        work_accident_id = in.readInt();
        employee_id = in.readString();
        employee_name = in.readString();
        day_accident = in.readString();
        date_accident = in.readString();
        time_accident = in.readString();
        job_grade_id = in.readString();
        company_workbase_id = in.readString();
        accident_type = in.readString();
    }

    public WorkAccident(JSONObject jsonObject){
        try {
            this.work_accident_id = jsonObject.getInt("work_accident_id");
            this.employee_id = jsonObject.getString("employee_id");
            this.employee_name = jsonObject.getString("employee_name");
            this.day_accident = jsonObject.getString("day_accident");
            this.date_accident = jsonObject.getString("date_accident");
            this.time_accident = jsonObject.getString("time_accident");
            this.job_grade_id = jsonObject.getString("job_grade_id");
            this.company_workbase_id = jsonObject.getString("company_workbase_id");
            this.accident_type = jsonObject.getString("accident_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkAccident> CREATOR = new Creator<WorkAccident>() {
        @Override
        public WorkAccident createFromParcel(Parcel in) {
            return new WorkAccident(in);
        }

        @Override
        public WorkAccident[] newArray(int size) {
            return new WorkAccident[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(work_accident_id);
        dest.writeString(employee_id);
        dest.writeString(employee_name);
        dest.writeString(day_accident);
        dest.writeString(date_accident);
        dest.writeString(time_accident);
        dest.writeString(job_grade_id);
        dest.writeString(company_workbase_id);
        dest.writeString(accident_type);
    }

    public int getWork_accident_id() {
        return work_accident_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getDay_accident() {
        return day_accident;
    }

    public String getDate_accident() {
        return date_accident;
    }

    public String getTime_accident() {
        return time_accident;
    }

    public String getJob_grade_id() {
        return job_grade_id;
    }

    public String getCompany_workbase_id() {
        return company_workbase_id;
    }

    public String getAccident_type() {
        return accident_type;
    }
}
