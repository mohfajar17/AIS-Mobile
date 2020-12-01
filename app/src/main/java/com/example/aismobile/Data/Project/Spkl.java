package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Spkl implements Parcelable {

    private int overtime_workorder_id;
    private String overtime_workorder_number;
    private String proposed_date;
    private String work_description;
    private String work_location;
    private String job_order_id;
    private String department_id;
    private String requested_id;
    private String request_date;
    private String ordered_by;
    private String ordered_date;
    private String approval1_by;
    private String approval1_date;
    private String approval2_by;
    private String approval2_date;
    private String verified_by;
    private String verified_date;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String overtime_archive;
    private String overtime_file_name;
    private String overtime_file_type;

    public Spkl(int overtime_workorder_id, String overtime_workorder_number, String proposed_date, String work_description,
                String work_location, String job_order_id, String department_id, String requested_id,
                String request_date, String ordered_by, String ordered_date, String approval1_by, String approval1_date,
                String approval2_by, String approval2_date, String verified_by, String verified_date,
                String created_by, String created_date, String modified_by, String modified_date, String overtime_archive,
                String overtime_file_name, String overtime_file_type){
        this.overtime_workorder_id = overtime_workorder_id;
        this.overtime_workorder_number = overtime_workorder_number;
        this.proposed_date = proposed_date;
        this.work_description = work_description;
        this.work_location = work_location;
        this.job_order_id = job_order_id;
        this.department_id = department_id;
        this.requested_id = requested_id;
        this.request_date = request_date;
        this.ordered_by = ordered_by;
        this.ordered_date = ordered_date;
        this.approval1_by = approval1_by;
        this.approval1_date = approval1_date;
        this.approval2_by = approval2_by;
        this.approval2_date = approval2_date;
        this.verified_by = verified_by;
        this.verified_date = verified_date;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.overtime_archive = overtime_archive;
        this.overtime_file_name = overtime_file_name;
        this.overtime_file_type = overtime_file_type;
    }

    protected Spkl(Parcel in) {
        overtime_workorder_id = in.readInt();
        overtime_workorder_number = in.readString();
        proposed_date = in.readString();
        work_description = in.readString();
        work_location = in.readString();
        job_order_id = in.readString();
        department_id = in.readString();
        requested_id = in.readString();
        request_date = in.readString();
        ordered_by = in.readString();
        ordered_date = in.readString();
        approval1_by = in.readString();
        approval1_date = in.readString();
        approval2_by = in.readString();
        approval2_date = in.readString();
        verified_by = in.readString();
        verified_date = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        overtime_archive = in.readString();
        overtime_file_name = in.readString();
        overtime_file_type = in.readString();
    }

    public Spkl(JSONObject jsonObject){
        try {
            this.overtime_workorder_id = jsonObject.getInt("overtime_workorder_id");
            this.overtime_workorder_number = jsonObject.getString("overtime_workorder_number");
            this.proposed_date = jsonObject.getString("proposed_date");
            this.work_description = jsonObject.getString("work_description");
            this.work_location = jsonObject.getString("work_location");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.department_id = jsonObject.getString("department_id");
            this.requested_id = jsonObject.getString("requested_id");
            this.request_date = jsonObject.getString("request_date");
            this.ordered_by = jsonObject.getString("ordered_by");
            this.ordered_date = jsonObject.getString("ordered_date");
            this.approval1_by = jsonObject.getString("approval1_by");
            this.approval1_date = jsonObject.getString("approval1_date");
            this.approval2_by = jsonObject.getString("approval2_by");
            this.approval2_date = jsonObject.getString("approval2_date");
            this.verified_by = jsonObject.getString("verified_by");
            this.verified_date = jsonObject.getString("verified_date");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.overtime_archive = jsonObject.getString("overtime_archive");
            this.overtime_file_name = jsonObject.getString("overtime_file_name");
            this.overtime_file_type = jsonObject.getString("overtime_file_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Spkl> CREATOR = new Creator<Spkl>() {
        @Override
        public Spkl createFromParcel(Parcel in) {
            return new Spkl(in);
        }

        @Override
        public Spkl[] newArray(int size) {
            return new Spkl[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(overtime_workorder_id);
        dest.writeString(overtime_workorder_number);
        dest.writeString(proposed_date);
        dest.writeString(work_description);
        dest.writeString(work_location);
        dest.writeString(job_order_id);
        dest.writeString(department_id);
        dest.writeString(requested_id);
        dest.writeString(request_date);
        dest.writeString(ordered_by);
        dest.writeString(ordered_date);
        dest.writeString(approval1_by);
        dest.writeString(approval1_date);
        dest.writeString(approval2_by);
        dest.writeString(approval2_date);
        dest.writeString(verified_by);
        dest.writeString(verified_date);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(overtime_archive);
        dest.writeString(overtime_file_name);
        dest.writeString(overtime_file_type);
    }

    public int getOvertime_workorder_id() {
        return overtime_workorder_id;
    }

    public String getOvertime_workorder_number() {
        return overtime_workorder_number;
    }

    public String getProposed_date() {
        return proposed_date;
    }

    public String getWork_description() {
        return work_description;
    }

    public String getWork_location() {
        return work_location;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public String getRequested_id() {
        return requested_id;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getOrdered_by() {
        return ordered_by;
    }

    public String getOrdered_date() {
        return ordered_date;
    }

    public String getApproval1_by() {
        return approval1_by;
    }

    public String getApproval1_date() {
        return approval1_date;
    }

    public String getApproval2_by() {
        return approval2_by;
    }

    public String getApproval2_date() {
        return approval2_date;
    }

    public String getVerified_by() {
        return verified_by;
    }

    public String getVerified_date() {
        return verified_date;
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

    public String getOvertime_archive() {
        return overtime_archive;
    }

    public String getOvertime_file_name() {
        return overtime_file_name;
    }

    public String getOvertime_file_type() {
        return overtime_file_type;
    }

    public void setApproval1_by(String approval1_by) {
        this.approval1_by = approval1_by;
    }

    public void setApproval2_by(String approval2_by) {
        this.approval2_by = approval2_by;
    }

    public void setVerified_by(String verified_by) {
        this.verified_by = verified_by;
    }
}
