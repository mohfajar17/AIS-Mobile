package com.asukacorp.aismobile.Data.Contact;

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
    private String npwp;
    private String company_address_sppkp;
    private String company_state;
    private String company_country;
    private String company_zipcode;
    private String company_phone1;
    private String company_phone2;
    private String company_fax;
    private String company_www;
    private String company_notes;

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
        npwp = in.readString();
        company_address_sppkp = in.readString();
        company_state = in.readString();
        company_country = in.readString();
        company_zipcode = in.readString();
        company_phone1 = in.readString();
        company_phone2 = in.readString();
        company_fax = in.readString();
        company_www = in.readString();
        company_notes = in.readString();
    }

    public Company(JSONObject jsonObject){
        try {
            this.company_id = jsonObject.getInt("company_id");
            this.company_name = jsonObject.getString("company_name");
            this.company_code = jsonObject.getString("company_code");
            this.company_address = jsonObject.getString("company_address");
            this.company_city = jsonObject.getString("company_city");
            this.company_email = jsonObject.getString("company_email");
            this.npwp = jsonObject.getString("npwp");
            this.company_address_sppkp = jsonObject.getString("company_address_sppkp");
            this.company_state = jsonObject.getString("company_state");
            this.company_country = jsonObject.getString("company_country");
            this.company_zipcode = jsonObject.getString("company_zipcode");
            this.company_phone1 = jsonObject.getString("company_phone1");
            this.company_phone2 = jsonObject.getString("company_phone2");
            this.company_fax = jsonObject.getString("company_fax");
            this.company_www = jsonObject.getString("company_www");
            this.company_notes = jsonObject.getString("company_notes");
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
        dest.writeString(npwp);
        dest.writeString(company_address_sppkp);
        dest.writeString(company_state);
        dest.writeString(company_country);
        dest.writeString(company_zipcode);
        dest.writeString(company_phone1);
        dest.writeString(company_phone2);
        dest.writeString(company_fax);
        dest.writeString(company_www);
        dest.writeString(company_notes);
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

    public String getNpwp() {
        return npwp;
    }

    public String getCompany_address_sppkp() {
        return company_address_sppkp;
    }

    public String getCompany_state() {
        return company_state;
    }

    public String getCompany_country() {
        return company_country;
    }

    public String getCompany_zipcode() {
        return company_zipcode;
    }

    public String getCompany_phone1() {
        return company_phone1;
    }

    public String getCompany_phone2() {
        return company_phone2;
    }

    public String getCompany_fax() {
        return company_fax;
    }

    public String getCompany_www() {
        return company_www;
    }

    public String getCompany_notes() {
        return company_notes;
    }
}
