package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkCompletion implements Parcelable {

    private int job_progress_report_id;
    private String job_progress_report_number;
    private String job_order_id;
    private String job_order_description;
    private String sales_quotation_id;
    private String company_id;
    private String progress_percentage;
    private String start_work;
    private String end_work;
    private String payment_term_id;
    private String description;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String prepared_by;
    private String prepared_date;
    private String accepted_by;
    private String accepted_by2;
    private String accepted_by3;
    private String client_po_number;
    private String is_delete;

    public WorkCompletion(int job_progress_report_id, String job_progress_report_number, String job_order_id, String job_order_description, String sales_quotation_id, String company_id,
                    String progress_percentage, String start_work, String end_work, String payment_term_id, String description, String notes,
                    String created_by, String created_date, String modified_by, String modified_date, String prepared_by, String prepared_date,
                    String accepted_by, String accepted_by2, String accepted_by3, String client_po_number, String is_delete) {
        this.job_progress_report_id = job_progress_report_id;
        this.job_progress_report_number = job_progress_report_number;
        this.job_order_id = job_order_id;
        this.job_order_description = job_order_description;
        this.sales_quotation_id = sales_quotation_id;
        this.company_id = company_id;
        this.progress_percentage = progress_percentage;
        this.start_work = start_work;
        this.end_work = end_work;
        this.payment_term_id = payment_term_id;
        this.description = description;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.prepared_by = prepared_by;
        this.prepared_date = prepared_date;
        this.accepted_by = accepted_by;
        this.accepted_by2 = accepted_by2;
        this.accepted_by3 = accepted_by3;
        this.client_po_number = client_po_number;
        this.is_delete = is_delete;
    }

    protected WorkCompletion(Parcel in) {
        job_progress_report_id = in.readInt();
        job_progress_report_number = in.readString();
        job_order_id = in.readString();
        job_order_description = in.readString();
        sales_quotation_id = in.readString();
        company_id = in.readString();
        progress_percentage = in.readString();
        start_work = in.readString();
        end_work = in.readString();
        payment_term_id = in.readString();
        description = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        prepared_by = in.readString();
        prepared_date = in.readString();
        accepted_by = in.readString();
        accepted_by2 = in.readString();
        accepted_by3 = in.readString();
        client_po_number = in.readString();
        is_delete = in.readString();
    }

    public WorkCompletion(JSONObject jsonObject){
        try {
            this.job_progress_report_id = jsonObject.getInt("job_progress_report_id");
            this.job_progress_report_number = jsonObject.getString("job_progress_report_number");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.sales_quotation_id = jsonObject.getString("sales_quotation_id");
            this.company_id = jsonObject.getString("company_id");
            this.progress_percentage = jsonObject.getString("progress_percentage");
            this.start_work = jsonObject.getString("start_work");
            this.end_work = jsonObject.getString("end_work");
            this.payment_term_id = jsonObject.getString("payment_term_id");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.prepared_by = jsonObject.getString("prepared_by");
            this.prepared_date = jsonObject.getString("prepared_date");
            this.accepted_by = jsonObject.getString("accepted_by");
            this.accepted_by2 = jsonObject.getString("accepted_by2");
            this.accepted_by3 = jsonObject.getString("accepted_by3");
            this.client_po_number = jsonObject.getString("client_po_number");
            this.is_delete = jsonObject.getString("is_delete");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkCompletion> CREATOR = new Creator<WorkCompletion>() {
        @Override
        public WorkCompletion createFromParcel(Parcel in) {
            return new WorkCompletion(in);
        }

        @Override
        public WorkCompletion[] newArray(int size) {
            return new WorkCompletion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public WorkCompletion() { }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(job_progress_report_id);
        dest.writeString(job_progress_report_number);
        dest.writeString(job_order_id);
        dest.writeString(job_order_description);
        dest.writeString(sales_quotation_id);
        dest.writeString(company_id);
        dest.writeString(progress_percentage);
        dest.writeString(start_work);
        dest.writeString(end_work);
        dest.writeString(payment_term_id);
        dest.writeString(description);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(prepared_by);
        dest.writeString(prepared_date);
        dest.writeString(accepted_by);
        dest.writeString(accepted_by2);
        dest.writeString(accepted_by3);
        dest.writeString(client_po_number);
        dest.writeString(is_delete);
    }

    public int getJob_progress_report_id() {
        return job_progress_report_id;
    }

    public String getJob_progress_report_number() {
        return job_progress_report_number;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getSales_quotation_id() {
        return sales_quotation_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getProgress_percentage() {
        return progress_percentage;
    }

    public String getStart_work() {
        return start_work;
    }

    public String getEnd_work() {
        return end_work;
    }

    public String getPayment_term_id() {
        return payment_term_id;
    }

    public String getDescription() {
        return description;
    }

    public String getNotes() {
        return notes;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public String getPrepared_by() {
        return prepared_by;
    }

    public String getPrepared_date() {
        return prepared_date;
    }

    public String getAccepted_by() {
        return accepted_by;
    }

    public String getAccepted_by2() {
        return accepted_by2;
    }

    public String getAccepted_by3() {
        return accepted_by3;
    }

    public String getClient_po_number() {
        return client_po_number;
    }

    public String getIs_delete() {
        return is_delete;
    }
}
