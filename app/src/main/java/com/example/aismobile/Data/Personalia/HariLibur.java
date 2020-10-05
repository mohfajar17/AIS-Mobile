package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class HariLibur implements Parcelable {

    private int holiday_id;
    private String holiday_name;
    private String holiday_type_name;
    private String holiday_date;
    private String description;
    private String is_overtime;

    public HariLibur (int holiday_id, String holiday_name, String holiday_type_name, String holiday_date,
                           String is_overtime){
        this.holiday_id = holiday_id;
        this.holiday_name = holiday_name;
        this.holiday_type_name = holiday_type_name;
        this.holiday_date = holiday_date;
        this.is_overtime = is_overtime;
    }
    protected HariLibur (Parcel in) {
        holiday_id = in.readInt();
        holiday_name = in.readString();
        holiday_type_name = in.readString();
        holiday_date = in.readString();
        description = in.readString();
        is_overtime = in.readString();
    }

    public HariLibur (JSONObject jsonObject){
        try {
            this.holiday_id = jsonObject.getInt("holiday_id");
            this.holiday_name = jsonObject.getString("holiday_name");
            this.holiday_type_name = jsonObject.getString("holiday_type_name");
            this.holiday_date = jsonObject.getString("holiday_date");
            this.description = jsonObject.getString("description");
            this.is_overtime = jsonObject.getString("is_overtime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<HariLibur> CREATOR = new Creator<HariLibur>() {
        @Override
        public HariLibur createFromParcel(Parcel in) {
            return new HariLibur(in);
        }

        @Override
        public HariLibur[] newArray(int size) {
            return new HariLibur[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(holiday_id);
        dest.writeString(holiday_name);
        dest.writeString(holiday_type_name);
        dest.writeString(holiday_date);
        dest.writeString(description);
        dest.writeString(is_overtime);
    }

    public int getHoliday_id() {
        return holiday_id;
    }

    public String getHoliday_name() {
        return holiday_name;
    }

    public String getHoliday_type_name() {
        return holiday_type_name;
    }

    public String getHoliday_date() {
        return holiday_date;
    }

    public String getDescription() {
        return description;
    }

    public String getIs_overtime() {
        return is_overtime;
    }
}
