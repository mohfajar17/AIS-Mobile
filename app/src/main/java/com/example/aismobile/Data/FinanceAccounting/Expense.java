package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Expense implements Parcelable {

    private int expenses_id;
    private String expenses_number;
    private String expenses_desc;
    private String checked_by;
    private String approval1;
    private String approval2;
    private String advanced_number;
    private String expenses_date;
    private String total_amount;
    private String bank_account_name;
    private String done;

    public Expense(int expenses_id, String expenses_number, String expenses_desc, String checked_by,
                   String approval1, String approval2, String advanced_number, String expenses_date,
                   String total_amount, String bank_account_name, String done){
        this.expenses_id = expenses_id;
        this.expenses_number = expenses_number;
        this.expenses_desc = expenses_desc;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.advanced_number = advanced_number;
        this.expenses_date = expenses_date;
        this.total_amount = total_amount;
        this.bank_account_name = bank_account_name;
        this.done = done;
    }
    protected Expense(Parcel in) {
        expenses_id = in.readInt();
        expenses_number = in.readString();
        expenses_desc = in.readString();
        checked_by = in.readString();
        approval1 = in.readString();
        approval2 = in.readString();
        advanced_number = in.readString();
        expenses_date = in.readString();
        total_amount = in.readString();
        bank_account_name = in.readString();
        done = in.readString();
    }

    public Expense(JSONObject jsonObject){
        try {
            this.expenses_id = jsonObject.getInt("expenses_id");
            this.expenses_number = jsonObject.getString("expenses_number");
            this.expenses_desc = jsonObject.getString("expenses_desc");
            this.checked_by = jsonObject.getString("checked_by");
            this.approval1 = jsonObject.getString("approval1");
            this.approval2 = jsonObject.getString("approval2");
            this.advanced_number = jsonObject.getString("advanced_number");
            this.expenses_date = jsonObject.getString("expenses_date");
            this.total_amount = jsonObject.getString("total_amount");
            this.bank_account_name = jsonObject.getString("bank_account_name");
            this.done = jsonObject.getString("done");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expenses_id);
        dest.writeString(expenses_number);
        dest.writeString(expenses_desc);
        dest.writeString(checked_by);
        dest.writeString(approval1);
        dest.writeString(approval2);
        dest.writeString(advanced_number);
        dest.writeString(expenses_date);
        dest.writeString(total_amount);
        dest.writeString(bank_account_name);
        dest.writeString(done);
    }
}
