package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkOrderDetail implements Parcelable {

    private String work_order_detail_id;
    private String item_name;
    private String wo_notes;
    private String quantity;
    private String unit_abbr;
    private String unit_price;
    private String discount;
    private String wo_app1;
    private String wo_app2;
    private String wo_app3;

    protected WorkOrderDetail(Parcel in) {
        work_order_detail_id = in.readString();
        item_name = in.readString();
        wo_notes = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price = in.readString();
        discount = in.readString();
        wo_app1 = in.readString();
        wo_app2 = in.readString();
        wo_app3 = in.readString();
    }

    public WorkOrderDetail(JSONObject jsonObject){
        try {
            this.work_order_detail_id = jsonObject.getString("work_order_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.wo_notes = jsonObject.getString("wo_notes");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price = jsonObject.getString("unit_price");
            this.discount = jsonObject.getString("discount");
            this.wo_app1 = jsonObject.getString("wo_app1");
            this.wo_app2 = jsonObject.getString("wo_app2");
            this.wo_app3 = jsonObject.getString("wo_app3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkOrderDetail> CREATOR = new Creator<WorkOrderDetail>() {
        @Override
        public WorkOrderDetail createFromParcel(Parcel in) {
            return new WorkOrderDetail(in);
        }

        @Override
        public WorkOrderDetail[] newArray(int size) {
            return new WorkOrderDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(work_order_detail_id);
        dest.writeString(item_name);
        dest.writeString(wo_notes);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price);
        dest.writeString(discount);
        dest.writeString(wo_app1);
        dest.writeString(wo_app2);
        dest.writeString(wo_app3);
    }

    public String getWork_order_detail_id() {
        return work_order_detail_id;
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

    public String getDiscount() {
        return discount;
    }

    public String getWo_app1() {
        return wo_app1;
    }

    public String getWo_app2() {
        return wo_app2;
    }

    public String getWo_app3() {
        return wo_app3;
    }
}
