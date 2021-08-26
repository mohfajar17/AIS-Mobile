package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MaterialRequestDetail implements Parcelable {

    private int material_request_detail_id;
    private String item_name;
    private String item_specification;
    private String notes;
    private String purchase_order_number;
    private String quantity;
    private String unit_abbr;
    private String is_stock_request;
    private String quantity_stock_request;
    private String po_app1;
    private String po_app2;
    private String po_app3;
    private String status;
    private String stock_charging;

    protected MaterialRequestDetail(Parcel in) {
        material_request_detail_id = in.readInt();
        item_name = in.readString();
        item_specification = in.readString();
        notes = in.readString();
        purchase_order_number = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        is_stock_request = in.readString();
        quantity_stock_request = in.readString();
        po_app1 = in.readString();
        po_app2 = in.readString();
        po_app3 = in.readString();
        status = in.readString();
        stock_charging = in.readString();
    }

    public MaterialRequestDetail(JSONObject jsonObject){
        try {
            this.material_request_detail_id = jsonObject.getInt("material_request_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.notes = jsonObject.getString("notes");
            this.purchase_order_number = jsonObject.getString("purchase_order_number");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.is_stock_request = jsonObject.getString("is_stock_request");
            this.quantity_stock_request = jsonObject.getString("quantity_stock_request");
            this.po_app1 = jsonObject.getString("po_app1");
            this.po_app2 = jsonObject.getString("po_app2");
            this.po_app3 = jsonObject.getString("po_app3");
            this.status = jsonObject.getString("status");
            this.stock_charging = jsonObject.getString("stock_charging");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MaterialRequestDetail> CREATOR = new Creator<MaterialRequestDetail>() {
        @Override
        public MaterialRequestDetail createFromParcel(Parcel in) {
            return new MaterialRequestDetail(in);
        }

        @Override
        public MaterialRequestDetail[] newArray(int size) {
            return new MaterialRequestDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_request_detail_id);
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(notes);
        dest.writeString(purchase_order_number);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(is_stock_request);
        dest.writeString(quantity_stock_request);
        dest.writeString(po_app1);
        dest.writeString(po_app2);
        dest.writeString(po_app3);
        dest.writeString(status);
        dest.writeString(stock_charging);
    }

    public int getMaterial_request_detail_id() {
        return material_request_detail_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getNotes() {
        return notes;
    }

    public String getPurchase_order_number() {
        return purchase_order_number;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getIs_stock_request() {
        return is_stock_request;
    }

    public String getQuantity_stock_request() {
        return quantity_stock_request;
    }

    public String getPo_app1() {
        return po_app1;
    }

    public String getPo_app2() {
        return po_app2;
    }

    public String getPo_app3() {
        return po_app3;
    }

    public String getStatus() {
        return status;
    }

    public String getStock_charging() {
        return stock_charging;
    }
}
