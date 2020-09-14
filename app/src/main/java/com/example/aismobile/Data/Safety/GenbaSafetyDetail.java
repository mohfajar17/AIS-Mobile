package com.example.aismobile.Data.Safety;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class GenbaSafetyDetail implements Parcelable {

    private int genba_safety_detail_id;
    private String employee_id;
    private String employee_name1;
    private String departemen;
    private String notes;

    protected GenbaSafetyDetail(Parcel in) {
        genba_safety_detail_id = in.readInt();
        employee_id = in.readString();
        employee_name1 = in.readString();
        departemen = in.readString();
        notes = in.readString();
    }

    public GenbaSafetyDetail(JSONObject jsonObject){
        try {
            this.genba_safety_detail_id = jsonObject.getInt("genba_safety_detail_id");
            this.employee_id = jsonObject.getString("employee_id");
            this.employee_name1 = jsonObject.getString("employee_name1");
            this.departemen = jsonObject.getString("departemen");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<GenbaSafetyDetail> CREATOR = new Creator<GenbaSafetyDetail>() {
        @Override
        public GenbaSafetyDetail createFromParcel(Parcel in) {
            return new GenbaSafetyDetail(in);
        }

        @Override
        public GenbaSafetyDetail[] newArray(int size) {
            return new GenbaSafetyDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(genba_safety_detail_id);
        dest.writeString(employee_id);
        dest.writeString(employee_name1);
        dest.writeString(departemen);
        dest.writeString(notes);
    }

    public int getGenba_safety_detail_id() {
        return genba_safety_detail_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getEmployee_name1() {
        return employee_name1;
    }

    public String getDepartemen() {
        return departemen;
    }

    public String getNotes() {
        return notes;
    }
}
