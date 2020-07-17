package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemCategory implements Parcelable {

    private int item_category_id;
    private String item_category_name;
    private String item_category_description;
    private String code_for_item;

    public ItemCategory (int item_category_id, String item_category_name, String item_category_description,
                         String code_for_item){
        this.item_category_id = item_category_id;
        this.item_category_name = item_category_name;
        this.item_category_description = item_category_description;
        this.code_for_item = code_for_item;
    }

    protected ItemCategory(Parcel in) {
        item_category_id = in.readInt();
        item_category_name = in.readString();
        item_category_description = in.readString();
        code_for_item = in.readString();
    }

    public ItemCategory(JSONObject jsonObject){
        try {
            this.item_category_id = jsonObject.getInt("item_category_id");
            this.item_category_name = jsonObject.getString("item_category_name");
            this.item_category_description = jsonObject.getString("item_category_description");
            this.code_for_item = jsonObject.getString("code_for_item");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ItemCategory> CREATOR = new Creator<ItemCategory>() {
        @Override
        public ItemCategory createFromParcel(Parcel in) {
            return new ItemCategory(in);
        }

        @Override
        public ItemCategory[] newArray(int size) {
            return new ItemCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_category_id);
        dest.writeString(item_category_name);
        dest.writeString(item_category_description);
        dest.writeString(code_for_item);
    }

    public int getItem_category_id() {
        return item_category_id;
    }

    public String getItem_category_name() {
        return item_category_name;
    }

    public String getItem_category_description() {
        return item_category_description;
    }

    public String getCode_for_item() {
        return code_for_item;
    }
}
