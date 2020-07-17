package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemGroup implements Parcelable {

    private int item_group_id;
    private String item_group_code;
    private String description;
    private String is_active;

    public ItemGroup (int item_group_id, String item_group_code, String description, String is_active){
        this.item_group_id = item_group_id;
        this.item_group_code = item_group_code;
        this.description = description;
        this.is_active = is_active;
    }

    protected ItemGroup(Parcel in) {
        item_group_id = in.readInt();
        item_group_code = in.readString();
        description = in.readString();
        is_active = in.readString();
    }

    public ItemGroup(JSONObject jsonObject){
        try {
            this.item_group_id = jsonObject.getInt("item_group_id");
            this.item_group_code = jsonObject.getString("item_group_code");
            this.description = jsonObject.getString("description");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ItemGroup> CREATOR = new Creator<ItemGroup>() {
        @Override
        public ItemGroup createFromParcel(Parcel in) {
            return new ItemGroup(in);
        }

        @Override
        public ItemGroup[] newArray(int size) {
            return new ItemGroup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_group_id);
        dest.writeString(item_group_code);
        dest.writeString(description);
        dest.writeString(is_active);
    }

    public int getItem_group_id() {
        return item_group_id;
    }

    public String getItem_group_code() {
        return item_group_code;
    }

    public String getDescription() {
        return description;
    }

    public String getIs_active() {
        return is_active;
    }
}
