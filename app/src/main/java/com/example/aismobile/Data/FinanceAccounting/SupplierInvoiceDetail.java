package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SupplierInvoiceDetail implements Parcelable {
    private String grn;
    private String services_receipt;
    private String work_handover;
    private String cash_on_delivery;
    private String supplier_name;
    private String notes;
    private String amount;
    private String discount;
    private String ppn;

    protected SupplierInvoiceDetail(Parcel in) {
        grn = in.readString();
        services_receipt = in.readString();
        work_handover = in.readString();
        cash_on_delivery = in.readString();
        supplier_name = in.readString();
        notes = in.readString();
        amount = in.readString();
        discount = in.readString();
        ppn = in.readString();
    }

    public SupplierInvoiceDetail(JSONObject jsonObject){
        try {
            this.grn = jsonObject.getString("grn");
            this.grn = jsonObject.getString("services_receipt");
            this.work_handover = jsonObject.getString("work_handover");
            this.cash_on_delivery = jsonObject.getString("cash_on_delivery");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.notes = jsonObject.getString("notes");
            this.amount = jsonObject.getString("amount");
            this.discount = jsonObject.getString("discount");
            this.ppn = jsonObject.getString("ppn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SupplierInvoiceDetail> CREATOR = new Creator<SupplierInvoiceDetail>() {
        @Override
        public SupplierInvoiceDetail createFromParcel(Parcel in) {
            return new SupplierInvoiceDetail(in);
        }

        @Override
        public SupplierInvoiceDetail[] newArray(int size) {
            return new SupplierInvoiceDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(grn);
        dest.writeString(services_receipt);
        dest.writeString(work_handover);
        dest.writeString(cash_on_delivery);
        dest.writeString(supplier_name);
        dest.writeString(notes);
        dest.writeString(amount);
        dest.writeString(discount);
        dest.writeString(ppn);
    }

    public String getGrn() {
        return grn;
    }

    public String getServices_receipt() {
        return services_receipt;
    }

    public String getWork_handover() {
        return work_handover;
    }

    public String getCash_on_delivery() {
        return cash_on_delivery;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getNotes() {
        return notes;
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
}
