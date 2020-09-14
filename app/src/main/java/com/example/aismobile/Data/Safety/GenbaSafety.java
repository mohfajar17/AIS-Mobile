package com.example.aismobile.Data.Safety;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class GenbaSafety implements Parcelable {

    private int genba_safety_id;
    private String genba_safety_number;
    private String genba_date;
    private String company_workbase_id;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String genba_time;
    private String notes;
    private String genba_photo1;
    private String genba_photo2;
    private String genba_photo3;

    public GenbaSafety (int genba_safety_id, String genba_safety_number, String genba_date,
                       String company_workbase_id, String created_by){
        this.genba_safety_id = genba_safety_id;
        this.genba_safety_number = genba_safety_number;
        this.genba_date = genba_date;
        this.company_workbase_id = company_workbase_id;
        this.created_by = created_by;
    }

    protected GenbaSafety(Parcel in) {
        genba_safety_id = in.readInt();
        genba_safety_number = in.readString();
        genba_date = in.readString();
        company_workbase_id = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        genba_time = in.readString();
        notes = in.readString();
        genba_photo1 = in.readString();
        genba_photo2 = in.readString();
        genba_photo3 = in.readString();
    }

    public GenbaSafety(JSONObject jsonObject){
        try {
            this.genba_safety_id = jsonObject.getInt("genba_safety_id");
            this.genba_safety_number = jsonObject.getString("genba_safety_number");
            this.genba_date = jsonObject.getString("genba_date");
            this.company_workbase_id = jsonObject.getString("company_workbase_id");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.genba_time = jsonObject.getString("genba_time");
            this.notes = jsonObject.getString("notes");
            this.genba_photo1 = jsonObject.getString("genba_photo1");
            this.genba_photo2 = jsonObject.getString("genba_photo2");
            this.genba_photo3 = jsonObject.getString("genba_photo3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<GenbaSafety> CREATOR = new Creator<GenbaSafety>() {
        @Override
        public GenbaSafety createFromParcel(Parcel in) {
            return new GenbaSafety(in);
        }

        @Override
        public GenbaSafety[] newArray(int size) {
            return new GenbaSafety[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(genba_safety_id);
        dest.writeString(genba_safety_number);
        dest.writeString(genba_date);
        dest.writeString(company_workbase_id);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(genba_time);
        dest.writeString(notes);
        dest.writeString(genba_photo1);
        dest.writeString(genba_photo2);
        dest.writeString(genba_photo3);
    }

    public int getGenba_safety_id() {
        return genba_safety_id;
    }

    public String getGenba_safety_number() {
        return genba_safety_number;
    }

    public String getGenba_date() {
        return genba_date;
    }

    public String getCompany_workbase_id() {
        return company_workbase_id;
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

    public String getGenba_time() {
        return genba_time;
    }

    public String getNotes() {
        return notes;
    }

    public String getGenba_photo1() {
        return genba_photo1;
    }

    public String getGenba_photo2() {
        return genba_photo2;
    }

    public String getGenba_photo3() {
        return genba_photo3;
    }
}
