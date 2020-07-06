package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ContractAgreement implements Parcelable {

    private int contract_agreement_id;
    private String agreement_number;
    private String related_agreement;
    private String supplier_id;
    private String contact_id;
    private String agreement_date;
    private String begin_date;
    private String end_date;
    private String agreement_archive;
    private String agreement_status;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String agreement_file_name;
    private String agreement_file_type;

    public ContractAgreement(int contract_agreement_id, String agreement_number, String related_agreement,
                             String supplier_id, String contact_id, String agreement_date, String begin_date,
                             String end_date, String agreement_archive, String agreement_status, String notes,
                             String created_by, String created_date, String modified_by, String modified_date,
                             String agreement_file_name, String agreement_file_type){
        this.contract_agreement_id = contract_agreement_id;
        this.agreement_number = agreement_number;
        this.related_agreement = related_agreement;
        this.supplier_id = supplier_id;
        this.contact_id = contact_id;
        this.agreement_date = agreement_date;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.agreement_archive = agreement_archive;
        this.agreement_status = agreement_status;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.agreement_file_name = agreement_file_name;
        this.agreement_file_type = agreement_file_type;
    }

    protected ContractAgreement(Parcel in) {
        contract_agreement_id = in.readInt();
        agreement_number = in.readString();
        related_agreement = in.readString();
        supplier_id = in.readString();
        contact_id = in.readString();
        agreement_date = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        agreement_archive = in.readString();
        agreement_status = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        agreement_file_name = in.readString();
        agreement_file_type = in.readString();
    }

    public ContractAgreement(JSONObject jsonObject){
        try {
            this.contract_agreement_id = jsonObject.getInt("contract_agreement_id");
            this.agreement_number = jsonObject.getString("agreement_number");
            this.related_agreement = jsonObject.getString("related_agreement");
            this.supplier_id = jsonObject.getString("supplier_id");
            this.contact_id = jsonObject.getString("contact_id");
            this.agreement_date = jsonObject.getString("agreement_date");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.agreement_archive = jsonObject.getString("agreement_archive");
            this.agreement_status = jsonObject.getString("agreement_status");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.agreement_file_name = jsonObject.getString("agreement_file_name");
            this.agreement_file_type = jsonObject.getString("agreement_file_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ContractAgreement> CREATOR = new Creator<ContractAgreement>() {
        @Override
        public ContractAgreement createFromParcel(Parcel in) {
            return new ContractAgreement(in);
        }

        @Override
        public ContractAgreement[] newArray(int size) {
            return new ContractAgreement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contract_agreement_id);
        dest.writeString(agreement_number);
        dest.writeString(related_agreement);
        dest.writeString(supplier_id);
        dest.writeString(contact_id);
        dest.writeString(agreement_date);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(agreement_archive);
        dest.writeString(agreement_status);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(agreement_file_name);
        dest.writeString(agreement_file_type);
    }

    public int getContract_agreement_id() {
        return contract_agreement_id;
    }

    public String getAgreement_number() {
        return agreement_number;
    }

    public String getRelated_agreement() {
        return related_agreement;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public String getContact_id() {
        return contact_id;
    }

    public String getAgreement_date() {
        return agreement_date;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getAgreement_archive() {
        return agreement_archive;
    }

    public String getAgreement_status() {
        return agreement_status;
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

    public String getAgreement_file_name() {
        return agreement_file_name;
    }

    public String getAgreement_file_type() {
        return agreement_file_type;
    }
}
