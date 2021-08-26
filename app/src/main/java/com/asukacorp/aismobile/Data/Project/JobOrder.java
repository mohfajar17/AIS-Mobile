package com.asukacorp.aismobile.Data.Project;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JobOrder implements Parcelable {

    private int job_order_id;
    private String job_order_number;
    private String job_order_status;
    private String job_order_type;
    private String job_order_category_id;
    private String job_order_description;
    private String sales_quotation_id;
    private String department_id;
    private String supervisor;
    private String job_order_location;
    private String begin_date;
    private String end_date;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String amount;
    private String budgeting_amount;
    private String max_pb_amount;
    private String material_amount;
    private String tools_amount;
    private String man_power_amount;
    private String cod_amount;
    private String wo_amount;
    private String material_return_amount;
    private String pb_amount;
    private String cpr_amount;
    private String expenses_amount;
    private String sales_archive;
    private String sales_file_name;
    private String sales_file_type;
    private String client_po_number;
    private String client_po_archive;
    private String client_po_file_name;
    private String client_po_file_type;
    private String tax_type_id;
    private String sales_order_id;
    private String account_job_order;

    public JobOrder(int job_order_id, String job_order_number, String job_order_status, String job_order_type, String job_order_category_id,
                    String job_order_description, String sales_quotation_id, String department_id, String supervisor, String job_order_location, String begin_date,
                    String end_date, String notes, String created_by, String created_date, String modified_by, String modified_date, String amount,
                    String budgeting_amount, String max_pb_amount, String material_amount, String tools_amount, String man_power_amount, String cod_amount,
                    String wo_amount, String material_return_amount, String pb_amount, String cpr_amount, String expenses_amount, String sales_archive,
                    String sales_file_name, String sales_file_type, String client_po_number, String client_po_archive, String client_po_file_name,
                    String client_po_file_type, String tax_type_id, String sales_order_id, String account_job_order){
        this.job_order_id = job_order_id;
        this.job_order_number = job_order_number;
        this.job_order_status = job_order_status;
        this.job_order_type = job_order_type;
        this.job_order_category_id = job_order_category_id;
        this.job_order_description = job_order_description;
        this.sales_quotation_id = sales_quotation_id;
        this.department_id = department_id;
        this.supervisor = supervisor;
        this.job_order_location = job_order_location;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.notes = notes;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
        this.amount = amount;
        this.budgeting_amount = budgeting_amount;
        this.max_pb_amount = max_pb_amount;
        this.material_amount = material_amount;
        this.tools_amount = tools_amount;
        this.man_power_amount = man_power_amount;
        this.cod_amount = cod_amount;
        this.wo_amount = wo_amount;
        this.material_return_amount = material_return_amount;
        this.pb_amount = pb_amount;
        this.cpr_amount = cpr_amount;
        this.expenses_amount = expenses_amount;
        this.sales_archive = sales_archive;
        this.sales_file_name = sales_file_name;
        this.sales_file_type = sales_file_type;
        this.client_po_number = client_po_number;
        this.client_po_archive = client_po_archive;
        this.client_po_file_name = client_po_file_name;
        this.client_po_file_type = client_po_file_type;
        this.tax_type_id = tax_type_id;
        this.sales_order_id = sales_order_id;
        this.account_job_order = account_job_order;
    }

    protected JobOrder(Parcel in) {
        job_order_id = in.readInt();
        job_order_number = in.readString();
        job_order_status = in.readString();
        job_order_type = in.readString();
        job_order_category_id = in.readString();
        job_order_description = in.readString();
        sales_quotation_id = in.readString();
        department_id = in.readString();
        supervisor = in.readString();
        job_order_location = in.readString();
        begin_date = in.readString();
        end_date = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        amount = in.readString();
        budgeting_amount = in.readString();
        max_pb_amount = in.readString();
        material_amount = in.readString();
        tools_amount = in.readString();
        man_power_amount = in.readString();
        cod_amount = in.readString();
        wo_amount = in.readString();
        material_return_amount = in.readString();
        pb_amount = in.readString();
        cpr_amount = in.readString();
        expenses_amount = in.readString();
        sales_archive = in.readString();
        sales_file_name = in.readString();
        sales_file_type = in.readString();
        client_po_number = in.readString();
        client_po_archive = in.readString();
        client_po_file_name = in.readString();
        client_po_file_type = in.readString();
        tax_type_id = in.readString();
        sales_order_id = in.readString();
        account_job_order = in.readString();
    }

    public JobOrder(JSONObject jsonObject){
        try {
            this.job_order_id = jsonObject.getInt("job_order_id");
            this.job_order_number = jsonObject.getString("job_order_number");
            this.job_order_status = jsonObject.getString("job_order_status");
            this.job_order_type = jsonObject.getString("job_order_type");
            this.job_order_category_id = jsonObject.getString("job_order_category_id");
            this.job_order_description = jsonObject.getString("job_order_description");
            this.sales_quotation_id = jsonObject.getString("sales_quotation_id");
            this.department_id = jsonObject.getString("department_id");
            this.supervisor = jsonObject.getString("supervisor");
            this.job_order_location = jsonObject.getString("job_order_location");
            this.begin_date = jsonObject.getString("begin_date");
            this.end_date = jsonObject.getString("end_date");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.amount = jsonObject.getString("amount");
            this.budgeting_amount = jsonObject.getString("budgeting_amount");
            this.max_pb_amount = jsonObject.getString("max_pb_amount");
            this.material_amount = jsonObject.getString("material_amount");
            this.tools_amount = jsonObject.getString("tools_amount");
            this.man_power_amount = jsonObject.getString("man_power_amount");
            this.cod_amount = jsonObject.getString("cod_amount");
            this.wo_amount = jsonObject.getString("wo_amount");
            this.material_return_amount = jsonObject.getString("material_return_amount");
            this.pb_amount = jsonObject.getString("pb_amount");
            this.cpr_amount = jsonObject.getString("cpr_amount");
            this.expenses_amount = jsonObject.getString("expenses_amount");
            this.sales_archive = jsonObject.getString("sales_archive");
            this.sales_file_name = jsonObject.getString("sales_file_name");
            this.sales_file_type = jsonObject.getString("sales_file_type");
            this.client_po_number = jsonObject.getString("client_po_number");
            this.client_po_archive = jsonObject.getString("client_po_archive");
            this.client_po_file_name = jsonObject.getString("client_po_file_name");
            this.client_po_file_type = jsonObject.getString("client_po_file_type");
            this.tax_type_id = jsonObject.getString("tax_type_id");
            this.sales_order_id = jsonObject.getString("sales_order_id");
            this.account_job_order = jsonObject.getString("account_job_order");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JobOrder> CREATOR = new Creator<JobOrder>() {
        @Override
        public JobOrder createFromParcel(Parcel in) {
            return new JobOrder(in);
        }

        @Override
        public JobOrder[] newArray(int size) {
            return new JobOrder[size];
        }
    };

    public JobOrder() { }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(job_order_id);
        dest.writeString(job_order_number);
        dest.writeString(job_order_status);
        dest.writeString(job_order_type);
        dest.writeString(job_order_category_id);
        dest.writeString(job_order_description);
        dest.writeString(sales_quotation_id);
        dest.writeString(department_id);
        dest.writeString(supervisor);
        dest.writeString(job_order_location);
        dest.writeString(begin_date);
        dest.writeString(end_date);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(amount);
        dest.writeString(budgeting_amount);
        dest.writeString(max_pb_amount);
        dest.writeString(material_amount);
        dest.writeString(tools_amount);
        dest.writeString(man_power_amount);
        dest.writeString(cod_amount);
        dest.writeString(wo_amount);
        dest.writeString(material_return_amount);
        dest.writeString(pb_amount);
        dest.writeString(cpr_amount);
        dest.writeString(expenses_amount);
        dest.writeString(sales_archive);
        dest.writeString(sales_file_name);
        dest.writeString(sales_file_type);
        dest.writeString(client_po_number);
        dest.writeString(client_po_archive);
        dest.writeString(client_po_file_name);
        dest.writeString(client_po_file_type);
        dest.writeString(tax_type_id);
        dest.writeString(sales_order_id);
        dest.writeString(account_job_order);
    }

    public int getJob_order_id() {
        return job_order_id;
    }

    public void setJob_order_id(int job_order_id) {
        this.job_order_id = job_order_id;
    }

    public String getJob_order_number() {
        return job_order_number;
    }

    public void setJob_order_number(String job_order_number) {
        this.job_order_number = job_order_number;
    }

    public String getJob_order_status() {
        return job_order_status;
    }

    public void setJob_order_status(String job_order_status) {
        this.job_order_status = job_order_status;
    }

    public String getJob_order_type() {
        return job_order_type;
    }

    public void setJob_order_type(String job_order_type) {
        this.job_order_type = job_order_type;
    }

    public String getJob_order_category_id() {
        return job_order_category_id;
    }

    public void setJob_order_category_id(String job_order_category_id) {
        this.job_order_category_id = job_order_category_id;
    }

    public String getJob_order_description() {
        return job_order_description;
    }

    public void setJob_order_description(String job_order_description) {
        this.job_order_description = job_order_description;
    }

    public String getSales_quotation_id() {
        return sales_quotation_id;
    }

    public void setSales_quotation_id(String sales_quotation_id) {
        this.sales_quotation_id = sales_quotation_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getJob_order_location() {
        return job_order_location;
    }

    public void setJob_order_location(String job_order_location) {
        this.job_order_location = job_order_location;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBudgeting_amount() {
        return budgeting_amount;
    }

    public void setBudgeting_amount(String budgeting_amount) {
        this.budgeting_amount = budgeting_amount;
    }

    public String getMax_pb_amount() {
        return max_pb_amount;
    }

    public void setMax_pb_amount(String max_pb_amount) {
        this.max_pb_amount = max_pb_amount;
    }

    public String getMaterial_amount() {
        return material_amount;
    }

    public void setMaterial_amount(String material_amount) {
        this.material_amount = material_amount;
    }

    public String getTools_amount() {
        return tools_amount;
    }

    public void setTools_amount(String tools_amount) {
        this.tools_amount = tools_amount;
    }

    public String getMan_power_amount() {
        return man_power_amount;
    }

    public void setMan_power_amount(String man_power_amount) {
        this.man_power_amount = man_power_amount;
    }

    public String getCod_amount() {
        return cod_amount;
    }

    public void setCod_amount(String cod_amount) {
        this.cod_amount = cod_amount;
    }

    public String getWo_amount() {
        return wo_amount;
    }

    public void setWo_amount(String wo_amount) {
        this.wo_amount = wo_amount;
    }

    public String getMaterial_return_amount() {
        return material_return_amount;
    }

    public void setMaterial_return_amount(String material_return_amount) {
        this.material_return_amount = material_return_amount;
    }

    public String getPb_amount() {
        return pb_amount;
    }

    public void setPb_amount(String pb_amount) {
        this.pb_amount = pb_amount;
    }

    public String getCpr_amount() {
        return cpr_amount;
    }

    public void setCpr_amount(String cpr_amount) {
        this.cpr_amount = cpr_amount;
    }

    public String getExpenses_amount() {
        return expenses_amount;
    }

    public void setExpenses_amount(String expenses_amount) {
        this.expenses_amount = expenses_amount;
    }

    public String getSales_archive() {
        return sales_archive;
    }

    public void setSales_archive(String sales_archive) {
        this.sales_archive = sales_archive;
    }

    public String getSales_file_name() {
        return sales_file_name;
    }

    public void setSales_file_name(String sales_file_name) {
        this.sales_file_name = sales_file_name;
    }

    public String getSales_file_type() {
        return sales_file_type;
    }

    public void setSales_file_type(String sales_file_type) {
        this.sales_file_type = sales_file_type;
    }

    public String getClient_po_number() {
        return client_po_number;
    }

    public void setClient_po_number(String client_po_number) {
        this.client_po_number = client_po_number;
    }

    public String getClient_po_archive() {
        return client_po_archive;
    }

    public void setClient_po_archive(String client_po_archive) {
        this.client_po_archive = client_po_archive;
    }

    public String getClient_po_file_name() {
        return client_po_file_name;
    }

    public void setClient_po_file_name(String client_po_file_name) {
        this.client_po_file_name = client_po_file_name;
    }

    public String getClient_po_file_type() {
        return client_po_file_type;
    }

    public void setClient_po_file_type(String client_po_file_type) {
        this.client_po_file_type = client_po_file_type;
    }

    public String getTax_type_id() {
        return tax_type_id;
    }

    public void setTax_type_id(String tax_type_id) {
        this.tax_type_id = tax_type_id;
    }

    public String getSales_order_id() {
        return sales_order_id;
    }

    public void setSales_order_id(String sales_order_id) {
        this.sales_order_id = sales_order_id;
    }

    public String getAccount_job_order() {
        return account_job_order;
    }

    public void setAccount_job_order(String account_job_order) {
        this.account_job_order = account_job_order;
    }
}
