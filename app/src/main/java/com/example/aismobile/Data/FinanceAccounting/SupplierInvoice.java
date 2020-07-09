package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SupplierInvoice implements Parcelable {

    private int supplier_invoice_id;
    private String supplier_invoice_number;
    private String supplier_name;
    private String supplier_invoice_date;
    private String invoice_receipt_date;
    private String due_date;
    private String payment_date;
    private String supplier_invoice_status;
    private String late_days;
    private String TotalSI;

    public SupplierInvoice(int supplier_invoice_id, String supplier_invoice_number, String supplier_name,
                           String supplier_invoice_date, String invoice_receipt_date, String due_date,
                           String payment_date, String supplier_invoice_status, String late_days, String TotalSI){
        this.supplier_invoice_id = supplier_invoice_id;
        this.supplier_invoice_number = supplier_invoice_number;
        this.supplier_name = supplier_name;
        this.supplier_invoice_date = supplier_invoice_date;
        this.invoice_receipt_date = invoice_receipt_date;
        this.due_date = due_date;
        this.payment_date = payment_date;
        this.supplier_invoice_status = supplier_invoice_status;
        this.late_days = late_days;
        this.TotalSI = TotalSI;
    }

    protected SupplierInvoice(Parcel in) {
        supplier_invoice_id = in.readInt();
        supplier_invoice_number = in.readString();
        supplier_name = in.readString();
        supplier_invoice_date = in.readString();
        invoice_receipt_date = in.readString();
        due_date = in.readString();
        payment_date = in.readString();
        supplier_invoice_status = in.readString();
        late_days = in.readString();
        TotalSI = in.readString();
    }

    public SupplierInvoice(JSONObject jsonObject){
        try {
            this.supplier_invoice_id = jsonObject.getInt("supplier_invoice_id");
            this.supplier_invoice_number = jsonObject.getString("supplier_invoice_number");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.supplier_invoice_date = jsonObject.getString("supplier_invoice_date");
            this.invoice_receipt_date = jsonObject.getString("invoice_receipt_date");
            this.due_date = jsonObject.getString("due_date");
            this.payment_date = jsonObject.getString("payment_date");
            this.supplier_invoice_status = jsonObject.getString("supplier_invoice_status");
            this.late_days = jsonObject.getString("late_days");
            this.TotalSI = jsonObject.getString("TotalSI");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SupplierInvoice> CREATOR = new Creator<SupplierInvoice>() {
        @Override
        public SupplierInvoice createFromParcel(Parcel in) {
            return new SupplierInvoice(in);
        }

        @Override
        public SupplierInvoice[] newArray(int size) {
            return new SupplierInvoice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(supplier_invoice_id);
        dest.writeString(supplier_invoice_number);
        dest.writeString(supplier_name);
        dest.writeString(supplier_invoice_date);
        dest.writeString(invoice_receipt_date);
        dest.writeString(due_date);
        dest.writeString(payment_date);
        dest.writeString(supplier_invoice_status);
        dest.writeString(late_days);
        dest.writeString(TotalSI);
    }
}
