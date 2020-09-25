package com.example.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Employee implements Parcelable {

    private int employee_id;
    private String employee_number;
    private String fullname;
    private String employee_grade_name;
    private String employee_status;
    private String department_name;
    private String company_workbase_name;
    private String join_date;
    private String termination_date;
    private String is_active;
    private String working_status;
    private String nickname;
    private String gender;
    private String place_birthday;
    private String birthday;
    private String religion_name;
    private String address;
    private String home_phone;
    private String mobile_phone;
    private String email1;
    private String no_rek;
    private String sin_num;
    private String sin_expiry_date;
    private String citizenship;
    private String city;
    private String state;
    private String country;
    private String origin_city_ktp;
    private String marital_status_name;
    private String employee_check_id;
    private String jobtitle_name;
    private String current_basic_salary;
    private String social_security_number;
    private String bpjs_health_number;
    private String npwp;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String employee_file_name;
    private String employment_date;
    private String termination_reason;
    private String come_out_date;
    private String notes;

    public Employee (int employee_id, String employee_number, String fullname, String employee_grade_name,
                     String employee_status, String department_name, String company_workbase_name, String join_date,
                     String termination_date, String is_active, String working_status){
        this.employee_id = employee_id;
        this.employee_number = employee_number;
        this.fullname = fullname;
        this.employee_grade_name = employee_grade_name;
        this.employee_status = employee_status;
        this.department_name = department_name;
        this.company_workbase_name = company_workbase_name;
        this.join_date = join_date;
        this.termination_date = termination_date;
        this.is_active = is_active;
        this.working_status = working_status;
    }
    protected Employee (Parcel in) {
        employee_id = in.readInt();
        employee_number = in.readString();
        fullname = in.readString();
        employee_grade_name = in.readString();
        employee_status = in.readString();
        department_name = in.readString();
        company_workbase_name = in.readString();
        join_date = in.readString();
        termination_date = in.readString();
        is_active = in.readString();
        working_status = in.readString();
        nickname = in.readString();
        gender = in.readString();
        place_birthday = in.readString();
        birthday = in.readString();
        religion_name = in.readString();
        address = in.readString();
        home_phone = in.readString();
        mobile_phone = in.readString();
        email1 = in.readString();
        no_rek = in.readString();
        sin_num = in.readString();
        sin_expiry_date = in.readString();
        citizenship = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        origin_city_ktp = in.readString();
        marital_status_name = in.readString();
        employee_check_id = in.readString();
        jobtitle_name = in.readString();
        current_basic_salary = in.readString();
        social_security_number = in.readString();
        bpjs_health_number = in.readString();
        npwp = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        employee_file_name = in.readString();
        employment_date = in.readString();
        termination_reason = in.readString();
        come_out_date = in.readString();
        notes = in.readString();
    }

    public Employee (JSONObject jsonObject){
        try {
            this.employee_id = jsonObject.getInt("employee_id");
            this.employee_number = jsonObject.getString("employee_number");
            this.fullname = jsonObject.getString("fullname");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.employee_status = jsonObject.getString("employee_status");
            this.department_name = jsonObject.getString("department_name");
            this.company_workbase_name = jsonObject.getString("company_workbase_name");
            this.join_date = jsonObject.getString("join_date");
            this.termination_date = jsonObject.getString("termination_date");
            this.is_active = jsonObject.getString("is_active");
            this.working_status = jsonObject.getString("working_status");
            this.nickname = jsonObject.getString("nickname");
            this.gender = jsonObject.getString("gender");
            this.place_birthday = jsonObject.getString("place_birthday");
            this.birthday = jsonObject.getString("birthday");
            this.religion_name = jsonObject.getString("religion_name");
            this.address = jsonObject.getString("address");
            this.home_phone = jsonObject.getString("home_phone");
            this.mobile_phone = jsonObject.getString("mobile_phone");
            this.email1 = jsonObject.getString("email1");
            this.no_rek = jsonObject.getString("no_rek");
            this.sin_num = jsonObject.getString("sin_num");
            this.sin_expiry_date = jsonObject.getString("sin_expiry_date");
            this.citizenship = jsonObject.getString("citizenship");
            this.city = jsonObject.getString("city");
            this.state = jsonObject.getString("state");
            this.country = jsonObject.getString("country");
            this.origin_city_ktp = jsonObject.getString("origin_city_ktp");
            this.marital_status_name = jsonObject.getString("marital_status_name");
            this.employee_check_id = jsonObject.getString("employee_check_id");
            this.jobtitle_name = jsonObject.getString("jobtitle_name");
            this.current_basic_salary = jsonObject.getString("current_basic_salary");
            this.social_security_number = jsonObject.getString("social_security_number");
            this.bpjs_health_number = jsonObject.getString("bpjs_health_number");
            this.npwp = jsonObject.getString("npwp");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.employee_file_name = jsonObject.getString("employee_file_name");
            this.employment_date = jsonObject.getString("employment_date");
            this.termination_reason = jsonObject.getString("termination_reason");
            this.come_out_date = jsonObject.getString("come_out_date");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_id);
        dest.writeString(employee_number);
        dest.writeString(fullname);
        dest.writeString(employee_grade_name);
        dest.writeString(employee_status);
        dest.writeString(department_name);
        dest.writeString(company_workbase_name);
        dest.writeString(join_date);
        dest.writeString(termination_date);
        dest.writeString(is_active);
        dest.writeString(working_status);
        dest.writeString(nickname);
        dest.writeString(gender);
        dest.writeString(place_birthday);
        dest.writeString(birthday);
        dest.writeString(religion_name);
        dest.writeString(address);
        dest.writeString(home_phone);
        dest.writeString(mobile_phone);
        dest.writeString(email1);
        dest.writeString(no_rek);
        dest.writeString(sin_num);
        dest.writeString(sin_expiry_date);
        dest.writeString(citizenship);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(origin_city_ktp);
        dest.writeString(marital_status_name);
        dest.writeString(employee_check_id);
        dest.writeString(jobtitle_name);
        dest.writeString(current_basic_salary);
        dest.writeString(social_security_number);
        dest.writeString(bpjs_health_number);
        dest.writeString(npwp);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(employee_file_name);
        dest.writeString(employment_date);
        dest.writeString(termination_reason);
        dest.writeString(come_out_date);
        dest.writeString(notes);
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getEmployee_status() {
        return employee_status;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getCompany_workbase_name() {
        return company_workbase_name;
    }

    public String getJoin_date() {
        return join_date;
    }

    public String getTermination_date() {
        return termination_date;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getWorking_status() {
        return working_status;
    }

    public String getNickname() {
        return nickname;
    }

    public String getGender() {
        return gender;
    }

    public String getPlace_birthday() {
        return place_birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getReligion_name() {
        return religion_name;
    }

    public String getAddress() {
        return address;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public String getEmail1() {
        return email1;
    }

    public String getNo_rek() {
        return no_rek;
    }

    public String getSin_num() {
        return sin_num;
    }

    public String getSin_expiry_date() {
        return sin_expiry_date;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getOrigin_city_ktp() {
        return origin_city_ktp;
    }

    public String getMarital_status_name() {
        return marital_status_name;
    }

    public String getEmployee_check_id() {
        return employee_check_id;
    }

    public String getJobtitle_name() {
        return jobtitle_name;
    }

    public String getCurrent_basic_salary() {
        return current_basic_salary;
    }

    public String getSocial_security_number() {
        return social_security_number;
    }

    public String getBpjs_health_number() {
        return bpjs_health_number;
    }

    public String getNpwp() {
        return npwp;
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

    public String getEmployee_file_name() {
        return employee_file_name;
    }

    public String getEmployment_date() {
        return employment_date;
    }

    public String getTermination_reason() {
        return termination_reason;
    }

    public String getCome_out_date() {
        return come_out_date;
    }

    public String getNotes() {
        return notes;
    }
}
