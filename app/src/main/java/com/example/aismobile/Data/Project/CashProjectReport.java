package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CashProjectReport implements Parcelable {

    private int responsbility_advance_id;
    private String responsbility_advance_number;
    private String cash_advance_id;
    private String job_order_id;
    private String job_order_number;
    private String begin_date;
    private String end_date;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String approval1;
    private String approval_date1;
    private String approval_comment1;
    private String approval2;
    private String approval_date2;
    private String approval_comment2;
    private String approval3;
    private String approval_date3;
    private String approval_comment3;
    private String checked_by;
    private String checked_date;
    private String done;
    private String ra_archive;
    private String ra_file_name;
    private String ra_file_type;
    private String bank_transaction_type_id;
    private String category;
    private String ra_discount_type;
    private String cash_advance_number;

    public CashProjectReport(int responsbility_advance_id, String responsbility_advance_number, String cash_advance_id, String job_order_id, String job_order_number,
                             String begin_date, String end_date, String notes, String created_by, String created_date,
                             String modified_by, String modified_date, String approval1, String approval_date1,
                             String approval_comment1, String approval2, String approval_date2, String approval_comment2,
                             String approval3, String approval_date3, String approval_comment3, String checked_by,
                             String checked_date, String done, String ra_archive, String ra_file_name, String ra_file_type,
                             String bank_transaction_type_id, String category, String ra_discount_type){
        this.responsbility_advance_id = responsbility_advance_id;
        this.responsbility_advance_number = responsbility_advance_number;
        this.cash_advance_id = cash_advance_id;
        this.job_order_id = job_order_id;
        this.job_order_number = job_order_number;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.approval1 = approval1;
        this.approval_date1 = approval_date1;
        this.approval_comment1 = approval_comment1;
        this.approval2 = approval2;
        this.approval_date2 = approval_date2;
        this.approval_comment2 = approval_comment2;
        this.approval3 = approval3;
        this.approval_date3 = approval_date3;
        this.approval_comment3 = approval_comment3;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.done = done;
        this.ra_archive = ra_archive;
        this.ra_file_name = ra_file_name;
        this.ra_file_type = ra_file_type;
        this.bank_transaction_type_id = bank_transaction_type_id;
        this.category = category;
        this.ra_discount_type = ra_discount_type;
    }

    protected CashProjectReport(Parcel in) {
        responsbility_advance_id = in.readInt();
        responsbility_advance_number = in.readString();
        cash_advance_id = in.readString();
        job_order_id = in.readString();
        job_order_number = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        approval1 = in.readString();
        approval_date1 = in.readString();
        approval_comment1 = in.readString();
        approval2 = in.readString();
        approval_date2 = in.readString();
        approval_comment2 = in.readString();
        approval3 = in.readString();
        approval_date3 = in.readString();
        approval_comment3 = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        done = in.readString();
        ra_archive = in.readString();
        ra_file_name = in.readString();
        ra_file_type = in.readString();
        bank_transaction_type_id = in.readString();
        category = in.readString();
        ra_discount_type = in.readString();
        cash_advance_number = in.readString();
    }

    public CashProjectReport(JSONObject jsonObject){
        try {
            this.responsbility_advance_id = jsonObject.getInt("responsbility_advance_id");
            this.responsbility_advance_number = jsonObject.getString("responsbility_advance_number");
            this.cash_advance_id = jsonObject.getString("cash_advance_id");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.approval1 = jsonObject.getString("approval1");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
            this.approval2 = jsonObject.getString("approval2");
            this.approval_date2 = jsonObject.getString("approval_date2");
            this.approval_comment2 = jsonObject.getString("approval_comment2");
            this.approval3 = jsonObject.getString("approval3");
            this.approval_date3 = jsonObject.getString("approval_date3");
            this.approval_comment3 = jsonObject.getString("approval_comment3");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.done = jsonObject.getString("done");
            this.ra_archive = jsonObject.getString("ra_archive");
            this.ra_file_name = jsonObject.getString("ra_file_name");
            this.ra_file_type = jsonObject.getString("ra_file_type");
            this.bank_transaction_type_id = jsonObject.getString("bank_transaction_type_id");
            this.category = jsonObject.getString("category");
            this.ra_discount_type = jsonObject.getString("ra_discount_type");
            this.cash_advance_number = jsonObject.getString("cash_advance_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CashProjectReport> CREATOR = new Creator<CashProjectReport>() {
        @Override
        public CashProjectReport createFromParcel(Parcel in) {
            return new CashProjectReport(in);
        }

        @Override
        public CashProjectReport[] newArray(int size) {
            return new CashProjectReport[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(responsbility_advance_id);
        dest.writeString(responsbility_advance_number);
        dest.writeString(cash_advance_id);
        dest.writeString(job_order_id);
        dest.writeString(job_order_number);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(approval1);
        dest.writeString(approval_date1);
        dest.writeString(approval_comment1);
        dest.writeString(approval2);
        dest.writeString(approval_date2);
        dest.writeString(approval_comment2);
        dest.writeString(approval3);
        dest.writeString(approval_date3);
        dest.writeString(approval_comment3);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(done);
        dest.writeString(ra_archive);
        dest.writeString(ra_file_name);
        dest.writeString(ra_file_type);
        dest.writeString(bank_transaction_type_id);
        dest.writeString(category);
        dest.writeString(ra_discount_type);
        dest.writeString(cash_advance_number);
    }

    public int getResponsbility_advance_id() {
        return responsbility_advance_id;
    }

    public String getResponsbility_advance_number() {
        return responsbility_advance_number;
    }

    public String getCash_advance_id() {
        return cash_advance_id;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getNotes() {
        return notes;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }

    public String getApproval2() {
        return approval2;
    }

    public String getApproval_date2() {
        return approval_date2;
    }

    public String getApproval_comment2() {
        return approval_comment2;
    }

    public String getApproval3() {
        return approval3;
    }

    public String getApproval_date3() {
        return approval_date3;
    }

    public String getApproval_comment3() {
        return approval_comment3;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getDone() {
        return done;
    }

    public String getRa_archive() {
        return ra_archive;
    }

    public String getRa_file_name() {
        return ra_file_name;
    }

    public String getRa_file_type() {
        return ra_file_type;
    }

    public String getBank_transaction_type_id() {
        return bank_transaction_type_id;
    }

    public String getCategory() {
        return category;
    }

    public String getRa_discount_type() {
        return ra_discount_type;
    }

    public String getCash_advance_number() {
        return cash_advance_number;
    }

    public void setApproval1(String approval1) {
        this.approval1 = approval1;
    }

    public void setApproval2(String approval2) {
        this.approval2 = approval2;
    }

    public void setApproval3(String approval3) {
        this.approval3 = approval3;
    }
}
