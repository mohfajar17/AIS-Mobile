package com.example.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoCod implements Parcelable {

    private String cash_on_delivery_number;
    private String approval_date1;
    private String nama_item;
    private String qunty;
    private String abbr;
    private String harga;
    private String discount;

    public JoCod(String cash_on_delivery_number, String approval_date1, String nama_item, String qunty, String abbr,
                String harga, String discount){
        this.cash_on_delivery_number = cash_on_delivery_number;
        this.approval_date1 = approval_date1;
        this.nama_item = nama_item;
        this.qunty = qunty;
        this.abbr = abbr;
        this.harga = harga;
        this.discount = discount;
    }

    protected JoCod(Parcel in) {
        cash_on_delivery_number = in.readString();
        approval_date1 = in.readString();
        nama_item = in.readString();
        qunty = in.readString();
        abbr = in.readString();
        harga = in.readString();
        discount = in.readString();
    }

    public JoCod(JSONObject jsonObject){
        try {
            this.cash_on_delivery_number = jsonObject.getString("cash_on_delivery_number");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.nama_item = jsonObject.getString("nama_item");
            this.qunty = jsonObject.getString("qunty");
            this.abbr = jsonObject.getString("abbr");
            this.harga = jsonObject.getString("harga");
            this.discount = jsonObject.getString("discount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoCod> CREATOR = new Creator<JoCod>() {
        @Override
        public JoCod createFromParcel(Parcel in) {
            return new JoCod(in);
        }

        @Override
        public JoCod[] newArray(int size) {
            return new JoCod[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cash_on_delivery_number);
        dest.writeString(approval_date1);
        dest.writeString(nama_item);
        dest.writeString(qunty);
        dest.writeString(abbr);
        dest.writeString(harga);
        dest.writeString(discount);
    }

    public String getCash_on_delivery_number() {
        return cash_on_delivery_number;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getNama_item() {
        return nama_item;
    }

    public String getQunty() {
        return qunty;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getHarga() {
        return harga;
    }

    public String getDiscount() {
        return discount;
    }
}
