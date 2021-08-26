package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoInvoice implements Parcelable {

    private int sales_order_invoice_id;
    private String sales_order_invoice_number;
    private String invoice_date;
    private String sales_order_invoice_description;
    private String job_progress_report_number;
    private String service_amount;
    private String status;
    private String payment_later;

    public JoInvoice(int sales_order_invoice_id, String sales_order_invoice_number, String invoice_date,
                     String sales_order_invoice_description, String job_progress_report_number, String service_amount,
                     String status, String payment_later){
        this.sales_order_invoice_id = sales_order_invoice_id;
        this.sales_order_invoice_number = sales_order_invoice_number;
        this.invoice_date = invoice_date;
        this.sales_order_invoice_description = sales_order_invoice_description;
        this.job_progress_report_number = job_progress_report_number;
        this.service_amount = service_amount;
        this.status = status;
        this.payment_later = payment_later;
    }

    protected JoInvoice(Parcel in) {
        sales_order_invoice_id = in.readInt();
        sales_order_invoice_number = in.readString();
        invoice_date = in.readString();
        sales_order_invoice_description = in.readString();
        job_progress_report_number = in.readString();
        service_amount = in.readString();
        status = in.readString();
        payment_later = in.readString();
    }

    public JoInvoice(JSONObject jsonObject){
        try {
            this.sales_order_invoice_id = jsonObject.getInt("sales_order_invoice_id");
            this.sales_order_invoice_number = jsonObject.getString("sales_order_invoice_number");
            this.invoice_date = jsonObject.getString("invoice_date");
            this.sales_order_invoice_description = jsonObject.getString("sales_order_invoice_description");
            this.job_progress_report_number = jsonObject.getString("job_progress_report_number");
            this.service_amount = jsonObject.getString("service_amount");
            this.status = jsonObject.getString("status");
            this.payment_later = jsonObject.getString("payment_later");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoInvoice> CREATOR = new Creator<JoInvoice>() {
        @Override
        public JoInvoice createFromParcel(Parcel in) {
            return new JoInvoice(in);
        }

        @Override
        public JoInvoice[] newArray(int size) {
            return new JoInvoice[size];
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
        dest.writeString(invoice_date);
        dest.writeString(sales_order_invoice_description);
        dest.writeString(job_progress_report_number);
        dest.writeString(service_amount);
        dest.writeString(status);
        dest.writeString(payment_later);
    }

    public int getSales_order_invoice_id() {
        return sales_order_invoice_id;
    }

    public String getSales_order_invoice_number() {
        return sales_order_invoice_number;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public String getSales_order_invoice_description() {
        return sales_order_invoice_description;
    }

    public String getJob_progress_report_number() {
        return job_progress_report_number;
    }

    public String getService_amount() {
        return service_amount;
    }

    public String getStatus() {
        return status;
    }

    public String getPayment_later() {
        return payment_later;
    }
}
