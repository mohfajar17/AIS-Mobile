package com.asukacorp.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyDetInvoice implements Parcelable {
    private String invoice_date;
    private String sales_order_invoice_number;
    private String due_date;
    private String service_amount;
    private String service_ppn;
    private String pph;
    private String adjustment_value;
    private String sales_order_invoice_status;

    protected CompanyDetInvoice(Parcel in) {
        invoice_date = in.readString();
        sales_order_invoice_number = in.readString();
        due_date = in.readString();
        service_amount = in.readString();
        service_ppn = in.readString();
        pph = in.readString();
        adjustment_value = in.readString();
        sales_order_invoice_status = in.readString();
    }

    public CompanyDetInvoice(JSONObject jsonObject){
        try {
            this.invoice_date = jsonObject.getString("invoice_date");
            this.sales_order_invoice_number = jsonObject.getString("sales_order_invoice_number");
            this.due_date = jsonObject.getString("due_date");
            this.service_amount = jsonObject.getString("service_amount");
            this.service_ppn = jsonObject.getString("service_ppn");
            this.pph = jsonObject.getString("pph");
            this.adjustment_value = jsonObject.getString("adjustment_value");
            this.sales_order_invoice_status = jsonObject.getString("sales_order_invoice_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CompanyDetInvoice> CREATOR = new Creator<CompanyDetInvoice>() {
        @Override
        public CompanyDetInvoice createFromParcel(Parcel in) {
            return new CompanyDetInvoice(in);
        }

        @Override
        public CompanyDetInvoice[] newArray(int size) {
            return new CompanyDetInvoice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(invoice_date);
        dest.writeString(sales_order_invoice_number);
        dest.writeString(due_date);
        dest.writeString(service_amount);
        dest.writeString(service_ppn);
        dest.writeString(pph);
        dest.writeString(adjustment_value);
        dest.writeString(sales_order_invoice_status);
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public String getSales_order_invoice_number() {
        return sales_order_invoice_number;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getService_amount() {
        return service_amount;
    }

    public String getService_ppn() {
        return service_ppn;
    }

    public String getPph() {
        return pph;
    }

    public String getAdjustment_value() {
        return adjustment_value;
    }

    public String getSales_order_invoice_status() {
        return sales_order_invoice_status;
    }
}
