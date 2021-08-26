package com.asukacorp.aismobile.Data.Personalia;

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
    private String basic_salary;
    private String meal_allowance;
    private String transport_allowance;
    private String profesional_allowance;
    private String overtime;
    private String welfare_allowance;
    private String location_project_allowance;
    private String other_allowance;
    private String notes;
    private String prepared_by;
    private String commented_by;
    private String processed_by;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

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
        basic_salary = in.readString();
        meal_allowance = in.readString();
        transport_allowance = in.readString();
        profesional_allowance = in.readString();
        overtime = in.readString();
        welfare_allowance = in.readString();
        location_project_allowance = in.readString();
        other_allowance = in.readString();
        notes = in.readString();
        prepared_by = in.readString();
        commented_by = in.readString();
        processed_by = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
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
            this.basic_salary = jsonObject.getString("basic_salary");
            this.meal_allowance = jsonObject.getString("meal_allowance");
            this.transport_allowance = jsonObject.getString("transport_allowance");
            this.profesional_allowance = jsonObject.getString("profesional_allowance");
            this.overtime = jsonObject.getString("overtime");
            this.welfare_allowance = jsonObject.getString("welfare_allowance");
            this.location_project_allowance = jsonObject.getString("location_project_allowance");
            this.other_allowance = jsonObject.getString("other_allowance");
            this.notes = jsonObject.getString("notes");
            this.prepared_by = jsonObject.getString("prepared_by");
            this.commented_by = jsonObject.getString("commented_by");
            this.processed_by = jsonObject.getString("processed_by");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
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
        dest.writeString(basic_salary);
        dest.writeString(meal_allowance);
        dest.writeString(transport_allowance);
        dest.writeString(profesional_allowance);
        dest.writeString(overtime);
        dest.writeString(welfare_allowance);
        dest.writeString(location_project_allowance);
        dest.writeString(other_allowance);
        dest.writeString(notes);
        dest.writeString(prepared_by);
        dest.writeString(commented_by);
        dest.writeString(processed_by);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
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

    public String getBasic_salary() {
        return basic_salary;
    }

    public String getMeal_allowance() {
        return meal_allowance;
    }

    public String getTransport_allowance() {
        return transport_allowance;
    }

    public String getProfesional_allowance() {
        return profesional_allowance;
    }

    public String getOvertime() {
        return overtime;
    }

    public String getWelfare_allowance() {
        return welfare_allowance;
    }

    public String getLocation_project_allowance() {
        return location_project_allowance;
    }

    public String getOther_allowance() {
        return other_allowance;
    }

    public String getNotes() {
        return notes;
    }

    public String getPrepared_by() {
        return prepared_by;
    }

    public String getCommented_by() {
        return commented_by;
    }

    public String getProcessed_by() {
        return processed_by;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }
}
