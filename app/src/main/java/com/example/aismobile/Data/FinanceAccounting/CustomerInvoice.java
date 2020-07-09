package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerInvoice implements Parcelable {

    private int sales_order_invoice_id;
    private String sales_order_invoice_number;
    private String sales_order_invoice_description;
    private String job_order_id;
    private String sales_quotation_id;
    private String job_progress_report_id;
    private String due_date;
    private String client_po_number;
    private String sales_order_invoice_status;
    private String payment_late;
    private String grand_total;

    public CustomerInvoice(int sales_order_invoice_id, String sales_order_invoice_number, String sales_order_invoice_description,
                           String job_order_id, String sales_quotation_id, String job_progress_report_id,
                           String due_date, String client_po_number, String sales_order_invoice_status,
                           String payment_late, String grand_total){
        this.sales_order_invoice_id = sales_order_invoice_id;
        this.sales_order_invoice_number = sales_order_invoice_number;
        this.sales_order_invoice_description = sales_order_invoice_description;
        this.job_order_id = job_order_id;
        this.sales_quotation_id = sales_quotation_id;
        this.job_progress_report_id = job_progress_report_id;
        this.due_date = due_date;
        this.client_po_number = client_po_number;
        this.sales_order_invoice_status = sales_order_invoice_status;
        this.payment_late = payment_late;
        this.grand_total = grand_total;
    }
    protected CustomerInvoice(Parcel in) {
        sales_order_invoice_id = in.readInt();
        sales_order_invoice_number = in.readString();
        sales_order_invoice_description = in.readString();
        job_order_id = in.readString();
        sales_quotation_id = in.readString();
        job_progress_report_id = in.readString();
        due_date = in.readString();
        client_po_number = in.readString();
        sales_order_invoice_status = in.readString();
        payment_late = in.readString();
        grand_total = in.readString();
    }

    public CustomerInvoice(JSONObject jsonObject){
        try {
            this.sales_order_invoice_id = jsonObject.getInt("sales_order_invoice_id");
            this.sales_order_invoice_number = jsonObject.getString("sales_order_invoice_number");
            this.sales_order_invoice_description = jsonObject.getString("sales_order_invoice_description");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.sales_quotation_id = jsonObject.getString("sales_quotation_id");
            this.job_progress_report_id = jsonObject.getString("job_progress_report_id");
            this.due_date = jsonObject.getString("due_date");
            this.client_po_number = jsonObject.getString("client_po_number");
            this.sales_order_invoice_status = jsonObject.getString("sales_order_invoice_status");
            this.payment_late = jsonObject.getString("payment_late");
            this.grand_total = jsonObject.getString("grand_total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CustomerInvoice> CREATOR = new Creator<CustomerInvoice>() {
        @Override
        public CustomerInvoice createFromParcel(Parcel in) {
            return new CustomerInvoice(in);
        }

        @Override
        public CustomerInvoice[] newArray(int size) {
            return new CustomerInvoice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sales_order_invoice_id);
        dest.writeString(sales_order_invoice_number);
        dest.writeString(sales_order_invoice_description);
        dest.writeString(job_order_id);
        dest.writeString(sales_quotation_id);
        dest.writeString(job_progress_report_id);
        dest.writeString(due_date);
        dest.writeString(client_po_number);
        dest.writeString(sales_order_invoice_status);
        dest.writeString(payment_late);
        dest.writeString(grand_total);
    }

    public int getSales_order_invoice_id() {
        return sales_order_invoice_id;
    }

    public String getSales_order_invoice_number() {
        return sales_order_invoice_number;
    }

    public String getSales_order_invoice_description() {
        return sales_order_invoice_description;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getSales_quotation_id() {
        return sales_quotation_id;
    }

    public String getJob_progress_report_id() {
        return job_progress_report_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getClient_po_number() {
        return client_po_number;
    }

    public String getSales_order_invoice_status() {
        return sales_order_invoice_status;
    }

    public String getPayment_late() {
        return payment_late;
    }

    public String getGrand_total() {
        return grand_total;
    }
}
