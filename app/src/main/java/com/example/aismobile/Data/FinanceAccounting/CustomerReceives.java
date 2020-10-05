package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerReceives implements Parcelable {

    private int customer_receive_id;
    private String customer_receive_number;
    private String customer_receive_date;
    private String receipt_number;
    private String ekspedisi_id;
    private String company_name;
    private String delivery_date;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    protected CustomerReceives(Parcel in) {
        customer_receive_id = in.readInt();
        customer_receive_number = in.readString();
        customer_receive_date = in.readString();
        receipt_number = in.readString();
        ekspedisi_id = in.readString();
        company_name = in.readString();
        delivery_date = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public CustomerReceives(JSONObject jsonObject){
        try {
            this.customer_receive_id = jsonObject.getInt("customer_receive_id");
            this.customer_receive_number = jsonObject.getString("customer_receive_number");
            this.customer_receive_date = jsonObject.getString("customer_receive_date");
            this.receipt_number = jsonObject.getString("receipt_number");
            this.ekspedisi_id = jsonObject.getString("ekspedisi_id");
            this.company_name = jsonObject.getString("company_name");
            this.delivery_date = jsonObject.getString("delivery_date");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CustomerReceives> CREATOR = new Creator<CustomerReceives>() {
        @Override
        public CustomerReceives createFromParcel(Parcel in) {
            return new CustomerReceives(in);
        }

        @Override
        public CustomerReceives[] newArray(int size) {
            return new CustomerReceives[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(customer_receive_id);
        dest.writeString(customer_receive_number);
        dest.writeString(customer_receive_date);
        dest.writeString(receipt_number);
        dest.writeString(ekspedisi_id);
        dest.writeString(company_name);
        dest.writeString(delivery_date);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getCustomer_receive_id() {
        return customer_receive_id;
    }

    public String getCustomer_receive_number() {
        return customer_receive_number;
    }

    public String getCustomer_receive_date() {
        return customer_receive_date;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public String getEkspedisi_id() {
        return ekspedisi_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getDelivery_date() {
        return delivery_date;
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
}
