package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseService implements Parcelable {

    private int purchase_service_id;
    private String purchase_service_number;
    private String purchase_order_type_id;
    private String tax_type_id;
    private String purchase_order_status_id;
    private String contract_agreement_id;
    private String supplier_id;
    private String purchase_quotation_number;
    private String purchase_quotation_date;
    private String begin_date;
    private String end_date;
    private String payment_term_id;
    private String payment_desc;
    private String approval_assign_id;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String po_approval1;
    private String po_approval_date1;
    private String po_comment1;
    private String checked_by;
    private String checked_date;
    private String purchase_service_archive;
    private String purchase_service_file_name;
    private String purchase_service_file_type;
    private String purchase_service_discount_type;
    private String tax_type_rate;

    public PurchaseService(int purchase_service_id, String purchase_service_number, String purchase_order_type_id,
                           String tax_type_id, String purchase_order_status_id, String contract_agreement_id,
                           String supplier_id, String purchase_quotation_number, String purchase_quotation_date,
                           String begin_date, String end_date, String payment_term_id, String payment_desc,
                           String approval_assign_id, String notes, String created_by, String created_date,
                           String modified_by, String modified_date, String po_approval1, String po_approval_date1,
                           String po_comment1, String checked_by, String checked_date, String purchase_service_archive,
                           String purchase_service_file_name, String purchase_service_file_type, String purchase_service_discount_type){
        this.purchase_service_id = purchase_service_id;
        this.purchase_service_number = purchase_service_number;
        this.purchase_order_type_id = purchase_order_type_id;
        this.tax_type_id = tax_type_id;
        this.purchase_order_status_id = purchase_order_status_id;
        this.contract_agreement_id = contract_agreement_id;
        this.supplier_id = supplier_id;
        this.purchase_quotation_number = purchase_quotation_number;
        this.purchase_quotation_date = purchase_quotation_date;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.payment_term_id = payment_term_id;
        this.payment_desc = payment_desc;
        this.approval_assign_id = approval_assign_id;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.po_approval1 = po_approval1;
        this.po_approval_date1 = po_approval_date1;
        this.po_comment1 = po_comment1;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.purchase_service_archive = purchase_service_archive;
        this.purchase_service_file_name = purchase_service_file_name;
        this.purchase_service_file_type = purchase_service_file_type;
        this.purchase_service_discount_type = purchase_service_discount_type;
    }

    protected PurchaseService(Parcel in) {
        purchase_service_id = in.readInt();
        purchase_service_number = in.readString();
        purchase_order_type_id = in.readString();
        tax_type_id = in.readString();
        purchase_order_status_id = in.readString();
        contract_agreement_id = in.readString();
        supplier_id = in.readString();
        purchase_quotation_number = in.readString();
        purchase_quotation_date = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        payment_term_id = in.readString();
        payment_desc = in.readString();
        approval_assign_id = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        po_approval1 = in.readString();
        po_approval_date1 = in.readString();
        po_comment1 = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        purchase_service_archive = in.readString();
        purchase_service_file_name = in.readString();
        purchase_service_file_type = in.readString();
        purchase_service_discount_type = in.readString();
        tax_type_rate = in.readString();
    }

    public PurchaseService(JSONObject jsonObject){
        try {
            this.purchase_service_id = jsonObject.getInt("purchase_service_id");
            this.purchase_service_number = jsonObject.getString("purchase_service_number");
            this.purchase_order_type_id = jsonObject.getString("purchase_order_type_id");
            this.tax_type_id = jsonObject.getString("tax_type_id");
            this.purchase_order_status_id = jsonObject.getString("purchase_order_status_id");
            this.contract_agreement_id = jsonObject.getString("contract_agreement_id");
            this.supplier_id = jsonObject.getString("supplier_id");
            this.purchase_quotation_number = jsonObject.getString("purchase_quotation_number");
            this.purchase_quotation_date = jsonObject.getString("purchase_quotation_date");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.payment_term_id = jsonObject.getString("payment_term_id");
            this.payment_desc = jsonObject.getString("payment_desc");
            this.approval_assign_id = jsonObject.getString("approval_assign_id");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.po_approval1 = jsonObject.getString("po_approval1");
            this.po_approval_date1 = jsonObject.getString("po_approval_date1");
            this.po_comment1 = jsonObject.getString("po_comment1");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.purchase_service_archive = jsonObject.getString("purchase_service_archive");
            this.purchase_service_file_name = jsonObject.getString("purchase_service_file_name");
            this.purchase_service_file_type = jsonObject.getString("purchase_service_file_type");
            this.purchase_service_discount_type = jsonObject.getString("purchase_service_discount_type");
            this.tax_type_rate = jsonObject.getString("tax_type_rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PurchaseService> CREATOR = new Creator<PurchaseService>() {
        @Override
        public PurchaseService createFromParcel(Parcel in) {
            return new PurchaseService(in);
        }

        @Override
        public PurchaseService[] newArray(int size) {
            return new PurchaseService[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(purchase_service_id);
        dest.writeString(purchase_service_number);
        dest.writeString(purchase_order_type_id);
        dest.writeString(tax_type_id);
        dest.writeString(purchase_order_status_id);
        dest.writeString(contract_agreement_id);
        dest.writeString(supplier_id);
        dest.writeString(purchase_quotation_number);
        dest.writeString(purchase_quotation_date);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(payment_term_id);
        dest.writeString(payment_desc);
        dest.writeString(approval_assign_id);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(po_approval1);
        dest.writeString(po_approval_date1);
        dest.writeString(po_comment1);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(purchase_service_archive);
        dest.writeString(purchase_service_file_name);
        dest.writeString(purchase_service_file_type);
        dest.writeString(purchase_service_discount_type);
        dest.writeString(tax_type_rate);
    }

    public int getPurchase_service_id() {
        return purchase_service_id;
    }

    public String getPurchase_service_number() {
        return purchase_service_number;
    }

    public String getPurchase_order_type_id() {
        return purchase_order_type_id;
    }

    public String getTax_type_id() {
        return tax_type_id;
    }

    public String getPurchase_order_status_id() {
        return purchase_order_status_id;
    }

    public String getContract_agreement_id() {
        return contract_agreement_id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public String getPurchase_quotation_number() {
        return purchase_quotation_number;
    }

    public String getPurchase_quotation_date() {
        return purchase_quotation_date;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getPayment_term_id() {
        return payment_term_id;
    }

    public String getPayment_desc() {
        return payment_desc;
    }

    public String getApproval_assign_id() {
        return approval_assign_id;
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

    public String getPo_approval1() {
        return po_approval1;
    }

    public String getPo_approval_date1() {
        return po_approval_date1;
    }

    public String getPo_comment1() {
        return po_comment1;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getPurchase_service_archive() {
        return purchase_service_archive;
    }

    public String getPurchase_service_file_name() {
        return purchase_service_file_name;
    }

    public String getPurchase_service_file_type() {
        return purchase_service_file_type;
    }

    public String getPurchase_service_discount_type() {
        return purchase_service_discount_type;
    }

    public String getTax_type_rate() {
        return tax_type_rate;
    }
}
