package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ServicesReceipt implements Parcelable {

    private int services_receipt_id;
    private String services_receipt_number;
    private String receipt_date;
    private String cash_on_delivery_id;
    private String recognized;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public ServicesReceipt(int services_receipt_id, String services_receipt_number, String receipt_date,
                           String cash_on_delivery_id, String recognized, String notes, String created_by,
                           String created_date, String modified_by, String modified_date){
        this.services_receipt_id = services_receipt_id;
        this.services_receipt_number = services_receipt_number;
        this.receipt_date = receipt_date;
        this.cash_on_delivery_id = cash_on_delivery_id;
        this.recognized = recognized;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    protected ServicesReceipt(Parcel in) {
        services_receipt_id = in.readInt();
        services_receipt_number = in.readString();
        receipt_date = in.readString();
        cash_on_delivery_id = in.readString();
        recognized = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public ServicesReceipt(JSONObject jsonObject){
        try {
            this.services_receipt_id = jsonObject.getInt("services_receipt_id");
            this.services_receipt_number = jsonObject.getString("services_receipt_number");
            this.receipt_date = jsonObject.getString("receipt_date");
            this.cash_on_delivery_id = jsonObject.getString("cash_on_delivery_id");
            this.recognized = jsonObject.getString("recognized");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ServicesReceipt> CREATOR = new Creator<ServicesReceipt>() {
        @Override
        public ServicesReceipt createFromParcel(Parcel in) {
            return new ServicesReceipt(in);
        }

        @Override
        public ServicesReceipt[] newArray(int size) {
            return new ServicesReceipt[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(services_receipt_id);
        dest.writeString(services_receipt_number);
        dest.writeString(receipt_date);
        dest.writeString(cash_on_delivery_id);
        dest.writeString(recognized);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getServices_receipt_id() {
        return services_receipt_id;
    }

    public String getServices_receipt_number() {
        return services_receipt_number;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public String getCash_on_delivery_id() {
        return cash_on_delivery_id;
    }

    public String getRecognized() {
        return recognized;
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
}
