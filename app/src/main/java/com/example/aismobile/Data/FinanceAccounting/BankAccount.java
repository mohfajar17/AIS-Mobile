package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class BankAccount implements Parcelable {

    private int bank_account_id;
    private String bank_account_name;
    private String bank_account_number;
    private String bank_name;
    private String currency_code;
    private String ending_reconcile_balance;
    private String last_reconciled_date;
    private String is_active;

    public BankAccount(int bank_account_id, String bank_account_name, String bank_account_number, String bank_name,
                       String currency_code, String ending_reconcile_balance, String last_reconciled_date,
                       String is_active){
        this.bank_account_id = bank_account_id;
        this.bank_account_name = bank_account_name;
        this.bank_account_number = bank_account_number;
        this.bank_name = bank_name;
        this.currency_code = currency_code;
        this.ending_reconcile_balance = ending_reconcile_balance;
        this.last_reconciled_date = last_reconciled_date;
        this.is_active = is_active;
    }

    protected BankAccount(Parcel in) {
        bank_account_id = in.readInt();
        bank_account_name = in.readString();
        bank_account_number = in.readString();
        bank_name = in.readString();
        currency_code = in.readString();
        ending_reconcile_balance = in.readString();
        last_reconciled_date = in.readString();
        is_active = in.readString();
    }

    public BankAccount(JSONObject jsonObject){
        try {
            this.bank_account_id = jsonObject.getInt("bank_account_id");
            this.bank_account_name = jsonObject.getString("bank_account_name");
            this.bank_account_number = jsonObject.getString("bank_account_number");
            this.bank_name = jsonObject.getString("bank_name");
            this.currency_code = jsonObject.getString("currency_code");
            this.ending_reconcile_balance = jsonObject.getString("ending_reconcile_balance");
            this.last_reconciled_date = jsonObject.getString("last_reconciled_date");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<BankAccount> CREATOR = new Creator<BankAccount>() {
        @Override
        public BankAccount createFromParcel(Parcel in) {
            return new BankAccount(in);
        }

        @Override
        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bank_account_id);
        dest.writeString(bank_account_name);
        dest.writeString(bank_account_number);
        dest.writeString(bank_name);
        dest.writeString(currency_code);
        dest.writeString(ending_reconcile_balance);
        dest.writeString(last_reconciled_date);
        dest.writeString(is_active);
    }

    public int getBank_account_id() {
        return bank_account_id;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public String getEnding_reconcile_balance() {
        return ending_reconcile_balance;
    }

    public String getLast_reconciled_date() {
        return last_reconciled_date;
    }

    public String getIs_active() {
        return is_active;
    }
}
