package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ExpenseDetail implements Parcelable {
    private String expenses_detail_id;
    private String item_name;
    private String job_order_number;
    private String status;
    private String bank_transaction_type_name;
    private String category;
    private String amount;
    private String checked_app;
    private String expenses_app1;
    private String expenses_app2;

    protected ExpenseDetail(Parcel in) {
        expenses_detail_id = in.readString();
        item_name = in.readString();
        job_order_number = in.readString();
        status = in.readString();
        bank_transaction_type_name = in.readString();
        category = in.readString();
        amount = in.readString();
        checked_app = in.readString();
        expenses_app1 = in.readString();
        expenses_app2 = in.readString();
    }

    public ExpenseDetail(JSONObject jsonObject){
        try {
            this.expenses_detail_id = jsonObject.getString("expenses_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.status = jsonObject.getString("status");
            this.bank_transaction_type_name = jsonObject.getString("bank_transaction_type_name");
            this.category = jsonObject.getString("category");
            this.amount = jsonObject.getString("amount");
            this.checked_app = jsonObject.getString("checked_app");
            this.expenses_app1 = jsonObject.getString("expenses_app1");
            this.expenses_app2 = jsonObject.getString("expenses_app2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ExpenseDetail> CREATOR = new Creator<ExpenseDetail>() {
        @Override
        public ExpenseDetail createFromParcel(Parcel in) {
            return new ExpenseDetail(in);
        }

        @Override
        public ExpenseDetail[] newArray(int size) {
            return new ExpenseDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenses_detail_id);
        dest.writeString(item_name);
        dest.writeString(job_order_number);
        dest.writeString(status);
        dest.writeString(bank_transaction_type_name);
        dest.writeString(category);
        dest.writeString(amount);
        dest.writeString(checked_app);
        dest.writeString(expenses_app1);
        dest.writeString(expenses_app2);
    }

    public String getExpenses_detail_id() {
        return expenses_detail_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getStatus() {
        return status;
    }

    public String getBank_transaction_type_name() {
        return bank_transaction_type_name;
    }

    public String getCategory() {
        return category;
    }

    public String getAmount() {
        return amount;
    }

    public String getChecked_app() {
        return checked_app;
    }

    public String getExpenses_app1() {
        return expenses_app1;
    }

    public String getExpenses_app2() {
        return expenses_app2;
    }
}
