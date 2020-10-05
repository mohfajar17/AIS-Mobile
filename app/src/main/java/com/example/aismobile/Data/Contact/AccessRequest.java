package com.example.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class AccessRequest implements Parcelable {
    private int access_request_id;
    private String access_request_number;
    private String notes;
    private String request_date;
    private String approval1;
    private String employee_request;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String approval_date;
    private String approval_comment1;

    public AccessRequest(int access_request_id, String access_request_number, String notes, String request_date,
                   String approval1, String employee_request){
        this.access_request_id = access_request_id;
        this.access_request_number = access_request_number;
        this.notes = notes;
        this.request_date = request_date;
        this.approval1 = approval1;
        this.employee_request = employee_request;
    }

    protected AccessRequest(Parcel in) {
        access_request_id = in.readInt();
        access_request_number = in.readString();
        notes = in.readString();
        request_date = in.readString();
        approval1 = in.readString();
        employee_request = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        approval_date = in.readString();
        approval_comment1 = in.readString();
    }

    public AccessRequest(JSONObject jsonObject){
        try {
            this.access_request_id = jsonObject.getInt("access_request_id");
            this.access_request_number = jsonObject.getString("access_request_number");
            this.notes = jsonObject.getString("notes");
            this.request_date = jsonObject.getString("request_date");
            this.approval1 = jsonObject.getString("approval1");
            this.employee_request = jsonObject.getString("employee_request");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.approval_date = jsonObject.getString("approval_date");
            this.approval_comment1 = jsonObject.getString("approval_comment1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<AccessRequest> CREATOR = new Creator<AccessRequest>() {
        @Override
        public AccessRequest createFromParcel(Parcel in) {
            return new AccessRequest(in);
        }

        @Override
        public AccessRequest[] newArray(int size) {
            return new AccessRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(access_request_id);
        dest.writeString(access_request_number);
        dest.writeString(notes);
        dest.writeString(request_date);
        dest.writeString(approval1);
        dest.writeString(employee_request);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(approval_date);
        dest.writeString(approval_comment1);
    }

    public int getAccess_request_id() {
        return access_request_id;
    }

    public String getAccess_request_number() {
        return access_request_number;
    }

    public String getNotes() {
        return notes;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getEmployee_request() {
        return employee_request;
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

    public String getApproval_date() {
        return approval_date;
    }

    public String getApproval_comment1() {
        return approval_comment1;
    }
}
