package com.asukacorp.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SupplierInvoice implements Parcelable {

    private int supplier_invoice_id;
    private String supplier_invoice_number;
    private String supplier_name;
    private String supplier_invoice_date;
    private String invoice_receipt_date;
    private String due_date;
    private String payment_date;
    private String supplier_invoice_status;
    private String late_days;
    private String TotalSI;
    private String transaction_number;
    private String category;
    private String tax_number;
    private String number_pieces_of_evidence;
    private String date_pieces_of_evidence;
    private String schedule_date;
    private String bank_transaction_type_name;
    private String amount;
    private String discount;
    private String ppn;
    private String pph;
    private String stamp;
    private String adjustment_value;
    private String bank_transaction_number;
    private String supplier_invoice_file_name;
    private String supplier_invoice_description;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public SupplierInvoice(int supplier_invoice_id, String supplier_invoice_number, String supplier_name,
                           String supplier_invoice_date, String invoice_receipt_date, String due_date,
                           String payment_date, String supplier_invoice_status, String late_days, String TotalSI){
        this.supplier_invoice_id = supplier_invoice_id;
        this.supplier_invoice_number = supplier_invoice_number;
        this.supplier_name = supplier_name;
        this.supplier_invoice_date = supplier_invoice_date;
        this.invoice_receipt_date = invoice_receipt_date;
        this.due_date = due_date;
        this.payment_date = payment_date;
        this.supplier_invoice_status = supplier_invoice_status;
        this.late_days = late_days;
        this.TotalSI = TotalSI;
    }

    protected SupplierInvoice(Parcel in) {
        supplier_invoice_id = in.readInt();
        supplier_invoice_number = in.readString();
        supplier_name = in.readString();
        supplier_invoice_date = in.readString();
        invoice_receipt_date = in.readString();
        due_date = in.readString();
        payment_date = in.readString();
        supplier_invoice_status = in.readString();
        late_days = in.readString();
        TotalSI = in.readString();
        transaction_number = in.readString();
        category = in.readString();
        tax_number = in.readString();
        number_pieces_of_evidence = in.readString();
        date_pieces_of_evidence = in.readString();
        schedule_date = in.readString();
        bank_transaction_type_name = in.readString();
        amount = in.readString();
        discount = in.readString();
        ppn = in.readString();
        pph = in.readString();
        stamp = in.readString();
        adjustment_value = in.readString();
        bank_transaction_number = in.readString();
        supplier_invoice_file_name = in.readString();
        supplier_invoice_description = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public SupplierInvoice(JSONObject jsonObject){
        try {
            this.supplier_invoice_id = jsonObject.getInt("supplier_invoice_id");
            this.supplier_invoice_number = jsonObject.getString("supplier_invoice_number");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.supplier_invoice_date = jsonObject.getString("supplier_invoice_date");
            this.invoice_receipt_date = jsonObject.getString("invoice_receipt_date");
            this.due_date = jsonObject.getString("due_date");
            this.payment_date = jsonObject.getString("payment_date");
            this.supplier_invoice_status = jsonObject.getString("supplier_invoice_status");
            this.late_days = jsonObject.getString("late_days");
            this.TotalSI = jsonObject.getString("TotalSI");
            this.transaction_number = jsonObject.getString("transaction_number");
            this.category = jsonObject.getString("category");
            this.tax_number = jsonObject.getString("tax_number");
            this.number_pieces_of_evidence = jsonObject.getString("number_pieces_of_evidence");
            this.date_pieces_of_evidence = jsonObject.getString("date_pieces_of_evidence");
            this.schedule_date = jsonObject.getString("schedule_date");
            this.bank_transaction_type_name = jsonObject.getString("bank_transaction_type_name");
            this.amount = jsonObject.getString("amount");
            this.discount = jsonObject.getString("discount");
            this.ppn = jsonObject.getString("ppn");
            this.pph = jsonObject.getString("pph");
            this.stamp = jsonObject.getString("stamp");
            this.adjustment_value = jsonObject.getString("adjustment_value");
            this.bank_transaction_number = jsonObject.getString("bank_transaction_number");
            this.supplier_invoice_file_name = jsonObject.getString("supplier_invoice_file_name");
            this.supplier_invoice_description = jsonObject.getString("supplier_invoice_description");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SupplierInvoice> CREATOR = new Creator<SupplierInvoice>() {
        @Override
        public SupplierInvoice createFromParcel(Parcel in) {
            return new SupplierInvoice(in);
        }

        @Override
        public SupplierInvoice[] newArray(int size) {
            return new SupplierInvoice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(supplier_invoice_id);
        dest.writeString(supplier_invoice_number);
        dest.writeString(supplier_name);
        dest.writeString(supplier_invoice_date);
        dest.writeString(invoice_receipt_date);
        dest.writeString(due_date);
        dest.writeString(payment_date);
        dest.writeString(supplier_invoice_status);
        dest.writeString(late_days);
        dest.writeString(TotalSI);
        dest.writeString(transaction_number);
        dest.writeString(category);
        dest.writeString(tax_number);
        dest.writeString(number_pieces_of_evidence);
        dest.writeString(date_pieces_of_evidence);
        dest.writeString(schedule_date);
        dest.writeString(bank_transaction_type_name);
        dest.writeString(amount);
        dest.writeString(discount);
        dest.writeString(ppn);
        dest.writeString(pph);
        dest.writeString(stamp);
        dest.writeString(adjustment_value);
        dest.writeString(bank_transaction_number);
        dest.writeString(supplier_invoice_file_name);
        dest.writeString(supplier_invoice_description);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getSupplier_invoice_id() {
        return supplier_invoice_id;
    }

    public String getSupplier_invoice_number() {
        return supplier_invoice_number;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getSupplier_invoice_date() {
        return supplier_invoice_date;
    }

    public String getInvoice_receipt_date() {
        return invoice_receipt_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public String getSupplier_invoice_status() {
        return supplier_invoice_status;
    }

    public String getLate_days() {
        return late_days;
    }

    public String getTotalSI() {
        return TotalSI;
    }

    public String getTransaction_number() {
        return transaction_number;
    }

    public String getCategory() {
        return category;
    }

    public String getTax_number() {
        return tax_number;
    }

    public String getNumber_pieces_of_evidence() {
        return number_pieces_of_evidence;
    }

    public String getDate_pieces_of_evidence() {
        return date_pieces_of_evidence;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public String getBank_transaction_type_name() {
        return bank_transaction_type_name;
    }

    public String getAmount() {
        return amount;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPpn() {
        return ppn;
    }

    public String getPph() {
        return pph;
    }

    public String getStamp() {
        return stamp;
    }

    public String getAdjustment_value() {
        return adjustment_value;
    }

    public String getBank_transaction_number() {
        return bank_transaction_number;
    }

    public String getSupplier_invoice_file_name() {
        return supplier_invoice_file_name;
    }

    public String getSupplier_invoice_description() {
        return supplier_invoice_description;
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
