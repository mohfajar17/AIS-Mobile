package com.example.aismobile.Data.Marketing;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SalesOrderDetail implements Parcelable {

    private String mr;
    private String pr;
    private String tr;
    private String manPowerTemporary;
    private String manPowerKontrak;
    private String manPower;
    private String cod;
    private String wo;
    private String pbHalf;
    private String pb;
    private String cpr;
    private String expenses;
    private String invoice;
    private String payment;
    private String matret;
    private String job_order_number;
    private String job_order_description;
    private String budgeting_amount;
    private String amount;

    protected SalesOrderDetail(Parcel in) {
        mr = in.readString();
        pr = in.readString();
        tr = in.readString();
        manPowerTemporary = in.readString();
        manPowerKontrak = in.readString();
        manPower = in.readString();
        cod = in.readString();
        wo = in.readString();
        pbHalf = in.readString();
        pb = in.readString();
        cpr = in.readString();
        expenses = in.readString();
        invoice = in.readString();
        payment = in.readString();
        matret = in.readString();
        job_order_number = in.readString();
        job_order_description = in.readString();
        budgeting_amount = in.readString();
        amount = in.readString();
    }

    public SalesOrderDetail(JSONObject jsonObject){
        try {
            this.mr = jsonObject.getString("mr");
            this.pr = jsonObject.getString("pr");
            this.tr = jsonObject.getString("tr");
            this.manPowerTemporary = jsonObject.getString("manPowerTemporary");
            this.manPowerKontrak = jsonObject.getString("manPowerKontrak");
            this.manPower = jsonObject.getString("manPower");
            this.cod = jsonObject.getString("cod");
            this.wo = jsonObject.getString("wo");
            this.pbHalf = jsonObject.getString("pbHalf");
            this.pb = jsonObject.getString("pb");
            this.cpr = jsonObject.getString("cpr");
            this.expenses = jsonObject.getString("expenses");
            this.invoice = jsonObject.getString("invoice");
            this.payment = jsonObject.getString("payment");
            this.matret = jsonObject.getString("matret");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.budgeting_amount = jsonObject.getString("budgeting_amount");
            this.amount = jsonObject.getString("amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SalesOrderDetail> CREATOR = new Creator<SalesOrderDetail>() {
        @Override
        public SalesOrderDetail createFromParcel(Parcel in) {
            return new SalesOrderDetail(in);
        }

        @Override
        public SalesOrderDetail[] newArray(int size) {
            return new SalesOrderDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mr);
        dest.writeString(pr);
        dest.writeString(tr);
        dest.writeString(manPowerTemporary);
        dest.writeString(manPowerKontrak);
        dest.writeString(manPower);
        dest.writeString(cod);
        dest.writeString(wo);
        dest.writeString(pbHalf);
        dest.writeString(pb);
        dest.writeString(cpr);
        dest.writeString(expenses);
        dest.writeString(invoice);
        dest.writeString(payment);
        dest.writeString(matret);
        dest.writeString(job_order_number);
        dest.writeString(job_order_description);
        dest.writeString(budgeting_amount);
        dest.writeString(amount);
    }

    public String getMr() {
        return mr;
    }

    public String getPr() {
        return pr;
    }

    public String getTr() {
        return tr;
    }

    public String getManPowerTemporary() {
        return manPowerTemporary;
    }

    public String getManPowerKontrak() {
        return manPowerKontrak;
    }

    public String getManPower() {
        return manPower;
    }

    public String getCod() {
        return cod;
    }

    public String getWo() {
        return wo;
    }

    public String getPbHalf() {
        return pbHalf;
    }

    public String getPb() {
        return pb;
    }

    public String getCpr() {
        return cpr;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getPayment() {
        return payment;
    }

    public String getMatret() {
        return matret;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public String getBudgeting_amount() {
        return budgeting_amount;
    }

    public String getAmount() {
        return amount;
    }
}
