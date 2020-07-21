package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MaritalStatus implements Parcelable {

    private int marital_status_id;
    private String marital_status_name;
    private String minimum_amount;
    private String minimum_amount_maried;
    private String person_to_care;
    private String tax_amount;
    private String ptkp_tahunan;
    private String jpk_bulanan;
    private String umk_amount;

    public MaritalStatus (int marital_status_id, String marital_status_name, String minimum_amount, String minimum_amount_maried,
                      String person_to_care, String tax_amount, String ptkp_tahunan, String jpk_bulanan, String umk_amount){
        this.marital_status_id = marital_status_id;
        this.marital_status_name = marital_status_name;
        this.minimum_amount = minimum_amount;
        this.minimum_amount_maried = minimum_amount_maried;
        this.person_to_care = person_to_care;
        this.tax_amount = tax_amount;
        this.ptkp_tahunan = ptkp_tahunan;
        this.jpk_bulanan = jpk_bulanan;
        this.umk_amount = umk_amount;
    }
    protected MaritalStatus (Parcel in) {
        marital_status_id = in.readInt();
        marital_status_name = in.readString();
        minimum_amount = in.readString();
        minimum_amount_maried = in.readString();
        person_to_care = in.readString();
        tax_amount = in.readString();
        ptkp_tahunan = in.readString();
        jpk_bulanan = in.readString();
        umk_amount = in.readString();
    }

    public MaritalStatus (JSONObject jsonObject){
        try {
            this.marital_status_id = jsonObject.getInt("marital_status_id");
            this.marital_status_name = jsonObject.getString("marital_status_name");
            this.minimum_amount = jsonObject.getString("minimum_amount");
            this.minimum_amount_maried = jsonObject.getString("minimum_amount_maried");
            this.person_to_care = jsonObject.getString("person_to_care");
            this.tax_amount = jsonObject.getString("tax_amount");
            this.ptkp_tahunan = jsonObject.getString("ptkp_tahunan");
            this.jpk_bulanan = jsonObject.getString("jpk_bulanan");
            this.umk_amount = jsonObject.getString("umk_amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MaritalStatus> CREATOR = new Creator<MaritalStatus>() {
        @Override
        public MaritalStatus createFromParcel(Parcel in) {
            return new MaritalStatus(in);
        }

        @Override
        public MaritalStatus[] newArray(int size) {
            return new MaritalStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(marital_status_id);
        dest.writeString(marital_status_name);
        dest.writeString(minimum_amount);
        dest.writeString(minimum_amount_maried);
        dest.writeString(person_to_care);
        dest.writeString(tax_amount);
        dest.writeString(ptkp_tahunan);
        dest.writeString(jpk_bulanan);
        dest.writeString(umk_amount);
    }

    public int getMarital_status_id() {
        return marital_status_id;
    }

    public String getMarital_status_name() {
        return marital_status_name;
    }

    public String getMinimum_amount() {
        return minimum_amount;
    }

    public String getMinimum_amount_maried() {
        return minimum_amount_maried;
    }

    public String getPerson_to_care() {
        return person_to_care;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public String getPtkp_tahunan() {
        return ptkp_tahunan;
    }

    public String getJpk_bulanan() {
        return jpk_bulanan;
    }

    public String getUmk_amount() {
        return umk_amount;
    }
}
