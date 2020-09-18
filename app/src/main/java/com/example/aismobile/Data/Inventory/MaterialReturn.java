package com.example.aismobile.Data.Inventory;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MaterialReturn implements Parcelable {

    private int material_return_id;
    private String material_return_number;
    private String job_order_number;
    private String job_order_description;
    private String return_date;
    private String created_by;
    private String notes;
    private String recognized;

    public MaterialReturn (int material_return_id, String material_return_number, String job_order_number,String job_order_description,
                            String return_date, String created_by, String notes, String recognized){
        this.material_return_id = material_return_id;
        this.material_return_number = material_return_number;
        this.job_order_number = job_order_number;
        this.job_order_description = job_order_description;
        this.return_date = return_date;
        this.created_by = created_by;
        this.notes = notes;
        this.recognized = recognized;
    }

    protected MaterialReturn(Parcel in) {
        material_return_id = in.readInt();
        material_return_number = in.readString();
        job_order_number = in.readString();
        job_order_description = in.readString();
        return_date = in.readString();
        created_by = in.readString();
        notes = in.readString();
        recognized = in.readString();
    }

    public MaterialReturn(JSONObject jsonObject){
        try {
            this.material_return_id = jsonObject.getInt("material_return_id");
            this.material_return_number = jsonObject.getString("material_return_number");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.return_date = jsonObject.getString("return_date");
            this.created_by = jsonObject.getString("created_by");
            this.notes = jsonObject.getString("notes");
            this.recognized = jsonObject.getString("recognized");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MaterialReturn> CREATOR = new Creator<MaterialReturn>() {
        @Override
        public MaterialReturn createFromParcel(Parcel in) {
            return new MaterialReturn(in);
        }

        @Override
        public MaterialReturn[] newArray(int size) {
            return new MaterialReturn[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_return_id);
        dest.writeString(material_return_number);
        dest.writeString(job_order_number);
        dest.writeString(job_order_description);
        dest.writeString(return_date);
        dest.writeString(created_by);
        dest.writeString(notes);
        dest.writeString(recognized);
    }

    public int getMaterial_return_id() {
        return material_return_id;
    }

    public String getMaterial_return_number() {
        return material_return_number;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getReturn_date() {
        return return_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getNotes() {
        return notes;
    }

    public String getRecognized() {
        return recognized;
    }
}
