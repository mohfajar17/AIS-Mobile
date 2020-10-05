package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerReceivesDetail implements Parcelable {
    private String sales_order_invoice_number;
    private String sales_order_invoice_description;
    private String amount;

    protected CustomerReceivesDetail(Parcel in) {
        sales_order_invoice_number = in.readString();
        sales_order_invoice_description = in.readString();
        amount = in.readString();
    }

    public CustomerReceivesDetail(JSONObject jsonObject){
        try {
            this.sales_order_invoice_number = jsonObject.getString("sales_order_invoice_number");
            this.sales_order_invoice_description = jsonObject.getString("sales_order_invoice_description");
            this.amount = jsonObject.getString("amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CustomerReceivesDetail> CREATOR = new Creator<CustomerReceivesDetail>() {
        @Override
        public CustomerReceivesDetail createFromParcel(Parcel in) {
            return new CustomerReceivesDetail(in);
        }

        @Override
        public CustomerReceivesDetail[] newArray(int size) {
            return new CustomerReceivesDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sales_order_invoice_number);
        dest.writeString(sales_order_invoice_description);
        dest.writeString(amount);
    }

    public String getSales_order_invoice_number() {
        return sales_order_invoice_number;
    }

    public String getSales_order_invoice_description() {
        return sales_order_invoice_description;
    }

    public String getAmount() {
        return amount;
    }
}
