package com.example.aismobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ProposedBudget implements Parcelable {

    private int cash_advance_id;
    private String cash_advance_number;
    private String job_order_id;
    private String person_in_charge;
    private String requisition_date;
    private String due_date;
    private String rest_value;
    private String rest_from;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String approval1;
    private String approval_date1;
    private String approval_comment1;
    private String approval1_status;
    private String approval2;
    private String approval_date2;
    private String approval_comment2;
    private String approval2_status;
    private String approval3;
    private String approval_date3;
    private String approval_comment3;
    private String approval3_status;
    private String checked_by;
    private String checked_date;
    private String recipient_by;
    private String done;
    private String bank_transaction_type_id;

    public ProposedBudget(int cash_advance_id, String cash_advance_number, String job_order_id, String person_in_charge,
                          String requisition_date, String due_date, String rest_value, String rest_from, String notes,
                          String created_by, String created_date, String modified_by, String modified_date, String approval1,
                          String approval_date1, String approval_comment1, String approval1_status, String approval2,
                          String approval_date2, String approval_comment2, String approval2_status, String approval3,
                          String approval_date3, String approval_comment3, String approval3_status, String checked_by,
                          String checked_date, String recipient_by, String done, String bank_transaction_type_id){
        this.cash_advance_id = cash_advance_id;
        this.cash_advance_number = cash_advance_number;
        this.job_order_id = job_order_id;
        this.person_in_charge = person_in_charge;
        this.requisition_date = requisition_date;
        this.due_date = due_date;
        this.rest_value = rest_value;
        this.rest_from = rest_from;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.approval1 = approval1;
        this.approval_date1 = approval_date1;
        this.approval_comment1 = approval_comment1;
        this.approval1_status = approval1_status;
        this.approval2 = approval2;
        this.approval_date2 = approval_date2;
        this.approval_comment2 = approval_comment2;
        this.approval2_status = approval2_status;
        this.approval3 = approval3;
        this.approval_date3 = approval_date3;
        this.approval_comment3 = approval_comment3;
        this.approval3_status = approval3_status;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.recipient_by = recipient_by;
        this.done = done;
        this.bank_transaction_type_id = bank_transaction_type_id;
    }

    protected ProposedBudget(Parcel in) {
        cash_advance_id = in.readInt();
        cash_advance_number = in.readString();
        job_order_id = in.readString();
        person_in_charge = in.readString();
        requisition_date = in.readString();
        due_date = in.readString();
        rest_value = in.readString();
        rest_from = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        approval1 = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        approval1_status = in.readString();
        approval2 = in.readString();
        approval_date2 = in.readString();
        approval_comment2 = in.readString();
        approval2_status = in.readString();
        approval3 = in.readString();
        approval_date3 = in.readString();
        approval_comment3 = in.readString();
        approval3_status = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        recipient_by = in.readString();
        done = in.readString();
        bank_transaction_type_id = in.readString();
    }

    public ProposedBudget(JSONObject jsonObject){
        try {
            this.cash_advance_id = jsonObject.getInt("cash_advance_id");
            this.cash_advance_number = jsonObject.getString("cash_advance_number");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.person_in_charge = jsonObject.getString("person_in_charge");
            this.requisition_date = jsonObject.getString("requisition_date");
            this.due_date = jsonObject.getString("due_date");
            this.rest_value = jsonObject.getString("rest_value");
            this.rest_from = jsonObject.getString("rest_from");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.approval1 = jsonObject.getString("approval1");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
            this.approval1_status = jsonObject.getString("approval1_status");
            this.approval2 = jsonObject.getString("approval2");
            this.approval_date2 = jsonObject.getString("approval_date2");
            this.approval_comment2 = jsonObject.getString("approval_comment2");
            this.approval2_status = jsonObject.getString("approval2_status");
            this.approval3 = jsonObject.getString("approval3");
            this.approval_date3 = jsonObject.getString("approval_date3");
            this.approval_comment3 = jsonObject.getString("approval_comment3");
            this.approval3_status = jsonObject.getString("approval3_status");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.recipient_by = jsonObject.getString("recipient_by");
            this.done = jsonObject.getString("done");
            this.bank_transaction_type_id = jsonObject.getString("bank_transaction_type_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ProposedBudget> CREATOR = new Creator<ProposedBudget>() {
        @Override
        public ProposedBudget createFromParcel(Parcel in) {
            return new ProposedBudget(in);
        }

        @Override
        public ProposedBudget[] newArray(int size) {
            return new ProposedBudget[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cash_advance_id);
        dest.writeString(cash_advance_number);
        dest.writeString(job_order_id);
        dest.writeString(person_in_charge);
        dest.writeString(requisition_date);
        dest.writeString(due_date);
        dest.writeString(rest_value);
        dest.writeString(rest_from);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(approval1);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(approval1_status);
        dest.writeString(approval2);
        dest.writeString(approval_date2);
        dest.writeString(approval_comment2);
        dest.writeString(approval2_status);
        dest.writeString(approval3);
        dest.writeString(approval_date3);
        dest.writeString(approval_comment3);
        dest.writeString(approval3_status);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(recipient_by);
        dest.writeString(done);
        dest.writeString(bank_transaction_type_id);
    }

    public int getCash_advance_id() {
        return cash_advance_id;
    }

    public String getCash_advance_number() {
        return cash_advance_number;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getPerson_in_charge() {
        return person_in_charge;
    }

    public String getRequisition_date() {
        return requisition_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getRest_value() {
        return rest_value;
    }

    public String getRest_from() {
        return rest_from;
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

    public String getApproval1() {
        return approval1;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }

    public String getApproval1_status() {
        return approval1_status;
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

    public String getApproval2_status() {
        return approval2_status;
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

    public String getApproval3_status() {
        return approval3_status;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getRecipient_by() {
        return recipient_by;
    }

    public String getDone() {
        return done;
    }

    public String getBank_transaction_type_id() {
        return bank_transaction_type_id;
    }
}
