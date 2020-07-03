package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkOrder implements Parcelable {

    private int work_order_id;
    private String work_order_number;
    private String work_order_description;
    private String job_order_id;
    private String job_order_type;
    private String begin_date;
    private String end_date;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String checked_by;
    private String checked_date;
    private String checked_comment;
    private String approval1;
    private String approval_date1;
    private String approval_comment1;
    private String approval2;
    private String approval_date2;
    private String approval_comment2;
    private String approval3;
    private String approval_date3;
    private String approval_comment3;
    private String work_order_discount_type;

    public WorkOrder(int work_order_id, String work_order_number, String work_order_description, String job_order_id,
                     String job_order_type, String begin_date, String end_date, String notes, String created_by,
                     String created_date, String modified_by, String modified_date, String checked_by,
                     String checked_date, String checked_comment, String approval1, String approval_date1,
                     String approval_comment1, String approval2, String approval_date2, String approval_comment2,
                     String approval3, String approval_date3, String approval_comment3, String work_order_discount_type){
        this.work_order_id = work_order_id;
        this.work_order_number = work_order_number;
        this.work_order_description = work_order_description;
        this.job_order_id = job_order_id;
        this.job_order_type = job_order_type;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.checked_comment = checked_comment;
        this.approval1 = approval1;
        this.approval_date1 = approval_date1;
        this.approval_comment1 = approval_comment1;
        this.approval2 = approval2;
        this.approval_date2 = approval_date2;
        this.approval_comment2 = approval_comment2;
        this.approval3 = approval3;
        this.approval_date3 = approval_date3;
        this.approval_comment3 = approval_comment3;
        this.work_order_discount_type = work_order_discount_type;
    }

    protected WorkOrder(Parcel in) {
        work_order_id = in.readInt();
        work_order_number = in.readString();
        work_order_description = in.readString();
        job_order_id = in.readString();
        job_order_type = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        checked_comment = in.readString();
        approval1 = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        approval2 = in.readString();
        approval_date2 = in.readString();
        approval_comment2 = in.readString();
        approval3 = in.readString();
        approval_date3 = in.readString();
        approval_comment3 = in.readString();
        work_order_discount_type = in.readString();
    }

    public WorkOrder(JSONObject jsonObject){
        try {
            this.work_order_id = jsonObject.getInt("work_order_id");
            this.work_order_number = jsonObject.getString("work_order_number");
            this.work_order_description = jsonObject.getString("work_order_description");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.job_order_type = jsonObject.getString("job_order_type");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.checked_comment = jsonObject.getString("checked_comment");
            this.approval1 = jsonObject.getString("approval1");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
            this.approval2 = jsonObject.getString("approval2");
            this.approval_date2 = jsonObject.getString("approval_date2");
            this.approval_comment2 = jsonObject.getString("approval_comment2");
            this.approval3 = jsonObject.getString("approval3");
            this.approval_date3 = jsonObject.getString("approval_date3");
            this.approval_comment3 = jsonObject.getString("approval_comment3");
            this.work_order_discount_type = jsonObject.getString("work_order_discount_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkOrder> CREATOR = new Creator<WorkOrder>() {
        @Override
        public WorkOrder createFromParcel(Parcel in) {
            return new WorkOrder(in);
        }

        @Override
        public WorkOrder[] newArray(int size) {
            return new WorkOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(work_order_id);
        dest.writeString(work_order_number);
        dest.writeString(work_order_description);
        dest.writeString(job_order_id);
        dest.writeString(job_order_type);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(checked_comment);
        dest.writeString(approval1);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(approval2);
        dest.writeString(approval_date2);
        dest.writeString(approval_comment2);
        dest.writeString(approval3);
        dest.writeString(approval_date3);
        dest.writeString(approval_comment3);
        dest.writeString(work_order_discount_type);
    }

    public int getWork_order_id() {
        return work_order_id;
    }

    public String getWork_order_number() {
        return work_order_number;
    }

    public String getWork_order_description() {
        return work_order_description;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getJob_order_type() {
        return job_order_type;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getNotes() {
        return notes;
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

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getChecked_comment() {
        return checked_comment;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }

    public String getApproval2() {
        return approval2;
    }

    public String getApproval_date2() {
        return approval_date2;
    }

    public String getApproval_comment2() {
        return approval_comment2;
    }

    public String getApproval3() {
        return approval3;
    }

    public String getApproval_date3() {
        return approval_date3;
    }

    public String getApproval_comment3() {
        return approval_comment3;
    }

    public String getWork_order_discount_type() {
        return work_order_discount_type;
    }
}
