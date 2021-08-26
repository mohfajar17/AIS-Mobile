package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoMatRetOther implements Parcelable {

    private int material_return_detail_id;
    private String item_name;
    private String item_specification;
    private String quantity;
    private String unit_abbr;
    private String unit_price_stock;

    protected JoMatRetOther(Parcel in) {
        material_return_detail_id = in.readInt();
        item_name = in.readString();
        item_specification = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        unit_price_stock = in.readString();
    }

    public JoMatRetOther(JSONObject jsonObject){
        try {
            this.material_return_detail_id = jsonObject.getInt("material_return_detail_id");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.unit_price_stock = jsonObject.getString("unit_price_stock");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoMatRetOther> CREATOR = new Creator<JoMatRetOther>() {
        @Override
        public JoMatRetOther createFromParcel(Parcel in) {
            return new JoMatRetOther(in);
        }

        @Override
        public JoMatRetOther[] newArray(int size) {
            return new JoMatRetOther[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_return_detail_id);
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(unit_price_stock);
    }

    public int getMaterial_return_detail_id() {
        return material_return_detail_id;
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

    public String getUnit_price_stock() {
        return unit_price_stock;
    }
}
