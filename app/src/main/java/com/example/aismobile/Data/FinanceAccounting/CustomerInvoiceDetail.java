package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerInvoiceDetail implements Parcelable {
    private String description;
    private String quantity;
    private String unit_abbr;
    private String amount;

    protected CustomerInvoiceDetail(Parcel in) {
        description = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        amount = in.readString();
    }

    public CustomerInvoiceDetail(JSONObject jsonObject){
        try {
            this.description = jsonObject.getString("description");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.amount = jsonObject.getString("amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CustomerInvoiceDetail> CREATOR = new Creator<CustomerInvoiceDetail>() {
        @Override
        public CustomerInvoiceDetail createFromParcel(Parcel in) {
            return new CustomerInvoiceDetail(in);
        }

        @Override
        public CustomerInvoiceDetail[] newArray(int size) {
            return new CustomerInvoiceDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(amount);
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getAmount() {
        return amount;
    }
}
