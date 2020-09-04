package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkCompletionDetail implements Parcelable {

    private int job_progress_report_detail_id;
    private String job_progress_report_id;
    private String description;
    private String quantity;
    private String unit_abbr;
    private String amount;
    private String status;
    private String account_job_progress_report_detail;

    protected WorkCompletionDetail(Parcel in) {
        job_progress_report_detail_id = in.readInt();
        job_progress_report_id = in.readString();
        description = in.readString();
        quantity = in.readString();
        unit_abbr = in.readString();
        amount = in.readString();
        status = in.readString();
        account_job_progress_report_detail = in.readString();
    }

    public WorkCompletionDetail(JSONObject jsonObject){
        try {
            this.job_progress_report_detail_id = jsonObject.getInt("job_progress_report_detail_id");
            this.job_progress_report_id = jsonObject.getString("job_progress_report_id");
            this.description = jsonObject.getString("description");
            this.quantity = jsonObject.getString("quantity");
            this.unit_abbr = jsonObject.getString("unit_abbr");
            this.amount = jsonObject.getString("amount");
            this.status = jsonObject.getString("status");
            this.account_job_progress_report_detail = jsonObject.getString("account_job_progress_report_detail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<WorkCompletionDetail> CREATOR = new Creator<WorkCompletionDetail>() {
        @Override
        public WorkCompletionDetail createFromParcel(Parcel in) {
            return new WorkCompletionDetail(in);
        }

        @Override
        public WorkCompletionDetail[] newArray(int size) {
            return new WorkCompletionDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(job_progress_report_detail_id);
        dest.writeString(job_progress_report_id);
        dest.writeString(description);
        dest.writeString(quantity);
        dest.writeString(unit_abbr);
        dest.writeString(amount);
        dest.writeString(status);
        dest.writeString(account_job_progress_report_detail);
    }

    public int getJob_progress_report_detail_id() {
        return job_progress_report_detail_id;
    }

    public String getJob_progress_report_id() {
        return job_progress_report_id;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit_abbr() {
        return unit_abbr;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getAccount_job_progress_report_detail() {
        return account_job_progress_report_detail;
    }
}
