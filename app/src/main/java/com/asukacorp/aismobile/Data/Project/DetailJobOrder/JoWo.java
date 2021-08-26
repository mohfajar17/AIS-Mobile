package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoWo implements Parcelable {

    private String purchase_service_number;
    private String work_order_id;
    private String purchase_service_id;
    private String item_name;
    private String wo_notes;
    private String quantity;
    private String unit_abbr;
    private String unit_price;
    private String work_order_number;
    private String discount;
    private String WO_Date;

    public JoWo(String purchase_service_number, String work_order_id, String purchase_service_id, String item_name,
                String quantity, String wo_notes, String unit_abbr, String unit_price, String work_order_number,
                String discount, String WO_Date){
        this.purchase_service_number = purchase_service_number;
        this.work_order_id = work_order_id;
        this.purchase_service_id = purchase_service_id;
        this.item_name = item_name;
        this.wo_notes = wo_notes;
        this.quantity = quantity;
        this.unit_abbr = unit_abbr;
        this.unit_price = unit_price;
        this.work_order_number = work_order_number;
        this.discount = discount;
        this.WO_Date = WO_Date;
    }

    protected JoWo(Parcel in) {
        purchase_service_number = in.readString();
        work_order_id = in.readString();
        purchase_service_id = in.readString();
        item_name = in.readString();
        wo_notes = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price = in.readString();
        work_order_number = in.readString();
        discount = in.readString();
        WO_Date = in.readString();
    }

    public JoWo(JSONObject jsonObject){
        try {
            this.purchase_service_number = jsonObject.getString("purchase_service_number");
            this.work_order_id = jsonObject.getString("work_order_id");
            this.purchase_service_id = jsonObject.getString("purchase_service_id");
            this.item_name = jsonObject.getString("item_name");
            this.wo_notes = jsonObject.getString("wo_notes");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price = jsonObject.getString("unit_price");
            this.work_order_number = jsonObject.getString("work_order_number");
            this.discount = jsonObject.getString("discount");
            this.WO_Date = jsonObject.getString("WO_Date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoWo> CREATOR = new Creator<JoWo>() {
        @Override
        public JoWo createFromParcel(Parcel in) {
            return new JoWo(in);
        }

        @Override
        public JoWo[] newArray(int size) {
            return new JoWo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(purchase_service_number);
        dest.writeString(work_order_id);
        dest.writeString(purchase_service_id);
        dest.writeString(item_name);
        dest.writeString(wo_notes);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price);
        dest.writeString(work_order_number);
        dest.writeString(discount);
        dest.writeString(WO_Date);
    }

    public String getPurchase_service_number() {
        return purchase_service_number;
    }

    public String getWork_order_id() {
        return work_order_id;
    }

    public String getPurchase_service_id() {
        return purchase_service_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getWo_notes() {
        return wo_notes;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public String getWork_order_number() {
        return work_order_number;
    }

    public String getDiscount() {
        return discount;
    }

    public String getWO_Date() {
        return WO_Date;
    }
}
