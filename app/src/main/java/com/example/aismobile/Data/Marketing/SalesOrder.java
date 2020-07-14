package com.example.aismobile.Data.Marketing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SalesOrder implements Parcelable {

    private int sales_order_id;
    private String sales_order_number;
    private String short_description;
    private String sales_order_date;
    private String due_date;
    private String status;

    public SalesOrder (int sales_order_id, String sales_order_number, String short_description,
                           String sales_order_date, String due_date, String status){
        this.sales_order_id = sales_order_id;
        this.sales_order_number = sales_order_number;
        this.short_description = short_description;
        this.sales_order_date = sales_order_date;
        this.due_date = due_date;
        this.status = status;
    }

    protected SalesOrder(Parcel in) {
        sales_order_id = in.readInt();
        sales_order_number = in.readString();
        short_description = in.readString();
        sales_order_date = in.readString();
        due_date = in.readString();
        status = in.readString();
    }

    public SalesOrder(JSONObject jsonObject){
        try {
            this.sales_order_id = jsonObject.getInt("sales_order_id");
            this.sales_order_number = jsonObject.getString("sales_order_number");
            this.short_description = jsonObject.getString("short_description");
            this.sales_order_date = jsonObject.getString("sales_order_date");
            this.due_date = jsonObject.getString("due_date");
            this.status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SalesOrder> CREATOR = new Creator<SalesOrder>() {
        @Override
        public SalesOrder createFromParcel(Parcel in) {
            return new SalesOrder(in);
        }

        @Override
        public SalesOrder[] newArray(int size) {
            return new SalesOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sales_order_id);
        dest.writeString(sales_order_number);
        dest.writeString(short_description);
        dest.writeString(sales_order_date);
        dest.writeString(due_date);
        dest.writeString(status);
    }

    public int getSales_order_id() {
        return sales_order_id;
    }

    public String getSales_order_number() {
        return sales_order_number;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getSales_order_date() {
        return sales_order_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getStatus() {
        return status;
    }
}
