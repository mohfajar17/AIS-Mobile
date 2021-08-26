package com.asukacorp.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class AssetRental implements Parcelable {

    private int asset_rental_id;
    private String asset_rental_code;
    private String asset_rental_name;
    private String short_description;
    private String rack_code;
    private String is_active;
    private String remark;
    private String asset_type_id;
    private String brand_name;
    private String serial_number;
    private String description;
    private String notes;
    private String warehouse_name;
    private String currency_symbol;
    private String price_buy;
    private String supplier_name;
    private String quantity;
    private String unit_abbr;
    private String acquisition_date;
    private String usage_date;
    private String depreciation_method;
    private String depreciation_rate;
    private String age_type;
    private String activa_account;
    private String depreciation_account;
    private String exchange_depreciation_account;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public AssetRental (int asset_rental_id, String asset_rental_code, String asset_rental_name, String short_description,
                        String rack_code, String is_active, String remark){
        this.asset_rental_id = asset_rental_id;
        this.asset_rental_code = asset_rental_code;
        this.asset_rental_name = asset_rental_name;
        this.short_description = short_description;
        this.rack_code = rack_code;
        this.is_active = is_active;
        this.remark = remark;
    }
    protected AssetRental(Parcel in) {
        asset_rental_id = in.readInt();
        asset_rental_code = in.readString();
        asset_rental_name = in.readString();
        short_description = in.readString();
        rack_code = in.readString();
        is_active = in.readString();
        remark = in.readString();
        asset_type_id = in.readString();
        brand_name = in.readString();
        serial_number = in.readString();
        description = in.readString();
        notes = in.readString();
        warehouse_name = in.readString();
        currency_symbol = in.readString();
        price_buy = in.readString();
        supplier_name = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        acquisition_date = in.readString();
        usage_date = in.readString();
        depreciation_method = in.readString();
        depreciation_rate = in.readString();
        age_type = in.readString();
        activa_account = in.readString();
        depreciation_account = in.readString();
        exchange_depreciation_account = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public AssetRental(JSONObject jsonObject){
        try {
            this.asset_rental_id = jsonObject.getInt("asset_rental_id");
            this.asset_rental_code = jsonObject.getString("asset_rental_code");
            this.asset_rental_name = jsonObject.getString("asset_rental_name");
            this.short_description = jsonObject.getString("short_description");
            this.rack_code = jsonObject.getString("rack_code");
            this.is_active = jsonObject.getString("is_active");
            this.remark = jsonObject.getString("remark");
            this.asset_type_id = jsonObject.getString("asset_type_id");
            this.brand_name = jsonObject.getString("brand_name");
            this.serial_number = jsonObject.getString("serial_number");
            this.description = jsonObject.getString("description");
            this.notes = jsonObject.getString("notes");
            this.warehouse_name = jsonObject.getString("warehouse_name");
            this.currency_symbol = jsonObject.getString("currency_symbol");
            this.price_buy = jsonObject.getString("price_buy");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.acquisition_date = jsonObject.getString("acquisition_date");
            this.usage_date = jsonObject.getString("usage_date");
            this.depreciation_method = jsonObject.getString("depreciation_method");
            this.depreciation_rate = jsonObject.getString("depreciation_rate");
            this.age_type = jsonObject.getString("age_type");
            this.activa_account = jsonObject.getString("activa_account");
            this.depreciation_account = jsonObject.getString("depreciation_account");
            this.exchange_depreciation_account = jsonObject.getString("exchange_depreciation_account");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<AssetRental> CREATOR = new Creator<AssetRental>() {
        @Override
        public AssetRental createFromParcel(Parcel in) {
            return new AssetRental(in);
        }

        @Override
        public AssetRental[] newArray(int size) {
            return new AssetRental[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(asset_rental_id);
        dest.writeString(asset_rental_code);
        dest.writeString(asset_rental_name);
        dest.writeString(short_description);
        dest.writeString(rack_code);
        dest.writeString(is_active);
        dest.writeString(remark);
        dest.writeString(asset_type_id);
        dest.writeString(brand_name);
        dest.writeString(serial_number);
        dest.writeString(description);
        dest.writeString(notes);
        dest.writeString(warehouse_name);
        dest.writeString(currency_symbol);
        dest.writeString(price_buy);
        dest.writeString(supplier_name);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(acquisition_date);
        dest.writeString(usage_date);
        dest.writeString(depreciation_method);
        dest.writeString(depreciation_rate);
        dest.writeString(age_type);
        dest.writeString(activa_account);
        dest.writeString(depreciation_account);
        dest.writeString(exchange_depreciation_account);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getAsset_rental_id() {
        return asset_rental_id;
    }

    public String getAsset_rental_code() {
        return asset_rental_code;
    }

    public String getAsset_rental_name() {
        return asset_rental_name;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getRack_code() {
        return rack_code;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getRemark() {
        return remark;
    }

    public String getAsset_type_id() {
        return asset_type_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getDescription() {
        return description;
    }

    public String getNotes() {
        return notes;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public String getPrice_buy() {
        return price_buy;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getAcquisition_date() {
        return acquisition_date;
    }

    public String getUsage_date() {
        return usage_date;
    }

    public String getDepreciation_method() {
        return depreciation_method;
    }

    public String getDepreciation_rate() {
        return depreciation_rate;
    }

    public String getAge_type() {
        return age_type;
    }

    public String getActiva_account() {
        return activa_account;
    }

    public String getDepreciation_account() {
        return depreciation_account;
    }

    public String getExchange_depreciation_account() {
        return exchange_depreciation_account;
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
}
