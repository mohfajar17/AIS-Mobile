package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryContract implements Parcelable {

    private int history_contract_id;
    private String fullname;
    private String employee_grade_name;
    private String description;
    private String start_date;
    private String end_date;

    public HistoryContract (int history_contract_id, String fullname, String employee_grade_name, String description,
                            String start_date, String end_date){
        this.history_contract_id = history_contract_id;
        this.fullname = fullname;
        this.employee_grade_name = employee_grade_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    protected HistoryContract (Parcel in) {
        history_contract_id = in.readInt();
        fullname = in.readString();
        employee_grade_name = in.readString();
        description = in.readString();
        start_date = in.readString();
        end_date = in.readString();
    }

    public HistoryContract (JSONObject jsonObject){
        try {
            this.history_contract_id = jsonObject.getInt("history_contract_id");
            this.fullname = jsonObject.getString("fullname");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.description = jsonObject.getString("description");
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<HistoryContract> CREATOR = new Creator<HistoryContract>() {
        @Override
        public HistoryContract createFromParcel(Parcel in) {
            return new HistoryContract(in);
        }

        @Override
        public HistoryContract[] newArray(int size) {
            return new HistoryContract[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(history_contract_id);
        dest.writeString(fullname);
        dest.writeString(employee_grade_name);
        dest.writeString(description);
        dest.writeString(start_date);
        dest.writeString(end_date);
    }

    public int getHistory_contract_id() {
        return history_contract_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getDescription() {
        return description;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
