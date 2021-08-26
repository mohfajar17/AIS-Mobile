package com.asukacorp.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SupplierDetail implements Parcelable {
    private String order_date;
    private String order_number;
    private String amount;
    private String supplier_invoice_date;
    private String supplier_invoice_number;
    private String grand_total;
    private String supplier_invoice_status;
    private String payment_date;

    protected SupplierDetail(Parcel in) {
        order_date = in.readString();
        order_number = in.readString();
        amount = in.readString();
        supplier_invoice_date = in.readString();
        supplier_invoice_number = in.readString();
        grand_total = in.readString();
        supplier_invoice_status = in.readString();
        payment_date = in.readString();
    }

    public SupplierDetail(JSONObject jsonObject){
        try {
            this.order_date = jsonObject.getString("order_date");
            this.order_number = jsonObject.getString("order_number");
            this.amount = jsonObject.getString("amount");
            this.supplier_invoice_date = jsonObject.getString("supplier_invoice_date");
            this.supplier_invoice_number = jsonObject.getString("supplier_invoice_number");
            this.grand_total = jsonObject.getString("grand_total");
            this.supplier_invoice_status = jsonObject.getString("supplier_invoice_status");
            this.payment_date = jsonObject.getString("payment_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SupplierDetail> CREATOR = new Creator<SupplierDetail>() {
        @Override
        public SupplierDetail createFromParcel(Parcel in) {
            return new SupplierDetail(in);
        }

        @Override
        public SupplierDetail[] newArray(int size) {
            return new SupplierDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_date);
        dest.writeString(order_number);
        dest.writeString(amount);
        dest.writeString(supplier_invoice_date);
        dest.writeString(supplier_invoice_number);
        dest.writeString(grand_total);
        dest.writeString(supplier_invoice_status);
        dest.writeString(payment_date);
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_number() {
        return order_number;
    }

    public String getAmount() {
        return amount;
    }

    public String getSupplier_invoice_date() {
        return supplier_invoice_date;
    }

    public String getSupplier_invoice_number() {
        return supplier_invoice_number;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public String getSupplier_invoice_status() {
        return supplier_invoice_status;
    }

    public String getPayment_date() {
        return payment_date;
    }
}
