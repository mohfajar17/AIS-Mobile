package com.asukacorp.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeReport implements Parcelable {

    private int employee_report_id;
    private String month_year;
    private String employee;
    private String job_order_number;
    private String company_workbase_name;
    private String basic_salary;
    private String total_tunjangan;
    private String overtime;
    private String absent;
    private String less_payment;
    private String total_pendapatan;
    private String location_project_allowance;
    private String official_travel_allowance;
    private String total_potongan;
    private String total_dibayar;
    private String total_biaya;
    private String person;
    private String employee_status;

    public EmployeeReport (int employee_report_id, String month_year, String employee, String job_order_number,
                           String company_workbase_name, String basic_salary, String total_tunjangan, String overtime,
                           String absent, String less_payment, String total_pendapatan, String location_project_allowance,
                           String official_travel_allowance, String total_potongan, String total_dibayar,
                           String total_biaya, String person, String employee_status){
        this.employee_report_id = employee_report_id;
        this.month_year = month_year;
        this.employee = employee;
        this.job_order_number = job_order_number;
        this.company_workbase_name = company_workbase_name;
        this.basic_salary = basic_salary;
        this.total_tunjangan = total_tunjangan;
        this.overtime = overtime;
        this.absent = absent;
        this.less_payment = less_payment;
        this.total_pendapatan = total_pendapatan;
        this.location_project_allowance = location_project_allowance;
        this.official_travel_allowance = official_travel_allowance;
        this.total_potongan = total_potongan;
        this.total_dibayar = total_dibayar;
        this.total_biaya = total_biaya;
        this.person = person;
        this.employee_status = employee_status;
    }
    protected EmployeeReport (Parcel in) {
        employee_report_id = in.readInt();
        month_year = in.readString();
        employee = in.readString();
        job_order_number = in.readString();
        company_workbase_name = in.readString();
        basic_salary = in.readString();
        overtime = in.readString();
        absent = in.readString();
        less_payment = in.readString();
        total_pendapatan = in.readString();
        location_project_allowance = in.readString();
        official_travel_allowance = in.readString();
        total_potongan = in.readString();
        total_dibayar = in.readString();
        total_biaya = in.readString();
        person = in.readString();
        employee_status = in.readString();
    }

    public EmployeeReport (JSONObject jsonObject){
        try {
            this.employee_report_id = jsonObject.getInt("employee_report_id");
            this.month_year = jsonObject.getString("month_year");
            this.employee = jsonObject.getString("employee");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.company_workbase_name = jsonObject.getString("company_workbase_name");
            this.basic_salary = jsonObject.getString("basic_salary");
            this.total_tunjangan = jsonObject.getString("total_tunjangan");
            this.overtime = jsonObject.getString("overtime");
            this.absent = jsonObject.getString("absent");
            this.less_payment = jsonObject.getString("less_payment");
            this.total_pendapatan = jsonObject.getString("total_pendapatan");
            this.location_project_allowance = jsonObject.getString("location_project_allowance");
            this.official_travel_allowance = jsonObject.getString("official_travel_allowance");
            this.total_potongan = jsonObject.getString("total_potongan");
            this.total_dibayar = jsonObject.getString("total_dibayar");
            this.total_biaya = jsonObject.getString("total_biaya");
            this.person = jsonObject.getString("person");
            this.employee_status = jsonObject.getString("employee_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeReport> CREATOR = new Creator<EmployeeReport>() {
        @Override
        public EmployeeReport createFromParcel(Parcel in) {
            return new EmployeeReport(in);
        }

        @Override
        public EmployeeReport[] newArray(int size) {
            return new EmployeeReport[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_report_id);
        dest.writeString(month_year);
        dest.writeString(employee);
        dest.writeString(job_order_number);
        dest.writeString(company_workbase_name);
        dest.writeString(basic_salary);
        dest.writeString(total_tunjangan);
        dest.writeString(overtime);
        dest.writeString(absent);
        dest.writeString(less_payment);
        dest.writeString(total_pendapatan);
        dest.writeString(location_project_allowance);
        dest.writeString(official_travel_allowance);
        dest.writeString(total_potongan);
        dest.writeString(total_dibayar);
        dest.writeString(total_biaya);
        dest.writeString(person);
        dest.writeString(employee_status);
    }

    public int getEmployee_report_id() {
        return employee_report_id;
    }

    public String getMonth_year() {
        return month_year;
    }

    public String getEmployee() {
        return employee;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getCompany_workbase_name() {
        return company_workbase_name;
    }

    public String getBasic_salary() {
        return basic_salary;
    }

    public String getTotal_tunjangan() {
        return total_tunjangan;
    }

    public String getOvertime() {
        return overtime;
    }

    public String getAbsent() {
        return absent;
    }

    public String getLess_payment() {
        return less_payment;
    }

    public String getTotal_pendapatan() {
        return total_pendapatan;
    }

    public String getLocation_project_allowance() {
        return location_project_allowance;
    }

    public String getOfficial_travel_allowance() {
        return official_travel_allowance;
    }

    public String getTotal_potongan() {
        return total_potongan;
    }

    public String getTotal_dibayar() {
        return total_dibayar;
    }

    public String getTotal_biaya() {
        return total_biaya;
    }

    public String getPerson() {
        return person;
    }

    public String getEmployee_status() {
        return employee_status;
    }
}