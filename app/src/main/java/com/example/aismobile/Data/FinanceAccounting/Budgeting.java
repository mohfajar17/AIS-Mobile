package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Budgeting implements Parcelable {

    private int budget_id;
    private String budget_number;
    private String created_by;
    private String start_date;
    private String end_date;
    private String checked_by;
    private String approval1;
    private String approval2;
    private String approval3;
    private String done;

    public Budgeting(int budget_id, String budget_number, String created_by, String start_date,
                   String end_date, String checked_by, String approval1, String approval2,
                   String approval3, String done){
        this.budget_id = budget_id;
        this.budget_number = budget_number;
        this.created_by = created_by;
        this.start_date = start_date;
        this.end_date = end_date;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.approval3 = approval3;
        this.done = done;
    }

    protected Budgeting(Parcel in) {
        budget_id = in.readInt();
        budget_number = in.readString();
        created_by = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        checked_by = in.readString();
        approval1 = in.readString();
        approval2 = in.readString();
        approval3 = in.readString();
        done = in.readString();
    }

    public Budgeting(JSONObject jsonObject){
        try {
            this.budget_id = jsonObject.getInt("budget_id");
            this.budget_number = jsonObject.getString("budget_number");
            this.created_by = jsonObject.getString("created_by");
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
            this.checked_by = jsonObject.getString("checked_by");
            this.approval1 = jsonObject.getString("approval1");
            this.approval2 = jsonObject.getString("approval2");
            this.approval3 = jsonObject.getString("approval3");
            this.done = jsonObject.getString("done");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Budgeting> CREATOR = new Creator<Budgeting>() {
        @Override
        public Budgeting createFromParcel(Parcel in) {
            return new Budgeting(in);
        }

        @Override
        public Budgeting[] newArray(int size) {
            return new Budgeting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(budget_id);
        dest.writeString(budget_number);
        dest.writeString(created_by);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(checked_by);
        dest.writeString(approval1);
        dest.writeString(approval2);
        dest.writeString(approval3);
        dest.writeString(done);
    }
}
