package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ResourcesRequest implements Parcelable {

    private int resources_request_id;
    private String resources_request_number;
    private String job_order_number;
    private String contact_id;
    private String created_by;
    private String begin_date;
    private String end_date;
    private String version;

    public ResourcesRequest(int resources_request_id, String resources_request_number, String job_order_number, String contact_id,
                            String created_by, String begin_date, String end_date, String version){
        this.resources_request_id = resources_request_id;
        this.resources_request_number = resources_request_number;
        this.job_order_number = job_order_number;
        this.contact_id = contact_id;
        this.created_by = created_by;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.version = version;
    }

    protected ResourcesRequest(Parcel in) {
        resources_request_id = in.readInt();
        resources_request_number = in.readString();
        job_order_number = in.readString();
        contact_id = in.readString();
        created_by = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        version = in.readString();
    }

    public ResourcesRequest(JSONObject jsonObject){
        try {
            this.resources_request_id = jsonObject.getInt("resources_request_id");
            this.resources_request_number = jsonObject.getString("resources_request_number");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.contact_id = jsonObject.getString("contact_id");
            this.created_by = jsonObject.getString("created_by");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.version = jsonObject.getString("version");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<ResourcesRequest> CREATOR = new Creator<ResourcesRequest>() {
        @Override
        public ResourcesRequest createFromParcel(Parcel in) {
            return new ResourcesRequest(in);
        }

        @Override
        public ResourcesRequest[] newArray(int size) {
            return new ResourcesRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resources_request_id);
        dest.writeString(resources_request_number);
        dest.writeString(job_order_number);
        dest.writeString(contact_id);
        dest.writeString(created_by);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(version);
    }

    public int getResources_request_id() {
        return resources_request_id;
    }

    public String getResources_request_number() {
        return resources_request_number;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public String getContact_id() {
        return contact_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getVersion() {
        return version;
    }
}
