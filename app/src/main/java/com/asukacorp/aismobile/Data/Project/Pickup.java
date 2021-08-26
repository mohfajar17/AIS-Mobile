package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Pickup implements Parcelable {

    private int pickup_id;
    private String pickup_number;
    private String material_request_id;
    private String pickup_by;
    private String taken_date;
    private String notes;
    private String recognized;
    private String recognized_by;
    private String recognized_date;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public Pickup(int pickup_id, String pickup_number, String material_request_id, String pickup_by, String taken_date,
                  String notes, String recognized, String recognized_by, String recognized_date, String created_by,
                  String created_date, String modified_by, String modified_date){
        this.pickup_id = pickup_id;
        this.pickup_number = pickup_number;
        this.material_request_id = material_request_id;
        this.pickup_by = pickup_by;
        this.taken_date = taken_date;
        this.notes = notes;
        this.recognized = recognized;
        this.recognized_by = recognized_by;
        this.recognized_date = recognized_date;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    protected Pickup(Parcel in) {
        pickup_id = in.readInt();
        pickup_number = in.readString();
        material_request_id = in.readString();
        pickup_by = in.readString();
        taken_date = in.readString();
        notes = in.readString();
        recognized = in.readString();
        recognized_by = in.readString();
        recognized_date = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public Pickup(JSONObject jsonObject){
        try {
            this.pickup_id = jsonObject.getInt("pickup_id");
            this.pickup_number = jsonObject.getString("pickup_number");
            this.material_request_id = jsonObject.getString("material_request_id");
            this.pickup_by = jsonObject.getString("pickup_by");
            this.taken_date = jsonObject.getString("taken_date");
            this.notes = jsonObject.getString("notes");
            this.recognized = jsonObject.getString("recognized");
            this.recognized_by = jsonObject.getString("recognized_by");
            this.recognized_date = jsonObject.getString("recognized_date");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Pickup> CREATOR = new Creator<Pickup>() {
        @Override
        public Pickup createFromParcel(Parcel in) {
            return new Pickup(in);
        }

        @Override
        public Pickup[] newArray(int size) {
            return new Pickup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pickup_id);
        dest.writeString(pickup_number);
        dest.writeString(material_request_id);
        dest.writeString(pickup_by);
        dest.writeString(taken_date);
        dest.writeString(notes);
        dest.writeString(recognized);
        dest.writeString(recognized_by);
        dest.writeString(recognized_date);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
    }

    public int getPickup_id() {
        return pickup_id;
    }

    public String getPickup_number() {
        return pickup_number;
    }

    public String getMaterial_request_id() {
        return material_request_id;
    }

    public String getPickup_by() {
        return pickup_by;
    }

    public String getTaken_date() {
        return taken_date;
    }

    public String getNotes() {
        return notes;
    }

    public String getRecognized() {
        return recognized;
    }

    public String getRecognized_by() {
        return recognized_by;
    }

    public String getRecognized_date() {
        return recognized_date;
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
