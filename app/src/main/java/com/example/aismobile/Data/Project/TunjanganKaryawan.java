package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TunjanganKaryawan implements Parcelable {

    private int employee_allowance_id;
    private String employee_allowance_number;
    private String employee_id;
    private String employee_grade_name;
    private String additional_allowance_type;
    private String kab_id;
    private String job_order_id;
    private String job_order_description;
    private String job_order_location;
    private String begin_date;
    private String end_date;
    private String days;
    private String notes;
    private String amount_perday;
    private String approval1_status;
    private String approval2_status;
    private String requested_by;
    private String approval1_by;
    private String approval_date1;
    private String approval_comment1;
    private String approval2_by;
    private String approval_date2;
    private String approval_comment2;
    private String verified_by;
    private String verified_date;
    private String checked_by;
    private String checked_date;
    private String checked_comment;
    private String paid;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public TunjanganKaryawan(int employee_allowance_id, String employee_allowance_number, String employee_id, String employee_grade_name,
                             String additional_allowance_type, String kab_id, String job_order_id, String job_order_description, String job_order_location, String begin_date,
                             String end_date, String days, String notes, String amount_perday, String approval1_status,
                             String approval2_status, String requested_by, String approval1_by, String approval_date1,
                             String approval_comment1, String approval2_by, String approval_date2, String approval_comment2,
                             String verified_by, String verified_date, String checked_by, String checked_date,
                             String checked_comment, String paid, String created_by, String created_date,
                             String modified_by, String modified_date){
        this.employee_allowance_id = employee_allowance_id;
        this.employee_allowance_number = employee_allowance_number;
        this.employee_id = employee_id;
        this.employee_grade_name = employee_grade_name;
        this.additional_allowance_type = additional_allowance_type;
        this.kab_id = kab_id;
        this.job_order_id = job_order_id;
        this.job_order_description = job_order_description;
        this.job_order_location = job_order_location;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.days = days;
        this.notes = notes;
        this.amount_perday = amount_perday;
        this.approval1_status = approval1_status;
        this.approval2_status = approval2_status;
        this.requested_by = requested_by;
        this.approval1_by = approval1_by;
        this.approval_date1 = approval_date1;
        this.approval_comment1 = approval_comment1;
        this.approval2_by = approval2_by;
        this.approval_date2 = approval_date2;
        this.approval_comment2 = approval_comment2;
        this.verified_by = verified_by;
        this.verified_date = verified_date;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.checked_comment = checked_comment;
        this.paid = paid;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    protected TunjanganKaryawan(Parcel in) {
        employee_allowance_id = in.readInt();
        employee_allowance_number = in.readString();
        employee_id = in.readString();
        employee_grade_name = in.readString();
        additional_allowance_type = in.readString();
        kab_id = in.readString();
        job_order_id = in.readString();
        job_order_description = in.readString();
        job_order_location = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        days = in.readString();
        notes = in.readString();
        amount_perday = in.readString();
        approval1_status = in.readString();
        approval2_status = in.readString();
        requested_by = in.readString();
        approval1_by = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        approval2_by = in.readString();
        approval_date2 = in.readString();
        approval_comment2 = in.readString();
        verified_by = in.readString();
        verified_date = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        checked_comment = in.readString();
        paid = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public TunjanganKaryawan(JSONObject jsonObject){
        try {
            this.employee_allowance_id = jsonObject.getInt("employee_allowance_id");
            this.employee_allowance_number = jsonObject.getString("employee_allowance_number");
            this.employee_id = jsonObject.getString("employee_id");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.additional_allowance_type = jsonObject.getString("additional_allowance_type");
            this.kab_id = jsonObject.getString("kab_id");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.job_order_location = jsonObject.getString("job_order_location");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.days = jsonObject.getString("days");
            this.notes = jsonObject.getString("notes");
            this.amount_perday = jsonObject.getString("amount_perday");
            this.approval1_status = jsonObject.getString("approval1_status");
            this.approval2_status = jsonObject.getString("approval2_status");
            this.requested_by = jsonObject.getString("requested_by");
            this.approval1_by = jsonObject.getString("approval1_by");
            this.approval_date1 = jsonObject.getString("approval1_date");
            this.approval_comment1 = jsonObject.getString("approval1_comment");
            this.approval2_by = jsonObject.getString("approval2_by");
            this.approval_date2 = jsonObject.getString("approval2_date");
            this.approval_comment2 = jsonObject.getString("approval2_comment");
            this.verified_by = jsonObject.getString("verified_by");
            this.verified_date = jsonObject.getString("verified_date");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.checked_comment = jsonObject.getString("checked_comment");
            this.paid = jsonObject.getString("paid");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<TunjanganKaryawan> CREATOR = new Creator<TunjanganKaryawan>() {
        @Override
        public TunjanganKaryawan createFromParcel(Parcel in) {
            return new TunjanganKaryawan(in);
        }

        @Override
        public TunjanganKaryawan[] newArray(int size) {
            return new TunjanganKaryawan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_allowance_id);
        dest.writeString(employee_allowance_number);
        dest.writeString(employee_id);
        dest.writeString(employee_grade_name);
        dest.writeString(additional_allowance_type);
        dest.writeString(kab_id);
        dest.writeString(job_order_id);
        dest.writeString(job_order_description);
        dest.writeString(job_order_location);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(days);
        dest.writeString(notes);
        dest.writeString(amount_perday);
        dest.writeString(approval1_status);
        dest.writeString(approval2_status);
        dest.writeString(requested_by);
        dest.writeString(approval1_by);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(approval2_by);
        dest.writeString(approval_date2);
        dest.writeString(approval_comment2);
        dest.writeString(verified_by);
        dest.writeString(verified_date);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(checked_comment);
        dest.writeString(paid);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getEmployee_allowance_id() {
        return employee_allowance_id;
    }

    public String getEmployee_allowance_number() {
        return employee_allowance_number;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getAdditional_allowance_type() {
        return additional_allowance_type;
    }

    public String getKab_id() {
        return kab_id;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDays() {
        return days;
    }

    public String getNotes() {
        return notes;
    }

    public String getAmount_perday() {
        return amount_perday;
    }

    public String getApproval1_status() {
        return approval1_status;
    }

    public String getApproval2_status() {
        return approval2_status;
    }

    public String getRequested_by() {
        return requested_by;
    }

    public String getApproval1_by() {
        return approval1_by;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }

    public String getApproval2_by() {
        return approval2_by;
    }

    public String getApproval_date2() {
        return approval_date2;
    }

    public String getApproval_comment2() {
        return approval_comment2;
    }

    public String getVerified_by() {
        return verified_by;
    }

    public String getVerified_date() {
        return verified_date;
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

    public String getPaid() {
        return paid;
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

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getJob_order_location() {
        return job_order_location;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }
}
