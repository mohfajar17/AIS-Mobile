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
    }

    public GenbaSafety(JSONObject jsonObject){
        try {
            this.genba_safety_id = jsonObject.getInt("genba_safety_id");
            this.genba_safety_number = jsonObject.getString("genba_safety_number");
            this.genba_date = jsonObject.getString("genba_date");
            this.company_workbase_id = jsonObject.getString("company_workbase_id");
            this.created_by = jsonObject.getString("created_by");
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
}
