package com.example.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CashOnDelivery implements Parcelable {

    private int cash_on_delivery_id;
    private String cash_on_delivery_number;
    private String job_order_id;
    private String job_order_description;
    private String used_by;
    private String purchase_order_type_id;
    private String tax_type_id;
    private String purchase_order_status_id;
    private String supplier_id;
    private String begin_date;
    private String end_date;
    private String payment_term_id;
    private String payment_desc;
    private String delivery_address;
    private String delivery_address2;
    private String notes;
    private String version;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String approval_assign_id;
    private String approval1;
    private String approval_date1;
    private String approval_comment1;
    private String checked_by;
    private String checked_date;
    private String cod_archive;
    private String cod_file_name;
    private String cod_file_type;
    private String cod_discount_type;
    private String tax_type_rate;

    public CashOnDelivery(int cash_on_delivery_id, String cash_on_delivery_number, String job_order_id, String job_order_description,
                          String used_by, String purchase_order_type_id, String tax_type_id, String purchase_order_status_id,
                          String supplier_id, String begin_date, String end_date, String payment_term_id,
                          String payment_desc, String delivery_address, String delivery_address2, String notes,
                          String version, String created_by, String created_date, String modified_by,
                          String modified_date, String approval_assign_id, String approval1, String approval_date1,
                          String approval_comment1, String checked_by, String checked_date, String cod_archive,
                          String cod_file_name, String cod_file_type, String cod_discount_type){
        this.cash_on_delivery_id = cash_on_delivery_id;
        this.cash_on_delivery_number = cash_on_delivery_number;
        this.job_order_id = job_order_id;
        this.job_order_description = job_order_description;
        this.used_by = used_by;
        this.purchase_order_type_id = purchase_order_type_id;
        this.tax_type_id = tax_type_id;
        this.purchase_order_status_id = purchase_order_status_id;
        this.supplier_id = supplier_id;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.payment_term_id = payment_term_id;
        this.payment_desc = payment_desc;
        this.delivery_address = delivery_address;
        this.delivery_address2 = delivery_address2;
        this.notes = notes;
        this.version = version;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.approval_assign_id = approval_assign_id;
        this.approval1 = approval1;
        this.approval_date1 = approval_date1;
        this.approval_comment1 = approval_comment1;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.cod_archive = cod_archive;
        this.cod_file_name = cod_file_name;
        this.cod_file_type = cod_file_type;
        this.cod_discount_type = cod_discount_type;
    }

    protected CashOnDelivery(Parcel in) {
        cash_on_delivery_id = in.readInt();
        cash_on_delivery_number = in.readString();
        job_order_id = in.readString();
        job_order_description = in.readString();
        used_by = in.readString();
        purchase_order_type_id = in.readString();
        tax_type_id = in.readString();
        purchase_order_status_id = in.readString();
        supplier_id = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        payment_term_id = in.readString();
        payment_desc = in.readString();
        delivery_address = in.readString();
        delivery_address2 = in.readString();
        notes = in.readString();
        version = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        approval_assign_id = in.readString();
        approval1 = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        cod_archive = in.readString();
        cod_file_name = in.readString();
        cod_file_type = in.readString();
        cod_discount_type = in.readString();
        tax_type_rate = in.readString();
    }

    public CashOnDelivery(JSONObject jsonObject){
        try {
            this.cash_on_delivery_id = jsonObject.getInt("cash_on_delivery_id");
            this.cash_on_delivery_number = jsonObject.getString("cash_on_delivery_number");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.used_by = jsonObject.getString("used_by");
            this.purchase_order_type_id = jsonObject.getString("purchase_order_type_id");
            this.tax_type_id = jsonObject.getString("tax_type_id");
            this.purchase_order_status_id = jsonObject.getString("purchase_order_status_id");
            this.supplier_id = jsonObject.getString("supplier_id");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.payment_term_id = jsonObject.getString("payment_term_id");
            this.payment_desc = jsonObject.getString("payment_desc");
            this.delivery_address = jsonObject.getString("delivery_address");
            this.delivery_address2 = jsonObject.getString("delivery_address2");
            this.notes = jsonObject.getString("notes");
            this.version = jsonObject.getString("version");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.approval_assign_id = jsonObject.getString("approval_assign_id");
            this.approval1 = jsonObject.getString("approval1");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.cod_archive = jsonObject.getString("cod_archive");
            this.cod_file_name = jsonObject.getString("cod_file_name");
            this.cod_file_type = jsonObject.getString("cod_file_type");
            this.cod_discount_type = jsonObject.getString("cod_discount_type");
            this.tax_type_rate = jsonObject.getString("tax_type_rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CashOnDelivery> CREATOR = new Creator<CashOnDelivery>() {
        @Override
        public CashOnDelivery createFromParcel(Parcel in) {
            return new CashOnDelivery(in);
        }

        @Override
        public CashOnDelivery[] newArray(int size) {
            return new CashOnDelivery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cash_on_delivery_id);
        dest.writeString(cash_on_delivery_number);
        dest.writeString(job_order_id);
        dest.writeString(job_order_description);
        dest.writeString(used_by);
        dest.writeString(purchase_order_type_id);
        dest.writeString(tax_type_id);
        dest.writeString(purchase_order_status_id);
        dest.writeString(supplier_id);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(payment_term_id);
        dest.writeString(payment_desc);
        dest.writeString(delivery_address);
        dest.writeString(delivery_address2);
        dest.writeString(notes);
        dest.writeString(version);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(approval_assign_id);
        dest.writeString(approval1);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(cod_archive);
        dest.writeString(cod_file_name);
        dest.writeString(cod_file_type);
        dest.writeString(cod_discount_type);
        dest.writeString(tax_type_rate);
    }

    public int getCash_on_delivery_id() {
        return cash_on_delivery_id;
    }

    public String getCash_on_delivery_number() {
        return cash_on_delivery_number;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getUsed_by() {
        return used_by;
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

    public String getSupplier_id() {
        return supplier_id;
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

    public String getApproval_assign_id() {
        return approval_assign_id;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getCod_archive() {
        return cod_archive;
    }

    public String getCod_file_name() {
        return cod_file_name;
    }

    public String getCod_file_type() {
        return cod_file_type;
    }

    public String getCod_discount_type() {
        return cod_discount_type;
    }

    public String getTax_type_rate() {
        return tax_type_rate;
    }
}
