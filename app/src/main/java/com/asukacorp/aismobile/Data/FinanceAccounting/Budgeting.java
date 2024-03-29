package com.asukacorp.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Budgeting implements Parcelable {

    private int budget_id;
    private String budget_number;
    private String created_by;
    private String start_date;
    private String end_date;
    private String checked_by;
    private String approval1;
    private String approval2;
    private String approval3;
    private String done;
    private String checked_date;
    private String approval_date1;
    private String approval_comment1;
    private String approval_date2;
    private String approval_comment2;
    private String approval_date3;
    private String approval_comment3;
    private String notes;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public Budgeting(int budget_id, String budget_number, String created_by, String start_date,
                   String end_date, String checked_by, String approval1, String approval2,
                   String approval3, String done){
        this.budget_id = budget_id;
        this.budget_number = budget_number;
        this.created_by = created_by;
        this.start_date = start_date;
        this.end_date = end_date;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.approval3 = approval3;
        this.done = done;
    }

    protected Budgeting(Parcel in) {
        budget_id = in.readInt();
        budget_number = in.readString();
        created_by = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        checked_by = in.readString();
        approval1 = in.readString();
        approval2 = in.readString();
        approval3 = in.readString();
        done = in.readString();
        checked_date = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        approval_date2 = in.readString();
        approval_comment2 = in.readString();
        approval_date3 = in.readString();
        approval_comment3 = in.readString();
        notes = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public Budgeting(JSONObject jsonObject){
        try {
            this.budget_id = jsonObject.getInt("budget_id");
            this.budget_number = jsonObject.getString("budget_number");
            this.created_by = jsonObject.getString("created_by");
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
            this.checked_by = jsonObject.getString("checked_by");
            this.approval1 = jsonObject.getString("approval1");
            this.approval2 = jsonObject.getString("approval2");
            this.approval3 = jsonObject.getString("approval3");
            this.done = jsonObject.getString("done");
            this.checked_date = jsonObject.getString("checked_date");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
            this.approval_date2 = jsonObject.getString("approval_date2");
            this.approval_comment2 = jsonObject.getString("approval_comment2");
            this.approval_date3 = jsonObject.getString("approval_date3");
            this.approval_comment3 = jsonObject.getString("approval_comment3");
            this.notes = jsonObject.getString("notes");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Budgeting> CREATOR = new Creator<Budgeting>() {
        @Override
        public Budgeting createFromParcel(Parcel in) {
            return new Budgeting(in);
        }

        @Override
        public Budgeting[] newArray(int size) {
            return new Budgeting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(budget_id);
        dest.writeString(budget_number);
        dest.writeString(created_by);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(checked_by);
        dest.writeString(approval1);
        dest.writeString(approval2);
        dest.writeString(approval3);
        dest.writeString(done);
        dest.writeString(checked_date);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(approval_date2);
        dest.writeString(approval_comment2);
        dest.writeString(approval_date3);
        dest.writeString(approval_comment3);
        dest.writeString(notes);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getBudget_id() {
        return budget_id;
    }

    public String getBudget_number() {
        return budget_number;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getApproval2() {
        return approval2;
    }

    public String getApproval3() {
        return approval3;
    }

    public String getDone() {
        return done;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }

    public String getApproval_date2() {
        return approval_date2;
    }

    public String getApproval_comment2() {
        return approval_comment2;
    }

    public String getApproval_date3() {
        return approval_date3;
    }

    public String getApproval_comment3() {
        return approval_comment3;
    }

    public String getNotes() {
        return notes;
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

    public void setApproval1(String approval1) {
        this.approval1 = approval1;
    }

    public void setApproval2(String approval2) {
        this.approval2 = approval2;
    }

    public void setApproval3(String approval3) {
        this.approval3 = approval3;
    }
}
