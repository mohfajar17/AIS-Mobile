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
}