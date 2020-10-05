package com.example.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class AccessRequestDetail implements Parcelable {
    private String access_name;
    private String notes;
    private String approval1;

    protected AccessRequestDetail(Parcel in) {
        access_name = in.readString();
        notes = in.readString();
        approval1 = in.readString();
    }

    public AccessRequestDetail(JSONObject jsonObject){
        try {
            this.access_name = jsonObject.getString("access_name");
            this.notes = jsonObject.getString("notes");
            this.approval1 = jsonObject.getString("approval1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<AccessRequestDetail> CREATOR = new Creator<AccessRequestDetail>() {
        @Override
        public AccessRequestDetail createFromParcel(Parcel in) {
            return new AccessRequestDetail(in);
        }

        @Override
        public AccessRequestDetail[] newArray(int size) {
            return new AccessRequestDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_name);
        dest.writeString(notes);
        dest.writeString(approval1);
    }

    public String getAccess_name() {
        return access_name;
    }

    public String getNotes() {
        return notes;
    }

    public String getApproval1() {
        return approval1;
    }
}
