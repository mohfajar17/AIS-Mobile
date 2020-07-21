package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Employee implements Parcelable {

    private int employee_id;
    private String employee_number;
    private String fullname;
    private String employee_grade_name;
    private String employee_status;
    private String department_name;
    private String company_workbase_name;
    private String join_date;
    private String termination_date;
    private String is_active;
    private String working_status;

    public Employee (int employee_id, String employee_number, String fullname, String employee_grade_name,
                     String employee_status, String department_name, String company_workbase_name, String join_date,
                     String termination_date, String is_active, String working_status){
        this.employee_id = employee_id;
        this.employee_number = employee_number;
        this.fullname = fullname;
        this.employee_grade_name = employee_grade_name;
        this.employee_status = employee_status;
        this.department_name = department_name;
        this.company_workbase_name = company_workbase_name;
        this.join_date = join_date;
        this.termination_date = termination_date;
        this.is_active = is_active;
        this.working_status = working_status;
    }
    protected Employee (Parcel in) {
        employee_id = in.readInt();
        employee_number = in.readString();
        fullname = in.readString();
        employee_grade_name = in.readString();
        employee_status = in.readString();
        department_name = in.readString();
        company_workbase_name = in.readString();
        join_date = in.readString();
        termination_date = in.readString();
        is_active = in.readString();
        working_status = in.readString();
    }

    public Employee (JSONObject jsonObject){
        try {
            this.employee_id = jsonObject.getInt("employee_id");
            this.employee_number = jsonObject.getString("employee_number");
            this.fullname = jsonObject.getString("fullname");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.employee_status = jsonObject.getString("employee_status");
            this.department_name = jsonObject.getString("department_name");
            this.company_workbase_name = jsonObject.getString("company_workbase_name");
            this.join_date = jsonObject.getString("join_date");
            this.termination_date = jsonObject.getString("termination_date");
            this.is_active = jsonObject.getString("is_active");
            this.working_status = jsonObject.getString("working_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_id);
        dest.writeString(employee_number);
        dest.writeString(fullname);
        dest.writeString(employee_grade_name);
        dest.writeString(employee_status);
        dest.writeString(department_name);
        dest.writeString(company_workbase_name);
        dest.writeString(join_date);
        dest.writeString(termination_date);
        dest.writeString(is_active);
        dest.writeString(working_status);
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getEmployee_status() {
        return employee_status;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getCompany_workbase_name() {
        return company_workbase_name;
    }

    public String getJoin_date() {
        return join_date;
    }

    public String getTermination_date() {
        return termination_date;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getWorking_status() {
        return working_status;
    }
}
