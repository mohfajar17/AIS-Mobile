package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentSupplier implements Parcelable {

    private int budget_supplier_id;
    private String budget_supplier_number;
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
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public PaymentSupplier(int budget_supplier_id, String budget_supplier_number, String start_date, String end_date,
                     String checked_by, String approval1, String approval2, String approval3, String done){
        this.budget_supplier_id = budget_supplier_id;
        this.budget_supplier_number = budget_supplier_number;
        this.start_date = start_date;
        this.end_date = end_date;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.approval3 = approval3;
        this.done = done;
    }

    protected PaymentSupplier(Parcel in) {
        budget_supplier_id = in.readInt();
        budget_supplier_number = in.readString();
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
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public PaymentSupplier(JSONObject jsonObject){
        try {
            this.budget_supplier_id = jsonObject.getInt("budget_supplier_id");
            this.budget_supplier_number = jsonObject.getString("budget_supplier_number");
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
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PaymentSupplier> CREATOR = new Creator<PaymentSupplier>() {
        @Override
        public PaymentSupplier createFromParcel(Parcel in) {
            return new PaymentSupplier(in);
        }

        @Override
        public PaymentSupplier[] newArray(int size) {
            return new PaymentSupplier[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(budget_supplier_id);
        dest.writeString(budget_supplier_number);
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
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getBudget_supplier_id() {
        return budget_supplier_id;
    }

    public String getBudget_supplier_number() {
        return budget_supplier_number;
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
