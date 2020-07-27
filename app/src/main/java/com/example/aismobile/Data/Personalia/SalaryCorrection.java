package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SalaryCorrection implements Parcelable {

    private int salary_correction_id;
    private String amount;
    private String notes;
    private String report_name;
    private String is_deduction;

    public SalaryCorrection (int salary_correction_id, String amount, String notes, String report_name,
                      String is_deduction){
        this.salary_correction_id = salary_correction_id;
        this.amount = amount;
        this.notes = notes;
        this.report_name = report_name;
        this.is_deduction = is_deduction;
    }
    protected SalaryCorrection (Parcel in) {
        salary_correction_id = in.readInt();
        amount = in.readString();
        notes = in.readString();
        report_name = in.readString();
        is_deduction = in.readString();
    }

    public SalaryCorrection (JSONObject jsonObject){
        try {
            this.salary_correction_id = jsonObject.getInt("salary_correction_id");
            this.amount = jsonObject.getString("amount");
            this.notes = jsonObject.getString("notes");
            this.report_name = jsonObject.getString("report_name");
            this.is_deduction = jsonObject.getString("is_deduction");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SalaryCorrection> CREATOR = new Creator<SalaryCorrection>() {
        @Override
        public SalaryCorrection createFromParcel(Parcel in) {
            return new SalaryCorrection(in);
        }

        @Override
        public SalaryCorrection[] newArray(int size) {
            return new SalaryCorrection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(salary_correction_id);
        dest.writeString(amount);
        dest.writeString(notes);
        dest.writeString(report_name);
        dest.writeString(is_deduction);
    }

    public int getSalary_correction_id() {
        return salary_correction_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }

    public String getReport_name() {
        return report_name;
    }

    public String getIs_deduction() {
        return is_deduction;
    }
}
