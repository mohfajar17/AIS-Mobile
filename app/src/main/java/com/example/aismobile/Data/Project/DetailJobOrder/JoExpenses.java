package com.example.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoExpenses implements Parcelable {

    private int expenses_detail_id;
    private String expenses_id;
    private String item_name;
    private String amount;
    private String bank_transaction_type_name;
    private String expenses_number;
    private String expenses_desc;
    private String bank_account_name;
    private String begin_date;

    public JoExpenses(int expenses_detail_id, String expenses_id, String item_name, String amount, String bank_transaction_type_name,
                      String expenses_number, String expenses_desc, String bank_account_name, String begin_date){
        this.expenses_detail_id = expenses_detail_id;
        this.expenses_id = expenses_id;
        this.item_name = item_name;
        this.amount = amount;
        this.bank_transaction_type_name = bank_transaction_type_name;
        this.expenses_number = expenses_number;
        this.expenses_desc = expenses_desc;
        this.bank_account_name = bank_account_name;
        this.begin_date = begin_date;
    }

    protected JoExpenses(Parcel in) {
        expenses_detail_id = in.readInt();
        expenses_id = in.readString();
        item_name = in.readString();
        amount = in.readString();
        bank_transaction_type_name = in.readString();
        expenses_number = in.readString();
        expenses_desc = in.readString();
        bank_account_name = in.readString();
        begin_date = in.readString();
    }

    public JoExpenses(JSONObject jsonObject){
        try {
            this.expenses_detail_id = jsonObject.getInt("expenses_detail_id");
            this.expenses_id = jsonObject.getString("expenses_id");
            this.item_name = jsonObject.getString("item_name");
            this.amount = jsonObject.getString("amount");
            this.bank_transaction_type_name = jsonObject.getString("bank_transaction_type_name");
            this.expenses_number = jsonObject.getString("expenses_number");
            this.expenses_desc = jsonObject.getString("expenses_desc");
            this.bank_account_name = jsonObject.getString("bank_account_name");
            this.begin_date = jsonObject.getString("begin_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoExpenses> CREATOR = new Creator<JoExpenses>() {
        @Override
        public JoExpenses createFromParcel(Parcel in) {
            return new JoExpenses(in);
        }

        @Override
        public JoExpenses[] newArray(int size) {
            return new JoExpenses[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expenses_detail_id);
        dest.writeString(expenses_id);
        dest.writeString(item_name);
        dest.writeString(amount);
        dest.writeString(bank_transaction_type_name);
        dest.writeString(expenses_number);
        dest.writeString(expenses_desc);
        dest.writeString(bank_account_name);
        dest.writeString(begin_date);
    }

    public int getExpenses_detail_id() {
        return expenses_detail_id;
    }

    public String getExpenses_id() {
        return expenses_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getAmount() {
        return amount;
    }

    public String getBank_transaction_type_name() {
        return bank_transaction_type_name;
    }

    public String getExpenses_number() {
        return expenses_number;
    }

    public String getExpenses_desc() {
        return expenses_desc;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public String getBegin_date() {
        return begin_date;
    }
}
