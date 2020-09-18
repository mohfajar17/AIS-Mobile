package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Asset implements Parcelable {

    private int asset_id;
    private String asset_code;
    private String asset_name;
    private String short_description;
    private String location;
    private String quantity;
    private String remark;
    private String status;
    private String asset_type_id;
    private String brand_name;
    private String serial_number;
    private String acquisition_date;
    private String usage_date;
    private String depreciation_method_id;
    private String depreciation_rate;
    private String age_type;
    private String activa_account;
    private String unit_abbr;
    private String currency_symbol;
    private String price_buy;
    private String is_base_currency;
    private String supplier_name;
    private String office_phone;
    private String email_address;
    private String phone;
    private String notes;
    private String description;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public Asset (int asset_id, String asset_code, String asset_name, String short_description, String location,
                 String quantity, String remark, String status){
        this.asset_id = asset_id;
        this.asset_code = asset_code;
        this.asset_name = asset_name;
        this.short_description = short_description;
        this.location = location;
        this.quantity = quantity;
        this.remark = remark;
        this.status = status;
    }

    protected Asset(Parcel in) {
        asset_id = in.readInt();
        asset_code = in.readString();
        asset_name = in.readString();
        short_description = in.readString();
        location = in.readString();
        quantity = in.readString();
        remark = in.readString();
        status = in.readString();
        asset_type_id = in.readString();
        brand_name = in.readString();
        serial_number = in.readString();
        acquisition_date = in.readString();
        usage_date = in.readString();
        depreciation_method_id = in.readString();
        depreciation_rate = in.readString();
        age_type = in.readString();
        activa_account = in.readString();
        unit_abbr = in.readString();
        currency_symbol = in.readString();
        price_buy = in.readString();
        is_base_currency = in.readString();
        supplier_name = in.readString();
        office_phone = in.readString();
        email_address = in.readString();
        phone = in.readString();
        notes = in.readString();
        description = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public Asset(JSONObject jsonObject){
        try {
            this.asset_id = jsonObject.getInt("asset_id");
            this.asset_code = jsonObject.getString("asset_code");
            this.asset_name = jsonObject.getString("asset_name");
            this.short_description = jsonObject.getString("short_description");
            this.location = jsonObject.getString("location");
            this.quantity = jsonObject.getString("quantity");
            this.remark = jsonObject.getString("remark");
            this.status = jsonObject.getString("status");
            this.asset_type_id = jsonObject.getString("asset_type_id");
            this.brand_name = jsonObject.getString("brand_name");
            this.serial_number = jsonObject.getString("serial_number");
            this.acquisition_date = jsonObject.getString("acquisition_date");
            this.usage_date = jsonObject.getString("usage_date");
            this.depreciation_method_id = jsonObject.getString("depreciation_method_id");
            this.depreciation_rate = jsonObject.getString("depreciation_rate");
            this.age_type = jsonObject.getString("age_type");
            this.activa_account = jsonObject.getString("activa_account");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.currency_symbol = jsonObject.getString("currency_symbol");
            this.price_buy = jsonObject.getString("price_buy");
            this.is_base_currency = jsonObject.getString("is_base_currency");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.office_phone = jsonObject.getString("office_phone");
            this.email_address = jsonObject.getString("email_address");
            this.phone = jsonObject.getString("phone");
            this.notes = jsonObject.getString("notes");
            this.description = jsonObject.getString("description");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Asset> CREATOR = new Creator<Asset>() {
        @Override
        public Asset createFromParcel(Parcel in) {
            return new Asset(in);
        }

        @Override
        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(asset_id);
        dest.writeString(asset_code);
        dest.writeString(asset_name);
        dest.writeString(short_description);
        dest.writeString(location);
        dest.writeString(quantity);
        dest.writeString(remark);
        dest.writeString(status);
        dest.writeString(asset_type_id);
        dest.writeString(brand_name);
        dest.writeString(serial_number);
        dest.writeString(acquisition_date);
        dest.writeString(usage_date);
        dest.writeString(depreciation_method_id);
        dest.writeString(depreciation_rate);
        dest.writeString(age_type);
        dest.writeString(activa_account);
        dest.writeString(unit_abbr);
        dest.writeString(currency_symbol);
        dest.writeString(price_buy);
        dest.writeString(is_base_currency);
        dest.writeString(supplier_name);
        dest.writeString(office_phone);
        dest.writeString(email_address);
        dest.writeString(phone);
        dest.writeString(notes);
        dest.writeString(description);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getAsset_id() {
        return asset_id;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getLocation() {
        return location;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRemark() {
        return remark;
    }

    public String getStatus() {
        return status;
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

    public String getAcquisition_date() {
        return acquisition_date;
    }

    public String getUsage_date() {
        return usage_date;
    }

    public String getDepreciation_method_id() {
        return depreciation_method_id;
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

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public String getPrice_buy() {
        return price_buy;
    }

    public String getIs_base_currency() {
        return is_base_currency;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getPhone() {
        return phone;
    }

    public String getNotes() {
        return notes;
    }

    public String getDescription() {
        return description;
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
