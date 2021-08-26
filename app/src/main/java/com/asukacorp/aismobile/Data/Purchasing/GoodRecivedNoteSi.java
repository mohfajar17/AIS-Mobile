package com.asukacorp.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class GoodRecivedNoteSi implements Parcelable {

    private String supplier_invoice_number;
    private String supplier_name;
    private String amount;
    private String discount;
    private String ppn;
    private String supplier_invoice_status;
    private String supplier_invoice_date;

    protected GoodRecivedNoteSi(Parcel in) {
        supplier_invoice_number = in.readString();
        supplier_name = in.readString();
        amount = in.readString();
        discount = in.readString();
        ppn = in.readString();
        supplier_invoice_status = in.readString();
        supplier_invoice_date = in.readString();
    }

    public GoodRecivedNoteSi(JSONObject jsonObject){
        try {
            this.supplier_invoice_number = jsonObject.getString("supplier_invoice_number");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.amount = jsonObject.getString("amount");
            this.discount = jsonObject.getString("discount");
            this.ppn = jsonObject.getString("ppn");
            this.supplier_invoice_status = jsonObject.getString("supplier_invoice_status");
            this.supplier_invoice_date = jsonObject.getString("supplier_invoice_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<GoodRecivedNoteSi> CREATOR = new Creator<GoodRecivedNoteSi>() {
        @Override
        public GoodRecivedNoteSi createFromParcel(Parcel in) {
            return new GoodRecivedNoteSi(in);
        }

        @Override
        public GoodRecivedNoteSi[] newArray(int size) {
            return new GoodRecivedNoteSi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(supplier_invoice_number);
        dest.writeString(supplier_name);
        dest.writeString(amount);
        dest.writeString(discount);
        dest.writeString(ppn);
        dest.writeString(supplier_invoice_status);
        dest.writeString(supplier_invoice_date);
    }

    public String getSupplier_invoice_number() {
        return supplier_invoice_number;
    }

    public String getSupplier_name() {
        return supplier_name;
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

    public String getSupplier_invoice_status() {
        return supplier_invoice_status;
    }

    public String getSupplier_invoice_date() {
        return supplier_invoice_date;
    }
}
