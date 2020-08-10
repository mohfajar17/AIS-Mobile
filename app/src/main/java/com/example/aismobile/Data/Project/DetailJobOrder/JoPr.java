package com.example.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoPr implements Parcelable {

    private int item_id;
    private String pickup_id;
    private String material_request_id;
    private String item_name;
    private String item_specification;
    private String material_request_number;
    private String unit_abbr;
    private String pickup_number;
    private String quantity_taked;
    private String unit_price;
    private String discount;
    private String purchase_order_number;
    private String quantity_purchase_request;
    private String purchase_order_id;
    private String unit_price_buy;
    private String status;
    private String material_request_detail_id;

    public JoPr(int item_id, String pickup_id, String material_request_id, String item_name, String item_specification,
                String material_request_number, String unit_abbr, String pickup_number, String quantity_taked,
                String unit_price, String discount, String purchase_order_number, String quantity_purchase_request,
                String purchase_order_id, String unit_price_buy, String status, String material_request_detail_id){
        this.item_id = item_id;
        this.pickup_id = pickup_id;
        this.material_request_id = material_request_id;
        this.item_name = item_name;
        this.item_specification = item_specification;
        this.material_request_number = material_request_number;
        this.unit_abbr = unit_abbr;
        this.pickup_number = pickup_number;
        this.quantity_taked = quantity_taked;
        this.unit_price = unit_price;
        this.discount = discount;
        this.purchase_order_number = purchase_order_number;
        this.quantity_purchase_request = quantity_purchase_request;
        this.purchase_order_id = purchase_order_id;
        this.unit_price_buy = unit_price_buy;
        this.status = status;
        this.material_request_detail_id = material_request_detail_id;
    }

    protected JoPr(Parcel in) {
        item_id = in.readInt();
        pickup_id = in.readString();
        material_request_id = in.readString();
        item_name = in.readString();
        item_specification = in.readString();
        material_request_number = in.readString();
        unit_abbr = in.readString();
        pickup_number = in.readString();
        quantity_taked = in.readString();
        unit_price = in.readString();
        discount = in.readString();
        purchase_order_number = in.readString();
        quantity_purchase_request = in.readString();
        purchase_order_id = in.readString();
        unit_price_buy = in.readString();
        status = in.readString();
        material_request_detail_id = in.readString();
    }

    public JoPr(JSONObject jsonObject){
        try {
            this.item_id = jsonObject.getInt("item_id");
            this.pickup_id = jsonObject.getString("pickup_id");
            this.material_request_id = jsonObject.getString("material_request_id");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.material_request_number = jsonObject.getString("material_request_number");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.pickup_number = jsonObject.getString("pickup_number");
            this.quantity_taked = jsonObject.getString("quantity_taked");
            this.unit_price = jsonObject.getString("unit_price");
            this.discount = jsonObject.getString("discount");
            this.purchase_order_number = jsonObject.getString("purchase_order_number");
            this.quantity_purchase_request = jsonObject.getString("quantity_purchase_request");
            this.purchase_order_id = jsonObject.getString("purchase_order_id");
            this.unit_price_buy = jsonObject.getString("unit_price_buy");
            this.status = jsonObject.getString("status");
            this.material_request_detail_id = jsonObject.getString("material_request_detail_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoPr> CREATOR = new Creator<JoPr>() {
        @Override
        public JoPr createFromParcel(Parcel in) {
            return new JoPr(in);
        }

        @Override
        public JoPr[] newArray(int size) {
            return new JoPr[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_id);
        dest.writeString(pickup_id);
        dest.writeString(material_request_id);
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(material_request_number);
        dest.writeString(unit_abbr);
        dest.writeString(pickup_number);
        dest.writeString(quantity_taked);
        dest.writeString(unit_price);
        dest.writeString(discount);
        dest.writeString(purchase_order_number);
        dest.writeString(quantity_purchase_request);
        dest.writeString(purchase_order_id);
        dest.writeString(unit_price_buy);
        dest.writeString(status);
        dest.writeString(material_request_detail_id);
    }

    public int getItem_id() {
        return item_id;
    }

    public String getPickup_id() {
        return pickup_id;
    }

    public String getMaterial_request_id() {
        return material_request_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getMaterial_request_number() {
        return material_request_number;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getPickup_number() {
        return pickup_number;
    }

    public String getQuantity_taked() {
        return quantity_taked;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPurchase_order_number() {
        return purchase_order_number;
    }

    public String getQuantity_purchase_request() {
        return quantity_purchase_request;
    }

    public String getPurchase_order_id() {
        return purchase_order_id;
    }

    public String getUnit_price_buy() {
        return unit_price_buy;
    }

    public String getStatus() {
        return status;
    }

    public String getMaterial_request_detail_id() {
        return material_request_detail_id;
    }
}
