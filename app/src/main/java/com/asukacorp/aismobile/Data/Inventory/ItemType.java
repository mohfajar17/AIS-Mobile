package com.asukacorp.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemType implements Parcelable {

    private int item_type_id;
    private String item_type_name;
    private String is_active;

    public ItemType (int item_type_id, String item_type_name, String is_active){
        this.item_type_id = item_type_id;
        this.item_type_name = item_type_name;
        this.is_active = is_active;
    }

    protected ItemType(Parcel in) {
        item_type_id = in.readInt();
        item_type_name = in.readString();
        is_active = in.readString();
    }

    public ItemType(JSONObject jsonObject){
        try {
            this.item_type_id = jsonObject.getInt("item_type_id");
            this.item_type_name = jsonObject.getString("item_type_name");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ItemType> CREATOR = new Creator<ItemType>() {
        @Override
        public ItemType createFromParcel(Parcel in) {
            return new ItemType(in);
        }

        @Override
        public ItemType[] newArray(int size) {
            return new ItemType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_type_id);
        dest.writeString(item_type_name);
        dest.writeString(is_active);
    }

    public int getItem_type_id() {
        return item_type_id;
    }

    public String getItem_type_name() {
        return item_type_name;
    }

    public String getIs_active() {
        return is_active;
    }
}
