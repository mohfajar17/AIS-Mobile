package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Department implements Parcelable {

    private int department_id;
    private String department_code;
    private String code_for_item;
    private String department_name;
    private String head_department;
    private String department_notes;
    private String atasan;
    private String company_name;

    public Department (int department_id, String department_code, String code_for_item, String department_name,
                       String head_department, String department_notes, String atasan, String company_name){
        this.department_id = department_id;
        this.department_code = department_code;
        this.code_for_item = code_for_item;
        this.department_name = department_name;
        this.head_department = head_department;
        this.department_notes = department_notes;
        this.atasan = atasan;
        this.company_name = company_name;
    }
    protected Department (Parcel in) {
        department_id = in.readInt();
        department_code = in.readString();
        code_for_item = in.readString();
        department_name = in.readString();
        head_department = in.readString();
        department_notes = in.readString();
        atasan = in.readString();
        company_name = in.readString();
    }

    public Department (JSONObject jsonObject){
        try {
            this.department_id = jsonObject.getInt("department_id");
            this.department_code = jsonObject.getString("department_code");
            this.code_for_item = jsonObject.getString("code_for_item");
            this.department_name = jsonObject.getString("department_name");
            this.head_department = jsonObject.getString("head_department");
            this.department_notes = jsonObject.getString("department_notes");
            this.atasan = jsonObject.getString("atasan");
            this.company_name = jsonObject.getString("company_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(department_id);
        dest.writeString(department_code);
        dest.writeString(code_for_item);
        dest.writeString(department_name);
        dest.writeString(head_department);
        dest.writeString(department_notes);
        dest.writeString(atasan);
        dest.writeString(company_name);
    }

    public int getDepartment_id() {
        return department_id;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public String getCode_for_item() {
        return code_for_item;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getHead_department() {
        return head_department;
    }

    public String getDepartment_notes() {
        return department_notes;
    }

    public String getAtasan() {
        return atasan;
    }

    public String getCompany_name() {
        return company_name;
    }
}
