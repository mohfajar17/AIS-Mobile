package com.asukacorp.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkHandover implements Parcelable {

    private int work_handover_id;
    private String work_handover_number;
    private String receipt_date;
    private String purchase_service_id;
    private String recognized;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String supplier_name;

    public WorkHandover(int work_handover_id, String work_handover_number, String receipt_date, String purchase_service_id,
                        String recognized, String notes, String created_by, String created_date, String modified_by,
                        String modified_date, String supplier_name){
        this.work_handover_id = work_handover_id;
        this.work_handover_number = work_handover_number;
        this.receipt_date = receipt_date;
        this.purchase_service_id = purchase_service_id;
        this.recognized = recognized;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.supplier_name = supplier_name;
    }

    protected WorkHandover(Parcel in) {
        work_handover_id = in.readInt();
        work_handover_number = in.readString();
        receipt_date = in.readString();
        purchase_service_id = in.readString();
        recognized = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        supplier_name = in.readString();
    }

    public WorkHandover(JSONObject jsonObject){
        try {
            this.work_handover_id = jsonObject.getInt("work_handover_id");
            this.work_handover_number = jsonObject.getString("work_handover_number");
            this.receipt_date = jsonObject.getString("receipt_date");
            this.purchase_service_id = jsonObject.getString("purchase_service_id");
            this.recognized = jsonObject.getString("recognized");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.supplier_name = jsonObject.getString("supplier_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkHandover> CREATOR = new Creator<WorkHandover>() {
        @Override
        public WorkHandover createFromParcel(Parcel in) {
            return new WorkHandover(in);
        }

        @Override
        public WorkHandover[] newArray(int size) {
            return new WorkHandover[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(work_handover_id);
        dest.writeString(work_handover_number);
        dest.writeString(receipt_date);
        dest.writeString(purchase_service_id);
        dest.writeString(recognized);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(supplier_name);
    }

    public int getWork_handover_id() {
        return work_handover_id;
    }

    public String getWork_handover_number() {
        return work_handover_number;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public String getPurchase_service_id() {
        return purchase_service_id;
    }

    public String getRecognized() {
        return recognized;
    }

    public String getCreated_by() {
        return created_by;
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

    public String getSupplier_name() {
        return supplier_name;
    }
}
