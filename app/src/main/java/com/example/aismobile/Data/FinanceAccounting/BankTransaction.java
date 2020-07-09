package com.example.aismobile.Data.FinanceAccounting;

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
    private String status;
    private String reconciled;

    public BankTransaction(int bank_transaction_id, String bank_transaction_number, String checked_by,
                           String approval1, String approval2, String transaction_date, String status,
                           String reconciled){
        this.bank_transaction_id = bank_transaction_id;
        this.bank_transaction_number = bank_transaction_number;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.transaction_date = transaction_date;
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
        status = in.readString();
        reconciled = in.readString();
    }

    public BankTransaction(JSONObject jsonObject){
        try {
            this.bank_transaction_id = jsonObject.getInt("bank_transaction_id");
            this.bank_transaction_number = jsonObject.getString("bank_transaction_number");
            this.checked_by = jsonObject.getString("checked_by");
            this.approval1 = jsonObject.getString("approval1");
            this.approval2 = jsonObject.getString("approval2");
            this.transaction_date = jsonObject.getString("transaction_date");
            this.status = jsonObject.getString("status");
            this.reconciled = jsonObject.getString("reconciled");
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
        dest.writeString(status);
        dest.writeString(reconciled);
    }
}