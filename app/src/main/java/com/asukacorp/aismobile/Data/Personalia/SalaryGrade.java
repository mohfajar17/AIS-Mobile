package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SalaryGrade implements Parcelable {

    private int salary_grade_id;
    private String salary_grade_name;
    private String salary_grade_code;
    private String currency_code;
    private String basic_salary;
    private String basic_salary_per_day;
    private String overtime_rule;
    private String payment_cycle;

    public SalaryGrade (int salary_grade_id, String salary_grade_name, String salary_grade_code, String currency_code,
                      String basic_salary, String basic_salary_per_day, String overtime_rule, String payment_cycle){
        this.salary_grade_id = salary_grade_id;
        this.salary_grade_name = salary_grade_name;
        this.salary_grade_code = salary_grade_code;
        this.currency_code = currency_code;
        this.basic_salary = basic_salary;
        this.basic_salary_per_day = basic_salary_per_day;
        this.overtime_rule = overtime_rule;
        this.payment_cycle = payment_cycle;
    }
    protected SalaryGrade (Parcel in) {
        salary_grade_id = in.readInt();
        salary_grade_name = in.readString();
        salary_grade_code = in.readString();
        currency_code = in.readString();
        basic_salary = in.readString();
        basic_salary_per_day = in.readString();
        overtime_rule = in.readString();
        payment_cycle = in.readString();
    }

    public SalaryGrade (JSONObject jsonObject){
        try {
            this.salary_grade_id = jsonObject.getInt("salary_grade_id");
            this.salary_grade_name = jsonObject.getString("salary_grade_name");
            this.salary_grade_code = jsonObject.getString("salary_grade_code");
            this.currency_code = jsonObject.getString("currency_code");
            this.basic_salary = jsonObject.getString("basic_salary");
            this.basic_salary_per_day = jsonObject.getString("basic_salary_per_day");
            this.overtime_rule = jsonObject.getString("overtime_rule");
            this.payment_cycle = jsonObject.getString("payment_cycle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<SalaryGrade> CREATOR = new Creator<SalaryGrade>() {
        @Override
        public SalaryGrade createFromParcel(Parcel in) {
            return new SalaryGrade(in);
        }

        @Override
        public SalaryGrade[] newArray(int size) {
            return new SalaryGrade[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(salary_grade_id);
        dest.writeString(salary_grade_name);
        dest.writeString(salary_grade_code);
        dest.writeString(currency_code);
        dest.writeString(basic_salary);
        dest.writeString(basic_salary_per_day);
        dest.writeString(overtime_rule);
        dest.writeString(payment_cycle);
    }

    public int getSalary_grade_id() {
        return salary_grade_id;
    }

    public String getSalary_grade_name() {
        return salary_grade_name;
    }

    public String getSalary_grade_code() {
        return salary_grade_code;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public String getBasic_salary() {
        return basic_salary;
    }

    public String getBasic_salary_per_day() {
        return basic_salary_per_day;
    }

    public String getOvertime_rule() {
        return overtime_rule;
    }

    public String getPayment_cycle() {
        return payment_cycle;
    }
}
