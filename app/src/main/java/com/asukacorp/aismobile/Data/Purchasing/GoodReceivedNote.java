package com.asukacorp.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class GoodReceivedNote implements Parcelable {

    private int grn_id;
    private String grn_number;
    private String receipt_date;
    private String purchase_order_id;
    private String notes;
    private String recognized;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String supplier_name;

    public GoodReceivedNote(int grn_id, String grn_number, String receipt_date, String purchase_order_id,
                            String notes, String recognized, String created_by, String created_date,
                            String modified_by, String modified_date, String supplier_name){
        this.grn_id = grn_id;
        this.grn_number = grn_number;
        this.receipt_date = receipt_date;
        this.purchase_order_id = purchase_order_id;
        this.notes = notes;
        this.recognized = recognized;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.supplier_name = supplier_name;
    }

    protected GoodReceivedNote(Parcel in) {
        grn_id = in.readInt();
        grn_number = in.readString();
        receipt_date = in.readString();
        purchase_order_id = in.readString();
        notes = in.readString();
        recognized = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        supplier_name = in.readString();
    }

    public GoodReceivedNote(JSONObject jsonObject){
        try {
            this.grn_id = jsonObject.getInt("grn_id");
            this.grn_number = jsonObject.getString("grn_number");
            this.receipt_date = jsonObject.getString("receipt_date");
            this.purchase_order_id = jsonObject.getString("purchase_order_id");
            this.notes = jsonObject.getString("notes");
            this.recognized = jsonObject.getString("recognized");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.supplier_name = jsonObject.getString("supplier_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<GoodReceivedNote> CREATOR = new Creator<GoodReceivedNote>() {
        @Override
        public GoodReceivedNote createFromParcel(Parcel in) {
            return new GoodReceivedNote(in);
        }

        @Override
        public GoodReceivedNote[] newArray(int size) {
            return new GoodReceivedNote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(grn_id);
        dest.writeString(grn_number);
        dest.writeString(receipt_date);
        dest.writeString(purchase_order_id);
        dest.writeString(notes);
        dest.writeString(recognized);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(supplier_name);
    }

    public int getGrn_id() {
        return grn_id;
    }

    public String getGrn_number() {
        return grn_number;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public String getPurchase_order_id() {
        return purchase_order_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getRecognized() {
        return recognized;
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

    public String getSupplier_name() {
        return supplier_name;
    }
}
