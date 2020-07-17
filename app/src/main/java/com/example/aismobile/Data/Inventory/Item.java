package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Item implements Parcelable {

    private int item_id;
    private String item_code;
    private String item_name;
    private String item_specification;
    private String current_stock;
    private String rack_code;
    private String is_warehouse;
    private String item_section;
    private String is_asset;
    private String is_active;

    public Item (int item_id, String item_code, String item_name, String item_specification, String current_stock,
                 String rack_code, String is_warehouse, String item_section, String is_asset, String is_active){
        this.item_id = item_id;
        this.item_code = item_code;
        this.item_name = item_name;
        this.item_specification = item_specification;
        this.current_stock = current_stock;
        this.rack_code = rack_code;
        this.is_warehouse = is_warehouse;
        this.item_section = item_section;
        this.is_asset = is_asset;
        this.is_active = is_active;
    }
    protected Item(Parcel in) {
        item_id = in.readInt();
        item_code = in.readString();
        item_name = in.readString();
        item_specification = in.readString();
        current_stock = in.readString();
        rack_code = in.readString();
        is_warehouse = in.readString();
        item_section = in.readString();
        is_asset = in.readString();
        is_active = in.readString();
    }

    public Item(JSONObject jsonObject){
        try {
            this.item_id = jsonObject.getInt("item_id");
            this.item_code = jsonObject.getString("item_code");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.current_stock = jsonObject.getString("current_stock");
            this.rack_code = jsonObject.getString("rack_code");
            this.is_warehouse = jsonObject.getString("is_warehouse");
            this.item_section = jsonObject.getString("item_section");
            this.is_asset = jsonObject.getString("is_asset");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_id);
        dest.writeString(item_code);
        dest.writeString(item_name);
        dest.writeString(item_specification);
        dest.writeString(current_stock);
        dest.writeString(rack_code);
        dest.writeString(is_warehouse);
        dest.writeString(item_section);
        dest.writeString(is_asset);
        dest.writeString(is_active);
    }

    public int getItem_id() {
        return item_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public String getCurrent_stock() {
        return current_stock;
    }

    public String getRack_code() {
        return rack_code;
    }

    public String getIs_warehouse() {
        return is_warehouse;
    }

    public String getItem_section() {
        return item_section;
    }

    public String getIs_asset() {
        return is_asset;
    }

    public String getIs_active() {
        return is_active;
    }
}
