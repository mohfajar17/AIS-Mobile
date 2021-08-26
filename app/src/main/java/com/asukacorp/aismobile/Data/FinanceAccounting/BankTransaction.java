package com.asukacorp.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class BankTransaction implements Parcelable {

    private int bank_transaction_id;
    private String bank_transaction_number;
    private String checked_by;
    private String approval1;
    private String approval2;
    private String transaction_date;
    private String total_amount;
    private String status;
    private String reconciled;
    private String bank_transaction_description;
    private String bank_name;
    private String bank_account_number;
    private String transaction_number;
    private String reconciled_by;
    private String reconciled_date;
    private String checked_date;
    private String checked_comment;
    private String approval_date1;
    private String approval_comment1;
    private String approval_date2;
    private String approval_comment2;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String notes;
    private String bank_transaction_file_name;

    public BankTransaction(int bank_transaction_id, String bank_transaction_number, String checked_by,
                           String approval1, String approval2, String transaction_date, String total_amount,
                           String status, String reconciled){
        this.bank_transaction_id = bank_transaction_id;
        this.bank_transaction_number = bank_transaction_number;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.transaction_date = transaction_date;
        this.total_amount = total_amount;
        this.status = status;
        this.reconciled = reconciled;
    }
    protected BankTransaction(Parcel in) {
        bank_transaction_id = in.readInt();
        bank_transaction_number = in.readString();
        checked_by = in.readString();
        approval1 = in.readString();
        approval2 = in.readString();
        transaction_date = in.readString();
        total_amount = in.readString();
        status = in.readString();
        reconciled = in.readString();
        bank_transaction_description = in.readString();
        bank_name = in.readString();
        bank_account_number = in.readString();
        transaction_number = in.readString();
        reconciled_by = in.readString();
        reconciled_date = in.readString();
        checked_date = in.readString();
        checked_comment = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        approval_date2 = in.readString();
        approval_comment2 = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        notes = in.readString();
        bank_transaction_file_name = in.readString();
    }

    public BankTransaction(JSONObject jsonObject){
        try {
            this.bank_transaction_id = jsonObject.getInt("bank_transaction_id");
            this.bank_transaction_number = jsonObject.getString("bank_transaction_number");
            this.checked_by = jsonObject.getString("checked_by");
            this.approval1 = jsonObject.getString("approval1");
            this.approval2 = jsonObject.getString("approval2");
            this.transaction_date = jsonObject.getString("transaction_date");
            this.total_amount = jsonObject.getString("total_amount");
            this.status = jsonObject.getString("status");
            this.reconciled = jsonObject.getString("reconciled");
            this.bank_transaction_description = jsonObject.getString("bank_transaction_description");
            this.bank_name = jsonObject.getString("bank_name");
            this.bank_account_number = jsonObject.getString("bank_account_number");
            this.transaction_number = jsonObject.getString("transaction_number");
            this.reconciled_by = jsonObject.getString("reconciled_by");
            this.reconciled_date = jsonObject.getString("reconciled_date");
            this.checked_date = jsonObject.getString("checked_date");
            this.checked_comment = jsonObject.getString("checked_comment");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
            this.approval_date2 = jsonObject.getString("approval_date2");
            this.approval_comment2 = jsonObject.getString("approval_comment2");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.notes = jsonObject.getString("notes");
            this.bank_transaction_file_name = jsonObject.getString("bank_transaction_file_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<BankTransaction> CREATOR = new Creator<BankTransaction>() {
        @Override
        public BankTransaction createFromParcel(Parcel in) {
            return new BankTransaction(in);
        }

        @Override
        public BankTransaction[] newArray(int size) {
            return new BankTransaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bank_transaction_id);
        dest.writeString(bank_transaction_number);
        dest.writeString(checked_by);
        dest.writeString(approval1);
        dest.writeString(approval2);
        dest.writeString(transaction_date);
        dest.writeString(total_amount);
        dest.writeString(status);
        dest.writeString(reconciled);
        dest.writeString(bank_transaction_description);
        dest.writeString(bank_name);
        dest.writeString(bank_account_number);
        dest.writeString(transaction_number);
        dest.writeString(reconciled_by);
        dest.writeString(reconciled_date);
        dest.writeString(checked_date);
        dest.writeString(checked_comment);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(approval_date2);
        dest.writeString(approval_comment2);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(notes);
        dest.writeString(bank_transaction_file_name);
    }

    public int getBank_transaction_id() {
        return bank_transaction_id;
    }

    public String getBank_transaction_number() {
        return bank_transaction_number;
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

    public String getTransaction_date() {
        return transaction_date;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getStatus() {
        return status;
    }

    public String getReconciled() {
        return reconciled;
    }

    public String getBank_transaction_description() {
        return bank_transaction_description;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public String getTransaction_number() {
        return transaction_number;
    }

    public String getReconciled_by() {
        return reconciled_by;
    }

    public String getReconciled_date() {
        return reconciled_date;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getChecked_comment() {
        return checked_comment;
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

    public String getNotes() {
        return notes;
    }

    public String getBank_transaction_file_name() {
        return bank_transaction_file_name;
    }

    public void setChecked_by(String checked_by) {
        this.checked_by = checked_by;
    }

    public void setApproval1(String approval1) {
        this.approval1 = approval1;
    }

    public void setApproval2(String approval2) {
        this.approval2 = approval2;
    }
}
