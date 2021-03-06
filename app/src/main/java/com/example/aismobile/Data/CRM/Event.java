package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Event implements Parcelable {

    private int event_id;
    private String event_name;
    private String event_location;
    private String start_date;
    private String end_date;
    private String event_photo_1;
    private String event_photo_2;
    private String event_photo_3;
    private String event_photo_4;

    public Event(int event_id, String event_name, String event_location, String start_date, String end_date){
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_location = event_location;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    protected Event(Parcel in) {
        event_id = in.readInt();
        event_name = in.readString();
        event_location = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        event_photo_1 = in.readString();
        event_photo_2 = in.readString();
        event_photo_3 = in.readString();
        event_photo_4 = in.readString();
    }

    public Event(JSONObject jsonObject){
        try {
            this.event_id = jsonObject.getInt("event_id");
            this.event_name = jsonObject.getString("event_name");
            this.event_location = jsonObject.getString("event_location");
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
            this.event_photo_1 = jsonObject.getString("event_photo_1");
            this.event_photo_2 = jsonObject.getString("event_photo_2");
            this.event_photo_3 = jsonObject.getString("event_photo_3");
            this.event_photo_4 = jsonObject.getString("event_photo_4");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(event_id);
        dest.writeString(event_name);
        dest.writeString(event_location);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(event_photo_1);
        dest.writeString(event_photo_2);
        dest.writeString(event_photo_3);
        dest.writeString(event_photo_4);
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_location() {
        return event_location;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getEvent_photo_1() {
        return event_photo_1;
    }

    public String getEvent_photo_2() {
        return event_photo_2;
    }

    public String getEvent_photo_3() {
        return event_photo_3;
    }

    public String getEvent_photo_4() {
        return event_photo_4;
    }
}
