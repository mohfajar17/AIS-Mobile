package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentSupplierDetail implements Parcelable {
    private String supplier_id;
    private String bank_account;
    private String bank_name;
    private String name_of_bank_account;
    private String amount;
    private String budget_supplier_app1;
    private String budget_supplier_app2;
    private String budget_supplier_app3;

    protected PaymentSupplierDetail(Parcel in) {
        supplier_id = in.readString();
        bank_account = in.readString();
        bank_name = in.readString();
        name_of_bank_account = in.readString();
        amount = in.readString();
        budget_supplier_app1 = in.readString();
        budget_supplier_app2 = in.readString();
        budget_supplier_app3 = in.readString();
    }

    public PaymentSupplierDetail(JSONObject jsonObject){
        try {
            this.supplier_id = jsonObject.getString("supplier_id");
            this.bank_account = jsonObject.getString("bank_account");
            this.bank_name = jsonObject.getString("bank_name");
            this.name_of_bank_account = jsonObject.getString("name_of_bank_account");
            this.amount = jsonObject.getString("amount");
            this.budget_supplier_app1 = jsonObject.getString("budget_supplier_app1");
            this.budget_supplier_app2 = jsonObject.getString("budget_supplier_app2");
            this.budget_supplier_app3 = jsonObject.getString("budget_supplier_app3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PaymentSupplierDetail> CREATOR = new Creator<PaymentSupplierDetail>() {
        @Override
        public PaymentSupplierDetail createFromParcel(Parcel in) {
            return new PaymentSupplierDetail(in);
        }

        @Override
        public PaymentSupplierDetail[] newArray(int size) {
            return new PaymentSupplierDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(supplier_id);
        dest.writeString(bank_account);
        dest.writeString(bank_name);
        dest.writeString(name_of_bank_account);
        dest.writeString(amount);
        dest.writeString(budget_supplier_app1);
        dest.writeString(budget_supplier_app2);
        dest.writeString(budget_supplier_app3);
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public String getBank_account() {
        return bank_account;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getName_of_bank_account() {
        return name_of_bank_account;
    }

    public String getAmount() {
        return amount;
    }

    public String getBudget_supplier_app1() {
        return budget_supplier_app1;
    }

    public String getBudget_supplier_app2() {
        return budget_supplier_app2;
    }

    public String getBudget_supplier_app3() {
        return budget_supplier_app3;
    }
}
