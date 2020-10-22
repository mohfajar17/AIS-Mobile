package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Monitoring implements Parcelable {

    private int sales_quotation_id;
    private String sales_quotation_number;
    private String company_name;
    private String created_by;
    private String sq_date;
    private String department_name;
    private String description;
    private String amount;
    private String wo_amount;
    private String status;
    private String completion_date;
    private String client_po_number;
    private String sales_quotation_category;
    private String payment_desc;
    private String supervisor;
    private String approval_assign_id;
    private String contact_id;
    private String notes;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String job_order_number;
    private String job_order_description;
    private String begin_date;
    private String end_date;

    public Monitoring(int sales_quotation_id, String sales_quotation_number, String company_name,
                      String created_by, String sq_date, String department_name, String description,
                      String amount, String wo_amount, String status){
        this.sales_quotation_id = sales_quotation_id;
        this.sales_quotation_number = sales_quotation_number;
        this.company_name = company_name;
        this.created_by = created_by;
        this.sq_date = sq_date;
        this.department_name = department_name;
        this.description = description;
        this.amount = amount;
        this.wo_amount = wo_amount;
        this.status = status;
    }

    protected Monitoring(Parcel in) {
        sales_quotation_id = in.readInt();
        sales_quotation_number = in.readString();
        company_name = in.readString();
        created_by = in.readString();
        sq_date = in.readString();
        department_name = in.readString();
        description = in.readString();
        amount = in.readString();
        wo_amount = in.readString();
        status = in.readString();
        completion_date = in.readString();
        client_po_number = in.readString();
        sales_quotation_category = in.readString();
        payment_desc = in.readString();
        supervisor = in.readString();
        approval_assign_id = in.readString();
        contact_id = in.readString();
        notes = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        job_order_number = in.readString();
        job_order_description = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
    }

    public Monitoring(JSONObject jsonObject){
        try {
            this.sales_quotation_id = jsonObject.getInt("sales_quotation_id");
            this.sales_quotation_number = jsonObject.getString("sales_quotation_number");
            this.company_name = jsonObject.getString("company_name");
            this.created_by = jsonObject.getString("created_by");
            this.sq_date = jsonObject.getString("sq_date");
            this.department_name = jsonObject.getString("department_name");
            this.description = jsonObject.getString("description");
            this.amount = jsonObject.getString("amount");
            this.wo_amount = jsonObject.getString("wo_amount");
            this.status = jsonObject.getString("status");
            this.completion_date = jsonObject.getString("completion_date");
            this.client_po_number = jsonObject.getString("client_po_number");
            this.sales_quotation_category = jsonObject.getString("sales_quotation_category");
            this.payment_desc = jsonObject.getString("payment_desc");
            this.supervisor = jsonObject.getString("supervisor");
            this.approval_assign_id = jsonObject.getString("approval_assign_id");
            this.contact_id = jsonObject.getString("contact_id");
            this.notes = jsonObject.getString("notes");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Monitoring> CREATOR = new Creator<Monitoring>() {
        @Override
        public Monitoring createFromParcel(Parcel in) {
            return new Monitoring(in);
        }

        @Override
        public Monitoring[] newArray(int size) {
            return new Monitoring[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sales_quotation_id);
        dest.writeString(sales_quotation_number);
        dest.writeString(company_name);
        dest.writeString(created_by);
        dest.writeString(sq_date);
        dest.writeString(department_name);
        dest.writeString(description);
        dest.writeString(amount);
        dest.writeString(wo_amount);
        dest.writeString(status);
        dest.writeString(completion_date);
        dest.writeString(client_po_number);
        dest.writeString(sales_quotation_category);
        dest.writeString(payment_desc);
        dest.writeString(supervisor);
        dest.writeString(approval_assign_id);
        dest.writeString(contact_id);
        dest.writeString(notes);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(job_order_number);
        dest.writeString(job_order_description);
        dest.writeString(begin_date);
        dest.writeString(end_date);
    }

    public int getSales_quotation_id() {
        return sales_quotation_id;
    }

    public String getSales_quotation_number() {
        return sales_quotation_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getSq_date() {
        return sq_date;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getWo_amount() {
        return wo_amount;
    }

    public String getStatus() {
        return status;
    }

    public String getCompletion_date() {
        return completion_date;
    }

    public String getClient_po_number() {
        return client_po_number;
    }

    public String getSales_quotation_category() {
        return sales_quotation_category;
    }

    public String getPayment_desc() {
        return payment_desc;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getApproval_assign_id() {
        return approval_assign_id;
    }

    public String getContact_id() {
        return contact_id;
    }

    public String getNotes() {
        return notes;
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

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
