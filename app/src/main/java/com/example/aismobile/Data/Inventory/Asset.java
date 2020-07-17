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
}
