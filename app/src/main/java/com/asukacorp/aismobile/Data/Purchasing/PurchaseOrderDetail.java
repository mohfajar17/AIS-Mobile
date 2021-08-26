package com.asukacorp.aismobile.Data.Purchasing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseOrderDetail implements Parcelable {

    private String material_request_detail_id;
    private String item_name;
    private String item_specification;
    private String notes;
    private String job_order_number;
    private String job_order_description;
    private String status;
    private String quantity;
    private String unit_abbr;
    private String unit_price_buy;
    private String max_budget;
    private String discount;
    private String po_app1;

    protected PurchaseOrderDetail(Parcel in) {
        material_request_detail_id = in.readString();
        item_name = in.readString();
        item_specification = in.readString();
        notes = in.readString();
        job_order_number = in.readString();
        job_order_description = in.readString();
        status = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price_buy = in.readString();
        max_budget = in.readString();
        discount = in.readString();
        po_app1 = in.readString();
    }

    public PurchaseOrderDetail(JSONObject jsonObject){
        try {
            this.material_request_detail_id = jsonObject.getString("material_request_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.notes = jsonObject.getString("notes");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.status = jsonObject.getString("status");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price_buy = jsonObject.getString("unit_price_buy");
            this.max_budget = jsonObject.getString("max_budget");
            this.discount = jsonObject.getString("discount");
            this.po_app1 = jsonObject.getString("po_app1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PurchaseOrderDetail> CREATOR = new Creator<PurchaseOrderDetail>() {
        @Override
        public PurchaseOrderDetail createFromParcel(Parcel in) {
            return new PurchaseOrderDetail(in);
        }

        @Override
        public PurchaseOrderDetail[] newArray(int size) {
            return new PurchaseOrderDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(material_request_detail_id);
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(notes);
        dest.writeString(job_order_number);
        dest.writeString(job_order_description);
        dest.writeString(status);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price_buy);
        dest.writeString(max_budget);
        dest.writeString(discount);
        dest.writeString(po_app1);
    }

    public String getMaterial_request_detail_id() {
        return material_request_detail_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getUnit_price_buy() {
        return unit_price_buy;
    }

    public String getMax_budget() {
        return max_budget;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPo_app1() {
        return po_app1;
    }

    public String getNotes() {
        return notes;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getStatus() {
        return status;
    }
}
