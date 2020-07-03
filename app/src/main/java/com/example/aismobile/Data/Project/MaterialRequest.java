package com.example.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MaterialRequest implements Parcelable {

    private int material_request_id;
    private String material_request_number;
    private String material_request_status;
    private String job_order_id;
    private String sales_quotation_id;
    private String requisition_date;
    private String usage_date;
    private String notes;
    private String version;
    private String priority;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String checked_by;
    private String checked_date;
    private String checked_comment;
    private String approval1;
    private String approval_date1;
    private String comment1;
    private String approval2;
    private String approval_date2;
    private String comment2;
    private String approval3;
    private String approval_date3;
    private String comment3;

    public MaterialRequest (int material_request_id, String material_request_number, String material_request_status,
                            String job_order_id, String sales_quotation_id, String requisition_date, String usage_date,
                            String notes, String version, String priority, String created_by, String created_date,
                            String modified_by, String modified_date, String checked_by, String checked_date,
                            String checked_comment, String approval1, String approval_date1, String comment1,
                            String approval2, String approval_date2, String comment2, String approval3,
                            String approval_date3, String comment3){
        this.material_request_id = material_request_id;
        this.material_request_number = material_request_number;
        this.material_request_status = material_request_status;
        this.job_order_id = job_order_id;
        this.sales_quotation_id = sales_quotation_id;
        this.requisition_date = requisition_date;
        this.usage_date = usage_date;
        this.notes = notes;
        this.version = version;
        this.priority = priority;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.checked_by = checked_by;
        this.checked_date = checked_date;
        this.checked_comment = checked_comment;
        this.approval1 = approval1;
        this.approval_date1 = approval_date1;
        this.comment1 = comment1;
        this.approval2 = approval2;
        this.approval_date2 = approval_date2;
        this.comment2 = comment2;
        this.approval3 = approval3;
        this.approval_date3 = approval_date3;
        this.comment3 = comment3;
    }

    protected MaterialRequest(Parcel in) {
        material_request_id = in.readInt();
        material_request_number = in.readString();
        material_request_status = in.readString();
        job_order_id = in.readString();
        sales_quotation_id = in.readString();
        requisition_date = in.readString();
        usage_date = in.readString();
        notes = in.readString();
        version = in.readString();
        priority = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        checked_by = in.readString();
        checked_date = in.readString();
        checked_comment = in.readString();
        approval1 = in.readString();
        approval_date1 = in.readString();
        comment1 = in.readString();
        approval2 = in.readString();
        approval_date2 = in.readString();
        comment2 = in.readString();
        approval3 = in.readString();
        approval_date3 = in.readString();
        comment3 = in.readString();
    }

    public MaterialRequest(JSONObject jsonObject){
        try {
            this.material_request_id = jsonObject.getInt("material_request_id");
            this.material_request_number = jsonObject.getString("material_request_number");
            this.material_request_status = jsonObject.getString("material_request_status");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.sales_quotation_id = jsonObject.getString("sales_quotation_id");
            this.requisition_date = jsonObject.getString("requisition_date");
            this.usage_date = jsonObject.getString("usage_date");
            this.notes = jsonObject.getString("notes");
            this.version = jsonObject.getString("version");
            this.priority = jsonObject.getString("priority");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.checked_by = jsonObject.getString("checked_by");
            this.checked_date = jsonObject.getString("checked_date");
            this.checked_comment = jsonObject.getString("checked_comment");
            this.approval1 = jsonObject.getString("approval1");
            this.approval_date1 = jsonObject.getString("approval_date1");
            this.comment1 = jsonObject.getString("comment1");
            this.approval2 = jsonObject.getString("approval2");
            this.approval_date2 = jsonObject.getString("approval_date2");
            this.comment2 = jsonObject.getString("comment2");
            this.approval3 = jsonObject.getString("approval3");
            this.approval_date3 = jsonObject.getString("approval_date3");
            this.comment3 = jsonObject.getString("comment3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MaterialRequest> CREATOR = new Creator<MaterialRequest>() {
        @Override
        public MaterialRequest createFromParcel(Parcel in) {
            return new MaterialRequest(in);
        }

        @Override
        public MaterialRequest[] newArray(int size) {
            return new MaterialRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_request_id);
        dest.writeString(material_request_number);
        dest.writeString(material_request_status);
        dest.writeString(job_order_id);
        dest.writeString(sales_quotation_id);
        dest.writeString(requisition_date);
        dest.writeString(usage_date);
        dest.writeString(notes);
        dest.writeString(version);
        dest.writeString(priority);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(checked_by);
        dest.writeString(checked_date);
        dest.writeString(checked_comment);
        dest.writeString(approval1);
        dest.writeString(approval_date1);
        dest.writeString(comment1);
        dest.writeString(approval2);
        dest.writeString(approval_date2);
        dest.writeString(comment2);
        dest.writeString(approval3);
        dest.writeString(approval_date3);
        dest.writeString(comment3);
    }

    public int getMaterial_request_id() {
        return material_request_id;
    }

    public String getMaterial_request_number() {
        return material_request_number;
    }

    public String getMaterial_request_status() {
        return material_request_status;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getSales_quotation_id() {
        return sales_quotation_id;
    }

    public String getRequisition_date() {
        return requisition_date;
    }

    public String getUsage_date() {
        return usage_date;
    }

    public String getNotes() {
        return notes;
    }

    public String getVersion() {
        return version;
    }

    public String getPriority() {
        return priority;
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

    public String getChecked_by() {
        return checked_by;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public String getChecked_comment() {
        return checked_comment;
    }

    public String getApproval1() {
        return approval1;
    }

    public String getApproval_date1() {
        return approval_date1;
    }

    public String getComment1() {
        return comment1;
    }

    public String getApproval2() {
        return approval2;
    }

    public String getApproval_date2() {
        return approval_date2;
    }

    public String getComment2() {
        return comment2;
    }

    public String getApproval3() {
        return approval3;
    }

    public String getApproval_date3() {
        return approval_date3;
    }

    public String getComment3() {
        return comment3;
    }
}
