package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Fullname implements Parcelable {

    private int id;
    private String fullname;
    private String employee_id;

    public Fullname (int id, String fullname, String employee_id){
        this.id = id;
        this.fullname = fullname;
        this.employee_id = employee_id;
    }
    protected Fullname (Parcel in) {
        id = in.readInt();
        fullname = in.readString();
        employee_id = in.readString();
    }

    public Fullname (JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.fullname = jsonObject.getString("fullname");
            this.employee_id = jsonObject.getString("employee_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Fullname> CREATOR = new Creator<Fullname>() {
        @Override
        public Fullname createFromParcel(Parcel in) {
            return new Fullname(in);
        }

        @Override
        public Fullname[] newArray(int size) {
            return new Fullname[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fullname);
        dest.writeString(employee_id);
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmployee_id() {
        return employee_id;
    }
}
