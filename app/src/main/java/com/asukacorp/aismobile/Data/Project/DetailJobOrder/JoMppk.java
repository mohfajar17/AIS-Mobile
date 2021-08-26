package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoMppk implements Parcelable {

    private int employee_report_id;
    private String year;
    private String GP;
    private String AB;
    private String GL;
    private String Tunjangan;
    private String jumlah;
    private String project_location;

    public JoMppk(int employee_report_id, String year, String GP, String AB, String GL, String Tunjangan,
                String jumlah, String project_location){
        this.employee_report_id = employee_report_id;
        this.year = year;
        this.GP = GP;
        this.AB = AB;
        this.GL = GL;
        this.Tunjangan = Tunjangan;
        this.jumlah = jumlah;
        this.project_location = project_location;
    }

    protected JoMppk(Parcel in) {
        employee_report_id = in.readInt();
        year = in.readString();
        GP = in.readString();
        AB = in.readString();
        GL = in.readString();
        Tunjangan = in.readString();
        jumlah = in.readString();
        project_location = in.readString();
    }

    public JoMppk(JSONObject jsonObject){
        try {
            this.employee_report_id = jsonObject.getInt("employee_report_id");
            this.year = jsonObject.getString("year");
            this.GP = jsonObject.getString("GP");
            this.AB = jsonObject.getString("AB");
            this.GL = jsonObject.getString("GL");
            this.Tunjangan = jsonObject.getString("Tunjangan");
            this.jumlah = jsonObject.getString("jumlah");
            this.project_location = jsonObject.getString("project_location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoMppk> CREATOR = new Creator<JoMppk>() {
        @Override
        public JoMppk createFromParcel(Parcel in) {
            return new JoMppk(in);
        }

        @Override
        public JoMppk[] newArray(int size) {
            return new JoMppk[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employee_report_id);
        dest.writeString(year);
        dest.writeString(GP);
        dest.writeString(AB);
        dest.writeString(GL);
        dest.writeString(Tunjangan);
        dest.writeString(jumlah);
        dest.writeString(project_location);
    }

    public int getEmployee_report_id() {
        return employee_report_id;
    }

    public String getYear() {
        return year;
    }

    public String getGP() {
        return GP;
    }

    public String getAB() {
        return AB;
    }

    public String getGL() {
        return GL;
    }

    public String getTunjangan() {
        return Tunjangan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getProject_location() {
        return project_location;
    }
}
