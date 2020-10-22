package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class BudgetingDetail implements Parcelable {
    private String budget_detail_id;
    private String description;
    private String department_id;
    private String company_workbase_id;
    private String to_department;
    private String amount;
    private String budget_app1;
    private String budget_app2;
    private String budget_app3;

    protected BudgetingDetail(Parcel in) {
        budget_detail_id = in.readString();
        description = in.readString();
        department_id = in.readString();
        company_workbase_id = in.readString();
        to_department = in.readString();
        amount = in.readString();
        budget_app1 = in.readString();
        budget_app2 = in.readString();
        budget_app3 = in.readString();
    }

    public BudgetingDetail(JSONObject jsonObject){
        try {
            this.budget_detail_id = jsonObject.getString("budget_detail_id");
            this.description = jsonObject.getString("description");
            this.department_id = jsonObject.getString("department_id");
            this.company_workbase_id = jsonObject.getString("company_workbase_id");
            this.to_department = jsonObject.getString("to_department");
            this.amount = jsonObject.getString("amount");
            this.budget_app1 = jsonObject.getString("budget_app1");
            this.budget_app2 = jsonObject.getString("budget_app2");
            this.budget_app3 = jsonObject.getString("budget_app3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<BudgetingDetail> CREATOR = new Creator<BudgetingDetail>() {
        @Override
        public BudgetingDetail createFromParcel(Parcel in) {
            return new BudgetingDetail(in);
        }

        @Override
        public BudgetingDetail[] newArray(int size) {
            return new BudgetingDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(budget_detail_id);
        dest.writeString(description);
        dest.writeString(department_id);
        dest.writeString(company_workbase_id);
        dest.writeString(to_department);
        dest.writeString(amount);
        dest.writeString(budget_app1);
        dest.writeString(budget_app2);
        dest.writeString(budget_app3);
    }

    public String getBudget_detail_id() {
        return budget_detail_id;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public String getCompany_workbase_id() {
        return company_workbase_id;
    }

    public String getTo_department() {
        return to_department;
    }

    public String getAmount() {
        return amount;
    }

    public String getBudget_app1() {
        return budget_app1;
    }

    public String getBudget_app2() {
        return budget_app2;
    }

    public String getBudget_app3() {
        return budget_app3;
    }
}
