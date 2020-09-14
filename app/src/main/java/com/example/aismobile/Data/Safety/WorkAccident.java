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
    private String is_work_location;
    private String witness;
    private String notes;
    private String action;
    private String counter_meassure;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String accident_photo_1;
    private String accident_photo_2;
    private String accident_photo_3;

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
        is_work_location = in.readString();
        witness = in.readString();
        notes = in.readString();
        action = in.readString();
        counter_meassure = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        accident_photo_1 = in.readString();
        accident_photo_2 = in.readString();
        accident_photo_3 = in.readString();
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
            this.is_work_location = jsonObject.getString("is_work_location");
            this.witness = jsonObject.getString("witness");
            this.notes = jsonObject.getString("notes");
            this.action = jsonObject.getString("action");
            this.counter_meassure = jsonObject.getString("counter_meassure");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.accident_photo_1 = jsonObject.getString("accident_photo_1");
            this.accident_photo_2 = jsonObject.getString("accident_photo_2");
            this.accident_photo_3 = jsonObject.getString("accident_photo_3");
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
        dest.writeString(is_work_location);
        dest.writeString(witness);
        dest.writeString(notes);
        dest.writeString(action);
        dest.writeString(counter_meassure);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(accident_photo_1);
        dest.writeString(accident_photo_2);
        dest.writeString(accident_photo_3);
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

    public String getIs_work_location() {
        return is_work_location;
    }

    public String getWitness() {
        return witness;
    }

    public String getNotes() {
        return notes;
    }

    public String getAction() {
        return action;
    }

    public String getCounter_meassure() {
        return counter_meassure;
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

    public String getAccident_photo_1() {
        return accident_photo_1;
    }

    public String getAccident_photo_2() {
        return accident_photo_2;
    }

    public String getAccident_photo_3() {
        return accident_photo_3;
    }
}
