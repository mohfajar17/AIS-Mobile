package com.example.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Company implements Parcelable {

    private int company_id;
    private String company_name;
    private String company_code;
    private String company_address;
    private String company_city;
    private String company_email;

    public Company(int company_id, String company_name, String company_code, String company_address,
                   String company_city, String company_email){
        this.company_id = company_id;
        this.company_name = company_name;
        this.company_code = company_code;
        this.company_address = company_address;
        this.company_city = company_city;
        this.company_email = company_email;
    }

    protected Company(Parcel in) {
        company_id = in.readInt();
        company_name = in.readString();
        company_code = in.readString();
        company_address = in.readString();
        company_city = in.readString();
        company_email = in.readString();
    }

    public Company(JSONObject jsonObject){
        try {
            this.company_id = jsonObject.getInt("company_id");
            this.company_name = jsonObject.getString("company_name");
            this.company_code = jsonObject.getString("company_code");
            this.company_address = jsonObject.getString("company_address");
            this.company_city = jsonObject.getString("company_city");
            this.company_email = jsonObject.getString("company_email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(company_id);
        dest.writeString(company_name);
        dest.writeString(company_code);
        dest.writeString(company_address);
        dest.writeString(company_city);
        dest.writeString(company_email);
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_code() {
        return company_code;
    }

    public String getCompany_address() {
        return company_address;
    }

    public String getCompany_city() {
        return company_city;
    }

    public String getCompany_email() {
        return company_email;
    }
}
