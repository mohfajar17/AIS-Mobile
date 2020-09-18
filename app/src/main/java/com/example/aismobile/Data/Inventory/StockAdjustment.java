package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class StockAdjustment implements Parcelable {

    private int stock_adjustment_id;
    private String adjustment_number;
    private String adjustment_date;
    private String short_description;
    private String notes;
    private String approval_by;
    private String approval_date;
    private String approval_notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public StockAdjustment (int stock_adjustment_id, String adjustment_number, String adjustment_date,
                            String short_description, String notes, String approval_by){
        this.stock_adjustment_id = stock_adjustment_id;
        this.adjustment_number = adjustment_number;
        this.adjustment_date = adjustment_date;
        this.short_description = short_description;
        this.notes = notes;
        this.approval_by = approval_by;
    }
    protected StockAdjustment(Parcel in) {
        stock_adjustment_id = in.readInt();
        adjustment_number = in.readString();
        adjustment_date = in.readString();
        short_description = in.readString();
        notes = in.readString();
        approval_by = in.readString();
        approval_date = in.readString();
        approval_notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public StockAdjustment(JSONObject jsonObject){
        try {
            this.stock_adjustment_id = jsonObject.getInt("stock_adjustment_id");
            this.adjustment_number = jsonObject.getString("adjustment_number");
            this.adjustment_date = jsonObject.getString("adjustment_date");
            this.short_description = jsonObject.getString("short_description");
            this.notes = jsonObject.getString("notes");
            this.approval_by = jsonObject.getString("approval_by");
            this.approval_date = jsonObject.getString("approval_date");
            this.approval_notes = jsonObject.getString("approval_notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<StockAdjustment> CREATOR = new Creator<StockAdjustment>() {
        @Override
        public StockAdjustment createFromParcel(Parcel in) {
            return new StockAdjustment(in);
        }

        @Override
        public StockAdjustment[] newArray(int size) {
            return new StockAdjustment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stock_adjustment_id);
        dest.writeString(adjustment_number);
        dest.writeString(adjustment_date);
        dest.writeString(short_description);
        dest.writeString(notes);
        dest.writeString(approval_by);
        dest.writeString(approval_date);
        dest.writeString(approval_notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getStock_adjustment_id() {
        return stock_adjustment_id;
    }

    public String getAdjustment_number() {
        return adjustment_number;
    }

    public String getAdjustment_date() {
        return adjustment_date;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getNotes() {
        return notes;
    }

    public String getApproval_by() {
        return approval_by;
    }

    public String getApproval_date() {
        return approval_date;
    }

    public String getApproval_notes() {
        return approval_notes;
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
