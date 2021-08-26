package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeGrade implements Parcelable {

    private int employee_grade_id;
    private String employee_grade_name;
    private String report_code;
    private String jobtitle_name;
    private String job_grade_name;
    private String salary_grade_name;
    private String overtime_limit;
    private String is_active;

    public EmployeeGrade (int employee_grade_id, String employee_grade_name, String report_code, String jobtitle_name,
                       String job_grade_name, String salary_grade_name, String overtime_limit, String is_active){
        this.employee_grade_id = employee_grade_id;
        this.employee_grade_name = employee_grade_name;
        this.report_code = report_code;
        this.jobtitle_name = jobtitle_name;
        this.job_grade_name = job_grade_name;
        this.salary_grade_name = salary_grade_name;
        this.overtime_limit = overtime_limit;
        this.is_active = is_active;
    }
    protected EmployeeGrade (Parcel in) {
        employee_grade_id = in.readInt();
        employee_grade_name = in.readString();
        report_code = in.readString();
        jobtitle_name = in.readString();
        job_grade_name = in.readString();
        salary_grade_name = in.readString();
        overtime_limit = in.readString();
        is_active = in.readString();
    }

    public EmployeeGrade (JSONObject jsonObject){
        try {
            this.employee_grade_id = jsonObject.getInt("employee_grade_id");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.report_code = jsonObject.getString("report_code");
            this.jobtitle_name = jsonObject.getString("jobtitle_name");
            this.job_grade_name = jsonObject.getString("job_grade_name");
            this.salary_grade_name = jsonObject.getString("salary_grade_name");
            this.overtime_limit = jsonObject.getString("overtime_limit");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeGrade> CREATOR = new Creator<EmployeeGrade>() {
        @Override
        public EmployeeGrade createFromParcel(Parcel in) {
            return new EmployeeGrade(in);
        }

        @Override
        public EmployeeGrade[] newArray(int size) {
            return new EmployeeGrade[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_grade_id);
        dest.writeString(employee_grade_name);
        dest.writeString(report_code);
        dest.writeString(jobtitle_name);
        dest.writeString(job_grade_name);
        dest.writeString(salary_grade_name);
        dest.writeString(overtime_limit);
        dest.writeString(is_active);
    }

    public int getEmployee_grade_id() {
        return employee_grade_id;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getReport_code() {
        return report_code;
    }

    public String getJobtitle_name() {
        return jobtitle_name;
    }

    public String getJob_grade_name() {
        return job_grade_name;
    }

    public String getSalary_grade_name() {
        return salary_grade_name;
    }

    public String getOvertime_limit() {
        return overtime_limit;
    }

    public String getIs_active() {
        return is_active;
    }
}
