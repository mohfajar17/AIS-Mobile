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
    private String low_level_stock;
    private String reorder_level_stock;
    private String rack_code;
    private String is_warehouse;
    private String item_section;
    private String is_asset;
    private String is_active;
    private String brand_name;
    private String serial_number;
    private String model_number;
    private String item_type_name;
    private String item_category_name;
    private String item_group_code;
    private String supplier_name;
    private String office_address1;
    private String office_phone;
    private String office_fax;
    private String unit_abbr;
    private String description;
    private String price_buy;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String item_file_name;

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
        low_level_stock = in.readString();
        reorder_level_stock = in.readString();
        rack_code = in.readString();
        is_warehouse = in.readString();
        item_section = in.readString();
        is_asset = in.readString();
        is_active = in.readString();
        brand_name = in.readString();
        serial_number = in.readString();
        model_number = in.readString();
        item_type_name = in.readString();
        item_category_name = in.readString();
        item_group_code = in.readString();
        supplier_name = in.readString();
        office_address1 = in.readString();
        office_phone = in.readString();
        office_fax = in.readString();
        unit_abbr = in.readString();
        description = in.readString();
        price_buy = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        item_file_name = in.readString();
    }

    public Item(JSONObject jsonObject){
        try {
            this.item_id = jsonObject.getInt("item_id");
            this.item_code = jsonObject.getString("item_code");
            this.item_name = jsonObject.getString("item_name");
            this.item_specification = jsonObject.getString("item_specification");
            this.current_stock = jsonObject.getString("current_stock");
            this.low_level_stock = jsonObject.getString("low_level_stock");
            this.reorder_level_stock = jsonObject.getString("reorder_level_stock");
            this.rack_code = jsonObject.getString("rack_code");
            this.is_warehouse = jsonObject.getString("is_warehouse");
            this.item_section = jsonObject.getString("item_section");
            this.is_asset = jsonObject.getString("is_asset");
            this.is_active = jsonObject.getString("is_active");
            this.brand_name = jsonObject.getString("brand_name");
            this.serial_number = jsonObject.getString("serial_number");
            this.model_number = jsonObject.getString("model_number");
            this.item_type_name = jsonObject.getString("item_type_name");
            this.item_category_name = jsonObject.getString("item_category_name");
            this.item_group_code = jsonObject.getString("item_group_code");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.office_address1 = jsonObject.getString("office_address1");
            this.office_phone = jsonObject.getString("office_phone");
            this.office_fax = jsonObject.getString("office_fax");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.description = jsonObject.getString("description");
            this.price_buy = jsonObject.getString("price_buy");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.item_file_name = jsonObject.getString("item_file_name");
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
        dest.writeString(low_level_stock);
        dest.writeString(reorder_level_stock);
        dest.writeString(rack_code);
        dest.writeString(is_warehouse);
        dest.writeString(item_section);
        dest.writeString(is_asset);
        dest.writeString(is_active);
        dest.writeString(brand_name);
        dest.writeString(serial_number);
        dest.writeString(model_number);
        dest.writeString(item_type_name);
        dest.writeString(item_category_name);
        dest.writeString(item_group_code);
        dest.writeString(supplier_name);
        dest.writeString(office_address1);
        dest.writeString(office_phone);
        dest.writeString(office_fax);
        dest.writeString(unit_abbr);
        dest.writeString(description);
        dest.writeString(price_buy);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(item_file_name);
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

    public String getLow_level_stock() {
        return low_level_stock;
    }

    public String getReorder_level_stock() {
        return reorder_level_stock;
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

    public String getBrand_name() {
        return brand_name;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getModel_number() {
        return model_number;
    }

    public String getItem_type_name() {
        return item_type_name;
    }

    public String getItem_category_name() {
        return item_category_name;
    }

    public String getItem_group_code() {
        return item_group_code;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getOffice_address1() {
        return office_address1;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public String getOffice_fax() {
        return office_fax;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice_buy() {
        return price_buy;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public String getItem_file_name() {
        return item_file_name;
    }
}
