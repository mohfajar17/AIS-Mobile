package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentSupplier implements Parcelable {

    private int budget_supplier_id;
    private String budget_supplier_number;
    private String start_date;
    private String end_date;
    private String checked_by;
    private String approval1;
    private String approval2;
    private String approval3;
    private String done;

    public PaymentSupplier(int budget_supplier_id, String budget_supplier_number, String start_date, String end_date,
                     String checked_by, String approval1, String approval2, String approval3, String done){
        this.budget_supplier_id = budget_supplier_id;
        this.budget_supplier_number = budget_supplier_number;
        this.start_date = start_date;
        this.end_date = end_date;
        this.checked_by = checked_by;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.approval3 = approval3;
        this.done = done;
    }

    protected PaymentSupplier(Parcel in) {
        budget_supplier_id = in.readInt();
        budget_supplier_number = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        checked_by = in.readString();
        approval1 = in.readString();
        approval2 = in.readString();
        approval3 = in.readString();
        done = in.readString();
    }

    public PaymentSupplier(JSONObject jsonObject){
        try {
            this.budget_supplier_id = jsonObject.getInt("budget_supplier_id");
            this.budget_supplier_number = jsonObject.getString("budget_supplier_number");
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

    public static final Creator<PaymentSupplier> CREATOR = new Creator<PaymentSupplier>() {
        @Override
        public PaymentSupplier createFromParcel(Parcel in) {
            return new PaymentSupplier(in);
        }

        @Override
        public PaymentSupplier[] newArray(int size) {
            return new PaymentSupplier[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(budget_supplier_id);
        dest.writeString(budget_supplier_number);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(checked_by);
        dest.writeString(approval1);
        dest.writeString(approval2);
        dest.writeString(approval3);
        dest.writeString(done);
    }

    public int getBudget_supplier_id() {
        return budget_supplier_id;
    }

    public String getBudget_supplier_number() {
        return budget_supplier_number;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getApproval2() {
        return approval2;
    }

    public String getApproval3() {
        return approval3;
    }

    public String getDone() {
        return done;
    }
}
