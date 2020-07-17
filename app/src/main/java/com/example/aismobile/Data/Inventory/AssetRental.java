package com.example.aismobile.Data.Inventory;

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
}
