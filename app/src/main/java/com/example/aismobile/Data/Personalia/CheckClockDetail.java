package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckClockDetail implements Parcelable {
    private String job_order;
    private String daily_wages;
    private String date_check_clock;
    private String day;
    private String check_in;
    private String check_out;
    private String start_time;
    private String finish_time;
    private String emergency_call;
    private String no_lunch;
    private String note_for_shift;
    private String shift_category;
    private String type_work_hour;
    private String permission_late;

    protected CheckClockDetail (Parcel in) {
        job_order = in.readString();
        daily_wages = in.readString();
        date_check_clock = in.readString();
        day = in.readString();
        check_in = in.readString();
        check_out = in.readString();
        start_time = in.readString();
        finish_time = in.readString();
        emergency_call = in.readString();
        no_lunch = in.readString();
        note_for_shift = in.readString();
        shift_category = in.readString();
        type_work_hour = in.readString();
        permission_late = in.readString();
    }

    public CheckClockDetail (JSONObject jsonObject){
        try {
            this.job_order = jsonObject.getString("job_order");
            this.daily_wages = jsonObject.getString("daily_wages");
            this.date_check_clock = jsonObject.getString("date_check_clock");
            this.day = jsonObject.getString("day");
            this.check_in = jsonObject.getString("check_in");
            this.check_out = jsonObject.getString("check_out");
            this.start_time = jsonObject.getString("start_time");
            this.finish_time = jsonObject.getString("finish_time");
            this.emergency_call = jsonObject.getString("emergency_call");
            this.no_lunch = jsonObject.getString("no_lunch");
            this.note_for_shift = jsonObject.getString("note_for_shift");
            this.shift_category = jsonObject.getString("shift_category");
            this.type_work_hour = jsonObject.getString("type_work_hour");
            this.permission_late = jsonObject.getString("permission_late");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CheckClockDetail> CREATOR = new Creator<CheckClockDetail>() {
        @Override
        public CheckClockDetail createFromParcel(Parcel in) {
            return new CheckClockDetail(in);
        }

        @Override
        public CheckClockDetail[] newArray(int size) {
            return new CheckClockDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(job_order);
        dest.writeString(daily_wages);
        dest.writeString(date_check_clock);
        dest.writeString(day);
        dest.writeString(check_in);
        dest.writeString(check_out);
        dest.writeString(start_time);
        dest.writeString(finish_time);
        dest.writeString(emergency_call);
        dest.writeString(no_lunch);
        dest.writeString(note_for_shift);
        dest.writeString(shift_category);
        dest.writeString(type_work_hour);
        dest.writeString(permission_late);
    }

    public String getJob_order() {
        return job_order;
    }

    public String getDaily_wages() {
        return daily_wages;
    }

    public String getDate_check_clock() {
        return date_check_clock;
    }

    public String getDay() {
        return day;
    }

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getEmergency_call() {
        return emergency_call;
    }

    public String getNo_lunch() {
        return no_lunch;
    }

    public String getNote_for_shift() {
        return note_for_shift;
    }

    public String getShift_category() {
        return shift_category;
    }

    public String getType_work_hour() {
        return type_work_hour;
    }

    public String getPermission_late() {
        return permission_late;
    }
}
