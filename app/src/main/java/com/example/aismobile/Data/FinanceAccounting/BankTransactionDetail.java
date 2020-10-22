package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class BankTransactionDetail implements Parcelable {
    private String bank_transaction_detail_id;
    private String supplier_invoice;
    private String proposed_budget;
    private String customer_invoice;
    private String cash_project_report;
    private String transaction_detail_name;
    private String destination;
    private String adjustment_value;
    private String amount;
    private String category;
    private String bank_transaction_type_name;

    protected BankTransactionDetail(Parcel in) {
        bank_transaction_detail_id = in.readString();
        supplier_invoice = in.readString();
        proposed_budget = in.readString();
        customer_invoice = in.readString();
        cash_project_report = in.readString();
        transaction_detail_name = in.readString();
        destination = in.readString();
        adjustment_value = in.readString();
        amount = in.readString();
        category = in.readString();
        bank_transaction_type_name = in.readString();
    }

    public BankTransactionDetail(JSONObject jsonObject){
        try {
            this.bank_transaction_detail_id = jsonObject.getString("bank_transaction_detail_id");
            this.supplier_invoice = jsonObject.getString("supplier_invoice");
            this.proposed_budget = jsonObject.getString("proposed_budget");
            this.customer_invoice = jsonObject.getString("customer_invoice");
            this.cash_project_report = jsonObject.getString("cash_project_report");
            this.transaction_detail_name = jsonObject.getString("transaction_detail_name");
            this.destination = jsonObject.getString("destination");
            this.adjustment_value = jsonObject.getString("adjustment_value");
            this.amount = jsonObject.getString("amount");
            this.category = jsonObject.getString("category");
            this.bank_transaction_type_name = jsonObject.getString("bank_transaction_type_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<BankTransactionDetail> CREATOR = new Creator<BankTransactionDetail>() {
        @Override
        public BankTransactionDetail createFromParcel(Parcel in) {
            return new BankTransactionDetail(in);
        }

        @Override
        public BankTransactionDetail[] newArray(int size) {
            return new BankTransactionDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bank_transaction_detail_id);
        dest.writeString(supplier_invoice);
        dest.writeString(proposed_budget);
        dest.writeString(customer_invoice);
        dest.writeString(cash_project_report);
        dest.writeString(transaction_detail_name);
        dest.writeString(destination);
        dest.writeString(adjustment_value);
        dest.writeString(amount);
        dest.writeString(category);
        dest.writeString(bank_transaction_type_name);
    }

    public String getBank_transaction_detail_id() {
        return bank_transaction_detail_id;
    }

    public String getSupplier_invoice() {
        return supplier_invoice;
    }

    public String getProposed_budget() {
        return proposed_budget;
    }

    public String getCustomer_invoice() {
        return customer_invoice;
    }

    public String getCash_project_report() {
        return cash_project_report;
    }

    public String getTransaction_detail_name() {
        return transaction_detail_name;
    }

    public String getDestination() {
        return destination;
    }

    public String getAdjustment_value() {
        return adjustment_value;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getBank_transaction_type_name() {
        return bank_transaction_type_name;
    }
}
