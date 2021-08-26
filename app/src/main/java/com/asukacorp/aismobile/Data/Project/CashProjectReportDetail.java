package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CashProjectReportDetail implements Parcelable {

    private int responsbility_advance_detail;
    private String usage_date;
    private String item_name;
    private String quantity;
    private String unit_abbr;
    private String unit_price;
    private String discount;
    private String respons_advance_app1;
    private String respons_advance_app2;
    private String respons_advance_app3;

    protected CashProjectReportDetail(Parcel in) {
        responsbility_advance_detail = in.readInt();
        usage_date = in.readString();
        item_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price = in.readString();
        discount = in.readString();
        respons_advance_app1 = in.readString();
        respons_advance_app2 = in.readString();
        respons_advance_app3 = in.readString();
    }

    public CashProjectReportDetail(JSONObject jsonObject){
        try {
            this.responsbility_advance_detail = jsonObject.getInt("responsbility_advance_detail");
            this.usage_date = jsonObject.getString("usage_date");
            this.item_name = jsonObject.getString("item_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price = jsonObject.getString("unit_price");
            this.discount = jsonObject.getString("discount");
            this.respons_advance_app1 = jsonObject.getString("respons_advance_app1");
            this.respons_advance_app2 = jsonObject.getString("respons_advance_app2");
            this.respons_advance_app3 = jsonObject.getString("respons_advance_app3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CashProjectReportDetail> CREATOR = new Creator<CashProjectReportDetail>() {
        @Override
        public CashProjectReportDetail createFromParcel(Parcel in) {
            return new CashProjectReportDetail(in);
        }

        @Override
        public CashProjectReportDetail[] newArray(int size) {
            return new CashProjectReportDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(responsbility_advance_detail);
        dest.writeString(usage_date);
        dest.writeString(item_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price);
        dest.writeString(discount);
        dest.writeString(respons_advance_app1);
        dest.writeString(respons_advance_app2);
        dest.writeString(respons_advance_app3);
    }

    public int getResponsbility_advance_detail() {
        return responsbility_advance_detail;
    }

    public String getUsage_date() {
        return usage_date;
    }

    public String getItem_name() {
        return item_name;
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

    public String getRespons_advance_app1() {
        return respons_advance_app1;
    }

    public String getRespons_advance_app2() {
        return respons_advance_app2;
    }

    public String getRespons_advance_app3() {
        return respons_advance_app3;
    }
}
