package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CashAdvance implements Parcelable {

    private int advanced_id;
    private String advanced_number;
    private String advanced_date;
    private String advanced_for;
    private String received_by;
    private String amount;
    private String status;
    private String approved_status;
    private String approved_by;
    private String approved_date;
    private String approved_comment;
    private String reconciled_by;
    private String reconciled_date;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public CashAdvance(int advanced_id, String advanced_number, String advanced_date, String advanced_for,
                   String received_by, String amount, String status){
        this.advanced_id = advanced_id;
        this.advanced_number = advanced_number;
        this.advanced_date = advanced_date;
        this.advanced_for = advanced_for;
        this.received_by = received_by;
        this.amount = amount;
        this.status = status;
    }
    protected CashAdvance(Parcel in) {
        advanced_id = in.readInt();
        advanced_number = in.readString();
        advanced_date = in.readString();
        advanced_for = in.readString();
        received_by = in.readString();
        amount = in.readString();
        status = in.readString();
        approved_status = in.readString();
        approved_by = in.readString();
        approved_date = in.readString();
        approved_comment = in.readString();
        reconciled_by = in.readString();
        reconciled_date = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public CashAdvance(JSONObject jsonObject){
        try {
            this.advanced_id = jsonObject.getInt("advanced_id");
            this.advanced_number = jsonObject.getString("advanced_number");
            this.advanced_date = jsonObject.getString("advanced_date");
            this.advanced_for = jsonObject.getString("advanced_for");
            this.received_by = jsonObject.getString("received_by");
            this.amount = jsonObject.getString("amount");
            this.status = jsonObject.getString("status");
            this.approved_status = jsonObject.getString("approved_status");
            this.approved_by = jsonObject.getString("approved_by");
            this.approved_date = jsonObject.getString("approved_date");
            this.approved_comment = jsonObject.getString("approved_comment");
            this.reconciled_by = jsonObject.getString("reconciled_by");
            this.reconciled_date = jsonObject.getString("reconciled_date");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CashAdvance> CREATOR = new Creator<CashAdvance>() {
        @Override
        public CashAdvance createFromParcel(Parcel in) {
            return new CashAdvance(in);
        }

        @Override
        public CashAdvance[] newArray(int size) {
            return new CashAdvance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(advanced_id);
        dest.writeString(advanced_number);
        dest.writeString(advanced_date);
        dest.writeString(advanced_for);
        dest.writeString(received_by);
        dest.writeString(amount);
        dest.writeString(status);
        dest.writeString(approved_status);
        dest.writeString(approved_by);
        dest.writeString(approved_date);
        dest.writeString(approved_comment);
        dest.writeString(reconciled_by);
        dest.writeString(reconciled_date);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getAdvanced_id() {
        return advanced_id;
    }

    public String getAdvanced_number() {
        return advanced_number;
    }

    public String getAdvanced_date() {
        return advanced_date;
    }

    public String getAdvanced_for() {
        return advanced_for;
    }

    public String getReceived_by() {
        return received_by;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getApproved_status() {
        return approved_status;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public String getApproved_date() {
        return approved_date;
    }

    public String getApproved_comment() {
        return approved_comment;
    }

    public String getReconciled_by() {
        return reconciled_by;
    }

    public String getReconciled_date() {
        return reconciled_date;
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
