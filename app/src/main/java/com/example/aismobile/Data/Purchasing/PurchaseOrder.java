package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseOrder implements Parcelable {
    private int purchase_order_id;
    private String purchase_order_number;
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
    private String delivery_address;
    private String delivery_address2;
    private String approval_assign_id;
    private String notes;
    private String version;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String po_approval1;
    private String po_approval_date1;
    private String po_comment1;
    private String po_approval2;
    private String po_approval_date2;
    private String po_comment2;
    private String po_approval3;
    private String po_approval_date3;
    private String po_comment3;
    private String checked_by;
    private String checked_date;
    private String purchase_archive;
    private String purchase_file_name;
    private String purchase_file_type;
    private String purchase_order_discount_type;
    private String job_order_number;

    public PurchaseOrder(int purchase_order_id, String purchase_order_number, String purchase_order_type_id, String tax_type_id, String purchase_order_status_id,
                         String contract_agreement_id, String supplier_id, String purchase_quotation_number,
                         String purchase_quotation_date, String begin_date, String end_date, String payment_term_id,
                         String payment_desc, String delivery_address, String delivery_address2, String approval_assign_id,
                         String notes, String version, String created_by, String created_date, String modified_by,
                         String modified_date, String po_approval1, String po_approval_date1, String po_comment1,
                         String po_approval2, String po_approval_date2, String po_comment2, String po_approval3,
                         String po_approval_date3, String po_comment3, String checked_by, String checked_date,
                         String purchase_archive, String purchase_file_name, String purchase_file_type,
                         String purchase_order_discount_type, String job_order_number){
        this.purchase_order_id = purchase_order_id;
        this.purchase_order_number = purchase_order_number;
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
        this.delivery_address = delivery_address;
        this.delivery_address2 = delivery_address2;
        this.approval_assign_id = approval_assign_id;
        this.notes = notes;
        this.version = version;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.po_approval1 = po_approval1;
        this.po_approval_date1 = po_approval_date1;
        this.po_comment1 = po_comment1;
        this.po_approval2 = po_approval2;
        this.po_approval_date2 = po_approval_date2;
        this.po_comment2 = po_comment2;
        this.po_approval3 = po_approval3;
        this.po_approval_date3 = po_approval_date3;
        this.po_comment3 = po_comment3;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.purchase_archive = purchase_archive;
        this.purchase_file_name = purchase_file_name;
        this.purchase_file_type = purchase_file_type;
        this.purchase_order_discount_type = purchase_order_discount_type;
        this.job_order_number = job_order_number;
    }

    protected PurchaseOrder(Parcel in) {
        purchase_order_id = in.readInt();
        purchase_order_number = in.readString();
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
        delivery_address = in.readString();
        delivery_address2 = in.readString();
        approval_assign_id = in.readString();
        notes = in.readString();
        version = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        po_approval1 = in.readString();
        po_approval_date1 = in.readString();
        po_comment1 = in.readString();
        po_approval2 = in.readString();
        po_approval_date2 = in.readString();
        po_comment2 = in.readString();
        po_approval3 = in.readString();
        po_approval_date3 = in.readString();
        po_comment3 = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        purchase_archive = in.readString();
        purchase_file_name = in.readString();
        purchase_file_type = in.readString();
        purchase_order_discount_type = in.readString();
        job_order_number = in.readString();
    }

    public PurchaseOrder(JSONObject jsonObject){
        try {
            this.purchase_order_id = jsonObject.getInt("purchase_order_id");
            this.purchase_order_number = jsonObject.getString("purchase_order_number");
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
            this.delivery_address = jsonObject.getString("delivery_address");
            this.delivery_address2 = jsonObject.getString("delivery_address2");
            this.approval_assign_id = jsonObject.getString("approval_assign_id");
            this.notes = jsonObject.getString("notes");
            this.version = jsonObject.getString("version");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.po_approval1 = jsonObject.getString("po_approval1");
            this.po_approval_date1 = jsonObject.getString("po_approval_date1");
            this.po_comment1 = jsonObject.getString("po_comment1");
            this.po_approval2 = jsonObject.getString("po_approval2");
            this.po_approval_date2 = jsonObject.getString("po_approval_date2");
            this.po_comment2 = jsonObject.getString("po_comment2");
            this.po_approval3 = jsonObject.getString("po_approval3");
            this.po_approval_date3 = jsonObject.getString("po_approval_date3");
            this.po_comment3 = jsonObject.getString("po_comment3");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.purchase_archive = jsonObject.getString("purchase_archive");
            this.purchase_file_name = jsonObject.getString("purchase_file_name");
            this.purchase_file_type = jsonObject.getString("purchase_file_type");
            this.purchase_order_discount_type = jsonObject.getString("purchase_order_discount_type");
            this.job_order_number = jsonObject.getString("job_order_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PurchaseOrder> CREATOR = new Creator<PurchaseOrder>() {
        @Override
        public PurchaseOrder createFromParcel(Parcel in) {
            return new PurchaseOrder(in);
        }

        @Override
        public PurchaseOrder[] newArray(int size) {
            return new PurchaseOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(purchase_order_id);
        dest.writeString(purchase_order_number);
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
        dest.writeString(delivery_address);
        dest.writeString(delivery_address2);
        dest.writeString(approval_assign_id);
        dest.writeString(notes);
        dest.writeString(version);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(po_approval1);
        dest.writeString(po_approval_date1);
        dest.writeString(po_comment1);
        dest.writeString(po_approval2);
        dest.writeString(po_approval_date2);
        dest.writeString(po_comment2);
        dest.writeString(po_approval3);
        dest.writeString(po_approval_date3);
        dest.writeString(po_comment3);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(purchase_archive);
        dest.writeString(purchase_file_name);
        dest.writeString(purchase_file_type);
        dest.writeString(purchase_order_discount_type);
        dest.writeString(job_order_number);
    }

    public int getPurchase_order_id() {
        return purchase_order_id;
    }

    public String getPurchase_order_number() {
        return purchase_order_number;
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

    public String getDelivery_address() {
        return delivery_address;
    }

    public String getDelivery_address2() {
        return delivery_address2;
    }

    public String getApproval_assign_id() {
        return approval_assign_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getVersion() {
        return version;
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

    public String getPo_approval2() {
        return po_approval2;
    }

    public String getPo_approval_date2() {
        return po_approval_date2;
    }

    public String getPo_comment2() {
        return po_comment2;
    }

    public String getPo_approval3() {
        return po_approval3;
    }

    public String getPo_approval_date3() {
        return po_approval_date3;
    }

    public String getPo_comment3() {
        return po_comment3;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getPurchase_archive() {
        return purchase_archive;
    }

    public String getPurchase_file_name() {
        return purchase_file_name;
    }

    public String getPurchase_file_type() {
        return purchase_file_type;
    }

    public String getPurchase_order_discount_type() {
        return purchase_order_discount_type;
    }

    public String getJob_order_number() {
        return job_order_number;
    }
}
