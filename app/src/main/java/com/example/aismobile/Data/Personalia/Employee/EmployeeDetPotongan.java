package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetPotongan implements Parcelable {
    private String deduction_name;
    private String value;
    private String is_active;

    protected EmployeeDetPotongan (Parcel in) {
        deduction_name = in.readString();
        value = in.readString();
        is_active = in.readString();
    }

    public EmployeeDetPotongan (JSONObject jsonObject){
        try {
            this.deduction_name = jsonObject.getString("deduction_name");
            this.value = jsonObject.getString("value");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetPotongan> CREATOR = new Creator<EmployeeDetPotongan>() {
        @Override
        public EmployeeDetPotongan createFromParcel(Parcel in) {
            return new EmployeeDetPotongan(in);
        }

        @Override
        public EmployeeDetPotongan[] newArray(int size) {
            return new EmployeeDetPotongan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deduction_name);
        dest.writeString(value);
        dest.writeString(is_active);
    }

    public String getDeduction_name() {
        return deduction_name;
    }

    public String getValue() {
        return value;
    }

    public String getIs_active() {
        return is_active;
    }
}
